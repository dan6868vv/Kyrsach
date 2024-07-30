package com.knf.dev.demo.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "spec", uniqueConstraints =
@UniqueConstraint(columnNames = "id"))
public class Spec {

    @Id
    private long id;
    @Column(name="speciality")
    private String speciality;
    private String email;

    public Spec(){}

//    public Spec(String speciality) {
//        this.speciality = speciality;
//    }

    public Spec(long id, String speciality) {
        this.id = id;
        this.speciality = speciality;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
