package com.webapp.ygsschool.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
public class Address extends BaseFormEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int addressId;

    @NotBlank(message = "Address must not be blank")
    @Size(min = 5, message = "Address1 must be at least 5 characters long")
    private String address1;

    @NotBlank(message = "Mobile number must not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be of 10 digits")
    private String mobileNumber;

    @NotBlank(message = "City must not be blank")
    private String city;

    @NotBlank(message = "State must not be blank")
    private String state;

    @NotBlank(message = "Zipcode must not be blank")
    @Pattern(regexp = "(^$|[0-9]{6})", message = "Zip Code must be 6 digits")
    private String zipCode;
}
