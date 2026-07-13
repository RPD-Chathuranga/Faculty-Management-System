package timetable;

import course.Course;
import dashboard.DashboardView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lecturer.Lecturer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class TimetableController {

    private final TimetableView view;
    private final TimetableService service;
    private final Stage stage;

    private Timetable selectedTimetable;

    private final ObservableList<Timetable> timetableData =
            FXCollections.observableArrayList();

    private final ObservableList<Course> courseData =
            FXCollections.observableArrayList();

    private final ObservableList<Lecturer> lecturerData =
            FXCollections.observableArrayList();

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("HH:mm");

    public TimetableController(
            TimetableView view,
            Stage stage
    ) {

        this.view = view;
        this.stage = stage;
        this.service = new TimetableService();

        connectButtons();
        connectTableSelection();
        connectSearch();

        loadCourses();
        loadLecturers();
        loadTimetables();

        updateButtonStates();
        updateStatistics();
    }

    // =====================================================
    // BUTTON EVENTS
    // =====================================================

    private void connectButtons() {

        view.getAddButton().setOnAction(
                event -> addTimetable()
        );

        view.getUpdateButton().setOnAction(
                event -> updateTimetable()
        );

        view.getDeleteButton().setOnAction(
                event -> deleteTimetable()
        );

        view.getClearButton().setOnAction(
                event -> clearForm()
        );

        view.getBackButton().setOnAction(
                event -> returnToDashboard()
        );

    }

    // =====================================================
    // LOAD COURSES
    // =====================================================

    private void loadCourses() {

        List<Course> list =
                service.getAllCourses();

        courseData.setAll(list);

        view.getCourseComboBox()
                .setItems(courseData);

    }

    // =====================================================
    // LOAD LECTURERS
    // =====================================================

    private void loadLecturers() {

        List<Lecturer> list =
                service.getAllLecturers();

        lecturerData.setAll(list);

        view.getLecturerComboBox()
                .setItems(lecturerData);

    }

    // =====================================================
    // LOAD TIMETABLES
    // =====================================================

    private void loadTimetables() {

        List<Timetable> list =
                service.getAllTimetables();

        timetableData.setAll(list);

        updateStatistics();

    }

    // =====================================================
    // SEARCH
    // =====================================================

    private void connectSearch() {

        FilteredList<Timetable> filtered =
                new FilteredList<>(
                        timetableData,
                        t -> true
                );

        view.getSearchField()
                .textProperty()
                .addListener((obs, oldValue, newValue) -> {

                    filtered.setPredicate(timetable -> {

                        if (newValue == null
                                || newValue.isBlank()) {

                            return true;

                        }

                        String search =
                                newValue.toLowerCase();

                        return String.valueOf(
                                timetable.getTimetableId()
                        ).contains(search)

                                ||

                                timetable.getCourseDisplayName()
                                        .toLowerCase()
                                        .contains(search)

                                ||

                                timetable.getLecturerName()
                                        .toLowerCase()
                                        .contains(search)

                                ||

                                timetable.getDayOfWeek()
                                        .toLowerCase()
                                        .contains(search)

                                ||

                                timetable.getRoom()
                                        .toLowerCase()
                                        .contains(search);

                    });

                });

        SortedList<Timetable> sorted =
                new SortedList<>(filtered);

        sorted.comparatorProperty().bind(
                view.getTimetableTable()
                        .comparatorProperty()
        );

        view.getTimetableTable()
                .setItems(sorted);

    }     // =====================================================
    // TABLE SELECTION
    // =====================================================

    private void connectTableSelection() {

        view.getTimetableTable()
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldTimetable, newTimetable) -> {

                    selectedTimetable = newTimetable;

                    if (newTimetable != null) {

                        populateForm(newTimetable);

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

    private void populateForm(
            Timetable timetable
    ) {

        selectCourse(
                timetable.getCourseId()
        );

        selectLecturer(
                timetable.getLecturerId()
        );

        view.getDayComboBox()
                .getSelectionModel()
                .select(
                        timetable.getDayOfWeek()
                );

        view.getSemesterComboBox()
                .getSelectionModel()
                .select(
                        Integer.valueOf(
                                timetable.getSemester()
                        )
                );

        view.getStartTimeField()
                .setText(
                        timetable.getStartTime()
                                .format(formatter)
                );

        view.getEndTimeField()
                .setText(
                        timetable.getEndTime()
                                .format(formatter)
                );

        view.getRoomField()
                .setText(
                        timetable.getRoom()
                );

    }

    // =====================================================
    // SELECT COURSE
    // =====================================================

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

    // =====================================================
    // SELECT LECTURER
    // =====================================================

    private void selectLecturer(
            int lecturerId
    ) {

        for (Lecturer lecturer : lecturerData) {

            if (lecturer.getLecturerId() == lecturerId) {

                view.getLecturerComboBox()
                        .getSelectionModel()
                        .select(lecturer);

                break;

            }

        }

    }

    // =====================================================
    // CREATE TIMETABLE OBJECT
    // =====================================================

    private Timetable createTimetableFromForm() {

        Course course =
                view.getCourseComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        Lecturer lecturer =
                view.getLecturerComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        String day =
                view.getDayComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        Integer semester =
                view.getSemesterComboBox()
                        .getSelectionModel()
                        .getSelectedItem();

        if (course == null
                || lecturer == null
                || day == null
                || semester == null) {

            return null;

        }

        try {

            LocalTime startTime =
                    LocalTime.parse(
                            view.getStartTimeField()
                                    .getText()
                                    .trim(),
                            formatter
                    );

            LocalTime endTime =
                    LocalTime.parse(
                            view.getEndTimeField()
                                    .getText()
                                    .trim(),
                            formatter
                    );

            Timetable timetable =
                    new Timetable();

            if (selectedTimetable != null) {

                timetable.setTimetableId(
                        selectedTimetable.getTimetableId()
                );

            }

            timetable.setCourseId(
                    course.getCourseId()
            );

            timetable.setLecturerId(
                    lecturer.getLecturerId()
            );

            timetable.setDayOfWeek(
                    day
            );

            timetable.setStartTime(
                    startTime
            );

            timetable.setEndTime(
                    endTime
            );

            timetable.setRoom(
                    view.getRoomField()
                            .getText()
                            .trim()
            );

            timetable.setSemester(
                    semester
            );

            return timetable;

        } catch (Exception ex) {

            return null;

        }

    }

    // =====================================================
    // ADD TIMETABLE
    // =====================================================

    private void addTimetable() {

        Timetable timetable =
                createTimetableFromForm();

        if (timetable == null) {

            showWarning(
                    "Invalid Input",
                    "Please complete all fields.\n\nTime must be in HH:mm format."
            );

            return;

        }

        boolean success =
                service.addTimetable(
                        timetable
                );

        if (success) {

            showInformation(
                    "Success",
                    "Timetable added successfully."
            );

            loadTimetables();

            clearForm();

        } else {

            showError(
                    "Add Failed",
                    "Lecturer or Room already has another class during this time."
            );

        }

    }     // =====================================================
    // UPDATE TIMETABLE
    // =====================================================

    private void updateTimetable() {

        if (selectedTimetable == null) {

            showWarning(
                    "Selection Required",
                    "Please select a timetable record to update."
            );

            return;
        }

        Timetable timetable =
                createTimetableFromForm();

        if (timetable == null) {

            showWarning(
                    "Invalid Input",
                    "Please complete all fields.\n\nTime must be in HH:mm format."
            );

            return;
        }

        boolean success =
                service.updateTimetable(
                        timetable
                );

        if (success) {

            showInformation(
                    "Success",
                    "Timetable updated successfully."
            );

            loadTimetables();

            clearForm();

        } else {

            showError(
                    "Update Failed",
                    "Lecturer or Room already has another class during this time."
            );

        }

    }

    // =====================================================
    // DELETE TIMETABLE
    // =====================================================

    private void deleteTimetable() {

        if (selectedTimetable == null) {

            showWarning(
                    "Selection Required",
                    "Please select a timetable record."
            );

            return;

        }

        Alert confirmation =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmation.setTitle(
                "Delete Timetable"
        );

        confirmation.setHeaderText(
                "Delete Timetable"
        );

        confirmation.setContentText(
                "Delete timetable for\n\n"
                        + selectedTimetable.getCourseDisplayName()
                        + "\n"
                        + selectedTimetable.getDayOfWeek()
                        + " "
                        + selectedTimetable.getStartTime().format(formatter)
                        + " - "
                        + selectedTimetable.getEndTime().format(formatter)
                        + " ?"
        );

        Optional<ButtonType> result =
                confirmation.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            boolean success =
                    service.deleteTimetable(
                            selectedTimetable.getTimetableId()
                    );

            if (success) {

                showInformation(
                        "Deleted",
                        "Timetable deleted successfully."
                );

                loadTimetables();

                clearForm();

            } else {

                showError(
                        "Delete Failed",
                        "Unable to delete timetable."
                );

            }

        }

    }

    // =====================================================
    // CLEAR FORM
    // =====================================================

    private void clearForm() {

        selectedTimetable = null;

        clearFormFieldsOnly();

        view.getTimetableTable()
                .getSelectionModel()
                .clearSelection();

        updateButtonStates();

        updateStatistics();

    }

    // =====================================================
    // CLEAR FORM FIELDS
    // =====================================================

    private void clearFormFieldsOnly() {

        view.getCourseComboBox()
                .getSelectionModel()
                .clearSelection();

        view.getLecturerComboBox()
                .getSelectionModel()
                .clearSelection();

        view.getDayComboBox()
                .getSelectionModel()
                .clearSelection();

        view.getSemesterComboBox()
                .getSelectionModel()
                .clearSelection();

        view.getStartTimeField().clear();

        view.getEndTimeField().clear();

        view.getRoomField().clear();

    }     // =====================================================
    // STATISTICS
    // =====================================================

    private void updateStatistics() {

        view.getTotalTimetablesValue()
                .setText(
                        String.valueOf(
                                timetableData.size()
                        )
                );

        view.getDatabaseStatusValue()
                .setText(
                        "Connected"
                );

        if (selectedTimetable == null) {

            view.getSelectedTimetableValue()
                    .setText(
                            "None"
                    );

        } else {

            view.getSelectedTimetableValue()
                    .setText(
                            selectedTimetable.getCourseCode()
                                    + " - "
                                    + selectedTimetable.getDayOfWeek()
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
                selectedTimetable == null;

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