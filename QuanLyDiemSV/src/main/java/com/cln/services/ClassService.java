/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services;

import java.util.List;
import com.cln.pojo.Class;
import java.util.Map;
/**
 *
 * @author LE NGAN
 */
public interface ClassService {
    List<Class> getClasses(Map<String, String> params);
}
