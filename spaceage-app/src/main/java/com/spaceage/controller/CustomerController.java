package com.spaceage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spaceage.model.Customer;
import com.spaceage.model.ResponseDTO;
import com.spaceage.service.CustomerService;

@Controller
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	CustomerService cusService;

	@GetMapping("/customer")
	public ResponseEntity<List<Customer>> getCustomer() {
		try {
			List<Customer> cus = cusService.getCustomer();

			if (cus.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(cus, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/createCustomer")
	public ResponseEntity<ResponseDTO> createCustomer(@RequestBody Customer cus) {
		try {
			cusService.createCustomer(cus);

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Customer Created Successfully!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
