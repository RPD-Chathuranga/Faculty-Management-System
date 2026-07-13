package lecturer;

import common.DatabaseConnection;
import department.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LecturerRepository {

    // =========================================================
    // ADD USER AND LECTURER
    // =========================================================

    public boolean addLecturer(
            Lecturer lecturer
    ) {

        String userSql =
                "INSERT INTO USERS " +
                        "(username, password, full_name, email, phone, role) " +
                        "VALUES (?, ?, ?, ?, ?, 'LECTURER')";

        String lecturerSql =
                "INSERT INTO LECTURER " +
                        "(user_id, employee_number, specialization, department_id) " +
                        "VALUES (?, ?, ?, ?)";

        Connection connection = null;

        try {

            connection =
                    DatabaseConnection.getConnection();

            if (connection == null) {
                return false;
            }

            connection.setAutoCommit(false);

            int generatedUserId;

            try (
                    PreparedStatement userStatement =
                            connection.prepareStatement(
                                    userSql,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                userStatement.setString(
                        1,
                        lecturer.getUsername()
                );

                userStatement.setString(
                        2,
                        lecturer.getPassword()
                );

                userStatement.setString(
                        3,
                        lecturer.getFullName()
                );

                userStatement.setString(
                        4,
                        lecturer.getEmail()
                );

                userStatement.setString(
                        5,
                        lecturer.getPhone()
                );

                int userRows =
                        userStatement.executeUpdate();

                if (userRows == 0) {

                    connection.rollback();
                    return false;
                }

                try (
                        ResultSet generatedKeys =
                                userStatement.getGeneratedKeys()
                ) {

                    if (!generatedKeys.next()) {

                        connection.rollback();
                        return false;
                    }

                    generatedUserId =
                            generatedKeys.getInt(1);
                }
            }

            try (
                    PreparedStatement lecturerStatement =
                            connection.prepareStatement(
                                    lecturerSql
                            )
            ) {

                lecturerStatement.setInt(
                        1,
                        generatedUserId
                );

                lecturerStatement.setString(
                        2,
                        lecturer.getEmployeeNumber()
                );

                lecturerStatement.setString(
                        3,
                        lecturer.getSpecialization()
                );

                lecturerStatement.setInt(
                        4,
                        lecturer.getDepartmentId()
                );

                int lecturerRows =
                        lecturerStatement.executeUpdate();

                if (lecturerRows == 0) {

                    connection.rollback();
                    return false;
                }
            }

            connection.commit();

            return true;

        } catch (SQLException e) {

            rollbackConnection(connection);

            System.out.println(
                    "Error adding lecturer: "
                            + e.getMessage()
            );

            return false;

        } finally {

            restoreAutoCommit(connection);
        }
    }

    // =========================================================
    // UPDATE USER AND LECTURER
    // =========================================================

    public boolean updateLecturer(
            Lecturer lecturer
    ) {

        /*
         * If the password field is empty, the current password
         * will not be changed.
         */
        boolean updatePassword =
                lecturer.getPassword() != null
                        && !lecturer.getPassword()
                        .trim()
                        .isEmpty();

        String userSql;

        if (updatePassword) {

            userSql =
                    "UPDATE USERS SET " +
                            "username = ?, " +
                            "password = ?, " +
                            "full_name = ?, " +
                            "email = ?, " +
                            "phone = ? " +
                            "WHERE user_id = ?";

        } else {

            userSql =
                    "UPDATE USERS SET " +
                            "username = ?, " +
                            "full_name = ?, " +
                            "email = ?, " +
                            "phone = ? " +
                            "WHERE user_id = ?";
        }

        String lecturerSql =
                "UPDATE LECTURER SET " +
                        "employee_number = ?, " +
                        "specialization = ?, " +
                        "department_id = ? " +
                        "WHERE lecturer_id = ?";

        Connection connection = null;

        try {

            connection =
                    DatabaseConnection.getConnection();

            if (connection == null) {
                return false;
            }

            connection.setAutoCommit(false);

            try (
                    PreparedStatement userStatement =
                            connection.prepareStatement(
                                    userSql
                            )
            ) {

                if (updatePassword) {

                    userStatement.setString(
                            1,
                            lecturer.getUsername()
                    );

                    userStatement.setString(
                            2,
                            lecturer.getPassword()
                    );

                    userStatement.setString(
                            3,
                            lecturer.getFullName()
                    );

                    userStatement.setString(
                            4,
                            lecturer.getEmail()
                    );

                    userStatement.setString(
                            5,
                            lecturer.getPhone()
                    );

                    userStatement.setInt(
                            6,
                            lecturer.getUserId()
                    );

                } else {

                    userStatement.setString(
                            1,
                            lecturer.getUsername()
                    );

                    userStatement.setString(
                            2,
                            lecturer.getFullName()
                    );

                    userStatement.setString(
                            3,
                            lecturer.getEmail()
                    );

                    userStatement.setString(
                            4,
                            lecturer.getPhone()
                    );

                    userStatement.setInt(
                            5,
                            lecturer.getUserId()
                    );
                }

                int userRows =
                        userStatement.executeUpdate();

                if (userRows == 0) {

                    connection.rollback();
                    return false;
                }
            }

            try (
                    PreparedStatement lecturerStatement =
                            connection.prepareStatement(
                                    lecturerSql
                            )
            ) {

                lecturerStatement.setString(
                        1,
                        lecturer.getEmployeeNumber()
                );

                lecturerStatement.setString(
                        2,
                        lecturer.getSpecialization()
                );

                lecturerStatement.setInt(
                        3,
                        lecturer.getDepartmentId()
                );

                lecturerStatement.setInt(
                        4,
                        lecturer.getLecturerId()
                );

                int lecturerRows =
                        lecturerStatement.executeUpdate();

                if (lecturerRows == 0) {

                    connection.rollback();
                    return false;
                }
            }

            connection.commit();

            return true;

        } catch (SQLException e) {

            rollbackConnection(connection);

            System.out.println(
                    "Error updating lecturer: "
                            + e.getMessage()
            );

            return false;

        } finally {

            restoreAutoCommit(connection);
        }
    }

    // =========================================================
    // DELETE USER AND CASCADE LECTURER
    // =========================================================

    public boolean deleteLecturer(
            int userId
    ) {

        /*
         * Deleting the USERS record automatically deletes
         * the matching LECTURER record because the foreign key
         * uses ON DELETE CASCADE.
         */
        String sql =
                "DELETE FROM USERS " +
                        "WHERE user_id = ? " +
                        "AND role = 'LECTURER'";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting lecturer: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // LOAD DEPARTMENTS
    // =========================================================

    public List<Department> getAllDepartments() {

        List<Department> departmentList =
                new ArrayList<>();

        String sql =
                "SELECT department_id, department_name " +
                        "FROM DEPARTMENT " +
                        "ORDER BY department_name";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Department department =
                        new Department();

                department.setDepartmentId(
                        resultSet.getInt(
                                "department_id"
                        )
                );

                department.setDepartmentName(
                        resultSet.getString(
                                "department_name"
                        )
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
    }     // =========================================================
    // LOAD ALL LECTURERS
    // =========================================================

    public List<Lecturer> getAllLecturers() {

        List<Lecturer> lecturerList =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "l.lecturer_id, " +
                        "l.user_id, " +
                        "l.employee_number, " +
                        "l.specialization, " +
                        "l.department_id, " +
                        "u.username, " +
                        "u.full_name, " +
                        "u.email, " +
                        "u.phone, " +
                        "d.department_name " +
                        "FROM LECTURER l " +
                        "INNER JOIN USERS u " +
                        "ON l.user_id = u.user_id " +
                        "INNER JOIN DEPARTMENT d " +
                        "ON l.department_id = d.department_id " +
                        "ORDER BY l.lecturer_id DESC";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Lecturer lecturer =
                        new Lecturer(
                                resultSet.getInt("lecturer_id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("username"),
                                resultSet.getString("full_name"),
                                resultSet.getString("email"),
                                resultSet.getString("phone"),
                                resultSet.getString("employee_number"),
                                resultSet.getString("specialization"),
                                resultSet.getInt("department_id"),
                                resultSet.getString("department_name")
                        );

                lecturerList.add(
                        lecturer
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading lecturers: "
                            + e.getMessage()
            );
        }

        return lecturerList;
    }

    // =========================================================
    // USERNAME EXISTS
    // =========================================================

    public boolean usernameExists(
            String username,
            int excludedUserId
    ) {

        String sql;

        if (excludedUserId > 0) {

            sql =
                    "SELECT user_id FROM USERS " +
                            "WHERE LOWER(username)=LOWER(?) " +
                            "AND user_id<>?";

        } else {

            sql =
                    "SELECT user_id FROM USERS " +
                            "WHERE LOWER(username)=LOWER(?)";
        }

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    username
            );

            if (excludedUserId > 0) {

                statement.setInt(
                        2,
                        excludedUserId
                );
            }

            ResultSet resultSet =
                    statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {

            return false;
        }
    }

    // =========================================================
    // EMAIL EXISTS
    // =========================================================

    public boolean emailExists(
            String email,
            int excludedUserId
    ) {

        String sql;

        if (excludedUserId > 0) {

            sql =
                    "SELECT user_id FROM USERS " +
                            "WHERE LOWER(email)=LOWER(?) " +
                            "AND user_id<>?";

        } else {

            sql =
                    "SELECT user_id FROM USERS " +
                            "WHERE LOWER(email)=LOWER(?)";
        }

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    email
            );

            if (excludedUserId > 0) {

                statement.setInt(
                        2,
                        excludedUserId
                );
            }

            ResultSet resultSet =
                    statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {

            return false;
        }
    }

    // =========================================================
    // EMPLOYEE NUMBER EXISTS
    // =========================================================

    public boolean employeeNumberExists(
            String employeeNumber,
            int excludedLecturerId
    ) {

        String sql;

        if (excludedLecturerId > 0) {

            sql =
                    "SELECT lecturer_id FROM LECTURER " +
                            "WHERE LOWER(employee_number)=LOWER(?) " +
                            "AND lecturer_id<>?";

        } else {

            sql =
                    "SELECT lecturer_id FROM LECTURER " +
                            "WHERE LOWER(employee_number)=LOWER(?)";
        }

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    employeeNumber
            );

            if (excludedLecturerId > 0) {

                statement.setInt(
                        2,
                        excludedLecturerId
                );
            }

            ResultSet resultSet =
                    statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {

            return false;
        }
    }

    // =========================================================
    // HELPER METHODS
    // =========================================================

    private void rollbackConnection(
            Connection connection
    ) {

        if (connection != null) {

            try {

                connection.rollback();

            } catch (SQLException ignored) {
            }

        }
    }

    private void restoreAutoCommit(
            Connection connection
    ) {

        if (connection != null) {

            try {

                connection.setAutoCommit(true);

            } catch (SQLException ignored) {
            }

        }
    }

}