package main.java.com.airtribe.studentmanagement.entity;

import java.time.LocalDate;
public class GraduateStudent extends Student {
    public enum PlacementStatus {
        NOT_APPLICABLE,
        SEEKING,
        INTERVIEWING,
        PLACED
    }

    private LocalDate graduationDate;
    private PlacementStatus placementStatus = PlacementStatus.NOT_APPLICABLE;
    private String placementCompany;

    public GraduateStudent() { super(); }

    public GraduateStudent(String name, String email, LocalDate dob, String track, String cohortId,
                           LocalDate graduationDate, PlacementStatus placementStatus, String placementCompany ) {
        super(name, email, dob, track, cohortId);
        this.graduationDate = graduationDate;
        this.placementStatus = placementStatus == null ? PlacementStatus.NOT_APPLICABLE : placementStatus;
        this.placementCompany = placementCompany;
    }

    public GraduateStudent(GraduateStudent other) {
        super(other);
        if (other == null) return;
        this.graduationDate = other.graduationDate;
        this.placementStatus = other.placementStatus;
        this.placementCompany = other.placementCompany;
    }

    public LocalDate getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
    }

    public PlacementStatus getPlacementStatus() {
        return placementStatus;
    }

    public void setPlacementStatus(PlacementStatus placementStatus) {
        this.placementStatus = placementStatus == null ? PlacementStatus.NOT_APPLICABLE : placementStatus;
    }

    public String getPlacementCompany() {
        return placementCompany;
    }

    public void setPlacementCompany(String placementCompany) {
        this.placementCompany = placementCompany;
    }

    @Override
    public String getRoleDescription() {
        String status = placementStatus == null ? PlacementStatus.NOT_APPLICABLE.name() : placementStatus.name();
        return "Graduate student (" + status + ")";
    }

    @Override
    public String toString() {
        return String.format("GraduateStudent[%s, track=%s, cohort=%s, grad=%s, placement=%s%s]",
                basicInfo(),
                getTrack(),
                getCohortId(),
                graduationDate == null ? "N/A" : graduationDate,
                placementStatus,
                placementCompany == null ? "" : " (" + placementCompany + ")");
    }
}

