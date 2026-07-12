package authentication;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import dashboard.DashboardView;


public class LoginView extends Application {


    @Override
    public void start(Stage stage) {


        LoginController controller = new LoginController();


        // ================= LEFT BRANDING PANEL =================


        VBox brandingPanel = new VBox(25);

        brandingPanel.setAlignment(Pos.CENTER);
        brandingPanel.setPrefWidth(320);

        brandingPanel.setStyle(
                "-fx-background-color:#990000;"
        );



        Label university =
                new Label(
                        "UNIVERSITY OF KELANIYA"
                );


        university.setStyle(
                "-fx-text-fill:white;" +
                        "-fx-font-size:22px;" +
                        "-fx-font-weight:bold;"
        );



        Label goldLine =
                new Label(
                        "━━━━━━━━━━━━"
                );


        goldLine.setStyle(
                "-fx-text-fill:#D4AF37;" +
                        "-fx-font-size:20px;"
        );



        Label faculty =
                new Label(
                        "Faculty of Computing and Technology"
                );


        faculty.setAlignment(Pos.CENTER);


        faculty.setStyle(
                "-fx-text-fill:#D4AF37;" +
                        "-fx-font-size:17px;" +
                        "-fx-font-weight:bold;"
        );



        Label system =
                new Label(
                        "FACULTY MANAGEMENT SYSTEM\n\n" +
                                "Administrative Portal"
                );


        system.setAlignment(Pos.CENTER);


        system.setStyle(
                "-fx-text-fill:white;" +
                        "-fx-font-size:15px;"
        );



        brandingPanel.getChildren()
                .addAll(
                        university,
                        goldLine,
                        faculty,
                        system
                );





        // ================= LOGIN CARD =================



        Label title =
                new Label(
                        "Welcome Back"
                );


        title.setStyle(
                "-fx-text-fill:#990000;" +
                        "-fx-font-size:28px;" +
                        "-fx-font-weight:bold;"
        );



        Label subtitle =
                new Label(
                        "Sign in to continue"
                );


        subtitle.setStyle(
                "-fx-text-fill:#666666;" +
                        "-fx-font-size:14px;"
        );




        Label usernameLabel =
                new Label(
                        "Username"
                );


        usernameLabel.setStyle(
                "-fx-text-fill:#333333;" +
                        "-fx-font-weight:bold;"
        );



        TextField usernameField =
                new TextField();


        usernameField.setPromptText(
                "Enter username"
        );


        usernameField.setPrefHeight(42);




        Label passwordLabel =
                new Label(
                        "Password"
                );


        passwordLabel.setStyle(
                "-fx-text-fill:#333333;" +
                        "-fx-font-weight:bold;"
        );



        PasswordField passwordField =
                new PasswordField();


        passwordField.setPromptText(
                "Enter password"
        );


        passwordField.setPrefHeight(42);





        Button loginButton =
                new Button(
                        "LOGIN"
                );


        loginButton.setPrefWidth(260);

        loginButton.setPrefHeight(45);



        loginButton.setStyle(
                "-fx-background-color:#990000;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-size:15px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-background-radius:25;"
        );



        loginButton.setOnMouseEntered(e ->

                loginButton.setStyle(
                        "-fx-background-color:#D4AF37;" +
                                "-fx-text-fill:#990000;" +
                                "-fx-font-size:15px;" +
                                "-fx-font-weight:bold;" +
                                "-fx-background-radius:25;"
                )
        );



        loginButton.setOnMouseExited(e ->

                loginButton.setStyle(
                        "-fx-background-color:#990000;" +
                                "-fx-text-fill:white;" +
                                "-fx-font-size:15px;" +
                                "-fx-font-weight:bold;" +
                                "-fx-background-radius:25;"
                )
        );





        // ================= LOGIN LOGIC =================



        loginButton.setOnAction(e -> {


            String username =
                    usernameField.getText().trim();


            String password =
                    passwordField.getText().trim();



            if(username.isEmpty() ||
                    password.isEmpty()){


                Alert alert =
                        new Alert(
                                Alert.AlertType.WARNING
                        );


                alert.setTitle("Warning");
                alert.setHeaderText(null);

                alert.setContentText(
                        "Please enter username and password."
                );


                alert.showAndWait();

                return;
            }




            boolean success =
                    controller.login(
                            username,
                            password
                    );



            if (success) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.INFORMATION
                        );

                alert.setTitle(
                        "Login Successful"
                );

                alert.setHeaderText(null);

                alert.setContentText(
                        "Welcome "
                                + UserSession.fullName
                                + "\nRole: "
                                + UserSession.role
                );

                alert.showAndWait();

                DashboardView dashboardView =
                        new DashboardView(stage);

                dashboardView.show();
            }

            else{


                Alert alert =
                        new Alert(
                                Alert.AlertType.ERROR
                        );


                alert.setTitle(
                        "Login Failed"
                );


                alert.setHeaderText(null);


                alert.setContentText(
                        "Invalid Username or Password."
                );


                alert.showAndWait();

            }


        });





        // ================= LOGIN CARD =================



        VBox loginCard =
                new VBox(15);


        loginCard.setAlignment(
                Pos.CENTER
        );


        loginCard.setPadding(
                new Insets(45)
        );


        loginCard.setPrefWidth(380);



        loginCard.setStyle(
                "-fx-background-color:white;" +
                        "-fx-background-radius:20;" +
                        "-fx-effect:dropshadow(three-pass-box,rgba(0,0,0,0.15),15,0,0,5);"
        );



        loginCard.getChildren()
                .addAll(
                        title,
                        subtitle,
                        usernameLabel,
                        usernameField,
                        passwordLabel,
                        passwordField,
                        loginButton
                );






        // ================= MAIN LAYOUT =================



        HBox root =
                new HBox();


        root.setAlignment(
                Pos.CENTER
        );


        root.setSpacing(70);


        root.setStyle(
                "-fx-background-color:#F5F5F5;"
        );



        root.getChildren()
                .addAll(
                        brandingPanel,
                        loginCard
                );





        Scene scene =
                new Scene(
                        root,
                        950,
                        600
                );



        stage.setTitle(
                "Faculty Management System"
        );


        stage.setScene(scene);

        stage.setResizable(false);

        stage.centerOnScreen();

        stage.show();

    }




    public static void main(String[] args){

        launch(args);

    }

}