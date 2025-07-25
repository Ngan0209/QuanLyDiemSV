/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.services.ClassService;
import com.cln.services.SemesterService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author LE NGAN
 */
@Controller
public class ClassController {
    @Autowired
    private ClassService ClassService;
    @Autowired
    private SemesterService semesterService;
    
    @GetMapping("/classes/semesters")
    public String listSemeter(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("semesters", this.semesterService.getSemesters(params));
        return "semesterClasses";
    }
    
    @GetMapping("/classes/semesters/{semesterId}")
    public String listClassesBySemester(Model model, @PathVariable(value = "semesterId") int id) {
        model.addAttribute("classes", this.semesterService.getClassesBySemesterId(id));
        model.addAttribute("semester", this.semesterService.getSemesterById(id));
        return "classes";
    }
    
}
