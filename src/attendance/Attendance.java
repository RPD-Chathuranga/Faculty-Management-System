package attendance;

import java.time.LocalDate;

public class Attendance {

    // ATTENDANCE table fields
    private int attendanceId;
    private int studentId;
    private int courseId;
    private LocalDate date;
    private String status;

    // Joined display values
    private String studentName;
    private String registrationNumber;
    private String courseCode;
    private String courseName;

    public Attendance() {
    }

    // Used when adding attendance
    public Attendance(
            int studentId,
            int courseId,
            LocalDate date,
            String status
    ) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.date = date;
        this.status = status;
    }

    // Used when updating attendance
    public Attendance(
            int attendanceId,
            int studentId,
            int courseId,
            LocalDate date,
            String status
    ) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.date = date;
        this.status = status;
    }

    // Used when loading joined records
    public Attendance(
            int attendanceId,
            int studentId,
            int courseId,
            LocalDate date,
            String status,
            String studentName,
            String registrationNumber,
            String courseCode,
            String courseName
    ) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.date = date;
        this.status = status;
        this.studentName = studentName;
        this.registrationNumber = registrationNumber;
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(
            int attendanceId
    ) {
        this.attendanceId = attendanceId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(
            int studentId
    ) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(
            int courseId
    ) {
        this.courseId = courseId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(
            LocalDate date
    ) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status
    ) {
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(
            String studentName
    ) {
        this.studentName = studentName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(
            String registrationNumber
    ) {
        this.registrationNumber = registrationNumber;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(
            String courseCode
    ) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(
            String courseName
    ) {
        this.courseName = courseName;
    }

    public String getStudentDisplayName() {

        if (registrationNumber == null
                || registrationNumber.trim().isEmpty()) {

            return studentName == null
                    ? ""
                    : studentName;
        }

        if (studentName == null
                || studentName.trim().isEmpty()) {

            return registrationNumber;
        }

        return registrationNumber
                + " - "
                + studentName;
    }

    public String getCourseDisplayName() {

        if (courseCode == null
                || courseCode.trim().isEmpty()) {

            return courseName == null
                    ? ""
                    : courseName;
        }

        if (courseName == null
                || courseName.trim().isEmpty()) {

            return courseCode;
        }

        return courseCode
                + " - "
                + courseName;
    }

    @Override
    public String toString() {

        return getStudentDisplayName()
                + " | "
                + getCourseDisplayName()
                + " | "
                + date
                + " | "
                + status;
    }
}