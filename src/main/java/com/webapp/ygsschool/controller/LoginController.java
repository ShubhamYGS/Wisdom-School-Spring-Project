package com.webapp.ygsschool.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    // To display Sign in page
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
                                   @RequestParam(value = "logout", required = false) String logout,
                                   @RequestParam(value = "register", required = false) String register,
                                   @RequestParam(value = "reset", required = false) String reset,
                                   Model model) {

        // If user has already logged in and trying to redirect to /login then redirect him to dashboard page
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken))
            return "redirect:/dashboard";

        // If there are any error/message then display them on front end page
        String errorMessage = null;
        String logoutMessage = null;

        if (error != null) {
            errorMessage = "Email or Password is incorrect";
        }
        if (logout != null) {
            logoutMessage = "You have been successfully logged out!";
        }
        if (register != null) {
            logoutMessage = "You registered successfully. Login with your registered credentials :)";
        }
        if (reset != null) {
            logoutMessage = "You have successfully reset your password. Login with your new credentials :)";
        }

        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("logoutMessage", logoutMessage);

        return "login.html";
    }

    // Log out functionality
    @GetMapping(value = "logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // If user found, logout him with the help of SecurityContextLogoutHandler and return to login page
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout=true";
    }
}
