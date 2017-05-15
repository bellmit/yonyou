/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : AuthException.java
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
 * auth ex to return 401
*
* @author zhangxianchao
* TODO description
* @date 2016年4月21日
 */
public class AuthLoginOutException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AuthLoginOutException(String msg) {
		super(msg);
	}

	public AuthLoginOutException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
