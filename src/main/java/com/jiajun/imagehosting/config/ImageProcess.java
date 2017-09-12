package com.jiajun.imagehosting.config;

/**
 * 图片处理
 * Created by jiajun on 2017/09/10 19:27
 */
public class ImageProcess {
	
	//缩略图大小
	private static final int IMAGE_ZOOM_WIDTH = 300;
	
	
	/**
	 * 缩放处理, 默认是宽度300, 高度等比
	 * @param originalUrl
	 * @return
	 */
	public static String doZoomProcess(String originalUrl) {
		return originalUrl+"?x-oss-process=image/resize,w_"+IMAGE_ZOOM_WIDTH;
	}
	
	
}
