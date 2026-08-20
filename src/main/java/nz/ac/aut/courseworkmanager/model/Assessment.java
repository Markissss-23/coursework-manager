package nz.ac.aut.courseworkmanager.model;

import java.time.LocalDate;

public class Assessment {
    private int id;
    private int courseId;
    private String title;
    private LocalDate dueDate;
    private double weight;
    private AssessmentStatus status;
    private String priority;
    private String notes;

    public Assessment() {

    }

    public Assessment(int courseId, String title, LocalDate dueDate, double weight, AssessmentStatus status, String priority, String notes) {
        this.courseId = courseId;
        this.title = title;
        this.dueDate = dueDate;
        this.weight = weight;
        this.status = status;
        this.priority = priority;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public AssessmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssessmentStatus status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}