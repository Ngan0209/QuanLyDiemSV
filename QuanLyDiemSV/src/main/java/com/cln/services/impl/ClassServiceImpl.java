/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services.impl;

import com.cln.pojo.Class;
import com.cln.pojo.Student;
import com.cln.repositories.ClassRepository;
import com.cln.services.ClassService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LE NGAN
 */
@Service
public class ClassServiceImpl implements ClassService{
    @Autowired
    private ClassRepository classRepository;

    @Override
    public List<Class> getClasses(Map<String, String> params) {
        return this.classRepository.getClasses(params);
    }

    @Override
    public List<Student> getStudentByClassId(int classId,  Map<String, String> params) {
        return this.classRepository.getStudentByClassId(classId, params);
    }

    @Override
    public Class getClassById(int id) {
        return this.classRepository.getClassById(id);
    }
    
}
