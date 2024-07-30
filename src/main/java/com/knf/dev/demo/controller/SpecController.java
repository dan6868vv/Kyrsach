package com.knf.dev.demo.controller;

import com.knf.dev.demo.entity.Spec;
import com.knf.dev.demo.entity.User;
import com.knf.dev.demo.service.SpecService;
import com.knf.dev.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("specializations")
public class SpecController {
    private SpecService specService;
    private UserService userService;
    public SpecController(SpecService specService, UserService userService){
        this.specService = specService;
        this.userService = userService;
    }
    @GetMapping("/{spec}")
    public String spec(@PathVariable String spec, Model model){
        model.addAttribute("spec", spec);
        List<Long> specList = specService.findBySpeciality(spec);
        List<User> userList = userService.getAll();
        List<User> returnUserList = new ArrayList<>();
        for(User user : userList){
            if(specList.contains(user.getId())){
                returnUserList.add(user);
            }
        }
        model.addAttribute("doctors",returnUserList);
        return "specializations/order";
    }
//    @GetMapping("/ophthalmologist")
//    public String ophthalmologist() {return "specializations/ophthalmologist";}
//
//    @GetMapping("/neurologist")
//    public String neurologist() {return "specializations/neurologist";}
//
//    @GetMapping("/dentist")
//    public String dentist(Model model) {
//        List<Long> specList = specService.findBySpeciality("dentist");
//        List<User> userList = userService.getAll();
//        List<User> returnUserList = new ArrayList<>();
//        for(User user : userList){
//            if(specList.contains(user.getId())){
//                returnUserList.add(user);
//            }
//        }
//        model.addAttribute("doctors",returnUserList);
//        return "specializations/dentist";}
//
//    @GetMapping("/traumatologist")
//    public String traumatologist() {return "specializations/traumatologist";}
//
//    @GetMapping("/urologist")
//    public String urologist() {return "specializations/urologist";}
//
//    @GetMapping("/lor")
//    public String lor() {return "specializations/lor";}
//
//
    @GetMapping("/choiceSpec")
    public String choice() {
        return "specializations/choiceSpec";
    }
}
