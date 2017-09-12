package com.jiajun.imagehosting.web.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
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

import com.jiajun.common.base.controller.BaseController;
import com.jiajun.common.bo.Result;
import com.jiajun.common.util.StringUtils;
import com.jiajun.imagehosting.domain.AlbumEntity;
import com.jiajun.imagehosting.domain.ImageEntity;
import com.jiajun.imagehosting.domain.UserEntity;
import com.jiajun.imagehosting.service.AlbumService;
import com.jiajun.imagehosting.service.ImageService;
import com.jiajun.imagehosting.util.AliyunOssHandler;
import com.jiajun.imagehosting.util.FileUtils;

@Controller
public class ImageController extends BaseController{
	
	@Autowired
	private ImageService imageService;
	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private AliyunOssHandler ossHandler;
	
	@RequestMapping("upload/{type}")
	public String toUploadLocal(@PathVariable("type")String type, 
			HttpSession session, Model model) throws Exception{
		UserEntity user = this.getLoginUser(session);
		//查询具有的相册
		List<AlbumEntity> albumList = albumService.getHasAlbumsWithImageNums(user.getId());
		model.addAttribute("albumList", albumList);
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
		filename = HtmlUtils.htmlEscape(filename);
		String fileType = filename.substring(filename.lastIndexOf(".")+1);
		BufferedImage image = ImageIO.read(file.getInputStream());
		if(image == null) {
			return Result.error("文件格式错误");
		} 
		int width = image.getWidth();
		int height = image.getHeight();
		long size = file.getSize();
		
		Integer albumId = this.getSelectedAlbum(session);
		AlbumEntity album = albumService.getById(albumId);
		
		UserEntity user = this.getLoginUser(session);
		String path = user.getUsername()+"/"+album.getName()+"/";
		
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
	
	
	@RequestMapping("network/upload")
	@ResponseBody
	public Result uploadNetworkImage(String fileurl, HttpSession session) throws Exception{
		// 判断是image路径
		if(StringUtils.isImageUrl(fileurl)) {
			BufferedImage imageBf = ImageIO.read( new URL(fileurl));
			if(imageBf == null) {
				return Result.fail("上传非图片内容!");
			}
			String fileType = fileurl.substring(fileurl.lastIndexOf(".")+1);
			ImageEntity image = new ImageEntity();
			
			image.setFileType(fileType);
			image.setHeight(imageBf.getHeight());
			image.setWidth(imageBf.getHeight());

			UserEntity user = this.getLoginUser(session);
			String uniqueName = FileUtils.generatorStoreName();
			Integer albumId = this.getSelectedAlbum(session);
			String fileName = fileurl.substring(fileurl.lastIndexOf("/")+1, fileurl.length()-4);
			fileName = HtmlUtils.htmlEscape(fileName);
		    
			image.setAlbumId(albumId);
			image.setFileName(fileName);
			image.setUniqueName(uniqueName);
			
			//BufferedImage 转 InputStream
			ByteArrayOutputStream os = new ByteArrayOutputStream();  
			ImageIO.write(imageBf, fileType, os);  
			InputStream is = new ByteArrayInputStream(os.toByteArray());  

			image.setSize((long) is.available());
			
			String path = user.getUsername()+"/"+albumService.getById(albumId).getName()+"/";
			String httpUrl = ossHandler.uploadFile(path, uniqueName+"."+fileType, is);
			image.setHttpUrl(httpUrl);
			imageService.save(image);
			
			return Result.success(uniqueName);
			
		} else {
			return Result.fail("非图片资源链接");
		}
	}
	
	@RequestMapping("detail/{uniqueName}")
	public String showImage(@PathVariable("uniqueName")String uniqueName, Model model, 
			HttpServletRequest request, HttpSession session) throws Exception {
		if(uniqueName.length() != 16) {
			throw new Exception("参数错误...");
		}
		ImageEntity imageEntity = imageService.getByUniqueName(uniqueName);
		if(imageEntity == null) {
			throw new Exception("所选图片不存在.");
		}
		AlbumEntity album = albumService.getById(imageEntity.getAlbumId());
		model.addAttribute("image", imageEntity);
		model.addAttribute("album",album);
		return "image/detail";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public Result imageUpdate(int pId, String pName, HttpSession session)throws Exception {
		UserEntity user = this.getLoginUser(session);
		if(user == null) {
			return Result.forbidden("session expire ");
		}
		if(hasPicture(user.getId(), pId)) {
			Assert.notNull(pName);
			//将html标签转义
			String  escape = HtmlUtils.htmlEscape(pName);
			imageService.updateImageName(pId, escape);
			return Result.success(escape);
		} else {
			return Result.error("非法操作");
		}
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Result imageDelete(int pId, HttpSession session) throws Exception {
		UserEntity user = this.getLoginUser(session);
		if(user == null) {
			return Result.forbidden("session expire ");
		}
		if(hasPicture(user.getId(), pId)) {
			imageService.deleteImage(pId);
			return Result.success(null);
		} else {
			return Result.error("非法操作");
		}
	}
	
	/**
	 * 查询用户是否具有这张图片
	 */
	private boolean hasPicture(int userId, int pId) throws Exception {
		List<Integer> ids = imageService.getIdsByUId(userId);
		if(CollectionUtils.isNotEmpty(ids) && ids.contains(pId)) {
			return true;
		}
		return false;
	}
	
}
