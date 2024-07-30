package com.knf.dev.demo.service;

import com.knf.dev.demo.dto.UserScheduleDto;
import com.knf.dev.demo.entity.UserSchedule;
import org.springframework.http.converter.json.GsonBuilderUtils;

public interface UserScheduleService {
    UserSchedule save(UserScheduleDto userScheduleDto);
    void delete(Long id);
    void saveAppointment(long id, String appointment);
    UserSchedule findOneById(long id);
    void changeDoctorId(Long id, long doctorId);
}
