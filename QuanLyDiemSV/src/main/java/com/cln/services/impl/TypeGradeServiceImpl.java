/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services.impl;

import com.cln.pojo.Grade;
import com.cln.pojo.Typegrade;
import com.cln.repositories.TypeGradeRepository;
import com.cln.services.TypeGradeService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LE NGAN
 */
@Service
public class TypeGradeServiceImpl implements TypeGradeService{
    
    @Autowired
    private TypeGradeRepository typeGradeRepository;

    @Override
    public List<Typegrade> listTypeGradeByGradeId(Grade grade) {
        return this.typeGradeRepository.listTypeGradeByGradeId(grade);
    }

    @Override
    public void saveTypeGradeColumn(Typegrade typeGrade) {
        this.typeGradeRepository.saveTypeGradeColumn(typeGrade);
    }

    @Override
    public void deleteTypeGradeColumn(String nameTypeGrade, int classId) {
        this.typeGradeRepository.deleteTypeGradeColumn(nameTypeGrade, classId);
    }

    
}
