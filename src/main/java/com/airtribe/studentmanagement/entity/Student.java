package main.java.com.airtribe.studentmanagement.entity;

import main.java.com.airtribe.studentmanagement.interfaces.Searchable;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
public class Student extends User implements Searchable {
    private static final AtomicInteger COUNTER = new AtomicInteger(1000);

    private String studentId;
    private String cohortId;
    private String track;
    private boolean active = true;

    public Student() {
        super();
        this.studentId = generateStudentId();
        setId(this.studentId);
    }

    public Student(String name, String email, LocalDate dateOfBirth, String track, String cohortId) {
        super(null, name, email, dateOfBirth);
        this.studentId = generateStudentId();
        setId(this.studentId);
        this.track = track;
        this.cohortId = cohortId;
    }

    public Student(Student other) {
        super(other);
        if (other == null) return;
        this.studentId = other.studentId;
        this.cohortId = other.cohortId;
        this.track = other.track;
        this.active = other.active;
        setId(this.studentId);
    }

    private static String generateStudentId() {
        return "Student" + COUNTER.getAndIncrement();
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; setId(studentId); }

    public String getCohortId() { return cohortId; }
    public void setCohortId(String cohortId) { this.cohortId = cohortId; }

    public String getTrack() { return track; }
    public void setTrack(String track) { this.track = track; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String getRoleDescription() {
        return "Student in track: " + (track == null ? "N/A" : track);
    }

    @Override
    public String searchableText() {
        String namePart = getName() == null ? "" : getName();
        String emailPart = getEmail() == null ? "" : getEmail();
        String trackPart = track == null ? "" : track;
        return namePart + " " + emailPart + " " + trackPart;
    }

    @Override
    public String toString() {
        return String.format("Student[%s, track=%s, cohort=%s, active=%s]",
                basicInfo(), track, cohortId, active);
    }
}
