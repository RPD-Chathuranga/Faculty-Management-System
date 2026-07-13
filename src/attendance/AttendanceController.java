package attendance;

import course.Course;
import dashboard.DashboardView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import student.Student;
import authentication.UserSession;

import java.util.List;

public class AttendanceController {

    private final AttendanceView view;
    private final AttendanceService service;
    private final Stage stage;

    private Attendance selectedAttendance;

    private final ObservableList<Attendance> attendanceData =
            FXCollections.observableArrayList();

    private final ObservableList<Student> studentData =
            FXCollections.observableArrayList();

    private final ObservableList<Course> courseData =
            FXCollections.observableArrayList();

    public AttendanceController(
            AttendanceView view,
            Stage stage
    ) {

        this.view = view;
        this.stage = stage;
        this.service = new AttendanceService();

        connectButtons();
        connectTableSelection();
        connectSearch();

        loadStudents();
        loadCourses();
        loadAttendance();

        updateButtonStates();
        updateStatistics();
        applyRolePermissions();
    }

    // =========================================================
    // BUTTON EVENTS
    // =========================================================

    private void connectButtons() {

        view.getAddButton().setOnAction(
                event -> addAttendance()
        );

        view.getUpdateButton().setOnAction(
                event -> updateAttendance()
        );

        view.getDeleteButton().setOnAction(
                event -> deleteAttendance()
        );

        view.getClearButton().setOnAction(
                event -> clearForm()
        );

        view.getBackButton().setOnAction(
                event -> returnToDashboard()
        );
    }

    // =========================================================
    // LOAD STUDENTS
    // =========================================================

    private void loadStudents() {

        List<Student> students =
                service.getAllStudents();

        studentData.setAll(
                students
        );

        view.getStudentComboBox()
                .setItems(
                        studentData
                );
    }

    // =========================================================
    // LOAD COURSES
    // =========================================================

    private void loadCourses() {

        List<Course> courses =
                service.getAllCourses();

        courseData.setAll(
                courses
        );

        view.getCourseComboBox()
                .setItems(
                        courseData
                );
    }

    // =========================================================
    // LOAD ATTENDANCE
    // =========================================================

    private void loadAttendance() {

        List<Attendance> list =
                service.getAllAttendance();

        attendanceData.setAll(
                list
        );

        updateStatistics();
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void connectSearch() {

        FilteredList<Attendance> filtered =
                new FilteredList<>(
                        attendanceData,
                        attendance -> true
                );

        view.getSearchField()
                .textProperty()
                .addListener((obs, oldValue, newValue) -> {

                    filtered.setPredicate(attendance -> {

                        if (newValue == null
                                || newValue.trim().isEmpty()) {

                            return true;

                        }

                        String search =
                                newValue.toLowerCase();

                        return String.valueOf(
                                attendance.getAttendanceId()
                        ).contains(search)

                                ||

                                attendance.getStudentDisplayName()
                                        .toLowerCase()
                                        .contains(search)

                                ||

                                attendance.getCourseDisplayName()
                                        .toLowerCase()
                                        .contains(search)

                                ||

                                attendance.getStatus()
                                        .toLowerCase()
                                        .contains(search)

                                ||

                                attendance.getDate()
                                        .toString()
                                        .contains(search);

                    });

                });

        SortedList<Attendance> sorted =
                new SortedList<>(filtered);

        sorted.comparatorProperty().bind(
                view.getAttendanceTable()
                        .comparatorProperty()
        );

        view.getAttendanceTable()
                .setItems(sorted);
    }     // =========================================================
    // TABLE SELECTION
    // =========================================================

    private void connectTableSelection() {

        view.getAttendanceTable()
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldAttendance, newAttendance) -> {

                    selectedAttendance = newAttendance;

                    if (newAttendance != null) {

                        populateForm(newAttendance);

                    } else {

                        clearFormFieldsOnly();

                    }

                    updateButtonStates();
                    updateStatistics();

                });

    }

    // =========================================================
    // POPULATE FORM
    // =========================================================

    private void populateForm(
            Attendance attendance
    ) {

        selectStudent(
                attendance.getStudentId()
        );

        selectCourse(
                attendance.getCourseId()
        );

        view.getDatePicker()
                .setValue(
                        attendance.getDate()
                );

        view.getStatusComboBox()
                .getSelectionModel()
                .select(
                        attendance.getStatus()
                );

    }

    // =========================================================
    // SELECT STUDENT
    // =========================================================

    private void selectStudent(
            int studentId
    ) {

        for (Student student : studentData) {

            if (student.getStudentId() == studentId) {

                view.getStudentComboBox()
                        .getSelectionModel()
                        .select(student);

                break;

            }

        }

    }

    // =========================================================
    // SELECT COURSE
    // =========================================================

    private void selectCourse(
            int courseId
    ) {

        for (Course course : courseData) {

            if (course.getCourseId() == courseId) {

                view.getCourseComboBox()
                        .getSelectionModel()
                        .select(course);

                break;

            }

        }

    }

    // =========================================================
    // CREATE ATTENDANCE OBJECT
    // =========================================================

    private Attendance createAttendanceFromForm() {

        Student student =
                view.getStudentComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        Course course =
                view.getCourseComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        String status =
                view.getStatusComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        if (student == null
                || course == null
                || status == null
                || view.getDatePicker().getValue() == null) {

            return null;

        }

        Attendance attendance =
                new Attendance();

        if (selectedAttendance != null) {

            attendance.setAttendanceId(
                    selectedAttendance.getAttendanceId()
            );

        }

        attendance.setStudentId(
                student.getStudentId()
        );

        attendance.setCourseId(
                course.getCourseId()
        );

        attendance.setDate(
                view.getDatePicker()
                        .getValue()
        );

        attendance.setStatus(
                status
        );

        return attendance;

    }

    // =========================================================
    // ADD ATTENDANCE
    // =========================================================

    private void addAttendance() {

        Attendance attendance =
                createAttendanceFromForm();

        if (attendance == null) {

            showWarning(
                    "Missing Information",
                    "Please complete all fields."
            );

            return;

        }

        boolean success =
                service.addAttendance(
                        attendance
                );

        if (success) {

            showInformation(
                    "Success",
                    "Attendance added successfully."
            );

            loadAttendance();

            clearForm();

        } else {

            showError(
                    "Add Failed",
                    "Attendance already exists for this student, course and date."
            );

        }

    }
    private void applyRolePermissions() {

        String role = UserSession.role;

        if ("STUDENT".equals(role)) {

            view.getAddButton().setDisable(true);
            view.getUpdateButton().setDisable(true);
            view.getDeleteButton().setDisable(true);

            view.getStudentComboBox().setDisable(true);
            view.getCourseComboBox().setDisable(true);
            view.getDatePicker().setDisable(true);
            view.getStatusComboBox().setDisable(true);
        }
    }

    // =========================================================
    // UPDATE ATTENDANCE
    // =========================================================

    private void updateAttendance() {

        if (selectedAttendance == null) {

            showWarning(
                    "Selection Required",
                    "Please select an attendance record to update."
            );

            return;
        }

        Attendance attendance =
                createAttendanceFromForm();

        if (attendance == null) {

            showWarning(
                    "Missing Information",
                    "Please complete all fields."
            );

            return;
        }

        boolean success =
                service.updateAttendance(
                        attendance
                );

        if (success) {

            showInformation(
                    "Success",
                    "Attendance updated successfully."
            );

            loadAttendance();

            clearForm();

        } else {

            showError(
                    "Update Failed",
                    "Attendance already exists for this student, course and date."
            );

        }

    }

    // =========================================================
    // DELETE ATTENDANCE
    // =========================================================

    private void deleteAttendance() {

        if (selectedAttendance == null) {

            showWarning(
                    "Selection Required",
                    "Please select an attendance record."
            );

            return;

        }

        Alert confirmation =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmation.setTitle(
                "Delete Attendance"
        );

        confirmation.setHeaderText(
                "Delete Attendance"
        );

        confirmation.setContentText(
                "Delete attendance record for\n\n"
                        + selectedAttendance.getStudentDisplayName()
                        + "\n"
                        + selectedAttendance.getCourseDisplayName()
                        + "\n"
                        + selectedAttendance.getDate()
                        + " ?"
        );

        java.util.Optional<javafx.scene.control.ButtonType> result =
                confirmation.showAndWait();

        if (result.isPresent()
                && result.get() == javafx.scene.control.ButtonType.OK) {

            boolean success =
                    service.deleteAttendance(
                            selectedAttendance.getAttendanceId()
                    );

            if (success) {

                showInformation(
                        "Deleted",
                        "Attendance deleted successfully."
                );

                loadAttendance();

                clearForm();

            } else {

                showError(
                        "Delete Failed",
                        "Unable to delete attendance."
                );

            }

        }

    }

    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        selectedAttendance = null;

        clearFormFieldsOnly();

        view.getAttendanceTable()
                .getSelectionModel()
                .clearSelection();

        updateButtonStates();

        updateStatistics();

    }

    // =========================================================
    // CLEAR FORM FIELDS
    // =========================================================

    private void clearFormFieldsOnly() {

        view.getStudentComboBox()
                .getSelectionModel()
                .clearSelection();

        view.getCourseComboBox()
                .getSelectionModel()
                .clearSelection();

        view.getStatusComboBox()
                .getSelectionModel()
                .clearSelection();

        view.getDatePicker()
                .setValue(null);

    }     // =========================================================
    // STATISTICS
    // =========================================================

    private void updateStatistics() {

        view.getTotalAttendanceValue()
                .setText(
                        String.valueOf(
                                attendanceData.size()
                        )
                );

        view.getDatabaseStatusValue()
                .setText(
                        "Connected"
                );

        if (selectedAttendance == null) {

            view.getSelectedAttendanceValue()
                    .setText(
                            "None"
                    );

        } else {

            view.getSelectedAttendanceValue()
                    .setText(
                            selectedAttendance.getStudentDisplayName()
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
                selectedAttendance == null;

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