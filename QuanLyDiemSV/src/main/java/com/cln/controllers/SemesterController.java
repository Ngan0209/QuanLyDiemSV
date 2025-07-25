/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.pojo.Semester;
import com.cln.services.SemesterService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author LE NGAN
 */
@Controller
public class SemesterController {
    @Autowired
    private SemesterService semesterService;

    @GetMapping("/semesters")
    public String listSemesters(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("semesters", this.semesterService.getSemesters(params));
        return "semesters";
    }
    
    @GetMapping("/semesters/addorupdate")
    public String listSemesters(Model model) {
        model.addAttribute("semester", new Semester());
        return "addOrUpdateSemester";
    }

    @PostMapping("/semesters/addorupdate")
    public String addSemester(@ModelAttribute(value = "semester") Semester p) {
        this.semesterService.addOrUpdateSemester(p);
        return "redirect:/semesters";
    }

    @GetMapping("/semesters/addorupdate/{semesterId}")
    public String updateSemester(Model model, @PathVariable(value = "semesterId") int id) {
        model.addAttribute("semester", this.semesterService.getSemesterById(id));
        return "addOrUpdateSemester";
    }
}
