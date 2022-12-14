package com.spaceage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "project_master")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "project_id")
	private int project_id;
	
	@Column(name = "project_code")
	private String project_code; 
	
	@Column(name = "project_name")
	private String project_name; 
	
	@Column(name = "project_location")
	private String project_location;
	
	@Column(name = "city")
	private String city; 
	
	@Column(name = "postal_code")
	private String postal_code; 
	
	@Column(name = "state")
	private String state; 
	
	@Column(name = "country")
	private String country; 
	
	@Column(name = "destination_location")
	private String destination_location;
	
	@Column(name = "buyername")
	private String buyername; 
	
	@Column(name = "designation")
	private String designation;
	
	@Column(name = "telephone")
	private String telephone; 
	
	@Column(name = "mobile")
	private String mobile; 
	
	@Column(name = "email")
	private String email; 
	
	@Column(name = "web_address")
	private String web_address;
	
	@Column(name = "CreatedBy")
	private int CreatedBy;
	
	@Column(name = "CreatedDate")
	private Date CreatedDate;
	
	@Column(name = "ModifiedBy")
	private String ModifiedBy;
	
	@Column(name = "ModifiedDate")
	private Date ModifiedDate;
	
	@Column(name = "Status")
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
