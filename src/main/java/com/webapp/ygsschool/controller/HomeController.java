package com.webapp.ygsschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value= {"","/","index"})
    public String showHomePage(){
        return "index.html";
    }

}
