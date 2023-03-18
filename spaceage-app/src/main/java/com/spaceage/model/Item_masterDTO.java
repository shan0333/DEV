package com.spaceage.model;

import java.util.List;

public class Item_masterDTO {
	private String item_id;
	private String lot_ref_no;
	private String customer_code;
	private String name;
	private String customer_location;
	private String project_code;

	private String project_name;
	private String designation;
	private String createdDate;
	private String country;
	private Integer totalElements;
	private String report;
	private String lot_size;
	private List<?> containers;

	public String getLot_ref_no() {
		return lot_ref_no;
	}

	public void setLot_ref_no(String lot_ref_no) {
		this.lot_ref_no = lot_ref_no;
	}

	public String getCustomer_code() {
		return customer_code;
	}

	public void setCustomer_code(String customer_code) {
		this.customer_code = customer_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomer_location() {
		return customer_location;
	}

	public void setCustomer_location(String customer_location) {
		this.customer_location = customer_location;
	}

	public String getProject_code() {
		return project_code;
	}

	public void setProject_code(String project_code) {
		this.project_code = project_code;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getLot_size() {
		return lot_size;
	}

	public void setLot_size(String lot_size) {
		this.lot_size = lot_size;
	}

	public List<?> getContainers() {
		return containers;
	}

	public void setContainers(List<?> containers) {
		this.containers = containers;
	}

	

}
