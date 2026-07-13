package course;

import common.DatabaseConnection;
import department.Department;
import lecturer.Lecturer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {

    // =========================================================
    // ADD COURSE
    // =========================================================

    public boolean addCourse(
            Course course
    ) {

        String sql =
                "INSERT INTO COURSE " +
                        "(course_code, course_name, credits, department_id, lecturer_id) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    course.getCourseCode()
            );

            statement.setString(
                    2,
                    course.getCourseName()
            );

            statement.setInt(
                    3,
                    course.getCredits()
            );

            statement.setInt(
                    4,
                    course.getDepartmentId()
            );

            if (course.getLecturerId() == null) {

                statement.setNull(
                        5,
                        Types.INTEGER
                );

            } else {

                statement.setInt(
                        5,
                        course.getLecturerId()
                );
            }

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error adding course: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // UPDATE COURSE
    // =========================================================

    public boolean updateCourse(
            Course course
    ) {

        String sql =
                "UPDATE COURSE SET " +
                        "course_code = ?, " +
                        "course_name = ?, " +
                        "credits = ?, " +
                        "department_id = ?, " +
                        "lecturer_id = ? " +
                        "WHERE course_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    course.getCourseCode()
            );

            statement.setString(
                    2,
                    course.getCourseName()
            );

            statement.setInt(
                    3,
                    course.getCredits()
            );

            statement.setInt(
                    4,
                    course.getDepartmentId()
            );

            if (course.getLecturerId() == null) {

                statement.setNull(
                        5,
                        Types.INTEGER
                );

            } else {

                statement.setInt(
                        5,
                        course.getLecturerId()
                );
            }

            statement.setInt(
                    6,
                    course.getCourseId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error updating course: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // DELETE COURSE
    // =========================================================

    public boolean deleteCourse(
            int courseId
    ) {

        String sql =
                "DELETE FROM COURSE " +
                        "WHERE course_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    courseId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting course: "
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
    }

    // =========================================================
    // LOAD LECTURERS
    // =========================================================

    public List<Lecturer> getAllLecturers() {

        List<Lecturer> lecturerList =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "l.lecturer_id, " +
                        "l.user_id, " +
                        "l.department_id, " +
                        "u.full_name " +
                        "FROM LECTURER l " +
                        "INNER JOIN USERS u " +
                        "ON l.user_id = u.user_id " +
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

                Lecturer lecturer =
                        new Lecturer();

                lecturer.setLecturerId(
                        resultSet.getInt(
                                "lecturer_id"
                        )
                );

                lecturer.setUserId(
                        resultSet.getInt(
                                "user_id"
                        )
                );

                lecturer.setDepartmentId(
                        resultSet.getInt(
                                "department_id"
                        )
                );

                lecturer.setFullName(
                        resultSet.getString(
                                "full_name"
                        )
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
    }     // =========================================================
    // LOAD ALL COURSES
    // =========================================================

    public List<Course> getAllCourses() {

        List<Course> courseList =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "c.course_id, " +
                        "c.course_code, " +
                        "c.course_name, " +
                        "c.credits, " +
                        "c.department_id, " +
                        "d.department_name, " +
                        "c.lecturer_id, " +
                        "u.full_name AS lecturer_name " +
                        "FROM COURSE c " +
                        "INNER JOIN DEPARTMENT d " +
                        "ON c.department_id = d.department_id " +
                        "LEFT JOIN LECTURER l " +
                        "ON c.lecturer_id = l.lecturer_id " +
                        "LEFT JOIN USERS u " +
                        "ON l.user_id = u.user_id " +
                        "ORDER BY c.course_id DESC";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                int lecturerIdValue =
                        resultSet.getInt(
                                "lecturer_id"
                        );

                Integer lecturerId =
                        resultSet.wasNull()
                                ? null
                                : lecturerIdValue;

                String lecturerName =
                        resultSet.getString(
                                "lecturer_name"
                        );

                if (lecturerName == null
                        || lecturerName.trim().isEmpty()) {

                    lecturerName =
                            "Not Assigned";
                }

                Course course =
                        new Course(
                                resultSet.getInt(
                                        "course_id"
                                ),
                                resultSet.getString(
                                        "course_code"
                                ),
                                resultSet.getString(
                                        "course_name"
                                ),
                                resultSet.getInt(
                                        "credits"
                                ),
                                resultSet.getInt(
                                        "department_id"
                                ),
                                resultSet.getString(
                                        "department_name"
                                ),
                                lecturerId,
                                lecturerName
                        );

                courseList.add(
                        course
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading courses: "
                            + e.getMessage()
            );
        }

        return courseList;
    }

    // =========================================================
    // CHECK DUPLICATE COURSE CODE
    // =========================================================

    public boolean courseCodeExists(
            String courseCode,
            int excludedCourseId
    ) {

        String sql;

        if (excludedCourseId > 0) {

            sql =
                    "SELECT course_id " +
                            "FROM COURSE " +
                            "WHERE LOWER(course_code) = LOWER(?) " +
                            "AND course_id <> ?";

        } else {

            sql =
                    "SELECT course_id " +
                            "FROM COURSE " +
                            "WHERE LOWER(course_code) = LOWER(?)";
        }

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    courseCode.trim()
            );

            if (excludedCourseId > 0) {

                statement.setInt(
                        2,
                        excludedCourseId
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
                    "Error checking course code: "
                            + e.getMessage()
            );

            return false;
        }
    }
}