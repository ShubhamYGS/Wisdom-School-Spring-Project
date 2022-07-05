package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Holiday;
import com.webapp.ygsschool.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HolidaysController {

    @Autowired
    HolidaysRepository holidaysRepository;

    @GetMapping("/holidays")
    public String displayHolidays(Model model) {
        List<Holiday> holidays = holidaysRepository.getAllHolidays();
        Holiday.Type[] types = Holiday.Type.values();
        for(Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }

        return "holidays.html";
    }
}
