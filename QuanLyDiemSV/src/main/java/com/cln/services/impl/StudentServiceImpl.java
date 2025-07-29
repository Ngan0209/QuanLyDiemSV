/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services.impl;

import com.cln.pojo.Student;
import com.cln.repositories.StudentRepository;
import com.cln.services.StudentService;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LE NGAN
 */
@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getStudents(Map<String, String> params) {
        return this.studentRepository.getStudents(params);
    }
    
    @Override
    public void addOrUpdateStudent(Student s) {
        this.studentRepository.addOrUpdateStudent(s);
    }

    @Override
    public Student getStudentById(int id) {
        return this.studentRepository.getStudentById(id);
    }

    @Override
    public Student getStudentsWithoutUser(String studentCode) {
        return this.studentRepository.getStudentsWithoutUser(studentCode);
    }
}
