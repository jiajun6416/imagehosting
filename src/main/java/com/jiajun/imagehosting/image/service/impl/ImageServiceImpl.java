package com.jiajun.imagehosting.image.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiajun.imagehosting.image.service.ImageService;
import com.jiajun.imagehosting.image.util.AliyunOssHandler;

@Service
public class ImageServiceImpl implements ImageService{
	
	@Autowired
	private AliyunOssHandler ossHandler; 
	@Override
	public void uploadToOss() throws Exception {
		ossHandler.uploadFile();
	}

}
