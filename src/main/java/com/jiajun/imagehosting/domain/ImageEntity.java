package com.jiajun.imagehosting.domain;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.jiajun.imagehosting.config.ImageProcess;

public class ImageEntity implements java.io.Serializable{
	
	
	/**
	 * 获得缩略图  imageZoomUrl
	 * @return 
	 */
	public String getImageZoomUrl() {
		if(StringUtils.isNotEmpty(httpUrl)) {
			return ImageProcess.getOssZoomImageUrl(httpUrl);
		} else {
			return "";
		}
	}
	
	private Integer id;
	private Integer albumId;
	private String fileName;
	private String fileType;
	private String uniqueName;
	private Integer width;
	private Integer height;
	private Long size;
	private String httpUrl;
	private Date createTime;
	private Date deleteTime;
	private Date updateTime;
	private Boolean isDelete;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	private static final long serialVersionUID = -1025504280966718306L;
	
}