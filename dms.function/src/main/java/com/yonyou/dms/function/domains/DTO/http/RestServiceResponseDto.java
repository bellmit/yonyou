/*
* Copyright 2016 Yonyou Auto Information Technology（Shanghai） Co., Ltd. All Rights Reserved.
*
* This software is published under the terms of the YONYOU Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : marketing-dealer-open-api
*
* @File name : RestResultDto.java
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


/**
* 定义请求rest接口的返回对象
* @author zhangxc
* @date 2016年11月29日
*/

public class RestServiceResponseDto {
    
    public final static int SUCCESS = 1;
    public final static int FAIL = 0;
    //0:失败 1:成功
    private int resultCode = SUCCESS;
    //返回的HTTP状态
    private int httpCode;
    //返回消息(成功，或者错误原因)
    private String responseMsg;
    /**
     * @return the resultCode
     */
    public int getResultCode() {
        return resultCode;
    }
    
    /**
     * @param resultCode the resultCode to set
     */
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
    
    
    /**
     * @return the httpCode
     */
    public int getHttpCode() {
        return httpCode;
    }

    
    /**
     * @param httpCode the httpCode to set
     */
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    
    public String getResponseMsg() {
        return responseMsg;
    }

    
    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
    
}
