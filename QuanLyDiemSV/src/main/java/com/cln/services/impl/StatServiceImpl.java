/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services.impl;

import com.cln.repositories.StatRepository;
import com.cln.services.StatService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LE NGAN
 */
@Service
public class StatServiceImpl implements StatService {

    @Autowired
    private StatRepository statRepository;

    @Override
    public Long countStudents() {
        return this.statRepository.countStudents();
    }

    @Override
    public Long countFaculties() {
        return this.statRepository.countFaculties();
    }

    @Override
    public Long countClasses() {
        return this.statRepository.countClasses();
    }

    @Override
    public Long countCourses() {
        return this.statRepository.countCourses();
    }

    @Override
    public Long countTeachers() {
        return this.statRepository.countTeachers();
    }

    @Override
    public List<Object[]> countStudentBySchoolYear() {
        return this.statRepository.countStudentBySchoolYear();
    }

    @Override
    public List<Object[]> countTeacherByEducation() {
        return this.statRepository.countTeacherByEducation();
    }

}
