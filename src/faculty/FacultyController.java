package faculty;

import dashboard.DashboardView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class FacultyController {

    private final FacultyView view;
    private final FacultyService service;
    private final Stage stage;

    private Faculty selectedFaculty;

    private final ObservableList<Faculty> facultyData =
            FXCollections.observableArrayList();

    public FacultyController(
            FacultyView view,
            Stage stage
    ) {

        this.view = view;
        this.stage = stage;
        this.service = new FacultyService();

        connectButtons();
        connectTableSelection();
        connectSearch();
        loadFaculties();
        updateButtonStates();
    }

    // =========================================================
    // CONNECT BUTTONS
    // =========================================================

    private void connectButtons() {

        view.getAddButton().setOnAction(
                event -> addFaculty()
        );

        view.getUpdateButton().setOnAction(
                event -> updateFaculty()
        );

        view.getDeleteButton().setOnAction(
                event -> deleteFaculty()
        );

        view.getClearButton().setOnAction(
                event -> clearForm()
        );

        view.getBackButton().setOnAction(
                event -> returnToDashboard()
        );
    }

    // =========================================================
    // TABLE SELECTION
    // =========================================================

    private void connectTableSelection() {

        view.getFacultyTable()
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (
                                observable,
                                oldSelection,
                                newSelection
                        ) -> {

                            if (newSelection != null) {

                                selectedFaculty =
                                        newSelection;

                                populateForm(
                                        newSelection
                                );

                            } else {

                                selectedFaculty =
                                        null;

                                updateStatistics();
                            }

                            updateButtonStates();
                        }
                );
    }

    private void populateForm(
            Faculty faculty
    ) {

        view.getFacultyNameField()
                .setText(
                        faculty.getFacultyName()
                );

        view.getDescriptionArea()
                .setText(
                        faculty.getDescription() == null
                                ? ""
                                : faculty.getDescription()
                );

        updateStatistics();
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void connectSearch() {

        FilteredList<Faculty> filteredData =
                new FilteredList<>(
                        facultyData,
                        faculty -> true
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
                                    faculty -> {

                                        if (newValue == null
                                                || newValue.trim().isEmpty()) {

                                            return true;
                                        }

                                        String searchText =
                                                newValue
                                                        .trim()
                                                        .toLowerCase();

                                        String facultyId =
                                                String.valueOf(
                                                        faculty.getFacultyId()
                                                );

                                        String facultyName =
                                                faculty.getFacultyName() == null
                                                        ? ""
                                                        : faculty.getFacultyName()
                                                          .toLowerCase();

                                        String description =
                                                faculty.getDescription() == null
                                                        ? ""
                                                        : faculty.getDescription()
                                                          .toLowerCase();

                                        return facultyId.contains(
                                                searchText
                                        )
                                                || facultyName.contains(
                                                searchText
                                        )
                                                || description.contains(
                                                searchText
                                        );
                                    }
                            );
                        }
                );

        SortedList<Faculty> sortedData =
                new SortedList<>(
                        filteredData
                );

        sortedData.comparatorProperty()
                .bind(
                        view.getFacultyTable()
                                .comparatorProperty()
                );

        view.getFacultyTable()
                .setItems(
                        sortedData
                );
    }

    // =========================================================
    // LOAD FACULTIES
    // =========================================================

    private void loadFaculties() {

        List<Faculty> facultyList =
                service.getAllFaculties();

        facultyData.setAll(
                facultyList
        );

        updateStatistics();
    }

    // =========================================================
    // ADD FACULTY
    // =========================================================

    private void addFaculty() {

        String facultyName =
                view.getFacultyNameField()
                        .getText()
                        .trim();

        String description =
                view.getDescriptionArea()
                        .getText()
                        .trim();

        if (!service.isValidFacultyName(
                facultyName
        )) {

            showWarning(
                    "Invalid Faculty Name",
                    "Please enter a faculty name between 2 and 100 characters."
            );

            view.getSystemStatusValue()
                    .setText(
                            "Validation Error"
                    );

            view.getFacultyNameField()
                    .requestFocus();

            return;
        }

        if (service.facultyNameExists(
                facultyName,
                0
        )) {

            showWarning(
                    "Duplicate Faculty",
                    "A faculty with this name already exists."
            );

            view.getSystemStatusValue()
                    .setText(
                            "Duplicate Name"
                    );

            view.getFacultyNameField()
                    .requestFocus();

            return;
        }

        boolean added =
                service.addFaculty(
                        facultyName,
                        description
                );

        if (added) {

            showInformation(
                    "Faculty Added",
                    "The faculty was added successfully."
            );

            loadFaculties();
            clearForm();

            view.getSystemStatusValue()
                    .setText(
                            "Faculty Added"
                    );

        } else {

            showError(
                    "Add Failed",
                    "The faculty could not be added. Please check the database connection."
            );

            view.getDatabaseStatusValue()
                    .setText(
                            "Error"
                    );

            view.getSystemStatusValue()
                    .setText(
                            "Add Failed"
                    );
        }
    }

    // =========================================================
    // UPDATE FACULTY
    // =========================================================

    private void updateFaculty() {

        if (selectedFaculty == null) {

            showWarning(
                    "No Faculty Selected",
                    "Please select a faculty from the table before updating."
            );

            view.getSystemStatusValue()
                    .setText(
                            "No Selection"
                    );

            return;
        }

        String facultyName =
                view.getFacultyNameField()
                        .getText()
                        .trim();

        String description =
                view.getDescriptionArea()
                        .getText()
                        .trim();

        if (!service.isValidFacultyName(
                facultyName
        )) {

            showWarning(
                    "Invalid Faculty Name",
                    "Please enter a faculty name between 2 and 100 characters."
            );

            view.getSystemStatusValue()
                    .setText(
                            "Validation Error"
                    );

            view.getFacultyNameField()
                    .requestFocus();

            return;
        }

        if (service.facultyNameExists(
                facultyName,
                selectedFaculty.getFacultyId()
        )) {

            showWarning(
                    "Duplicate Faculty",
                    "Another faculty already uses this name."
            );

            view.getSystemStatusValue()
                    .setText(
                            "Duplicate Name"
                    );

            view.getFacultyNameField()
                    .requestFocus();

            return;
        }

        boolean updated =
                service.updateFaculty(
                        selectedFaculty.getFacultyId(),
                        facultyName,
                        description
                );

        if (updated) {

            showInformation(
                    "Faculty Updated",
                    "The faculty was updated successfully."
            );

            loadFaculties();
            clearForm();

            view.getSystemStatusValue()
                    .setText(
                            "Faculty Updated"
                    );

        } else {

            showError(
                    "Update Failed",
                    "The faculty could not be updated."
            );

            view.getSystemStatusValue()
                    .setText(
                            "Update Failed"
                    );
        }
    }

    // =========================================================
    // DELETE FACULTY
    // =========================================================

    private void deleteFaculty() {

        if (selectedFaculty == null) {

            showWarning(
                    "No Faculty Selected",
                    "Please select a faculty from the table before deleting."
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
                "Delete Faculty"
        );

        confirmation.setHeaderText(
                "Delete "
                        + selectedFaculty
                        .getFacultyName()
                        + "?"
        );

        confirmation.setContentText(
                "This action cannot be undone."
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
                service.deleteFaculty(
                        selectedFaculty
                                .getFacultyId()
                );

        if (deleted) {

            showInformation(
                    "Faculty Deleted",
                    "The faculty was deleted successfully."
            );

            loadFaculties();
            clearForm();

            view.getSystemStatusValue()
                    .setText(
                            "Faculty Deleted"
                    );

        } else {

            showError(
                    "Delete Failed",
                    "The faculty could not be deleted. It may be linked to departments or other records."
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

        view.getFacultyNameField()
                .clear();

        view.getDescriptionArea()
                .clear();

        view.getFacultyTable()
                .getSelectionModel()
                .clearSelection();

        selectedFaculty = null;

        view.getFacultyNameField()
                .requestFocus();

        updateButtonStates();
        updateStatistics();
    }

    // =========================================================
    // STATISTICS
    // =========================================================

    private void updateStatistics() {

        view.getTotalFacultiesValue()
                .setText(
                        String.valueOf(
                                facultyData.size()
                        )
                );

        view.getDatabaseStatusValue()
                .setText(
                        "Connected"
                );

        if (selectedFaculty == null) {

            view.getSelectedFacultyValue()
                    .setText(
                            "None"
                    );

        } else {

            view.getSelectedFacultyValue()
                    .setText(
                            selectedFaculty
                                    .getFacultyName()
                    );
        }

        if (view.getSystemStatusValue()
                .getText() == null
                || view.getSystemStatusValue()
                .getText()
                .trim()
                .isEmpty()) {

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
                selectedFaculty == null;

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