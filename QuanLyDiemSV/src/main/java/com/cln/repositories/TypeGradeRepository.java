/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.repositories;

import com.cln.pojo.Grade;
import com.cln.pojo.Typegrade;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LE NGAN
 */
public interface TypeGradeRepository {
    List<Typegrade> listTypeGradeByGradeId(Grade grade);
    void saveTypeGradeColumn(Typegrade typeGrade);
    void deleteTypeGradeColumn(String nameTypeGrade, int classId);
}
