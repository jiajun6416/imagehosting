package com.jiajun.imagehosting.image.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiajun.imagehosting.common.base.dao.BaseDao;
import com.jiajun.imagehosting.image.domain.ImageEntity;
import com.jiajun.imagehosting.image.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService{
	
	
	private static final String PICTURE_NAMESPACE = "imageEntityMapper.";
	
	@Autowired
	private BaseDao dao;

	@Override
	public void save(ImageEntity imageEntity) throws Exception {
		Date now = new Date();
		imageEntity.setCreateTime(now);
		imageEntity.setUpdateTime(now);
		imageEntity.setIsDelete(false);
		dao.insert(PICTURE_NAMESPACE+"insert", imageEntity);
	}

	@Override
	public ImageEntity getByUniqueName(String uniqueName) throws Exception {
		return (ImageEntity) dao.selectObject(PICTURE_NAMESPACE+"selectByUnique", uniqueName);
	}

	@Override
	public void updateImageName(Integer pId, String pName) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("id", pId);
		params.put("pName", pName);
		params.put("updateTime", new Date());
		dao.update(PICTURE_NAMESPACE+"updateName", params);
	}

	@Override
	public void deleteImage(Integer pId) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("id", pId);
		params.put("updateTime", new Date());
		dao.update(PICTURE_NAMESPACE+"updateStateToDelete", params);
	}
	
}
