package com.webapp.ygsschool.repository;

import com.webapp.ygsschool.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByEmail(String email);

    // For reset password finding the user by provided token which in given inside link
    Person findByResetPasswordToken(String token);
}
