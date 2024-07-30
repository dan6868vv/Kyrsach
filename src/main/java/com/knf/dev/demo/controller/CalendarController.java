package com.knf.dev.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;


@Controller
public class CalendarController {

    @GetMapping("/calendar")
    public String getCalendar(@RequestParam(name = "month", required = false) Integer month,
                              @RequestParam(name = "day", required = false) Integer day,
                              Model model) {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();

        if (month == null) {
            month = currentDate.getMonthValue();
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int daysInMonth = yearMonth.lengthOfMonth();
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        List<List<Map<String, Object>>> weeks = new ArrayList<>();
        List<Map<String, Object>> week = new ArrayList<>();

        // Заполняем пустыми датами до первого дня месяца
        for (int i = 1; i < firstDayOfWeek; i++) {
            week.add(null);
        }

        for (int i = 1; i <= daysInMonth; i++) {
            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", LocalDate.of(year, month, i));
            dayData.put("freeSlots", getFreeSlots(i));
            week.add(dayData);
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

        model.addAttribute("weeks", weeks);
        model.addAttribute("currentMonth", month);
        model.addAttribute("months", getMonthNames());

        if (day != null) {
            LocalDate selectedDate = LocalDate.of(year, month, day);
            model.addAttribute("selectedDate", selectedDate);
            model.addAttribute("timeSlots", getTimeSlots());
        }

        return "calendar2";
    }

    private int getFreeSlots(int day) {
        // Здесь ваша логика получения количества свободных талонов
        return (int) (Math.random() * 10); // Пример случайного количества свободных талонов
    }

    private List<String> getMonthNames() {
        List<String> monthNames = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            monthNames.add(YearMonth.of(2024, i).getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("ru")));
        }
        return monthNames;
    }

    private List<String> getTimeSlots() {
        List<String> timeSlots = new ArrayList<>();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        while (startTime.isBefore(endTime)) {
            timeSlots.add(startTime.toString());
            startTime = startTime.plusMinutes(20);
        }
        return timeSlots;
    }
}
