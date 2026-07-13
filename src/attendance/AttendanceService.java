package attendance;

import course.Course;
import student.Student;

import java.util.List;

public class AttendanceService {

    private final AttendanceRepository repository;

    public AttendanceService() {

        repository =
                new AttendanceRepository();
    }

    // =========================================================
    // ADD ATTENDANCE
    // =========================================================

    public boolean addAttendance(
            Attendance attendance
    ) {

        if (!validateAttendance(
                attendance
        )) {

            return false;
        }

        if (repository.attendanceExists(
                attendance
        )) {

            return false;
        }

        return repository.addAttendance(
                attendance
        );
    }

    // =========================================================
    // UPDATE ATTENDANCE
    // =========================================================

    public boolean updateAttendance(
            Attendance attendance
    ) {

        if (attendance == null
                || attendance.getAttendanceId() <= 0) {

            return false;
        }

        if (!validateAttendance(
                attendance
        )) {

            return false;
        }

        if (repository.attendanceExists(
                attendance
        )) {

            return false;
        }

        return repository.updateAttendance(
                attendance
        );
    }

    // =========================================================
    // DELETE ATTENDANCE
    // =========================================================

    public boolean deleteAttendance(
            int attendanceId
    ) {

        if (attendanceId <= 0) {

            return false;
        }

        return repository.deleteAttendance(
                attendanceId
        );
    }

    // =========================================================
    // LOAD ATTENDANCE
    // =========================================================

    public List<Attendance> getAllAttendance() {

        return repository.getAllAttendance();
    }

    // =========================================================
    // LOAD STUDENTS
    // =========================================================

    public List<Student> getAllStudents() {

        return repository.getAllStudents();
    }

    // =========================================================
    // LOAD COURSES
    // =========================================================

    public List<Course> getAllCourses() {

        return repository.getAllCourses();
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private boolean validateAttendance(
            Attendance attendance
    ) {

        return attendance != null
                && attendance.getStudentId() > 0
                && attendance.getCourseId() > 0
                && attendance.getDate() != null
                && isValidStatus(
                attendance.getStatus()
        );
    }

    public boolean isValidStatus(
            String status
    ) {

        return "PRESENT".equals(status)
                || "ABSENT".equals(status)
                || "LATE".equals(status);
    }
}