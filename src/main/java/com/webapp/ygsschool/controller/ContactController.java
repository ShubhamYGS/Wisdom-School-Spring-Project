package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Contact;
import com.webapp.ygsschool.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class ContactController {

    private ContactService contactService;

    // Return the contact Page
    @RequestMapping("/contact")
    public String showContactPage(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact.html";
    }

    // Performing DI/Autowiring with the help of constructor
    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
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
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors, RedirectAttributes redirectAttributes) {
        // If there are any validation errors return the page with errors
        if (errors.hasErrors()) {
            log.error("Contact form validation failed due to: " + errors);
            return "contact.html";
        }
        contactService.saveMessageDetails(contact);
        redirectAttributes.addFlashAttribute("successMessage","Your query has been submitted successfully. We will get to back to you within 24 hrs.");
        return "redirect:/contact";
    }

    // Displaying Admin Career Page with JPA Pagination & Sorting
    @RequestMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(Model model, @PathVariable(name = "pageNum") int pageNum,
                                        @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir) {
        // Taking the messagePage as Page return type with Page number, Sorting field & Direction
        Page<Contact> msgPage = contactService.findMsgsWithOpenStatus(pageNum, sortField, sortDir);

        // Taking the content out of Page<Contact>
        List<Contact> contactMsgs = msgPage.getContent();

        ModelAndView modelAndView = new ModelAndView("messages.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", msgPage.getTotalPages());
        model.addAttribute("totalMsgs", msgPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        //Checking and returning the sort direction with if else condition
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("contactMsgs", contactMsgs);
        return modelAndView;
    }

    //Closing the message by returning displayMessages page with default value(Page: 1, sortField: Name & Direction: Descending)
    @RequestMapping("/closeMsg")
    public String closeMsg(@RequestParam int id, Authentication authentication, RedirectAttributes redirectAttributes) {
        contactService.updateMsgStatus(id, authentication.getName());
        redirectAttributes.addFlashAttribute("successMessage","Ticket has been closed successfully");
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc";
    }
}