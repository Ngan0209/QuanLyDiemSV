/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.repositories.impl;

import com.cln.repositories.TypeGradeRepository;
import com.cln.pojo.Grade;
import com.cln.pojo.Student;
import com.cln.pojo.StudentClass;
import com.cln.pojo.Typegrade;
import com.cln.repositories.GradeRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.HashMap;
import java.util.Iterator;
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
public class TypeGradeRepositoryImpl implements TypeGradeRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Autowired
    private GradeRepository gradeRepository;

    @Override
    public List<Typegrade> listTypeGradeByGradeId(Grade grade) {
        Session s = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Typegrade> q = b.createQuery(Typegrade.class);
        Root<Typegrade> root = q.from(Typegrade.class);

        q.select(root).where(b.equal(root.get("gradeId"), grade));

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public void addTypeGradeColumn(Typegrade typeGrade) {
        Session s = sessionFactory.getObject().getCurrentSession();
        s.merge(typeGrade);
    }

    @Override
    public void deleteTypeGradeColumn(String nameTypeGrade, int classId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        List<StudentClass> studentClass = gradeRepository.getStudentGradeByClassId(classId,new HashMap<>());
        for (StudentClass sc : studentClass) {
            Grade grade = s.get(Grade.class, sc.getGrade().getId());

            if (grade != null && grade.getTypegradeSet() != null) {
                List<Typegrade> typeGrades = grade.getTypegradeSet();
                Iterator<Typegrade> iterator = typeGrades.iterator();
                while (iterator.hasNext()) {
                    Typegrade t = iterator.next();
                    if (t.getName().equals(nameTypeGrade)) {
                        iterator.remove();  // Xóa khỏi list
                        //s.remove(t);        // Xóa khỏi session Hibernate
                    }
                }
            }
        }
//
//                // Duyệt và xóa những TypeGrade có name trùng khớp
//                Iterator<Typegrade> iterator = typeGrades.iterator();
//                boolean removed = false;
//
//                while (iterator.hasNext()) {
//                    Typegrade tg = iterator.next();
//
//                    if (nameTypeGrade.trim().equalsIgnoreCase(tg.getName().trim())) {
//                        tg.setGrade(null); // Hủy liên kết nếu có mappedBy
//                        iterator.remove(); // Xóa khỏi list
//                        removed = true;
//                    }
//                }
//
//                if (removed) {
//                    gradeRepository.addOrUpdateGrade(grade); // Lưu thay đổi
//                }
//            }
    }
}
