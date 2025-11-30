package main.java.com.airtribe.studentmanagement.service;

import main.java.com.airtribe.studentmanagement.entity.Course;
import main.java.com.airtribe.studentmanagement.exception.InvalidDataException;
import main.java.com.airtribe.studentmanagement.util.Database;

import java.util.List;
import java.util.Optional;

public class CourseService {
    private final Database db = Database.getInstance();

    public CourseService() {}

    public void createCourse(Course c) throws InvalidDataException {
        if (c == null) throw new InvalidDataException("Course cannot be null");
        if (c.getCourseId() == null || c.getCourseId().isBlank()) throw new InvalidDataException("Course id required");
        if (c.getName() == null || c.getName().isBlank()) throw new InvalidDataException("Course name required");
        String newId = c.getCourseId();
        boolean exists = db.getCourses().stream()
                .anyMatch(x -> x.getCourseId() != null && x.getCourseId().equalsIgnoreCase(newId));
        if (exists) throw new InvalidDataException("Course with id already exists: " + c.getCourseId());
        db.getCourses().add(c);
    }

    public Optional<Course> findById(String courseId) {
        if (courseId == null) return Optional.empty();
        String target = courseId.toLowerCase();
        return db.getCourses().stream()
                .filter(c -> c.getCourseId() != null && c.getCourseId().toLowerCase().equals(target))
                .findFirst();
    }

    public List<Course> listAll() {
        return db.snapshotCourses();
    }
}
