package course;

import department.Department;
import lecturer.Lecturer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CourseView {

    private final Stage stage;

    private TextField courseCodeField;
    private TextField courseNameField;
    private TextField searchField;

    private ComboBox<Integer> creditsComboBox;
    private ComboBox<Department> departmentComboBox;
    private ComboBox<Lecturer> lecturerComboBox;

    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Button clearButton;
    private Button backButton;

    private TableView<Course> courseTable;

    private Label totalCoursesValue;
    private Label databaseStatusValue;
    private Label selectedCourseValue;
    private Label systemStatusValue;

    private final String MAROON = "#7A0019";
    private final String DARK_MAROON = "#4A0010";
    private final String GOLD = "#D4AF37";
    private final String LIGHT_GOLD = "#F5E7B2";
    private final String BACKGROUND = "#F7F5F2";
    private final String TEXT_DARK = "#252525";

    public CourseView(Stage stage) {
        this.stage = stage;
    }

    public void show() {

        BorderPane root =
                new BorderPane();

        root.setStyle(
                "-fx-background-color:" + BACKGROUND + ";"
        );

        VBox sidebar =
                createSidebar();

        VBox mainContent =
                createMainContent();

        ScrollPane scrollPane =
                new ScrollPane(mainContent);

        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        scrollPane.setStyle(
                "-fx-background-color:transparent;" +
                        "-fx-background:" + BACKGROUND + ";"
        );

        root.setLeft(sidebar);
        root.setCenter(scrollPane);

        Scene scene =
                new Scene(
                        root,
                        1280,
                        800
                );

        stage.setTitle(
                "Course Management - Faculty Management System"
        );

        stage.setScene(scene);
        stage.setMinWidth(1120);
        stage.setMinHeight(720);
        stage.centerOnScreen();

        new CourseController(
                this,
                stage
        );

        stage.show();
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

    private VBox createSidebar() {

        VBox sidebar =
                new VBox(18);

        sidebar.setPrefWidth(260);

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
                        "to bottom," +
                        DARK_MAROON + "," +
                        MAROON + ");"
        );

        Label universityIcon =
                new Label("UOK");

        universityIcon.setAlignment(
                Pos.CENTER
        );

        universityIcon.setMinSize(
                72,
                72
        );

        universityIcon.setMaxSize(
                72,
                72
        );

        universityIcon.setStyle(
                "-fx-background-color:" + GOLD + ";" +
                        "-fx-background-radius:36;" +
                        "-fx-text-fill:" + DARK_MAROON + ";" +
                        "-fx-font-size:18px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-border-color:#FFF2C7;" +
                        "-fx-border-width:3;" +
                        "-fx-border-radius:36;"
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
                "-fx-text-fill:" + GOLD + ";" +
                        "-fx-font-size:13px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-text-alignment:center;"
        );

        VBox brandingBox =
                new VBox(
                        12,
                        universityIcon,
                        universityName,
                        facultyName
                );

        brandingBox.setAlignment(
                Pos.CENTER
        );

        Label sectionLabel =
                new Label(
                        "CURRENT MODULE"
                );

        sectionLabel.setStyle(
                "-fx-text-fill:#D9BDC5;" +
                        "-fx-font-size:10px;" +
                        "-fx-font-weight:bold;"
        );

        Label moduleLabel =
                new Label(
                        "Course Management"
                );

        moduleLabel.setMaxWidth(
                Double.MAX_VALUE
        );

        moduleLabel.setStyle(
                "-fx-background-color:" + GOLD + ";" +
                        "-fx-background-radius:8;" +
                        "-fx-padding:13 15 13 15;" +
                        "-fx-text-fill:" + DARK_MAROON + ";" +
                        "-fx-font-size:14px;" +
                        "-fx-font-weight:bold;"
        );

        Label descriptionLabel =
                new Label(
                        "Create, update, view and delete\n" +
                                "university course records."
                );

        descriptionLabel.setStyle(
                "-fx-text-fill:#F0E4E7;" +
                        "-fx-font-size:12px;" +
                        "-fx-line-spacing:4;"
        );

        Region spacer =
                new Region();

        VBox.setVgrow(
                spacer,
                Priority.ALWAYS
        );

        backButton =
                new Button(
                        "← Back to Dashboard"
                );

        backButton.setMaxWidth(
                Double.MAX_VALUE
        );

        backButton.setPrefHeight(43);
        backButton.setCursor(Cursor.HAND);

        String normalStyle =
                "-fx-background-color:transparent;" +
                        "-fx-border-color:" + GOLD + ";" +
                        "-fx-border-width:1.5;" +
                        "-fx-border-radius:8;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-size:13px;" +
                        "-fx-font-weight:bold;";

        String hoverStyle =
                "-fx-background-color:" + GOLD + ";" +
                        "-fx-background-radius:8;" +
                        "-fx-border-color:" + GOLD + ";" +
                        "-fx-border-width:1.5;" +
                        "-fx-border-radius:8;" +
                        "-fx-text-fill:" + DARK_MAROON + ";" +
                        "-fx-font-size:13px;" +
                        "-fx-font-weight:bold;";

        backButton.setStyle(
                normalStyle
        );

        backButton.setOnMouseEntered(
                event ->
                        backButton.setStyle(
                                hoverStyle
                        )
        );

        backButton.setOnMouseExited(
                event ->
                        backButton.setStyle(
                                normalStyle
                        )
        );

        sidebar.getChildren().addAll(
                brandingBox,
                sectionLabel,
                moduleLabel,
                descriptionLabel,
                spacer,
                backButton
        );

        VBox.setMargin(
                sectionLabel,
                new Insets(
                        25,
                        0,
                        0,
                        5
                )
        );

        return sidebar;
    }

    // =========================================================
    // MAIN CONTENT
    // =========================================================

    private VBox createMainContent() {

        VBox mainContent =
                new VBox(24);

        mainContent.setPadding(
                new Insets(
                        32,
                        38,
                        40,
                        38
                )
        );

        mainContent.setMaxWidth(
                Double.MAX_VALUE
        );

        HBox header =
                createHeader();

        HBox statisticsSection =
                createStatisticsSection();

        HBox formSection =
                createFormSection();

        VBox tableSection =
                createTableSection();

        mainContent.getChildren().addAll(
                header,
                statisticsSection,
                formSection,
                tableSection
        );

        return mainContent;
    }

    // =========================================================
    // HEADER
    // =========================================================

    private HBox createHeader() {

        Label titleLabel =
                new Label(
                        "Course Management"
                );

        titleLabel.setStyle(
                "-fx-text-fill:" + TEXT_DARK + ";" +
                        "-fx-font-size:28px;" +
                        "-fx-font-weight:bold;"
        );

        Label subtitleLabel =
                new Label(
                        "Manage course, department and lecturer assignments"
                );

        subtitleLabel.setStyle(
                "-fx-text-fill:#777777;" +
                        "-fx-font-size:13px;"
        );

        VBox titleBox =
                new VBox(
                        4,
                        titleLabel,
                        subtitleLabel
                );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label statusLabel =
                new Label(
                        "● Database Connected"
                );

        statusLabel.setStyle(
                "-fx-background-color:#E7F6EC;" +
                        "-fx-background-radius:15;" +
                        "-fx-padding:8 14 8 14;" +
                        "-fx-text-fill:#247A3C;" +
                        "-fx-font-size:11px;" +
                        "-fx-font-weight:bold;"
        );

        HBox header =
                new HBox(
                        titleBox,
                        spacer,
                        statusLabel
                );

        header.setAlignment(
                Pos.CENTER_LEFT
        );

        return header;
    }

    // =========================================================
    // STATISTICS
    // =========================================================

    private HBox createStatisticsSection() {

        totalCoursesValue =
                new Label("0");

        databaseStatusValue =
                new Label("Connected");

        selectedCourseValue =
                new Label("None");

        systemStatusValue =
                new Label("Ready");

        VBox totalCard =
                createStatisticCard(
                        "Total Courses",
                        totalCoursesValue,
                        "C"
                );

        VBox databaseCard =
                createStatisticCard(
                        "Database Status",
                        databaseStatusValue,
                        "DB"
                );

        VBox selectedCard =
                createStatisticCard(
                        "Selected Course",
                        selectedCourseValue,
                        "S"
                );

        VBox systemCard =
                createStatisticCard(
                        "System Status",
                        systemStatusValue,
                        "✓"
                );

        HBox statisticsBox =
                new HBox(
                        16,
                        totalCard,
                        databaseCard,
                        selectedCard,
                        systemCard
                );

        statisticsBox.setAlignment(
                Pos.CENTER_LEFT
        );

        HBox.setHgrow(
                totalCard,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                databaseCard,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                selectedCard,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                systemCard,
                Priority.ALWAYS
        );

        return statisticsBox;
    }

    private VBox createStatisticCard(
            String title,
            Label valueLabel,
            String iconText
    ) {

        Label iconLabel =
                new Label(iconText);

        iconLabel.setAlignment(
                Pos.CENTER
        );

        iconLabel.setMinSize(
                46,
                46
        );

        iconLabel.setMaxSize(
                46,
                46
        );

        iconLabel.setStyle(
                "-fx-background-color:" + LIGHT_GOLD + ";" +
                        "-fx-background-radius:12;" +
                        "-fx-text-fill:" + DARK_MAROON + ";" +
                        "-fx-font-size:14px;" +
                        "-fx-font-weight:bold;"
        );

        Label titleLabel =
                new Label(title);

        titleLabel.setStyle(
                "-fx-text-fill:#777777;" +
                        "-fx-font-size:11px;" +
                        "-fx-font-weight:bold;"
        );

        valueLabel.setStyle(
                "-fx-text-fill:" + TEXT_DARK + ";" +
                        "-fx-font-size:18px;" +
                        "-fx-font-weight:bold;"
        );

        valueLabel.setWrapText(true);

        VBox textBox =
                new VBox(
                        5,
                        titleLabel,
                        valueLabel
                );

        HBox cardContent =
                new HBox(
                        13,
                        iconLabel,
                        textBox
                );

        cardContent.setAlignment(
                Pos.CENTER_LEFT
        );

        VBox card =
                new VBox(
                        cardContent
                );

        card.setPadding(
                new Insets(18)
        );

        card.setMinHeight(90);
        card.setMaxWidth(
                Double.MAX_VALUE
        );

        card.setStyle(
                "-fx-background-color:white;" +
                        "-fx-background-radius:14;" +
                        "-fx-border-color:#EEE8E2;" +
                        "-fx-border-radius:14;" +
                        "-fx-border-width:1;" +
                        "-fx-effect:dropshadow(gaussian," +
                        "rgba(0,0,0,0.06),10,0.1,0,3);"
        );

        return card;
    }

    // =========================================================
    // FORM SECTION
    // =========================================================

    private HBox createFormSection() {

        Label formTitle =
                new Label(
                        "Course Information"
                );

        formTitle.setStyle(
                "-fx-text-fill:" + TEXT_DARK + ";" +
                        "-fx-font-size:19px;" +
                        "-fx-font-weight:bold;"
        );

        Label formSubtitle =
                new Label(
                        "Enter course details and academic assignments"
                );

        formSubtitle.setStyle(
                "-fx-text-fill:#777777;" +
                        "-fx-font-size:12px;"
        );

        courseCodeField =
                createStyledTextField(
                        "Example: ICT1201"
                );

        courseNameField =
                createStyledTextField(
                        "Example: Programming Fundamentals"
                );

        creditsComboBox =
                new ComboBox<>();

        creditsComboBox.getItems().addAll(
                1,
                2,
                3,
                4,
                5,
                6
        );

        creditsComboBox.setPromptText(
                "Select credits"
        );

        styleComboBox(
                creditsComboBox
        );

        departmentComboBox =
                new ComboBox<>();

        departmentComboBox.setPromptText(
                "Select a department"
        );

        styleComboBox(
                departmentComboBox
        );

        lecturerComboBox =
                new ComboBox<>();

        lecturerComboBox.setPromptText(
                "Select lecturer (optional)"
        );

        styleComboBox(
                lecturerComboBox
        );

        GridPane formGrid =
                new GridPane();

        formGrid.setHgap(18);
        formGrid.setVgap(12);

        addFormField(
                formGrid,
                "Course Code",
                courseCodeField,
                0,
                0
        );

        addFormField(
                formGrid,
                "Course Name",
                courseNameField,
                1,
                0
        );

        addFormField(
                formGrid,
                "Credits",
                creditsComboBox,
                0,
                1
        );

        addFormField(
                formGrid,
                "Department",
                departmentComboBox,
                1,
                1
        );

        addFormField(
                formGrid,
                "Lecturer (Optional)",
                lecturerComboBox,
                0,
                2
        );

        HBox buttonBox =
                createButtonBox();

        VBox formCard =
                new VBox(
                        14,
                        formTitle,
                        formSubtitle,
                        formGrid,
                        buttonBox
                );

        formCard.setPadding(
                new Insets(25)
        );

        formCard.setPrefWidth(710);

        formCard.setStyle(
                "-fx-background-color:white;" +
                        "-fx-background-radius:16;" +
                        "-fx-border-color:#EEE8E2;" +
                        "-fx-border-radius:16;" +
                        "-fx-border-width:1;" +
                        "-fx-effect:dropshadow(gaussian," +
                        "rgba(0,0,0,0.08),14,0.1,0,5);"
        );

        VBox informationCard =
                createInformationCard();

        HBox formSection =
                new HBox(
                        20,
                        formCard,
                        informationCard
                );

        HBox.setHgrow(
                formCard,
                Priority.ALWAYS
        );

        return formSection;
    }

    private TextField createStyledTextField(
            String promptText
    ) {

        TextField textField =
                new TextField();

        textField.setPromptText(
                promptText
        );

        textField.setPrefHeight(43);

        textField.setStyle(
                "-fx-background-color:#FAFAFA;" +
                        "-fx-border-color:#DED9D5;" +
                        "-fx-border-radius:7;" +
                        "-fx-background-radius:7;" +
                        "-fx-padding:0 12 0 12;" +
                        "-fx-font-size:13px;"
        );

        return textField;
    }

    private void styleComboBox(
            ComboBox<?> comboBox
    ) {

        comboBox.setPrefHeight(43);

        comboBox.setMaxWidth(
                Double.MAX_VALUE
        );

        comboBox.setStyle(
                "-fx-background-color:#FAFAFA;" +
                        "-fx-border-color:#DED9D5;" +
                        "-fx-border-radius:7;" +
                        "-fx-background-radius:7;" +
                        "-fx-font-size:13px;"
        );
    }

    private void addFormField(
            GridPane grid,
            String labelText,
            javafx.scene.Node field,
            int column,
            int row
    ) {

        Label label =
                new Label(labelText);

        label.setStyle(
                "-fx-text-fill:#333333;" +
                        "-fx-font-size:13px;" +
                        "-fx-font-weight:bold;"
        );

        VBox fieldBox =
                new VBox(
                        7,
                        label,
                        field
                );

        fieldBox.setMaxWidth(
                Double.MAX_VALUE
        );

        GridPane.setHgrow(
                fieldBox,
                Priority.ALWAYS
        );

        grid.add(
                fieldBox,
                column,
                row
        );
    }

    private HBox createButtonBox() {

        addButton =
                new Button(
                        "Add Course"
                );

        updateButton =
                new Button(
                        "Update"
                );

        deleteButton =
                new Button(
                        "Delete"
                );

        clearButton =
                new Button(
                        "Clear"
                );

        stylePrimaryButton(
                addButton
        );

        styleGoldButton(
                updateButton
        );

        styleDeleteButton(
                deleteButton
        );

        styleSecondaryButton(
                clearButton
        );

        HBox buttonBox =
                new HBox(
                        10,
                        addButton,
                        updateButton,
                        deleteButton,
                        clearButton
                );

        buttonBox.setAlignment(
                Pos.CENTER_LEFT
        );

        return buttonBox;
    }

    private VBox createInformationCard() {

        Label icon =
                new Label("C");

        icon.setAlignment(
                Pos.CENTER
        );

        icon.setMinSize(
                58,
                58
        );

        icon.setMaxSize(
                58,
                58
        );

        icon.setStyle(
                "-fx-background-color:" + LIGHT_GOLD + ";" +
                        "-fx-background-radius:15;" +
                        "-fx-text-fill:" + DARK_MAROON + ";" +
                        "-fx-font-size:22px;" +
                        "-fx-font-weight:bold;"
        );

        Label title =
                new Label(
                        "Course Module"
                );

        title.setStyle(
                "-fx-text-fill:" + TEXT_DARK + ";" +
                        "-fx-font-size:17px;" +
                        "-fx-font-weight:bold;"
        );

        Label information =
                new Label(
                        "• Course codes must be unique.\n\n" +
                                "• Every course must belong to a department.\n\n" +
                                "• Lecturer assignment is optional.\n\n" +
                                "• Credits must be selected from 1 to 6.\n\n" +
                                "• If a lecturer is deleted, the course\n" +
                                "  will remain without an assigned lecturer."
                );

        information.setWrapText(true);

        information.setStyle(
                "-fx-text-fill:#666666;" +
                        "-fx-font-size:12px;" +
                        "-fx-line-spacing:3;"
        );

        VBox informationCard =
                new VBox(
                        14,
                        icon,
                        title,
                        information
                );

        informationCard.setPadding(
                new Insets(25)
        );

        informationCard.setPrefWidth(320);

        informationCard.setStyle(
                "-fx-background-color:#FFFDF8;" +
                        "-fx-background-radius:16;" +
                        "-fx-border-color:#E8D59A;" +
                        "-fx-border-radius:16;" +
                        "-fx-border-width:1;"
        );

        return informationCard;
    }     // =========================================================
    // TABLE SECTION
    // =========================================================

    private VBox createTableSection() {

        Label tableTitle =
                new Label("Course Records");

        tableTitle.setStyle(
                "-fx-text-fill:" + TEXT_DARK + ";" +
                        "-fx-font-size:19px;" +
                        "-fx-font-weight:bold;"
        );

        Label tableSubtitle =
                new Label(
                        "Search, update and manage course records"
                );

        tableSubtitle.setStyle(
                "-fx-text-fill:#777777;" +
                        "-fx-font-size:12px;"
        );

        searchField =
                createStyledTextField(
                        "Search course..."
                );

        courseTable =
                new TableView<>();

        courseTable.setPrefHeight(430);

        courseTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        TableColumn<Course,Integer> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("courseId")
        );

        TableColumn<Course,String> codeColumn =
                new TableColumn<>("Course Code");

        codeColumn.setCellValueFactory(
                new PropertyValueFactory<>("courseCode")
        );

        TableColumn<Course,String> nameColumn =
                new TableColumn<>("Course Name");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("courseName")
        );

        TableColumn<Course,Integer> creditsColumn =
                new TableColumn<>("Credits");

        creditsColumn.setCellValueFactory(
                new PropertyValueFactory<>("credits")
        );

        TableColumn<Course,String> departmentColumn =
                new TableColumn<>("Department");

        departmentColumn.setCellValueFactory(
                new PropertyValueFactory<>("departmentName")
        );

        TableColumn<Course,String> lecturerColumn =
                new TableColumn<>("Lecturer");

        lecturerColumn.setCellValueFactory(
                new PropertyValueFactory<>("lecturerName")
        );

        courseTable.getColumns().addAll(
                idColumn,
                codeColumn,
                nameColumn,
                creditsColumn,
                departmentColumn,
                lecturerColumn
        );

        VBox tableCard =
                new VBox(
                        10,
                        tableTitle,
                        tableSubtitle,
                        searchField,
                        courseTable
                );

        tableCard.setPadding(
                new Insets(25)
        );

        tableCard.setStyle(
                "-fx-background-color:white;" +
                        "-fx-background-radius:16;" +
                        "-fx-border-color:#EEE8E2;" +
                        "-fx-border-radius:16;" +
                        "-fx-border-width:1;" +
                        "-fx-effect:dropshadow(gaussian," +
                        "rgba(0,0,0,0.08),14,0.1,0,5);"
        );

        return tableCard;
    }

    // =========================================================
    // BUTTON STYLES
    // =========================================================

    private void stylePrimaryButton(Button button){

        button.setPrefHeight(40);
        button.setCursor(Cursor.HAND);

        button.setStyle(
                "-fx-background-color:" + MAROON + ";" +
                        "-fx-text-fill:white;" +
                        "-fx-background-radius:8;" +
                        "-fx-font-weight:bold;"
        );
    }

    private void styleGoldButton(Button button){

        button.setPrefHeight(40);
        button.setCursor(Cursor.HAND);

        button.setStyle(
                "-fx-background-color:" + GOLD + ";" +
                        "-fx-text-fill:" + DARK_MAROON + ";" +
                        "-fx-background-radius:8;" +
                        "-fx-font-weight:bold;"
        );
    }

    private void styleDeleteButton(Button button){

        button.setPrefHeight(40);
        button.setCursor(Cursor.HAND);

        button.setStyle(
                "-fx-background-color:#C62828;" +
                        "-fx-text-fill:white;" +
                        "-fx-background-radius:8;" +
                        "-fx-font-weight:bold;"
        );
    }

    private void styleSecondaryButton(Button button){

        button.setPrefHeight(40);
        button.setCursor(Cursor.HAND);

        button.setStyle(
                "-fx-background-color:white;" +
                        "-fx-border-color:#BBBBBB;" +
                        "-fx-border-radius:8;" +
                        "-fx-background-radius:8;" +
                        "-fx-font-weight:bold;"
        );
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public Stage getStage() {
        return stage;
    }

    public TextField getCourseCodeField() {
        return courseCodeField;
    }

    public TextField getCourseNameField() {
        return courseNameField;
    }

    public ComboBox<Integer> getCreditsComboBox() {
        return creditsComboBox;
    }

    public ComboBox<Department> getDepartmentComboBox() {
        return departmentComboBox;
    }

    public ComboBox<Lecturer> getLecturerComboBox() {
        return lecturerComboBox;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public TableView<Course> getCourseTable() {
        return courseTable;
    }

    public Label getTotalCoursesValue() {
        return totalCoursesValue;
    }

    public Label getDatabaseStatusValue() {
        return databaseStatusValue;
    }

    public Label getSelectedCourseValue() {
        return selectedCourseValue;
    }

    public Label getSystemStatusValue() {
        return systemStatusValue;
    }

}