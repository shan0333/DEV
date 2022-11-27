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
import com.spaceage.model.Project;
import com.spaceage.service.CustomerService;
import com.spaceage.service.ProjectService;

@Repository
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	Environment env;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Project> getProject() {
		try {
			List<Project> project = jdbcTemplate.query(env.getProperty("select_project"),
					new BeanPropertyRowMapper<Project>(Project.class));

			return project;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void createProject(Project cus) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(env.getProperty("insert_project"));
				ps.setString(1, cus.getProject_code());
				ps.setString(2, cus.getProject_name());
				ps.setString(3, cus.getProject_location());
				ps.setString(4, cus.getCity());
				ps.setString(5, cus.getPostal_code());
				ps.setString(6, cus.getState());
				ps.setString(7, cus.getCountry());
				ps.setString(8, cus.getDestination_location());
				ps.setString(9, cus.getBuyername());
				ps.setString(10, cus.getDesignation());
				ps.setString(11, cus.getTelephone());
				ps.setString(12, cus.getMobile());
				ps.setString(13, cus.getEmail());
				ps.setString(14, cus.getWeb_address());
				ps.setInt(15, 1);
				return ps;
			}
		});
	}
	
}
