package com.spaceage.model;

import java.util.Date;

public class Item_masterDTO {

	private String lot_ref_no;
	private String customer_code;
	private String name;
	private String customer_location;
	private String project_code;
	
	private String project_name;
	private String designation;
	private Date createdDate;
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
