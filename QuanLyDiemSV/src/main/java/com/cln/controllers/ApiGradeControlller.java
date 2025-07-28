/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.services.TypeGradeService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LE NGAN
 */

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiGradeControlller {
    
    @Autowired
    private TypeGradeService typeGradeService;

    @DeleteMapping("/grade/classes/{classId}/delete-column")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTypeGradeColumn(@PathVariable("classId") int classId,@RequestParam("name") String name) {

        this.typeGradeService.deleteTypeGradeColumn(name, classId);
    }
}
