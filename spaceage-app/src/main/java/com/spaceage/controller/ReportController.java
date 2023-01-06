package com.spaceage.controller;

import java.io.ByteArrayInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spaceage.model.Bom;
import com.spaceage.model.Case;
import com.spaceage.report.GeneratePdfReport;
import com.spaceage.service.ItemMasterService;

@RestController
@CrossOrigin
public class ReportController {

	@Autowired
	  ItemMasterService fileService;

	  @GetMapping("/download/{id:.+}")
	  public ResponseEntity<Resource> getFile(@PathVariable("id") String id) {
	    String filename = "tutorials.xlsx";
	    InputStreamResource file = new InputStreamResource(fileService.load(id));

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
	  }
	  
//	  @PostMapping("/caseList")
//	  public ResponseEntity<InputStreamResource> citiesReport(@RequestBody String value) throws IOException {
//
//		    Bom bom =fileService.getBomJson(value);
//		  
//	        ByteArrayInputStream bis = GeneratePdfReport.caseReport(fileService.getBomById(bom.getLot_ref_no(), bom.getPackingGroup()), fileService.findById(bom.getLot_ref_no()), bom);
//
//	        var headers = new HttpHeaders();
//	        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");
//
//	        return ResponseEntity
//	                .ok()
//	                .headers(headers)
//	                .contentType(MediaType.APPLICATION_PDF)
//	                .body(new InputStreamResource(bis));
//	    }
	  
	  @PostMapping("/case")
		public ResponseEntity<InputStreamResource> createCase(@RequestBody String value) {
			
				try {
					Bom bom = fileService.getBomJson(value);
					fileService.createCase(bom);
					
					
					ByteArrayInputStream bis = GeneratePdfReport.caseReport(
							fileService.getBomById(bom.getLot_ref_no(), bom.getPackingGroup()),
							fileService.findById(bom.getLot_ref_no()), bom);
					var headers = new HttpHeaders();
					headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");
					return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
							.body(new InputStreamResource(bis));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
		
	  }
}
