/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.repositories.impl;

import com.cln.pojo.Course;
import com.cln.pojo.Student;
import com.cln.repositories.StudentRepository;
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
public class StudentRepositoryImpl implements StudentRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Student> getStudents(Map<String, String> params) {
        Session s = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Student> q = b.createQuery(Student.class);
        Root root = q.from(Student.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Predicate namePredicate = b.like(root.get("name"), String.format("%%%s%%", kw));
                Predicate codePredicate = b.like(root.get("studentCode"), String.format("%%%s%%", kw));

                predicates.add(b.or(namePredicate, codePredicate));
            }

            String schoolYear = params.get("schoolYear");
            if (schoolYear != null && !schoolYear.isEmpty()) {
                predicates.add(b.like(root.get("schoolYear"), String.format("%%%s%%", schoolYear)));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public Student getStudentById(int id) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        return s.get(Student.class, id);

    }

    @Override
    public void addOrUpdateStudent(Student p) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        if (p.getId() == null) {
            s.persist(p);
        } else {
            s.merge(p);
        }
    }

    @Override
    public Student getStudentsWithoutUser(String studentCode) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Student> query = cb.createQuery(Student.class);
        Root<Student> root = query.from(Student.class);

        Predicate byCode = cb.equal(root.get("studentCode"), studentCode);
        Predicate noUser = cb.isNull(root.get("userId"));

        // userId là field kiểu User, kiểm tra null
        query.select(root).where(cb.isNull(root.get("userId")));query.select(root).where(cb.and(byCode, noUser));
        
        List<Student> results = session.createQuery(query).getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

}
