package dashboard;

import authentication.UserSession;

public class DashboardService {

    public String getLoggedInUserName() {

        if (UserSession.fullName == null
                || UserSession.fullName.trim().isEmpty()) {

            return "System User";
        }

        return UserSession.fullName;
    }

    public String getLoggedInUserRole() {

        if (UserSession.role == null
                || UserSession.role.trim().isEmpty()) {

            return "USER";
        }

        return UserSession.role;
    }

    public boolean isAdmin() {

        return "ADMIN".equalsIgnoreCase(
                UserSession.role
        );
    }

    public boolean isLecturer() {

        return "LECTURER".equalsIgnoreCase(
                UserSession.role
        );
    }

    public boolean isStudent() {

        return "STUDENT".equalsIgnoreCase(
                UserSession.role
        );
    }

    public boolean isLoggedIn() {

        return UserSession.isLoggedIn();
    }

    public void logout() {

        UserSession.clearSession();
    }
}