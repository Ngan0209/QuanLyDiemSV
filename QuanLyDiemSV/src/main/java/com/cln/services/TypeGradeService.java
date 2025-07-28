/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services;

import com.cln.pojo.Grade;
import com.cln.pojo.Typegrade;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LE NGAN
 */
public interface TypeGradeService {
    List<Typegrade> listTypeGradeByGradeId(Grade grade);
    void addTypeGradeColumn(Typegrade typeGrade);
    void deleteTypeGradeColumn(String nameTypeGrade, int classId);

}
