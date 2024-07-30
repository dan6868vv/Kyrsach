
package com.knf.dev.demo.controller;

import com.knf.dev.demo.dto.SpecDto;
import com.knf.dev.demo.dto.UserCreatingDto;
import com.knf.dev.demo.dto.UserRegistrationDto;
import com.knf.dev.demo.entity.Spec;
import com.knf.dev.demo.entity.User;
import com.knf.dev.demo.entity.UserSchedule;
import com.knf.dev.demo.repository.UserRepository;
import com.knf.dev.demo.repository.UserScheduleRepository;
import com.knf.dev.demo.service.SpecService;
import com.knf.dev.demo.service.UserService;
import com.knf.dev.demo.service.impl.UserScheduleImpl;
import com.knf.dev.demo.service.impl.UserServiceImpl;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

@Controller
@RequestMapping("/admin")
public class AdminController {

//    private final UserScheduleImpl userScheduleImpl;
    private final UserRepository userRepository;
    private final UserScheduleRepository userScheduleRepository;
    private final UserScheduleImpl userScheduleImpl;
    private UserService userService;
    private SpecService specService;
    private UserServiceImpl userServiceImpl;
    public AdminController(UserService userService, SpecService specService,
                           UserServiceImpl userServiceImpl, UserRepository userRepository, UserScheduleRepository userScheduleRepository, UserScheduleImpl userScheduleImpl) {
        super();
        this.userService = userService;
        this.specService = specService;
        this.userService = userService;
//        this.userScheduleImpl = userScheduleImpl;
        this.userRepository = userRepository;
        this.userScheduleRepository = userScheduleRepository;
        this.userScheduleImpl = userScheduleImpl;
    }
    @GetMapping("/menu")
    public String menu(Model model) {

        return "admin/menu";
    }
//    @GetMapping("/addDoctor")
//    public String showAddDoctorForm(Model model,Model rol) {
//        model.addAttribute("doctor", new UserCreatingDto());
//        model.addAttribute("spec",new SpecDto());
//        return "admin/addDoctor";}
//
//    @PostMapping("/addDoctor")
//    public String addDoctor(@ModelAttribute UserCreatingDto dto,@ModelAttribute SpecDto specDto) {
//        try {
//            specDto.setId(userService.saveCreate(dto).getId());
//            specService.save(specDto);
//        }catch(Exception e)
//        {
//            System.out.println(e);
//        }
//        return "redirect:/";
//    }
    @GetMapping("/users/add")
    public String addUser(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "admin/addUser";
    }


    @GetMapping("/users")
    public String searchUsers(@RequestParam("keyword") String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("users", userRepository.searchByKeyword(keyword));
        } else {
            model.addAttribute("users", userService.getAll());
        }
        return "admin/users";
    }
//    @GetMapping("/schedule")
//    public String showSchedule(@RequestParam("keyword") String keyword, Model model) {
//        if (keyword != null && !keyword.isEmpty()) {
//
//            model.addAttribute("schedules", userScheduleRepository.searchByKeyword(keyword,userRepository.findAll()));
//        } else {
//            model.addAttribute("users", userService.getAll());
//        }
//        return "admin/users";
//    }
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userRepository.deleteById(id);
        return "redirect:/admin/users?keyword=";
    }

//    @GetMapping("getTalon")
//    public String getTalon(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//
//        model.addAttribute("schedule",userScheduleImpl.findByEmailString(username));
//        return "admin/talon";
//    }
    @GetMapping("/users/addUser")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new UserCreatingDto());
        return "admin/addUser";
    }

    @PostMapping("/users/addUser")
    public String createUser(@ModelAttribute UserCreatingDto dto) {
        userService.saveCreate(dto);
        return "redirect:/admin/users?keyword=";
    }
    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "/admin/editUser";
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute User user) {
        userService.updateUser(id, user);
        return "redirect:/admin/users?keyword=";
    }

    @GetMapping("/talons")
    public String showTalons(@RequestParam("keyword") String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            try {
                List<User> userList = new ArrayList<>();
                userList = userRepository.searchByKeyword("keyword");
                List<UserSchedule> userScheduleList = userScheduleRepository.findAll();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
//            model.addAttribute("talons", userScheduleRepository.findAll());
//            model.addAttribute("users",userRepository.searchByKeyword("keyword"));
//            model.addAttribute("users",userRepository.searchByKeyword("keyword"));
//            List<User> modelList= new ArrayList<>();
//            for(UserSchedule userSchedule : userScheduleList){
//
//            }
        } else {
            try {
                model.addAttribute("users", userRepository.findAll());
                model.addAttribute("talons", userScheduleRepository.findAll());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return "admin/talons";
    }

    @GetMapping("/doctor/edit")
    public String editDoctor(@RequestParam("id") long id, Model model){
        SpecDto specDto = new SpecDto();
        specDto.setId(id);
        System.out.println(specDto.getId());
        model.addAttribute("id",id);
        model.addAttribute("spec",specDto);
        return "admin/editDoctor";
    }
    @PostMapping("/doctor/edit/{id}")
    public String editDoctorPost(@PathVariable("id") long id,@ModelAttribute SpecDto specDto){
        System.out.println("id:"+specDto.getId() + " speciality:"+specDto.getSpeciality());
        specDto.setId(id);
        System.out.println("After set id in editDoctorPost");
        System.out.println("id:"+specDto.getId() + " speciality:"+specDto.getSpeciality());
        try {
            specService.save(specDto);
        }catch(Exception e)
        {
            System.out.println(e);
        }
//        return "admin/users?keyword=";
        return "redirect:/admin/users?keyword=";
    }

    @GetMapping("/users/talons/{id}")
    public String showTalons(@PathVariable("id") long id, Model model){
        List<String> months= new ArrayList<String>();
        months.add("января");
        months.add("февраля");
        months.add("марта");
        months.add("апреля");
        months.add("мая");
        months.add("июня");
        months.add("июля");
        months.add("августа");
        months.add("сентября");
        months.add("октября");
        months.add("ноября");
        months.add("декабря");
        model.addAttribute("months", months);
        model.addAttribute("schedules",userScheduleImpl.findByUserId(id));
        return "admin/userTalons";
    }

    @GetMapping("/talons/delete/{id}")
    public String deleteTalon(@PathVariable("id") long id){

        try {
            userScheduleRepository.deleteById(id);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/admin/talons?keyword=";
    }

//    @GetMapping("/addDoctor")
//    public String showAddDoctorForm(Model model,Model rol) {
//        model.addAttribute("doctor", new UserCreatingDto());
//        model.addAttribute("spec",new SpecDto());
//        return "admin/addDoctor";}
//
//    @PostMapping("/addDoctor")
//    public String addDoctor(@ModelAttribute UserCreatingDto dto,@ModelAttribute SpecDto specDto) {
//        try {
//            specDto.setId(userService.saveCreate(dto).getId());
//            specService.save(specDto);
//        }catch(Exception e)
//        {
//            System.out.println(e);
//        }
//        return "redirect:/";
//    }
}
