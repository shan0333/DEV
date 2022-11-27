package com.spaceage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spaceage.model.Project;

@Service
public interface ProjectService {

	List<Project> getProject();

	void createProject(Project cus);
}
