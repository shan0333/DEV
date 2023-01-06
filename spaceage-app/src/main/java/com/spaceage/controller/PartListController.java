package com.spaceage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spaceage.model.Bom;
import com.spaceage.model.Case;
import com.spaceage.model.ResponseDTO;
import com.spaceage.service.ItemMasterService;

@CrossOrigin
@RestController
public class PartListController {

	@Autowired
	ItemMasterService itemService;

	@GetMapping("/bombyid/{id}")
	public ResponseEntity<List<Bom>> getBomById(@PathVariable("id") String id) {
		try {
			List<Bom> item = itemService.getBomById(id, null);

			if (item.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(item, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/createBom")
	public ResponseEntity<ResponseDTO> createBom(@RequestBody Bom bom) {
		try {
			itemService.createBom(bom);

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Project Created Successfully!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
