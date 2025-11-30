package main.java.com.airtribe.studentmanagement.service;

import main.java.com.airtribe.studentmanagement.entity.Cohort;
import main.java.com.airtribe.studentmanagement.exception.InvalidDataException;
import main.java.com.airtribe.studentmanagement.util.Database;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CohortService {
    private final Database db = Database.getInstance();

    public CohortService() {}

    public void createCohort(Cohort cohort) throws InvalidDataException {
        if (cohort == null) throw new InvalidDataException("Cohort cannot be null");
        if (cohort.getCohortId() == null || cohort.getCohortId().isBlank()) throw new InvalidDataException("Cohort id required");
        if (cohort.getCourseId() == null || cohort.getCourseId().isBlank()) throw new InvalidDataException("Associated courseId required");
        String newId = cohort.getCohortId();
        boolean exists = db.getCohorts().stream()
                .anyMatch(c -> c.getCohortId() != null && c.getCohortId().equalsIgnoreCase(newId));
        if (exists) throw new InvalidDataException("Cohort id already exists: " + cohort.getCohortId());
        db.getCohorts().add(cohort);
    }

    public Optional<Cohort> findById(String cohortId) {
        if (cohortId == null) return Optional.empty();
        String target = cohortId.toLowerCase();
        return db.getCohorts().stream()
                .filter(c -> c.getCohortId() != null && c.getCohortId().toLowerCase().equals(target))
                .findFirst();
    }

    public List<Cohort> listByCourseId(String courseId) {
        if (courseId == null) return List.of();
        String target = courseId.toLowerCase();
        return db.getCohorts().stream()
                .filter(c -> c.getCourseId() != null && c.getCourseId().toLowerCase().equals(target))
                .collect(Collectors.toList());
    }

    public List<Cohort> listAll() {
        return db.snapshotCohorts();
    }
}
