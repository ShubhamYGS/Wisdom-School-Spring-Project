package com.webapp.ygsschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value= {"","/","/index.html"})
    public String showHomePage(){
        return "index.html";
    }

    @RequestMapping("/testing.html")
    public String showTestingPage() {
        return "testing.html";
    }
}
