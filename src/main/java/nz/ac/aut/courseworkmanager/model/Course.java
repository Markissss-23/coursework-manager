package nz.ac.aut.courseworkmanager.model;

public class Course {
    private int id;
    private String code;
    private String name;
    private String semester;

    public Course() {

    }

    public Course(String code, String name, String semester) {
        this.code = code;
        this.name = name;
        this.semester = semester;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
