package main.java.com.airtribe.studentmanagement.service;

import main.java.com.airtribe.studentmanagement.entity.Enrollment;
import main.java.com.airtribe.studentmanagement.entity.Course;
import main.java.com.airtribe.studentmanagement.entity.Cohort;
import main.java.com.airtribe.studentmanagement.entity.Student;
import main.java.com.airtribe.studentmanagement.exception.InvalidDataException;
import main.java.com.airtribe.studentmanagement.exception.StudentNotFoundException;
import main.java.com.airtribe.studentmanagement.util.Database;

import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentService {
    private final Database db = Database.getInstance();
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private final CohortService cohortService = new CohortService();

    public EnrollmentService() {}

    public void enroll(String studentId, String courseId, String cohortId) throws InvalidDataException, StudentNotFoundException {
        if (studentId == null || studentId.isBlank()) throw new InvalidDataException("Student id required");
        if (courseId == null || courseId.isBlank()) throw new InvalidDataException("Course id required");
        if (cohortId == null || cohortId.isBlank()) throw new InvalidDataException("Cohort id required");

        Student student = studentService.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Student not found: " + studentId));
        Course course = courseService.findById(courseId).orElseThrow(() -> new InvalidDataException("Course not found: " + courseId));
        Cohort cohort = cohortService.findById(cohortId).orElseThrow(() -> new InvalidDataException("Cohort not found: " + cohortId));
        String canonicalCourseId = course.getCourseId();
        String canonicalCohortId = cohort.getCohortId();
        if (cohort.getCourseId() == null || !cohort.getCourseId().equalsIgnoreCase(canonicalCourseId)) {
            throw new InvalidDataException("Cohort " + canonicalCohortId + " does not belong to course " + canonicalCourseId);
        }

        boolean already = db.getEnrollments().stream()
                .anyMatch(e -> studentId.equals(e.getStudentId()) && e.getCourseId() != null && e.getCourseId().equalsIgnoreCase(canonicalCourseId));
        if (already) throw new InvalidDataException("Student already enrolled in course: " + canonicalCourseId);

        Enrollment en = new Enrollment(studentId, canonicalCourseId, canonicalCohortId);
        db.getEnrollments().add(en);

        if (student.getCohortId() == null) student.setCohortId(canonicalCohortId);
    }

    public void assignGrade(String studentId, String courseId, double grade) throws InvalidDataException {
        if (grade < 0 || grade > 100) throw new InvalidDataException("Grade must be between 0 and 100");
        Enrollment en = db.getEnrollments().stream()
                .filter(e -> studentId.equals(e.getStudentId()) && courseId.equals(e.getCourseId()))
                .findFirst()
                .orElseThrow(() -> new InvalidDataException("Enrollment not found for student/course"));
        en.setGrade(grade);
    }

    public void updateAttendance(String studentId, String courseId, int attendancePerc) throws InvalidDataException {
        if (attendancePerc < 0 || attendancePerc > 100) throw new InvalidDataException("Attendance must be 0-100");
        Enrollment en = db.getEnrollments().stream()
                .filter(e -> studentId.equals(e.getStudentId()) && courseId.equals(e.getCourseId()))
                .findFirst()
                .orElseThrow(() -> new InvalidDataException("Enrollment not found for student/course"));
        en.setAttendancePerc(attendancePerc);
    }

    public List<Enrollment> listEnrollmentsForStudent(String studentId) {
        return db.getEnrollments().stream()
                .filter(e -> studentId.equals(e.getStudentId()))
                .collect(Collectors.toList());
    }

    public List<Enrollment> listEnrollmentsForCourse(String courseId) {
        return db.getEnrollments().stream()
                .filter(e -> courseId.equals(e.getCourseId()))
                .collect(Collectors.toList());
    }

    public void unenroll(String studentId, String courseId) throws InvalidDataException {
        boolean removed = db.getEnrollments().removeIf(e -> studentId.equals(e.getStudentId()) && courseId.equals(e.getCourseId()));
        if (!removed) throw new InvalidDataException("Enrollment not found for student/course");
    }
}
