package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/faculty_management_system";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "RPD48677";

    private static Connection connection;

    public static Connection getConnection() {

        try {

            if (connection == null || connection.isClosed()) {

                connection = DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD
                );

                System.out.println("Database Connected Successfully!");
            }

        } catch (SQLException e) {

            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }

        return connection;
    }
}