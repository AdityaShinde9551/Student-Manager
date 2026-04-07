package com.example.studentmanager.controller;

import com.example.studentmanager.model.Student;
import com.example.studentmanager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // ── Dashboard — show all students + stats ──
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Student> students = studentRepository.findAll();

        // ── Stats Processing ──
        long totalStudents = students.size();

        long passed = students.stream()
                .filter(s -> s.getResult().equals("Pass"))
                .count();

        long failed = totalStudents - passed;

        double avgPercentage = students.stream()
                .mapToDouble(Student::getPercentage)
                .average()
                .orElse(0.0);
        // Round to 2 decimal places
        avgPercentage = Math.round(avgPercentage * 100.0) / 100.0;

        // Grade counts
        long gradeO = students.stream().filter(s -> s.getGrade().equals("O")).count();
        long gradeA = students.stream().filter(s -> s.getGrade().equals("A")).count();
        long gradeB = students.stream().filter(s -> s.getGrade().equals("B")).count();
        long gradeC = students.stream().filter(s -> s.getGrade().equals("C")).count();
        long gradeD = students.stream().filter(s -> s.getGrade().equals("D")).count();
        long gradeF = students.stream().filter(s -> s.getGrade().equals("F")).count();

        // Send everything to the HTML template
        model.addAttribute("students", students);
        model.addAttribute("newStudent", new Student());
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("passed", passed);
        model.addAttribute("failed", failed);
        model.addAttribute("avgPercentage", avgPercentage);
        model.addAttribute("gradeO", gradeO);
        model.addAttribute("gradeA", gradeA);
        model.addAttribute("gradeB", gradeB);
        model.addAttribute("gradeC", gradeC);
        model.addAttribute("gradeD", gradeD);
        model.addAttribute("gradeF", gradeF);

        return "dashboard";
    }

    // ── Add new student ──
    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute("newStudent") Student student) {
        studentRepository.save(student);
        return "redirect:/dashboard";
    }

    // ── Show edit form — pre-filled with student data ──
    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));
        model.addAttribute("student", student);
        return "edit"; // → templates/edit.html
    }

    // ── Save edited student ──
    @PostMapping("/students/edit/{id}")
    public String saveEdit(@PathVariable Long id,
                           @ModelAttribute("student") Student updatedStudent) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));

        // Update all fields
        existing.setName(updatedStudent.getName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setCourse(updatedStudent.getCourse());
        existing.setMarksJava(updatedStudent.getMarksJava());
        existing.setMarksWeb(updatedStudent.getMarksWeb());
        existing.setMarksOs(updatedStudent.getMarksOs());
        existing.setMarksTnm(updatedStudent.getMarksTnm());
        existing.setMarksToc(updatedStudent.getMarksToc());

        studentRepository.save(existing);
        return "redirect:/dashboard";
    }

    // ── Delete student ──
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return "redirect:/dashboard";
    }
}
