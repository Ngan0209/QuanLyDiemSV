/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.pojo.Student;
import com.cln.pojo.User;
import com.cln.services.ClassService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ApiClassController {
    
    @Autowired
    private ClassService classService;
    
    @GetMapping("/secure/teacher/classes/{classId}/students")
    public ResponseEntity<?> listStudent(
            @PathVariable("classId") int classid,
            @RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.classService.getStudentByClassId(classid, params), HttpStatus.OK);
    }
}
