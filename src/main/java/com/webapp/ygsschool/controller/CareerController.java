package com.webapp.ygsschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CareerController {

    @RequestMapping("/career")
    public String showCareerPage() {
        return "career.html";
    }
}
