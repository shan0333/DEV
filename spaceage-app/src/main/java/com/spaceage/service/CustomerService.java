package com.spaceage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spaceage.model.Customer;

@Service
public interface CustomerService {

	void createCustomer(Customer cus);
	
	List<Customer> getCustomer();
}
