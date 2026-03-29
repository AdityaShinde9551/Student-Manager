package com.example.studentmanager.controller;

import com.example.studentmanager.model.Student;
import com.example.studentmanager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // ✅ Dashboard — show all students
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("newStudent", new Student()); // for add form
        return "dashboard"; // → templates/dashboard.html
    }

    // ✅ Add a new student
    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute("newStudent") Student student) {
        studentRepository.save(student);
        return "redirect:/dashboard";
    }

    // ✅ Delete a student by ID
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return "redirect:/dashboard";
    }
}
