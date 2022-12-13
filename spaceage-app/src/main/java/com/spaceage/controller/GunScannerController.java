package com.spaceage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.spaceage.model.Customer;
import com.spaceage.service.ItemMasterService;

@CrossOrigin
@RestController
public class GunScannerController {

	@Autowired
	ItemMasterService itemService;

	@GetMapping("/gunscanner/{id}/{value}")
	public ResponseEntity<List<Customer>> gunScanner(@PathVariable("id") String id, @PathVariable("value") String value) {
		 try {
				itemService.gunScanner(id, value);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}

}
