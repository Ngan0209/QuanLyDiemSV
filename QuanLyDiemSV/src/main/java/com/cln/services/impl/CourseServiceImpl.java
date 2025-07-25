/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services.impl;

import com.cln.pojo.Course;
import com.cln.repositories.CourseRepository;
import com.cln.services.CourseService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LE NGAN
 */
@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseRepository courseRepository;
    
    @Override
    public List<Course> getCourses(Map<String, String> params) {
        return this.courseRepository.getCourses(params);
    }

    @Override
    public Course getCourseById(int id) {
        return this.courseRepository.getCourseById(id);
    }

    @Override
    public void addOrUpdateCourse(Course p) {
        this.courseRepository.addOrUpdateCourse(p);
    }
    
    
}
