package student;

public class Student {

    // STUDENT table fields
    private int studentId;
    private int userId;
    private String registrationNumber;
    private int semester;
    private int departmentId;

    // USERS table fields
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;

    // Joined display value
    private String departmentName;

    public Student() {
    }

    // Used when adding a new student
    public Student(
            String username,
            String password,
            String fullName,
            String email,
            String phone,
            String registrationNumber,
            int semester,
            int departmentId
    ) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.registrationNumber = registrationNumber;
        this.semester = semester;
        this.departmentId = departmentId;
    }

    // Used when updating a student
    public Student(
            int studentId,
            int userId,
            String username,
            String password,
            String fullName,
            String email,
            String phone,
            String registrationNumber,
            int semester,
            int departmentId
    ) {
        this.studentId = studentId;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.registrationNumber = registrationNumber;
        this.semester = semester;
        this.departmentId = departmentId;
    }

    // Used when loading joined records
    public Student(
            int studentId,
            int userId,
            String username,
            String fullName,
            String email,
            String phone,
            String registrationNumber,
            int semester,
            int departmentId,
            String departmentName
    ) {
        this.studentId = studentId;
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.registrationNumber = registrationNumber;
        this.semester = semester;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(
            int studentId
    ) {
        this.studentId = studentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(
            int userId
    ) {
        this.userId = userId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(
            String registrationNumber
    ) {
        this.registrationNumber = registrationNumber;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(
            int semester
    ) {
        this.semester = semester;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(
            int departmentId
    ) {
        this.departmentId = departmentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(
            String username
    ) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(
            String password
    ) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(
            String fullName
    ) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(
            String email
    ) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(
            String phone
    ) {
        this.phone = phone;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(
            String departmentName
    ) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {

        if (fullName != null
                && !fullName.trim().isEmpty()) {

            return fullName;
        }

        return registrationNumber == null
                ? ""
                : registrationNumber;
    }
}