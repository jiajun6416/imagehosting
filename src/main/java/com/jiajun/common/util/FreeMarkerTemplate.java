package com.jiajun.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;


import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerTemplate {
	
	private static  String filePath="d:/template/";
	
	public static void executeToHtml(String ftlName,  Map<String, Object> params){
		FileWriter writer = null;
		try {
			Configuration config = new Configuration(Configuration.VERSION_2_3_26);
			config.setClassLoaderForTemplateLoading(FreeMarkerTemplate.class.getClassLoader(), "ftl");
			Template template = config.getTemplate(ftlName+".ftl");
			String htmlFile = filePath+ftlName+".html";
			File f = new File(htmlFile);
			if(!f.exists()) {
				f.getParentFile().mkdirs();
			}
			writer = new FileWriter(htmlFile);
			template.process(params, writer);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void executeToConsole(String ftlName,  Map<String, Object> params){
		OutputStreamWriter os = null;
		try {
			Configuration config = new Configuration(Configuration.VERSION_2_3_26);
			config.setClassLoaderForTemplateLoading(FreeMarkerTemplate.class.getClassLoader(), "ftl");
			Template template = config.getTemplate(ftlName+".ftl");
			os = new OutputStreamWriter(System.out);
			template.process(params, os);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
	
}
