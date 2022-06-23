package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Holiday;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HolidaysController {

    @GetMapping("/holidays")
    public String displayHolidays(Model model) {
        List<Holiday> holidays = Arrays.asList(
            new Holiday("Jan 1","New Year",Holiday.Type.FEDERAL),
            new Holiday("Jan 26","Republic Day",Holiday.Type.FEDERAL),
            new Holiday("Feb 19","ShivJayanti",Holiday.Type.FEDERAL),
            new Holiday("Mar 11","Mahashivratri",Holiday.Type.FESTIVAL),
            new Holiday("Apr 2","Good Friday",Holiday.Type.FEDERAL),
            new Holiday("Apr 13","Gudhi Padva",Holiday.Type.FESTIVAL),
            new Holiday("May 1","Maharashtra Day",Holiday.Type.FEDERAL),
            new Holiday("May 13","Ramzan Eid",Holiday.Type.FESTIVAL),
            new Holiday("Jul 20","Bakri Eid",Holiday.Type.FESTIVAL),
            new Holiday("Aug 15","Independence Day",Holiday.Type.FEDERAL),
            new Holiday("Oct 2","Gandhi Jayanti",Holiday.Type.FEDERAL),
            new Holiday("Nov 16","Diwali",Holiday.Type.FESTIVAL),
            new Holiday("Dec 25","Christmas",Holiday.Type.FESTIVAL)
        );
        Holiday.Type[] types = Holiday.Type.values();
        for(Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }

        return "holidays.html";
    }
}
