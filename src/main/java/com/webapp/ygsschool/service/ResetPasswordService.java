package com.webapp.ygsschool.service;

import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Check if user is present in database. If present, set the reset password token.
    public boolean updateResetPasswordToken(String token, String email) {
        Person person = personRepository.findByEmail(email);
        // If person is found set reset password token and save it into database
        if (person != null) {
            person.setResetPasswordToken(token);
            personRepository.save(person);
            return true;
        }
        return false;
    }

    // Sending out the reset password email to user
    public void sendResetPasswordLinkEmail(String email, String link) {
        String subject = "Wisdom School | Link to reset your Password";
        String body = "Hello,\n\n A request has been received to change the password for your Wisdom School account.\n\nClick the below link to change your password:\n" + link + "\n\nIgnore this email if you remember your password, or you have not made this request.\n\nRegards,\nWisdom School";
        emailSenderService.sendEmail(email, subject, body);
    }

    // For Verifying the person identity finding out the person by his token
    public Person getByResetPasswordToken(String token) {
        return personRepository.findByResetPasswordToken(token);
    }

    // Update user password to a new password
    public void updatePassword(Person person, String newPassword) {
        // Hashing the password before saving it into database
        person.setPwd(passwordEncoder.encode(newPassword));
        person.setResetPasswordToken(null);
        personRepository.save(person);
    }
}
