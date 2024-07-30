package com.knf.dev.demo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "user_schedule")
public class UserSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doc_id")
    private Long docId;
    @Column(name="patient_id")
    private Long patientId;
    @Column(name = "doc_email")
    private String docEmail;
    @Column(name = "patient_email")
    private String patientEmail;
    @Column(name = "time_str")
    private String timeStr;
    private Integer day;
    private Integer month;

    @Column(name="appointment")
    private String appointment;

    public UserSchedule(Long docId, Long patientId, String docEmail, String patientEmail,
                         String timeStr, Integer day, Integer month, String appointment) {
        this.docId = docId;
        this.patientId = patientId;
        this.docEmail = docEmail;
        this.patientEmail = patientEmail;
        this.timeStr = timeStr;
        this.day = day;
        this.month = month;
        this.appointment = appointment;
    }

    public UserSchedule(){}

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getDocEmail() {
        return docEmail;
    }

    public void setDocEmail(String docEmail) {
        this.docEmail = docEmail;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }



    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

}