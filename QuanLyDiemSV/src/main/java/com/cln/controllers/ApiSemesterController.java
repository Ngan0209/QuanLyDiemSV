/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.pojo.Semester;
import com.cln.pojo.Class;
import com.cln.services.SemesterService;
import java.util.List;
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
public class ApiSemesterController {
    @Autowired
    private SemesterService semesterService;
    
    @GetMapping("/semesters")
    public ResponseEntity<List<Semester>> list(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.semesterService.getSemesters(params), HttpStatus.OK);
    }
    
    @GetMapping("/semesters/{semesterId}/classes")
    public ResponseEntity<List<Class>> retrieve(@PathVariable(value = "semesterId") int id, @RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.semesterService.getClassesBySemesterId(id, params), HttpStatus.OK);
    }
}
