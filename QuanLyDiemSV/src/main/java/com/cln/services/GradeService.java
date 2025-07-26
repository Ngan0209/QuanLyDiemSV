/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services;

import com.cln.pojo.Grade;
import com.cln.pojo.StudentClass;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LE NGAN
 */
public interface GradeService {
    List<StudentClass> getStudentGradeByClassId(int classId, Map<String, String> parmas);
}
