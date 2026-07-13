package grade;

import course.Course;
import student.Student;

import java.util.List;

public class GradeService {

    private final GradeRepository repository;

    public GradeService() {

        repository =
                new GradeRepository();
    }

    // =========================================================
    // ADD GRADE
    // =========================================================

    public boolean addGrade(
            Grade grade
    ) {

        if (!validateGrade(grade)) {

            return false;
        }

        grade.setLetterGrade(
                calculateLetterGrade(
                        grade.getMarks()
                )
        );

        if (repository.gradeExists(grade)) {

            return false;
        }

        return repository.addGrade(grade);
    }

    // =========================================================
    // UPDATE GRADE
    // =========================================================

    public boolean updateGrade(
            Grade grade
    ) {

        if (grade == null
                || grade.getGradeId() <= 0) {

            return false;
        }

        if (!validateGrade(grade)) {

            return false;
        }

        grade.setLetterGrade(
                calculateLetterGrade(
                        grade.getMarks()
                )
        );

        if (repository.gradeExists(grade)) {

            return false;
        }

        return repository.updateGrade(grade);
    }

    // =========================================================
    // DELETE GRADE
    // =========================================================

    public boolean deleteGrade(
            int gradeId
    ) {

        if (gradeId <= 0) {

            return false;
        }

        return repository.deleteGrade(
                gradeId
        );
    }

    // =========================================================
    // LOAD DATA
    // =========================================================

    public List<Grade> getAllGrades() {

        return repository.getAllGrades();
    }

    public List<Student> getAllStudents() {

        return repository.getAllStudents();
    }

    public List<Course> getAllCourses() {

        return repository.getAllCourses();
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private boolean validateGrade(
            Grade grade
    ) {

        return grade != null
                && grade.getStudentId() > 0
                && grade.getCourseId() > 0
                && grade.getMarks() >= 0
                && grade.getMarks() <= 100;
    }

    // =========================================================
    // LETTER GRADE
    // =========================================================

    public String calculateLetterGrade(
            double marks
    ) {

        if (marks >= 85) {

            return "A+";

        } else if (marks >= 75) {

            return "A";

        } else if (marks >= 65) {

            return "B";

        } else if (marks >= 55) {

            return "C";

        } else if (marks >= 45) {

            return "D";

        } else {

            return "F";

        }

    }

}