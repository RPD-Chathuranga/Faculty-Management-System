package student;

import common.DatabaseConnection;
import department.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    // =========================================================
    // ADD USER + STUDENT
    // =========================================================

    public boolean addStudent(Student student) {

        String userSql =
                "INSERT INTO USERS " +
                        "(username,password,full_name,email,phone,role) " +
                        "VALUES (?,?,?,?,?,'STUDENT')";

        String studentSql =
                "INSERT INTO STUDENT " +
                        "(user_id,registration_number,semester,department_id) " +
                        "VALUES (?,?,?,?)";

        Connection connection = null;

        try {

            connection = DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            int generatedUserId;

            try (PreparedStatement userStatement =
                         connection.prepareStatement(
                                 userSql,
                                 Statement.RETURN_GENERATED_KEYS
                         )) {

                userStatement.setString(1, student.getUsername());
                userStatement.setString(2, student.getPassword());
                userStatement.setString(3, student.getFullName());
                userStatement.setString(4, student.getEmail());
                userStatement.setString(5, student.getPhone());

                userStatement.executeUpdate();

                ResultSet keys =
                        userStatement.getGeneratedKeys();

                keys.next();

                generatedUserId =
                        keys.getInt(1);
            }

            try (PreparedStatement studentStatement =
                         connection.prepareStatement(
                                 studentSql
                         )) {

                studentStatement.setInt(
                        1,
                        generatedUserId
                );

                studentStatement.setString(
                        2,
                        student.getRegistrationNumber()
                );

                studentStatement.setInt(
                        3,
                        student.getSemester()
                );

                studentStatement.setInt(
                        4,
                        student.getDepartmentId()
                );

                studentStatement.executeUpdate();

            }

            connection.commit();

            return true;

        } catch (SQLException e) {

            try {

                if (connection != null) {

                    connection.rollback();

                }

            } catch (SQLException ignored) {
            }

            e.printStackTrace();

            return false;

        } finally {

            try {

                if (connection != null) {

                    connection.setAutoCommit(true);

                }

            } catch (SQLException ignored) {
            }

        }

    }

    // =========================================================
    // UPDATE
    // =========================================================

    public boolean updateStudent(Student student) {

        Connection connection = null;

        try {

            connection =
                    DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            String userSql;

            if (student.getPassword() == null ||
                    student.getPassword().trim().isEmpty()) {

                userSql =
                        "UPDATE USERS SET " +
                                "username=?,full_name=?,email=?,phone=? " +
                                "WHERE user_id=?";

            } else {

                userSql =
                        "UPDATE USERS SET " +
                                "username=?,password=?,full_name=?,email=?,phone=? " +
                                "WHERE user_id=?";
            }

            PreparedStatement userStatement =
                    connection.prepareStatement(userSql);

            if (student.getPassword() == null ||
                    student.getPassword().trim().isEmpty()) {

                userStatement.setString(1, student.getUsername());
                userStatement.setString(2, student.getFullName());
                userStatement.setString(3, student.getEmail());
                userStatement.setString(4, student.getPhone());
                userStatement.setInt(5, student.getUserId());

            } else {

                userStatement.setString(1, student.getUsername());
                userStatement.setString(2, student.getPassword());
                userStatement.setString(3, student.getFullName());
                userStatement.setString(4, student.getEmail());
                userStatement.setString(5, student.getPhone());
                userStatement.setInt(6, student.getUserId());

            }

            userStatement.executeUpdate();

            PreparedStatement studentStatement =
                    connection.prepareStatement(

                            "UPDATE STUDENT SET " +
                                    "registration_number=?,semester=?,department_id=? " +
                                    "WHERE student_id=?"

                    );

            studentStatement.setString(
                    1,
                    student.getRegistrationNumber()
            );

            studentStatement.setInt(
                    2,
                    student.getSemester()
            );

            studentStatement.setInt(
                    3,
                    student.getDepartmentId()
            );

            studentStatement.setInt(
                    4,
                    student.getStudentId()
            );

            studentStatement.executeUpdate();

            connection.commit();

            return true;

        } catch (SQLException e) {

            try {

                if (connection != null)
                    connection.rollback();

            } catch (SQLException ignored) {
            }

            e.printStackTrace();

            return false;

        } finally {

            try {

                if (connection != null)
                    connection.setAutoCommit(true);

            } catch (SQLException ignored) {
            }

        }

    }     // =========================================================
    // DELETE
    // =========================================================

    public boolean deleteStudent(int userId) {

        String sql =
                "DELETE FROM USERS " +
                        "WHERE user_id=? " +
                        "AND role='STUDENT'";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setInt(1, userId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    // =========================================================
    // LOAD DEPARTMENTS
    // =========================================================

    public List<Department> getAllDepartments() {

        List<Department> departments =
                new ArrayList<>();

        String sql =
                "SELECT * FROM DEPARTMENT ORDER BY department_name";

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
                        resultSet.getInt("department_id")
                );

                department.setDepartmentName(
                        resultSet.getString("department_name")
                );

                departments.add(department);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return departments;

    }

    // =========================================================
    // LOAD STUDENTS
    // =========================================================

    public List<Student> getAllStudents() {

        List<Student> students =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "s.student_id," +
                        "s.user_id," +
                        "s.registration_number," +
                        "s.semester," +
                        "s.department_id," +
                        "u.username," +
                        "u.full_name," +
                        "u.email," +
                        "u.phone," +
                        "d.department_name " +
                        "FROM STUDENT s " +
                        "INNER JOIN USERS u " +
                        "ON s.user_id=u.user_id " +
                        "INNER JOIN DEPARTMENT d " +
                        "ON s.department_id=d.department_id " +
                        "ORDER BY s.student_id DESC";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()

        ) {

            while (resultSet.next()) {

                Student student =
                        new Student(

                                resultSet.getInt("student_id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("username"),
                                resultSet.getString("full_name"),
                                resultSet.getString("email"),
                                resultSet.getString("phone"),
                                resultSet.getString("registration_number"),
                                resultSet.getInt("semester"),
                                resultSet.getInt("department_id"),
                                resultSet.getString("department_name")

                        );

                students.add(student);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return students;

    }

    // =========================================================
    // DUPLICATE CHECKS
    // =========================================================

    public boolean usernameExists(
            String username,
            int excludeUserId
    ) {

        return exists(
                "SELECT user_id FROM USERS WHERE LOWER(username)=LOWER(?) AND user_id<>?",
                username,
                excludeUserId
        );

    }

    public boolean emailExists(
            String email,
            int excludeUserId
    ) {

        return exists(
                "SELECT user_id FROM USERS WHERE LOWER(email)=LOWER(?) AND user_id<>?",
                email,
                excludeUserId
        );

    }

    public boolean registrationExists(
            String registration,
            int excludeStudentId
    ) {

        return exists(
                "SELECT student_id FROM STUDENT WHERE LOWER(registration_number)=LOWER(?) AND student_id<>?",
                registration,
                excludeStudentId
        );

    }

    // =========================================================
    // HELPER
    // =========================================================

    private boolean exists(
            String sql,
            String value,
            int id
    ) {

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setString(1, value);

            statement.setInt(2, id);

            ResultSet resultSet =
                    statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {

            return false;

        }

    }

}