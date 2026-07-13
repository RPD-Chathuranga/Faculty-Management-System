package grade;

public class Grade {

    private int gradeId;

    private int studentId;
    private int courseId;

    private double marks;
    private String letterGrade;

    // Display values
    private String studentName;
    private String registrationNumber;

    private String courseCode;
    private String courseName;

    public Grade() {
    }

    public Grade(
            int studentId,
            int courseId,
            double marks,
            String letterGrade
    ) {

        this.studentId = studentId;
        this.courseId = courseId;
        this.marks = marks;
        this.letterGrade = letterGrade;
    }

    public Grade(
            int gradeId,
            int studentId,
            int courseId,
            double marks,
            String letterGrade
    ) {

        this.gradeId = gradeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.marks = marks;
        this.letterGrade = letterGrade;
    }

    public Grade(
            int gradeId,
            int studentId,
            int courseId,
            double marks,
            String letterGrade,
            String studentName,
            String registrationNumber,
            String courseCode,
            String courseName
    ) {

        this.gradeId = gradeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.marks = marks;
        this.letterGrade = letterGrade;

        this.studentName = studentName;
        this.registrationNumber = registrationNumber;

        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
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

    public String getStudentDisplayName() {

        if (registrationNumber == null || registrationNumber.isBlank()) {
            return studentName;
        }

        return registrationNumber + " - " + studentName;
    }

    public String getCourseDisplayName() {

        if (courseCode == null || courseCode.isBlank()) {
            return courseName;
        }

        return courseCode + " - " + courseName;
    }

    @Override
    public String toString() {

        return getStudentDisplayName()
                + " | "
                + getCourseDisplayName()
                + " | "
                + marks;
    }

}