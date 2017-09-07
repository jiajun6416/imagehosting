package com.jiajun.imagehosting.image.service;

import com.jiajun.imagehosting.image.domain.ImageEntity;

public interface ImageService {
	
	void save(ImageEntity imageEntity) throws Exception;

	ImageEntity getByUniqueName(String uniqueName) throws Exception; 
}
