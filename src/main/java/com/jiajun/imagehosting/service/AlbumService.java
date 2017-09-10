package com.jiajun.imagehosting.service;

import java.util.List;

import com.jiajun.imagehosting.domain.AlbumEntity;

public interface AlbumService {
	
	AlbumEntity getById(int id) throws Exception;
	
	void save(AlbumEntity albumEntity) throws Exception;
	
	/**
	 * 查询用户下是否具有此名称的相册
	 * @param id
	 * @return
	 */
	AlbumEntity getByUserAndName(Integer userId, String albumName) throws Exception;
	
	/**
	 * 获得用户下的所有相册, 相册下具有的图片总数, 和6张预览图
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<AlbumEntity> getHasAlbumsContainImage(Integer userId) throws Exception;

	/**
	 * 获得用户下的具有的相册以及相册下具有的图片总数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<AlbumEntity> getHasAlbumsWithImageNums(Integer userId) throws Exception;
	
	/**
	 * 获得拥有的所有相册, 不包括image
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<AlbumEntity> getHasAlbums(Integer id)throws Exception;
	
	/**
	 *  获得具备的Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<Integer> getHasAlbumIds(Integer id)throws Exception;

	/**
	 * 修改相册的访问权限
	 * @param albumId
	 * @param type
	 * @throws Exception
	 */
	void updateAlbumAuthority(Integer albumId, int type) throws Exception;
	
	void delete(Integer albumId) throws Exception;

	void update(AlbumEntity album) throws Exception;

}
