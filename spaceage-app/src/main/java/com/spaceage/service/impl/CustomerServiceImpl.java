package com.spaceage.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.spaceage.model.Customer;
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
			List<Customer> customers = jdbcTemplate.query(env.getProperty("select_customer"),
					new BeanPropertyRowMapper<Customer>(Customer.class));

			return customers;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void createCustomer(Customer cus) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(env.getProperty("insert_customer"));
				ps.setString(1, cus.getCustomer_code());
				ps.setString(2, cus.getEmail());
				ps.setString(3, cus.getName());
				ps.setString(4, cus.getMobile());
				ps.setString(5, cus.getAddress1());
				ps.setString(6, cus.getAddress2());
				ps.setString(7, cus.getCountry());
				ps.setString(8, cus.getState());
				ps.setString(9, cus.getCity());
				
				ps.setString(10, cus.getPostal_code());
				ps.setString(11, cus.getContact_name());
				ps.setString(12, cus.getDesignation());
				

				ps.setString(13, cus.getPhone());
				ps.setString(14, cus.getWeb_address());
				ps.setString(15, cus.getCustomer_location());

				ps.setString(16, cus.getCustomer_logo());
				
				ps.setInt(17, 1);
				return ps;
			}
		});
	}
	
}
