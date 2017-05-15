/*
* Copyright 2016 Yonyou Auto Information Technology（Shanghai） Co., Ltd. All Rights Reserved.
*
* This software is published under the terms of the YONYOU Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : marketing-dealer-open-api
*
* @File name : RestDemoUtil.java
*
* @Author : sangdeliang
*
* @Date : 2016年11月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月29日    sangdeliang    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.function.utils.http;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.function.domains.DTO.http.RestServiceRequestDto;
import com.yonyou.dms.function.domains.DTO.http.RestServiceResponseDto;
import com.yonyou.dms.function.exception.UtilException;
import com.yonyou.dms.function.service.common.RegexReplaceCallBack;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.jsonSerializer.JSONUtil;
import com.yonyou.dms.function.utils.security.MD5Util;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author zhangxianchao @ description OKHTTP发送消息工具类
 * @date 2016年11月29日
 */

public class RestServiceUtil {

    // 定义日志接口
    private static final Logger   logger                     = LoggerFactory.getLogger(RestServiceUtil.class);

    // 发送的字符为UTF-8格式
    public static final MediaType JSON                       = MediaType.parse("application/json; charset=utf-8");
    // 发送的字符为UTF-8格式
    public static final MediaType TEXT_HTML                  = MediaType.parse("text/html; charset=utf-8");
    // 设置读取超时时间 默认5S
    public static final int       REST_READ_TIMEOUT          = 5000;
    // 设置写的超时时间 默认5S
    public static final int       REST_WRITE_TIMEOUT         = 5000;
    // 设置连接超时时间 默认5S
    public static final int       REST_CONN_TIMEOUT          = 5000;

    public static final String    GET_METHOD_NAME            = "get";
    public static final String    POST_METHOD_NAME           = "post";
    public static final String    PUT_METHOD_NAME            = "put";
    public static final String    DELETE_METHOD_NAME         = "delete";

    /**
     * @author zhangxc @ description 发送GET请求，并且返回消息内容
     * @date 2016年12月5日
     * @param paramDto
     * @return
     * @throws Exception
     */
    public static RestServiceResponseDto getRequest(RestServiceRequestDto requestDto){
        return sendRequest(requestDto, GET_METHOD_NAME, null);
    }

    /**
     * 发送请求---POST 方式
     * 
     * @author zhangxc
     * @date 2017年1月14日
     * @param paramDto
     * @return
     * @throws Exception
     */
    public static RestServiceResponseDto postJsonRequest(RestServiceRequestDto requestDto) {
        // 如果body 不为空
        RequestBody body = null;
        if (!StringUtils.isNullOrEmpty(requestDto.getBody())) {
            // 建立发送消息体
            body = RequestBody.create(JSON, JSONUtil.objectToJson(requestDto.getBody()));
        }
        return sendRequest(requestDto, POST_METHOD_NAME, body);
    }

    /**
     * 发送请求---POST 方式
     * 
     * @author zhangxc
     * @date 2017年1月14日
     * @param paramDto
     * @return
     * @throws Exception
     */
    public static RestServiceResponseDto postHtmlRequest(RestServiceRequestDto requestDto) {
        // 如果body 不为空
        RequestBody body = null;
        if(requestDto.getBody()!=null){
            Object bodyObject = requestDto.getBody();
            if(bodyObject instanceof Map ){
                body = getRequestBodyByMap((Map)bodyObject);
            }else{
                throw new UtilException("请求参数不支持");
            }
        }
        return sendRequest(requestDto, POST_METHOD_NAME, body);
    }

    /**
     * 发送请求
     * 
     * @author zhangxc
     * @date 2017年1月14日
     * @param requestDto
     * @return
     */
    private static RestServiceResponseDto sendRequest(RestServiceRequestDto requestDto, String requestType,
                                                      RequestBody requestBody) {
        RestServiceResponseDto resultDto = new RestServiceResponseDto();
        try {
            // URL
            String url = buildUrlParams(requestDto.getUrl(), requestDto.getUrlParams());
            // 将MAP转换为HEADERS信息
            Headers headers = buildHeaders(requestDto.getHeadersParams());
            // 创建连接
            OkHttpClient client = new OkHttpClient.Builder().readTimeout(REST_READ_TIMEOUT, TimeUnit.MILLISECONDS)// 设置读取超时时间
                                                            .writeTimeout(REST_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)// 设置写的超时时间
                                                            .connectTimeout(REST_CONN_TIMEOUT, TimeUnit.MILLISECONDS)// 设置连接超时时间
                                                            .build();
            // 设置URL,JSON内容，头信息
            Request request = setRequestMethodBuilder(new Request.Builder().url(url).headers(headers), requestType,
                                                      requestBody).build();
            // 返回内容
            Response response = client.newCall(request).execute();
            // 发送成功
            String responseBody = response.body().string();

            int responseCode = response.code();
            if (responseCode >= 200 && responseCode <= 204) {
                // 响应方处理业务成功，返回201
                resultDto.setResultCode(RestServiceResponseDto.SUCCESS);
            } else {
                // 其他未知情况
                resultDto.setResultCode(RestServiceResponseDto.FAIL);
            }
            // HTTP CODE
            resultDto.setHttpCode(responseCode);
            // 成功或者失败原因
            resultDto.setResponseMsg(responseBody);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // 返送失败(如无法连接，断网，500等)
            resultDto.setResultCode(RestServiceResponseDto.FAIL);
            resultDto.setResponseMsg("请求失败");
        }
        return resultDto;
    }

    /**
     * 根据请求方法返回具体的Builder 对象
     * 
     * @author zhangxc
     * @date 2017年1月14日
     * @param builder
     * @param method
     */
    private static okhttp3.Request.Builder setRequestMethodBuilder(okhttp3.Request.Builder builder, String method,
                                                                   RequestBody body) {
        if (method.equals(GET_METHOD_NAME)) {
            return builder.get();
        }
        if (method.equals(POST_METHOD_NAME)) {
            return builder.post(body);
        }
        if (method.equals(PUT_METHOD_NAME)) {
            return builder.put(body);
        }
        if (method.equals(DELETE_METHOD_NAME)) {
            return builder.delete();
        }
        throw new UtilException("请求方法不支持：" + method);
    }

    /**
     * @author zhangxc @ description 把MAP的类型转化为OKHTTP的HEADERS类型
     * @date 2016年11月29日
     * @param headersParams
     * @return
     * @throws Exception
     */
    private static Headers buildHeaders(Map<String, String> headersParams){
        
        okhttp3.Headers.Builder headersbuilder = new okhttp3.Headers.Builder();
        // 循环遍历并解析
        if (!CommonUtils.isNullOrEmpty(headersParams)) {
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                String value = headersParams.get(key);
                headersbuilder.add(key, value);
            }
            // 构建okHttp的Headers
        }
        return headersbuilder.build();
    }

    /**
     * @author zhangxc @ description 替换URL中的{xxx}为XXX
     * @date 2016年12月5日
     * @param urlParams
     * @return
     */
    private static String buildUrlParams(String url, final Map<String, String> urlParams) {
        String urlReplace = url;
        // 对URL中的参数进行替换
        if (!CommonUtils.isNullOrEmpty(urlParams)) {
            // URL后缀
            urlReplace = StringUtils.getMatcherPatternStr(url, "\\{[\\S]+\\}", new RegexReplaceCallBack() {

                @Override
                public String replace(String matcherGroup) {
                    return urlParams.get(matcherGroup);
                }
            });
        }
        return urlReplace;
    }

    /**
     * 根据Map 返回请求参数体
     * 
     * @author zhangxc
     * @date 2017年1月14日
     * @param BodyParams
     * @return
     */
    private static RequestBody getRequestBodyByMap(Map<String, Object> BodyParams) {
        RequestBody body = null;
        if (BodyParams != null) {
            okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
            Iterator<String> iterator = BodyParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                formEncodingBuilder.add(key, BodyParams.get(key).toString());
            }
            body = formEncodingBuilder.build();
        }
        return body;
    }

    /**
     * 加密参数值
     * 
     * @author zhangxc
     * @date 2016年12月8日
     * @param url
     * @param jsonBody
     * @return
     * @throws Exception
     */
    public static String generateEncryptStr(String url, String jsonBody, String cryptKey){
        String md5String = url;
        if (!StringUtils.isNullOrEmpty(jsonBody)) {
            md5String += jsonBody;
        }
        return MD5Util.encrypte(cryptKey, md5String);
    }

}
