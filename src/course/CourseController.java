package course;

import dashboard.DashboardView;
import department.Department;
import lecturer.Lecturer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class CourseController {

    private final CourseView view;
    private final CourseService service;
    private final Stage stage;

    private Course selectedCourse;

    private final ObservableList<Course> courseData =
            FXCollections.observableArrayList();

    private final ObservableList<Department> departmentData =
            FXCollections.observableArrayList();

    private final ObservableList<Lecturer> lecturerData =
            FXCollections.observableArrayList();

    public CourseController(
            CourseView view,
            Stage stage
    ) {

        this.view = view;
        this.stage = stage;
        this.service = new CourseService();

        connectButtons();
        connectTableSelection();
        connectSearch();

        loadDepartments();
        loadLecturers();
        loadCourses();

        updateButtonStates();
        updateStatistics();
    }

    // =========================================================
    // CONNECT BUTTONS
    // =========================================================

    private void connectButtons() {

        view.getAddButton().setOnAction(
                event -> addCourse()
        );

        view.getUpdateButton().setOnAction(
                event -> updateCourse()
        );

        view.getDeleteButton().setOnAction(
                event -> deleteCourse()
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

        view.getLecturerComboBox()
                .setItems(
                        lecturerData
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

        updateStatistics();
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void connectSearch() {

        FilteredList<Course> filteredData =
                new FilteredList<>(
                        courseData,
                        course -> true
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
                                    course -> {

                                        if (newValue == null
                                                || newValue.trim().isEmpty()) {

                                            return true;
                                        }

                                        String searchText =
                                                newValue
                                                        .trim()
                                                        .toLowerCase();

                                        String courseId =
                                                String.valueOf(
                                                        course.getCourseId()
                                                );

                                        String courseCode =
                                                course.getCourseCode() == null
                                                        ? ""
                                                        : course.getCourseCode()
                                                        .toLowerCase();

                                        String courseName =
                                                course.getCourseName() == null
                                                        ? ""
                                                        : course.getCourseName()
                                                        .toLowerCase();

                                        String credits =
                                                String.valueOf(
                                                        course.getCredits()
                                                );

                                        String departmentName =
                                                course.getDepartmentName() == null
                                                        ? ""
                                                        : course.getDepartmentName()
                                                        .toLowerCase();

                                        String lecturerName =
                                                course.getLecturerName() == null
                                                        ? ""
                                                        : course.getLecturerName()
                                                        .toLowerCase();

                                        return courseId.contains(
                                                searchText
                                        )
                                                || courseCode.contains(
                                                searchText
                                        )
                                                || courseName.contains(
                                                searchText
                                        )
                                                || credits.contains(
                                                searchText
                                        )
                                                || departmentName.contains(
                                                searchText
                                        )
                                                || lecturerName.contains(
                                                searchText
                                        );
                                    }
                            );
                        }
                );

        SortedList<Course> sortedData =
                new SortedList<>(
                        filteredData
                );

        sortedData.comparatorProperty()
                .bind(
                        view.getCourseTable()
                                .comparatorProperty()
                );

        view.getCourseTable()
                .setItems(
                        sortedData
                );
    }     // =========================================================
    // TABLE SELECTION
    // =========================================================

    private void connectTableSelection() {

        view.getCourseTable()
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldCourse, newCourse) -> {

                    selectedCourse = newCourse;

                    if (newCourse != null) {

                        populateForm(newCourse);

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

    private void populateForm(Course course) {

        view.getCourseCodeField()
                .setText(course.getCourseCode());

        view.getCourseNameField()
                .setText(course.getCourseName());

        view.getCreditsComboBox()
                .getSelectionModel()
                .select(Integer.valueOf(course.getCredits()));

        selectDepartment(course.getDepartmentId());

        if (course.getLecturerId() == null) {

            view.getLecturerComboBox()
                    .getSelectionModel()
                    .clearSelection();

        } else {

            selectLecturer(course.getLecturerId());

        }
    }

    // =========================================================
    // SELECT DEPARTMENT
    // =========================================================

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

    // =========================================================
    // SELECT LECTURER
    // =========================================================

    private void selectLecturer(int lecturerId) {

        for (Lecturer lecturer : lecturerData) {

            if (lecturer.getLecturerId() == lecturerId) {

                view.getLecturerComboBox()
                        .getSelectionModel()
                        .select(lecturer);

                break;
            }
        }
    }

    // =========================================================
    // CREATE COURSE OBJECT
    // =========================================================

    private Course createCourseFromForm() {

        Department department =
                view.getDepartmentComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        Integer credits =
                view.getCreditsComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        if (department == null || credits == null) {

            return null;

        }

        Lecturer lecturer =
                view.getLecturerComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        Course course =
                new Course();

        if (selectedCourse != null) {

            course.setCourseId(
                    selectedCourse.getCourseId()
            );

        }

        course.setCourseCode(
                view.getCourseCodeField()
                        .getText()
                        .trim()
        );

        course.setCourseName(
                view.getCourseNameField()
                        .getText()
                        .trim()
        );

        course.setCredits(
                credits
        );

        course.setDepartmentId(
                department.getDepartmentId()
        );

        course.setDepartmentName(
                department.getDepartmentName()
        );

        if (lecturer != null) {

            course.setLecturerId(
                    lecturer.getLecturerId()
            );

            course.setLecturerName(
                    lecturer.getFullName()
            );

        } else {

            course.setLecturerId(null);
            course.setLecturerName("Not Assigned");

        }

        return course;
    }

    // =========================================================
    // ADD COURSE
    // =========================================================

    private void addCourse() {

        Course course =
                createCourseFromForm();

        if (course == null) {

            showWarning(
                    "Missing Information",
                    "Please select Credits and Department."
            );

            return;
        }

        boolean success =
                service.addCourse(course);

        if (success) {

            showInformation(
                    "Success",
                    "Course added successfully."
            );

            loadCourses();

            clearForm();

        } else {

            showError(
                    "Add Failed",
                    "Unable to add course.\n\nCourse code may already exist."
            );

        }
    }     // =========================================================
    // UPDATE COURSE
    // =========================================================

    private void updateCourse() {

        if (selectedCourse == null) {

            showWarning(
                    "Selection Required",
                    "Please select a course to update."
            );

            return;
        }

        Course course =
                createCourseFromForm();

        if (course == null) {

            showWarning(
                    "Missing Information",
                    "Please select Credits and Department."
            );

            return;
        }

        boolean success =
                service.updateCourse(course);

        if (success) {

            showInformation(
                    "Success",
                    "Course updated successfully."
            );

            loadCourses();

            clearForm();

        } else {

            showError(
                    "Update Failed",
                    "Unable to update course.\n\nCourse code may already exist."
            );

        }

    }

    // =========================================================
    // DELETE COURSE
    // =========================================================

    private void deleteCourse() {

        if (selectedCourse == null) {

            showWarning(
                    "Selection Required",
                    "Please select a course."
            );

            return;
        }

        Alert confirmation =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmation.setTitle(
                "Delete Course"
        );

        confirmation.setHeaderText(
                "Delete Course"
        );

        confirmation.setContentText(
                "Delete course:\n\n"
                        + selectedCourse.getCourseCode()
                        + " - "
                        + selectedCourse.getCourseName()
                        + " ?"
        );

        Optional<ButtonType> result =
                confirmation.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            boolean success =
                    service.deleteCourse(
                            selectedCourse.getCourseId()
                    );

            if (success) {

                showInformation(
                        "Deleted",
                        "Course deleted successfully."
                );

                loadCourses();

                clearForm();

            } else {

                showError(
                        "Delete Failed",
                        "Unable to delete course."
                );

            }

        }

    }

    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        selectedCourse = null;

        clearFormFieldsOnly();

        view.getCourseTable()
                .getSelectionModel()
                .clearSelection();

        updateButtonStates();

        updateStatistics();

    }

    // =========================================================
    // CLEAR FORM FIELDS
    // =========================================================

    private void clearFormFieldsOnly() {

        view.getCourseCodeField().clear();

        view.getCourseNameField().clear();

        view.getCreditsComboBox()
                .getSelectionModel()
                .clearSelection();

        view.getDepartmentComboBox()
                .getSelectionModel()
                .clearSelection();

        view.getLecturerComboBox()
                .getSelectionModel()
                .clearSelection();

    }     // =========================================================
    // STATISTICS
    // =========================================================

    private void updateStatistics() {

        view.getTotalCoursesValue()
                .setText(
                        String.valueOf(
                                courseData.size()
                        )
                );

        view.getDatabaseStatusValue()
                .setText(
                        "Connected"
                );

        if (selectedCourse == null) {

            view.getSelectedCourseValue()
                    .setText(
                            "None"
                    );

        } else {

            view.getSelectedCourseValue()
                    .setText(
                            selectedCourse.getCourseCode()
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
                selectedCourse == null;

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