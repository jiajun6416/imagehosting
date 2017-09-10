package com.jiajun.imagehosting.domain;

import java.util.Date;
import java.util.List;

public class AlbumEntity implements java.io.Serializable{
	
	private static final long serialVersionUID = 5196824696603211610L;
	
	private Integer id;
	private Integer userId;
	private String name;
	private Boolean isPublic;
	private Boolean isSelected;
	private Date createTime;
	private Date updateTime;
	private Date deleteTime;
	private Boolean isDelete;
	
	private int imageSize;
	private List<ImageEntity> imageList;
	
	
	public int getImageSize() {
		return imageSize;
	}

	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}

	public List<ImageEntity> getImageList() {
		return imageList;
	}

	public void setImageList(List<ImageEntity> imageList) {
		this.imageList = imageList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public AlbumEntity selectByUserAndName(Integer id2) {
		return null;
	}

	

	
}
