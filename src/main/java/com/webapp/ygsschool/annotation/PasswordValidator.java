package com.webapp.ygsschool.annotation;

import com.webapp.ygsschool.validation.PasswordStrengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/*
 * These custom annotations is made with help of Original Annotation present under Jakarta validation external library (constraint);
 * @Constraint annotation is having a class on which this interface is depend (Main business logic)
 * */

@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidator {

    //This message can be Override
    String message() default "Please choose a strong password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
