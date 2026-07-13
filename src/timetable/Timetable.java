package timetable;

import java.time.LocalTime;

public class Timetable {

    // TIMETABLE table fields
    private int timetableId;
    private int courseId;
    private int lecturerId;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;
    private int semester;

    // Joined values for displaying in the table
    private String courseCode;
    private String courseName;
    private String lecturerName;

    public Timetable() {
    }

    // Used when adding a timetable record
    public Timetable(
            int courseId,
            int lecturerId,
            String dayOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            String room,
            int semester
    ) {
        this.courseId = courseId;
        this.lecturerId = lecturerId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.semester = semester;
    }

    // Used when updating a timetable record
    public Timetable(
            int timetableId,
            int courseId,
            int lecturerId,
            String dayOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            String room,
            int semester
    ) {
        this.timetableId = timetableId;
        this.courseId = courseId;
        this.lecturerId = lecturerId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.semester = semester;
    }

    // Used when loading records with SQL JOINs
    public Timetable(
            int timetableId,
            int courseId,
            int lecturerId,
            String dayOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            String room,
            int semester,
            String courseCode,
            String courseName,
            String lecturerName
    ) {
        this.timetableId = timetableId;
        this.courseId = courseId;
        this.lecturerId = lecturerId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.semester = semester;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.lecturerName = lecturerName;
    }

    public int getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(
            int timetableId
    ) {
        this.timetableId = timetableId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(
            int courseId
    ) {
        this.courseId = courseId;
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(
            int lecturerId
    ) {
        this.lecturerId = lecturerId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(
            String dayOfWeek
    ) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(
            LocalTime startTime
    ) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(
            LocalTime endTime
    ) {
        this.endTime = endTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(
            String room
    ) {
        this.room = room;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(
            int semester
    ) {
        this.semester = semester;
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

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(
            String lecturerName
    ) {
        this.lecturerName = lecturerName;
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

        return getCourseDisplayName()
                + " | "
                + dayOfWeek
                + " "
                + startTime
                + " - "
                + endTime;
    }
}