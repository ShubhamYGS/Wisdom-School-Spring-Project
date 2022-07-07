package com.webapp.ygsschool.controller;

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

    @RequestMapping(value = "/login",method = {RequestMethod.GET, RequestMethod.POST})
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
                                   @RequestParam(value = "logout", required = false) String logout,
                                   @RequestParam(value = "register", required = false) String register,
                                   Model model) {
        String errorMessage = null;
        String logoutMessage = null;
        if(error != null) {
            errorMessage = "Email or Password is incorrect";
        }
        if(logout != null) {
            logoutMessage = "You have been successfully logged out!";
        }
        if(register != null) {
            logoutMessage = "You registered successfully. Login with your registered credentials :)";
        }
        model.addAttribute("errorMessage",errorMessage);
        model.addAttribute("logoutMessage",logoutMessage);
        return "login.html";
    }

    @GetMapping(value = "logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout=true";
    }
}
