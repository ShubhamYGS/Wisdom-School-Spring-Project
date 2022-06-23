package com.webapp.ygsschool.model;

/*@Data Annotation is provided by lombok library which generates getter, setter,
equals(), hashcode(), toString() methods & Constructor at compile time.
This makes our code short and clean.
 */

import lombok.Data;

@Data
public class Contact {
    private String name;
    private String email;
    private String subject;
    private String message;
}
