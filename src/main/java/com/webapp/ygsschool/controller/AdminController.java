package com.webapp.ygsschool.controller;

import com.webapp.ygsschool.model.Courses;
import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.model.WisdomClass;
import com.webapp.ygsschool.repository.CoursesRepository;
import com.webapp.ygsschool.repository.PersonRepository;
import com.webapp.ygsschool.repository.WisdomClassRepository;
import com.webapp.ygsschool.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
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

    // Configurations inside application.properties file
    @Value("${wisdomschool.course.pagesize}")
    private int defaultPageSize;

    /***********************************
     *         CLASS SECTION           *
     ***********************************/

    // To display all the classes
    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model) {
        List<WisdomClass> wisdomClassList = wisdomClassRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");

        /* Here, we are checking if the wisdomClass attribute is already passed to method (In case of redirect).
         For Checking Validations errors */
        if (!model.containsAttribute("wisdomClass")) {
            modelAndView.addObject("wisdomClass", new WisdomClass());
        }
        modelAndView.addObject("wisdomClasses", wisdomClassList);
        return modelAndView;
    }

    //To Add New Class
    @PostMapping("/addNewClass")
    public String saveClassDetails(Model model, @Valid @ModelAttribute("wisdomClass") WisdomClass wisdomClass, BindingResult errors,
                                   RedirectAttributes redirectAttributes) {
        // If we have any validation errors we are redirecting to displayclasses method with all the errors
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.wisdomClass", errors);
            redirectAttributes.addFlashAttribute("wisdomClass", wisdomClass);
            return "redirect:/admin/displayClasses";
        }
        wisdomClassRepository.save(wisdomClass);
        redirectAttributes.addFlashAttribute("addMessage", wisdomClass.getName() + " has been added successfully.");
        return "redirect:/admin/displayClasses";
    }

    // To delete existing class
    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam int id, RedirectAttributes redirectAttributes) {
        Optional<WisdomClass> wisdomClass = wisdomClassRepository.findById(id);

        // After finding the class, delete all the students associated to that class by making their class null
        for (Person person : wisdomClass.get().getPersons()) {
            person.setWisdomClass(null);
            personRepository.save(person);
        }
        wisdomClassRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        redirectAttributes.addFlashAttribute("addMessage", wisdomClass.get().getName() + " has been deleted successfully");
        return modelAndView;
    }

    /***********************************
     *       STUDENT SECTION           *
     ***********************************/

    // To display all the students under particular class
    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession httpSession,
                                        @RequestParam(value = "error", required = false) String error) {
        // Here, we are taking the errorMessage attribute which can have any kind of error
        Object errorMessage = httpSession.getAttribute("errorMessage");

        Optional<WisdomClass> wisdomClass = wisdomClassRepository.findById(classId);

        ModelAndView modelAndView = new ModelAndView("students.html");
        modelAndView.addObject("wisdomClass", wisdomClass.get());
        modelAndView.addObject("person", new Person());

        // Passing wisdomClass in session so that it can be accessed later for adding/deleting the student in that class
        httpSession.setAttribute("wisdomClass", wisdomClass.get());

        // If there is error that means our requestparam error has having the value true and object must not be null
        if (error != null) {
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    // To add New Student to Class
    @PostMapping("/addStudent")
    public ModelAndView addNewStudent(Model model, @ModelAttribute("person") Person person, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        //Taking the attribute passed from displayStudents() method
        WisdomClass wisdomClass = (WisdomClass) httpSession.getAttribute("wisdomClass");

        Person personEntity = personRepository.findByEmail(person.getEmail());

        // If there is no student with correspond email return invalid email
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + wisdomClass.getClassId()
                    + "&error=true");
            httpSession.setAttribute("errorMessage", "Invalid Email entered!");
            return modelAndView;
        } else if (personEntity.getWisdomClass() != null) {
            //If User is already present
            if (personEntity.getWisdomClass().getClassId() == wisdomClass.getClassId()) {
                httpSession.setAttribute("errorMessage", "Student is already present in class");
            } else {
                httpSession.setAttribute("errorMessage", personEntity.getEmail() + " is already part of " + personEntity.getWisdomClass().getName());
            }
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + wisdomClass.getClassId()
                    + "&error=true");
            return modelAndView;
        }

        personEntity.setWisdomClass(wisdomClass);
        personRepository.save(personEntity);
        wisdomClass.getPersons().add(personEntity);
        wisdomClassRepository.save(wisdomClass);
        redirectAttributes.addFlashAttribute("successMessage",personEntity.getName()+" has been successfully added to "+wisdomClass.getName());
        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + wisdomClass.getClassId());
        return modelAndView;
    }

    // To Delete Student from Class
    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        // Taking session attribute passed from displayStudent() method
        WisdomClass wisdomClass = (WisdomClass) httpSession.getAttribute("wisdomClass");

        // Taking out the person by using personId and then set it's class as null and remove it from Set of Person
        Optional<Person> person = personRepository.findById(personId);
        person.get().setWisdomClass(null);
        wisdomClass.getPersons().remove(person.get());
        WisdomClass wisdomClassSaved = wisdomClassRepository.save(wisdomClass);

        httpSession.setAttribute("wisdomClass", wisdomClassSaved);
        redirectAttributes.addFlashAttribute("successMessage",person.get().getName()+" has been successfully removed from "+wisdomClass.getName());
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId=" + wisdomClass.getClassId());
        return modelAndView;
    }

    /***********************************
     *       COURSES SECTION           *
     ***********************************/

    // Display courses (Here, it accepts page number as path variable)
    @RequestMapping("/displayCourses/page/{pageNum}")
    public ModelAndView displayCourses(Model model, @PathVariable(name = "pageNum") int pageNum) {
        ModelAndView modelAndView = new ModelAndView("addcourses.html");

        // This line is to check for the validation related errors. In case of errors course attribute is passed from other methods
        if (!model.containsAttribute("course")) {
            modelAndView.addObject("course", new Courses());
        }

        // Defining page size and accepting all the courses in Page as return type (Pages will be 0,1,2)
        Pageable pageable = PageRequest.of(pageNum - 1, defaultPageSize);
        Page<Courses> coursesPage = coursesRepository.findAll(pageable);

        // Take content out of coursePage and fill it in list of courses
        List<Courses> coursesList = coursesPage.getContent();

        // Passing the currentPage and totalPage attribute to front end
        modelAndView.addObject("currentPage", pageNum);
        modelAndView.addObject("totalPages", coursesPage.getTotalPages());

        modelAndView.addObject("courses", coursesList);
        modelAndView.addObject("person", new Person());

        Optional<Courses> courseList = coursesRepository.findById(1);
        Set<Person> personList = courseList.get().getPersons();
        model.addAttribute("personList", personList);
        return modelAndView;
    }

    // To add new course
    @PostMapping(value = "/addNewCourse")
    public String addNewCourse(Model model, @Valid @ModelAttribute("course") Courses course, BindingResult errors,
                               @RequestParam("file") MultipartFile multipartFile,
                               RedirectAttributes redirectAttributes) {
        //Check if image is empty (if empty add it to lombok validation errors list) [Using bindingresult instead of errors for the same]
        if (multipartFile.isEmpty()) {
            ObjectError error = new ObjectError("course", "Course Image file must not be empty");
            errors.addError(error);
        }

        // If page has any validation errors return the same to displayCourses() method
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.course", errors);
            redirectAttributes.addFlashAttribute("course", course);
            return "redirect:/admin/displayCourses/page/1";
        }

        // Get the uploaded file name from MultiPartFile
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        //Upload File
        if (!(fileUploadService.uploadFile(fileName, multipartFile))) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error while uploading course image. Try again!");
            return "redirect:/admin/displayCourses/page/1";
        }
        course.setCourseImage(fileName);
        coursesRepository.save(course);
        redirectAttributes.addFlashAttribute("addMessage", course.getName() + " course has been added successfully.");
        return "redirect:/admin/displayCourses/page/1";
    }

    // For checking the enrolled students under particular course
    @GetMapping(value = "/enrolledStudents/{courseId}")
    public void showEnrolledStudents(Model model, @PathVariable(name = "courseId") int courseId) {
        System.out.println("Controller Called");
        Optional<Courses> courseList = coursesRepository.findById(courseId);
        Set<Person> personList = courseList.get().getPersons();
        model.addAttribute("personList", personList);
    }
}
