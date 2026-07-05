package app;

import authentication.LoginController;

public class Main {
    public static void main(String[] args) {

        LoginController loginController = new LoginController();
        loginController.startLogin();
    }
}