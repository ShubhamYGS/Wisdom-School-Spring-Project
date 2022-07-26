package com.webapp.ygsschool.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class Profile extends BaseFormEntity {
    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Mobile number must not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be of 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Address must not be blank")
    @Size(min = 5, message = "Address must be at least 5 characters long")
    private String address1;

    @NotBlank(message = "City must not be blank")
    private String city;

    @NotBlank(message = "State must not be blank")
    private String state;

    @NotBlank(message = "Zipcode must not be blank")
    @Pattern(regexp = "(^$|[0-9]{6})", message = "Zip Code must be 6 digits")
    private String zipCode;
}
