package com.spaceage.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spaceage.model.Bom;
import com.spaceage.model.Case;
import com.spaceage.model.Country;
import com.spaceage.model.Item;
import com.spaceage.model.PackagingType;
import com.spaceage.model.RequestDTO;
import com.spaceage.model.ResponseDTO;
import com.spaceage.model.SummaryDTO;

@Service
public interface ItemMasterService {

	Item getJson(String item);
	
	Bom getBomJson(String bom);

	long save(Item itemJson);

	void save(MultipartFile file, String itemId);

	ResponseDTO getAllItems(RequestDTO request);

	ByteArrayInputStream load(String id);

	List<PackagingType> getPackagingType();

	List<Country> getCountry();

	List<Bom> getBomById(String id, String string);

	SummaryDTO findById(String id);
	
	void createBom(Bom bom);

	String gunScanner(String id, String value);

	List<String> getLotRefNo();

	void deleteItemMaster(String lot_ref_no);

	void createCase(Bom c);
	
	Case getCase(Bom bom);

}
