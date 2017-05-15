/*
* Copyright 2016 Yonyou Auto Information Technology（Shanghai） Co., Ltd. All Rights Reserved.
*
* This software is published under the terms of the YONYOU Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : marketing-dealer-open-api
*
* @File name : RestParamDto.java
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
	
package com.yonyou.dms.function.domains.DTO.http;

import java.util.Map;

/**
*
* @author zhangxc
* @ description 参数封装
* @date 2016年11月29日
*/

public class RestServiceRequestDto {
    //发送消息的对方地址URL
    private String url;
    //消息头
    private Map<String, String> headersParams;
    //URL参数 放置在URL的后面，譬如 ?aaa=1&bbb=2
    private Map<String, String> urlParams;
    //发送消息的JSON内容
    private Object body;
    
    public RestServiceRequestDto() {
        super();
    }
    
    /**
     * 定义请求对象，主要用于Get请求
     * @author zhangxc
     * @date 2016年11月29日
     * @param url
     * @param json
     * @param headersParams
     */
     public RestServiceRequestDto(String url){
         this.url = url;
     }
     
     /**
      * 定义请求对象，主要用于Get请求
      * @author zhangxc
      * @date 2016年11月29日
      * @param url
      * @param json
      * @param headersParams
      */
      public RestServiceRequestDto(String url,Map<String, String> urlParams){
          this.url = url;
          this.urlParams = urlParams;
      }
      
      /**
       * 定义请求对象，主要用于Get请求
       * @author zhangxc
       * @date 2016年11月29日
       * @param url
       * @param json
       * @param headersParams
       */
       public RestServiceRequestDto(String url,Map<String, String> urlParams,Map<String, String> headersParams){
           this.url = url;
           this.urlParams = urlParams;
       }
     
    /**
     * 定义请求对象，主要用于Post请求
     * @author zhangxc
     * @date 2016年11月29日
     * @param url
     * @param json
     * @param headersParams
     */
     public RestServiceRequestDto(String url, Object body){
         this.url = url;
         this.body = body;
     }
     
    /**
    * 定义请求对象，主要用于Post请求
    * @author zhangxc
    * @date 2016年11月29日
    * @param url
    * @param json
    * @param headersParams
    */
    public RestServiceRequestDto(String url, String body, Map<String, String> headersParams){
        this.url = url;
        this.body = body;
        this.headersParams = headersParams;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    
    /**
     * @return the headersParams
     */
    public Map<String, String> getHeadersParams() {
        return headersParams;
    }

    
    /**
     * @param headersParams the headersParams to set
     */
    public void setHeadersParams(Map<String, String> headersParams) {
        this.headersParams = headersParams;
    }
    
    /**
     * @return the urlParams
     */
    public Map<String, String> getUrlParams() {
        return urlParams;
    }
    
    /**
     * @param urlParams the urlParams to set
     */
    public void setUrlParams(Map<String, String> urlParams) {
        this.urlParams = urlParams;
    }

    
    public Object getBody() {
        return body;
    }

    
    public void setBody(Object body) {
        this.body = body;
    }

    
    
}
