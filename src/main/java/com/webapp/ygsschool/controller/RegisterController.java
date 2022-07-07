package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @RequestMapping("/register")
    public String doRegister(Model model) {
        model.addAttribute("person",new Person());
        return "register.html";
    }

    @PostMapping(value = "/doregister")
    public String saveMessage(@Valid @ModelAttribute("person") Person person, Errors errors){
        if(errors.hasErrors()) {
            return "register.html";
        }
        //If there are no validation errors redirect to login page with request parameters (Accepting these param. on login page)
        return "redirect:/login?register=true";
    }
}
