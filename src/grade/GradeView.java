package grade;

import course.Course;
import student.Student;
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

public class GradeView {

    private final Stage stage;

    private ComboBox<Student> studentComboBox;
    private ComboBox<Course> courseComboBox;

    private TextField marksField;
    private TextField letterGradeField;
    private TextField searchField;

    private Button calculateButton;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Button clearButton;
    private Button backButton;

    private TableView<Grade> gradeTable;

    private Label totalGradesValue;
    private Label databaseStatusValue;
    private Label selectedGradeValue;
    private Label systemStatusValue;

    private final String MAROON = "#7A0019";
    private final String DARK_MAROON = "#4A0010";
    private final String GOLD = "#D4AF37";
    private final String LIGHT_GOLD = "#F5E7B2";
    private final String BACKGROUND = "#F7F5F2";
    private final String TEXT_DARK = "#252525";

    public GradeView(Stage stage) {

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
                "Grade Management - Faculty Management System"
        );

        stage.setScene(scene);
        stage.setMinWidth(1120);
        stage.setMinHeight(720);
        stage.centerOnScreen();

        new GradeController(
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
                        "Grade Management"
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
                        "Record, calculate and manage\n" +
                                "student course grades."
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
                        "Grade Management"
                );

        titleLabel.setStyle(
                "-fx-text-fill:" + TEXT_DARK + ";" +
                        "-fx-font-size:28px;" +
                        "-fx-font-weight:bold;"
        );

        Label subtitleLabel =
                new Label(
                        "Record marks and calculate student letter grades"
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

        totalGradesValue =
                new Label("0");

        databaseStatusValue =
                new Label("Connected");

        selectedGradeValue =
                new Label("None");

        systemStatusValue =
                new Label("Ready");

        VBox totalCard =
                createStatisticCard(
                        "Total Grades",
                        totalGradesValue,
                        "G"
                );

        VBox databaseCard =
                createStatisticCard(
                        "Database Status",
                        databaseStatusValue,
                        "DB"
                );

        VBox selectedCard =
                createStatisticCard(
                        "Selected Grade",
                        selectedGradeValue,
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
                        "Grade Information"
                );

        formTitle.setStyle(
                "-fx-text-fill:" + TEXT_DARK + ";" +
                        "-fx-font-size:19px;" +
                        "-fx-font-weight:bold;"
        );

        Label formSubtitle =
                new Label(
                        "Select a student and course, then enter marks"
                );

        formSubtitle.setStyle(
                "-fx-text-fill:#777777;" +
                        "-fx-font-size:12px;"
        );

        studentComboBox =
                new ComboBox<>();

        studentComboBox.setPromptText(
                "Select a student"
        );

        styleComboBox(
                studentComboBox
        );

        courseComboBox =
                new ComboBox<>();

        courseComboBox.setPromptText(
                "Select a course"
        );

        styleComboBox(
                courseComboBox
        );

        marksField =
                createStyledTextField(
                        "Enter marks from 0 to 100"
                );

        letterGradeField =
                createStyledTextField(
                        "Calculated automatically"
                );

        letterGradeField.setEditable(false);

        letterGradeField.setStyle(
                letterGradeField.getStyle()
                        + "-fx-font-weight:bold;"
                        + "-fx-text-fill:"
                        + DARK_MAROON
                        + ";"
        );

        calculateButton =
                new Button(
                        "Calculate Grade"
                );

        styleGoldButton(
                calculateButton
        );

        GridPane formGrid =
                new GridPane();

        formGrid.setHgap(18);
        formGrid.setVgap(12);

        addFormField(
                formGrid,
                "Student",
                studentComboBox,
                0,
                0
        );

        addFormField(
                formGrid,
                "Course",
                courseComboBox,
                1,
                0
        );

        addFormField(
                formGrid,
                "Marks",
                marksField,
                0,
                1
        );

        addFormField(
                formGrid,
                "Letter Grade",
                letterGradeField,
                1,
                1
        );

        GridPane.setColumnSpan(
                calculateButton,
                2
        );

        formGrid.add(
                calculateButton,
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
                        "Add Grade"
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
                new Label("G");

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
                        "Grade Module"
                );

        title.setStyle(
                "-fx-text-fill:" + TEXT_DARK + ";" +
                        "-fx-font-size:17px;" +
                        "-fx-font-weight:bold;"
        );

        Label information =
                new Label(
                        "• Select an existing student and course.\n\n" +
                                "• Marks must be between 0 and 100.\n\n" +
                                "• Letter grades are calculated automatically.\n\n" +
                                "• Only one grade is allowed for each\n" +
                                "  student and course combination.\n\n" +
                                "• Select a row before updating or deleting."
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
    }    // =========================================================
    // TABLE SECTION
    // =========================================================

    private VBox createTableSection() {

        Label tableTitle =
                new Label("Grade Records");

        tableTitle.setStyle(
                "-fx-text-fill:" + TEXT_DARK + ";" +
                        "-fx-font-size:19px;" +
                        "-fx-font-weight:bold;"
        );

        Label tableSubtitle =
                new Label(
                        "Search, update and manage student grades"
                );

        tableSubtitle.setStyle(
                "-fx-text-fill:#777777;" +
                        "-fx-font-size:12px;"
        );

        searchField =
                new TextField();

        searchField.setPromptText(
                "Search grades..."
        );

        searchField.setPrefHeight(42);

        searchField.setStyle(
                "-fx-background-color:#FAFAFA;" +
                        "-fx-border-color:#DED9D5;" +
                        "-fx-border-radius:7;" +
                        "-fx-background-radius:7;" +
                        "-fx-padding:0 12 0 12;"
        );

        gradeTable =
                new TableView<>();

        gradeTable.setPrefHeight(430);

        gradeTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        TableColumn<Grade,Integer> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("gradeId")
        );

        TableColumn<Grade,String> studentColumn =
                new TableColumn<>("Student");

        studentColumn.setCellValueFactory(
                new PropertyValueFactory<>("studentDisplayName")
        );

        TableColumn<Grade,String> courseColumn =
                new TableColumn<>("Course");

        courseColumn.setCellValueFactory(
                new PropertyValueFactory<>("courseDisplayName")
        );

        TableColumn<Grade,Double> marksColumn =
                new TableColumn<>("Marks");

        marksColumn.setCellValueFactory(
                new PropertyValueFactory<>("marks")
        );

        TableColumn<Grade,String> gradeColumn =
                new TableColumn<>("Letter Grade");

        gradeColumn.setCellValueFactory(
                new PropertyValueFactory<>("letterGrade")
        );

        gradeTable.getColumns().addAll(
                idColumn,
                studentColumn,
                courseColumn,
                marksColumn,
                gradeColumn
        );

        VBox tableCard =
                new VBox(
                        10,
                        tableTitle,
                        tableSubtitle,
                        searchField,
                        gradeTable
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

    private void stylePrimaryButton(Button button) {

        button.setPrefHeight(40);
        button.setCursor(Cursor.HAND);

        button.setStyle(
                "-fx-background-color:" + MAROON + ";" +
                        "-fx-text-fill:white;" +
                        "-fx-background-radius:8;" +
                        "-fx-font-weight:bold;"
        );
    }

    private void styleGoldButton(Button button) {

        button.setPrefHeight(40);
        button.setCursor(Cursor.HAND);

        button.setStyle(
                "-fx-background-color:" + GOLD + ";" +
                        "-fx-text-fill:" + DARK_MAROON + ";" +
                        "-fx-background-radius:8;" +
                        "-fx-font-weight:bold;"
        );
    }

    private void styleDeleteButton(Button button) {

        button.setPrefHeight(40);
        button.setCursor(Cursor.HAND);

        button.setStyle(
                "-fx-background-color:#C62828;" +
                        "-fx-text-fill:white;" +
                        "-fx-background-radius:8;" +
                        "-fx-font-weight:bold;"
        );
    }

    private void styleSecondaryButton(Button button) {

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

    public ComboBox<Student> getStudentComboBox() {
        return studentComboBox;
    }

    public ComboBox<Course> getCourseComboBox() {
        return courseComboBox;
    }

    public TextField getMarksField() {
        return marksField;
    }

    public TextField getLetterGradeField() {
        return letterGradeField;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getCalculateButton() {
        return calculateButton;
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

    public TableView<Grade> getGradeTable() {
        return gradeTable;
    }

    public Label getTotalGradesValue() {
        return totalGradesValue;
    }

    public Label getDatabaseStatusValue() {
        return databaseStatusValue;
    }

    public Label getSelectedGradeValue() {
        return selectedGradeValue;
    }

    public Label getSystemStatusValue() {
        return systemStatusValue;
    }

}