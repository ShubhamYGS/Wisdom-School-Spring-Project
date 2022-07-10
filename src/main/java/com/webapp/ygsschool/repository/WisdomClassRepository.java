package com.webapp.ygsschool.repository;

import com.webapp.ygsschool.model.WisdomClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WisdomClassRepository extends JpaRepository<WisdomClass,Integer> {

}
