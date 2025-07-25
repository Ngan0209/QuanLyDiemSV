/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.pojo.Student;
import com.cln.services.FacultyService;
import com.cln.services.StudentService;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author LE NGAN
 */
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private FacultyService facultyService;

    @ModelAttribute
    public void commonResponses(Model model, Map<String, String> params) {
        model.addAttribute("faculties", this.facultyService.getFaculties(params));
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    @GetMapping("/students")
    public String listStudents(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("students", this.studentService.getStudents(params));
        return "students";
    }

    @GetMapping("/students/addorupdate")
    public String listStudents(Model model) {
        model.addAttribute("student", new Student());
        return "addOrUpdateStudent";
    }

    @PostMapping("/students/addorupdate")
    public String addStudent(@ModelAttribute(value = "student") Student p) {
        this.studentService.addOrUpdateStudent(p);
        return "redirect:/students";
    }

    @GetMapping("/students/addorupdate/{studentId}")
    public String updateStudent(Model model, @PathVariable(value = "studentId") int id) {
        model.addAttribute("student", this.studentService.getStudentById(id));
        return "addOrUpdateStudent";
    }


}
