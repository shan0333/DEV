package com.spaceage.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.spaceage.model.Bom;
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
		ResponseDTO responseDto = new ResponseDTO();
		List<Bom> list = new ArrayList<>();
		try {
			int scanId = 0;
			if(value.equalsIgnoreCase("part")) {
				String[] arrOfStr = id.split("P@");
				  id = arrOfStr[0];
				  scanId = Integer.parseInt(arrOfStr[1]);
			}
			
			response = itemService.gunScanner(id, value);
			
			Bom bom = itemService.checkPickLableScanData(id);
			if (response.isEmpty() || response.equals("0")) {
				if(value.equalsIgnoreCase("pick")){
					if(bom !=null) {
						
						list.add(bom);
						responseDto.setData(list);
						if(bom.getPick_label_scan() > 1) {
							bom.setEnablePartLabel(true);
							responseDto.setMessage("Pick Lable Already Scanned!");
						}
					}else {
						return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}else if(value.equalsIgnoreCase("part")){
					Bom part = itemService.checkPartLableScanData(id);
					if(part!= null) { 
						if(scanId!=0 && scanId <= part.getPart_label_scan()) {
							responseDto.setMessage("Part Lable Already Scanned!");
						}else {
							return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
						}
						
					}else {
						return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
					}
					
				}
			}else if(value.equalsIgnoreCase("pick")){
				
				if(bom !=null) {
					
					list.add(bom);
					responseDto.setData(list);
					if(bom.getPick_label_scan() > 1) {
						bom.setEnablePartLabel(true);
					}
					responseDto.setMessage("Pick Lable Scanned Successfully");
				}
			return new ResponseEntity<>(responseDto, HttpStatus.OK);
			}else if(value.equalsIgnoreCase("part")) {
				responseDto.setMessage("Part Lable Scanned Successfully");
			}
		
		} catch (Exception e) {
			responseDto.setMessage("Please Scan valid Pick/Part Number");
			return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

}
