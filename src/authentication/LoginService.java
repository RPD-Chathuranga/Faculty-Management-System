package authentication;

import common.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginService {

    public boolean authenticate(String username, String password) {

        String sql = "SELECT * FROM USERS WHERE username = ? AND password = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                // store session
                UserSession.userId = rs.getInt("user_id");
                UserSession.username = rs.getString("username");
                UserSession.fullName = rs.getString("full_name");
                UserSession.role = rs.getString("role");

                return true;
            }

        } catch (Exception e) {
            System.out.println("Authentication Error: " + e.getMessage());
        }

        return false;
    }
}