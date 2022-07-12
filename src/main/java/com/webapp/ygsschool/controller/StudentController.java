package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Courses;
import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.repository.CoursesRepository;
import com.webapp.ygsschool.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(value = "/courses")
    public ModelAndView showCoursesPage(Model model){
        List<Courses> coursesList = coursesRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("courses.html");
        modelAndView.addObject("course",new Courses());
        modelAndView.addObject("courses",coursesList);
        return modelAndView;
    }

    @RequestMapping(value = "/enrollStudent", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView modelAndView(Model model, @RequestParam("courseId") int courseId, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Courses> courses = coursesRepository.findById(courseId);
        Person personEntity = personRepository.findByEmail(authentication.getName());
        personEntity.getCourses().add(courses.get());
        courses.get().getPersons().add(personEntity);
        personRepository.save(personEntity);
        modelAndView.setViewName("redirect:/student/courses");
        return modelAndView;
    }

//    @GetMapping("/displayCourses")
//    public ModelAndView modelAndView(Authentication authentication) {
//        ModelAndView modelAndView = new ModelAndView();
//        Person personEntity = personRepository.findByEmail(authentication.getName());
//        Courses courses = new Courses();
//        courses.getPersons(personEntity).;
//    }
}
