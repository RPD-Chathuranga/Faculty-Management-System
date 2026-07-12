package department;

import faculty.Faculty;

public class Department {

    private int departmentId;
    private String departmentName;
    private int facultyId;
    private Faculty faculty;

    public Department() {
    }

    public Department(
            int departmentId,
            String departmentName,
            int facultyId
    ) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.facultyId = facultyId;
    }

    public Department(
            String departmentName,
            int facultyId
    ) {
        this.departmentName = departmentName;
        this.facultyId = facultyId;
    }

    public Department(
            int departmentId,
            String departmentName,
            int facultyId,
            Faculty faculty
    ) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.facultyId = facultyId;
        this.faculty = faculty;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(
            int departmentId
    ) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(
            String departmentName
    ) {
        this.departmentName = departmentName;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(
            int facultyId
    ) {
        this.facultyId = facultyId;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(
            Faculty faculty
    ) {
        this.faculty = faculty;
    }

    public String getFacultyName() {

        if (faculty == null) {
            return "";
        }

        return faculty.getFacultyName();
    }

    @Override
    public String toString() {
        return departmentName;
    }
}