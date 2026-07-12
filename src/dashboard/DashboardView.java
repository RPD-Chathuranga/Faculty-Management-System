package dashboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class DashboardView {

    private final Stage stage;
    private final DashboardService dashboardService;

    private Button facultyButton;
    private Button departmentButton;
    private Button lecturerButton;
    private Button studentButton;
    private Button courseButton;
    private Button academicButton;
    private Button logoutButton;

    private final String MAROON = "#7A0019";
    private final String DARK_MAROON = "#4A0010";
    private final String GOLD = "#D4AF37";
    private final String LIGHT_GOLD = "#F5E7B2";
    private final String BACKGROUND = "#F7F5F2";
    private final String TEXT_DARK = "#252525";

    public DashboardView(Stage stage) {

        this.stage = stage;
        this.dashboardService =
                new DashboardService();
    }

    public void show() {

        BorderPane root =
                new BorderPane();

        root.setStyle(
                "-fx-background-color:"
                        + BACKGROUND
                        + ";"
        );

        /*
         * Main content is created first.
         * This initializes all main module buttons.
         */
        VBox mainContent =
                createMainContent();

        /*
         * Sidebar is created after the main buttons.
         */
        VBox sidebar =
                createSidebar();

        ScrollPane scrollPane =
                new ScrollPane(mainContent);

        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        scrollPane.setStyle(
                "-fx-background-color:transparent;" +
                        "-fx-background:"
                        + BACKGROUND
                        + ";"
        );

        root.setLeft(sidebar);
        root.setCenter(scrollPane);

        Scene scene =
                new Scene(
                        root,
                        1200,
                        760
                );

        stage.setTitle(
                "Faculty Management System - Dashboard"
        );

        stage.setScene(scene);
        stage.setMinWidth(1050);
        stage.setMinHeight(700);
        stage.centerOnScreen();

        /*
         * Connect buttons after the full view is created.
         */
        new DashboardController(
                this,
                dashboardService,
                stage
        );

        stage.show();
    }

    private VBox createSidebar() {

        VBox sidebar =
                new VBox();

        sidebar.setPrefWidth(275);

        sidebar.setPadding(
                new Insets(
                        30,
                        20,
                        25,
                        20
                )
        );

        sidebar.setStyle(
                "-fx-background-color:linear-gradient(" +
                        "to bottom,"
                        + DARK_MAROON
                        + ","
                        + MAROON
                        + ");"
        );

        Label universityIcon =
                new Label("UOK");

        universityIcon.setAlignment(
                Pos.CENTER
        );

        universityIcon.setMinSize(
                74,
                74
        );

        universityIcon.setMaxSize(
                74,
                74
        );

        universityIcon.setStyle(
                "-fx-background-color:"
                        + GOLD
                        + ";" +
                        "-fx-background-radius:37;" +
                        "-fx-text-fill:"
                        + DARK_MAROON
                        + ";" +
                        "-fx-font-size:19px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-border-color:#FFF2C7;" +
                        "-fx-border-width:3;" +
                        "-fx-border-radius:37;"
        );

        Label universityName =
                new Label(
                        "UNIVERSITY OF\nKELANIYA"
                );

        universityName.setAlignment(
                Pos.CENTER
        );

        universityName.setStyle(
                "-fx-text-fill:white;" +
                        "-fx-font-size:17px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-text-alignment:center;"
        );

        Label facultyName =
                new Label(
                        "Faculty of Computing\nand Technology"
                );

        facultyName.setAlignment(
                Pos.CENTER
        );

        facultyName.setStyle(
                "-fx-text-fill:"
                        + GOLD
                        + ";" +
                        "-fx-font-size:13px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-text-alignment:center;"
        );

        VBox brandBox =
                new VBox(
                        13,
                        universityIcon,
                        universityName,
                        facultyName
                );

        brandBox.setAlignment(
                Pos.CENTER
        );

        Separator separator =
                new Separator();

        Label menuTitle =
                new Label("MAIN MENU");

        menuTitle.setStyle(
                "-fx-text-fill:#E8D7DC;" +
                        "-fx-font-size:11px;" +
                        "-fx-font-weight:bold;"
        );

        Button dashboardMenuButton =
                createSidebarButton(
                        "⌂",
                        "Dashboard",
                        true
                );

        Button facultyMenuButton =
                createSidebarButton(
                        "F",
                        "Faculties",
                        false
                );

        Button departmentMenuButton =
                createSidebarButton(
                        "D",
                        "Departments",
                        false
                );

        Button lecturerMenuButton =
                createSidebarButton(
                        "L",
                        "Lecturers",
                        false
                );

        Button studentMenuButton =
                createSidebarButton(
                        "S",
                        "Students",
                        false
                );

        Button courseMenuButton =
                createSidebarButton(
                        "C",
                        "Courses",
                        false
                );

        /*
         * Sidebar buttons use the same actions
         * as the main card buttons.
         */
        facultyMenuButton.setOnAction(
                event -> facultyButton.fire()
        );

        departmentMenuButton.setOnAction(
                event -> departmentButton.fire()
        );

        lecturerMenuButton.setOnAction(
                event -> lecturerButton.fire()
        );

        studentMenuButton.setOnAction(
                event -> studentButton.fire()
        );

        courseMenuButton.setOnAction(
                event -> courseButton.fire()
        );

        dashboardMenuButton.setOnAction(
                event -> scrollToTop()
        );

        Region spacer =
                new Region();

        VBox.setVgrow(
                spacer,
                Priority.ALWAYS
        );

        Label signedInLabel =
                new Label(
                        "SIGNED IN AS"
                );

        signedInLabel.setStyle(
                "-fx-text-fill:#D9BDC5;" +
                        "-fx-font-size:10px;" +
                        "-fx-font-weight:bold;"
        );

        Label userNameLabel =
                new Label(
                        dashboardService
                                .getLoggedInUserName()
                );

        userNameLabel.setWrapText(true);

        userNameLabel.setStyle(
                "-fx-text-fill:white;" +
                        "-fx-font-size:14px;" +
                        "-fx-font-weight:bold;"
        );

        Label userRoleLabel =
                new Label(
                        dashboardService
                                .getLoggedInUserRole()
                );

        userRoleLabel.setStyle(
                "-fx-background-color:"
                        + GOLD
                        + ";" +
                        "-fx-background-radius:12;" +
                        "-fx-padding:4 12 4 12;" +
                        "-fx-text-fill:"
                        + DARK_MAROON
                        + ";" +
                        "-fx-font-size:10px;" +
                        "-fx-font-weight:bold;"
        );

        VBox userBox =
                new VBox(
                        6,
                        signedInLabel,
                        userNameLabel,
                        userRoleLabel
                );

        logoutButton =
                new Button("Logout");

        logoutButton.setMaxWidth(
                Double.MAX_VALUE
        );

        logoutButton.setPrefHeight(42);
        logoutButton.setCursor(
                Cursor.HAND
        );

        String logoutNormalStyle =
                "-fx-background-color:transparent;" +
                        "-fx-border-color:"
                        + GOLD
                        + ";" +
                        "-fx-border-radius:8;" +
                        "-fx-border-width:1.5;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-size:13px;" +
                        "-fx-font-weight:bold;";

        String logoutHoverStyle =
                "-fx-background-color:"
                        + GOLD
                        + ";" +
                        "-fx-border-color:"
                        + GOLD
                        + ";" +
                        "-fx-border-radius:8;" +
                        "-fx-background-radius:8;" +
                        "-fx-border-width:1.5;" +
                        "-fx-text-fill:"
                        + DARK_MAROON
                        + ";" +
                        "-fx-font-size:13px;" +
                        "-fx-font-weight:bold;";

        logoutButton.setStyle(
                logoutNormalStyle
        );

        logoutButton.setOnMouseEntered(
                event ->
                        logoutButton.setStyle(
                                logoutHoverStyle
                        )
        );

        logoutButton.setOnMouseExited(
                event ->
                        logoutButton.setStyle(
                                logoutNormalStyle
                        )
        );

        sidebar.getChildren().addAll(
                brandBox,
                separator,
                menuTitle,
                dashboardMenuButton,
                facultyMenuButton,
                departmentMenuButton,
                lecturerMenuButton,
                studentMenuButton,
                courseMenuButton,
                spacer,
                userBox,
                logoutButton
        );

        VBox.setMargin(
                separator,
                new Insets(
                        25,
                        0,
                        20,
                        0
                )
        );

        VBox.setMargin(
                menuTitle,
                new Insets(
                        0,
                        0,
                        7,
                        8
                )
        );

        VBox.setMargin(
                logoutButton,
                new Insets(
                        18,
                        0,
                        0,
                        0
                )
        );

        return sidebar;
    }

    private Button createSidebarButton(
            String icon,
            String text,
            boolean selected
    ) {

        Button button =
                new Button(
                        icon
                                + "     "
                                + text
                );

        button.setAlignment(
                Pos.CENTER_LEFT
        );

        button.setMaxWidth(
                Double.MAX_VALUE
        );

        button.setPrefHeight(45);
        button.setCursor(
                Cursor.HAND
        );

        String normalStyle =
                "-fx-background-color:transparent;" +
                        "-fx-text-fill:#F5ECEF;" +
                        "-fx-font-size:13px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-background-radius:8;" +
                        "-fx-padding:0 15 0 15;";

        String selectedStyle =
                "-fx-background-color:"
                        + GOLD
                        + ";" +
                        "-fx-text-fill:"
                        + DARK_MAROON
                        + ";" +
                        "-fx-font-size:13px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-background-radius:8;" +
                        "-fx-padding:0 15 0 15;";

        String hoverStyle =
                "-fx-background-color:rgba(255,255,255,0.12);" +
                        "-fx-text-fill:white;" +
                        "-fx-font-size:13px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-background-radius:8;" +
                        "-fx-padding:0 15 0 15;";

        button.setStyle(
                selected
                        ? selectedStyle
                        : normalStyle
        );

        if (!selected) {

            button.setOnMouseEntered(
                    event ->
                            button.setStyle(
                                    hoverStyle
                            )
            );

            button.setOnMouseExited(
                    event ->
                            button.setStyle(
                                    normalStyle
                            )
            );
        }

        return button;
    }

    private VBox createMainContent() {

        VBox mainContent =
                new VBox(25);

        mainContent.setMaxWidth(
                Double.MAX_VALUE
        );

        mainContent.setPadding(
                new Insets(
                        32,
                        38,
                        35,
                        38
                )
        );

        HBox header =
                createHeader();

        HBox welcomeBanner =
                createWelcomeBanner();

        Label sectionTitle =
                new Label(
                        "Management Modules"
                );

        sectionTitle.setStyle(
                "-fx-text-fill:"
                        + TEXT_DARK
                        + ";" +
                        "-fx-font-size:21px;" +
                        "-fx-font-weight:bold;"
        );

        Label sectionSubtitle =
                new Label(
                        "Select a module to manage university information"
                );

        sectionSubtitle.setStyle(
                "-fx-text-fill:#777777;" +
                        "-fx-font-size:13px;"
        );

        VBox sectionHeading =
                new VBox(
                        4,
                        sectionTitle,
                        sectionSubtitle
                );

        GridPane moduleGrid =
                createModuleGrid();

        mainContent.getChildren().addAll(
                header,
                welcomeBanner,
                sectionHeading,
                moduleGrid
        );

        return mainContent;
    }

    private HBox createHeader() {

        Label pageTitle =
                new Label(
                        "Executive Dashboard"
                );

        pageTitle.setStyle(
                "-fx-text-fill:"
                        + TEXT_DARK
                        + ";" +
                        "-fx-font-size:27px;" +
                        "-fx-font-weight:bold;"
        );

        Label pageSubtitle =
                new Label(
                        "Faculty Management System"
                );

        pageSubtitle.setStyle(
                "-fx-text-fill:#808080;" +
                        "-fx-font-size:13px;"
        );

        VBox titleBox =
                new VBox(
                        3,
                        pageTitle,
                        pageSubtitle
                );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Circle profileCircle =
                new Circle(23);

        profileCircle.setFill(
                Color.web(GOLD)
        );

        Label profileLetter =
                new Label(
                        getFirstLetter(
                                dashboardService
                                        .getLoggedInUserName()
                        )
                );

        profileLetter.setStyle(
                "-fx-text-fill:"
                        + DARK_MAROON
                        + ";" +
                        "-fx-font-size:17px;" +
                        "-fx-font-weight:bold;"
        );

        StackPane profileIcon =
                new StackPane(
                        profileCircle,
                        profileLetter
                );

        Label profileName =
                new Label(
                        dashboardService
                                .getLoggedInUserName()
                );

        profileName.setStyle(
                "-fx-text-fill:"
                        + TEXT_DARK
                        + ";" +
                        "-fx-font-size:13px;" +
                        "-fx-font-weight:bold;"
        );

        Label profileRole =
                new Label(
                        dashboardService
                                .getLoggedInUserRole()
                );

        profileRole.setStyle(
                "-fx-text-fill:#888888;" +
                        "-fx-font-size:11px;"
        );

        VBox profileText =
                new VBox(
                        2,
                        profileName,
                        profileRole
                );

        HBox profileBox =
                new HBox(
                        10,
                        profileIcon,
                        profileText
                );

        profileBox.setAlignment(
                Pos.CENTER_LEFT
        );

        HBox header =
                new HBox(
                        titleBox,
                        spacer,
                        profileBox
                );

        header.setAlignment(
                Pos.CENTER_LEFT
        );

        return header;
    }

    private HBox createWelcomeBanner() {

        Label statusBadge =
                new Label(
                        "● System Online"
                );

        statusBadge.setStyle(
                "-fx-background-color:rgba(255,255,255,0.16);" +
                        "-fx-background-radius:15;" +
                        "-fx-padding:7 14 7 14;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-size:11px;" +
                        "-fx-font-weight:bold;"
        );

        Label welcomeLabel =
                new Label(
                        "Welcome back, "
                                + dashboardService
                                .getLoggedInUserName()
                                + "!"
                );

        welcomeLabel.setStyle(
                "-fx-text-fill:white;" +
                        "-fx-font-size:24px;" +
                        "-fx-font-weight:bold;"
        );

        Label descriptionLabel =
                new Label(
                        "Manage faculties, departments, lecturers, students,\n" +
                                "courses and academic activities from one place."
                );

        descriptionLabel.setStyle(
                "-fx-text-fill:#F4DCE3;" +
                        "-fx-font-size:13px;"
        );

        VBox welcomeText =
                new VBox(
                        9,
                        statusBadge,
                        welcomeLabel,
                        descriptionLabel
                );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label decorativeText =
                new Label("FMS");

        decorativeText.setStyle(
                "-fx-text-fill:rgba(255,255,255,0.16);" +
                        "-fx-font-size:73px;" +
                        "-fx-font-weight:bold;"
        );

        HBox banner =
                new HBox(
                        welcomeText,
                        spacer,
                        decorativeText
                );

        banner.setAlignment(
                Pos.CENTER_LEFT
        );

        banner.setPadding(
                new Insets(
                        27,
                        32,
                        27,
                        32
                )
        );

        banner.setMinHeight(175);

        banner.setStyle(
                "-fx-background-color:linear-gradient(" +
                        "to right,"
                        + DARK_MAROON
                        + ","
                        + MAROON
                        + ");" +
                        "-fx-background-radius:18;" +
                        "-fx-effect:dropshadow(gaussian," +
                        "rgba(70,0,15,0.22),18,0.2,0,7);"
        );

        return banner;
    }

    private GridPane createModuleGrid() {

        facultyButton =
                new Button(
                        "Manage Faculties"
                );

        departmentButton =
                new Button(
                        "Manage Departments"
                );

        lecturerButton =
                new Button(
                        "Manage Lecturers"
                );

        studentButton =
                new Button(
                        "Manage Students"
                );

        courseButton =
                new Button(
                        "Manage Courses"
                );

        academicButton =
                new Button(
                        "Open Academic Tools"
                );

        GridPane grid =
                new GridPane();

        grid.setHgap(18);
        grid.setVgap(18);

        grid.setMaxWidth(
                Double.MAX_VALUE
        );

        ColumnConstraints firstColumn =
                new ColumnConstraints();

        firstColumn.setPercentWidth(50);

        ColumnConstraints secondColumn =
                new ColumnConstraints();

        secondColumn.setPercentWidth(50);

        grid.getColumnConstraints().addAll(
                firstColumn,
                secondColumn
        );

        VBox facultyCard =
                createModuleCard(
                        "F",
                        "Faculty Management",
                        "Create, update and manage university faculties.",
                        facultyButton
                );

        VBox departmentCard =
                createModuleCard(
                        "D",
                        "Department Management",
                        "Organize departments under each faculty.",
                        departmentButton
                );

        VBox lecturerCard =
                createModuleCard(
                        "L",
                        "Lecturer Management",
                        "Manage lecturer profiles and course assignments.",
                        lecturerButton
                );

        VBox studentCard =
                createModuleCard(
                        "S",
                        "Student Management",
                        "Manage student registrations and information.",
                        studentButton
                );

        VBox courseCard =
                createModuleCard(
                        "C",
                        "Course Management",
                        "Manage academic courses, credits and lecturers.",
                        courseButton
                );

        VBox academicCard =
                createModuleCard(
                        "A",
                        "Academic Management",
                        "Manage timetable, attendance and grades.",
                        academicButton
                );

        grid.add(
                facultyCard,
                0,
                0
        );

        grid.add(
                departmentCard,
                1,
                0
        );

        grid.add(
                lecturerCard,
                0,
                1
        );

        grid.add(
                studentCard,
                1,
                1
        );

        grid.add(
                courseCard,
                0,
                2
        );

        grid.add(
                academicCard,
                1,
                2
        );

        GridPane.setHgrow(
                facultyCard,
                Priority.ALWAYS
        );

        GridPane.setHgrow(
                departmentCard,
                Priority.ALWAYS
        );

        GridPane.setHgrow(
                lecturerCard,
                Priority.ALWAYS
        );

        GridPane.setHgrow(
                studentCard,
                Priority.ALWAYS
        );

        GridPane.setHgrow(
                courseCard,
                Priority.ALWAYS
        );

        GridPane.setHgrow(
                academicCard,
                Priority.ALWAYS
        );

        return grid;
    }

    private VBox createModuleCard(
            String iconText,
            String title,
            String description,
            Button actionButton
    ) {

        Label icon =
                new Label(iconText);

        icon.setAlignment(
                Pos.CENTER
        );

        icon.setMinSize(
                52,
                52
        );

        icon.setMaxSize(
                52,
                52
        );

        icon.setStyle(
                "-fx-background-color:"
                        + LIGHT_GOLD
                        + ";" +
                        "-fx-background-radius:14;" +
                        "-fx-text-fill:"
                        + DARK_MAROON
                        + ";" +
                        "-fx-font-size:19px;" +
                        "-fx-font-weight:bold;"
        );

        Label titleLabel =
                new Label(title);

        titleLabel.setStyle(
                "-fx-text-fill:"
                        + TEXT_DARK
                        + ";" +
                        "-fx-font-size:16px;" +
                        "-fx-font-weight:bold;"
        );

        Label descriptionLabel =
                new Label(description);

        descriptionLabel.setWrapText(true);

        descriptionLabel.setStyle(
                "-fx-text-fill:#777777;" +
                        "-fx-font-size:12px;"
        );

        Region spacer =
                new Region();

        VBox.setVgrow(
                spacer,
                Priority.ALWAYS
        );

        actionButton.setCursor(
                Cursor.HAND
        );

        actionButton.setPrefHeight(36);

        String normalButtonStyle =
                "-fx-background-color:"
                        + MAROON
                        + ";" +
                        "-fx-text-fill:white;" +
                        "-fx-font-size:12px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-background-radius:7;" +
                        "-fx-padding:8 16 8 16;";

        String hoverButtonStyle =
                "-fx-background-color:"
                        + GOLD
                        + ";" +
                        "-fx-text-fill:"
                        + DARK_MAROON
                        + ";" +
                        "-fx-font-size:12px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-background-radius:7;" +
                        "-fx-padding:8 16 8 16;";

        actionButton.setStyle(
                normalButtonStyle
        );

        actionButton.setOnMouseEntered(
                event ->
                        actionButton.setStyle(
                                hoverButtonStyle
                        )
        );

        actionButton.setOnMouseExited(
                event ->
                        actionButton.setStyle(
                                normalButtonStyle
                        )
        );

        VBox card =
                new VBox(
                        10,
                        icon,
                        titleLabel,
                        descriptionLabel,
                        spacer,
                        actionButton
                );

        card.setPadding(
                new Insets(20)
        );

        card.setMinHeight(205);

        card.setMaxWidth(
                Double.MAX_VALUE
        );

        String normalCardStyle =
                "-fx-background-color:white;" +
                        "-fx-background-radius:14;" +
                        "-fx-border-color:#EEE8E2;" +
                        "-fx-border-radius:14;" +
                        "-fx-border-width:1;" +
                        "-fx-effect:dropshadow(gaussian," +
                        "rgba(0,0,0,0.07),12,0.1,0,4);";

        String hoverCardStyle =
                "-fx-background-color:white;" +
                        "-fx-background-radius:14;" +
                        "-fx-border-color:"
                        + GOLD
                        + ";" +
                        "-fx-border-radius:14;" +
                        "-fx-border-width:1.5;" +
                        "-fx-effect:dropshadow(gaussian," +
                        "rgba(80,0,20,0.17),18,0.15,0,7);";

        card.setStyle(
                normalCardStyle
        );

        card.setOnMouseEntered(
                event ->
                        card.setStyle(
                                hoverCardStyle
                        )
        );

        card.setOnMouseExited(
                event ->
                        card.setStyle(
                                normalCardStyle
                        )
        );

        return card;
    }

    private void scrollToTop() {

        /*
         * Dashboard button currently keeps the user
         * on the dashboard page.
         */
    }

    private String getFirstLetter(
            String name
    ) {

        if (name == null
                || name.trim().isEmpty()) {

            return "U";
        }

        return name
                .trim()
                .substring(0, 1)
                .toUpperCase();
    }

    public Button getFacultyButton() {
        return facultyButton;
    }

    public Button getDepartmentButton() {
        return departmentButton;
    }

    public Button getLecturerButton() {
        return lecturerButton;
    }

    public Button getStudentButton() {
        return studentButton;
    }

    public Button getCourseButton() {
        return courseButton;
    }

    public Button getAcademicButton() {
        return academicButton;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }
}