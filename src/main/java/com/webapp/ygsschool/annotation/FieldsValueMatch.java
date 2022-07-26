package com.webapp.ygsschool.annotation;

import com.webapp.ygsschool.validation.FieldsValueMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsValueMatch {
    String message() default "Field value doesn't match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // As we want compare two string, we are taking here two string field and fieldMatch

    String field();

    String fieldMatch();

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        FieldsValueMatch[] value();
    }
}
