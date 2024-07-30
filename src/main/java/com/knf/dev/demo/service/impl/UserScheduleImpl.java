package com.knf.dev.demo.service.impl;

import com.knf.dev.demo.dto.UserScheduleDto;
import com.knf.dev.demo.entity.User;
import com.knf.dev.demo.entity.UserSchedule;
import com.knf.dev.demo.repository.UserRepository;
import com.knf.dev.demo.repository.UserScheduleRepository;
import com.knf.dev.demo.service.UserScheduleService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserScheduleImpl implements UserScheduleService {
    private final UserRepository userRepository;
    private List<String> talonTime=new ArrayList<>();

    private final UserScheduleRepository userScheduleRepository;
    public UserScheduleImpl(UserScheduleRepository userScheduleRepository, UserRepository userRepository) {

        this.userScheduleRepository = userScheduleRepository;
        talonTime.add("9:00-9:20");//0
        talonTime.add("9:20-9:40");//1
        talonTime.add("9:40-10:00");//2
        talonTime.add("10:00-10:20");//3
        talonTime.add("10:20-10:40");
        talonTime.add("10:40-11:00");
        talonTime.add("11:00-11:20");
        talonTime.add("11:20-11:40");
        talonTime.add("11:40-12:00");
        talonTime.add("12:00-12:20");
        talonTime.add("12:20-12:40");
        talonTime.add("12:40-13:00");
        talonTime.add("14:00-14:20");
        talonTime.add("14:20-14:40");
        talonTime.add("14:40-15:00");
        talonTime.add("15:00-15:20");
        talonTime.add("15:20-15:40");
        talonTime.add("15:40-16:00");
        this.userRepository = userRepository;
    }
    public List<UserSchedule> findAll() {
        return userScheduleRepository.findAll();
    }
    public List<String> findByEmailString(String email) {
        List<String> list = new ArrayList<>();
        for(UserSchedule userSchedule : findAll()) {
            if (userSchedule.getPatientEmail() != null) {
                if(userSchedule.getPatientEmail().equals(email)) {
                    list.add(userSchedule.toString());
                }
            }

        }
        return list;
    }
    public List<UserSchedule> findByEmailForDoctor(String email) {
        List<UserSchedule> list = new ArrayList<>();
        for(UserSchedule userSchedule : findAll()) {
            if(userSchedule.getDocEmail().equals(email)) {
                list.add(userSchedule);
            }
        }
        return list;
    }

    public List<UserSchedule> findByEmailForUser(String email) {
        List<UserSchedule> list = new ArrayList<>();
        for(UserSchedule userSchedule : findAll()) {
            if(userSchedule.getPatientEmail().equals(email)) {
                list.add(userSchedule);
            }
        }
        return list;
    }

    public List<String> findByEDMStringTime(String email,int day,int month){
        System.out.println("day:"+day+"month:"+month);
        List<String> list = new ArrayList<>();
        for(UserSchedule userSchedule : findAll()) {
            if(userSchedule.getDocEmail()!=null){
            if(userSchedule.getDocEmail().equals(email) && userSchedule.getDay()==day && userSchedule.getMonth()==month) {
                list.add(userSchedule.getTimeStr());
            }}
        }
        return list;
    }

    public List<String> findByEmailStringTime(String email){
        List<String> list = new ArrayList<>();
        for(UserSchedule userSchedule : findAll()) {
            if(userSchedule.getDocEmail()!=null){
                if(userSchedule.getDocEmail().equals(email)) {
                    list.add(userSchedule.getTimeStr());
                }}
        }
        return list;
    }
    public List<UserSchedule> findByEmailDayMonth(String email,int day,int month) {
        List<UserSchedule> list = new ArrayList<>();
        for(UserSchedule userSchedule : findAll()) {
            if(userSchedule.getDocEmail()!=null){
            if(userSchedule.getDocEmail().equals(email) && userSchedule.getDay()==day && userSchedule.getMonth()==month) {
                list.add(userSchedule);
            }}
        }
        return list;
    }

    public List<String> getTalonTime() {
        return talonTime;
    }

    @Override
    public UserSchedule save(UserScheduleDto userScheduleDto) {
        var userSchedule = new UserSchedule(userScheduleDto.getDocId(),userScheduleDto.getPatientId(),userScheduleDto.getDocEmail(),
                userScheduleDto.getPatientEmail(),userScheduleDto.getTimeStr(),
                userScheduleDto.getDay(),userScheduleDto.getMonth(),userScheduleDto.getAppointment());
        return userScheduleRepository.save(userSchedule);
    }
    public UserSchedule findById(long id){

         for(UserSchedule userSchedule : userScheduleRepository.findAll()) {
             if(userSchedule.getId()==id) {
                 return userSchedule;
             }
         }
         return null;
    }
    public List<UserSchedule> findByUserId(long id) {
        List<UserSchedule> list = new ArrayList<>();
        for(UserSchedule userSchedule : userScheduleRepository.findAll()) {
            if(userSchedule.getPatientId()==id || userSchedule.getDocId()==id) {
                list.add(userSchedule);
            }
        }
        return list;
    }

    @Override
    public void delete(Long id) {
        userScheduleRepository.delete(userScheduleRepository.getReferenceById(id));
    }

    @Override
    public void saveAppointment(long id, String appointment) {
        System.out.println("saveAppointment");
        try {
            UserSchedule existingUserSchedule = userScheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            existingUserSchedule.setAppointment(appointment);
            userScheduleRepository.save(existingUserSchedule);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public UserSchedule findOneById(long id) {
        for(UserSchedule userSchedule: findAll()){
            if(userSchedule.getId().equals(id)){
                return userSchedule;
            }
        }
        return null;
    }

    @Override
    public void changeDoctorId(Long id, long doctorId) {
        System.out.println("change Doctor Id");
        try {
            UserSchedule existingUserSchedule = userScheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            existingUserSchedule.setDocId(doctorId);
            existingUserSchedule.setDocEmail(userRepository.findDoctorById(doctorId).getEmail());
            userScheduleRepository.save(existingUserSchedule);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
