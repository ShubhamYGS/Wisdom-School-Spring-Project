package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepository;

    // Showing the dashboard to user
    @RequestMapping("/dashboard")
    public String showDashboardPage(Model model, Authentication authentication, HttpSession httpSession) {
        // Retrieving the logged in person details
        Person person = personRepository.findByEmail(authentication.getName());

        //Populating the name of logged in to user to dashboard
        model.addAttribute("username", person.getName());
        //Displaying the role of user
        model.addAttribute("roles", authentication.getAuthorities().toString());

        // Check if user has been added to any of the class. If yes, then pass the className to front end
        if (person.getWisdomClass() != null && person.getWisdomClass().getName() != null)
            model.addAttribute("studentClass", person.getWisdomClass().getName());

        // Storing the user session for further usage [profile page]
        httpSession.setAttribute("loggedInPerson", person);
        return "dashboard.html";
    }
}
