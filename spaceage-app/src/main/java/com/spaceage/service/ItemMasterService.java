package com.spaceage.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spaceage.model.Bom;
import com.spaceage.model.Country;
import com.spaceage.model.Customer;
import com.spaceage.model.Item;
import com.spaceage.model.Item_masterDTO;
import com.spaceage.model.PackagingType;
import com.spaceage.model.Project;

@Service
public interface ItemMasterService {
	
//	int save(Tutorial book);
//
//	int update(Tutorial book);
//
//	Tutorial findById(Long id);
//
//	int deleteById(Long id);
//
//	List<Tutorial> findAll();
//
//	List<Tutorial> findByPublished(boolean published);
//
//	List<Tutorial> findByTitleContaining(String title);
//
//	int deleteAll();

	Item getJson(String item);

	long save(Item itemJson);

	void save(MultipartFile file, long itemId);

	List<Item_masterDTO> getAllItems();

	List<Customer> getCustomer();

	List<Project> getProject();

	InputStream load();

	List<PackagingType> getPackagingType();

	List<Country> getCountry();

	List<Bom> getBomById(String id);
}
