package department;

import dashboard.DashboardView;
import faculty.Faculty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class DepartmentController {

    private final DepartmentView view;
    private final DepartmentService service;
    private final Stage stage;

    private Department selectedDepartment;

    private final ObservableList<Department> departmentData =
            FXCollections.observableArrayList();

    private final ObservableList<Faculty> facultyData =
            FXCollections.observableArrayList();

    public DepartmentController(
            DepartmentView view,
            Stage stage
    ) {

        this.view = view;
        this.stage = stage;
        this.service = new DepartmentService();

        connectButtons();
        connectTableSelection();
        connectSearch();

        loadFaculties();
        loadDepartments();

        updateButtonStates();
        updateStatistics();
    }

    // =========================================================
    // CONNECT BUTTONS
    // =========================================================

    private void connectButtons() {

        view.getAddButton().setOnAction(
                event -> addDepartment()
        );

        view.getUpdateButton().setOnAction(
                event -> updateDepartment()
        );

        view.getDeleteButton().setOnAction(
                event -> deleteDepartment()
        );

        view.getClearButton().setOnAction(
                event -> clearForm()
        );

        view.getBackButton().setOnAction(
                event -> returnToDashboard()
        );
    }

    // =========================================================
    // LOAD FACULTIES
    // =========================================================

    private void loadFaculties() {

        List<Faculty> faculties =
                service.getAllFaculties();

        facultyData.setAll(
                faculties
        );

        view.getFacultyComboBox()
                .setItems(
                        facultyData
                );
    }

    // =========================================================
    // LOAD DEPARTMENTS
    // =========================================================

    private void loadDepartments() {

        List<Department> departments =
                service.getAllDepartments();

        departmentData.setAll(
                departments
        );

        updateStatistics();
    }

    // =========================================================
    // TABLE SELECTION
    // =========================================================

    private void connectTableSelection() {

        view.getDepartmentTable()
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (
                                observable,
                                oldSelection,
                                newSelection
                        ) -> {

                            if (newSelection != null) {

                                selectedDepartment =
                                        newSelection;

                                populateForm(
                                        newSelection
                                );

                            } else {

                                selectedDepartment =
                                        null;

                                updateStatistics();
                            }

                            updateButtonStates();
                        }
                );
    }

    private void populateForm(
            Department department
    ) {

        view.getDepartmentNameField()
                .setText(
                        department.getDepartmentName()
                );

        selectFacultyInComboBox(
                department.getFacultyId()
        );

        updateStatistics();
    }

    private void selectFacultyInComboBox(
            int facultyId
    ) {

        for (Faculty faculty : facultyData) {

            if (faculty.getFacultyId()
                    == facultyId) {

                view.getFacultyComboBox()
                        .getSelectionModel()
                        .select(
                                faculty
                        );

                return;
            }
        }

        view.getFacultyComboBox()
                .getSelectionModel()
                .clearSelection();
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void connectSearch() {

        FilteredList<Department> filteredData =
                new FilteredList<>(
                        departmentData,
                        department -> true
                );

        view.getSearchField()
                .textProperty()
                .addListener(
                        (
                                observable,
                                oldValue,
                                newValue
                        ) -> {

                            filteredData.setPredicate(
                                    department -> {

                                        if (newValue == null
                                                || newValue.trim().isEmpty()) {

                                            return true;
                                        }

                                        String searchText =
                                                newValue
                                                        .trim()
                                                        .toLowerCase();

                                        String departmentId =
                                                String.valueOf(
                                                        department
                                                                .getDepartmentId()
                                                );

                                        String departmentName =
                                                department
                                                        .getDepartmentName()
                                                        == null
                                                        ? ""
                                                        : department
                                                          .getDepartmentName()
                                                          .toLowerCase();

                                        String facultyName =
                                                department
                                                        .getFacultyName()
                                                        == null
                                                        ? ""
                                                        : department
                                                          .getFacultyName()
                                                          .toLowerCase();

                                        return departmentId.contains(
                                                searchText
                                        )
                                                || departmentName.contains(
                                                searchText
                                        )
                                                || facultyName.contains(
                                                searchText
                                        );
                                    }
                            );
                        }
                );

        SortedList<Department> sortedData =
                new SortedList<>(
                        filteredData
                );

        sortedData.comparatorProperty()
                .bind(
                        view.getDepartmentTable()
                                .comparatorProperty()
                );

        view.getDepartmentTable()
                .setItems(
                        sortedData
                );
    }

    // =========================================================
    // ADD DEPARTMENT
    // =========================================================

    private void addDepartment() {

        String departmentName =
                view.getDepartmentNameField()
                        .getText()
                        .trim();

        Faculty selectedFaculty =
                view.getFacultyComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        if (!service.isValidDepartmentName(
                departmentName
        )) {

            showWarning(
                    "Invalid Department Name",
                    "Please enter a department name between 2 and 100 characters."
            );

            view.getSystemStatusValue()
                    .setText(
                            "Validation Error"
                    );

            view.getDepartmentNameField()
                    .requestFocus();

            return;
        }

        if (selectedFaculty == null) {

            showWarning(
                    "Faculty Required",
                    "Please select a faculty for the department."
            );

            view.getSystemStatusValue()
                    .setText(
                            "Faculty Required"
                    );

            view.getFacultyComboBox()
                    .requestFocus();

            return;
        }

        if (service.departmentNameExists(
                departmentName,
                selectedFaculty.getFacultyId(),
                0
        )) {

            showWarning(
                    "Duplicate Department",
                    "This department already exists under the selected faculty."
            );

            view.getSystemStatusValue()
                    .setText(
                            "Duplicate Department"
                    );

            view.getDepartmentNameField()
                    .requestFocus();

            return;
        }

        boolean added =
                service.addDepartment(
                        departmentName,
                        selectedFaculty.getFacultyId()
                );

        if (added) {

            showInformation(
                    "Department Added",
                    "The department was added successfully."
            );

            loadDepartments();
            clearForm();

            view.getSystemStatusValue()
                    .setText(
                            "Department Added"
                    );

        } else {

            showError(
                    "Add Failed",
                    "The department could not be added."
            );

            view.getSystemStatusValue()
                    .setText(
                            "Add Failed"
                    );
        }
    }

    // =========================================================
    // UPDATE DEPARTMENT
    // =========================================================

    private void updateDepartment() {

        if (selectedDepartment == null) {

            showWarning(
                    "No Department Selected",
                    "Please select a department before updating."
            );

            view.getSystemStatusValue()
                    .setText(
                            "No Selection"
                    );

            return;
        }

        String departmentName =
                view.getDepartmentNameField()
                        .getText()
                        .trim();

        Faculty selectedFaculty =
                view.getFacultyComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        if (!service.isValidDepartmentName(
                departmentName
        )) {

            showWarning(
                    "Invalid Department Name",
                    "Please enter a department name between 2 and 100 characters."
            );

            view.getSystemStatusValue()
                    .setText(
                            "Validation Error"
                    );

            view.getDepartmentNameField()
                    .requestFocus();

            return;
        }

        if (selectedFaculty == null) {

            showWarning(
                    "Faculty Required",
                    "Please select a faculty for the department."
            );

            view.getSystemStatusValue()
                    .setText(
                            "Faculty Required"
                    );

            view.getFacultyComboBox()
                    .requestFocus();

            return;
        }

        if (service.departmentNameExists(
                departmentName,
                selectedFaculty.getFacultyId(),
                selectedDepartment.getDepartmentId()
        )) {

            showWarning(
                    "Duplicate Department",
                    "Another department with this name already exists under the selected faculty."
            );

            view.getSystemStatusValue()
                    .setText(
                            "Duplicate Department"
                    );

            return;
        }

        boolean updated =
                service.updateDepartment(
                        selectedDepartment.getDepartmentId(),
                        departmentName,
                        selectedFaculty.getFacultyId()
                );

        if (updated) {

            showInformation(
                    "Department Updated",
                    "The department was updated successfully."
            );

            loadDepartments();
            clearForm();

            view.getSystemStatusValue()
                    .setText(
                            "Department Updated"
                    );

        } else {

            showError(
                    "Update Failed",
                    "The department could not be updated."
            );

            view.getSystemStatusValue()
                    .setText(
                            "Update Failed"
                    );
        }
    }

    // =========================================================
    // DELETE DEPARTMENT
    // =========================================================

    private void deleteDepartment() {

        if (selectedDepartment == null) {

            showWarning(
                    "No Department Selected",
                    "Please select a department before deleting."
            );

            view.getSystemStatusValue()
                    .setText(
                            "No Selection"
                    );

            return;
        }

        Alert confirmation =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmation.setTitle(
                "Delete Department"
        );

        confirmation.setHeaderText(
                "Delete "
                        + selectedDepartment
                        .getDepartmentName()
                        + "?"
        );

        confirmation.setContentText(
                "Faculty: "
                        + selectedDepartment
                        .getFacultyName()
                        + "\n\nThis action cannot be undone."
        );

        Optional<ButtonType> result =
                confirmation.showAndWait();

        if (result.isEmpty()
                || result.get() != ButtonType.OK) {

            view.getSystemStatusValue()
                    .setText(
                            "Delete Cancelled"
                    );

            return;
        }

        boolean deleted =
                service.deleteDepartment(
                        selectedDepartment
                                .getDepartmentId()
                );

        if (deleted) {

            showInformation(
                    "Department Deleted",
                    "The department was deleted successfully."
            );

            loadDepartments();
            clearForm();

            view.getSystemStatusValue()
                    .setText(
                            "Department Deleted"
                    );

        } else {

            showError(
                    "Delete Failed",
                    "The department could not be deleted. It may be linked to lecturers, students, courses or other records."
            );

            view.getSystemStatusValue()
                    .setText(
                            "Delete Failed"
                    );
        }
    }

    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        view.getDepartmentNameField()
                .clear();

        view.getFacultyComboBox()
                .getSelectionModel()
                .clearSelection();

        view.getDepartmentTable()
                .getSelectionModel()
                .clearSelection();

        selectedDepartment = null;

        view.getDepartmentNameField()
                .requestFocus();

        updateButtonStates();
        updateStatistics();
    }

    // =========================================================
    // STATISTICS
    // =========================================================

    private void updateStatistics() {

        view.getTotalDepartmentsValue()
                .setText(
                        String.valueOf(
                                departmentData.size()
                        )
                );

        view.getDatabaseStatusValue()
                .setText(
                        "Connected"
                );

        if (selectedDepartment == null) {

            view.getSelectedDepartmentValue()
                    .setText(
                            "None"
                    );

        } else {

            view.getSelectedDepartmentValue()
                    .setText(
                            selectedDepartment
                                    .getDepartmentName()
                    );
        }

        String currentStatus =
                view.getSystemStatusValue()
                        .getText();

        if (currentStatus == null
                || currentStatus.trim().isEmpty()) {

            view.getSystemStatusValue()
                    .setText(
                            "Ready"
                    );
        }
    }

    // =========================================================
    // BUTTON STATES
    // =========================================================

    private void updateButtonStates() {

        boolean noSelection =
                selectedDepartment == null;

        view.getUpdateButton()
                .setDisable(
                        noSelection
                );

        view.getDeleteButton()
                .setDisable(
                        noSelection
                );
    }

    // =========================================================
    // RETURN TO DASHBOARD
    // =========================================================

    private void returnToDashboard() {

        DashboardView dashboardView =
                new DashboardView(stage);

        dashboardView.show();
    }

    // =========================================================
    // ALERT METHODS
    // =========================================================

    private void showInformation(
            String title,
            String message
    ) {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(
            String title,
            String message
    ) {

        Alert alert =
                new Alert(
                        Alert.AlertType.WARNING
                );

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(
            String title,
            String message
    ) {

        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}