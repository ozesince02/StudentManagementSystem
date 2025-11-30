package main.java.com.airtribe.studentmanagement;

import main.java.com.airtribe.studentmanagement.entity.Cohort;
import main.java.com.airtribe.studentmanagement.entity.Course;
import main.java.com.airtribe.studentmanagement.entity.Enrollment;
import main.java.com.airtribe.studentmanagement.entity.GraduateStudent;
import main.java.com.airtribe.studentmanagement.entity.Student;
import main.java.com.airtribe.studentmanagement.entity.User;
import main.java.com.airtribe.studentmanagement.exception.InvalidDataException;
import main.java.com.airtribe.studentmanagement.exception.StudentNotFoundException;
import main.java.com.airtribe.studentmanagement.service.CohortService;
import main.java.com.airtribe.studentmanagement.service.CourseService;
import main.java.com.airtribe.studentmanagement.service.EnrollmentService;
import main.java.com.airtribe.studentmanagement.service.RecommendationService;
import main.java.com.airtribe.studentmanagement.service.StudentService;
import main.java.com.airtribe.studentmanagement.util.InputValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
public class Main {

    private StudentService studentService = new StudentService();
    private CourseService courseService = new CourseService();
    private CohortService cohortService = new CohortService();
    private EnrollmentService enrollmentService = new EnrollmentService();
    private RecommendationService recommendationService = new RecommendationService();

    public static void main(String[] args) {
        Main app = new Main();
        app.seedSampleData();
        app.runMenu();
    }

    private void seedSampleData() {
        try {
            Course java = new Course("JAVA101", "Backend Engineering Course", 5);
            java.addTrack("java");
            java.addTrack("node.js");

            Course dsa = new Course("DSA101", "Data Structures & Algorithms", 5);
            dsa.addTrack("general");

            courseService.createCourse(java);
            courseService.createCourse(dsa);

            Cohort c1 = new Cohort("C1", "Oct Backend Cohort", "JAVA101",
                    LocalDate.now().minusDays(7), LocalDate.now().plusMonths(3));
            Cohort c2 = new Cohort("C2", "Oct DSA Cohort", "DSA101",
                    LocalDate.now().plusDays(7), LocalDate.now().plusMonths(4));

            cohortService.createCohort(c1);
            cohortService.createCohort(c2);

            System.out.println("Seeded courses:");
            courseService.listAll().forEach(System.out::println);

            System.out.println("Seeded cohorts:");
            cohortService.listAll().forEach(System.out::println);
        } catch (InvalidDataException ex) {
            System.out.println("Sample data could not be created: " + ex.getMessage());
        }
    }

    private void runMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;
            while (!exit) {
                printMenu();
                int choice = InputValidator.readIntInRange(scanner, "Choose an option: ", 1, 11);
                try {
                    switch (choice) {
                        case 1 -> handleAddStudent(scanner);
                        case 2 -> handleListStudents();
                        case 3 -> handleSearchStudentById(scanner);
                        case 4 -> handleSearchStudentByName(scanner);
                        case 5 -> handleUpdateStudent(scanner);
                        case 6 -> handleDeleteStudent(scanner);
                        case 7 -> handleListCourses();
                        case 8 -> handleEnrollStudent(scanner);
                        case 9 -> handleViewEnrollments(scanner);
                        case 10 -> handleAiSuggestion(scanner);
                        case 11 -> exit = true;
                        default -> System.out.println("Unknown option.");
                    }
                } catch (InvalidDataException | StudentNotFoundException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
                System.out.println();
            }
        }
    }

    private void printMenu() {
        System.out.println("Student Management System");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Search Student by Name");
        System.out.println("5. Update Student");
        System.out.println("6. Delete Student");
        System.out.println("7. List Courses");
        System.out.println("8. Enroll Student in Course/Cohort");
        System.out.println("9. View Enrollments for a Student");
        System.out.println("10. Get AI Study Suggestion");
        System.out.println("11. Exit");
    }

    private void handleAddStudent(Scanner scanner) throws InvalidDataException {
        System.out.println("-- Add Student --");
        String name = InputValidator.readNonEmptyString(scanner, "Name: ");
        String email = InputValidator.readNonEmptyString(scanner, "Email: ");
        LocalDate dob = InputValidator.readDate(scanner, "Date of birth");
        String track = InputValidator.readNonEmptyString(scanner, "Track (e.g. Backend): ");

        main.java.com.airtribe.studentmanagement.entity.Student student =
                new main.java.com.airtribe.studentmanagement.entity.Student(name, email, dob, track, null);

        studentService.addStudent(student);
        System.out.println("Student created with id: " + student.getStudentId());
    }

    private void handleListStudents() {
        System.out.println("-- All Students --");
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        students.forEach(System.out::println);
    }

    private void handleSearchStudentById(Scanner scanner) {
        System.out.println("-- Search Student by ID --");
        String id = InputValidator.readNonEmptyString(scanner, "Student ID: ");
        studentService.findById(id)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("No student found with id " + id)
                );
    }

    private void handleSearchStudentByName(Scanner scanner) {
        System.out.println("-- Search Student by Name --");
        String keyword = InputValidator.readNonEmptyString(scanner, "Keyword: ");
        List<Student> results = studentService.searchByName(keyword);
        if (results.isEmpty()) {
            System.out.println("No students match that keyword.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private void handleUpdateStudent(Scanner scanner) throws InvalidDataException, StudentNotFoundException {
        System.out.println("-- Update Student --");
        String id = InputValidator.readNonEmptyString(scanner, "Student ID to update: ");

        Student existing = studentService.findById(id).orElse(null);
        if (existing == null) {
            throw new StudentNotFoundException("Student not found: " + id);
        }

        System.out.println("Leave field empty to keep the current value.");
        System.out.print("New name (" + existing.getName() + "): ");
        String newName = scanner.nextLine().trim();
        System.out.print("New email (" + existing.getEmail() + "): ");
        String newEmail = scanner.nextLine().trim();

        Student updated = new Student(existing);
        if (!newName.isEmpty()) updated.setName(newName);
        if (!newEmail.isEmpty()) updated.setEmail(newEmail);

        studentService.updateStudent(updated);
        System.out.println("Student updated.");

        boolean wasGraduate = existing instanceof GraduateStudent;
        String prompt = wasGraduate
                ? "Update graduate-specific details as well? (y/n): "
                : "Promote this student to GraduateStudent and set graduate details? (y/n): ";
        System.out.print(prompt);
        String answer = scanner.nextLine().trim();
        if (answer.equalsIgnoreCase("y")) {
            handleGraduateDetails(scanner, id, !wasGraduate);
        }
    }

    private void handleGraduateDetails(Scanner scanner, String studentId, boolean isPromotion) throws StudentNotFoundException {
        LocalDate graduationDate = InputValidator.readDate(scanner, "Graduation date");
        System.out.print("Placement status (NOT_APPLICABLE, SEEKING, INTERVIEWING, PLACED; leave empty to skip): ");
        String statusRaw = scanner.nextLine().trim();
        GraduateStudent.PlacementStatus status = null;
        if (!statusRaw.isEmpty()) {
            try {
                status = GraduateStudent.PlacementStatus.valueOf(statusRaw.toUpperCase());
            } catch (IllegalArgumentException ex) {
                status = GraduateStudent.PlacementStatus.NOT_APPLICABLE;
            }
        }
        System.out.print("Placement company (leave empty to skip): ");
        String company = scanner.nextLine().trim();
        if (company.isEmpty()) {
            company = null;
        }
        studentService.promoteOrUpdateGraduateStudent(studentId, graduationDate, status, company);
        if (isPromotion) {
            System.out.println("Student promoted to GraduateStudent with graduate details.");
        } else {
            System.out.println("Graduate student details updated.");
        }
    }

    private void handleDeleteStudent(Scanner scanner) throws StudentNotFoundException {
        System.out.println("-- Delete Student --");
        String id = InputValidator.readNonEmptyString(scanner, "Student ID to delete: ");
        studentService.deleteStudent(id);
        System.out.println("Student deleted (and related enrollments removed).");
    }

    private void handleListCourses() {
        System.out.println("-- Courses --");
        List<Course> courses = courseService.listAll();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        courses.forEach(System.out::println);
    }

    private void handleEnrollStudent(Scanner scanner) throws InvalidDataException, StudentNotFoundException {
        System.out.println("-- Enroll Student --");
        String studentId = InputValidator.readNonEmptyString(scanner, "Student ID: ");
        String courseId = InputValidator.readNonEmptyString(scanner, "Course ID: ");
        String cohortId = InputValidator.readNonEmptyString(scanner, "Cohort ID: ");

        enrollmentService.enroll(studentId, courseId, cohortId);
        System.out.println("Enrollment created.");
    }

    private void handleViewEnrollments(Scanner scanner) {
        System.out.println("-- Enrollments for Student --");
        String studentId = InputValidator.readNonEmptyString(scanner, "Student ID: ");
        List<Enrollment> enrollments = enrollmentService.listEnrollmentsForStudent(studentId);
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found for that student.");
        } else {
            enrollments.forEach(System.out::println);
        }
    }

    private void handleAiSuggestion(Scanner scanner) {
        System.out.println("-- AI Study Suggestion --");
        String studentId = InputValidator.readNonEmptyString(scanner, "Student ID: ");
        Student student = studentService.findById(studentId).orElse(null);
        if (student == null) {
            System.out.println("Student not found: " + studentId);
            return;
        }
        List<Enrollment> enrollments = enrollmentService.listEnrollmentsForStudent(studentId);
        String suggestion = recommendationService.generateStudySuggestion(student, enrollments);
        System.out.println(suggestion);
    }
}
