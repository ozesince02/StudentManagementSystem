package main.java.com.airtribe.studentmanagement.entity;

import main.java.com.airtribe.studentmanagement.interfaces.Searchable;

import java.util.ArrayList;
import java.util.List;

public class Course implements Searchable {
    private String courseId;
    private String name;
    private int credits;
    private List<String> tracks = new ArrayList<>();

    public Course() {}

    public Course(String courseId, String name, int credits) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
    }

    public Course(String courseId, String name, int credits, List<String> tracks) {
        this(courseId, name, credits);
        if (tracks != null) this.tracks = new ArrayList<>(tracks);
    }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public List<String> getTracks() { return tracks; }
    public void setTracks(List<String> tracks) {
        this.tracks = tracks == null ? new ArrayList<>() : new ArrayList<>(tracks);
    }

    public void addTrack(String track) {
        if (track != null && !track.isBlank() && !tracks.contains(track)) {
            tracks.add(track);
        }
    }

    public void removeTrack(String track) {
        tracks.remove(track);
    }

    @Override
    public String searchableText() {
        return name == null ? "" : name;
    }

    @Override
    public String toString() {
        return String.format("Course[id=%s, name=%s, credits=%d, tracks=%s]",
                courseId, name, credits, tracks);
    }
}

