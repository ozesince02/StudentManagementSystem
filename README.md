## Student Management System (Java, Console)

This is a simple **Student Management System** built in Java.  
It focuses on clean, readable code that demonstrates core OOP concepts and design principles.

### 1. What this project does

- Manages **students**, **courses**, **cohorts**, and **enrollments** in memory.
- Lets you:
  - Add, view, search, update, and delete students.
  - List courses and enroll students into a course + cohort.
  - View a student’s enrollments.
  - Get a small, rule‑based **“AI” study suggestion** for a student.
- Uses:
  - Inheritance: `User → Student → GraduateStudent`
  - Abstraction: abstract class `User`
  - Interfaces with default methods: `Searchable`, `Gradeable`
  - Encapsulation via private fields + getters/setters

### 2. Project structure (packages)

Code lives under `src/main/java/main/java/com/airtribe/studentmanagement`:

- `entity` – domain classes:
  - `User`, `Student`, `GraduateStudent`, `Course`, `Cohort`, `Enrollment`
- `service` – business logic:
  - `StudentService`, `CourseService`, `CohortService`, `EnrollmentService`, `RecommendationService`
- `util` – helpers:
  - `Database` (in‑memory storage), `InputValidator`, `DateUtil`
- `exception` – custom checked exceptions:
  - `InvalidDataException`, `StudentNotFoundException`
- root:
  - `Main` – console menu entry point

Tests (placeholders) are under `src/test/java`.

### 3. How to run

1. Open the project folder in your IDE.
2. Build the project (the IDE compiles everything under `src/main/java`).
3. Run the `Main` class:
   - Fully qualified name: `main.java.com.airtribe.studentmanagement.Main`

### 4. How to use the console menu

When `Main` runs, it:

1. Seeds example courses (`JAVA101`, `DSA101`) and cohorts (`C1`, `C2`) and prints them to the console.
2. Shows a menu with options:

- `1` – Add Student  
- `2` – View All Students  
- `3` – Search Student by ID  
- `4` – Search Student by Name  
- `5` – Update Student (can also promote to / edit `GraduateStudent` details)  
- `6` – Delete Student  
- `7` – List Courses  
- `8` – Enroll Student in Course/Cohort  
- `9` – View Enrollments for a Student  
- `10` – Get AI Study Suggestion  
- `11` – Exit  

Minimal example flow:

1. **Add a student** (1) – enter name, email, date of birth, and track.
2. **View all students** (2) – confirm the student appears.
3. **Search** by name (4) and by ID (3).
4. **Update** the student (5) – change name/email and optionally promote/update graduate details.
5. **List courses** (7) – see `JAVA101` and `DSA101`.
6. **Enroll** the student (8) – use the student ID, `JAVA101`, and a cohort ID (e.g. `C1`).
7. **View enrollments** (9) – check the enrollment record.
8. **Get AI suggestion** (10) – see a study recommendation based on enrollments.
9. **Exit** (11).

