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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "midterm")
    private Float midterm;
    @Column(name = "final_exem")
    private Float finalExem;
    @OneToOne(mappedBy = "gradeId")
    private StudentClass studentClass;
    @JoinColumn(name = "student_class_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private StudentClass studentClassId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gradeId")
    private Collection<Typegrade> typegradeCollection;

    public Grade() {
    }

    public Grade(Long id) {
        this.id = id;
    }

    public Grade(Long id, short finall) {
        this.id = id;
        this.finall = finall;
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

    public Float getMidterm() {
        return midterm;
    }

    public void setMidterm(Float midterm) {
        this.midterm = midterm;
    }

    public Float getFinalExem() {
        return finalExem;
    }

    public void setFinalExem(Float finalExem) {
        this.finalExem = finalExem;
    }

    public StudentClass getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(StudentClass studentClass) {
        this.studentClass = studentClass;
    }

    public StudentClass getStudentClassId() {
        return studentClassId;
    }

    public void setStudentClassId(StudentClass studentClassId) {
        this.studentClassId = studentClassId;
    }

    public Collection<Typegrade> getTypegradeCollection() {
        return typegradeCollection;
    }

    public void setTypegradeCollection(Collection<Typegrade> typegradeCollection) {
        this.typegradeCollection = typegradeCollection;
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
