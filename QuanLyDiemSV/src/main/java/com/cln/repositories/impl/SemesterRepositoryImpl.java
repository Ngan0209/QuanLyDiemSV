/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.repositories.impl;

import com.cln.pojo.Semester;
import com.cln.pojo.Class;
import com.cln.repositories.SemesterRepository;
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
public class SemesterRepositoryImpl implements SemesterRepository {

    private static final int PAGE_SIZE = 3;
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Semester> getSemesters(Map<String, String> params) {
        Session s = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Semester> q = b.createQuery(Semester.class);
        Root root = q.from(Semester.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
            }

            q.where(predicates.toArray(Predicate[]::new));

            q.orderBy(b.desc(root.get(params.getOrDefault("sortBy", "id"))));

        }

        Query query = s.createQuery(q);
        
        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.get("page"));
            int start = (page - 1) * PAGE_SIZE;

            query.setMaxResults(PAGE_SIZE);
            query.setFirstResult(start);
        }
               
        return query.getResultList();
    }

    @Override
    public Semester getSemesterById(int id) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        return s.get(Semester.class, id);
    }

    @Override
    public void addOrUpdateSemester(Semester p) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        if (p.getId() == null) {
            s.persist(p);
        } else {
            s.merge(p);
        }
    }

    @Override
    public List<Class> getClassesBySemesterId(int semesterId, Map<String, String> params) {
        Session s = this.sessionFactory.getObject().getCurrentSession();

        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Class> q = b.createQuery(Class.class);
        Root<Class> root = q.from(Class.class);
        q.select(root);
        
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(root.get("semesterId").get("id"), semesterId));
            String kwClass = params.get("kwClass");
            if (kwClass != null && !kwClass.isEmpty()) {
                Predicate namePrediacate = b.like(root.get("name"), String.format("%%%s%%", kwClass));
                Predicate coursePrediacate = b.like(root.get("courseId").get("name"), String.format("%%%s%%", kwClass));

                predicates.add(b.or(namePrediacate,coursePrediacate));
            }
            
            q.where(predicates.toArray(Predicate[]::new));
        }
        Query query = s.createQuery(q);
        
        
        return query.getResultList();
    }

}
