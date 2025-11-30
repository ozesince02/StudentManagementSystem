package main.java.com.airtribe.studentmanagement.entity;

import main.java.com.airtribe.studentmanagement.interfaces.Gradeable;

import java.time.LocalDate;
import java.util.Optional;
public class Enrollment implements Gradeable {
    private String studentId;
    private String courseId;
    private String cohortId;
    private LocalDate enrolledOn;
    private Double grade;
    private int attendancePerc;

    public Enrollment() {}

    public Enrollment(String studentId, String courseId, String cohortId) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.cohortId = cohortId;
        this.enrolledOn = LocalDate.now();
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getCohortId() { return cohortId; }
    public void setCohortId(String cohortId) { this.cohortId = cohortId; }

    public LocalDate getEnrolledOn() { return enrolledOn; }
    public void setEnrolledOn(LocalDate enrolledOn) { this.enrolledOn = enrolledOn; }

    public Optional<Double> getGrade() { return Optional.ofNullable(grade); }
    public void setGrade(Double grade) { this.grade = grade; }

    public int getAttendancePerc() { return attendancePerc; }
    public void setAttendancePerc(int attendancePerc) { this.attendancePerc = attendancePerc; }

    @Override
    public Double getGradeValue() {
        return grade;
    }

    @Override
    public String toString() {
        return String.format("Enrollment[student=%s, course=%s, cohort=%s, grade=%s (%s), att=%d%%, enrolled=%s]",
                studentId, courseId, cohortId,
                grade == null ? "NA" : String.format("%.2f", grade),
                gradeLabel(),
                attendancePerc,
                enrolledOn == null ? "N/A" : enrolledOn.toString());
    }
}
