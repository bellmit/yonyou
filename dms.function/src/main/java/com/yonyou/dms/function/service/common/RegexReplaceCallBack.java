
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : RegexReplaceCallBack.java
*
* @Author : zhangxc
*
* @Date : 2017年1月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月14日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.function.service.common;

/**
* 定义正则表达示替换的回调
* @author zhangxc
* @date 2017年1月14日
*/

public interface RegexReplaceCallBack {

    public String replace(String matcherGroup);
}
