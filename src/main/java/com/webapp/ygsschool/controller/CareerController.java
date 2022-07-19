package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.constants.FormConstants;
import com.webapp.ygsschool.model.Career;
import com.webapp.ygsschool.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CareerController {

    @Autowired
    private CareerService careerService;

    @RequestMapping("/career")
    public String showCareerPage(Model model) {
        model.addAttribute("career",new Career());
        return "career.html";
    }

    @PostMapping(value = "/applyJob")
    public String applyForJob(@Valid @ModelAttribute("career") Career career, BindingResult errors,
                              @RequestParam("file")MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        //Check if file is empty
        if(multipartFile.isEmpty()) {
            ObjectError error = new ObjectError("career","Resume/CV file must not be empty");
            errors.addError(error);
        }
        if(errors.hasErrors()) {
            return "career.html";
        }
        careerService.saveJobDetails(career, multipartFile, redirectAttributes);
        return "redirect:/career";
    }

    @RequestMapping("/admin/displayCareer/{status}")
    public ModelAndView displayCareer(Model model, @PathVariable String status) {
        ModelAndView modelAndView = new ModelAndView("displaycareer.html");
        List<Career> careerList =  careerService.showJobsByType(status);
        modelAndView.addObject("careerList",careerList);
        return modelAndView;
    }
}
