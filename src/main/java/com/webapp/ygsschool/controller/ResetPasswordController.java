package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.service.ForgotPasswordService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ResetPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @GetMapping("/forgot")
    public String showForgotPasswordPage() {
        return "forgot.html";
    }

    @PostMapping("/forgotPassword")
    public String performForgotPassword(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        if(!(forgotPasswordService.updateResetPasswordToken(token, email))) {
            redirectAttributes.addFlashAttribute("failureMessage","No user found. Please enter correct email.");
            return "redirect:/forgot";
        }
        String siteURL = request.getRequestURL().toString();
        String resetPasswordLink =  siteURL.replace(request.getServletPath(), "")+ "/reset_password?token=" + token;
        forgotPasswordService.sendResetPasswordLinkEmail(email, resetPasswordLink);
        redirectAttributes.addFlashAttribute("successMessage","We have sent a reset password link to your email. Please check.");
        return "redirect:/forgot";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        Person person = forgotPasswordService.getByResetPasswordToken(token);
        model.addAttribute("token",token);

        if(person == null) {
            throw new RuntimeException("Invalid Password token, please try again!");
        }
        return "resetpassword.html";
    }

    @PostMapping("/reset_password")
    public String performResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Person person = forgotPasswordService.getByResetPasswordToken(token);
        if(person == null) {
            throw new RuntimeException("Invalid Password token, please try again!");
        }

        forgotPasswordService.updatePassword(person, password);

        return "redirect:/login?reset=true";
    }
}
