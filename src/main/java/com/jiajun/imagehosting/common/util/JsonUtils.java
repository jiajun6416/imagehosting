package com.jiajun.imagehosting.common.util;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * jackson json工具类
 */
@SuppressWarnings("unchecked")
public class JsonUtils {
	
    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 对象转换为json字符串
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            logger.error("encode(Object)", e); 
        } catch (JsonMappingException e) {
            logger.error("encode(Object)", e); 
        } catch (IOException e) {
            logger.error("encode(Object)", e); 
        }
        return null;
    }

    /**
     *将集合类型的json转化为对象
     *
     * @param json
     * @param jsonTypeReference
     * @return
     */
    public static <T> T toString(String json, TypeReference<T> jsonTypeReference) {
        try {
            return (T) objectMapper.readValue(json, jsonTypeReference);
        } catch (JsonParseException e) {
            logger.error("decode(String, JsonTypeReference<T>)", e);
        } catch (JsonMappingException e) {
            logger.error("decode(String, JsonTypeReference<T>)", e);
        } catch (IOException e) {
            logger.error("decode(String, JsonTypeReference<T>)", e);
        }
        return null;
    }
    
    /**
     * 将json string反序列化成对象
     * @param json
     * @param valueType
     * @return
     */
    public static <T> T toObject(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonParseException e) {
            logger.error("decode(String, Class<T>)", e);
        } catch (JsonMappingException e) {
            logger.error("decode(String, Class<T>)", e);
        } catch (IOException e) {
            logger.error("decode(String, Class<T>)", e);
        }
        return null;
    }
   /**
    * 将集合类型的json转化为对象
    * @param is
    * @param jsonTypeReference
    * @return
    */
    public static <T> T toObject(InputStream is, TypeReference<T> jsonTypeReference) {
        try {
            return (T) objectMapper.readValue(is, jsonTypeReference);
        } catch (JsonParseException e) {
            logger.error("decode(String, JsonTypeReference<T>)", e);
        } catch (JsonMappingException e) {
            logger.error("decode(String, JsonTypeReference<T>)", e);
        } catch (IOException e) {
            logger.error("decode(String, JsonTypeReference<T>)", e);
        }
        return null;
    }
}
