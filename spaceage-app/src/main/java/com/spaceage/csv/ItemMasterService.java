package com.spaceage.csv;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spaceage.model.Bom;
import com.spaceage.model.Customer;
import com.spaceage.model.Item;
import com.spaceage.model.Project;
import com.spaceage.repo.BomRepository;
import com.spaceage.repo.CustomerRepository;
import com.spaceage.repo.ItemRepository;
import com.spaceage.repo.ProjectRepository;

@Service
public class ItemMasterService {
	@Autowired
	BomRepository bomRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	CustomerRepository cusRepository;
	
	@Autowired
	ProjectRepository proRepository;

	
	public void save(MultipartFile file, long itemId) {
		try {
			List<Bom> fileData = CSVHelper.csvToBom(file.getInputStream(), itemId);

			bomRepository.saveAll(fileData);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ByteArrayInputStream load() {
		List<Bom> tutorials = bomRepository.findAll();

		ByteArrayInputStream in = CSVHelper.tutorialsToCSV(tutorials);
		return in;
	}

	public List<Item> getAllItems() {
		return itemRepository.findAll();
	}

	public Item getJson(String item) {
		Item it = new Item();
		ObjectMapper objMap = new ObjectMapper();
		try {
			it = objMap.readValue(item, Item.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return it;
	}

	public int save(Item itemJson) {
		Item item =itemRepository.save(itemJson);
		return item.getItem_id();
	}

	public List<Customer> getCustomer() {
		return cusRepository.findAll();
	}

	public List<Project> getProject() {
		return proRepository.findAll();
	}
}
