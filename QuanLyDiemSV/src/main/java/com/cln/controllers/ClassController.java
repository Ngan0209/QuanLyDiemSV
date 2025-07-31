/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.services.ClassService;
import com.cln.services.SemesterService;
import com.cln.services.StudentService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author LE NGAN
 */
@Controller
@RequestMapping("/admin")
public class ClassController {

    @Autowired
    private ClassService classService;
    @Autowired
    private SemesterService semesterService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/classes/semesters")
    public String listSemeter(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("semesters", this.semesterService.getSemesters(params));
        model.addAttribute("action", "class");
        return "semesterClasses";
    }

    @GetMapping("/classes/semesters/{semesterId}")
    public String listClassesBySemester(Model model, @PathVariable(value = "semesterId") int id, @RequestParam Map<String, String> params) {
        model.addAttribute("classes", this.semesterService.getClassesBySemesterId(id, params));
        model.addAttribute("semester", this.semesterService.getSemesterById(id));
        model.addAttribute("action", "class");
        return "classes";
    }

    @GetMapping("/classes/{classId}/students")
    public String listStudentsByClass(Model model, @PathVariable("classId") int classId, @RequestParam Map<String, String> params) {
        model.addAttribute("students", this.classService.getStudentByClassId(classId, params));
        model.addAttribute("class", this.classService.getClassById(classId));
        model.addAttribute("action", "class");
        return "studentsByClass";
    }

}
