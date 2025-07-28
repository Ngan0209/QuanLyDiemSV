/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.repositories.impl;

import com.cln.pojo.Course;
import com.cln.pojo.Faculty;
import com.cln.pojo.Student;
import com.cln.pojo.Teacher;
import com.cln.repositories.StatRepository;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
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
public class StatRepositoryImpl implements StatRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public Long countStudents() {
        Session session = sessionFactory.getObject().getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Student> root = cq.from(Student.class);
        cq.select(cb.count(root));

        TypedQuery<Long> query = session.createQuery(cq);
        return query.getSingleResult();
    }

    @Override
    public Long countFaculties() {
        Session session = sessionFactory.getObject().getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Faculty> root = cq.from(Faculty.class);
        cq.select(cb.count(root));

        TypedQuery<Long> query = session.createQuery(cq);
        return query.getSingleResult();
    }

    @Override
    public Long countClasses() {
        Session session = sessionFactory.getObject().getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<com.cln.pojo.Class> root = cq.from(com.cln.pojo.Class.class);
        cq.select(cb.count(root));

        TypedQuery<Long> query = session.createQuery(cq);
        return query.getSingleResult();
    }

    @Override
    public Long countCourses() {
        Session session = sessionFactory.getObject().getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Course> root = cq.from(Course.class);
        cq.select(cb.count(root));

        TypedQuery<Long> query = session.createQuery(cq);
        return query.getSingleResult();
    }

    @Override
    public Long countTeachers() {
        Session session = sessionFactory.getObject().getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Teacher> root = cq.from(Teacher.class);
        cq.select(cb.count(root));

        TypedQuery<Long> query = session.createQuery(cq);
        return query.getSingleResult();
    }

    @Override
    public List<Object[]> countStudentBySchoolYear() {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<Student> root = cq.from(Student.class);

        cq.multiselect(
                root.get("schoolYear"),
                cb.count(root.get("id"))
        );
        cq.groupBy(root.get("schoolYear"));

        TypedQuery<Object[]> query = session.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Object[]> countTeacherByEducation() {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<Teacher> root = cq.from(Teacher.class);

        cq.multiselect(
                root.get("education"),
                cb.count(root.get("id"))
        );
        cq.groupBy(root.get("education"));

        TypedQuery<Object[]> query = session.createQuery(cq);
        return query.getResultList();
    }

}
