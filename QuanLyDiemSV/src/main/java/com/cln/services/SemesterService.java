/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services;

import com.cln.pojo.Semester;
import com.cln.pojo.Class;
import com.cln.pojo.Grade;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author LE NGAN
 */
public interface SemesterService {
    List<Semester> getSemesters(Map<String, String> params);
    Semester getSemesterById(int id);
    void addOrUpdateSemester(Semester p);
    List<Class> getClassesBySemesterId(int semesterId, Map<String, String> params);
    List<Class> getClassesBySemesterIdAndUser(Long semesterId, Long userId);
    List<Grade> getGradesBySemesterAndUser(Long semesterId, Long userId);
}
