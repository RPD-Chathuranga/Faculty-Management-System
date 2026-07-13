package student;

import department.Department;

import java.util.List;

public class StudentService {

    private final StudentRepository repository;

    public StudentService() {
        repository = new StudentRepository();
    }

    // =========================================================
    // ADD STUDENT
    // =========================================================

    public boolean addStudent(
            Student student
    ) {

        if (!validateStudent(student)) {
            return false;
        }

        if (repository.usernameExists(
                student.getUsername(),
                0
        )) {
            return false;
        }

        if (repository.emailExists(
                student.getEmail(),
                0
        )) {
            return false;
        }

        if (repository.registrationExists(
                student.getRegistrationNumber(),
                0
        )) {
            return false;
        }

        return repository.addStudent(student);
    }

    // =========================================================
    // UPDATE STUDENT
    // =========================================================

    public boolean updateStudent(
            Student student
    ) {

        if (!validateStudentForUpdate(student)) {
            return false;
        }

        if (repository.usernameExists(
                student.getUsername(),
                student.getUserId()
        )) {
            return false;
        }

        if (repository.emailExists(
                student.getEmail(),
                student.getUserId()
        )) {
            return false;
        }

        if (repository.registrationExists(
                student.getRegistrationNumber(),
                student.getStudentId()
        )) {
            return false;
        }

        return repository.updateStudent(student);
    }

    // =========================================================
    // DELETE STUDENT
    // =========================================================

    public boolean deleteStudent(
            int userId
    ) {

        if (userId <= 0) {
            return false;
        }

        return repository.deleteStudent(userId);
    }

    // =========================================================
    // LOAD DATA
    // =========================================================

    public List<Student> getAllStudents() {

        return repository.getAllStudents();
    }

    public List<Department> getAllDepartments() {

        return repository.getAllDepartments();
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private boolean validateStudent(
            Student student
    ) {

        return student != null
                && isValidFullName(student.getFullName())
                && isValidUsername(student.getUsername())
                && isValidPassword(student.getPassword())
                && isValidEmail(student.getEmail())
                && isValidPhone(student.getPhone())
                && isValidRegistrationNumber(
                student.getRegistrationNumber()
        )
                && isValidSemester(student.getSemester())
                && student.getDepartmentId() > 0;
    }

    private boolean validateStudentForUpdate(
            Student student
    ) {

        return student != null
                && student.getStudentId() > 0
                && student.getUserId() > 0
                && isValidFullName(student.getFullName())
                && isValidUsername(student.getUsername())
                && isValidOptionalPassword(
                student.getPassword()
        )
                && isValidEmail(student.getEmail())
                && isValidPhone(student.getPhone())
                && isValidRegistrationNumber(
                student.getRegistrationNumber()
        )
                && isValidSemester(student.getSemester())
                && student.getDepartmentId() > 0;
    }

    public boolean isValidFullName(
            String fullName
    ) {

        return fullName != null
                && fullName.trim().length() >= 2
                && fullName.trim().length() <= 100;
    }

    public boolean isValidUsername(
            String username
    ) {

        return username != null
                && username.trim().length() >= 3
                && username.trim().length() <= 50;
    }

    public boolean isValidPassword(
            String password
    ) {

        return password != null
                && password.trim().length() >= 4;
    }

    public boolean isValidOptionalPassword(
            String password
    ) {

        return password == null
                || password.trim().isEmpty()
                || password.trim().length() >= 4;
    }

    public boolean isValidEmail(
            String email
    ) {

        return email != null
                && email.trim().length() <= 100
                && email.trim().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        );
    }

    public boolean isValidPhone(
            String phone
    ) {

        return phone != null
                && !phone.trim().isEmpty()
                && phone.trim().length() <= 15
                && phone.trim().matches(
                "[0-9+\\- ]+"
        );
    }

    public boolean isValidRegistrationNumber(
            String registrationNumber
    ) {

        return registrationNumber != null
                && registrationNumber.trim().length() >= 2
                && registrationNumber.trim().length() <= 50;
    }

    public boolean isValidSemester(
            int semester
    ) {

        return semester >= 1
                && semester <= 8;
    }
}