package main.java.com.airtribe.studentmanagement.entity;

import java.time.LocalDate;
public abstract class User {
    private String id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;

    protected String basicInfo() {
        return String.format("id=%s, name=%s, email=%s", id, name, email);
    }

    public abstract String getRoleDescription();

    public User() {}

    public User(String id, String name, String email, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public User(User other) {
        if (other == null) return;
        this.id = other.id;
        this.name = other.name;
        this.email = other.email;
        this.dateOfBirth = other.dateOfBirth;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDob() { return dateOfBirth; }
    public void setDob(LocalDate dob) { this.dateOfBirth = dob; }

}
