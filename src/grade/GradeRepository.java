package grade;

import common.DatabaseConnection;
import course.Course;
import student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeRepository {

    // =========================================================
    // ADD GRADE
    // =========================================================

    public boolean addGrade(
            Grade grade
    ) {

        String sql =
                "INSERT INTO GRADE " +
                        "(student_id, course_id, marks, letter_grade) " +
                        "VALUES (?, ?, ?, ?)";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setInt(
                    1,
                    grade.getStudentId()
            );

            statement.setInt(
                    2,
                    grade.getCourseId()
            );

            statement.setDouble(
                    3,
                    grade.getMarks()
            );

            statement.setString(
                    4,
                    grade.getLetterGrade()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        }

    }

    // =========================================================
    // UPDATE GRADE
    // =========================================================

    public boolean updateGrade(
            Grade grade
    ) {

        String sql =
                "UPDATE GRADE SET " +
                        "student_id=?, " +
                        "course_id=?, " +
                        "marks=?, " +
                        "letter_grade=? " +
                        "WHERE grade_id=?";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setInt(1, grade.getStudentId());
            statement.setInt(2, grade.getCourseId());
            statement.setDouble(3, grade.getMarks());
            statement.setString(4, grade.getLetterGrade());
            statement.setInt(5, grade.getGradeId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        }

    }

    // =========================================================
    // DELETE GRADE
    // =========================================================

    public boolean deleteGrade(
            int gradeId
    ) {

        String sql =
                "DELETE FROM GRADE WHERE grade_id=?";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setInt(
                    1,
                    gradeId
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
                "SELECT s.student_id, s.registration_number, u.full_name " +
                        "FROM STUDENT s " +
                        "INNER JOIN USERS u ON s.user_id=u.user_id " +
                        "ORDER BY u.full_name";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet rs =
                        statement.executeQuery()

        ) {

            while (rs.next()) {

                Student student =
                        new Student();

                student.setStudentId(
                        rs.getInt("student_id")
                );

                student.setRegistrationNumber(
                        rs.getString("registration_number")
                );

                student.setFullName(
                        rs.getString("full_name")
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

                ResultSet rs =
                        statement.executeQuery()

        ) {

            while (rs.next()) {

                Course course =
                        new Course();

                course.setCourseId(
                        rs.getInt("course_id")
                );

                course.setCourseCode(
                        rs.getString("course_code")
                );

                course.setCourseName(
                        rs.getString("course_name")
                );

                courses.add(course);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return courses;

    }     // =========================================================
    // LOAD ALL GRADES
    // =========================================================

    public List<Grade> getAllGrades() {

        List<Grade> gradeList =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "g.grade_id, " +
                        "g.student_id, " +
                        "g.course_id, " +
                        "g.marks, " +
                        "g.letter_grade, " +
                        "u.full_name, " +
                        "s.registration_number, " +
                        "c.course_code, " +
                        "c.course_name " +
                        "FROM GRADE g " +
                        "INNER JOIN STUDENT s " +
                        "ON g.student_id = s.student_id " +
                        "INNER JOIN USERS u " +
                        "ON s.user_id = u.user_id " +
                        "INNER JOIN COURSE c " +
                        "ON g.course_id = c.course_id " +
                        "ORDER BY g.grade_id DESC";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Grade grade =
                        new Grade(
                                resultSet.getInt(
                                        "grade_id"
                                ),
                                resultSet.getInt(
                                        "student_id"
                                ),
                                resultSet.getInt(
                                        "course_id"
                                ),
                                resultSet.getDouble(
                                        "marks"
                                ),
                                resultSet.getString(
                                        "letter_grade"
                                ),
                                resultSet.getString(
                                        "full_name"
                                ),
                                resultSet.getString(
                                        "registration_number"
                                ),
                                resultSet.getString(
                                        "course_code"
                                ),
                                resultSet.getString(
                                        "course_name"
                                )
                        );

                gradeList.add(
                        grade
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading grades: "
                            + e.getMessage()
            );
        }

        return gradeList;
    }

    // =========================================================
    // CHECK DUPLICATE GRADE
    // =========================================================

    public boolean gradeExists(
            Grade grade
    ) {

        String sql =
                "SELECT grade_id " +
                        "FROM GRADE " +
                        "WHERE student_id = ? " +
                        "AND course_id = ? " +
                        "AND grade_id <> ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    grade.getStudentId()
            );

            statement.setInt(
                    2,
                    grade.getCourseId()
            );

            statement.setInt(
                    3,
                    grade.getGradeId()
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                return resultSet.next();
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error checking duplicate grade: "
                            + e.getMessage()
            );

            return false;
        }
    }
}