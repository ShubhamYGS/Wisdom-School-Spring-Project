package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Career;
import com.webapp.ygsschool.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    // Show career page
    @RequestMapping("/career")
    public String showCareerPage(Model model) {
        model.addAttribute("career", new Career());
        return "career.html";
    }

    // Once user click on apply job button this method will gets called
    @PostMapping(value = "/applyJob")
    public String applyForJob(@Valid @ModelAttribute("career") Career career, BindingResult errors,
                              @RequestParam("file") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        //Check if file is empty (If it is then add it lombok validation errors)
        if (multipartFile.isEmpty()) {
            ObjectError error = new ObjectError("career", "Resume/CV file must not be empty");
            errors.addError(error);
        }
        if (errors.hasErrors()) {
            return "career.html";
        }

        careerService.saveJobDetails(career, multipartFile, redirectAttributes);
        return "redirect:/career";
    }

    // Returning the Admin Career Page with requested status [OPEN, HIRED, REJECTED]
    @RequestMapping("/admin/displayCareer/{status}")
    public ModelAndView displayCareer(Model model, @PathVariable String status) {
        ModelAndView modelAndView = new ModelAndView("displaycareer.html");
        List<Career> careerList = careerService.showJobsByType(status);
        modelAndView.addObject("careerList", careerList);
        return modelAndView;
    }

    // Showing Job Profile of selected Person
    @GetMapping("/admin/displayCareer/viewProfile")
    public ModelAndView showJobProfile(@RequestParam("jobId") int jobId) {
        // find out the person by his job id
        Career career = careerService.findJobProfile(jobId);
        ModelAndView modelAndView = new ModelAndView("showjobprofile.html");
        modelAndView.addObject("career", career);
        return modelAndView;
    }

    // Reject Candidate
    @RequestMapping("/admin/displayCareer/rejectCandidate")
    public ModelAndView rejectCandidate(@RequestParam("jobId") int jobId, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayCareer/viewProfile?jobId=" + jobId);
        // If profile is found
        if (careerService.rejectCandidate(jobId)) {
            redirectAttributes.addFlashAttribute("successMessage", "You have successfully rejected this candidate");
            return modelAndView;
        }
        redirectAttributes.addFlashAttribute("failureMessage", "Unable to perform Reject Candidate action. Try again.");
        return modelAndView;
    }

    // Hire Candidate
    @GetMapping("/admin/displayCareer/hireCandidate")
    public ModelAndView hireCandidate(@RequestParam("jobId") int jobId, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayCareer/viewProfile?jobId=" + jobId);
        // If profile is found
        if (careerService.hireCandidate(jobId)) {
            redirectAttributes.addFlashAttribute("successMessage", "You have successfully hired this candidate");
            return modelAndView;
        }
        redirectAttributes.addFlashAttribute("failureMessage", "Unable to perform Hire Candidate action. Try again.");
        return modelAndView;
    }
}
