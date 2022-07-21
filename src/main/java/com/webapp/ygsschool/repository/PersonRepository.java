package com.webapp.ygsschool.repository;

import com.webapp.ygsschool.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {

    Person findByEmail(String email);

    Person findByResetPasswordToken(String token);
}
