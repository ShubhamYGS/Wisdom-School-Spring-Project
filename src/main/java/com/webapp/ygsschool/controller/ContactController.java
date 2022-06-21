package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Contact;
import com.webapp.ygsschool.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ContactController {

    private ContactService contactService;

    @RequestMapping("/contact")
    public String showContactPage() {
        return "contact.html";
    }

    @Autowired
    public ContactController(ContactService contactService){
        this.contactService=contactService;
    }

    //First way
//    @PostMapping(value = "/saveMsg")
//    public ModelAndView saveMessage(@RequestParam String name, @RequestParam String email, @RequestParam String subject, @RequestParam String message) {
//        log.info("Name: "+name);
//        log.info("Email: "+email);
//        log.info("Subject: "+subject);
//        log.info("Message: "+message);
//        return new ModelAndView("redirect:/contact");
//    }

    //Second way using POJO class and service class
     @PostMapping(value = "/saveMsg")
     public ModelAndView saveMessage(Contact contact){
        contactService.saveMessageDetails(contact);
         return new ModelAndView("redirect:/contact");
     }
}
