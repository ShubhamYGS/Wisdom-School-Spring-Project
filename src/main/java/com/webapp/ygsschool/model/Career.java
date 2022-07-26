package com.webapp.ygsschool.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Career extends BaseFormEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int jobId;

    @NotBlank(message = "First Name must not be blank")
    @Size(min = 2, message = "First Name must be at least 2 characters long")
    private String firstName;

    @NotBlank(message = "Last Name must not be blank")
    @Size(min = 2, message = "Last Name must be at least 2 characters long")
    private String lastName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Address must not be blank")
    @Size(min = 8, message = "Address must be at least 8 characters long")
    private String address;

    @NotBlank(message = "City must not be blank")
    private String city;

    @NotBlank(message = "State must not be blank")
    private String state;

    @NotBlank(message = "Zipcode must not be blank")
    @Pattern(regexp = "(^$|[0-9]{6})", message = "Zip Code must be 6 digits")
    private String zipCode;

    private String yourself;

    @NotBlank(message = "College must not be blank")
    @Size(min = 4, message = "College Name must be at least 4 characters long")
    private String college;

    @NotBlank(message = "Degree must not be blank")
    private String degree;

    @NotBlank(message = "Percentage/CGPA must not be blank")
    private String cgpa;

    private String jobTitle;

    private String company;

    private boolean working;

    private String resume;

    private String status;

    @Transient
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm a")
    private LocalDateTime datetime = LocalDateTime.now().plusDays(7).withHour(13).withMinute(30);
}
