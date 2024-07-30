package com.knf.dev.demo.controller;

import com.knf.dev.demo.dto.SpecDto;
import com.knf.dev.demo.dto.UserCreatingDto;
import com.knf.dev.demo.dto.UserScheduleDto;
import com.knf.dev.demo.entity.UserSchedule;
import com.knf.dev.demo.repository.UserScheduleRepository;
import com.knf.dev.demo.service.UserScheduleService;
import com.knf.dev.demo.service.UserService;
import com.knf.dev.demo.service.impl.UserScheduleImpl;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    private final UserScheduleImpl userScheduleImpl;
    private UserScheduleService userScheduleService;
    private UserService userService;
    public DoctorController(UserService userService, UserScheduleImpl userScheduleImpl,
                            UserScheduleService userScheduleService) {
        this.userScheduleService = userScheduleService;

        this.userService = userService;
        this.userScheduleImpl = userScheduleImpl;
    }
    @GetMapping("{id}/talon/{year}-{month}-{day}")
        public String talon1 (Model model,@PathVariable("id") long id, @PathVariable("day") int day,
        @PathVariable("month") int month, @PathVariable("year") int year){
        try {
            model.addAttribute("doctor", userService.findDoctorById(id));
            model.addAttribute("docSchedule", userScheduleImpl.findByEmailDayMonth(userService.findDoctorById(id).getEmail(), day, month));
            model.addAttribute("docScheduleTimeString", userScheduleImpl.findByEDMStringTime(userService.findDoctorById(id).getEmail(), day, month));
            model.addAttribute("months", getMonthNames());
            model.addAttribute("allScheduleTimeString", userScheduleImpl.getTalonTime());
            model.addAttribute("day", day);
            model.addAttribute("year", year);
            model.addAttribute("month", month);
            model.addAttribute("newTalon", new UserScheduleDto());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            model.addAttribute("patient", userService.findUserByEmail(auth.getName()));
            LocalDate currentDate = LocalDate.now();
            model.addAttribute("toDay",currentDate );
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "doctor/talon";
    }


    @GetMapping("/{id}")
    public String getCalendar(@RequestParam(name = "month", required = false) Integer month, Model model,@PathVariable("id") long id) {
        model.addAttribute("doctor", userService.findDoctorById(id));
        model.addAttribute("docSchedule",userScheduleImpl.findByEmailString(userService.findDoctorById(id).getEmail()));
        model.addAttribute("docId", id);
        model.addAttribute("docScheduleBean",userScheduleImpl);
        LocalDate currentDate = LocalDate.now();
        model.addAttribute("toDay",currentDate );

        int year = currentDate.getYear();

        if (month == null) {
            month = currentDate.getMonthValue();
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int daysInMonth = yearMonth.lengthOfMonth();
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        List<List<LocalDate>> weeks = new ArrayList<>();
        List<LocalDate> week = new ArrayList<>();

        // Заполняем пустыми датами до первого дня месяца
        for (int i = 1; i < firstDayOfWeek; i++) {
            week.add(null);
        }

        for (int day = 1; day <= daysInMonth; day++) {
            week.add(LocalDate.of(year, month, day));
            if (week.size() == 7) {
                weeks.add(week);
                week = new ArrayList<>();
            }
        }

        // Заполняем пустыми датами до конца недели
        if (!week.isEmpty()) {
            while (week.size() < 7) {
                week.add(null);
            }
            weeks.add(week);
        }
        model.addAttribute("docScheduleTimeString", userScheduleImpl.findByEmailStringTime(userService.findDoctorById(id).getEmail()));
        model.addAttribute("weeks", weeks);
        model.addAttribute("currentMonth", month);
        model.addAttribute("months", getMonthNames());
        return "calendar";
    }

    private List<String> getMonthNames() {
        List<String> monthNames = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            monthNames.add(YearMonth.of(2024, i).getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("ru")));
        }
        return monthNames;
    }

    @GetMapping("/{id}/talon/{timeStr}+{day}+{month}+{docId}+{docEmail}+{patientEmail}+{patientId}")
    public String newTalon(@PathVariable int day, @PathVariable long id,
                           @PathVariable String docEmail, @PathVariable long docId,
                           @PathVariable int month, @PathVariable String patientEmail,
                           @PathVariable long patientId, @PathVariable String timeStr){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.findUserByEmail(auth.getName());
        if(userService.findUserByEmail(auth.getName()).getId().equals(patientId)
                || auth.getAuthorities().stream().toList().toString().contains("ROLE_ADMIN")){
        try {
            UserScheduleDto userScheduleDto = new UserScheduleDto(docId,patientId,docEmail,patientEmail,
                    day,month,timeStr,"");
            userScheduleService.save(userScheduleDto);
        }catch(Exception e)
        {
            System.out.println(e);
        }
        }
        return "redirect:/";
    }
    // Номер талона/День/Месяц  1->9:00-9:20 ; 2->9:20-9:40 ......


}
