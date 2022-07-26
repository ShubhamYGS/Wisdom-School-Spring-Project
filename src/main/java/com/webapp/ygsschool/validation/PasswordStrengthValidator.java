package com.webapp.ygsschool.validation;

import com.webapp.ygsschool.annotation.PasswordValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator, String> {

    // Storing all the passwords in weakPasswords List.
    List<String> weakPasswords;

    @Override
    public void initialize(PasswordValidator constraintAnnotation) {
        weakPasswords = Arrays.asList("123456789", "qwerty", "password", "12345", "11111", "123123", "qwerty123", "1q2w3e", "1234567890", "DEFAULT", "00000", "abc123", "54321", "123321", "iloveyou", "66666");
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // If any of the password matches from password list return false
        return (!weakPasswords.contains(s));
    }
}
