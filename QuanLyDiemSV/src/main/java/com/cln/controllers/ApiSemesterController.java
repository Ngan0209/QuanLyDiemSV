/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.pojo.Semester;
import com.cln.pojo.Class;
import com.cln.pojo.Grade;
import com.cln.pojo.User;
import com.cln.services.SemesterService;
import com.cln.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LE NGAN
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiSemesterController {

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private UserService userDetailsService;

    @GetMapping("/semesters")
    public ResponseEntity<List<Semester>> list(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.semesterService.getSemesters(params), HttpStatus.OK);
    }

    @GetMapping("/secure/semesters/{semesterId}/classes")
    public ResponseEntity<List<Class>> classes(@PathVariable(value = "semesterId") Long semesterid,
            HttpServletRequest request) {
        User user = this.userDetailsService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return new ResponseEntity<>(this.semesterService.getClassesBySemesterIdAndUser(semesterid, user.getId()), HttpStatus.OK);
    }

    @GetMapping("/secure/student/semesters/{semesterId}/classes/grades")
    public ResponseEntity<List<Grade>> grades(@PathVariable(value = "semesterId") Long semesterid,
            HttpServletRequest request) {
        User user = this.userDetailsService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        return new ResponseEntity<>(this.semesterService.getGradesBySemesterAndUser(semesterid, user.getId()), HttpStatus.OK);
        List<Grade> grades = this.semesterService.getGradesBySemesterAndUser(semesterid, user.getId());
        System.out.println("Grade list: " + grades.size());
        grades.forEach(g -> {
            
                System.out.println("Grade ID: " + g.getId());
                System.out.println("StudentClass ID: " + (g.getStudentClassId() != null ? g.getStudentClassId().getId() : "null"));
            
        });
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }
}
