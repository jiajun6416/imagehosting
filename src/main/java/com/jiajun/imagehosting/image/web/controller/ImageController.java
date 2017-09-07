package com.jiajun.imagehosting.image.web.controller;


import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.jiajun.imagehosting.common.base.controller.BaseController;
import com.jiajun.imagehosting.common.bo.Result;
import com.jiajun.imagehosting.common.config.Constant;
import com.jiajun.imagehosting.common.util.StringUtils;
import com.jiajun.imagehosting.image.domain.AlbumEntity;
import com.jiajun.imagehosting.image.domain.ImageEntity;
import com.jiajun.imagehosting.image.domain.UserEntity;
import com.jiajun.imagehosting.image.service.AlbumService;
import com.jiajun.imagehosting.image.service.ImageService;
import com.jiajun.imagehosting.image.util.AliyunOssHandler;
import com.jiajun.imagehosting.image.util.FileUtils;

@Controller
public class ImageController extends BaseController{
	
	@Autowired
	private ImageService imageService;
	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private AliyunOssHandler ossHandler;
	
	@RequestMapping("upload/{type}")
	public String toUploadLocal(
			@PathVariable("type")String type) {
		return "upload/"+type;
	}
	
	@RequestMapping("local/upload")
	@ResponseBody
	public Result doUpload(@RequestParam("file")MultipartFile file, HttpServletRequest request, HttpSession session) throws Exception {
		String contentType = file.getContentType();
		if(!"image/png".equals(contentType) && !"image/jpg".equals(contentType) 
				&& !"image/jpeg".equals(contentType) && !"image/gif".equals(contentType)) {
			return Result.error("文件格式错误");
		}
		String filename = file.getOriginalFilename();
		String fileType = filename.substring(filename.lastIndexOf(".")+1);
		BufferedImage image = ImageIO.read(file.getInputStream());
		if(image == null) {
			return Result.error("文件格式错误");
		} 
		int width = image.getWidth();
		int height = image.getHeight();
		long size = file.getSize();
		
		AlbumEntity album =this.getSelectedAlbum(session);
		if(album == null) {
			//todo get default album
		}
		UserEntity user = this.getLoginUser(session);
		String path = user.getUsername()+"/"+album.getName()+"/";
		
		session.getAttribute(Constant.LOGIN_USER);
		ImageEntity imageEntity = new ImageEntity();
		imageEntity.setHeight(height);
		imageEntity.setWidth(width);
		imageEntity.setSize(size);
		imageEntity.setFileName(filename.substring(0, filename.lastIndexOf(".")));
		imageEntity.setFileType(fileType);
		imageEntity.setAlbumId(album.getId());
		String uniqueName = FileUtils.generatorStoreName();
		imageEntity.setUniqueName(uniqueName);
		//上传到oss服务器
		String httpUrl = ossHandler.uploadFile(path, uniqueName+"."+fileType, file.getInputStream());
		
		imageEntity.setHttpUrl(httpUrl);
		
		imageService.save(imageEntity);
		
		return Result.success(uniqueName);
	}
	
	@RequestMapping("detail/{uniqueName}")
	public String showImage(@PathVariable("uniqueName")String uniqueName, Model model, 
			HttpServletRequest request, HttpSession session) throws Exception {
		if(uniqueName.length() != 16) {
			throw new Exception("参数错误...");
		}
		AlbumEntity album = null;
		String albumId = request.getParameter("albumId");
		if(StringUtils.isEmpty(albumId)) {
			album = this.getSelectedAlbum(session);
		} else {
			album = albumService.getById(Integer.valueOf(albumId));
		}
		UserEntity user = this.getLoginUser(session);
		Assert.notNull(album);
		Assert.notNull(user);
		ImageEntity imageEntity = imageService.getByUniqueName(uniqueName);
		model.addAttribute("username", user.getUsername());
		model.addAttribute("album",album);
		model.addAttribute("image", imageEntity);
		return "image/detail";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public Result imageUpdate(int pId, String pName)throws Exception {
		Assert.notNull(pName);
		//将html标签转意
		String  escape = HtmlUtils.htmlEscape(pName);
		imageService.updateImageName(pId, escape);
		return Result.success(escape);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Result imageDelete(int pId) throws Exception {
		imageService.deleteImage(pId);
		return Result.success(null);
	}
	
	
}
