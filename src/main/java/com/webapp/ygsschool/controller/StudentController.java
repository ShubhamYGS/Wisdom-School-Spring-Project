package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Courses;
import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.repository.CoursesRepository;
import com.webapp.ygsschool.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private PersonRepository personRepository;

    // Configurations inside application.properties file
    @Value("${wisdomschool.course.pagesize}")
    private int deafultPageSize;

    /***********************************
     *       COURSES SECTION           *
     ***********************************/

    // Displaying all courses Page
    @GetMapping(value = "/courses/page/{pageNum}")
    public ModelAndView showCoursesPage(Model model, @PathVariable(name = "pageNum") int pageNum, Authentication authentication) {

        // Setting up the maximum courses one page can have (1 page 3 courses)
        Pageable pageable = PageRequest.of(pageNum - 1, deafultPageSize);

        // Get all the pages
        Page<Courses> coursesPage = coursesRepository.findAll(pageable);

        // Get content out of coursesPage
        List<Courses> coursesList = coursesPage.getContent();

        // Finding out the current logged in user and passing it in case of enrolling user in courses
        Person personEntity = personRepository.findByEmail(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("courses.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", coursesPage.getTotalPages());
        modelAndView.addObject("person", personEntity);
        modelAndView.addObject("course", new Courses());
        modelAndView.addObject("courses", coursesList);
        return modelAndView;
    }

    // Displaying My courses Page
    @GetMapping("/displayCourses/page/{pageNum}")
    public ModelAndView modelAndView(Model model, @PathVariable(name = "pageNum") int pageNum,
                                     Authentication authentication) {
        Pageable pageable = PageRequest.of(pageNum - 1, deafultPageSize);

        // Display only courses enrolled by student
        Person personEntity = personRepository.findByEmail(authentication.getName());

        Page<Courses> coursesPage = coursesRepository.findPersonCourses(personEntity.getPersonId(), pageable);
        List<Courses> courses = coursesPage.getContent();
        ModelAndView modelAndView = new ModelAndView("studentcourses.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", coursesPage.getTotalPages());
        modelAndView.addObject("courses", courses);
        return modelAndView;
    }

    // Enroll student to course
    @RequestMapping(value = "/enrollStudent", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView enrollStudent(Model model, @RequestParam("courseId") int courseId, Authentication authentication, RedirectAttributes redirAttrs) {
        ModelAndView modelAndView = new ModelAndView();

        // Find out the course and person for enroll attachment
        Optional<Courses> courses = coursesRepository.findById(courseId);
        Person personEntity = personRepository.findByEmail(authentication.getName());

        // If person is already enrolled to course
        if (courses.get().getPersons().contains(personEntity)) {
            redirAttrs.addFlashAttribute("errorMessage", "You have already enrolled for course: " + courses.get().getName());
            modelAndView.setViewName("redirect:/student/courses/page/1");
            return modelAndView;
        }
        // Enrolling student in course (by adding it to list)
        personEntity.getCourses().add(courses.get());
        courses.get().getPersons().add(personEntity);
        personRepository.save(personEntity);
        redirAttrs.addFlashAttribute("enrollMessage", "You have successfully enrolled for course: " + courses.get().getName());
        modelAndView.setViewName("redirect:/student/courses/page/1");
        return modelAndView;
    }

    // UnEnroll student from Course
    @RequestMapping(value = "/unEnrollStudent", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView unEnrollStudent(Model model, @RequestParam("courseId") int courseId, Authentication authentication, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        // Find out the course and person for unenroll attachment
        Person personEntity = personRepository.findByEmail(authentication.getName());
        Optional<Courses> courses = coursesRepository.findById(courseId);

        // Removing the course from person and person from course
        personEntity.getCourses().remove(courses.get());
        courses.get().getPersons().remove(personEntity);

        personRepository.save(personEntity);
        redirectAttributes.addFlashAttribute("unEnrollMessage", "You have successfully UnEnrolled from Course: " + courses.get().getName());
        modelAndView.setViewName("redirect:/student/displayCourses/page/1");
        return modelAndView;
    }
}
