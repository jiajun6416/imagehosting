package com.jiajun.imagehosting.util;

import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;

/**
 * aliyun oss操作 
 * @Date 2017/09/06 16时
 */
@Component
public class AliyunOssHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(AliyunOssHandler.class);
	
	@Value("${accessKeyId}")
	private String accessKeyId;
	
	@Value("${accessKeySecret}")
	private String accessKeySecret;
	
	@Value("${endPoint}")
    private String endpoint;
    
	@Value("${bucketName}")
    private String bucketName;

	@Value("${maxConnections}")
	private int maxConnections;
	
	@Value("${timeoutSecond}")
	private int timeoutSecond;
	
	@Value("${domain}")
	private String domain;
    
    private OSSClient ossClient;
    
    @PostConstruct
    public void initClient() {
    	ClientConfiguration conf = new ClientConfiguration();
    	conf.setMaxConnections(maxConnections);
    	conf.setSocketTimeout(timeoutSecond*1000);
    	ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
    }
    
    @PreDestroy
    public void destoryClient() {
    	ossClient.shutdown();
    }
    
    
    public String uploadFile(String path, String uniqueName, InputStream inputStream ) {
    	if(logger.isInfoEnabled()) {
    		logger.info("Uploading image {} to oss", path+uniqueName);
    	}
    	String key = path + uniqueName;
    	ossClient.putObject(bucketName, key, inputStream);
    	return domain+key;
    }
    
}
