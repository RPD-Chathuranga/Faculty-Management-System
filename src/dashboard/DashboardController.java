package dashboard;

import authentication.LoginView;
import faculty.FacultyView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;
import department.DepartmentView;
import lecturer.LecturerView;
import student.StudentView;
import course.CourseView;
import timetable.TimetableView ;
import grade.GradeView;
import authentication.UserSession;

import attendance.AttendanceView;
import javafx.scene.control.ChoiceDialog;
import java.util.List;

public class DashboardController {

    private final DashboardView view;
    private final DashboardService service;
    private final Stage stage;

    public DashboardController(
            DashboardView view,
            DashboardService service,
            Stage stage
    ) {
        this.view = view;
        this.service = service;
        this.stage = stage;

        connectButtons();
        applyRolePermissions();
    }

    private void connectButtons() {

        // Open the real Faculty Management page
        view.getFacultyButton().setOnAction(event ->
                openFacultyModule()
        );

        // These modules will be connected later
        view.getDepartmentButton().setOnAction(event -> {

            DepartmentView departmentView =
                    new DepartmentView(stage);

            departmentView.show();

        });

        view.getLecturerButton().setOnAction(event -> {

            LecturerView lecturerView =
                    new LecturerView(stage);

            lecturerView.show();
        });

        view.getStudentButton().setOnAction(event -> {

            StudentView studentView =
                    new StudentView(stage);

            studentView.show();
        });

        view.getCourseButton().setOnAction(event -> {

            CourseView courseView =
                    new CourseView(stage);

            courseView.show();

        });

        view.getAcademicButton().setOnAction(event ->
                openAcademicModule()
        );

        view.getLogoutButton().setOnAction(event ->
                logout()
        );
    }

    private void openFacultyModule() {

        FacultyView facultyView =
                new FacultyView(stage);

        facultyView.show();
    }

    private void showModuleMessage(
            String title,
            String message
    ) {
        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void openAcademicModule() {

        List<String> modules =
                List.of(
                        "Timetable Management",
                        "Attendance Management",
                        "Grade Management"
                );

        ChoiceDialog<String> dialog =
                new ChoiceDialog<>(
                        "Timetable Management",
                        modules
                );

        dialog.setTitle("Academic Management");
        dialog.setHeaderText("Select an Academic Module");
        dialog.setContentText("Module:");

        Optional<String> result =
                dialog.showAndWait();

        if (result.isEmpty()) {
            return;
        }

        switch (result.get()) {

            case "Timetable Management" -> {

                new TimetableView(stage).show();

            }

            case "Attendance Management" -> {

                new AttendanceView(stage).show();

            }

            case "Grade Management" -> {

                GradeView gradeView =
                        new GradeView(stage);

                gradeView.show();
            }
        }
    }

    private void applyRolePermissions() {

        String role = UserSession.role;

        if (role == null) {
            return;
        }

        switch (role) {

            case "ADMIN" -> {

                view.getFacultyButton().setVisible(true);
                view.getFacultyButton().setManaged(true);

                view.getDepartmentButton().setVisible(true);
                view.getDepartmentButton().setManaged(true);

                view.getLecturerButton().setVisible(true);
                view.getLecturerButton().setManaged(true);

                view.getStudentButton().setVisible(true);
                view.getStudentButton().setManaged(true);

                view.getCourseButton().setVisible(true);
                view.getCourseButton().setManaged(true);

                view.getAcademicButton().setVisible(true);
                view.getAcademicButton().setManaged(true);
            }

            case "LECTURER" -> {

                view.getFacultyButton().setVisible(false);
                view.getFacultyButton().setManaged(false);

                view.getDepartmentButton().setVisible(false);
                view.getDepartmentButton().setManaged(false);

                view.getLecturerButton().setVisible(false);
                view.getLecturerButton().setManaged(false);

                view.getStudentButton().setVisible(false);
                view.getStudentButton().setManaged(false);

                view.getCourseButton().setVisible(true);
                view.getCourseButton().setManaged(true);

                view.getAcademicButton().setVisible(true);
                view.getAcademicButton().setManaged(true);
            }

            case "STUDENT" -> {

                view.getFacultyButton().setVisible(false);
                view.getFacultyButton().setManaged(false);

                view.getDepartmentButton().setVisible(false);
                view.getDepartmentButton().setManaged(false);

                view.getLecturerButton().setVisible(false);
                view.getLecturerButton().setManaged(false);

                view.getStudentButton().setVisible(false);
                view.getStudentButton().setManaged(false);

                view.getCourseButton().setVisible(false);
                view.getCourseButton().setManaged(false);

                view.getAcademicButton().setVisible(true);
                view.getAcademicButton().setManaged(true);
            }

            default -> {
                view.getFacultyButton().setDisable(true);
                view.getDepartmentButton().setDisable(true);
                view.getLecturerButton().setDisable(true);
                view.getStudentButton().setDisable(true);
                view.getCourseButton().setDisable(true);
                view.getAcademicButton().setDisable(true);
            }
        }
    }

    private void logout() {

        Alert confirmation =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmation.setTitle("Logout");
        confirmation.setHeaderText("Confirm Logout");

        confirmation.setContentText(
                "Are you sure you want to logout?"
        );

        Optional<ButtonType> result =
                confirmation.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            service.logout();

            LoginView loginView =
                    new LoginView();

            loginView.start(stage);
        }
    }
}