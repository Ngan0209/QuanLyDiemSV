/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services;

import com.cln.pojo.Course;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LE NGAN
 */
public interface CourseService {
    List<Course> getCourses(Map<String, String> params);
    Course getCourseById(int id);
    void addOrUpdateCourse(Course p);
}
