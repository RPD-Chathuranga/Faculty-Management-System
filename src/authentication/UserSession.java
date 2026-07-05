package authentication;

public class UserSession {

    public static int userId;
    public static String username;
    public static String fullName;
    public static String role;

    public static boolean isLoggedIn() {
        return userId != 0;
    }

    public static void clearSession() {
        userId = 0;
        username = null;
        fullName = null;
        role = null;
    }
}