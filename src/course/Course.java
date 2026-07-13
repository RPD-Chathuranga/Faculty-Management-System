package course;

public class Course {

    private int courseId;
    private String courseCode;
    private String courseName;
    private int credits;

    private int departmentId;
    private String departmentName;

    private Integer lecturerId;
    private String lecturerName;

    public Course() {
    }

    // Constructor for Add
    public Course(
            String courseCode,
            String courseName,
            int credits,
            int departmentId,
            Integer lecturerId
    ) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.departmentId = departmentId;
        this.lecturerId = lecturerId;

    }

    // Constructor for Update
    public Course(
            int courseId,
            String courseCode,
            String courseName,
            int credits,
            int departmentId,
            Integer lecturerId
    ) {

        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.departmentId = departmentId;
        this.lecturerId = lecturerId;

    }

    // Constructor for Table
    public Course(
            int courseId,
            String courseCode,
            String courseName,
            int credits,
            int departmentId,
            String departmentName,
            Integer lecturerId,
            String lecturerName
    ) {

        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.lecturerId = lecturerId;
        this.lecturerName = lecturerName;

    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    @Override
    public String toString() {

        return courseCode + " - " + courseName;

    }

}