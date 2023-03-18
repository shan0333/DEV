package com.spaceage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spaceage.model.Customer;
import com.spaceage.model.ResponseDTO;
import com.spaceage.service.CustomerService;

@Repository
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	Environment env;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Customer> getCustomer() {
		try {
			List<Customer> customers = jdbcTemplate.query(env.getProperty("SELECT_CUSTOMER"),
					new BeanPropertyRowMapper<Customer>(Customer.class));

			return customers;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public int createCustomer(Customer cus) {
		try {
			if(cus.getCustomer_id()!= 0 && !cus.isDeleteFlag()) {
				
				    return jdbcTemplate.update(env.getProperty("UPDATE_CUSTOMER"), getObject(cus, true));
				  
			}else if(cus.isDeleteFlag()) {
				return jdbcTemplate.update(env.getProperty("DELETE_CUSTOMER"),  new Object[] {cus.getCustomer_id()});
			}else {
				return jdbcTemplate.update(env.getProperty("INSERT_CUSTOMER"),  getObject(cus, false));
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ResponseDTO getCustomerList() {
		ResponseDTO response = new ResponseDTO();
		List<Customer> customers = new ArrayList<>(); 
		Integer count =0;
		try {
			count = jdbcTemplate.queryForObject(env.getProperty("COUNT_CUSTOMER"), Integer.class);
			 customers = jdbcTemplate.query(env.getProperty("SELECT_CUSTOMER"),
					new BeanPropertyRowMapper<Customer>(Customer.class));

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setData(customers);
		response.setTotalElements(count);
		if (!response.getData().isEmpty()) {
			response.setNoData(false);
		}
		return response;
		
	}
	
	public Object[] getObject(Customer cus, boolean flag) {
		if(!flag) {
		return new Object[] {
				cus.getCustomer_code(),
				cus.getEmail(),
				cus.getName(),
				cus.getMobile(),
				cus.getAddress1(),
				cus.getAddress2(),
				cus.getCountry(),
				cus.getState(),
				cus.getCity(),
				cus.getPostal_code(),
				cus.getContact_name(),
				cus.getDesignation(),
				cus.getPhone(),
				cus.getWeb_address(),
				cus.getCustomer_location(),
				cus.getCustomer_logo(),
				cus.getCreatedBy()
				
				
     };
		}else {
			return new Object[] {
					cus.getCustomer_code(),
					cus.getEmail(),
					cus.getName(),
					cus.getMobile(),
					cus.getAddress1(),
					cus.getAddress2(),
					cus.getCountry(),
					cus.getState(),
					cus.getCity(),
					cus.getPostal_code(),
					cus.getContact_name(),
					cus.getDesignation(),
					cus.getPhone(),
					cus.getWeb_address(),
					cus.getCustomer_location(),
					cus.getCustomer_logo(),
					cus.getCreatedBy(),
					cus.getCustomer_id()
		};
		}
		
	}
	
}
