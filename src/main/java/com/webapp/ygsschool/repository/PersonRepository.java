package com.webapp.ygsschool.repository;

import com.webapp.ygsschool.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Integer> {
}
