package com.spaceage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spaceage.model.Project;
import com.spaceage.model.ResponseDTO;

@Service
public interface ProjectService {

	List<Project> getProject();

	int createProject(Project cus);

	ResponseDTO getProjectList();
}
