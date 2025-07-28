/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services;

import java.util.List;

/**
 *
 * @author LE NGAN
 */
public interface StatService {
    Long countStudents();
    Long countFaculties();
    Long countClasses();
    Long countCourses();
    Long countTeachers();
    List<Object[]> countStudentBySchoolYear();
    List<Object[]> countTeacherByEducation();
}
