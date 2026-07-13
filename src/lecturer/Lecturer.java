package lecturer;

public class Lecturer {

    // LECTURER table fields
    private int lecturerId;
    private int userId;
    private String employeeNumber;
    private String specialization;
    private int departmentId;

    // USERS table fields
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;

    // Joined display value
    private String departmentName;

    public Lecturer() {
    }

    // Used when adding a new lecturer
    public Lecturer(
            String username,
            String password,
            String fullName,
            String email,
            String phone,
            String employeeNumber,
            String specialization,
            int departmentId
    ) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.employeeNumber = employeeNumber;
        this.specialization = specialization;
        this.departmentId = departmentId;
    }

    // Used when updating a lecturer
    public Lecturer(
            int lecturerId,
            int userId,
            String username,
            String password,
            String fullName,
            String email,
            String phone,
            String employeeNumber,
            String specialization,
            int departmentId
    ) {
        this.lecturerId = lecturerId;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.employeeNumber = employeeNumber;
        this.specialization = specialization;
        this.departmentId = departmentId;
    }

    // Used when loading lecturer records with JOIN
    public Lecturer(
            int lecturerId,
            int userId,
            String username,
            String fullName,
            String email,
            String phone,
            String employeeNumber,
            String specialization,
            int departmentId,
            String departmentName
    ) {
        this.lecturerId = lecturerId;
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.employeeNumber = employeeNumber;
        this.specialization = specialization;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Keeps compatibility with older controller/view code
    public String getPhoneNumber() {
        return phone;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone = phoneNumber;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {

        if (fullName != null && !fullName.trim().isEmpty()) {
            return fullName;
        }

        return employeeNumber == null
                ? ""
                : employeeNumber;
    }
}