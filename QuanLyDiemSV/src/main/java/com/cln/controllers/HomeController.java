/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.pojo.Course;
import com.cln.services.CourseService;
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
    private CourseService courseService;
    
    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("courses",this.courseService.getCourses(params));
        return "index";
    }

}
