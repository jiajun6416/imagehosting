package com.jiajun.imagehosting.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.jiajun.common.util.DesEncryptDecrypt;

/**
 * 对配置文件加解密 
 * @author tvu
 */
public class EncryPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{
	
	private String key;

	public void setKey(String key) {
		this.key = key;
	}

	private static List<String> encryPropers  = new ArrayList<>();

	static {
		encryPropers.add("accessKeyId");
		encryPropers.add("accessKeySecret");
		encryPropers.add("jdbc.username");
		encryPropers.add("jdbc.password");
	}
	
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if(encryPropers.contains(propertyName)) {
			 DesEncryptDecrypt descEnceypt = DesEncryptDecrypt.getInstance(key);
			return descEnceypt.decrypt(propertyValue);
		}
		return super.convertProperty(propertyName, propertyValue);
	}
}
