package com.infoeai.eai.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.boldseas.secret.util.StringDesUtils;

public class HttpUtil {
	private static Logger logger = Logger.getLogger(HttpUtil.class);
	public static String httpPost(String url, Map<String, String> params)
			throws Exception {
		URL u = null;
		HttpURLConnection con = null;
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			for (Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
		}
		logger.info("send_url:" + url);
		logger.info("send_data:" + sb.toString());
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(10000);
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				con.getInputStream(), "UTF-8"));
		String temp;
		while ((temp = br.readLine()) != null) {
			buffer.append(temp);
			buffer.append("\n");
		}

		return buffer.toString();
	}
	/**
	 * HttpPost方法的加密算法（只对参数进行加密）
	 */
	public static String httpPostDes(String url, Map<String, String> params)
			throws Exception {
		URL u = null;
		HttpURLConnection con = null;
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			for (Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(StringDesUtils.encrypt(e.getValue()));
				sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
		}
		logger.info("send_url:" + url);
		logger.info("send_data:" + sb.toString());
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(10000);
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		
		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				con.getInputStream(), "UTF-8"));
		String temp;
		while ((temp = br.readLine()) != null) {
			buffer.append(temp);
			buffer.append("\n");
		}
		
		return buffer.toString();
	}
	
	/** 
     * 发送HttpPost请求 
     * @param strURL 服务地址 
     * @param params json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/> 
     * @return 成功:返回json字符串<br/> 
     */ 
	public static String httpPostToJson(String strURL, String params)
			throws Exception {
		logger.info("send_url:" + strURL);
		logger.info("params:" + params);
		HttpURLConnection connection = null;
		OutputStreamWriter out = null;
		InputStream is = null;
		BufferedReader in  = null;
		try {  
            URL url = new URL(strURL);// 创建连接  
            connection = (HttpURLConnection) url.openConnection();  
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true);  
            connection.setRequestMethod("POST"); // 设置请求方式  
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.connect();  
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码  
            out.append(params);  
            out.flush();  
            out.close();  
            // 读取响应  
            /*int length = (int) connection.getContentLength();// 获取长度  
            is = connection.getInputStream();  
            if (length != -1) {  
                byte[] data = new byte[length];  
                byte[] temp = new byte[512];  
                int readLen = 0;  
                int destPos = 0;  
                while ((readLen = is.read(temp)) > 0) {  
                    System.arraycopy(temp, 0, data, destPos, readLen);  
                    destPos += readLen;  
                }  
                String result = new String(data, "UTF-8"); // utf-8编码  
                return result;  
            }  */
           
           /* ByteArrayOutputStream bo = new ByteArrayOutputStream();
            is = connection.getInputStream();  
            byte[] temp = new byte[512];  
            int readLen = 0;  
            while ((readLen = is.read(temp)) > 0) {  
            	bo.write(temp,0,readLen);
            }  
            String result = new String(bo.toByteArray(), "UTF-8"); // utf-8编码  
            return result;  */
            
            String result = "";
            // 定义BufferedReader输入流来读取URL的响应
	        InputStream cc= connection.getInputStream();
	        InputStreamReader ii =new InputStreamReader(cc,"Utf-8");
	        in = new BufferedReader(ii);
	        String line;
	        while ((line = in.readLine()) != null) {
	            result += line;
	        }
	        return result;
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } finally {
			if (out != null) {
				out.close();
			}
			if (is != null) {
				is.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
        return "error"; // 自定义错误信息 
	}
	
	public static void main(String[] args) throws Exception {
		System.out.print("output:");
		System.out.println(HttpUtil.httpPostToJson("http://localhost/DMS_HMCI/testServlet", null));
//		String resultXml = HttpUtil.httpPost(BoldseasConstant.TEST_URL, params);
	}
}
