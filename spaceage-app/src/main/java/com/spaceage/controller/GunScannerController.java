package com.spaceage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.spaceage.model.ResponseDTO;
import com.spaceage.service.ItemMasterService;

@CrossOrigin
@RestController
public class GunScannerController {

	@Autowired
	ItemMasterService itemService;

	@GetMapping("/gunscanner/{id}/{value}")
	public ResponseEntity<ResponseDTO> gunScanner(@PathVariable("id") String id, @PathVariable("value") String value) {
		String response = null;
		try {
			response = itemService.gunScanner(id, value);

			if (response.equals("1")) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseDTO(value + " Label Scanned Successfully!!"));
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ResponseDTO("Please try again!!"));
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO("Please try again!!"));
		}
	}

}
