package com.infoeai.eai.common;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 售中工具彪洋的接口调用方式
 * @title:
 * @author:weixia
 * @date：2016-12-29
 */

public class HttpConnectionManager {  
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	private static Integer connTimeout = 100000;
	private static Integer readTimeout = 100000;
	private static String charset = "UTF-8";
	
	/**
	 * 发送一个 GET 请求 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static JSONObject httpsGet(String urlStr)
			throws ConnectTimeoutException,SocketTimeoutException, Exception {
	    logger.info("============调用地址："+urlStr+"===========");
		JSONObject jsonObj = null;
		SSLContext sslContext = SSLContext.getInstance("TLS");//或SSL
		TrustManager[] tms = { ignoreCertificationTrustManger };
		sslContext.init(null, tms, new java.security.SecureRandom());
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		
		URL url = new URL(urlStr);
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		HttpsURLConnection conn = ((HttpsURLConnection) url.openConnection());
		conn.setSSLSocketFactory(ssf);
		if (connTimeout != null) {
			conn.setConnectTimeout(connTimeout);
		}
		if (readTimeout != null) {
			conn.setReadTimeout(readTimeout);
		}
		conn.setRequestProperty("apiKey", "apiKey-yonyou");
		conn.setRequestProperty("Content-Type", "application/json");
		//获取状态码
		int respcode = conn.getResponseCode();
		if(respcode==200){
			InputStreamReader im = new InputStreamReader(conn.getInputStream(),charset);
			BufferedReader reader = new BufferedReader(im);

			StringBuffer sbf = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sbf.append(line);
			}
			logger.info("===200===访问成功");
		    String json="{\"Data\":"+sbf+"}";
		    if(null != sbf && !"".equals(sbf) && !"null".equals(sbf)){
		    	logger.info(json);
		    	jsonObj = new JSONObject(json);
		    }
		}else if(respcode==400){
			logger.info("===400===查询不到数据");
	    	return jsonObj;
		}else if(respcode==404){
			logger.info("===404===UAL错误");
	    	return jsonObj;
		}else if(respcode==500){
			logger.info("===500===服务异常");
	    	return jsonObj;
		}
		return jsonObj;
	}
	
	/**
	 * 重写三个方法
	 */
	private static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {
	
		private X509Certificate[] certificates;
		
		@Override
		public void checkClientTrusted(X509Certificate certificates[],
		String authType) throws CertificateException {
			if (this.certificates == null) {
				this.certificates = certificates;
			}
		}
		
		@Override
		public void checkServerTrusted(X509Certificate[] ax509certificate,
		String s) throws CertificateException {
			if (this.certificates == null) {
				this.certificates = ax509certificate;
			}
		}
		
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};
	
	/**
	 * 重写一个方法
	 */
	private static HostnameVerifier hv = new HostnameVerifier() {
		public boolean verify(String urlHostName, SSLSession session) {
			return urlHostName.equals(session.getPeerHost());
		}
	};
	
	public static void main(String[] args) throws Exception {
		JSONObject json = httpsGet("https://dccmtest.boldseas.com/api/sps/dataSync/selectOpportunityInfo/1/20190302115326/20170302115329");
		if(null != json && !"".equals(json) && !"null".equals(json)){
			JSONArray date = json.getJSONArray("Data");
			int iSize1 = date.length();  
			logger.info("json中数组Size:" + iSize1);  
		    for (int i = 0; i < iSize1; i++) {  
		    	json= date.getJSONObject(i);
		    	logger.info("================获取潜客信息数据详细必填项====================");
		    	logger.info("[" + i + "]uniquenessID=" + json.get("uniquenessID"));
		    	logger.info("[" + i + "]clientType=" + json.get("clientType"));
		    	logger.info("[" + i + "]name=" + json.get("name"));
		    	logger.info("[" + i + "]gender=" + json.get("gender"));
		    	logger.info("[" + i + "]phone=" + json.get("phone"));
		    	logger.info("[" + i + "]provinceID=" + json.get("provinceID"));
		    	logger.info("[" + i + "]cityID=" + json.get("cityID"));
		    	logger.info("[" + i + "]oppLevelID=" + json.get("oppLevelID"));
		    	logger.info("[" + i + "]sourceType=" + json.get("sourceType"));
		    	logger.info("[" + i + "]secondSourceType=" + json.get("secondSourceType"));
		    	logger.info("[" + i + "]dealerCode=" + json.get("dealerCode"));
		    	logger.info("[" + i + "]dealerUserID=" + json.get("dealerUserID"));
		    	logger.info("[" + i + "]brandID=" + json.get("brandID"));
		    	logger.info("[" + i + "]modelID=" + json.get("modelID"));
		    	logger.info("[" + i + "]carStyleID=" + json.get("carStyleID"));
		    	logger.info("[" + i + "]intentCarColor=" + json.get("intentCarColor"));
		    	logger.info("[" + i + "]createDate=" + json.get("createDate"));
		    	logger.info("[" + i + "]updateDate=" + json.get("updateDate"));
		    	logger.info("[" + i + "]dccmId=" + json.get("dccmId"));
		    }
		}
	}
}
