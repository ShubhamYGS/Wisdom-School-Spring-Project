package com.webapp.ygsschool.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    /*
     * Global Exception Handler with the use of @ControllerAdvice annotation.
     * To handle all kind of exception use @ExceptionHandler with Exception.class as parameter which contains all the exceptions.
     * If there is any exception then return static error html page with exception message.
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView showExceptionPage(Exception exception) {
        String errorMsg = null;
        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error");
        if (exception.getMessage() != null) {
            errorMsg = exception.getMessage();
        } else if (exception.getCause() != null) {
            errorMsg = exception.getCause().toString();
        } else if (exception != null) {
            errorMsg = exception.toString();
        }
        errorPage.addObject("errormsg", errorMsg);
        return errorPage;
    }
}
