package com.jiajun.imagehosting.image.web.controller;


import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jiajun.imagehosting.common.bo.Result;
import com.jiajun.imagehosting.image.service.ImageService;

@Controller
public class ImageuploaderController {
	
	@Autowired
	private ImageService imageService;
	
	@RequestMapping("upload/{type}")
	public String toUploadLocal(
			@PathVariable("type")String type) {
		return "upload/"+type;
	}
	
	@RequestMapping("local/upload")
	@ResponseBody
	public Result doUpload(@RequestParam("file")MultipartFile file, HttpServletRequest request) throws Exception {
		imageService.uploadToOss();
		String contentType = file.getContentType();
		if(!"image/png".equals(contentType) && !"image/jpg".equals(contentType) 
				&& !"image/jpeg".equals(contentType) && !"image/gif".equals(contentType)) {
			return Result.error("文件格式错误");
		}
		String filename = file.getOriginalFilename();
		//获得图片宽度和高度
		BufferedImage image = ImageIO.read(file.getInputStream());
		if(image == null) {
			return Result.error("文件格式错误");
		} 
		int width = image.getWidth();
		int height = image.getHeight();
		long size = file.getSize();
		
		return Result.success(null);
	}
	
	
}
