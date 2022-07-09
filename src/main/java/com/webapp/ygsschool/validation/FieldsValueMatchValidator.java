package com.webapp.ygsschool.validation;

import com.webapp.ygsschool.annotation.FieldsValueMatch;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object fieldValue = new BeanWrapperImpl(o).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(o).getPropertyValue(fieldMatch);

        //All hashpassowrd starts with $2a as prefix (for ignore the same)
        //First validation checks normal string and at the end mvc also checks after saving the hashed value into database
        if(fieldValue.toString().startsWith("$2a"))
            return true;

        return fieldValue.equals(fieldMatchValue);
    }
}
