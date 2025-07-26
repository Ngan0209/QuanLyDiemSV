/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.repositories.impl;

import com.cln.pojo.Class;
import com.cln.pojo.Student;
import com.cln.pojo.StudentClass;
import com.cln.repositories.ClassRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class ClassRepositoryImpl implements ClassRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Class> getClasses(Map<String, String> params) {
        Session s = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Class> q = b.createQuery(Class.class);
        Root root = q.from(Class.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predcates = new ArrayList<>();

//            String kw = params.get("kw");
//            if (kw != null && !kw.isEmpty()) {
//                predcates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
//            }
            q.where(predcates.toArray(Predicate[]::new));

            // Sap xep du lieu
            q.orderBy(b.desc(root.get(params.getOrDefault("sortBy", "id"))));
        }
        Query query = s.createQuery(q);
        return query.getResultList();
    }
    
    @Override
    public Class getClassById(int id) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        return s.get(Class.class, id);
    }

    @Override
    public List<Student> getStudentByClassId(int classId, Map<String, String> params) {
        Session s = this.sessionFactory.getObject().getCurrentSession();

        CriteriaBuilder b = s.getCriteriaBuilder();
        
        CriteriaQuery<Student> q = b.createQuery(Student.class);
        Root<StudentClass> root = q.from(StudentClass.class);
        q.select(root.get("studentId"));
        
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(root.get("classId").get("id"), classId));
            String kwStudent = params.get("kwStudent");
            if (kwStudent != null && !kwStudent.isEmpty()) {
                Predicate namePrediacate = b.like(root.get("studentId").get("name"), String.format("%%%s%%", kwStudent));
                Predicate codePrediacate = b.like(root.get("studentId").get("studentCode"), String.format("%%%s%%", kwStudent));

                predicates.add(b.or(namePrediacate,codePrediacate));
            }
            
            q.where(predicates.toArray(Predicate[]::new));
        }
        Query query = s.createQuery(q);
        return query.getResultList();
    }

}
