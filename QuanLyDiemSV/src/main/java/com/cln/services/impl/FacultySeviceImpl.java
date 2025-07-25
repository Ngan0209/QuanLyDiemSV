/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services.impl;

import com.cln.pojo.Faculty;
import com.cln.repositories.FacultyRepository;
import com.cln.services.FacultyService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LE NGAN
 */
@Service
public class FacultySeviceImpl implements FacultyService{
    @Autowired
        private FacultyRepository facultyRepository ;
    
    @Override
    public List<Faculty> getFaculties(Map<String, String> params) {
        return this.facultyRepository.getFaculties(params);
    }   

    @Override
    public Faculty getFacultyById(int id) {
        return this.facultyRepository.getFacultyById(id);
    }

    @Override
    public void addOrUpdateFaculty(Faculty p) {
        this.facultyRepository.addOrUpdateFaculty(p);
    }
}
