package com.jiajun.imagehosting.image.util;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;

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
	
    private static String key = "testkey";
    
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
    
    
    public void uploadFile() {
    	if(logger.isInfoEnabled()) {
    		logger.info("Uploading a new object to OSS from a file");
    	}
    }
}
