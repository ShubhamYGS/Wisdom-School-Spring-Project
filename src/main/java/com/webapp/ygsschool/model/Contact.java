package com.webapp.ygsschool.model;

/*@Data Annotation is provided by lombok library which generates getter, setter,
equals(), hashcode(), toString() methods & Constructor at compile time.
This makes our code short and clean.
 */

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "contact_msg")
public class Contact extends BaseFormEntity {

    /*
   * @NotNull: Checks if a given field is not null but allows empty values & zero elements inside collections.
     @NotEmpty: Checks if a given field is not null and its size/length is greater than zero.
     @NotBlank: Checks if a given field is not null and trimmed length is greater than zero.
   * */

    /*
     * @Table annotation is used when your Pojo class name and db table name is not same
     * @Column annotation is used when your field name and table column name is not same (ignore '_')
     * */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int contactId;

    private String status;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Subject must not be blank")
    @Size(min = 5, message = "Subject must be at least 5 characters long")
    private String subject;

    @NotBlank(message = "Message must not be blank")
    @Size(min = 10, message = "Message must be at least 10 characters long")
    private String message;
}