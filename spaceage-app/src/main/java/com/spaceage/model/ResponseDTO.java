package com.spaceage.model;

import java.util.List;

public class ResponseDTO {

	private Integer totalElements;
	private String sortByColumn;
	private String sortByMode;
	private boolean noData;
	private String message;
	private List<?> data;
	

	public ResponseDTO(String message2) {
		this.message = message2;
	}

	public ResponseDTO() {
		
	}

	public Integer getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

	public String getSortByColumn() {
		return sortByColumn;
	}

	public void setSortByColumn(String sortByColumn) {
		this.sortByColumn = sortByColumn;
	}

	public String getSortByMode() {
		return sortByMode;
	}

	public void setSortByMode(String sortByMode) {
		this.sortByMode = sortByMode;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public boolean getNoData() {
		return noData;
	}

	public void setNoData(boolean noData) {
		this.noData = noData;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
