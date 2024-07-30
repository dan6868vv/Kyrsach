package com.knf.dev.demo.service.impl;

import com.knf.dev.demo.dto.SpecDto;
import com.knf.dev.demo.entity.Spec;
import com.knf.dev.demo.repository.SpecRepository;
import com.knf.dev.demo.repository.UserRepository;
import com.knf.dev.demo.service.SpecService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SpecServiceImpl implements SpecService {
//public class SpecServiceImpl {

    private SpecRepository specRepository;

    public SpecServiceImpl(SpecRepository specRepository) {
        this.specRepository = specRepository;
    }

//    @Override
    public Spec save(SpecDto specDto) {
        var spec = new Spec();
        try {
            System.out.println("TRY save spec");
            spec = new Spec(specDto.getId(), specDto.getSpeciality());
            System.out.println("id="+specDto.getId()+" speciality="+specDto.getSpeciality());
            System.out.println("IS save spec");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return specRepository.save(spec);
    }

//    @Override
    public List<Spec> getAll() {
        System.out.println("getAll");
        return specRepository.findAll();
    }

    @Override
    public String getSpecialityById(Long id) {
        for(Spec spec : getAll()) {
            if(spec.getId() == id) {
                return spec.getSpeciality();
            }
        }
        return null;
    }


    public List<Long> findBySpeciality(String speciality) {
        List<Spec> specList = specRepository.findAll();
        List<Long> returnList = new ArrayList<>();
        for (Spec spec : specList) {
            if (spec.getSpeciality().equals(speciality)) {
                returnList.add(spec.getId());
            }
        }
        return returnList;
    }
//    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("User is found!!! "+username);
//        var spec = specRepository.findByEmail(username);
//        if (spec == null) {
//            throw new UsernameNotFoundException
//                    ("Invalid username or password.");
//        }
////        return new org.springframework.security
////                .core.userdetails.Spec(spec.getId(),spec.getSpeciality());
//        return new org.springframework.security
//                .core.userdetails
        return null;
    }

}
