/*
* Copyright 2016 Yonyou Auto Information Technology（Shanghai） Co., Ltd. All Rights Reserved.
*
* This software is published under the terms of the YONYOU Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : marketing-dealer-open-api
*
* @File name : RestServiceConstant.java
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
	
package com.yonyou.dms.function.common;


/**
*
* @author zhangxc
* @ description REST常量
* @date 2016年11月29日
*/

public class RestServiceConstant {
    
    //REST放置的内容
    public static final String REST_BUSINESS_MSG = "REST_BUSINESS_MSG";
    
    //REST处理成功/失败
    public static final String REST_BUSINESS_STATUS = "REST_BUSINESS_STATUS";
    
    //RESPONSE HEADERS存放的返回内容
    public static final String RESPONSE_HEADER_CONTENT = "RESPONSE_HEADER_CONTENT";
    //REQUEST信息中加密微信ID,经销商CODE
    public static final String REQUEST_URL_BODY_MD5_CODE = "SECURE_MD5_CODE";
}
