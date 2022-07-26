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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller("ProfileControllerBean")
public class ProfileController {

    @Autowired
    private PersonRepository personRepository;

    // Displaying the User Profile
    @RequestMapping("/displayProfile")
    public ModelAndView displayProfilePage(Model model, HttpSession httpSession) {
        // Get the loggedInPerson session which was passed from Dashboard
        Person person = (Person) httpSession.getAttribute("loggedInPerson");

        // Creating new profile with setting the already having data like Name & Email
        Profile profile = new Profile();
        profile.setName(person.getName());
        profile.setEmail(person.getEmail());

        // When person has updated his details set them in field
        if (person.getAddress() != null && person.getAddress().getAddressId() > 0) {
            profile.setMobileNumber(person.getAddress().getMobileNumber());
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }

        ModelAndView modelAndView = new ModelAndView("profile.html");
        modelAndView.addObject("profile", profile);
        return modelAndView;
    }

    // Updating the user profile
    @PostMapping(value = "/updateProfile")
    public String updateUserProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors,
                                    HttpSession httpSession, RedirectAttributes redirectAttributes) {
        // If there are any validation error show them on page
        if (errors.hasErrors())
            return "profile.html";

        Person person = (Person) httpSession.getAttribute("loggedInPerson");
        person.setName(profile.getName());
        person.setEmail(profile.getEmail());

        // If the address details are empty create new address object
        if (person.getAddress() == null || !(person.getAddress().getAddressId() > 0)) {
            person.setAddress(new Address());
        }
        // Setting all the data again (In case User has modified any of the field it may help)
        person.getAddress().setMobileNumber(profile.getMobileNumber());
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setState(profile.getState());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setZipCode(profile.getZipCode());

        //Copying the saved person object and passing it httpSession. So that profile will be updated with new details.
        Person savedPerson = personRepository.save(person);
        httpSession.setAttribute("loggedInPerson", savedPerson);

        redirectAttributes.addFlashAttribute("successMessage", "Your profile has been updated successfully.");
        return "redirect:/displayProfile";
    }
}
