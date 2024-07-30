package com.knf.dev.demo.dto;

public class SpecDto {
    long id;
    String speciality;
    public SpecDto() {}

    public SpecDto(long id) {
        this.id = id;
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
