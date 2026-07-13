package course;

import department.Department;
import lecturer.Lecturer;

import java.util.List;

public class CourseService {

    private final CourseRepository repository;

    public CourseService() {

        repository =
                new CourseRepository();

    }

    // =====================================================
    // ADD
    // =====================================================

    public boolean addCourse(
            Course course
    ) {

        if (!validateCourse(course)) {

            return false;

        }

        if (repository.courseCodeExists(
                course.getCourseCode(),
                0
        )) {

            return false;

        }

        return repository.addCourse(
                course
        );

    }

    // =====================================================
    // UPDATE
    // =====================================================

    public boolean updateCourse(
            Course course
    ) {

        if (!validateCourse(course)) {

            return false;

        }

        if (repository.courseCodeExists(
                course.getCourseCode(),
                course.getCourseId()
        )) {

            return false;

        }

        return repository.updateCourse(
                course
        );

    }

    // =====================================================
    // DELETE
    // =====================================================

    public boolean deleteCourse(
            int courseId
    ) {

        return repository.deleteCourse(
                courseId
        );

    }

    // =====================================================
    // LOAD
    // =====================================================

    public List<Course> getAllCourses() {

        return repository.getAllCourses();

    }

    public List<Department> getAllDepartments() {

        return repository.getAllDepartments();

    }

    public List<Lecturer> getAllLecturers() {

        return repository.getAllLecturers();

    }

    // =====================================================
    // VALIDATION
    // =====================================================

    private boolean validateCourse(
            Course course
    ) {

        return course != null

                && notEmpty(
                course.getCourseCode()
        )

                && notEmpty(
                course.getCourseName()
        )

                && course.getCredits() > 0

                && course.getDepartmentId() > 0;

    }

    private boolean notEmpty(
            String value
    ) {

        return value != null &&
                !value.trim().isEmpty();

    }

}