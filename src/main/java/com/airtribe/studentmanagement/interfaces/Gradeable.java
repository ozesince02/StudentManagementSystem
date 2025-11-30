package main.java.com.airtribe.studentmanagement.interfaces;
public interface Gradeable {

    Double getGradeValue();

    default String gradeLabel() {
        Double value = getGradeValue();
        if (value == null) {
            return "Not graded";
        }
        if (value >= 85) return "Excellent";
        if (value >= 70) return "Good";
        if (value >= 50) return "Pass";
        return "Needs Improvement";
    }
}
