/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.formatters;

import com.cln.pojo.Teacher;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author LE NGAN
 */
public class TeacherFormatter implements Formatter<Teacher>{

    @Override
    public String print(Teacher t, Locale locale) {
        return String.valueOf(t.getId());
    }

    @Override
    public Teacher parse(String teacherId, Locale locale) throws ParseException {
        Teacher t = new Teacher(Long.valueOf(teacherId));
        return t;
    }
    
}
