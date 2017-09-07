package com.jiajun.imagehosting.image.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiajun.imagehosting.common.base.dao.BaseDao;
import com.jiajun.imagehosting.image.domain.AlbumEntity;
import com.jiajun.imagehosting.image.service.AlbumService;

@Service
public class AlbumServiceImpl implements AlbumService{
	
	@Autowired
	private BaseDao dao;
	
	private static final String ALBUM_NAMESPACE = "albumEntityMapper.";
	
	@Override
	public AlbumEntity getById(int id) throws Exception {
		return (AlbumEntity) dao.selectObject(ALBUM_NAMESPACE+"selectByPrimaryKey", id);
	}

}
