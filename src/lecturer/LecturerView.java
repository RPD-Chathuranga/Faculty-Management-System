package lecturer;

import department.Department;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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

public class LecturerView {

    private final Stage stage;

    private TextField fullNameField;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField emailField;
    private TextField phoneField;
    private TextField employeeNumberField;
    private TextField specializationField;
    private TextField searchField;

    private ComboBox<Department> departmentComboBox;

    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Button clearButton;
    private Button backButton;

    private TableView<Lecturer> lecturerTable;

    private Label totalLecturersValue;
    private Label databaseStatusValue;
    private Label selectedLecturerValue;
    private Label systemStatusValue;

    private final String MAROON = "#7A0019";
    private final String DARK_MAROON = "#4A0010";
    private final String GOLD = "#D4AF37";
    private final String LIGHT_GOLD = "#F5E7B2";
    private final String BACKGROUND = "#F7F5F2";
    private final String TEXT_DARK = "#252525";

    public LecturerView(Stage stage) {
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
                "Lecturer Management - Faculty Management System"
        );

        stage.setScene(scene);
        stage.setMinWidth(1120);
        stage.setMinHeight(720);
        stage.centerOnScreen();

        new LecturerController(
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
                        "Lecturer Management"
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
                        "Create lecturer accounts and\n" +
                                "manage academic lecturer records."
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
                        "Lecturer Management"
                );

        titleLabel.setStyle(
                "-fx-text-fill:" + TEXT_DARK + ";" +
                        "-fx-font-size:28px;" +
                        "-fx-font-weight:bold;"
        );

        Label subtitleLabel =
                new Label(
                        "Create accounts and manage lecturer academic details"
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

        totalLecturersValue =
                new Label("0");

        databaseStatusValue =
                new Label("Connected");

        selectedLecturerValue =
                new Label("None");

        systemStatusValue =
                new Label("Ready");

        VBox totalCard =
                createStatisticCard(
                        "Total Lecturers",
                        totalLecturersValue,
                        "L"
                );

        VBox databaseCard =
                createStatisticCard(
                        "Database Status",
                        databaseStatusValue,
                        "DB"
                );

        VBox selectedCard =
                createStatisticCard(
                        "Selected Lecturer",
                        selectedLecturerValue,
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
                        "Lecturer Account and Academic Information"
                );

        formTitle.setStyle(
                "-fx-text-fill:" + TEXT_DARK + ";" +
                        "-fx-font-size:19px;" +
                        "-fx-font-weight:bold;"
        );

        Label formSubtitle =
                new Label(
                        "Create the login account and lecturer record together"
                );

        formSubtitle.setStyle(
                "-fx-text-fill:#777777;" +
                        "-fx-font-size:12px;"
        );

        fullNameField =
                createStyledTextField(
                        "Enter lecturer full name"
                );

        usernameField =
                createStyledTextField(
                        "Enter login username"
                );

        passwordField =
                new PasswordField();

        passwordField.setPromptText(
                "Enter password"
        );

        passwordField.setPrefHeight(43);

        passwordField.setStyle(
                "-fx-background-color:#FAFAFA;" +
                        "-fx-border-color:#DED9D5;" +
                        "-fx-border-radius:7;" +
                        "-fx-background-radius:7;" +
                        "-fx-padding:0 12 0 12;" +
                        "-fx-font-size:13px;"
        );

        emailField =
                createStyledTextField(
                        "Enter email address"
                );

        phoneField =
                createStyledTextField(
                        "Enter phone number"
                );

        employeeNumberField =
                createStyledTextField(
                        "Example: LEC001"
                );

        specializationField =
                createStyledTextField(
                        "Example: Artificial Intelligence"
                );

        departmentComboBox =
                new ComboBox<>();

        departmentComboBox.setPromptText(
                "Select a department"
        );

        departmentComboBox.setPrefHeight(43);
        departmentComboBox.setMaxWidth(
                Double.MAX_VALUE
        );

        departmentComboBox.setStyle(
                "-fx-background-color:#FAFAFA;" +
                        "-fx-border-color:#DED9D5;" +
                        "-fx-border-radius:7;" +
                        "-fx-background-radius:7;" +
                        "-fx-font-size:13px;"
        );

        GridPane formGrid =
                new GridPane();

        formGrid.setHgap(18);
        formGrid.setVgap(12);

        addFormField(
                formGrid,
                "Full Name",
                fullNameField,
                0,
                0
        );

        addFormField(
                formGrid,
                "Username",
                usernameField,
                1,
                0
        );

        addFormField(
                formGrid,
                "Password",
                passwordField,
                0,
                1
        );

        addFormField(
                formGrid,
                "Email",
                emailField,
                1,
                1
        );

        addFormField(
                formGrid,
                "Phone",
                phoneField,
                0,
                2
        );

        addFormField(
                formGrid,
                "Employee Number",
                employeeNumberField,
                1,
                2
        );

        addFormField(
                formGrid,
                "Specialization",
                specializationField,
                0,
                3
        );

        addFormField(
                formGrid,
                "Department",
                departmentComboBox,
                1,
                3
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
                        "Add Lecturer"
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

        stylePrimaryButton(addButton);
        styleGoldButton(updateButton);
        styleDeleteButton(deleteButton);
        styleSecondaryButton(clearButton);

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
                new Label("L");

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
                        "Lecturer Module"
                );

        title.setStyle(
                "-fx-text-fill:" + TEXT_DARK + ";" +
                        "-fx-font-size:17px;" +
                        "-fx-font-weight:bold;"
        );

        Label information =
                new Label(
                        "• A USERS account is created automatically.\n\n" +
                                "• The role is automatically set to LECTURER.\n\n" +
                                "• Username, email and employee number\n" +
                                "  must be unique.\n\n" +
                                "• Leave the password empty during an\n" +
                                "  update to keep the existing password.\n\n" +
                                "• Every lecturer must belong to a department."
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
                new Label("Lecturer Records");

        tableTitle.setStyle(
                "-fx-text-fill:" + TEXT_DARK + ";" +
                        "-fx-font-size:19px;" +
                        "-fx-font-weight:bold;"
        );

        Label tableSubtitle =
                new Label(
                        "Search, update and manage lecturer records"
                );

        tableSubtitle.setStyle(
                "-fx-text-fill:#777777;" +
                        "-fx-font-size:12px;"
        );

        searchField =
                createStyledTextField(
                        "Search lecturer..."
                );

        lecturerTable =
                new TableView<>();

        lecturerTable.setPrefHeight(430);

        lecturerTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        TableColumn<Lecturer,Integer> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("lecturerId")
        );

        idColumn.setPrefWidth(60);

        TableColumn<Lecturer,String> employeeColumn =
                new TableColumn<>("Employee No");

        employeeColumn.setCellValueFactory(
                new PropertyValueFactory<>("employeeNumber")
        );

        employeeColumn.setPrefWidth(120);

        TableColumn<Lecturer,String> nameColumn =
                new TableColumn<>("Full Name");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("fullName")
        );

        nameColumn.setPrefWidth(180);

        TableColumn<Lecturer,String> usernameColumn =
                new TableColumn<>("Username");

        usernameColumn.setCellValueFactory(
                new PropertyValueFactory<>("username")
        );

        usernameColumn.setPrefWidth(130);

        TableColumn<Lecturer,String> emailColumn =
                new TableColumn<>("Email");

        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );

        emailColumn.setPrefWidth(180);

        TableColumn<Lecturer,String> phoneColumn =
                new TableColumn<>("Phone");

        phoneColumn.setCellValueFactory(
                new PropertyValueFactory<>("phone")
        );

        phoneColumn.setPrefWidth(120);

        TableColumn<Lecturer,String> departmentColumn =
                new TableColumn<>("Department");

        departmentColumn.setCellValueFactory(
                new PropertyValueFactory<>("departmentName")
        );

        departmentColumn.setPrefWidth(170);

        TableColumn<Lecturer,String> specializationColumn =
                new TableColumn<>("Specialization");

        specializationColumn.setCellValueFactory(
                new PropertyValueFactory<>("specialization")
        );

        specializationColumn.setPrefWidth(180);

        lecturerTable.getColumns().addAll(

                idColumn,
                employeeColumn,
                nameColumn,
                usernameColumn,
                emailColumn,
                phoneColumn,
                departmentColumn,
                specializationColumn

        );

        VBox tableCard =
                new VBox(
                        10,
                        tableTitle,
                        tableSubtitle,
                        searchField,
                        lecturerTable
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

    public TextField getFullNameField() {
        return fullNameField;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getPhoneField() {
        return phoneField;
    }

    public TextField getEmployeeNumberField() {
        return employeeNumberField;
    }

    public TextField getSpecializationField() {
        return specializationField;
    }

    public ComboBox<Department> getDepartmentComboBox() {
        return departmentComboBox;
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

    public TableView<Lecturer> getLecturerTable() {
        return lecturerTable;
    }

    public Label getTotalLecturersValue() {
        return totalLecturersValue;
    }

    public Label getDatabaseStatusValue() {
        return databaseStatusValue;
    }

    public Label getSelectedLecturerValue() {
        return selectedLecturerValue;
    }

    public Label getSystemStatusValue() {
        return systemStatusValue;
    }

}