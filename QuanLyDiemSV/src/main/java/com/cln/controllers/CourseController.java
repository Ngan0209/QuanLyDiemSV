/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.pojo.Course;
import com.cln.services.CourseService;
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
public class CourseController {
    @Autowired
    private CourseService courseService;
    
    @GetMapping("/courses")
    public String listCourses(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("courses",this.courseService.getCourses(params));
        return "courses";
    }
    
    @GetMapping("/courses/addorupdate")
    public String listStudents(Model model) {
        model.addAttribute("course", new Course());
        return "addOrUpdateCourse";
    }

    @PostMapping("/courses/addorupdate")
    public String addCourse(@ModelAttribute(value = "course") Course p) {
        this.courseService.addOrUpdateCourse(p);
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/addorupdate/{courseId}")
    public String updateCourse(Model model, @PathVariable(value = "courseId") int id) {
        model.addAttribute("course", this.courseService.getCourseById(id));
        return "addOrUpdateCourse";
    }
}
