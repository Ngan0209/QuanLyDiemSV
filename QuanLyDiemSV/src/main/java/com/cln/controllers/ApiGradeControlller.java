/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.pojo.Grade;
import com.cln.services.GradeService;
import com.cln.services.TypeGradeService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private GradeService gradeService;

    @DeleteMapping("delete-columnGrade/classes/{classId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTypeGradeColumn(@PathVariable("classId") int classId, @RequestParam("name") String name) {
        this.typeGradeService.deleteTypeGradeColumn(name, classId);
    }

    @PostMapping(path = "/secure/teacher/classes/{classId}/add-GradeColumn",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTypeGradeColumn(@PathVariable("classId") int classId,
            @RequestBody Map<String, String> params) {
        try {
            String columnName = params.get("columnName");
            this.gradeService.addTypeGradeColumnForClass(classId, columnName);
            return ResponseEntity.ok(Map.of(
                    "message", "Thêm cột điểm thành công!",
                    "classId", classId
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @PostMapping("/secure/teacher/classes/{classId}/saveGrade")
    public ResponseEntity<?> saveGrade(@PathVariable("classId") int classId,
            @RequestBody List<Grade> grades) {
         try {
            gradeService.saveGrades(grades);
            return ResponseEntity.ok(Map.of("message", "Lưu điểm thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
