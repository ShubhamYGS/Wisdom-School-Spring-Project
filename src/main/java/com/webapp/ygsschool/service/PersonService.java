package com.webapp.ygsschool.service;

import com.webapp.ygsschool.constants.FormConstants;
import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.model.Roles;
import com.webapp.ygsschool.repository.PersonRepository;
import com.webapp.ygsschool.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;
    public boolean savePersonDetails(Person person) {
        boolean isSaved = false;
        Roles roles = rolesRepository.getByRoleName(FormConstants.ROLE_STUDENT);
        person.setRoles(roles);
        person = personRepository.save(person);
        if(person != null && person.getPersonId()>0)
            isSaved = true;
        return isSaved;
    }
}
