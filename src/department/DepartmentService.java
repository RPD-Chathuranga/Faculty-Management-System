package department;

import common.DatabaseConnection;
import faculty.Faculty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    // =========================================================
    // ADD DEPARTMENT
    // =========================================================

    public boolean addDepartment(
            String departmentName,
            int facultyId
    ) {

        if (!isValidDepartmentName(departmentName)) {
            return false;
        }

        if (facultyId <= 0) {
            return false;
        }

        String cleanDepartmentName =
                departmentName.trim();

        if (departmentNameExists(
                cleanDepartmentName,
                facultyId,
                0
        )) {
            return false;
        }

        String sql =
                "INSERT INTO DEPARTMENT " +
                        "(department_name, faculty_id) " +
                        "VALUES (?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    cleanDepartmentName
            );

            statement.setInt(
                    2,
                    facultyId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error adding department: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // UPDATE DEPARTMENT
    // =========================================================

    public boolean updateDepartment(
            int departmentId,
            String departmentName,
            int facultyId
    ) {

        if (departmentId <= 0) {
            return false;
        }

        if (!isValidDepartmentName(departmentName)) {
            return false;
        }

        if (facultyId <= 0) {
            return false;
        }

        String cleanDepartmentName =
                departmentName.trim();

        if (departmentNameExists(
                cleanDepartmentName,
                facultyId,
                departmentId
        )) {
            return false;
        }

        String sql =
                "UPDATE DEPARTMENT " +
                        "SET department_name = ?, faculty_id = ? " +
                        "WHERE department_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    cleanDepartmentName
            );

            statement.setInt(
                    2,
                    facultyId
            );

            statement.setInt(
                    3,
                    departmentId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error updating department: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // DELETE DEPARTMENT
    // =========================================================

    public boolean deleteDepartment(
            int departmentId
    ) {

        if (departmentId <= 0) {
            return false;
        }

        String sql =
                "DELETE FROM DEPARTMENT " +
                        "WHERE department_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    departmentId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting department: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // LOAD FACULTIES FOR COMBOBOX
    // =========================================================

    public List<Faculty> getAllFaculties() {

        List<Faculty> facultyList =
                new ArrayList<>();

        String sql =
                "SELECT faculty_id, faculty_name, description " +
                        "FROM FACULTY " +
                        "ORDER BY faculty_name";

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

                facultyList.add(faculty);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading faculties: "
                            + e.getMessage()
            );
        }

        return facultyList;
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    public boolean isValidDepartmentName(
            String departmentName
    ) {

        return departmentName != null
                && !departmentName.trim().isEmpty()
                && departmentName.trim().length() >= 2
                && departmentName.trim().length() <= 100;
    }     // =========================================================
    // LOAD ALL DEPARTMENTS WITH FACULTY NAME
    // =========================================================

    public List<Department> getAllDepartments() {

        List<Department> departmentList =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "d.department_id, " +
                        "d.department_name, " +
                        "d.faculty_id, " +
                        "f.faculty_name, " +
                        "f.description " +
                        "FROM DEPARTMENT d " +
                        "INNER JOIN FACULTY f " +
                        "ON d.faculty_id = f.faculty_id " +
                        "ORDER BY d.department_id DESC";

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

                Department department =
                        new Department(
                                resultSet.getInt(
                                        "department_id"
                                ),
                                resultSet.getString(
                                        "department_name"
                                ),
                                resultSet.getInt(
                                        "faculty_id"
                                ),
                                faculty
                        );

                departmentList.add(
                        department
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading departments: "
                            + e.getMessage()
            );
        }

        return departmentList;
    }

    // =========================================================
    // CHECK DUPLICATE DEPARTMENT NAME
    // =========================================================

    public boolean departmentNameExists(
            String departmentName,
            int facultyId,
            int excludedDepartmentId
    ) {

        String sql;

        if (excludedDepartmentId > 0) {

            sql =
                    "SELECT department_id " +
                            "FROM DEPARTMENT " +
                            "WHERE LOWER(department_name) = LOWER(?) " +
                            "AND faculty_id = ? " +
                            "AND department_id <> ?";

        } else {

            sql =
                    "SELECT department_id " +
                            "FROM DEPARTMENT " +
                            "WHERE LOWER(department_name) = LOWER(?) " +
                            "AND faculty_id = ?";
        }

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    departmentName.trim()
            );

            statement.setInt(
                    2,
                    facultyId
            );

            if (excludedDepartmentId > 0) {

                statement.setInt(
                        3,
                        excludedDepartmentId
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
                    "Error checking department name: "
                            + e.getMessage()
            );

            return false;
        }
    }
}