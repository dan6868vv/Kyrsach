package com.knf.dev.demo.controller;

import com.knf.dev.demo.service.UserScheduleService;
import com.knf.dev.demo.service.UserService;
import com.knf.dev.demo.service.impl.SpecServiceImpl;
import com.knf.dev.demo.service.impl.UserScheduleImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/doctor-menu")
public class DoctorMenuController {
    private final UserScheduleImpl userScheduleImpl;
    private final UserScheduleService userScheduleService;
    private final UserService userService;
    private final SpecServiceImpl specServiceImpl;

    public DoctorMenuController(UserScheduleImpl userScheduleImpl,
                                UserScheduleService userScheduleService,
                                UserService userService, SpecServiceImpl specServiceImpl) {
        this.userScheduleImpl = userScheduleImpl;
        this.userScheduleService = userScheduleService;
        this.userService = userService;
        this.specServiceImpl = specServiceImpl;
    }


    @GetMapping("/")
    public String doctorMenu() {
        return "doctor-menu/home";
    }

    @GetMapping("/schedule")
    public String schedule(Model model) {
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("mySchedules",
                userScheduleImpl.findByEmailForDoctor(authentication.getName()));
        model.addAttribute("users",userService);
        return "doctor-menu/schedule";
    }

    @GetMapping("/schedule/appointment/{id}")
    public String doctorMenuSetAppointment(@PathVariable int id, Model model) {
        System.out.println("id: " + id);
        model.addAttribute("schedule", userScheduleImpl.findById(id));
//        model.addAttribute("appointDoctor",(String)"");
        return "doctor-menu/setAppointment";
    }

    @PostMapping("/schedule/appointment/{id}")
    public String doctorMenuAppointment(@PathVariable int id, Model model,
                                        @RequestParam String appointment) {
        userScheduleService.saveAppointment(id,appointment);
        return "redirect:/doctor-menu/schedule";
    }

    @GetMapping("/schedule/change-doctor/{id}")
    public String changeDoctor(@PathVariable("id") long id, Model model) {
        model.addAttribute("doctors", userService.findDoctorBySpec(specServiceImpl
                .getSpecialityById(userScheduleService.findOneById(id).getDocId())));
        return "doctor-menu/change-doctor";
    }

    @PostMapping("schedule/change-doctor/{id}")
    public String changeDoctorPost(@PathVariable("id") long id, Model model,
                                   @RequestParam long newDoctorId) {
        System.out.println("POST CHANGE DOCTOR");
        System.out.println("NewDoctorId: " + newDoctorId);
        userScheduleService.changeDoctorId(id,newDoctorId);
        return "redirect:/doctor-menu/schedule";
    }
}
