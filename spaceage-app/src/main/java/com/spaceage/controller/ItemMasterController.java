package com.spaceage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import com.spaceage.csv.CSVHelper;
import com.spaceage.model.Country;
import com.spaceage.model.Customer;
import com.spaceage.model.Item;
import com.spaceage.model.PackagingType;
import com.spaceage.model.Project;
import com.spaceage.model.RequestDTO;
import com.spaceage.model.ResponseDTO;
import com.spaceage.service.ItemMasterService;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/api/csv")
public class ItemMasterController {

	@Autowired
	ItemMasterService itemService;

	@PostMapping("/upload")
	public ResponseEntity<ResponseDTO> uploadFile(@RequestPart("file") MultipartFile file,
			@RequestPart("item") String item) {
		String message = "";
		Item itemJson = null;
		if (CSVHelper.hasCSVFormat(file)) {
			try {
				itemJson = itemService.getJson(item);

				long lotRefNo = itemService.save(itemJson);

				itemService.save(file, lotRefNo);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();

//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/csv/download/")
//                .path(file.getOriginalFilename())
//                .toUriString();

				return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(message));
			} catch (DataAccessException e) {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
						.body(new ResponseDTO("Lot Ref No " + itemJson.getLot_ref_no() + " already exist !"));
			}

			catch (Exception e) {
				e.printStackTrace();
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO(message));
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(message));
	}

	@GetMapping("/item")
	public ResponseEntity<ResponseDTO> getAllItems(@RequestBody RequestDTO request) {
		try {
			ResponseDTO item = itemService.getAllItems(request);

			if (item.getData().isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(item, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer")
	public ResponseEntity<List<Customer>> getCustomer() {
		try {
			List<Customer> cus = itemService.getCustomer();

			if (cus.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(cus, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/project")
	public ResponseEntity<List<Project>> getProject() {
		try {
			List<Project> pro = itemService.getProject();

			if (pro.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(pro, HttpStatus.OK);
		} catch (Exception e) {
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

	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
		InputStreamResource file = new InputStreamResource(itemService.load());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
}
