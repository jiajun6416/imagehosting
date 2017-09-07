package com.jiajun.imagehosting.image.service;

import com.jiajun.imagehosting.image.domain.AlbumEntity;

public interface AlbumService {
	AlbumEntity getById(int id) throws Exception;
}
