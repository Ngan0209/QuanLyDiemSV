/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services.impl;

import com.cln.pojo.Teacher;
import com.cln.repositories.TeacherRepository;
import com.cln.services.TeacherSevice;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LE NGAN
 */
@Service
public class TeacherServiceImpl implements TeacherSevice{
    @Autowired
    private TeacherRepository teacherRepository ;

    @Override
    public List<Teacher> getTeachers(Map<String, String> params) {
        return this.teacherRepository.getTeachers(params);
    }

    @Override
    public Teacher getTeacherById(int id) {
        return this.teacherRepository.getTeacherById(id);
    }

    @Override
    public void addOrUpdateTeacher(Teacher p) {
        this.teacherRepository.addOrUpdateTeacher(p);
    }

    @Override
    public List<Teacher> getTeachersWithoutUser() {
        return this.teacherRepository.getTeachersWithoutUser();
    }
    
    
    
}
