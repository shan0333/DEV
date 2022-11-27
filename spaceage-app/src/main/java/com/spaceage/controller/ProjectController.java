package com.spaceage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spaceage.model.Project;
import com.spaceage.model.ResponseDTO;
import com.spaceage.service.ProjectService;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/api")
public class ProjectController {

	@Autowired
	ProjectService proService;

	@GetMapping("/project")
	public ResponseEntity<List<Project>> getProject() {
		try {
			List<Project> pro = proService.getProject();

			if (pro.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(pro, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/createProject")
	public ResponseEntity<ResponseDTO> createCustomer(@RequestBody Project pro) {
		try {
			proService.createProject(pro);

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Project Created Successfully!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
