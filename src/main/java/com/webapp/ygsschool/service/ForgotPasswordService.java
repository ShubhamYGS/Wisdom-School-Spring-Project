package com.webapp.ygsschool.service;

import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public boolean updateResetPasswordToken(String token, String email) {
        Person person = personRepository.findByEmail(email);
        if(person != null) {
            person.setResetPasswordToken(token);
            personRepository.save(person);
            return true;
        }
        return false;
    }

    public void sendResetPasswordLinkEmail(String email, String link) {
        String subject = "Wisdom School | Link to reset your Password";
        String body = "Hello,\n\n A request has been received to change the password for your Wisdom School account.\n\nClick the below link to change your password:\n"+ link + "\n\nIgnore this email if you remember your password, or you have not made this request.\n\nRegards,\nWisdom School";
        emailSenderService.sendHireEmail(email,subject,body);
    }

    public Person getByResetPasswordToken(String token) {
        return personRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(Person person, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        person.setPwd(encodedPassword);
        person.setResetPasswordToken(null);
        personRepository.save(person);
    }
}
