package com.webapp.ygsschool.validation;

import com.webapp.ygsschool.annotation.FieldsValueMatch;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {

    /*
     * initialize method is used to initialize the fields if you have any
     * isValid method is used to check if the validations are correct or not
     * These two methods are comes under ConstraintValidator
     */


    // field and fieldMatch contains the password and confirm password field
    private String field;
    private String fieldMatch;

    // Assigning the values to strings by taking them from FieldsValueMatch class
    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    // This method is to check the validations. If both password are correct it will return true
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object fieldValue = new BeanWrapperImpl(o).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(o).getPropertyValue(fieldMatch);

        //All hashpassowrd starts with $2a as prefix (for ignore the same)
        //First validation checks normal string and at the end mvc jpa also checks after saving the hashed value into database
        //We have disabled it in application.properties
//        if(fieldValue.toString().startsWith("$2a"))
//            return true;

        return fieldValue.equals(fieldMatchValue);
    }
}
