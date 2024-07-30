package com.knf.dev.demo.service;

import com.knf.dev.demo.dto.SpecDto;
import com.knf.dev.demo.dto.UserCreatingDto;
import com.knf.dev.demo.dto.UserRegistrationDto;
import com.knf.dev.demo.entity.Spec;
import com.knf.dev.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

//public interface SpecService extends UserDetailsService {
public interface SpecService {
    List<Long> findBySpeciality(String spciality);
    Spec save(SpecDto specDto);
    List<Spec> getAll();
    String getSpecialityById(Long id);
}
