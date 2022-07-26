package com.webapp.ygsschool.repository;

import com.webapp.ygsschool.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {

    //When you want to create your own custom query (Derived Query Type)
    //List<Contact> findByStatus(String status);

    Page<Contact> findByStatus(String status, Pageable pageable);

    /*Transactional & Modifying annotations are used when we are updating or modifying the data.
     * So these two annotations will be used on all type of queries except select
     * JPA Auditing won't work on Native Queries (We are not using below query)*/
    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.status = ?1, c.updatedAt = ?2, c.updatedBy = ?3 WHERE c.contactId = ?4")
    int updateStatusById(String status, LocalDateTime now, String updatedBy, int id);
}
