/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author LE NGAN
 */
@Entity
@Table(name = "typegrade")
@NamedQueries({
    @NamedQuery(name = "Typegrade.findAll", query = "SELECT t FROM Typegrade t"),
    @NamedQuery(name = "Typegrade.findById", query = "SELECT t FROM Typegrade t WHERE t.id = :id"),
    @NamedQuery(name = "Typegrade.findByName", query = "SELECT t FROM Typegrade t WHERE t.name = :name"),
    @NamedQuery(name = "Typegrade.findByGrade", query = "SELECT t FROM Typegrade t WHERE t.grade = :grade")})
public class Typegrade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "grade")
    private Float grade;
    @JoinColumn(name = "grade_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonBackReference
    private Grade gradeId;

    public Typegrade() {
    }

    public Typegrade(Long id) {
        this.id = id;
    }

    public Typegrade(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public Grade getGradeId() {
        return gradeId;
    }

    public void setGradeId(Grade gradeId) {
        this.gradeId = gradeId;
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
        if (!(object instanceof Typegrade)) {
            return false;
        }
        Typegrade other = (Typegrade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cln.pojo.Typegrade[ id=" + id + " ]";
    }
    
}
