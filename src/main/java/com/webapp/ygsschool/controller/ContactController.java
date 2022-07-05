package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Contact;
import com.webapp.ygsschool.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class ContactController {

    private ContactService contactService;

    @RequestMapping("/contact")
    public String showContactPage(Model model) {
        model.addAttribute("contact",new Contact());
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
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors){
        if(errors.hasErrors()) {
            log.error("Contact form validation failed due to: "+errors);
            return "contact.html";
        }
        contactService.saveMessageDetails(contact);
        return "redirect:/contact";
    }

    @RequestMapping("/displayMessages")
    public ModelAndView displayMessages(Model model) {
        List<Contact> contactMsgs = contactService.findMsgsWithOpenStaus();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        modelAndView.addObject("contactMsgs",contactMsgs);
        return modelAndView;
    }
}