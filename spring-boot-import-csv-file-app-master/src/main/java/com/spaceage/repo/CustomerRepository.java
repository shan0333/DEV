package com.spaceage.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spaceage.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
