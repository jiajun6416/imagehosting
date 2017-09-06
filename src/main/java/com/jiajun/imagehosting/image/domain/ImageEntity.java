package com.jiajun.imagehosting.image.domain;

import java.util.Date;

public class ImageEntity implements java.io.Serializable{
	
	private static final long serialVersionUID = 3464967742771286578L;

	private int id;
	private int userId;
	private String fileName;
	private int width;
	private int height;
	private long size;
	private String httpUrl;
	private String htmlUrl;
	private String markdownUrl;
	private String ubbUrl;
	
	private Date createTime;
	private Date deleteTime;
	private Date uploadTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getHttpUrl() {
		return httpUrl;
	}
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	public String getHtmlUrl() {
		return htmlUrl;
	}
	public String getMarkdownUrl() {
		return markdownUrl;
	}
	public String getUbbUrl() {
		return ubbUrl;
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
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
}

/*{
	"width": "826",
	"height": "644",
	"type": "png",
	"size": "384859",
	"ubburl": "[img]http:\/\/i4.bvimg.com\/609510\/dde5a906df6a89fd.png[\/img]",
	"linkurl": "http:\/\/i4.bvimg.com\/609510\/dde5a906df6a89fd.png",
	"htmlurl": "<img src='http:\/\/i4.bvimg.com\/609510\/dde5a906df6a89fd.png' \/>",
	"markdown": "![Markdown](http:\/\/i4.bvimg.com\/609510\/dde5a906df6a89fd.png)",
	"s_url": "http:\/\/i4.bvimg.com\/609510\/dde5a906df6a89fds.png",
	"t_url": "http:\/\/i4.bvimg.com\/609510\/dde5a906df6a89fdt.jpg",
	"findurl": "f0400008f8965e52"
}*/