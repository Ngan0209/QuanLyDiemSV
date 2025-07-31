/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.pojo.Course;
import com.cln.services.CourseService;
import com.cln.services.StatService;
import com.cln.services.StudentService;
import jakarta.persistence.Query;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author LE NGAN
 */
@Controller
public class HomeController {
    
    @Autowired
    private StatService statService;
    
    @GetMapping("/admin")
    public String index(Model model){
        model.addAttribute("countStudent", this.statService.countStudents());
        model.addAttribute("countFaculty", this.statService.countFaculties());
        model.addAttribute("countCourse", this.statService.countCourses());
        model.addAttribute("countClass", this.statService.countClasses());
        model.addAttribute("countTeacher", this.statService.countTeachers());
        
        model.addAttribute("countStudentBySchoolyear", this.statService.countStudentBySchoolYear());
        model.addAttribute("countTeacherByEducation", this.statService.countTeacherByEducation());
        return "home";
    }
}
