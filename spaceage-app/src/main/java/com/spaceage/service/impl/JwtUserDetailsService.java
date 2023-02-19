package com.spaceage.service.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spaceage.model.SummaryDTO;
import com.spaceage.model.UserDTO;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	Logger log = LoggerFactory.getLogger(JwtUserDetailsService.class);
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Environment env; 

	/*
	 * public UserDetails loadUserByUsername(String username, String password)
	 * throws UsernameNotFoundException {
	 * 
	 * 
	 * UserDTO user = null; try { user =
	 * jdbcTemplate.queryForObject(env.getProperty("SELECT_USER_PWD"), new Object[]
	 * { username, password }, new BeanPropertyRowMapper<UserDTO>(UserDTO.class)); }
	 * catch (EmptyResultDataAccessException e) {
	 * log.warn("No record found for "+username, e);
	 * 
	 * } if (user == null) { throw new
	 * UsernameNotFoundException("User not found with username: " + username); }
	 * return new
	 * org.springframework.security.core.userdetails.User(user.getUsername(),
	 * user.getPassword(), new ArrayList<>()); }
	 */
	
	public UserDTO save(UserDTO user) {
		
		// define query arguments
        Object[] params = new Object[] { user.getUsername(), bcryptEncoder.encode(user.getPassword()) };
         
        // define SQL types of the arguments
        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR};
 
        int row = 0;
		try {
			row = jdbcTemplate.update(env.getProperty("INSERT_USER"), params, types);
		} catch (DataAccessException e) {
			log.warn("Error while creating "+user.getUsername(), e);
			return null;
		}
        System.out.println(row + " row inserted.");
		
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDTO user = null;
		try {
			user = jdbcTemplate.queryForObject(env.getProperty("SELECT_USER"), new Object[] { username },
					new BeanPropertyRowMapper<UserDTO>(UserDTO.class));
		} catch (EmptyResultDataAccessException e) {
			log.warn("No record found for "+username, e);
		      
		}
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
}