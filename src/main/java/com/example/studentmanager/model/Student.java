package com.example.studentmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String course;

    // ── B.Tech Computer Engineering Subjects ──
    // Each subject is out of 100. Default 0 so old students don't break.
    @Column(nullable = false, columnDefinition = "int default 0")
    private int marksJava;   // Java Programming

    @Column(nullable = false, columnDefinition = "int default 0")
    private int marksWeb;    // Web Technology

    @Column(nullable = false, columnDefinition = "int default 0")
    private int marksOs;     // Operating System

    @Column(nullable = false, columnDefinition = "int default 0")
    private int marksTnm;    // Transform & Numerical Methods

    @Column(nullable = false, columnDefinition = "int default 0")
    private int marksToc;    // Theory of Computation

    // ── Constructors ──
    public Student() {}

    public Student(String name, String email, String course) {
        this.name = name;
        this.email = email;
        this.course = course;
    }

    // ──────────────────────────────────────────
    // PROCESSING METHODS — auto calculated,
    // never stored in DB, computed on the fly
    // ──────────────────────────────────────────

    // Total marks out of 500
    public int getTotalMarks() {
        return marksJava + marksWeb + marksOs + marksTnm + marksToc;
    }

    // Percentage rounded to 2 decimal places
    public double getPercentage() {
        return Math.round((getTotalMarks() / 500.0) * 100.0 * 100.0) / 100.0;
    }

    // B.Tech grading system
    public String getGrade() {
        double p = getPercentage();
        if (p >= 75) return "O";   // Outstanding
        if (p >= 65) return "A";
        if (p >= 55) return "B";
        if (p >= 45) return "C";
        if (p >= 40) return "D";
        return "F";                // Fail
    }

    // Pass if grade is not F
    public String getResult() {
        return getGrade().equals("F") ? "Fail" : "Pass";
    }

    // ── Getters & Setters ──
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public int getMarksJava() { return marksJava; }
    public void setMarksJava(int marksJava) { this.marksJava = marksJava; }

    public int getMarksWeb() { return marksWeb; }
    public void setMarksWeb(int marksWeb) { this.marksWeb = marksWeb; }

    public int getMarksOs() { return marksOs; }
    public void setMarksOs(int marksOs) { this.marksOs = marksOs; }

    public int getMarksTnm() { return marksTnm; }
    public void setMarksTnm(int marksTnm) { this.marksTnm = marksTnm; }

    public int getMarksToc() { return marksToc; }
    public void setMarksToc(int marksToc) { this.marksToc = marksToc; }
}
