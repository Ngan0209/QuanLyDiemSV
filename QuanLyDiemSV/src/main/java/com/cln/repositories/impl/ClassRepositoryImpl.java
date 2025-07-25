/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.repositories.impl;

import com.cln.pojo.Class;
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

}
