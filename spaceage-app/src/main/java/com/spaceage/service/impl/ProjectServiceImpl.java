package com.spaceage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spaceage.model.Project;
import com.spaceage.model.ResponseDTO;
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
			List<Project> project = jdbcTemplate.query(env.getProperty("SELECT_PROJECT"),
					new BeanPropertyRowMapper<Project>(Project.class));

			return project;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public int createProject(Project pro) {
		
		try {
			if(pro.getProject_id()!= 0 && !pro.isDeleteFlag()) {
				
				    return jdbcTemplate.update(env.getProperty("UPDATE_PROJECT"), getObject(pro, true));
				  
			}else if(pro.isDeleteFlag()) {
				return jdbcTemplate.update(env.getProperty("DELETE_PROJECT"),  new Object[] {pro.getProject_id()});
			}else {
				return jdbcTemplate.update(env.getProperty("INSERT_PROJECT"),  getObject(pro, false));
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	

	public Object[] getObject(Project pro, boolean flag) {
		if(!flag) {
		return new Object[] {
				pro.getProject_code(),
				pro.getProject_name(),
				pro.getProject_location(),
				pro.getCity(),
				pro.getPostal_code(),
				pro.getState(),
				pro.getCountry(),
				pro.getDestination_location(),
				pro.getBuyername(),
				pro.getDesignation(),
				pro.getTelephone(),
				pro.getMobile(),
				pro.getEmail(),
				pro.getWeb_address(),
				pro.getCreatedBy(),
				
				
     };
		}else {
			return new Object[] {
					pro.getProject_code(),
					pro.getProject_name(),
					pro.getProject_location(),
					pro.getCity(),
					pro.getPostal_code(),
					pro.getState(),
					pro.getCountry(),
					pro.getDestination_location(),
					pro.getBuyername(),
					pro.getDesignation(),
					pro.getTelephone(),
					pro.getMobile(),
					pro.getEmail(),
					pro.getWeb_address(),
					pro.getCreatedBy(),
					
					pro.getProject_id()
		};
		}
		
	}
	
	@Override
	public ResponseDTO getProjectList() {
		ResponseDTO response = new ResponseDTO();
		List<Project> project = new ArrayList<>(); 
		Integer count =0;
		try {
			count = jdbcTemplate.queryForObject(env.getProperty("COUNT_PROJECT"), Integer.class);
			project = jdbcTemplate.query(env.getProperty("SELECT_PROJECT"),
					new BeanPropertyRowMapper<Project>(Project.class));

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setData(project);
		response.setTotalElements(count);
		if (!response.getData().isEmpty()) {
			response.setNoData(false);
		}
		return response;
		
	}
	
}
