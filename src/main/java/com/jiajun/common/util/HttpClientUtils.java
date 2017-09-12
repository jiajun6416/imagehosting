package com.jiajun.common.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @Desc httpclient 工具类, 编码格式统一是utf-8
 * @author tvu
 * @Date 2017年7月10日 下午3:43:44
 * @version 1.0.0
 */
public class HttpClientUtils {

	private static final int DEFAULT_TIME_OUT = 20000;
	/** PoolingHttpClientConnectionManager */
	private static final PoolingHttpClientConnectionManager HTTP_CLIENT_CONNECTION_MANAGER;
	/** CloseableHttpClient */
	private static final CloseableHttpClient HTTP_CLIENT;

	static {
		HTTP_CLIENT_CONNECTION_MANAGER = new PoolingHttpClientConnectionManager(
				RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", SSLConnectionSocketFactory.getSocketFactory())
				.build());
		HTTP_CLIENT_CONNECTION_MANAGER.setDefaultMaxPerRoute(100);
		HTTP_CLIENT_CONNECTION_MANAGER.setMaxTotal(200);
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(DEFAULT_TIME_OUT)
				.setConnectTimeout(DEFAULT_TIME_OUT)
				.setSocketTimeout(DEFAULT_TIME_OUT)
				.build();
		HTTP_CLIENT = HttpClientBuilder.create()
				.setConnectionManager(HTTP_CLIENT_CONNECTION_MANAGER)
				.setDefaultRequestConfig(requestConfig).build();
	}
	/**
	 * get
	 * 
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url,Map<String, String> param) throws Exception {
		String resultString = null;
		CloseableHttpResponse response = null;
		URIBuilder builder = new URIBuilder(url);
		if (param != null) {
			for (String key : param.keySet()) {
				builder.addParameter(key, param.get(key));
			}
		}
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);
		
		try {
			response = HTTP_CLIENT.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				resultString = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(response != null) {
				response.close();
			}
		}
		return resultString;
	}

	/**
	 * post @param url @param param map @return @throws Exception @throws
	 */
	public static String doPost(String url, Map<String, String> param) throws Exception {
		CloseableHttpResponse response = null;
		String resultString = null;
		HttpPost httpPost = new HttpPost(url);
		if (param != null) {
			List<NameValuePair> paramList = new ArrayList<>();
			for (String key : param.keySet()) {
				paramList.add(new BasicNameValuePair(key, param.get(key)));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
			httpPost.setEntity(entity);
		}
		
		try {
			response = HTTP_CLIENT.execute(httpPost);
			if(response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(response != null) {
				response.close();
			}
		}
		return resultString;
	}

	/**
	 * post
	 * 
	 * @param url
	 * @param json格式
	 * @return
	 * @throws Exception
	 * @throws ParseException
	 */
	public static String doPostJson(String url, String json) throws ParseException, Exception {
		CloseableHttpResponse response = null;
		String resultString = null;
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
		httpPost.setEntity(entity);
		
		try {
			response = HTTP_CLIENT.execute(httpPost);
			if(response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(response != null) {
				response.close();
			}
		}
		return resultString;
	}
	
	public static InputStream getImageStream(String url) throws Exception {
		CloseableHttpResponse response = null;
		HttpGet httpGet = new HttpGet(url);
		try {
			response = HTTP_CLIENT.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return entity.getContent();
			}
		} catch (Exception e) {
			throw e;
		} finally {
		}
		return null;
	}
	
	/**
	 * get
	 * 
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static InputStream getInPutStreamByGet(String url,Map<String, String> param) throws Exception {
		CloseableHttpResponse response = null;
		URIBuilder builder = new URIBuilder(url);
		if (param != null) {
			for (String key : param.keySet()) {
				builder.addParameter(key, param.get(key));
			}
		}
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);
		
		try {
			response = HTTP_CLIENT.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return entity.getContent();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(response != null) {
				response.close();
			}
		}
		return null;
	}
	
	/**
	 *  保存至本地
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static void saveToOutPutStream(String url,Map<String, String> param, OutputStream os) throws Exception {
		CloseableHttpResponse response = null;
		URIBuilder builder = new URIBuilder(url);
		if (param != null) {
			for (String key : param.keySet()) {
				builder.addParameter(key, param.get(key));
			}
		}
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);
		
		try {
			response = HTTP_CLIENT.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity.writeTo(os);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(response != null) {
				response.close();
			}
		}
	}
	
	public static void main(String[] args) {
		try {
/*			InputStream is = getInPutStreamByGet("http://outofmemory.cn/static/book/java-concurrent-art.jpg", null);
			BufferedInputStream bis = new BufferedInputStream(is);
			FileOutputStream os = new FileOutputStream("d:/abc.png");
			byte[] b = new byte[1024];
			int len = -1;
			while((len= bis.read(b)) != -1) {
				os.write(b, 0, len);
			}
			bis.close();
			os.close();*/
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
