/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services.impl;

import com.cln.pojo.Semester;
import com.cln.pojo.Class;
import com.cln.repositories.SemesterRepository;
import com.cln.services.SemesterService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LE NGAN
 */
@Service
public class SemesterSeviceImpl implements SemesterService{
    @Autowired
    private SemesterRepository semesterRepository; 

    @Override
    public List<Semester> getSemesters(Map<String, String> params) {
        return this.semesterRepository.getSemesters(params);
    }

    @Override
    public Semester getSemesterById(int id) {
        return this.semesterRepository.getSemesterById(id);
    }

    @Override
    public void addOrUpdateSemester(Semester p) {
        this.semesterRepository.addOrUpdateSemester(p);
    }

    @Override
    public List<Class> getClassesBySemesterId(int semesterId, Map<String, String> params) {
        return this.semesterRepository.getClassesBySemesterId(semesterId, params);
    }
    
}
