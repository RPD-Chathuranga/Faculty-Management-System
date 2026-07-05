package authentication;

import java.util.Scanner;

public class LoginController {

    private LoginService loginService = new LoginService();

    public void startLogin() {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== FMS LOGIN ===");

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        boolean success = loginService.authenticate(username, password);

        if (success) {

            System.out.println("\nLogin Successful!");
            System.out.println("Welcome " + UserSession.fullName);
            System.out.println("Role: " + UserSession.role);

            // NEXT STEP AFTER AUTHENTICATION
            // (we will build dashboard next)
            // DashboardController.showMenu();

        } else {
            System.out.println("\nInvalid credentials!");
        }
    }
}