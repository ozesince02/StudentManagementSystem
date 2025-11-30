package main.java.com.airtribe.studentmanagement.util;

import main.java.com.airtribe.studentmanagement.entity.Student;
import main.java.com.airtribe.studentmanagement.entity.Course;
import main.java.com.airtribe.studentmanagement.entity.Cohort;
import main.java.com.airtribe.studentmanagement.entity.Enrollment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Database {

    private static volatile Database instance;

    private final List<Student> students = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();
    private final List<Cohort> cohorts = new ArrayList<>();
    private final List<Enrollment> enrollments = new ArrayList<>();

    private Database() {}

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Cohort> getCohorts() {
        return cohorts;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void clearAll() {
        students.clear();
        courses.clear();
        cohorts.clear();
        enrollments.clear();
    }

    public List<Student> snapshotStudents() {
        return Collections.unmodifiableList(new ArrayList<>(students));
    }

    public List<Course> snapshotCourses() {
        return Collections.unmodifiableList(new ArrayList<>(courses));
    }

    public List<Cohort> snapshotCohorts() {
        return Collections.unmodifiableList(new ArrayList<>(cohorts));
    }

    public List<Enrollment> snapshotEnrollments() {
        return Collections.unmodifiableList(new ArrayList<>(enrollments));
    }
}
