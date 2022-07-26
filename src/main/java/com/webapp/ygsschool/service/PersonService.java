package com.webapp.ygsschool.service;

import com.webapp.ygsschool.constants.FormConstants;
import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.model.Roles;
import com.webapp.ygsschool.repository.PersonRepository;
import com.webapp.ygsschool.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // While registering the user. Assigning student role and password save it into database
    public boolean savePersonDetails(Person person) {
        boolean isSaved = false;
        Roles roles = rolesRepository.getByRoleName(FormConstants.ROLE_STUDENT);
        person.setRoles(roles);
        //Hashing the password before saving it into database
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        //Saving person information in database
        person = personRepository.save(person);
        if (person != null && person.getPersonId() > 0)
            isSaved = true;
        return isSaved;
    }
}
