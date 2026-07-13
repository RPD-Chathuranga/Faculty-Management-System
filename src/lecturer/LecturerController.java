package lecturer;

import dashboard.DashboardView;
import department.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class LecturerController {

    private final LecturerView view;
    private final LecturerService service;
    private final Stage stage;

    private Lecturer selectedLecturer;

    private final ObservableList<Lecturer> lecturerData =
            FXCollections.observableArrayList();

    private final ObservableList<Department> departmentData =
            FXCollections.observableArrayList();

    public LecturerController(
            LecturerView view,
            Stage stage
    ) {

        this.view = view;
        this.stage = stage;
        this.service = new LecturerService();

        connectButtons();
        connectTableSelection();
        connectSearch();

        loadDepartments();
        loadLecturers();

        updateButtonStates();
        updateStatistics();
    }

    // =========================================================
    // CONNECT BUTTONS
    // =========================================================

    private void connectButtons() {

        view.getAddButton().setOnAction(
                event -> addLecturer()
        );

        view.getUpdateButton().setOnAction(
                event -> updateLecturer()
        );

        view.getDeleteButton().setOnAction(
                event -> deleteLecturer()
        );

        view.getClearButton().setOnAction(
                event -> clearForm()
        );

        view.getBackButton().setOnAction(
                event -> returnToDashboard()
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

        view.getDepartmentComboBox()
                .setItems(
                        departmentData
                );
    }

    // =========================================================
    // LOAD LECTURERS
    // =========================================================

    private void loadLecturers() {

        List<Lecturer> lecturers =
                service.getAllLecturers();

        lecturerData.setAll(
                lecturers
        );

        updateStatistics();
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void connectSearch() {

        FilteredList<Lecturer> filteredData =
                new FilteredList<>(
                        lecturerData,
                        lecturer -> true
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
                                    lecturer -> {

                                        if (newValue == null
                                                || newValue.trim().isEmpty()) {

                                            return true;
                                        }

                                        String searchText =
                                                newValue
                                                        .trim()
                                                        .toLowerCase();

                                        String lecturerId =
                                                String.valueOf(
                                                        lecturer.getLecturerId()
                                                );

                                        String employeeNumber =
                                                lecturer.getEmployeeNumber() == null
                                                        ? ""
                                                        : lecturer.getEmployeeNumber()
                                                        .toLowerCase();

                                        String fullName =
                                                lecturer.getFullName() == null
                                                        ? ""
                                                        : lecturer.getFullName()
                                                        .toLowerCase();

                                        String username =
                                                lecturer.getUsername() == null
                                                        ? ""
                                                        : lecturer.getUsername()
                                                        .toLowerCase();

                                        String email =
                                                lecturer.getEmail() == null
                                                        ? ""
                                                        : lecturer.getEmail()
                                                        .toLowerCase();

                                        String phone =
                                                lecturer.getPhone() == null
                                                        ? ""
                                                        : lecturer.getPhone()
                                                        .toLowerCase();

                                        String departmentName =
                                                lecturer.getDepartmentName() == null
                                                        ? ""
                                                        : lecturer.getDepartmentName()
                                                        .toLowerCase();

                                        String specialization =
                                                lecturer.getSpecialization() == null
                                                        ? ""
                                                        : lecturer.getSpecialization()
                                                        .toLowerCase();

                                        return lecturerId.contains(
                                                searchText
                                        )
                                                || employeeNumber.contains(
                                                searchText
                                        )
                                                || fullName.contains(
                                                searchText
                                        )
                                                || username.contains(
                                                searchText
                                        )
                                                || email.contains(
                                                searchText
                                        )
                                                || phone.contains(
                                                searchText
                                        )
                                                || departmentName.contains(
                                                searchText
                                        )
                                                || specialization.contains(
                                                searchText
                                        );
                                    }
                            );
                        }
                );

        SortedList<Lecturer> sortedData =
                new SortedList<>(
                        filteredData
                );

        sortedData.comparatorProperty()
                .bind(
                        view.getLecturerTable()
                                .comparatorProperty()
                );

        view.getLecturerTable()
                .setItems(
                        sortedData
                );
    }     // =========================================================
    // TABLE SELECTION
    // =========================================================

    private void connectTableSelection() {

        view.getLecturerTable()
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (
                                observable,
                                oldSelection,
                                newSelection
                        ) -> {

                            selectedLecturer = newSelection;

                            if (newSelection != null) {

                                populateForm(
                                        newSelection
                                );

                            } else {

                                clearFormFieldsOnly();
                            }

                            updateButtonStates();
                            updateStatistics();

                        }
                );

    }

    // =========================================================
    // POPULATE FORM
    // =========================================================

    private void populateForm(
            Lecturer lecturer
    ) {

        view.getFullNameField()
                .setText(
                        lecturer.getFullName()
                );

        view.getUsernameField()
                .setText(
                        lecturer.getUsername()
                );

        // Leave password empty on update
        view.getPasswordField().clear();

        view.getEmailField()
                .setText(
                        lecturer.getEmail()
                );

        view.getPhoneField()
                .setText(
                        lecturer.getPhone()
                );

        view.getEmployeeNumberField()
                .setText(
                        lecturer.getEmployeeNumber()
                );

        view.getSpecializationField()
                .setText(
                        lecturer.getSpecialization()
                );

        selectDepartment(
                lecturer.getDepartmentId()
        );

    }

    // =========================================================
    // SELECT DEPARTMENT
    // =========================================================

    private void selectDepartment(
            int departmentId
    ) {

        for (Department department : departmentData) {

            if (department.getDepartmentId()
                    == departmentId) {

                view.getDepartmentComboBox()
                        .getSelectionModel()
                        .select(
                                department
                        );

                break;

            }

        }

    }

    // =========================================================
    // CREATE LECTURER OBJECT
    // =========================================================

    private Lecturer createLecturerFromForm() {

        Department department =
                view.getDepartmentComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        if (department == null) {

            return null;

        }

        Lecturer lecturer =
                new Lecturer();

        if (selectedLecturer != null) {

            lecturer.setLecturerId(
                    selectedLecturer.getLecturerId()
            );

            lecturer.setUserId(
                    selectedLecturer.getUserId()
            );

        }

        lecturer.setFullName(
                view.getFullNameField()
                        .getText()
                        .trim()
        );

        lecturer.setUsername(
                view.getUsernameField()
                        .getText()
                        .trim()
        );

        lecturer.setPassword(
                view.getPasswordField()
                        .getText()
                        .trim()
        );

        lecturer.setEmail(
                view.getEmailField()
                        .getText()
                        .trim()
        );

        lecturer.setPhone(
                view.getPhoneField()
                        .getText()
                        .trim()
        );

        lecturer.setEmployeeNumber(
                view.getEmployeeNumberField()
                        .getText()
                        .trim()
        );

        lecturer.setSpecialization(
                view.getSpecializationField()
                        .getText()
                        .trim()
        );

        lecturer.setDepartmentId(
                department.getDepartmentId()
        );

        lecturer.setDepartmentName(
                department.getDepartmentName()
        );

        return lecturer;

    }

    // =========================================================
    // ADD LECTURER
    // =========================================================

    private void addLecturer() {

        Lecturer lecturer =
                createLecturerFromForm();

        if (lecturer == null) {

            showWarning(
                    "Department Required",
                    "Please select a department."
            );

            return;

        }

        boolean success =
                service.addLecturer(
                        lecturer
                );

        if (success) {

            showInformation(
                    "Success",
                    "Lecturer account created successfully."
            );

            loadLecturers();

            clearForm();

        } else {

            showError(
                    "Add Failed",
                    "Unable to create lecturer.\n\n" +
                            "Please check:\n" +
                            "• Username\n" +
                            "• Email\n" +
                            "• Employee Number\n\n" +
                            "They must all be unique."
            );

        }

    }     // =========================================================
    // UPDATE LECTURER
    // =========================================================

    private void updateLecturer() {

        if (selectedLecturer == null) {

            showWarning(
                    "Selection Required",
                    "Please select a lecturer to update."
            );

            return;
        }

        Lecturer lecturer =
                createLecturerFromForm();

        if (lecturer == null) {

            showWarning(
                    "Department Required",
                    "Please select a department."
            );

            return;
        }

        boolean success =
                service.updateLecturer(
                        lecturer
                );

        if (success) {

            showInformation(
                    "Success",
                    "Lecturer updated successfully."
            );

            loadLecturers();

            clearForm();

        } else {

            showError(
                    "Update Failed",
                    "Unable to update lecturer.\n\n" +
                            "Username, Email or Employee Number may already exist."
            );

        }

    }

    // =========================================================
    // DELETE LECTURER
    // =========================================================

    private void deleteLecturer() {

        if (selectedLecturer == null) {

            showWarning(
                    "Selection Required",
                    "Please select a lecturer."
            );

            return;
        }

        Alert confirmation =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmation.setTitle(
                "Delete Lecturer"
        );

        confirmation.setHeaderText(
                "Delete Lecturer"
        );

        confirmation.setContentText(
                "Delete "
                        + selectedLecturer.getFullName()
                        + " ?\n\nThis will also remove the lecturer login account."
        );

        Optional<ButtonType> result =
                confirmation.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            boolean success =
                    service.deleteLecturer(
                            selectedLecturer.getUserId()
                    );

            if (success) {

                showInformation(
                        "Deleted",
                        "Lecturer deleted successfully."
                );

                loadLecturers();

                clearForm();

            } else {

                showError(
                        "Delete Failed",
                        "Unable to delete lecturer."
                );

            }

        }

    }

    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        selectedLecturer = null;

        clearFormFieldsOnly();

        view.getLecturerTable()
                .getSelectionModel()
                .clearSelection();

        updateButtonStates();

        updateStatistics();

    }

    // =========================================================
    // CLEAR FIELDS ONLY
    // =========================================================

    private void clearFormFieldsOnly() {

        view.getFullNameField().clear();

        view.getUsernameField().clear();

        view.getPasswordField().clear();

        view.getEmailField().clear();

        view.getPhoneField().clear();

        view.getEmployeeNumberField().clear();

        view.getSpecializationField().clear();

        view.getDepartmentComboBox()
                .getSelectionModel()
                .clearSelection();

    }     // =========================================================
    // STATISTICS
    // =========================================================

    private void updateStatistics() {

        view.getTotalLecturersValue()
                .setText(
                        String.valueOf(
                                lecturerData.size()
                        )
                );

        view.getDatabaseStatusValue()
                .setText(
                        "Connected"
                );

        if (selectedLecturer == null) {

            view.getSelectedLecturerValue()
                    .setText(
                            "None"
                    );

        } else {

            view.getSelectedLecturerValue()
                    .setText(
                            selectedLecturer.getFullName()
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
                selectedLecturer == null;

        view.getUpdateButton()
                .setDisable(
                        noSelection
                );

        view.getDeleteButton()
                .setDisable(
                        noSelection
                );

        view.getAddButton()
                .setDisable(
                        !noSelection
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

        view.getSystemStatusValue()
                .setText(
                        message
                );
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

        view.getSystemStatusValue()
                .setText(
                        title
                );
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

        view.getSystemStatusValue()
                .setText(
                        title
                );
    }
}