package com.webapp.ygsschool.repository;

import com.webapp.ygsschool.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends JpaRepository<Career, Integer> {

    List<Career> findByStatus(String status);
}
