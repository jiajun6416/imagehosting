package com.jiajun.imagehosting.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.jiajun.common.base.controller.BaseController;
import com.jiajun.common.bo.Page;
import com.jiajun.common.bo.Result;
import com.jiajun.imagehosting.config.Constant;
import com.jiajun.imagehosting.domain.AlbumEntity;
import com.jiajun.imagehosting.domain.ImageEntity;
import com.jiajun.imagehosting.domain.UserEntity;
import com.jiajun.imagehosting.service.AlbumService;
import com.jiajun.imagehosting.service.ImageService;

@Controller
public class AlbumController extends BaseController{
	
	@Autowired
	private AlbumService albumService;
	@Autowired
	private ImageService imageService;
	
	
	/**
	 * 验证 此相册是否是用户下的
	 * @return
	 * @throws Exception 
	 */
	private boolean hasAlbum(int userId, int albumId) throws Exception {
		//查询当前用户的所有相册
		List<Integer> aIds = albumService.getHasAlbumIds(userId);
		if(CollectionUtils.isNotEmpty(aIds) && aIds.contains(albumId)) {
			return true;
		} 
		return false;
	}
	
	/**
	 * 显示所有相册
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/album")
	public String toAlbum(HttpSession session, Model model) throws Exception {
		//查询所有相册
		UserEntity user = this.getLoginUser(session);
		List<AlbumEntity> albumList = albumService.getHasAlbumsContainImage(user.getId());
		model.addAttribute("albumList", albumList);
		return "album/list";
	}
	
	/**
	 * 创建相册
	 * @param name
	 * @param albumtype 1表示公开相册, 0 表示私有相册
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("createAblumn")
	@ResponseBody
	public Result createAblum(String name, int albumtype, HttpSession session) throws Exception{
		UserEntity user = this.getLoginUser(session);
		//将名称进行html编码
		name = HtmlUtils.htmlEscape(name);
		//查询是否有重名
		AlbumEntity existAlbum = albumService.getByUserAndName(user.getId(), name);
		if(existAlbum != null) {
			return Result.fail("相册名称重复");
		} else {
			AlbumEntity albumEntity = new AlbumEntity();
			albumEntity.setUserId(user.getId());
			albumEntity.setName(name);
			if(albumtype == 0) {
				albumEntity.setIsPublic(false);
			} else {
				albumEntity.setIsPublic(true);
			}
			albumService.save(albumEntity);
			return Result.success(null);
		}
	}
	/**
	 * 修改相册只能修改当前用户下的
	 * @param albumId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("album/publicSet")
	@ResponseBody
	public Result albumPublicSet(int albumId, int type, HttpSession session) throws Exception {
		UserEntity user = this.getLoginUser(session);
		if(user == null) {
			return Result.forbidden("not login");
		}
		if(this.hasAlbum(user.getId(), albumId)) {
			albumService.updateAlbumAuthority(albumId, type); 
			return Result.success(null);
		} else {
			return Result.error("非法操作!");
		}
	}
	
	@RequestMapping("album/delete")
	@ResponseBody
	public Result delete(int albumId, HttpSession session) throws Exception {
		UserEntity user = this.getLoginUser(session);
		//查询当前用户的所有相册
		List<Integer> aIds = albumService.getHasAlbumIds(user.getId());
		if(CollectionUtils.isNotEmpty(aIds) && aIds.contains(albumId)) {
			//如果只有一个相册则不允许删除
			if(aIds.size() == 1) {
				return Result.fail("one");
			}
			//查询是否有图片
			List<ImageEntity> imageList = imageService.getByAlbumId(albumId);
			if(CollectionUtils.isNotEmpty(imageList)) {
				return Result.fail("hasImage");
			}
			albumService.delete(albumId);
			return Result.success(null);
		} else {
			return Result.error("非法操作!");
		}
	}
	
	@RequestMapping("album/update")
	@ResponseBody
	public Result albumUpdate(int albumId, String name, int type, HttpSession session) throws Exception{
		UserEntity user = this.getLoginUser(session);
		if(this.hasAlbum(user.getId(), albumId)) {
			//输入转码
			name = HtmlUtils.htmlEscape(name);
			//查询是否有重名
			AlbumEntity album = albumService.getByUserAndName(user.getId(), name);
			if(album != null && album.getIsPublic()==(type!=0)) {
				return Result.fail("exist");
			}
			album = new AlbumEntity();
			album.setId(albumId);
			album.setName(name);
			album.setIsPublic(type!=0);
			albumService.update(album);
			return Result.success(null);
		} else {
			return Result.error("非法操作!");
		}
	}
	
	@RequestMapping("album/updateNum")
	@ResponseBody
	public Result albumUpdateNum(int albumId, HttpSession session) throws Exception {
		UserEntity user = this.getLoginUser(session);
		if(hasAlbum(user.getId(), albumId)) {
			int count = imageService.getCountByAlbumId(albumId);
			return Result.success(count);
		} else {
			return Result.error("非法操作!");
			
		}
	}
	
	@RequestMapping("album/selected")
	@ResponseBody
	public Result setSelectedAlbum(int albumId, HttpSession session, HttpServletResponse response) throws Exception{
		UserEntity user = this.getLoginUser(session);
		if(hasAlbum(user.getId(), albumId)) {
			//将用户行为,选中的album存储在session中
			session.setAttribute(Constant.SESSION_ALBUM_SELECTED, albumId);
			return Result.success(null);
		} else {
			return Result.error("非法操作!");
			
		}
	}
	@RequestMapping("list/{albumId}")
	public String imageList(@PathVariable("albumId") int albumId, HttpServletRequest request, 
							Model model, HttpSession session) throws Exception {
		UserEntity user = this.getLoginUser(session);
		if(hasAlbum(user.getId(), albumId)) {
			AlbumEntity album = albumService.getById(albumId);
			model.addAttribute("album", album);
			//分页查询
			Page<ImageEntity> page = new Page<>(request);
			page.addCondition("albumId", albumId);
			imageService.getPage(page);
			model.addAttribute("page", page);
			return "image/list";
		} else {
			throw new Exception("非法操作");
		}
	}

}
