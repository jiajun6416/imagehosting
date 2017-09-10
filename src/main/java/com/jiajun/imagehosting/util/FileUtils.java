package com.jiajun.imagehosting.util;

import java.util.Random;

public class FileUtils {
	
	/** 
	 * 获得文件的存储名称 0fec8b58b60a8983 ce00de0d24c5bdb0
	 * @return
	 */
	public static String generatorStoreName() {
		String str="";
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		Random random = new Random();
		for(int i=0; i<16; i++) {
			str+=hexDigits[random.nextInt(15)];
		}
		System.out.println(str);
		return str;
	}
}
