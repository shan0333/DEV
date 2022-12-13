package com.spaceage.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spaceage.model.Bom;
import com.spaceage.model.Country;
import com.spaceage.model.Item;
import com.spaceage.model.PackagingType;
import com.spaceage.model.RequestDTO;
import com.spaceage.model.ResponseDTO;

@Service
public interface ItemMasterService {

	Item getJson(String item);

	long save(Item itemJson);

	void save(MultipartFile file, String itemId);

	ResponseDTO getAllItems(RequestDTO request);

	InputStream load();

	List<PackagingType> getPackagingType();

	List<Country> getCountry();

	List<Bom> getBomById(String id);

	void createBom(Bom bom);

	void gunScanner(String id, String value);

	List<String> getLotRefNo();

}
