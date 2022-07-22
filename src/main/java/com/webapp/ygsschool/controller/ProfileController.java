package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Address;
import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.model.Profile;
import com.webapp.ygsschool.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller("ProfileControllerBean")
public class ProfileController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/displayProfile")
    public ModelAndView displayProfilePage(Model model, HttpSession httpSession){
        Person person = (Person) httpSession.getAttribute("loggedInPerson");
        Profile profile = new Profile();
        profile.setName(person.getName());
        profile.setEmail(person.getEmail());

        if(person.getAddress()!=null && person.getAddress().getAddressId()>0) {
            profile.setMobileNumber(person.getAddress().getMobileNumber());
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }

        ModelAndView modelAndView = new ModelAndView("profile.html");
        modelAndView.addObject("profile",profile);
        return modelAndView;
    }

    @PostMapping(value = "/updateProfile")
    public String updateUserProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors,
                                  HttpSession httpSession) {
        if(errors.hasErrors())
            return "profile.html";

        Person person = (Person) httpSession.getAttribute("loggedInPerson");
        person.setName(profile.getName());
        person.setEmail(profile.getEmail());

        if(person.getAddress()==null || !(person.getAddress().getAddressId()>0)) {
            person.setAddress(new Address());
        }
        person.getAddress().setMobileNumber(profile.getMobileNumber());
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setState(profile.getState());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setZipCode(profile.getZipCode());

        //Copying the saved person object and passing it httpSession. So that profile will be updated with new details.
        Person savedPerson = personRepository.save(person);
        httpSession.setAttribute("loggedInPerson",savedPerson);

        return "redirect:/displayProfile";
    }
}
