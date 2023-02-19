package com.spaceage.service;

import org.springframework.stereotype.Service;

import com.spaceage.model.UserDTO;

@Service
public interface UserRepository {
	UserDTO findByUsername(String username);
}