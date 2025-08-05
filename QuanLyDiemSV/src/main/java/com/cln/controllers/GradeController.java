/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.dto.GradeListWrapper;
import com.cln.pojo.Grade;
import com.cln.pojo.StudentClass;
import com.cln.pojo.Typegrade;
import com.cln.services.ClassService;
import com.cln.services.GradeService;
import com.cln.services.SemesterService;
import com.cln.services.StudentService;
import com.cln.services.TypeGradeService;
import jakarta.data.repository.Delete;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author LE NGAN
 */
@Controller
@RequestMapping("/admin/grade")
public class GradeController {

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private ClassService classService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private TypeGradeService typeGradeService;

    @GetMapping("/classes/semesters")
    public String listSemesters(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("semesters", this.semesterService.getSemesters(params));
        model.addAttribute("action", "grade");
        return "semesterClasses";
    }

    @GetMapping("/classes/semesters/{semesterId}")
    public String listClassesBySemester(Model model, @PathVariable(value = "semesterId") int id, @RequestParam Map<String, String> params) {
        model.addAttribute("classes", this.semesterService.getClassesBySemesterId(id, params));
        model.addAttribute("semester", this.semesterService.getSemesterById(id));
        model.addAttribute("action", "grade");
        return "classes";
    }

    @GetMapping("/classes/{classId}/students")
    public String listStudentsForGrading(Model model,@PathVariable("classId") int classId, @RequestParam Map<String, String> params) {

        List<StudentClass> grades = this.gradeService.getStudentGradeByClassId(classId, params);
        this.gradeService.averageScores(grades);
       
        model.addAttribute("grades", grades); 
        model.addAttribute("action", "grade");

        return "grade"; 
    }

    @GetMapping("/classes/{classId}/students/add")
    public String addGrade(Model model, @PathVariable("classId") int classId, @RequestParam Map<String, String> params) {
        model.addAttribute("grades", this.gradeService.getStudentGradeByClassId(classId, params));
        return "addOrUpdateGrade";
    }

    @PostMapping("/classes/{classId}/students/add")
    public String saveGrade(@PathVariable("classId") int classId,
            @ModelAttribute("grades") GradeListWrapper wrapper) {
        this.gradeService.saveGrades(wrapper.getGrades());
        return "redirect:/admin/grade/classes/" + classId + "/students";

    }

    @GetMapping("/classes/{classId}/add-column")
    public String addTypeGrade(@PathVariable("classId") int classId) {
        return "addTypeGrade";
    }

    @PostMapping("/classes/{classId}/add-column")
    public String addTypeGradeColumn(@PathVariable("classId") int classId, @RequestParam("columnName") String columnName) {
        this.gradeService.addTypeGradeColumnForClass(classId, columnName);
        return "redirect:/admin/grade/classes/" + classId + "/students";
    }

    @GetMapping("/classes/{classId}/delete-column")
    public String deleteTypeGradeColumn(@PathVariable("classId") int classId, @RequestParam("name") String name) {
        this.typeGradeService.deleteTypeGradeColumn(name, classId);
        return "redirect:/admin/grade/classes/" + classId + "/students";
    }

}
