package com.spaceage.model;

public class ImageModel {
	private Long id;
	private String name;
	private String type;
	private byte[] picByte;
	private String imgString;

	public ImageModel(String name, String type, String imgString) {
		this.name = name;
		this.type = type;
		this.setImgString(imgString);
	}

	public ImageModel() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	public String getImgString() {
		return imgString;
	}

	public void setImgString(String imgString) {
		this.imgString = imgString;
	}

}
