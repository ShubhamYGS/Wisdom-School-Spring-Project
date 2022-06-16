package com.webapp.ygsschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value= {"","/","/home"})
    public String showHomePage(Model model){
        model.addAttribute("username","Shubham");
        return "home.html";
    }
}
