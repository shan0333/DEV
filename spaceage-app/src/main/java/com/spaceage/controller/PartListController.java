package com.spaceage.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.spaceage.model.PartRequestDTO;
import com.spaceage.model.ResponseDTO;
import com.spaceage.model.Search;
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
	
	
	@PostMapping("/bombyid")
	public ResponseEntity<ResponseDTO> getBomById(@RequestBody PartRequestDTO request) {
		try {
			ResponseDTO item = itemService.getBomById(request);

			if (item == null ) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(item, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/search")
	public ResponseEntity<ResponseDTO> search(@RequestBody Search req) {
		try {
			ResponseDTO item = null;
			PartRequestDTO request = new PartRequestDTO();
			request.setKey(req.getLot_ref_no());
			String partNo = req.getPart_no()!=null? req.getPart_no():"";
			String bomId = req.getBomid()!=null? req.getBomid():"";
			if(partNo.isEmpty() && bomId.isEmpty()) {
				request.setLimit("10");
				request.setOffset("0");
				item = itemService.getBomById(request);
			}else {
			
			
			List<String> value = new ArrayList<>();
			
			value.add(partNo);
			value.add(bomId);
			request.setSearchValue(value);
			
			 item = itemService.getBomById(request);
			}
			if (item == null ) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			 }
			return new ResponseEntity<>(item, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/bomByIdForPicklable/{id}")
	public ResponseEntity<List<Bom>> getBomByIdForPicklable(@PathVariable("id") String lotRefNo) {
		try {
			List<Bom> item = itemService.getBomByIdForLable(lotRefNo, null);

			if (item.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(item, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/bomByIdForBarcode/{id}")
	public ResponseEntity<List<Bom>> getBomByIdForBarcode(@PathVariable("id") String bomId) {
		try {
			List<Bom> response = itemService.getBomByIdForLable(null,bomId);
		
			if (response.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/createPart")
	public ResponseEntity<ResponseDTO> createBom(@RequestBody Bom bom) {
		try {
			itemService.createBom(bom);

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Project Created Successfully!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
