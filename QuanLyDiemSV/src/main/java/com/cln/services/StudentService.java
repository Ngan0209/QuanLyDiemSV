/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services;

import com.cln.pojo.Student;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LE NGAN
 */
public interface StudentService {
    List<Student> getStudents(Map<String,String> params);
    Student getStudentById(int id);
    void addOrUpdateStudent(Student p);
    Student getStudentsWithoutUser(String studentCode);
}
