/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.pojo.Teacher;
import com.cln.services.FacultyService;
import com.cln.services.TeacherSevice;
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
@RequestMapping("/admin")
public class TeacherController {
    @Autowired
    private TeacherSevice teacherSevice;
    
    @Autowired
    private FacultyService facultyService;

    @ModelAttribute
    public void commonResponses(Model model, Map<String, String> params) {
        model.addAttribute("faculties", this.facultyService.getFaculties(params));
    }
    
    @GetMapping("/teachers")
    public  String listTeachers(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("teachers", this.teacherSevice.getTeachers(params));
        return "teachers";
    }
    
    @GetMapping("/teachers/addorupdate")
    public String listTeachers(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "addOrUpdateTeacher";
    }

    @PostMapping("/teachers/addorupdate")
    public String addTeacher(@ModelAttribute(value = "teacher") Teacher p) {
        this.teacherSevice.addOrUpdateTeacher(p);
        return "redirect:/admin/teachers";
    }

    @GetMapping("/teachers/addorupdate/{teacherId}")
    public String updateTeacher(Model model, @PathVariable(value = "teacherId") int id) {
        model.addAttribute("teacher", this.teacherSevice.getTeacherById(id));
        return "addOrUpdateTeacher";
    }
    
}
