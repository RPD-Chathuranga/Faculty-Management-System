package timetable;

import course.Course;
import lecturer.Lecturer;

import java.time.LocalTime;
import java.util.List;

public class TimetableService {

    private final TimetableRepository repository;

    public TimetableService() {

        repository =
                new TimetableRepository();

    }

    // =====================================================
    // ADD
    // =====================================================

    public boolean addTimetable(
            Timetable timetable
    ) {

        if (!validateTimetable(timetable)) {

            return false;

        }

        if (repository.lecturerConflict(timetable)) {

            return false;

        }

        if (repository.roomConflict(timetable)) {

            return false;

        }

        return repository.addTimetable(
                timetable
        );

    }

    // =====================================================
    // UPDATE
    // =====================================================

    public boolean updateTimetable(
            Timetable timetable
    ) {

        if (!validateTimetable(timetable)) {

            return false;

        }

        if (repository.lecturerConflict(timetable)) {

            return false;

        }

        if (repository.roomConflict(timetable)) {

            return false;

        }

        return repository.updateTimetable(
                timetable
        );

    }

    // =====================================================
    // DELETE
    // =====================================================

    public boolean deleteTimetable(
            int timetableId
    ) {

        return repository.deleteTimetable(
                timetableId
        );

    }

    // =====================================================
    // LOAD
    // =====================================================

    public List<Timetable> getAllTimetables() {

        return repository.getAllTimetables();

    }

    public List<Course> getAllCourses() {

        return repository.getAllCourses();

    }

    public List<Lecturer> getAllLecturers() {

        return repository.getAllLecturers();

    }

    // =====================================================
    // VALIDATION
    // =====================================================

    private boolean validateTimetable(
            Timetable timetable
    ) {

        return timetable != null

                && timetable.getCourseId() > 0

                && timetable.getLecturerId() > 0

                && notEmpty(
                timetable.getDayOfWeek()
        )

                && timetable.getStartTime() != null

                && timetable.getEndTime() != null

                && timetable.getStartTime().isBefore(
                timetable.getEndTime()
        )

                && timetable.getSemester() >= 1

                && timetable.getSemester() <= 8;

    }

    private boolean notEmpty(
            String value
    ) {

        return value != null
                && !value.trim().isEmpty();

    }

    // =====================================================
    // OPTIONAL TIME VALIDATION
    // =====================================================

    public boolean isValidTimeRange(
            LocalTime start,
            LocalTime end
    ) {

        if (start == null || end == null) {

            return false;

        }

        return start.isBefore(end);

    }

}