/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.pojo.Faculty;
import com.cln.services.FacultyService;
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
public class FacultyController {
    @Autowired
    private FacultyService facultyService;

    @GetMapping("/faculties")
    public String listFaculties(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("faculties", this.facultyService.getFaculties(params));
        return "faculties";
    }
    
    @GetMapping("/faculties/addorupdate")
    public String listFaculties(Model model) {
        model.addAttribute("faculty", new Faculty());
        return "addOrUpdateFaculty";
    }

    @PostMapping("/faculties/addorupdate")
    public String addFaculty(@ModelAttribute(value = "faculty") Faculty p) {
        this.facultyService.addOrUpdateFaculty(p);
        return "redirect:/admin/faculties";
    }

    @GetMapping("/faculties/addorupdate/{facultyId}")
    public String updateFaculty(Model model, @PathVariable(value = "facultyId") int id) {
        model.addAttribute("faculty", this.facultyService.getFacultyById(id));
        return "addOrUpdateFaculty";
    }

}
