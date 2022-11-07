package com.spaceage.model;

import java.util.Date;

public class Project {

	private int project_id;

	private String project_code;

	private String project_name;

	private String project_location;

	private String city;

	private String postal_code;

	private String state;

	private String country;

	private String destination_location;

	private String buyername;

	private String designation;

	private String telephone;

	private String mobile;

	private String email;

	private String web_address;

	private int CreatedBy;

	private Date CreatedDate;

	private String ModifiedBy;

	private Date ModifiedDate;

	private int Status;

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
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

	public String getProject_location() {
		return project_location;
	}

	public void setProject_location(String project_location) {
		this.project_location = project_location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDestination_location() {
		return destination_location;
	}

	public void setDestination_location(String destination_location) {
		this.destination_location = destination_location;
	}

	public String getBuyername() {
		return buyername;
	}

	public void setBuyername(String buyername) {
		this.buyername = buyername;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeb_address() {
		return web_address;
	}

	public void setWeb_address(String web_address) {
		this.web_address = web_address;
	}

	public int getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(int createdBy) {
		CreatedBy = createdBy;
	}

	public Date getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}

	public String getModifiedBy() {
		return ModifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return ModifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		ModifiedDate = modifiedDate;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "Project [project_id=" + project_id + ", project_code=" + project_code + ", project_name=" + project_name
				+ ", project_location=" + project_location + ", city=" + city + ", postal_code=" + postal_code
				+ ", state=" + state + ", country=" + country + ", destination_location=" + destination_location
				+ ", buyername=" + buyername + ", designation=" + designation + ", telephone=" + telephone + ", mobile="
				+ mobile + ", email=" + email + ", web_address=" + web_address + ", CreatedBy=" + CreatedBy
				+ ", CreatedDate=" + CreatedDate + ", ModifiedBy=" + ModifiedBy + ", ModifiedDate=" + ModifiedDate
				+ ", Status=" + Status + "]";
	}

}
