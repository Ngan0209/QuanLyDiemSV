/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.repositories;

import com.cln.pojo.Grade;
import com.cln.pojo.StudentClass;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LE NGAN
 */
public interface GradeRepository {
    List<StudentClass> getStudentGradeByClassId(int classId, Map<String, String> parmas);
    void addOrUpdateGrade(Grade grades);
    void addTypeGradeColumnForClass(int classId, String columnName);
    void saveGrades(List<Grade> grades);
    void averageScores(List<StudentClass> studentClasses);
}
