/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.services.StudentService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ApiStudentController {
    @Autowired
    private StudentService studentService;
    
    @GetMapping("/secure/teacher/students/{studentId}")
    public ResponseEntity<?> getStudent(
            @PathVariable("studentId") int studentId) {
        return new ResponseEntity<>(this.studentService.getStudentById(studentId), HttpStatus.OK);
    }
}
