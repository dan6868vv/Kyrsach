package com.knf.dev.demo.dto;

import jakarta.persistence.Column;

public class UserScheduleDto {
    private Long docId;
    private Long patientId;
    private String docEmail;
    private String patientEmail;
    private String timeStr;
    private Integer day;
    private Integer month;
    private String appointment;

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public UserScheduleDto(Long docId, Long patientId, String docEmail, String patientEmail,
                           Integer day, Integer month, String timeStr,String appointment) {
        this.docId = docId;
        this.patientId = patientId;
        this.docEmail = docEmail;
        this.patientEmail = patientEmail;
        this.timeStr = timeStr;
        this.day = day;
        this.month = month;
        this.appointment = appointment;
    }

    public Integer getMonth() {
        return month;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }



    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getDocEmail() {
        return docEmail;
    }

    public void setDocEmail(String docEmail) {
        this.docEmail = docEmail;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public UserScheduleDto() {}


}
