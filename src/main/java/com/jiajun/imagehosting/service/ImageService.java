package com.jiajun.imagehosting.service;

import java.util.List;

import com.jiajun.common.bo.Page;
import com.jiajun.imagehosting.domain.ImageEntity;

public interface ImageService {
	
	void save(ImageEntity imageEntity) throws Exception;

	ImageEntity getByUniqueName(String uniqueName) throws Exception; 

	void  updateImageName(Integer pId, String pName) throws Exception;

	void deleteImage(Integer pId) throws Exception;
	
	List<ImageEntity> getByAlbumId(Integer albumId) throws Exception;

	int getCountByAlbumId(Integer albumId) throws Exception;

	/**
	 * 分页查询
	 * @param page
	 */
	void getPage(Page<ImageEntity> page) throws Exception;

	List<Integer> getIdsByUId(int userId) throws Exception;

}
