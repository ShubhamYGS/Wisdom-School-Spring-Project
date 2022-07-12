package com.webapp.ygsschool.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Courses extends BaseFormEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int courseId;

    private String name;

    private String descrip;

    private String tags;

    private String lessonNo;

    private String rating;

    private String fees;

    private String courseImage;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Person> persons = new HashSet<>();
}
