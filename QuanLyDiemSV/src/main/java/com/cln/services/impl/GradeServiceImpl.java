/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services.impl;

import com.cln.pojo.Grade;
import com.cln.pojo.StudentClass;
import com.cln.repositories.GradeRepository;
import com.cln.services.GradeService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LE NGAN
 */
@Service
public class GradeServiceImpl implements GradeService{
    @Autowired
    private GradeRepository gradeRepository;

    @Override
    public List<StudentClass> getStudentGradeByClassId(int classId, Map<String, String> parmas) {
        return this.gradeRepository.getStudentGradeByClassId(classId, parmas);
    }

    @Override
    public void addOrUpdateGrade(Grade grades) {
        this.gradeRepository.addOrUpdateGrade(grades);
    }

    @Override
    public void addTypeGradeColumnForClass(int classId, String columnName) {
        this.gradeRepository.addTypeGradeColumnForClass(classId, columnName);
    }

    @Override
    public void saveGrades(List<Grade> grades) {
        this.gradeRepository.saveGrades(grades);
    }

    @Override
    public void averageScores(List<StudentClass> studentClasses) {
        this.gradeRepository.averageScores(studentClasses);
    }

    
    
}
