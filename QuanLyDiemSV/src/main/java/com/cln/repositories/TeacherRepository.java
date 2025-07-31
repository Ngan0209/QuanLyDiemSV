/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.repositories;

import com.cln.pojo.Student;
import com.cln.pojo.Teacher;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LE NGAN
 */
public interface TeacherRepository {
    List<Teacher> getTeachers(Map<String, String> params);
    Teacher getTeacherById(long id);
    void addOrUpdateTeacher(Teacher p);
    List<Teacher> getTeachersWithoutUser();
}
