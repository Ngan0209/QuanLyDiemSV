/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.formatters;

import com.cln.pojo.Typegrade;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author LE NGAN
 */
public class TypeGradeFormatter implements Formatter<Typegrade> {

    @Override
    public String print(Typegrade tg, Locale locale) {
        return String.valueOf(tg.getId());
    }

    @Override
    public Typegrade parse(String typeGradeId, Locale locale) throws ParseException {
        Typegrade t = new Typegrade(Long.valueOf(typeGradeId));
        return t;
    }

}
