package attendance;

import common.DatabaseConnection;
import course.Course;
import student.Student;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {

    // =========================================================
    // ADD ATTENDANCE
    // =========================================================

    public boolean addAttendance(
            Attendance attendance
    ) {

        String sql =
                "INSERT INTO ATTENDANCE " +
                        "(student_id, course_id, date, status) " +
                        "VALUES (?, ?, ?, ?)";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setInt(
                    1,
                    attendance.getStudentId()
            );

            statement.setInt(
                    2,
                    attendance.getCourseId()
            );

            statement.setDate(
                    3,
                    Date.valueOf(
                            attendance.getDate()
                    )
            );

            statement.setString(
                    4,
                    attendance.getStatus()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

    }

    // =========================================================
    // UPDATE
    // =========================================================

    public boolean updateAttendance(
            Attendance attendance
    ) {

        String sql =
                "UPDATE ATTENDANCE SET " +
                        "student_id=?, " +
                        "course_id=?, " +
                        "date=?, " +
                        "status=? " +
                        "WHERE attendance_id=?";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setInt(
                    1,
                    attendance.getStudentId()
            );

            statement.setInt(
                    2,
                    attendance.getCourseId()
            );

            statement.setDate(
                    3,
                    Date.valueOf(
                            attendance.getDate()
                    )
            );

            statement.setString(
                    4,
                    attendance.getStatus()
            );

            statement.setInt(
                    5,
                    attendance.getAttendanceId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

    }

    // =========================================================
    // DELETE
    // =========================================================

    public boolean deleteAttendance(
            int attendanceId
    ) {

        String sql =
                "DELETE FROM ATTENDANCE " +
                        "WHERE attendance_id=?";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setInt(
                    1,
                    attendanceId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

    }

    // =========================================================
    // LOAD STUDENTS
    // =========================================================

    public List<Student> getAllStudents() {

        List<Student> students =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "s.student_id, " +
                        "s.registration_number, " +
                        "u.full_name " +
                        "FROM STUDENT s " +
                        "INNER JOIN USERS u " +
                        "ON s.user_id = u.user_id " +
                        "ORDER BY u.full_name";

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
                        new Student();

                student.setStudentId(
                        resultSet.getInt(
                                "student_id"
                        )
                );

                student.setRegistrationNumber(
                        resultSet.getString(
                                "registration_number"
                        )
                );

                student.setFullName(
                        resultSet.getString(
                                "full_name"
                        )
                );

                students.add(student);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return students;

    }

    // =========================================================
    // LOAD COURSES
    // =========================================================

    public List<Course> getAllCourses() {

        List<Course> courses =
                new ArrayList<>();

        String sql =
                "SELECT course_id, course_code, course_name " +
                        "FROM COURSE " +
                        "ORDER BY course_code";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()

        ) {

            while (resultSet.next()) {

                Course course =
                        new Course();

                course.setCourseId(
                        resultSet.getInt(
                                "course_id"
                        )
                );

                course.setCourseCode(
                        resultSet.getString(
                                "course_code"
                        )
                );

                course.setCourseName(
                        resultSet.getString(
                                "course_name"
                        )
                );

                courses.add(course);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return courses;

    }     // =========================================================
    // LOAD ATTENDANCE
    // =========================================================

    public List<Attendance> getAllAttendance() {

        List<Attendance> attendanceList =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "a.attendance_id, " +
                        "a.student_id, " +
                        "a.course_id, " +
                        "a.date, " +
                        "a.status, " +
                        "u.full_name, " +
                        "s.registration_number, " +
                        "c.course_code, " +
                        "c.course_name " +
                        "FROM ATTENDANCE a " +
                        "INNER JOIN STUDENT s " +
                        "ON a.student_id = s.student_id " +
                        "INNER JOIN USERS u " +
                        "ON s.user_id = u.user_id " +
                        "INNER JOIN COURSE c " +
                        "ON a.course_id = c.course_id " +
                        "ORDER BY a.date DESC, u.full_name";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()

        ) {

            while (resultSet.next()) {

                Attendance attendance =
                        new Attendance(

                                resultSet.getInt("attendance_id"),
                                resultSet.getInt("student_id"),
                                resultSet.getInt("course_id"),
                                resultSet.getDate("date").toLocalDate(),
                                resultSet.getString("status"),
                                resultSet.getString("full_name"),
                                resultSet.getString("registration_number"),
                                resultSet.getString("course_code"),
                                resultSet.getString("course_name")

                        );

                attendanceList.add(
                        attendance
                );

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return attendanceList;

    }

    // =========================================================
    // CHECK DUPLICATE ATTENDANCE
    // =========================================================

    public boolean attendanceExists(
            Attendance attendance
    ) {

        String sql =
                "SELECT attendance_id " +
                        "FROM ATTENDANCE " +
                        "WHERE student_id=? " +
                        "AND course_id=? " +
                        "AND date=? " +
                        "AND attendance_id<>?";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setInt(
                    1,
                    attendance.getStudentId()
            );

            statement.setInt(
                    2,
                    attendance.getCourseId()
            );

            statement.setDate(
                    3,
                    Date.valueOf(
                            attendance.getDate()
                    )
            );

            statement.setInt(
                    4,
                    attendance.getAttendanceId()
            );

            ResultSet resultSet =
                    statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {

            return false;

        }

    }

}