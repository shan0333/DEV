package com.spaceage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spaceage.model.Customer;
import com.spaceage.model.ResponseDTO;

@Service
public interface CustomerService {

	int createCustomer(Customer cus);
	
	List<Customer> getCustomer();

	ResponseDTO getCustomerList();
}
