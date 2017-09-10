package com.jiajun.imagehosting.config;

/**
 * 图片处理
 * Created by jiajun on 2017/09/10 19:27
 */
public class ImageProcess {
	
	//缩略图大小
	private static final int IMAGE_ZOOM_WIDTH = 300;
	
	public static String getOssZoomImageUrl(String originalUrl) {
		return originalUrl+"?x-oss-process=image/resize,w_"+IMAGE_ZOOM_WIDTH;
	}
	
	
}
