package com.webapp.ygsschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    // For Showing the main Index/Home Page
    @RequestMapping(value = {"", "/", "index"})
    public String showHomePage() {
        return "index.html";
    }

}
