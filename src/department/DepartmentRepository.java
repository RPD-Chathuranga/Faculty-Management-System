package department;

import common.DatabaseConnection;
import faculty.Faculty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {

    // =========================================================
    // INSERT DEPARTMENT
    // =========================================================

    public boolean addDepartment(
            Department department
    ) {

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
                    department.getDepartmentName()
            );

            statement.setInt(
                    2,
                    department.getFacultyId()
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
            Department department
    ) {

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
                    department.getDepartmentName()
            );

            statement.setInt(
                    2,
                    department.getFacultyId()
            );

            statement.setInt(
                    3,
                    department.getDepartmentId()
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
    // LOAD ALL DEPARTMENTS WITH FACULTY DETAILS
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

                facultyList.add(
                        faculty
                );
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
    // CHECK DUPLICATE DEPARTMENT
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
                    departmentName
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