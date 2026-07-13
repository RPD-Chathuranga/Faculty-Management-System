package student;

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

public class StudentController {

    private final StudentView view;
    private final StudentService service;
    private final Stage stage;

    private Student selectedStudent;

    private final ObservableList<Student> studentData =
            FXCollections.observableArrayList();

    private final ObservableList<Department> departmentData =
            FXCollections.observableArrayList();

    public StudentController(
            StudentView view,
            Stage stage
    ) {

        this.view = view;
        this.stage = stage;
        this.service = new StudentService();

        connectButtons();
        connectTableSelection();
        connectSearch();

        loadDepartments();
        loadStudents();

        updateButtonStates();
        updateStatistics();
    }

    // =====================================================
    // BUTTONS
    // =====================================================

    private void connectButtons() {

        view.getAddButton().setOnAction(
                e -> addStudent()
        );

        view.getUpdateButton().setOnAction(
                e -> updateStudent()
        );

        view.getDeleteButton().setOnAction(
                e -> deleteStudent()
        );

        view.getClearButton().setOnAction(
                e -> clearForm()
        );

        view.getBackButton().setOnAction(
                e -> returnToDashboard()
        );

    }

    // =====================================================
    // LOAD DEPARTMENTS
    // =====================================================

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

    // =====================================================
    // LOAD STUDENTS
    // =====================================================

    private void loadStudents() {

        List<Student> students =
                service.getAllStudents();

        studentData.setAll(
                students
        );

        updateStatistics();

    }

    // =====================================================
    // SEARCH
    // =====================================================

    private void connectSearch() {

        FilteredList<Student> filteredData =
                new FilteredList<>(
                        studentData,
                        student -> true
                );

        view.getSearchField()
                .textProperty()
                .addListener((obs, oldValue, newValue) -> {

                    filteredData.setPredicate(student -> {

                        if (newValue == null ||
                                newValue.trim().isEmpty()) {

                            return true;

                        }

                        String search =
                                newValue.toLowerCase();

                        return String.valueOf(student.getStudentId()).contains(search)

                                || student.getRegistrationNumber()
                                .toLowerCase()
                                .contains(search)

                                || student.getFullName()
                                .toLowerCase()
                                .contains(search)

                                || student.getUsername()
                                .toLowerCase()
                                .contains(search)

                                || student.getEmail()
                                .toLowerCase()
                                .contains(search)

                                || student.getDepartmentName()
                                .toLowerCase()
                                .contains(search);

                    });

                });

        SortedList<Student> sortedData =
                new SortedList<>(
                        filteredData
                );

        sortedData.comparatorProperty().bind(
                view.getStudentTable()
                        .comparatorProperty()
        );

        view.getStudentTable().setItems(
                sortedData
        );

    }     // =====================================================
    // TABLE SELECTION
    // =====================================================

    private void connectTableSelection() {

        view.getStudentTable()
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldStudent, newStudent) -> {

                    selectedStudent = newStudent;

                    if (newStudent != null) {

                        populateForm(newStudent);

                    } else {

                        clearFormFieldsOnly();

                    }

                    updateButtonStates();
                    updateStatistics();

                });

    }

    // =====================================================
    // POPULATE FORM
    // =====================================================

    private void populateForm(Student student) {

        view.getFullNameField().setText(
                student.getFullName()
        );

        view.getUsernameField().setText(
                student.getUsername()
        );

        view.getPasswordField().clear();

        view.getEmailField().setText(
                student.getEmail()
        );

        view.getPhoneField().setText(
                student.getPhone()
        );

        view.getRegistrationNumberField().setText(
                student.getRegistrationNumber()
        );

        view.getSemesterComboBox()
                .getSelectionModel()
                .select(
                        Integer.valueOf(student.getSemester())
                );

        selectDepartment(
                student.getDepartmentId()
        );

    }

    // =====================================================
    // SELECT DEPARTMENT
    // =====================================================

    private void selectDepartment(int departmentId) {

        for (Department department : departmentData) {

            if (department.getDepartmentId() == departmentId) {

                view.getDepartmentComboBox()
                        .getSelectionModel()
                        .select(department);

                break;

            }

        }

    }

    // =====================================================
    // CREATE STUDENT OBJECT
    // =====================================================

    private Student createStudentFromForm() {

        Department department =
                view.getDepartmentComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        Integer semester =
                view.getSemesterComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        if (department == null || semester == null) {

            return null;

        }

        Student student =
                new Student();

        if (selectedStudent != null) {

            student.setStudentId(
                    selectedStudent.getStudentId()
            );

            student.setUserId(
                    selectedStudent.getUserId()
            );

        }

        student.setFullName(
                view.getFullNameField()
                        .getText()
                        .trim()
        );

        student.setUsername(
                view.getUsernameField()
                        .getText()
                        .trim()
        );

        student.setPassword(
                view.getPasswordField()
                        .getText()
                        .trim()
        );

        student.setEmail(
                view.getEmailField()
                        .getText()
                        .trim()
        );

        student.setPhone(
                view.getPhoneField()
                        .getText()
                        .trim()
        );

        student.setRegistrationNumber(
                view.getRegistrationNumberField()
                        .getText()
                        .trim()
        );

        student.setSemester(
                semester
        );

        student.setDepartmentId(
                department.getDepartmentId()
        );

        student.setDepartmentName(
                department.getDepartmentName()
        );

        return student;

    }

    // =====================================================
    // ADD STUDENT
    // =====================================================

    private void addStudent() {

        Student student =
                createStudentFromForm();

        if (student == null) {

            showWarning(
                    "Missing Information",
                    "Please select both Department and Semester."
            );

            return;

        }

        boolean success =
                service.addStudent(student);

        if (success) {

            showInformation(
                    "Success",
                    "Student account created successfully."
            );

            loadStudents();

            clearForm();

        } else {

            showError(
                    "Add Failed",
                    "Unable to create student.\n\n" +
                            "Please ensure the Username,\n" +
                            "Email and Registration Number\n" +
                            "are unique."
            );

        }

    }     // =====================================================
    // UPDATE STUDENT
    // =====================================================

    private void updateStudent() {

        if (selectedStudent == null) {

            showWarning(
                    "Selection Required",
                    "Please select a student to update."
            );

            return;
        }

        Student student =
                createStudentFromForm();

        if (student == null) {

            showWarning(
                    "Missing Information",
                    "Please select both Department and Semester."
            );

            return;
        }

        boolean success =
                service.updateStudent(
                        student
                );

        if (success) {

            showInformation(
                    "Success",
                    "Student updated successfully."
            );

            loadStudents();

            clearForm();

        } else {

            showError(
                    "Update Failed",
                    "Unable to update student.\n\n" +
                            "Username, Email or Registration Number may already exist."
            );

        }

    }

    // =====================================================
    // DELETE STUDENT
    // =====================================================

    private void deleteStudent() {

        if (selectedStudent == null) {

            showWarning(
                    "Selection Required",
                    "Please select a student."
            );

            return;
        }

        Alert confirmation =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmation.setTitle(
                "Delete Student"
        );

        confirmation.setHeaderText(
                "Delete Student"
        );

        confirmation.setContentText(
                "Delete "
                        + selectedStudent.getFullName()
                        + "?\n\nThis will also remove the student's login account."
        );

        Optional<ButtonType> result =
                confirmation.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            boolean success =
                    service.deleteStudent(
                            selectedStudent.getUserId()
                    );

            if (success) {

                showInformation(
                        "Deleted",
                        "Student deleted successfully."
                );

                loadStudents();

                clearForm();

            } else {

                showError(
                        "Delete Failed",
                        "Unable to delete student."
                );

            }

        }

    }

    // =====================================================
    // CLEAR FORM
    // =====================================================

    private void clearForm() {

        selectedStudent = null;

        clearFormFieldsOnly();

        view.getStudentTable()
                .getSelectionModel()
                .clearSelection();

        updateButtonStates();

        updateStatistics();

    }

    // =====================================================
    // CLEAR FORM FIELDS
    // =====================================================

    private void clearFormFieldsOnly() {

        view.getFullNameField().clear();

        view.getUsernameField().clear();

        view.getPasswordField().clear();

        view.getEmailField().clear();

        view.getPhoneField().clear();

        view.getRegistrationNumberField().clear();

        view.getSemesterComboBox()
                .getSelectionModel()
                .clearSelection();

        view.getDepartmentComboBox()
                .getSelectionModel()
                .clearSelection();

    }     // =====================================================
    // STATISTICS
    // =====================================================

    private void updateStatistics() {

        view.getTotalStudentsValue()
                .setText(
                        String.valueOf(
                                studentData.size()
                        )
                );

        view.getDatabaseStatusValue()
                .setText(
                        "Connected"
                );

        if (selectedStudent == null) {

            view.getSelectedStudentValue()
                    .setText(
                            "None"
                    );

        } else {

            view.getSelectedStudentValue()
                    .setText(
                            selectedStudent.getFullName()
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

    // =====================================================
    // BUTTON STATES
    // =====================================================

    private void updateButtonStates() {

        boolean noSelection =
                selectedStudent == null;

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

    // =====================================================
    // RETURN TO DASHBOARD
    // =====================================================

    private void returnToDashboard() {

        DashboardView dashboardView =
                new DashboardView(stage);

        dashboardView.show();
    }

    // =====================================================
    // ALERT METHODS
    // =====================================================

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