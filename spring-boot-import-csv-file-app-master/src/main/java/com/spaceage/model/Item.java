package com.spaceage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "item_master")
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "item_id")
	private int item_id;
	
	@Column(name = "customer_code")
	private int customer_code;
	
	@Column(name = "project_code")
	private int project_code;
	
	@Column(name = "org_country_id")
	private int org_country_id;
	
	@Column(name = "packing_type")
	private int packing_type;
	
	@Column(name = "lot_size")
	private String lot_size;
	
	@Column(name = "customer_login")
	private int customer_login;
	
	@Column(name = "lot_ref_no")
	private String lot_ref_no;
	
	@Column(name = "containers")
	private String containers;
	
	@Column(name = "CreatedBy")
	private int CreatedBy;
	
	@Column(name = "CreatedDate")
	private Date CreatedDate;
	
	@Column(name = "ModifiedBy")
	private int ModifiedBy;
	
	@Column(name = "ModifiedDate")
	private Date ModifiedDate;
	
	@Column(name = "Status")
	private int Status;

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public int getCustomer_code() {
		return customer_code;
	}

	public void setCustomer_code(int customer_code) {
		this.customer_code = customer_code;
	}

	public int getProject_code() {
		return project_code;
	}

	public void setProject_code(int project_code) {
		this.project_code = project_code;
	}

	public int getOrg_country_id() {
		return org_country_id;
	}

	public void setOrg_country_id(int org_country_id) {
		this.org_country_id = org_country_id;
	}

	public int getPacking_type() {
		return packing_type;
	}

	public void setPacking_type(int packing_type) {
		this.packing_type = packing_type;
	}

	public String getLot_size() {
		return lot_size;
	}

	public void setLot_size(String lot_size) {
		this.lot_size = lot_size;
	}

	public int getCustomer_login() {
		return customer_login;
	}

	public void setCustomer_login(int customer_login) {
		this.customer_login = customer_login;
	}

	public String getLot_ref_no() {
		return lot_ref_no;
	}

	public void setLot_ref_no(String lot_ref_no) {
		this.lot_ref_no = lot_ref_no;
	}

	public String getContainers() {
		return containers;
	}

	public void setContainers(String containers) {
		this.containers = containers;
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
	
}
