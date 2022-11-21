package com.spaceage.model;

import java.util.Date;

public class Bom {

	private long bomId;

	private long itemMasterId;

	private String partNo;

	private String partDescription;

	private String version;

	private String stLoction;

	private String validity;

	private String catDescription;

	private String qtyRequired;

	private String qtyLot;

	private String primaryNo;

	private String secondaryNo;

	private String packCode;

	private String packQty;

	private String packingGroup;

	private String totalNoOfPackingGroup;

	private String mixGroup;

	private String mix;

	private String bomNo;

	private String caseMap;

	private String images;

	private long createdBy;

	private Date createdDate;

	private long modifiedBy;

	private Date modifiedDate;

	private boolean status;

	private String barCodeNo;

	private String bomSlNo;

	public Bom() {
		super();
	}

	public Bom(long itemMasterId, String partNo, String partDescription, String version, String stLoction,
			String validity, String catDescription, String qtyRequired, String qtyLot, String primaryNo,
			String secondaryNo, String packCode, String packQty, String packingGroup, String totalNoOfPackingGroup,
			String mixGroup, String mix, String bomNo, String caseMap, String images) {
		super();
		this.itemMasterId = itemMasterId;
		this.partNo = partNo;
		this.partDescription = partDescription;
		this.version = version;
		this.stLoction = stLoction;
		this.validity = validity;
		this.catDescription = catDescription;
		this.qtyRequired = qtyRequired;
		this.qtyLot = qtyLot;
		this.primaryNo = primaryNo;
		this.secondaryNo = secondaryNo;
		this.packCode = packCode;
		this.packQty = packQty;
		this.packingGroup = packingGroup;
		this.totalNoOfPackingGroup = totalNoOfPackingGroup;
		this.mixGroup = mixGroup;
		this.mix = mix;
		this.bomNo = bomNo;
		this.caseMap = caseMap;
		this.images = images;
	}

	public long getBomId() {
		return bomId;
	}

	public void setBomId(long bomId) {
		this.bomId = bomId;
	}

	public long getItemMasterId() {
		return itemMasterId;
	}

	public void setItemMasterId(long itemMasterId) {
		this.itemMasterId = itemMasterId;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStLoction() {
		return stLoction;
	}

	public void setStLoction(String stLoction) {
		this.stLoction = stLoction;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getCatDescription() {
		return catDescription;
	}

	public void setCatDescription(String catDescription) {
		this.catDescription = catDescription;
	}

	public String getQtyRequired() {
		return qtyRequired;
	}

	public void setQtyRequired(String qtyRequired) {
		this.qtyRequired = qtyRequired;
	}

	public String getQtyLot() {
		return qtyLot;
	}

	public void setQtyLot(String qtyLot) {
		this.qtyLot = qtyLot;
	}

	public String getPrimaryNo() {
		return primaryNo;
	}

	public void setPrimaryNo(String primaryNo) {
		this.primaryNo = primaryNo;
	}

	public String getSecondaryNo() {
		return secondaryNo;
	}

	public void setSecondaryNo(String secondaryNo) {
		this.secondaryNo = secondaryNo;
	}

	public String getPackCode() {
		return packCode;
	}

	public void setPackCode(String packCode) {
		this.packCode = packCode;
	}

	public String getPackQty() {
		return packQty;
	}

	public void setPackQty(String packQty) {
		this.packQty = packQty;
	}

	public String getPackingGroup() {
		return packingGroup;
	}

	public void setPackingGroup(String packingGroup) {
		this.packingGroup = packingGroup;
	}

	public String getTotalNoOfPackingGroup() {
		return totalNoOfPackingGroup;
	}

	public void setTotalNoOfPackingGroup(String totalNoOfPackingGroup) {
		this.totalNoOfPackingGroup = totalNoOfPackingGroup;
	}

	public String getMixGroup() {
		return mixGroup;
	}

	public void setMixGroup(String mixGroup) {
		this.mixGroup = mixGroup;
	}

	public String getMix() {
		return mix;
	}

	public void setMix(String mix) {
		this.mix = mix;
	}

	public String getBomNo() {
		return bomNo;
	}

	public void setBomNo(String bomNo) {
		this.bomNo = bomNo;
	}

	public String getCaseMap() {
		return caseMap;
	}

	public void setCaseMap(String caseMap) {
		this.caseMap = caseMap;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getBomSlNo() {
		return bomSlNo;
	}

	public void setBomSlNo(String bomSlNo) {
		this.bomSlNo = bomSlNo;
	}

	public String getBarCodeNo() {
		return barCodeNo;
	}

	public void setBarCodeNo(String barCodeNo) {
		this.barCodeNo = barCodeNo;
	}

}
