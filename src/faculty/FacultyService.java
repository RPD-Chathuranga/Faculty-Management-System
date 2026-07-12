package faculty;

import common.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacultyService {

    // =========================================================
    // ADD FACULTY
    // =========================================================

    public boolean addFaculty(
            String facultyName,
            String description
    ) {

        if (!isValidFacultyName(facultyName)) {
            return false;
        }

        String cleanFacultyName =
                facultyName.trim();

        String cleanDescription =
                description == null
                        ? ""
                        : description.trim();

        if (facultyNameExists(cleanFacultyName, 0)) {
            return false;
        }

        String sql =
                "INSERT INTO FACULTY " +
                        "(faculty_name, description) " +
                        "VALUES (?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    cleanFacultyName
            );

            statement.setString(
                    2,
                    cleanDescription
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error adding faculty: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // GET ALL FACULTIES
    // =========================================================

    public List<Faculty> getAllFaculties() {

        List<Faculty> faculties =
                new ArrayList<>();

        String sql =
                "SELECT faculty_id, faculty_name, description " +
                        "FROM FACULTY " +
                        "ORDER BY faculty_id DESC";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Faculty faculty =
                        new Faculty(
                                resultSet.getInt(
                                        "faculty_id"
                                ),
                                resultSet.getString(
                                        "faculty_name"
                                ),
                                resultSet.getString(
                                        "description"
                                )
                        );

                faculties.add(faculty);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading faculties: "
                            + e.getMessage()
            );
        }

        return faculties;
    }

    // =========================================================
    // UPDATE FACULTY
    // =========================================================

    public boolean updateFaculty(
            int facultyId,
            String facultyName,
            String description
    ) {

        if (facultyId <= 0) {
            return false;
        }

        if (!isValidFacultyName(facultyName)) {
            return false;
        }

        String cleanFacultyName =
                facultyName.trim();

        String cleanDescription =
                description == null
                        ? ""
                        : description.trim();

        if (facultyNameExists(
                cleanFacultyName,
                facultyId
        )) {
            return false;
        }

        String sql =
                "UPDATE FACULTY " +
                        "SET faculty_name = ?, description = ? " +
                        "WHERE faculty_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    cleanFacultyName
            );

            statement.setString(
                    2,
                    cleanDescription
            );

            statement.setInt(
                    3,
                    facultyId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error updating faculty: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // DELETE FACULTY
    // =========================================================

    public boolean deleteFaculty(
            int facultyId
    ) {

        if (facultyId <= 0) {
            return false;
        }

        String sql =
                "DELETE FROM FACULTY " +
                        "WHERE faculty_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    facultyId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting faculty: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // CHECK DUPLICATE FACULTY NAME
    // =========================================================

    public boolean facultyNameExists(
            String facultyName,
            int excludedFacultyId
    ) {

        String sql;

        if (excludedFacultyId > 0) {

            sql =
                    "SELECT faculty_id " +
                            "FROM FACULTY " +
                            "WHERE LOWER(faculty_name) = LOWER(?) " +
                            "AND faculty_id <> ?";

        } else {

            sql =
                    "SELECT faculty_id " +
                            "FROM FACULTY " +
                            "WHERE LOWER(faculty_name) = LOWER(?)";
        }

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    facultyName.trim()
            );

            if (excludedFacultyId > 0) {

                statement.setInt(
                        2,
                        excludedFacultyId
                );
            }

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                return resultSet.next();
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error checking faculty name: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    public boolean isValidFacultyName(
            String facultyName
    ) {

        return facultyName != null
                && !facultyName.trim().isEmpty()
                && facultyName.trim().length() >= 2
                && facultyName.trim().length() <= 100;
    }
}