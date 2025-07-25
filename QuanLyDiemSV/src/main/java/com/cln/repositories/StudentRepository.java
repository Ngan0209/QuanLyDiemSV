/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.repositories;

import com.cln.pojo.Student;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LE NGAN
 */
public interface StudentRepository {
    List<Student> getStudents(Map<String, String> params);
    Student getStudentById(int id);
    void addOrUpdateStudent(Student p);
}
