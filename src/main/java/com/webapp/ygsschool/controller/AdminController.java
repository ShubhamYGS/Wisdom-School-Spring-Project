package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.model.WisdomClass;
import com.webapp.ygsschool.repository.PersonRepository;
import com.webapp.ygsschool.repository.WisdomClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private WisdomClassRepository wisdomClassRepository;

    @Autowired
    private PersonRepository personRepository;

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
        String errorMessage = null;
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
}
