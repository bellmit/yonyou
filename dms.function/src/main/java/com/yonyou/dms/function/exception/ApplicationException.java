/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : ServiceAppException.java
*
* @Author : 
*
* @Date : 2016年2月24日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年2月24日                                   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/


package com.yonyou.dms.function.exception;

/**
 * Service层异常统一封装的程序运行异常，不做异常捕捉与处理
 * INTERNAL_SERVER_ERROR
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = -7063395111284988266L;

    public ApplicationException(String msg) {
        super(msg);
    }
    
    public ApplicationException( Exception e){
        super(e);
    }

    
    public ApplicationException(String msg, Exception e){
        super(msg, e);
    }

}
