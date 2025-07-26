/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.pojo.Grade;
import com.cln.services.ClassService;
import com.cln.services.GradeService;
import com.cln.services.SemesterService;
import com.cln.services.StudentService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author LE NGAN
 */
@Controller
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private ClassService classService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private GradeService gradeService;

    @GetMapping("/classes/semesters")
    public String listSemesters(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("semesters", this.semesterService.getSemesters(params));
        model.addAttribute("action", "grade");
        return "semesterClasses";
    }

    @GetMapping("/classes/semesters/{semesterId}")
    public String listClassesBySemester(Model model, @PathVariable(value = "semesterId") int id, @RequestParam Map<String, String> params) {
        model.addAttribute("classes", this.semesterService.getClassesBySemesterId(id, params));
        model.addAttribute("semester", this.semesterService.getSemesterById(id));
        model.addAttribute("action", "grade");
        return "classes";
    }

    @GetMapping(value = "/classes/{classId}/students")
    public String listStudentsForGrading(Model model, @PathVariable("classId") int classId, @RequestParam Map<String, String> params) {
        model.addAttribute("students", this.classService.getStudentByClassId(classId, params));
        model.addAttribute("class", this.classService.getClassById(classId));
        model.addAttribute("grades", this.gradeService.getStudentGradeByClassId(classId, params));
        model.addAttribute("action", "grade"); 
        return "grade";
    }

    @GetMapping("/classes/{classId}/students/{studentId}/add")
    public String addGrade(Model model, @PathVariable("classId") int classId, @PathVariable("studentId") int studentId) {
        model.addAttribute("student", this.studentService.getStudentById(studentId));
        model.addAttribute("class", this.classService.getClassById(classId));
        model.addAttribute("grade", new Grade());
        return "addOrUpdateGrade";
    }

    @PostMapping("/classes/{classId}/students/{studentId}/add")
    public String saveGrade(@PathVariable("classId") int classId,
                            @PathVariable("studentId") int studentId,
                            @ModelAttribute(value = "grade") Grade grade) {
        this.gradeService.addOrUpdateGrade(classId, studentId, grade);
        return "redirect:/classes/" + classId + "/students";
    }
}

