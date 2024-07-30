package com.knf.dev.demo.controller;

import com.knf.dev.demo.repository.UserScheduleRepository;
import com.knf.dev.demo.service.SpecService;
import com.knf.dev.demo.service.impl.UserScheduleImpl;
import com.knf.dev.demo.service.impl.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
public class HomeController {


   private final UserScheduleImpl userScheduleImpl;
   private final UserScheduleRepository userScheduleRepository;
   private final UserServiceImpl userServiceImpl;
   private final SpecService specService;
   public HomeController(UserScheduleImpl userScheduleImpl, UserScheduleRepository userScheduleRepository, UserServiceImpl userServiceImpl, SpecService specService) {
      this.userScheduleImpl = userScheduleImpl;
      this.userScheduleRepository = userScheduleRepository;
       this.userServiceImpl = userServiceImpl;
       this.specService = specService;
   }
   @GetMapping("/myTalons/delete/{id}")
   public String deleteTalon(Model model, @PathVariable long id) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if(userScheduleImpl.findById(id).getPatientEmail().equals(auth.getName()) ){

         try {
            userScheduleRepository.deleteById(id);
         }
         catch (Exception e) {
            System.out.println(e.getMessage());
         }
      }
//      userScheduleImpl.delete(id);
      return "redirect:/myTalons";
   }
   @GetMapping("/login")
   public String login() {
      return "login";
   }

   @GetMapping("/")
   public String home(Model model) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      model.addAttribute("authen", auth);
      model.addAttribute("nameUser", userServiceImpl.getNameByEmail(auth.getName()));
      return "index";
   }
   @GetMapping("/myTalons")
   public String myTalons(Model model) {
//      List<String> months= new ArrayList<String>();
//      months.add("января");
//      months.add("февраля");
//      months.add("марта");
//      months.add("апреля");
//      months.add("мая");
//      months.add("июня");
//      months.add("июля");
//      months.add("августа");
//      months.add("сентября");
//      months.add("октября");
//      months.add("ноября");
//      months.add("декабря");
//      model.addAttribute("months", months);
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      model.addAttribute("mySchedules",
              userScheduleImpl.findByEmailForUser(authentication.getName()));
      model.addAttribute("users", userServiceImpl);
      model.addAttribute("currentDate", LocalDate.now());
      model.addAttribute("specialities", specService);
      HashMap<String, String> specialitiesString = new HashMap<>();
      specialitiesString.put("lor", "ЛОР");
      specialitiesString.put("urologist", "Уролог");
      specialitiesString.put("neurologist", "Невролог");
      specialitiesString.put("dentist", "Стоматолог");
      specialitiesString.put("ophthalmologist", "Офтальмолог");
      specialitiesString.put("traumatologist", "Травматолог");
      model.addAttribute("specialitiesString", specialitiesString);
      // Передаем список месяцев
      List<String> months = Arrays.asList("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь");
      model.addAttribute("months", months);
      return "myTalons";
   }

}




