package com.spaceage.model;

import java.util.Date;

public class Customer {

	private int customer_id;

	private String customer_code;

	private String email;

	private String name;

	private String mobile;

	private String address1;

	private String address2;

	private String country;

	private String state;

	private String city;

	private String postal_code;

	private String contact_name;

	private String designation;

	private String phone;

	private String web_address;

	private String customer_location;

	private String customer_logo;

	private int CreatedBy;

	private Date CreatedDate;

	private int ModifiedBy;

	private Date ModifiedDate;

	private int Status;

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_code() {
		return customer_code;
	}

	public void setCustomer_code(String customer_code) {
		this.customer_code = customer_code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWeb_address() {
		return web_address;
	}

	public void setWeb_address(String web_address) {
		this.web_address = web_address;
	}

	public String getCustomer_location() {
		return customer_location;
	}

	public void setCustomer_location(String customer_location) {
		this.customer_location = customer_location;
	}

	public String getCustomer_logo() {
		return customer_logo;
	}

	public void setCustomer_logo(String customer_logo) {
		this.customer_logo = customer_logo;
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

	public int getModifiedBy() {
		return ModifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
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
		return "Customer [customer_id=" + customer_id + ", customer_code=" + customer_code + ", email=" + email
				+ ", name=" + name + ", mobile=" + mobile + ", address1=" + address1 + ", address2=" + address2
				+ ", country=" + country + ", state=" + state + ", city=" + city + ", postal_code=" + postal_code
				+ ", contact_name=" + contact_name + ", designation=" + designation + ", phone=" + phone
				+ ", web_address=" + web_address + ", customer_location=" + customer_location + ", customer_logo="
				+ customer_logo + ", CreatedBy=" + CreatedBy + ", CreatedDate=" + CreatedDate + ", ModifiedBy="
				+ ModifiedBy + ", ModifiedDate=" + ModifiedDate + ", Status=" + Status + "]";
	}

}
