package timetable;

import common.DatabaseConnection;
import course.Course;
import lecturer.Lecturer;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimetableRepository {

    // =========================================================
    // ADD TIMETABLE
    // =========================================================

    public boolean addTimetable(
            Timetable timetable
    ) {

        String sql =
                "INSERT INTO TIMETABLE " +
                        "(course_id, lecturer_id, day_of_week, start_time, end_time, room, semester) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setInt(
                    1,
                    timetable.getCourseId()
            );

            statement.setInt(
                    2,
                    timetable.getLecturerId()
            );

            statement.setString(
                    3,
                    timetable.getDayOfWeek()
            );

            statement.setTime(
                    4,
                    Time.valueOf(
                            timetable.getStartTime()
                    )
            );

            statement.setTime(
                    5,
                    Time.valueOf(
                            timetable.getEndTime()
                    )
            );

            statement.setString(
                    6,
                    timetable.getRoom()
            );

            statement.setInt(
                    7,
                    timetable.getSemester()
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

    public boolean updateTimetable(
            Timetable timetable
    ) {

        String sql =
                "UPDATE TIMETABLE SET " +
                        "course_id=?, " +
                        "lecturer_id=?, " +
                        "day_of_week=?, " +
                        "start_time=?, " +
                        "end_time=?, " +
                        "room=?, " +
                        "semester=? " +
                        "WHERE timetable_id=?";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setInt(1, timetable.getCourseId());
            statement.setInt(2, timetable.getLecturerId());
            statement.setString(3, timetable.getDayOfWeek());

            statement.setTime(
                    4,
                    Time.valueOf(
                            timetable.getStartTime()
                    )
            );

            statement.setTime(
                    5,
                    Time.valueOf(
                            timetable.getEndTime()
                    )
            );

            statement.setString(
                    6,
                    timetable.getRoom()
            );

            statement.setInt(
                    7,
                    timetable.getSemester()
            );

            statement.setInt(
                    8,
                    timetable.getTimetableId()
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

    public boolean deleteTimetable(
            int timetableId
    ) {

        String sql =
                "DELETE FROM TIMETABLE " +
                        "WHERE timetable_id=?";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setInt(
                    1,
                    timetableId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

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
                        resultSet.getInt("course_id")
                );

                course.setCourseCode(
                        resultSet.getString("course_code")
                );

                course.setCourseName(
                        resultSet.getString("course_name")
                );

                courses.add(course);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return courses;

    }

    // =========================================================
    // LOAD LECTURERS
    // =========================================================

    public List<Lecturer> getAllLecturers() {

        List<Lecturer> lecturers =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "l.lecturer_id," +
                        "u.full_name " +
                        "FROM LECTURER l " +
                        "INNER JOIN USERS u " +
                        "ON l.user_id=u.user_id " +
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
                        resultSet.getInt("lecturer_id")
                );

                lecturer.setFullName(
                        resultSet.getString("full_name")
                );

                lecturers.add(
                        lecturer
                );

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return lecturers;

    }     // =========================================================
    // LOAD TIMETABLES
    // =========================================================

    public List<Timetable> getAllTimetables() {

        List<Timetable> timetables =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "t.timetable_id, " +
                        "t.course_id, " +
                        "t.lecturer_id, " +
                        "t.day_of_week, " +
                        "t.start_time, " +
                        "t.end_time, " +
                        "t.room, " +
                        "t.semester, " +
                        "c.course_code, " +
                        "c.course_name, " +
                        "u.full_name AS lecturer_name " +
                        "FROM TIMETABLE t " +
                        "INNER JOIN COURSE c " +
                        "ON t.course_id = c.course_id " +
                        "INNER JOIN LECTURER l " +
                        "ON t.lecturer_id = l.lecturer_id " +
                        "INNER JOIN USERS u " +
                        "ON l.user_id = u.user_id " +
                        "ORDER BY " +
                        "t.day_of_week, " +
                        "t.start_time";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()

        ) {

            while (resultSet.next()) {

                Timetable timetable =
                        new Timetable(

                                resultSet.getInt("timetable_id"),
                                resultSet.getInt("course_id"),
                                resultSet.getInt("lecturer_id"),
                                resultSet.getString("day_of_week"),
                                resultSet.getTime("start_time").toLocalTime(),
                                resultSet.getTime("end_time").toLocalTime(),
                                resultSet.getString("room"),
                                resultSet.getInt("semester"),
                                resultSet.getString("course_code"),
                                resultSet.getString("course_name"),
                                resultSet.getString("lecturer_name")

                        );

                timetables.add(
                        timetable
                );

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return timetables;

    }

    // =========================================================
    // CHECK LECTURER CONFLICT
    // =========================================================

    public boolean lecturerConflict(
            Timetable timetable
    ) {

        String sql =
                "SELECT timetable_id " +
                        "FROM TIMETABLE " +
                        "WHERE lecturer_id=? " +
                        "AND day_of_week=? " +
                        "AND timetable_id<>? " +
                        "AND start_time < ? " +
                        "AND end_time > ?";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setInt(
                    1,
                    timetable.getLecturerId()
            );

            statement.setString(
                    2,
                    timetable.getDayOfWeek()
            );

            statement.setInt(
                    3,
                    timetable.getTimetableId()
            );

            statement.setTime(
                    4,
                    Time.valueOf(
                            timetable.getEndTime()
                    )
            );

            statement.setTime(
                    5,
                    Time.valueOf(
                            timetable.getStartTime()
                    )
            );

            ResultSet resultSet =
                    statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {

            return false;

        }

    }

    // =========================================================
    // CHECK ROOM CONFLICT
    // =========================================================

    public boolean roomConflict(
            Timetable timetable
    ) {

        String sql =
                "SELECT timetable_id " +
                        "FROM TIMETABLE " +
                        "WHERE room=? " +
                        "AND day_of_week=? " +
                        "AND timetable_id<>? " +
                        "AND start_time < ? " +
                        "AND end_time > ?";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)

        ) {

            statement.setString(
                    1,
                    timetable.getRoom()
            );

            statement.setString(
                    2,
                    timetable.getDayOfWeek()
            );

            statement.setInt(
                    3,
                    timetable.getTimetableId()
            );

            statement.setTime(
                    4,
                    Time.valueOf(
                            timetable.getEndTime()
                    )
            );

            statement.setTime(
                    5,
                    Time.valueOf(
                            timetable.getStartTime()
                    )
            );

            ResultSet resultSet =
                    statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {

            return false;

        }

    }

}