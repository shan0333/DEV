package com.spaceage.model;

public class RequestDTO {

	private String sortByColumn;
	private String sortByMode;
	private String offset;
	private String limit;

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

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
