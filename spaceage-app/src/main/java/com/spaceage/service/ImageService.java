package com.spaceage.service;

import org.springframework.stereotype.Service;

import com.spaceage.model.ImageModel;

@Service
public interface ImageService {

	void save(ImageModel img);

}
