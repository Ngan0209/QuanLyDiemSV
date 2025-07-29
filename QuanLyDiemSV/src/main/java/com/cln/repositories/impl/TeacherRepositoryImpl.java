/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.repositories.impl;

import com.cln.pojo.Teacher;
import com.cln.repositories.TeacherRepository;
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
public class TeacherRepositoryImpl implements TeacherRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Teacher> getTeachers(Map<String, String> params) {
        Session s = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Teacher> q = b.createQuery(Teacher.class);
        Root root = q.from(Teacher.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Predicate namePredicate = b.like(root.get("name"), String.format("%%%s%%", kw));
                Predicate eduPredicate = b.like(root.get("education"), String.format("%%%s%%", kw));

                predicates.add(b.or(namePredicate, eduPredicate));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public Teacher getTeacherById(int id) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        return s.get(Teacher.class, id);

    }

    @Override
    public void addOrUpdateTeacher(Teacher p) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        if (p.getId() == null) {
            s.persist(p);
        } else {
            s.merge(p);
        }
    }

    @Override
    public List<Teacher> getTeachersWithoutUser() {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Teacher> query = cb.createQuery(Teacher.class);
        Root<Teacher> root = query.from(Teacher.class);

        // userId là field kiểu User, kiểm tra null
        query.select(root).where(cb.isNull(root.get("userId")));

        return session.createQuery(query).getResultList();
    }

}
