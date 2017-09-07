package com.jiajun.imagehosting.image.service;

import com.jiajun.imagehosting.image.domain.ImageEntity;

public interface ImageService {
	
	void save(ImageEntity imageEntity) throws Exception;

	ImageEntity getByUniqueName(String uniqueName) throws Exception; 

	void  updateImageName(Integer pId, String pName) throws Exception;

	void deleteImage(Integer pId) throws Exception;
}
