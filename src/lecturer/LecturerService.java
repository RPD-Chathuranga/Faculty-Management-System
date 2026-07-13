package lecturer;

import department.Department;

import java.util.List;

public class LecturerService {

    private final LecturerRepository repository;

    public LecturerService() {
        repository = new LecturerRepository();
    }

    // =========================================================
    // ADD
    // =========================================================

    public boolean addLecturer(Lecturer lecturer) {

        if (!validateLecturer(lecturer)) {
            return false;
        }

        if (repository.usernameExists(
                lecturer.getUsername(),
                0
        )) {
            return false;
        }

        if (repository.emailExists(
                lecturer.getEmail(),
                0
        )) {
            return false;
        }

        if (repository.employeeNumberExists(
                lecturer.getEmployeeNumber(),
                0
        )) {
            return false;
        }

        return repository.addLecturer(lecturer);
    }

    // =========================================================
    // UPDATE
    // =========================================================

    public boolean updateLecturer(Lecturer lecturer) {

        if (!validateLecturerForUpdate(lecturer)) {
            return false;
        }

        if (repository.usernameExists(
                lecturer.getUsername(),
                lecturer.getUserId()
        )) {
            return false;
        }

        if (repository.emailExists(
                lecturer.getEmail(),
                lecturer.getUserId()
        )) {
            return false;
        }

        if (repository.employeeNumberExists(
                lecturer.getEmployeeNumber(),
                lecturer.getLecturerId()
        )) {
            return false;
        }

        return repository.updateLecturer(lecturer);
    }

    // =========================================================
    // DELETE
    // =========================================================

    public boolean deleteLecturer(int userId) {

        return repository.deleteLecturer(userId);
    }

    // =========================================================
    // LOAD
    // =========================================================

    public List<Lecturer> getAllLecturers() {
        return repository.getAllLecturers();
    }

    public List<Department> getAllDepartments() {
        return repository.getAllDepartments();
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private boolean validateLecturer(Lecturer lecturer) {

        return lecturer != null
                && notEmpty(lecturer.getFullName())
                && notEmpty(lecturer.getUsername())
                && notEmpty(lecturer.getPassword())
                && notEmpty(lecturer.getEmail())
                && notEmpty(lecturer.getPhone())
                && notEmpty(lecturer.getEmployeeNumber())
                && lecturer.getDepartmentId() > 0;
    }

    private boolean validateLecturerForUpdate(
            Lecturer lecturer
    ) {

        return lecturer != null
                && lecturer.getLecturerId() > 0
                && lecturer.getUserId() > 0
                && notEmpty(lecturer.getFullName())
                && notEmpty(lecturer.getUsername())
                && notEmpty(lecturer.getEmail())
                && notEmpty(lecturer.getPhone())
                && notEmpty(lecturer.getEmployeeNumber())
                && lecturer.getDepartmentId() > 0;
    }

    private boolean notEmpty(String value) {

        return value != null &&
                !value.trim().isEmpty();
    }

}