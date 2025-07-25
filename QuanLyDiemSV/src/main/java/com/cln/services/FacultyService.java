/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services;

import com.cln.pojo.Faculty;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LE NGAN
 */
public interface FacultyService {
    List<Faculty> getFaculties(Map<String, String> params);
    Faculty getFacultyById(int id);
    void addOrUpdateFaculty(Faculty p);
}
