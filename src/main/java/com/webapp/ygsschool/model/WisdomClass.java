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
@Table(name = "class")
public class WisdomClass extends BaseFormEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int classId;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    //mappedBy denotes that in target class we will be creating a object named as wisdomClass
    @OneToMany(mappedBy = "wisdomClass", fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST, targetEntity = Person.class)
    private Set<Person> persons = new HashSet<>();
}
