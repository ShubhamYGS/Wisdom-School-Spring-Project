package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Holiday;
import com.webapp.ygsschool.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HolidaysController {

    @Autowired
    HolidaysRepository holidaysRepository;

    // Displaying Holidays Page
    @GetMapping("/holidays")
    public String displayHolidays(Model model) {
        // Find out and store all the holidays from database to holidays field
        Iterable<Holiday> holidays = holidaysRepository.findAll();

        // Converting all the holidays from type Iterable to List with StreamSupport
        List<Holiday> holidayList = StreamSupport.stream(holidays.spliterator(), false)
                .collect(Collectors.toList());

        // Stroing types of holidays under types field
        Holiday.Type[] types = Holiday.Type.values();

        // Iterate over all the holidays and store them in different lists
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidayList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }

        return "holidays.html";
    }
}
