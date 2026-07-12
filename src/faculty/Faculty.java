package faculty;

public class Faculty {

    private int facultyId;
    private String facultyName;
    private String description;

    public Faculty() {
    }

    public Faculty(
            int facultyId,
            String facultyName,
            String description
    ) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.description = description;
    }

    public Faculty(
            String facultyName,
            String description
    ) {
        this.facultyName = facultyName;
        this.description = description;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return facultyName;
    }
}