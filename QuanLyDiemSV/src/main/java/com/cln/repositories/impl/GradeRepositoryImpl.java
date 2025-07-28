/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.repositories.impl;

import com.cln.repositories.TypeGradeRepository;
import com.cln.pojo.Grade;
import com.cln.pojo.StudentClass;
import com.cln.pojo.Typegrade;
import com.cln.repositories.GradeRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
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

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private TypeGradeRepository typeGradeRepository;

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
        List<StudentClass> results = s.createQuery(q).getResultList();

        // Khởi tạo Grade cho các StudentClass chưa có điểm
        for (StudentClass sc : results) {
            if (sc.getGrade() == null) {
                StudentClass managedSc = s.get(StudentClass.class, sc.getId());
                Grade g = new Grade();
                g.setStudentClassId(managedSc);
                s.persist(g);       // Lưu Grade với liên kết hợp lệ
                managedSc.setGrade(g);
            }
        }

        return results;
    }

    @Override
    public void addOrUpdateGrade(Grade grades) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        s.merge(grades);
    }

    @Override
    public void addTypeGradeColumnForClass(int classId, String columnName) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();

        CriteriaQuery<Grade> q = b.createQuery(Grade.class);
        Root<Grade> root = q.from(Grade.class);

        Join<Object, Object> studentClassJoin = root.join("studentClassId");
        Join<Object, Object> classJoin = studentClassJoin.join("classId");

        q.select(root).where(b.equal(classJoin.get("id"), classId));

        List<Grade> grades = s.createQuery(q).getResultList();

        for (Grade g : grades) {

            if (g.getTypegradeSet() != null && g.getTypegradeSet().size() >= 5) {
                continue;
            }
            Typegrade tg = new Typegrade();
            tg.setName(columnName);
            tg.setGradeId(g);

            typeGradeRepository.addTypeGradeColumn(tg);
        }

    }

    @Override
    public void saveGrades(List<Grade> grades) {
        for (Grade g : grades) {
            if (g.getStudentClassId() != null && g.getStudentClassId().getId() != null) {
                StudentClass sc = new StudentClass();
                sc.setId(g.getStudentClassId().getId());
                g.setStudentClassId(sc);
            }

            // Lưu Typegrade 
            if (g.getTypegradeSet() != null) {
                for (Typegrade tg : g.getTypegradeSet()) {
                    tg.setGradeId(g);
                    typeGradeRepository.addTypeGradeColumn(tg);
                }
            }

            // Lưu Grade
            this.addOrUpdateGrade(g);
        }
    }

    public Float calculateAverage(Grade grade) {
        if (grade == null || grade.getFinalExem() == null || grade.getMidterm() == null) {
            return null;
        }

        float finalScore = grade.getFinalExem();
        float midtermScore = grade.getMidterm();
        List<Typegrade> typeGrades = grade.getTypegradeSet();

        float typeSum = 0;
        int count = 0;

        if (typeGrades != null) {
            for (Typegrade tg : typeGrades) {
                if (tg.getGrade() != null) {
                    typeSum += tg.getGrade();
                    count++;
                }
            }
        }

        float midComponent = count > 0
                ? (midtermScore + (typeSum / count)) / 2
                : midtermScore;

        return (float)(Math.round((finalScore * 0.6 + midComponent * 0.4) * 100.0) / 100.0);
    }
    
    @Override
    public void averageScores(List<StudentClass> studentClasses) {
        for (StudentClass sc : studentClasses) {
            Grade grade = sc.getGrade();
            if (grade != null) {
                Float avg = calculateAverage(grade);
                grade.setAverageScore(avg);
            }
        }
    }

}
