package main.java.com.airtribe.studentmanagement.entity;

import java.time.LocalDate;

public class Cohort {
    private String cohortId;
    private String name;
    private String courseId;
    private LocalDate startDate;
    private LocalDate endDate;

    public Cohort() {}

    public Cohort(String cohortId, String name, String courseId, LocalDate startDate, LocalDate endDate) {
        this.cohortId = cohortId;
        this.name = name;
        this.courseId = courseId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCohortId() { return cohortId; }
    public void setCohortId(String cohortId) { this.cohortId = cohortId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    @Override
    public String toString() {
        return String.format("Cohort[id=%s, name=%s, courseId=%s, start=%s, end=%s]",
                cohortId, name, courseId,
                startDate == null ? "N/A" : startDate.toString(),
                endDate == null ? "N/A" : endDate.toString());
    }
}
