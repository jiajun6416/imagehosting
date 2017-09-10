package com.jiajun.imagehosting.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jiajun.common.base.dao.BaseDao;
import com.jiajun.imagehosting.domain.AlbumEntity;
import com.jiajun.imagehosting.service.AlbumService;

@SuppressWarnings("unchecked")
@Service
public class AlbumServiceImpl implements AlbumService{
	
	@Autowired
	private BaseDao dao;
	
	private static final String ALBUM_NAMESPACE = "albumEntityMapper.";
	
	@Override
	public AlbumEntity getById(int id) throws Exception {
		return (AlbumEntity) dao.selectObject(ALBUM_NAMESPACE+"selectByPrimaryKey", id);
	}

	@Override
	public void save(AlbumEntity albumEntity) throws Exception {
		Date now = new Date();
		albumEntity.setCreateTime(now );
		albumEntity.setUpdateTime(now);
		albumEntity.setIsSelected(false);
		albumEntity.setIsDelete(false);
		dao.insert(ALBUM_NAMESPACE+"insert", albumEntity);
	}

	@Override
	public AlbumEntity getByUserAndName(Integer userId, String albumName) throws Exception {
		Assert.notNull(albumName);
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("name", albumName);
		return (AlbumEntity) dao.selectObject(ALBUM_NAMESPACE+"selectByUserAndName", params);
	}

	
	@Override
	public List<AlbumEntity> getHasAlbumsContainImage(Integer userId) throws Exception {
		return dao.selectList("selectContainImageByUserId", userId);
	}

	@Override
	public List<AlbumEntity> getHasAlbums(Integer id) throws Exception {
		return dao.selectList(ALBUM_NAMESPACE+"selectByUserId", id);
	}

	@Override
	public List<Integer> getHasAlbumIds(Integer id) throws Exception {
		return dao.selectList(ALBUM_NAMESPACE+"selectIdsByUserId", id);
	}

	@Override
	public void updateAlbumAuthority(Integer albumId, int type) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("id", albumId);
		params.put("isPublic", type==0?1:0);
		params.put("updateTime", new Date());
		dao.update(ALBUM_NAMESPACE+"updateAlbumAuthority", params);
	}

	@Override
	public void delete(Integer albumId) throws Exception {
		Map<String, Object> params = new HashMap<>();
		Date now = new Date();
		params.put("id", albumId);
		params.put("updateTime", now);
		params.put("deleteTime", now);
		dao.update(ALBUM_NAMESPACE+"updateStateToDelte", params);
	}

	@Override
	public void update(AlbumEntity album) throws Exception {
		album.setUpdateTime(new Date());
		dao.update(ALBUM_NAMESPACE+"updateByPrimaryKeySelective", album);
	}

	@Override
	public List<AlbumEntity> getHasAlbumsWithImageNums(Integer userId) throws Exception {
		return dao.selectList(ALBUM_NAMESPACE+"selectContainImageNumsByUserId", userId);
	}

}
