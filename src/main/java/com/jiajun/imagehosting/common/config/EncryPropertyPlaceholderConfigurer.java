package com.jiajun.imagehosting.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.jiajun.imagehosting.common.util.DesEncryptDecrypt;

/**
 * 对配置文件加解密 
 * @author tvu
 */
public class EncryPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{
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
			System.out.println(propertyName+"_"+propertyValue);
			return DesEncryptDecrypt.getInstance().decrypt(propertyValue);
		}
		return super.convertProperty(propertyName, propertyValue);
	}
}
