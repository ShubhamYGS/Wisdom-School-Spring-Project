package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Courses;
import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.repository.CoursesRepository;
import com.webapp.ygsschool.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping(value = "/courses/page/{pageNum}")
    public ModelAndView showCoursesPage(Model model, @PathVariable(name = "pageNum") int pageNum,
                                        Authentication authentication){
        int pageSize=3;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Courses> coursesPage = coursesRepository.findAll(pageable);
        List<Courses> coursesList = coursesPage.getContent();
        Person personEntity = personRepository.findByEmail(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("courses.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", coursesPage.getTotalPages());
        modelAndView.addObject("person",personEntity);
        modelAndView.addObject("course",new Courses());
        modelAndView.addObject("courses",coursesList);
        return modelAndView;
    }

    @RequestMapping(value = "/enrollStudent", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView enrollStudent(Model model, @RequestParam("courseId") int courseId, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Courses> courses = coursesRepository.findById(courseId);
        Person personEntity = personRepository.findByEmail(authentication.getName());
        personEntity.getCourses().add(courses.get());
        courses.get().getPersons().add(personEntity);
        personRepository.save(personEntity);
        modelAndView.setViewName("redirect:/student/courses/page/1");
        return modelAndView;
    }

    @RequestMapping(value = "/unEnrollStudent", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView unEnrollStudent(Model model, @RequestParam("courseId") int courseId, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        Person personEntity = personRepository.findByEmail(authentication.getName());
        Optional<Courses> courses = coursesRepository.findById(courseId);
        personEntity.getCourses().remove(courses.get());
        courses.get().getPersons().remove(personEntity);
        personRepository.save(personEntity);
        modelAndView.setViewName("redirect:/student/displayCourses/page/1");
        return modelAndView;
    }

    @GetMapping("/displayCourses")
    public ModelAndView modelAndView(Model model, Authentication authentication) {
        Person personEntity = personRepository.findByEmail(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("studentcourses.html");
        modelAndView.addObject("person",personEntity);
        return modelAndView;
    }
}
