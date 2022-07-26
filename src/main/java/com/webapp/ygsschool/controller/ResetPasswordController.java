package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.service.ResetPasswordService;
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
    private ResetPasswordService resetPasswordService;

    // Showing forgot passowrd Page
    @GetMapping("/forgot")
    public String showForgotPasswordPage() {
        return "forgot.html";
    }

    // When user clicks on forgot password after entering his email
    @PostMapping("/forgotPassword")
    public String performForgotPassword(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

        // Get the User email and create a random token of 30 characters
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        // If there is no user with provided mail
        if (!(resetPasswordService.updateResetPasswordToken(token, email))) {
            redirectAttributes.addFlashAttribute("failureMessage", "No user found. Please enter correct email.");
            return "redirect:/forgot";
        }

        //Getting siteURL using HttpServletRequest and appending the reset_password_token string
        String siteURL = request.getRequestURL().toString();
        String resetPasswordLink = siteURL.replace(request.getServletPath(), "") + "/reset_password?token=" + token;

        // Sending out the reset password link to email
        resetPasswordService.sendResetPasswordLinkEmail(email, resetPasswordLink);
        redirectAttributes.addFlashAttribute("successMessage", "We have sent a reset password link to your email. Please check.");

        return "redirect:/forgot";
    }

    // Reset User Password Page
    @GetMapping("/reset_password")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {

        // Get User from the Reset Password Link token
        Person person = resetPasswordService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        // If no user found throw an exception
        if (person == null) {
            throw new RuntimeException("Invalid Password token, please try again!");
        }
        // else return a page
        return "resetpassword.html";
    }

    // Resetting the user password
    @PostMapping("/reset_password")
    public String performResetPassword(HttpServletRequest request, Model model) {
        // Taking the token from link and password from input field
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        // If there is no person with provided token
        Person person = resetPasswordService.getByResetPasswordToken(token);
        if (person == null) {
            throw new RuntimeException("Invalid Password token, please try again!");
        }

        // Updating the token to null and setting up the new password
        resetPasswordService.updatePassword(person, password);

        return "redirect:/login?reset=true";
    }
}
