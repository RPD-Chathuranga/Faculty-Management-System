package grade;

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

public class GradeController {

    private final GradeView view;
    private final GradeService service;
    private final Stage stage;

    private Grade selectedGrade;

    private final ObservableList<Grade> gradeData =
            FXCollections.observableArrayList();

    private final ObservableList<Student> studentData =
            FXCollections.observableArrayList();

    private final ObservableList<Course> courseData =
            FXCollections.observableArrayList();

    public GradeController(
            GradeView view,
            Stage stage
    ) {

        this.view = view;
        this.stage = stage;
        this.service = new GradeService();

        connectButtons();
        connectTableSelection();
        connectSearch();

        loadStudents();
        loadCourses();
        loadGrades();

        updateButtonStates();
        updateStatistics();
        applyRolePermissions();
    }

    // =========================================================
    // BUTTON EVENTS
    // =========================================================

    private void connectButtons() {

        view.getCalculateButton().setOnAction(
                event -> calculateGrade()
        );

        view.getAddButton().setOnAction(
                event -> addGrade()
        );

        view.getUpdateButton().setOnAction(
                event -> updateGrade()
        );

        view.getDeleteButton().setOnAction(
                event -> deleteGrade()
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

        studentData.setAll(students);

        view.getStudentComboBox()
                .setItems(studentData);
    }

    // =========================================================
    // LOAD COURSES
    // =========================================================

    private void loadCourses() {

        List<Course> courses =
                service.getAllCourses();

        courseData.setAll(courses);

        view.getCourseComboBox()
                .setItems(courseData);
    }

    // =========================================================
    // LOAD GRADES
    // =========================================================

    private void loadGrades() {

        List<Grade> grades =
                service.getAllGrades();

        gradeData.setAll(grades);

        updateStatistics();
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void connectSearch() {

        FilteredList<Grade> filtered =
                new FilteredList<>(
                        gradeData,
                        grade -> true
                );

        view.getSearchField()
                .textProperty()
                .addListener((obs, oldValue, newValue) -> {

                    filtered.setPredicate(grade -> {

                        if (newValue == null
                                || newValue.trim().isEmpty()) {

                            return true;

                        }

                        String search =
                                newValue.toLowerCase();

                        return String.valueOf(
                                grade.getGradeId()
                        ).contains(search)

                                ||

                                grade.getStudentDisplayName()
                                        .toLowerCase()
                                        .contains(search)

                                ||

                                grade.getCourseDisplayName()
                                        .toLowerCase()
                                        .contains(search)

                                ||

                                grade.getLetterGrade()
                                        .toLowerCase()
                                        .contains(search)

                                ||

                                String.valueOf(
                                        grade.getMarks()
                                ).contains(search);

                    });

                });

        SortedList<Grade> sorted =
                new SortedList<>(filtered);

        sorted.comparatorProperty().bind(
                view.getGradeTable()
                        .comparatorProperty()
        );

        view.getGradeTable()
                .setItems(sorted);
    }     // =========================================================
    // TABLE SELECTION
    // =========================================================

    private void connectTableSelection() {

        view.getGradeTable()
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldGrade, newGrade) -> {

                    selectedGrade = newGrade;

                    if (newGrade != null) {

                        populateForm(newGrade);

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
            Grade grade
    ) {

        selectStudent(
                grade.getStudentId()
        );

        selectCourse(
                grade.getCourseId()
        );

        view.getMarksField()
                .setText(
                        String.valueOf(
                                grade.getMarks()
                        )
                );

        view.getLetterGradeField()
                .setText(
                        grade.getLetterGrade()
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
    // CALCULATE LETTER GRADE
    // =========================================================

    private void calculateGrade() {

        try {

            double marks =
                    Double.parseDouble(
                            view.getMarksField()
                                    .getText()
                                    .trim()
                    );

            String letterGrade =
                    service.calculateLetterGrade(
                            marks
                    );

            view.getLetterGradeField()
                    .setText(
                            letterGrade
                    );

        } catch (NumberFormatException ex) {

            showWarning(
                    "Invalid Marks",
                    "Please enter a valid numeric mark between 0 and 100."
            );

        }

    }

    // =========================================================
    // CREATE GRADE OBJECT
    // =========================================================

    private Grade createGradeFromForm() {

        Student student =
                view.getStudentComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        Course course =
                view.getCourseComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        if (student == null
                || course == null) {

            return null;
        }

        try {

            double marks =
                    Double.parseDouble(
                            view.getMarksField()
                                    .getText()
                                    .trim()
                    );

            Grade grade =
                    new Grade();

            if (selectedGrade != null) {

                grade.setGradeId(
                        selectedGrade.getGradeId()
                );

            }

            grade.setStudentId(
                    student.getStudentId()
            );

            grade.setCourseId(
                    course.getCourseId()
            );

            grade.setMarks(
                    marks
            );

            grade.setLetterGrade(
                    service.calculateLetterGrade(
                            marks
                    )
            );

            return grade;

        } catch (Exception ex) {

            return null;

        }

    }

    // =========================================================
    // ADD GRADE
    // =========================================================

    private void addGrade() {

        Grade grade =
                createGradeFromForm();

        if (grade == null) {

            showWarning(
                    "Missing Information",
                    "Please complete all fields correctly."
            );

            return;
        }

        boolean success =
                service.addGrade(
                        grade
                );

        if (success) {

            showInformation(
                    "Success",
                    "Grade added successfully."
            );

            loadGrades();

            clearForm();

        } else {

            showError(
                    "Add Failed",
                    "A grade already exists for this student and course."
            );

        }

    }     // =========================================================
    // UPDATE GRADE
    // =========================================================

    private void updateGrade() {

        if (selectedGrade == null) {

            showWarning(
                    "Selection Required",
                    "Please select a grade record to update."
            );

            return;
        }

        Grade grade =
                createGradeFromForm();

        if (grade == null) {

            showWarning(
                    "Missing Information",
                    "Please complete all fields correctly."
            );

            return;
        }

        boolean success =
                service.updateGrade(
                        grade
                );

        if (success) {

            showInformation(
                    "Success",
                    "Grade updated successfully."
            );

            loadGrades();

            clearForm();

        } else {

            showError(
                    "Update Failed",
                    "A grade already exists for this student and course."
            );

        }

    }

    // =========================================================
    // DELETE GRADE
    // =========================================================

    private void deleteGrade() {

        if (selectedGrade == null) {

            showWarning(
                    "Selection Required",
                    "Please select a grade record."
            );

            return;

        }

        Alert confirmation =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmation.setTitle(
                "Delete Grade"
        );

        confirmation.setHeaderText(
                "Delete Grade"
        );

        confirmation.setContentText(
                "Delete grade for\n\n"
                        + selectedGrade.getStudentDisplayName()
                        + "\n"
                        + selectedGrade.getCourseDisplayName()
                        + "\nMarks : "
                        + selectedGrade.getMarks()
                        + " ?"
        );

        java.util.Optional<javafx.scene.control.ButtonType> result =
                confirmation.showAndWait();

        if (result.isPresent()
                && result.get() == javafx.scene.control.ButtonType.OK) {

            boolean success =
                    service.deleteGrade(
                            selectedGrade.getGradeId()
                    );

            if (success) {

                showInformation(
                        "Deleted",
                        "Grade deleted successfully."
                );

                loadGrades();

                clearForm();

            } else {

                showError(
                        "Delete Failed",
                        "Unable to delete grade."
                );

            }

        }

    }

    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        selectedGrade = null;

        clearFormFieldsOnly();

        view.getGradeTable()
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

        view.getMarksField().clear();

        view.getLetterGradeField().clear();

    }     // =========================================================
    // STATISTICS
    // =========================================================

    private void updateStatistics() {

        view.getTotalGradesValue()
                .setText(
                        String.valueOf(
                                gradeData.size()
                        )
                );

        view.getDatabaseStatusValue()
                .setText(
                        "Connected"
                );

        if (selectedGrade == null) {

            view.getSelectedGradeValue()
                    .setText(
                            "None"
                    );

        } else {

            view.getSelectedGradeValue()
                    .setText(
                            selectedGrade.getStudentDisplayName()
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
                selectedGrade == null;

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

    private void applyRolePermissions() {

        String role = UserSession.role;

        if ("STUDENT".equals(role)) {

            view.getAddButton().setDisable(true);
            view.getUpdateButton().setDisable(true);
            view.getDeleteButton().setDisable(true);

            view.getCalculateButton().setDisable(true);

            view.getMarksField().setEditable(false);

            view.getStudentComboBox().setDisable(true);
            view.getCourseComboBox().setDisable(true);

        }

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