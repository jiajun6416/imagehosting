package com.jiajun.imagehosting.common.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		HTTP_CLIENT_CONNECTION_MANAGER = new PoolingHttpClientConnectionManager(RegistryBuilder
				.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", SSLConnectionSocketFactory.getSocketFactory()).build());
		HTTP_CLIENT_CONNECTION_MANAGER.setDefaultMaxPerRoute(100);
		HTTP_CLIENT_CONNECTION_MANAGER.setMaxTotal(200);
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(DEFAULT_TIME_OUT)
				.setConnectTimeout(DEFAULT_TIME_OUT).setSocketTimeout(DEFAULT_TIME_OUT).build();
		HTTP_CLIENT = HttpClientBuilder.create().setConnectionManager(HTTP_CLIENT_CONNECTION_MANAGER)
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
	public static String doGet(String url, String charSer, Map<String, String> param) throws Exception {
		String resultString = "";
		CloseableHttpResponse response = null;
		URIBuilder builder = new URIBuilder(url);
		if (param != null) {
			for (String key : param.keySet()) {
				builder.addParameter(key, param.get(key));
			}
		}
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);

		response = HTTP_CLIENT.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
		}
		return resultString;
	}

	/**
	 * post @param url @param param map @return @throws Exception @throws
	 */
	public static String doPost(String url, Map<String, String> param) throws Exception {
		CloseableHttpResponse response = null;
		String resultString = "";
		HttpPost httpPost = new HttpPost(url);
		if (param != null) {
			List<NameValuePair> paramList = new ArrayList<>();
			for (String key : param.keySet()) {
				paramList.add(new BasicNameValuePair(key, param.get(key)));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
			httpPost.setEntity(entity);
		}
		response = HTTP_CLIENT.execute(httpPost);
		resultString = EntityUtils.toString(response.getEntity(), "utf-8");
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
		String resultString = "";
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
		httpPost.setEntity(entity);
		response = HTTP_CLIENT.execute(httpPost);
		resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		return resultString;
	}

}
