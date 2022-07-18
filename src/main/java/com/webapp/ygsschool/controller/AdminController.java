package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Courses;
import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.model.WisdomClass;
import com.webapp.ygsschool.repository.CoursesRepository;
import com.webapp.ygsschool.repository.PersonRepository;
import com.webapp.ygsschool.repository.WisdomClassRepository;
import com.webapp.ygsschool.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private WisdomClassRepository wisdomClassRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model) {
        List<WisdomClass> wisdomClassList = wisdomClassRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("wisdomClass",new WisdomClass());
        modelAndView.addObject("wisdomClasses",wisdomClassList);
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView saveClassDetails(Model model, @ModelAttribute("wisdomClass") WisdomClass wisdomClass) {
        wisdomClassRepository.save(wisdomClass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam int id) {
        Optional<WisdomClass> wisdomClass = wisdomClassRepository.findById(id);
        for(Person person: wisdomClass.get().getPersons()) {
            person.setWisdomClass(null);
            personRepository.save(person);
        }
        wisdomClassRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession httpSession,
                                        @RequestParam(value = "error", required = false) String error) {
        Object errorMessage = httpSession.getAttribute("errorMessage");
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<WisdomClass> wisdomClass = wisdomClassRepository.findById(classId);
        modelAndView.addObject("wisdomClass",wisdomClass.get());
        modelAndView.addObject("person",new Person());
        httpSession.setAttribute("wisdomClass",wisdomClass.get());
        if(error != null) {
            modelAndView.addObject("errorMessage",errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addNewStudent(Model model, @ModelAttribute("person") Person person, HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView();
        WisdomClass wisdomClass = (WisdomClass) httpSession.getAttribute("wisdomClass");
        Person personEntity = personRepository.findByEmail(person.getEmail());

        if(personEntity==null || !(personEntity.getPersonId()>0)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+wisdomClass.getClassId()
                    +"&error=true");
            httpSession.setAttribute("errorMessage","Invalid Email entered!");
            return modelAndView;
        } else if(personEntity.getWisdomClass()!=null){
            //If User is already present
            if(personEntity.getWisdomClass().getClassId()==wisdomClass.getClassId()) {
                httpSession.setAttribute("errorMessage","Student is already present in class");
            } else {
                httpSession.setAttribute("errorMessage",personEntity.getEmail()+" is already part of "+personEntity.getWisdomClass().getName());
            }
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+wisdomClass.getClassId()
                    +"&error=true");
            return modelAndView;
        }

        personEntity.setWisdomClass(wisdomClass);
        personRepository.save(personEntity);
        wisdomClass.getPersons().add(personEntity);
        wisdomClassRepository.save(wisdomClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+wisdomClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession httpSession) {
        WisdomClass wisdomClass = (WisdomClass) httpSession.getAttribute("wisdomClass");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setWisdomClass(null);
        wisdomClass.getPersons().remove(person.get());
        WisdomClass wisdomClassSaved = wisdomClassRepository.save(wisdomClass);
        httpSession.setAttribute("wisdomClass",wisdomClassSaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId="+wisdomClass.getClassId());
        return modelAndView;
    }

    @RequestMapping("/displayCourses/page/{pageNum}")
    public ModelAndView displayCourses(Model model, @PathVariable(name = "pageNum") int pageNum) {
        ModelAndView modelAndView = new ModelAndView("addcourses.html");
        if(!model.containsAttribute("course")){
            modelAndView.addObject("course", new Courses());
        }
        int pageSize=3;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Courses> coursesPage = coursesRepository.findAll(pageable);
        List<Courses> coursesList = coursesPage.getContent();
        modelAndView.addObject("currentPage", pageNum);
        modelAndView.addObject("totalPages", coursesPage.getTotalPages());
        modelAndView.addObject("courses",coursesList);
        modelAndView.addObject("person",new Person());
        return modelAndView;
    }

    @PostMapping(value = "/addNewCourse")
    public String addNewCourse(Model model,@Valid @ModelAttribute("course") Courses course, BindingResult errors,
                                     @RequestParam("file")MultipartFile multipartFile,
                                     RedirectAttributes redirectAttributes) {
        //Check if image is empty
        if(multipartFile.isEmpty()) {
            ObjectError error = new ObjectError("course","Course Image file must not be empty");
            errors.addError(error);
        }
        if(errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.course",errors);
            redirectAttributes.addFlashAttribute("course", course);
            return "redirect:/admin/displayCourses/page/1";
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        //Upload File
        if (!(fileUploadService.uploadFile(fileName, multipartFile))) {
            redirectAttributes.addFlashAttribute("errorMessage","Error while uploading course image. Try again!");
            return "redirect:/admin/displayCourses/page/1";
        }
        course.setCourseImage(fileName);
        coursesRepository.save(course);
        redirectAttributes.addFlashAttribute("addMessage",course.getName()+" course has been added successfully.");
        return "redirect:/admin/displayCourses/page/1";
    }

    @RequestMapping(value = "/enrolledStudents/{courseId}", method = {RequestMethod.GET})
    public Set<Person> showEnrolledStudents(Model model, @PathVariable(name = "courseId") int courseId) {
        Optional<Courses> courseList = coursesRepository.findById(courseId);
        Set<Person> personList = courseList.get().getPersons();
        model.addAttribute("courseList",personList);
        return personList;
    }
}
