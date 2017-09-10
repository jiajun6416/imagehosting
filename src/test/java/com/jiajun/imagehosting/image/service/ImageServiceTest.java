package com.jiajun.imagehosting.image.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiajun.imagehosting.service.ImageService;

public class ImageServiceTest extends BaseTest{
	
	@Autowired
	private ImageService iamgeService;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void uploadToOssTest() {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
