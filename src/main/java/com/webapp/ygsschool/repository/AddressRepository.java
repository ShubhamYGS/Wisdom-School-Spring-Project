package com.webapp.ygsschool.repository;

import com.webapp.ygsschool.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
