package com.webapp.ygsschool.model;

import com.webapp.ygsschool.annotation.FieldsValueMatch;
import com.webapp.ygsschool.annotation.PasswordValidator;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldsValueMatch(
        field = "pwd",
        fieldMatch = "confirmpwd",
        message = "Passwords do not match."
)
public class Person {

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, message = "Password must be at least 4 characters long")
    @PasswordValidator
    private String pwd;

    @NotBlank(message = "Password must not be blank")
    private String confirmpwd;
}
