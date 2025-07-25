/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author LE NGAN
 */
@Entity
@Table(name = "grade")
@NamedQueries({
    @NamedQuery(name = "Grade.findAll", query = "SELECT g FROM Grade g"),
    @NamedQuery(name = "Grade.findById", query = "SELECT g FROM Grade g WHERE g.id = :id"),
    @NamedQuery(name = "Grade.findByFinall", query = "SELECT g FROM Grade g WHERE g.finall = :finall"),
    @NamedQuery(name = "Grade.findByMidterm", query = "SELECT g FROM Grade g WHERE g.midterm = :midterm"),
    @NamedQuery(name = "Grade.findByFinalExem", query = "SELECT g FROM Grade g WHERE g.finalExem = :finalExem")})
public class Grade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "finall")
    private short finall;
    @Basic(optional = false)
    @NotNull
    @Column(name = "midterm")
    private float midterm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "final_exem")
    private float finalExem;
    @JoinColumn(name = "student_class_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private StudentClass studentClassId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gradeId")
    private Set<Typegrade> typegradeSet;

    public Grade() {
    }

    public Grade(Long id) {
        this.id = id;
    }

    public Grade(Long id, short finall, float midterm, float finalExem) {
        this.id = id;
        this.finall = finall;
        this.midterm = midterm;
        this.finalExem = finalExem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public short getFinall() {
        return finall;
    }

    public void setFinall(short finall) {
        this.finall = finall;
    }

    public float getMidterm() {
        return midterm;
    }

    public void setMidterm(float midterm) {
        this.midterm = midterm;
    }

    public float getFinalExem() {
        return finalExem;
    }

    public void setFinalExem(float finalExem) {
        this.finalExem = finalExem;
    }

    public StudentClass getStudentClassId() {
        return studentClassId;
    }

    public void setStudentClassId(StudentClass studentClassId) {
        this.studentClassId = studentClassId;
    }

    public Set<Typegrade> getTypegradeSet() {
        return typegradeSet;
    }

    public void setTypegradeSet(Set<Typegrade> typegradeSet) {
        this.typegradeSet = typegradeSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grade)) {
            return false;
        }
        Grade other = (Grade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cln.pojo.Grade[ id=" + id + " ]";
    }
    
}
