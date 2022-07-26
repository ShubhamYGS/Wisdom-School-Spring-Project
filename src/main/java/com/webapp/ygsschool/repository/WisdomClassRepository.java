package com.webapp.ygsschool.repository;

import com.webapp.ygsschool.model.WisdomClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WisdomClassRepository extends JpaRepository<WisdomClass, Integer> {

    /*
    Spring Data JPA allows us to apply static sorting by adding the OrderBy keyword
    to the method name along with the property name and sort direction (Asc or Desc).
    By default without mentioning asc/desc it sorts in ascending order.
    * */
    List<WisdomClass> findByOrderByNameDesc();
}
