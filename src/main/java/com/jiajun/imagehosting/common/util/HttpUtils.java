package com.jiajun.imagehosting.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * @desc http 工具类 
 * @author JIAJUN
 * @date 2017年7月23日下午9:56:09
 */
public class HttpUtils {
	
	/*静态请求后缀*/
	private static String staticRequest = ".css,.js,.png,.jpg,.gif,.jpeg,.bmp,.ico,.swf,.psd,.htc,.htm,.html,.crx,.xpi,.exe,.ipa,.apk,.html";
	
	/**
	 * 判断是否是ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		boolean mine = request.getHeader("accept").contains("application/json");
		String header = request.getHeader("X-Requested-With");
		if(mine || StringUtils.isNoneEmpty(header) && header.contains("XMLHttpRequest")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 写回json对象
	 * @param reponse
	 * @param object
	 */
	public static void WriteJson(HttpServletResponse response, Object object) {
		response.setContentType("application/json; charset=utf-8");
		 PrintWriter write = null;
			try {
				write = response.getWriter();
				write.write(JsonUtils.toString(object));
				write.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(write != null) {
					write.close();
				}
				write = null;
			}
	}
	
	/**
	 * 返回返回json格式的字符串
	 * @param reponse
	 * @param object
	 */
	public static void WriteJson(HttpServletResponse response, String msg) {
		response.setContentType("application/json; charset=utf-8");
		 PrintWriter write = null;
			try {
				write = response.getWriter();
				write.write(msg);
				write.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(write != null) {
					write.close();
				}
				write = null;
			}
	}
	
	/**
	 * 设置客户端缓存过期时间 的Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		// Http 1.0 header, set a fix expires date.
		response.setDateHeader(HttpHeaders.EXPIRES, System.currentTimeMillis() + expiresSeconds * 1000);
		// Http 1.1 header, set a time after now.
		response.setHeader(HttpHeaders.CACHE_CONTROL, "private, max-age=" + expiresSeconds);
	}

	/**
	 * 设置禁止客户端缓存的Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader(HttpHeaders.EXPIRES, 1L);
		response.addHeader(HttpHeaders.PRAGMA, "no-cache");
		// Http 1.1 header
		response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0");
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModifiedDate);
	}

	/**
	 * 设置Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader(HttpHeaders.ETAG, etag);
	}

	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
	 * 
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 * 
	 * @param lastModified 内容的最后修改时间.
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
			long lastModified) {
		long ifModifiedSince = request.getDateHeader(HttpHeaders.IF_MODIFIED_SINCE);
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
	 * 
	 * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 * 
	 * @param etag 内容的ETag.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader(HttpHeaders.IF_NONE_MATCH);
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader(HttpHeaders.ETAG, etag);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 获取当前请求对象, 前提是开启RequestContextListener监听器
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		try{
			return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName 下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
		try {
			// 中文文件名支持
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
			e.getMessage();
		}
	}
	
	
	/**
	 * 判断是否是静态请求
	 * @return
	 */
	public static boolean isStaticRequest(String uri) {
		String[] staticFiles = staticRequest.split(",");
		if (StringUtils.endsWithAny(uri, staticFiles) && !StringUtils.endsWithAny(uri, ".jsp") && !StringUtils.endsWithAny(uri, ".java")){
			return true;
		}
		return false;
	}
	
	/**
	 * 获得IP地址
	 * @param request
	 * @return
	 */
    public static String getIpAddress(HttpServletRequest request) {  
        String localIP = "127.0.0.1";  
        String ip = request.getHeader("x-forwarded-for");  
        if (StringUtils.isBlank(ip) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (StringUtils.isBlank(ip) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (StringUtils.isBlank(ip) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  
    
    /**
     * 获得basePath
     * @param request
     * @return
     */
    public static String getBasePath(HttpServletRequest request) {
    	return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
    }
}
