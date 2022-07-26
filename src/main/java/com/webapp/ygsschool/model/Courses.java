package com.webapp.ygsschool.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Courses extends BaseFormEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int courseId;

    @NotBlank(message = "Course Name must not be blank")
    @Size(min = 3, message = "Course Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Course Description must not be blank")
    @Size(min = 10, message = "Course Description must be at least 10 characters long")
    private String descrip;

    @NotBlank(message = "Course tags must not be blank")
    private String tags;

    @NotBlank(message = "Number of lessons inside course must not be blank")
    private String lessonNo;

    @NotBlank(message = "Course Rating must not be blank")
    private String rating;

    @NotBlank(message = "Course Fees must not be blank")
    private String fees;

    private String courseImage;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Person> persons = new HashSet<>();
}
