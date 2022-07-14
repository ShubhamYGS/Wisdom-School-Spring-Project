package com.webapp.ygsschool.repository;

import com.webapp.ygsschool.model.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository extends JpaRepository<Courses,Integer> {

    //For Pagination inside courses page (Modified the original method to return pages)
    Page<Courses> findAll(Pageable pageable);
}
