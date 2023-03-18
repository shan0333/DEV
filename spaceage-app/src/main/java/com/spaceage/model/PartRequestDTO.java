package com.spaceage.model;

import java.util.ArrayList;
import java.util.List;

public class PartRequestDTO {

	private String sortByColumn = "";
	private String sortByMode;
	private String offset;
	private String limit;
	private String key;
	private List<String> searchValue = new ArrayList<>();

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<String> getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(List<String> searchValue) {
		this.searchValue = searchValue;
	}

}
