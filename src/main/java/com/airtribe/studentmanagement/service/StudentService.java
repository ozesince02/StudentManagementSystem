package main.java.com.airtribe.studentmanagement.service;

import main.java.com.airtribe.studentmanagement.entity.GraduateStudent;
import main.java.com.airtribe.studentmanagement.entity.Student;
import main.java.com.airtribe.studentmanagement.exception.InvalidDataException;
import main.java.com.airtribe.studentmanagement.exception.StudentNotFoundException;
import main.java.com.airtribe.studentmanagement.util.Database;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentService {
    private final Database db = Database.getInstance();

    public StudentService() {}

    public void addStudent(Student s) throws InvalidDataException {
        if (s == null) throw new InvalidDataException("Student cannot be null");
        if (s.getName() == null || s.getName().isBlank()) throw new InvalidDataException("Student name is required");
        if (s.getEmail() == null || s.getEmail().isBlank()) throw new InvalidDataException("Student email is required");
        boolean emailExists = db.getStudents().stream().anyMatch(st -> st.getEmail() != null && st.getEmail().equalsIgnoreCase(s.getEmail()));
        if (emailExists) throw new InvalidDataException("Email already exists: " + s.getEmail());
        db.getStudents().add(s);
    }

    public Optional<Student> findById(String studentId) {
        if (studentId == null) return Optional.empty();
        return db.getStudents().stream().filter(s -> studentId.equals(s.getStudentId())).findFirst();
    }

    public void updateStudent(Student updated) throws StudentNotFoundException, InvalidDataException {
        if (updated == null) throw new InvalidDataException("Updated student cannot be null");
        String sid = updated.getStudentId();
        if (sid == null) throw new InvalidDataException("Student id is required for update");
        Student existing = findById(sid).orElseThrow(() -> new StudentNotFoundException("Student not found: " + sid));
        existing.setName(updated.getName() == null ? existing.getName() : updated.getName());
        existing.setEmail(updated.getEmail() == null ? existing.getEmail() : updated.getEmail());
        existing.setDob(updated.getDob() == null ? existing.getDob() : updated.getDob());
        existing.setTrack(updated.getTrack() == null ? existing.getTrack() : updated.getTrack());
        existing.setCohortId(updated.getCohortId() == null ? existing.getCohortId() : updated.getCohortId());
        existing.setActive(updated.isActive());
    }

    public void promoteOrUpdateGraduateStudent(String studentId, LocalDate graduationDate,
        GraduateStudent.PlacementStatus status, String placementCompany) throws StudentNotFoundException {
        if (studentId == null || studentId.isBlank()) throw new StudentNotFoundException("Student not found: " + studentId);
        List<Student> students = db.getStudents();
        int index = -1;
        for (int i = 0; i < students.size(); i++) {
            if (studentId.equals(students.get(i).getStudentId())) {
                index = i;
                break;
            }
        }
        if (index == -1) throw new StudentNotFoundException("Student not found: " + studentId);
        Student current = students.get(index);
        if (current instanceof GraduateStudent) {
            GraduateStudent existingGrad = (GraduateStudent) current;
            if (graduationDate != null) existingGrad.setGraduationDate(graduationDate);
            if (status != null) existingGrad.setPlacementStatus(status);
            if (placementCompany != null && !placementCompany.isBlank()) existingGrad.setPlacementCompany(placementCompany);
        } else {
            GraduateStudent grad = new GraduateStudent(
                current.getName(),
                current.getEmail(),
                current.getDob(),
                current.getTrack(),
                current.getCohortId(),
                graduationDate,
                status,
                placementCompany
            );
            grad.setStudentId(current.getStudentId());
            grad.setActive(current.isActive());
            students.set(index, grad);
        }
    }

    public void deleteStudent(String studentId) throws StudentNotFoundException {
        boolean removed = db.getStudents().removeIf(s -> s.getStudentId().equals(studentId));
        if (!removed) throw new StudentNotFoundException("Student not found: " + studentId);
        db.getEnrollments().removeIf(e -> e.getStudentId().equals(studentId));
    }

    public List<Student> getAllStudents() {
        return db.snapshotStudents();
    }

    public List<Student> searchByName(String keyword) {
        return db.getStudents().stream()
                .filter(s -> s.matches(keyword))
                .collect(Collectors.toList());
    }
}
