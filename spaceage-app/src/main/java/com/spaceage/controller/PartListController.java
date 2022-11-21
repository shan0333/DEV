package com.spaceage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.spaceage.model.Bom;
import com.spaceage.service.ItemMasterService;

@CrossOrigin("http://localhost:4200")
@Controller
@RequestMapping("/api/csv")
public class PartListController {

	@Autowired
	ItemMasterService itemService;

	@GetMapping("/bombyid/{id}")
	public ResponseEntity<List<Bom>> getBomById(@PathVariable("id") String id) {
		try {
			List<Bom> item = itemService.getBomById(id);

			if (item.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(item, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
