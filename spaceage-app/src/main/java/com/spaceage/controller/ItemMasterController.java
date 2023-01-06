package com.spaceage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spaceage.model.Country;
import com.spaceage.model.Item;
import com.spaceage.model.PackagingType;
import com.spaceage.model.RequestDTO;
import com.spaceage.model.ResponseDTO;
import com.spaceage.report.CSVHelper;
import com.spaceage.service.ItemMasterService;

@CrossOrigin
@RestController
public class ItemMasterController {

	@Autowired
	ItemMasterService itemService;

	@PostMapping("/upload")
	public ResponseEntity<ResponseDTO> uploadFile(@RequestPart("file") MultipartFile file,
			@RequestPart("item") String item) {
		Item itemJson = null;
		if (CSVHelper.hasCSVFormat(file)) {
			try {
				itemJson = itemService.getJson(item);

				itemService.save(itemJson);

				itemService.save(file, itemJson.getLot_ref_no());

				

				return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Uploaded the file successfully: " + file.getOriginalFilename()));
			} catch (UncategorizedSQLException e) {
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Uploaded the file successfully: " + file.getOriginalFilename()));
			} catch (DataAccessException e) {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
						.body(new ResponseDTO("Lot Ref No " + itemJson.getLot_ref_no() + " already exist !"));
			}

			catch (Exception e) {
				itemService.deleteItemMaster(itemJson.getLot_ref_no());
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO("Could not upload the file: " + file.getOriginalFilename() + ", Please verify your file before uploading!"));
				
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Please upload a csv file!"));
	}

	@PostMapping("/item-master")
	public ResponseEntity<ResponseDTO> getAllItems(@RequestBody RequestDTO request) {
		try {
			ResponseDTO item = itemService.getAllItems(request);

			if (item == null ) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(item, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/packagingtype")
	public ResponseEntity<List<PackagingType>> getPackingType() {
		try {
			List<PackagingType> pro = itemService.getPackagingType();

			if (pro.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(pro, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/country")
	public ResponseEntity<List<Country>> getCountry() {
		try {
			List<Country> pro = itemService.getCountry();

			if (pro.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(pro, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/lotrefno")
	public ResponseEntity<List<String>> getLotRefNo() {
		try {
			List<String> lotRefNo = itemService.getLotRefNo();

			if (lotRefNo == null ) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(lotRefNo, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
