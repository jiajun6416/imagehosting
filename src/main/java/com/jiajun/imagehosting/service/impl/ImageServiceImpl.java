package com.jiajun.imagehosting.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.jiajun.common.base.dao.BaseDao;
import com.jiajun.common.bo.Page;
import com.jiajun.imagehosting.domain.ImageEntity;
import com.jiajun.imagehosting.service.ImageService;

@SuppressWarnings("unchecked")
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
		Date now = new Date();
		params.put("updateTime", now);
		params.put("deleteTime", now);
		dao.update(PICTURE_NAMESPACE+"updateStateToDelete", params);
	}

	@Override
	public List<ImageEntity> getByAlbumId(Integer albumId) throws Exception {
		return dao.selectList(PICTURE_NAMESPACE+"selectByAlbumId", albumId);
	}

	@Override
	public int getCountByAlbumId(Integer albumId) throws Exception {
		return (int) dao.selectObject(PICTURE_NAMESPACE+"selectCountByAlbumId", albumId);
	}

	@Override
	public void getPage(Page<ImageEntity> page) throws Exception{
		dao.page(page);
	}

	@Override
	public List<Integer> getIdsByUId(int userId) throws Exception {
		return dao.selectList(PICTURE_NAMESPACE+"selectIdsByUserId", userId);
	}
	
}
