package com.webapp.ygsschool.repository;

import com.webapp.ygsschool.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact,Integer> {

    //When you want to create your own custom query (Derived Query Type)
    List<Contact> findByStatus(String status);
}
