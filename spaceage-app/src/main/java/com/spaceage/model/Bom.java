package com.spaceage.model;

import java.util.Date;

public class Bom {

	private long bomId;

	private String lot_ref_no;

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

	private long createdBy=1;

	private Date createdDate;

	private long modifiedBy;

	private Date modifiedDate;

	private int status = 1;

	private String barCodeNo;

	private String bomSlNo;
	
	private String labelStatus;
	
	private String colorCode;
	
	private int pick_label_scan;
	
	private int part_label_scan;
	
	private boolean enablePartLabel;
	
	private String totalPartScanned;
	
	private String netWeight;
	
	private String grossWeight;
	
	private boolean enableCaseReport;
	
	private String projectCode;
	
	private Date pendingDate;
	
	private Date ackDate;
	
	private Date receivedDate;
	
	private Date packedDate;
	
	private byte[] picByte;
	
	private String lot_size;
	
	private Integer totalElements;
	
	private boolean deleteFlag;
	

	public Bom() {
		super();
	}

	public Bom(String lot_ref_no, String partNo, String partDescription, String version, String stLoction,
			String validity, String catDescription, String qtyRequired, String qtyLot, String primaryNo,
			String secondaryNo, String packCode, String packQty, String packingGroup, String totalNoOfPackingGroup,
			String mixGroup, String mix, String bomNo, String caseMap, String images) {
		super();
		this.lot_ref_no = lot_ref_no;
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

	public String getLot_ref_no() {
		return lot_ref_no;
	}

	public void setLot_ref_no(String lot_ref_no) {
		this.lot_ref_no = lot_ref_no;
	}

	public String getLabelStatus() {
		return labelStatus;
	}

	public void setLabelStatus(String labelStatus) {
		this.labelStatus = labelStatus;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public int getPick_label_scan() {
		return pick_label_scan;
	}

	public void setPick_label_scan(int pick_label_scan) {
		this.pick_label_scan = pick_label_scan;
	}

	public int getPart_label_scan() {
		return part_label_scan;
	}

	public void setPart_label_scan(int part_label_scan) {
		this.part_label_scan = part_label_scan;
	}

	public boolean isEnablePartLabel() {
		return enablePartLabel;
	}

	public void setEnablePartLabel(boolean enablePartLabel) {
		this.enablePartLabel = enablePartLabel;
	}

	public String getTotalPartScanned() {
		return totalPartScanned;
	}

	public void setTotalPartScanned(String totalPartScanned) {
		this.totalPartScanned = totalPartScanned;
	}

	public String getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}

	public String getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	public boolean isEnableCaseReport() {
		return enableCaseReport;
	}

	public void setEnableCaseReport(boolean enableCaseReport) {
		this.enableCaseReport = enableCaseReport;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public Date getPendingDate() {
		return pendingDate;
	}

	public void setPendingDate(Date pendingDate) {
		this.pendingDate = pendingDate;
	}

	public Date getAckDate() {
		return ackDate;
	}

	public void setAckDate(Date ackDate) {
		this.ackDate = ackDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getPackedDate() {
		return packedDate;
	}

	public void setPackedDate(Date packedDate) {
		this.packedDate = packedDate;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	public String getLot_size() {
		return lot_size;
	}

	public void setLot_size(String lot_size) {
		this.lot_size = lot_size;
	}

	public Integer getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	
}
