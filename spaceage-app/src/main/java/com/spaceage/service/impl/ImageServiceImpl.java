package com.spaceage.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

import com.spaceage.model.ImageModel;
import com.spaceage.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Environment env;

	@Override
	public void save(ImageModel img) {
		
		//SELECT_IMAGE_NAME
		
        Object[] inputs = new Object[] {img.getName()};
        
        Integer data = 0;
		try {
			data = jdbcTemplate.queryForObject(env.getProperty("SELECT_IMAGE_NAME"), inputs, Integer.class);
		} catch (DataAccessException e) {
			//do nothing
		}
		 
		if(data==0) {
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(env.getProperty("INSERT_IMAGE"));
				ps.setString(1, img.getName());
				ps.setString(2, img.getType());
				ps.setString(3, img.getPicByte());
				
				return ps;
			}
		});
		}else {
			jdbcTemplate.update(
					env.getProperty("UPDATE_IMAGE"), 
	                img.getType(), img.getPicByte(), data);
		}
	}

}
