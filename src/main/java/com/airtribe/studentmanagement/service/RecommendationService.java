package main.java.com.airtribe.studentmanagement.service;

import main.java.com.airtribe.studentmanagement.entity.Enrollment;
import main.java.com.airtribe.studentmanagement.entity.Student;

import java.util.List;
public class RecommendationService {

    public String generateStudySuggestion(Student student, List<Enrollment> enrollments) {
        if (student == null) {
            return "No student selected.";
        }

        if (enrollments == null || enrollments.isEmpty()) {
            return String.format(
                    "Hi %s, you are not enrolled in any course yet. Start with the core course for your track (%s).",
                    safeName(student.getName()),
                    student.getTrack() == null ? "general foundations" : student.getTrack()
            );
        }

        double avgGrade = enrollments.stream()
                .map(Enrollment::getGradeValue)
                .filter(g -> g != null)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(-1);

        double avgAttendance = enrollments.stream()
                .mapToInt(Enrollment::getAttendancePerc)
                .average()
                .orElse(-1);

        StringBuilder suggestion = new StringBuilder();
        suggestion.append("Suggestion for ").append(safeName(student.getName())).append(": ");

        if (avgAttendance >= 0 && avgAttendance < 70) {
            suggestion.append("your attendance is below 70%. Try to attend more live sessions and block study time in your calendar.");
        } else if (avgGrade >= 0 && avgGrade >= 85) {
            suggestion.append("you are performing very well (average grade around ")
                    .append(String.format("%.1f", avgGrade))
                    .append("). Consider exploring advanced topics or helping peers.");
        } else if (avgGrade >= 0 && avgGrade < 50) {
            suggestion.append("your grades suggest some gaps in understanding. Revisit core lectures and practise with smaller problems first.");
        } else {
            suggestion.append("keep a steady pace, review notes after each class, and practise with small coding exercises.");
        }

        if (student.getTrack() != null && !student.getTrack().isBlank()) {
            suggestion.append(" Focus on building fundamentals in the \"")
                    .append(student.getTrack())
                    .append("\" track.");
        }

        return suggestion.toString();
    }

    private String safeName(String name) {
        return (name == null || name.isBlank()) ? "student" : name;
    }
}


