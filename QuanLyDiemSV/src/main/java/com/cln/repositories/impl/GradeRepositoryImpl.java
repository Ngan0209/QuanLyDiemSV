/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.repositories.impl;

import com.cln.pojo.Grade;
import com.cln.pojo.StudentClass;
import com.cln.repositories.GradeRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author LE NGAN
 */
@Repository
@Transactional
public class GradeRepositoryImpl implements GradeRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<StudentClass> getStudentGradeByClassId(int classId, Map<String, String> params) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();

        CriteriaQuery<StudentClass> q = b.createQuery(StudentClass.class);
        Root<StudentClass> root = q.from(StudentClass.class);

        root.fetch("studentId", JoinType.LEFT);
        root.fetch("grade", JoinType.LEFT);//lấy hết sinh viên

        q.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(root.get("classId").get("id"), classId));

        if (params != null) {
            String kwStudent = params.get("kwStudent");
            if (kwStudent != null && !kwStudent.isEmpty()) {
                Predicate namePrediacate = b.like(root.get("studentId").get("name"), String.format("%%%s%%", kwStudent));
                Predicate codePrediacate = b.like(root.get("studentId").get("studentCode"), String.format("%%%s%%", kwStudent));

                predicates.add(b.or(namePrediacate, codePrediacate));
            }
        }
        q.where(predicates.toArray(Predicate[]::new));
        Query query = s.createQuery(q);
        return query.getResultList();
    }

}
