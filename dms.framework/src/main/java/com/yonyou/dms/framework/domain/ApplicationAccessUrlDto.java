/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.

* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.web
*
* @File name : LoginInfoDto.java
*
* @Author : zhangxc
*
* @Date : 2016年6月30日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年6月30日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.framework.domain;

import org.springframework.stereotype.Component;

/*
 * 记录收到权限控制的URL
 * @author  zhangxc
 * @date   2016年11月21号
 */
@Component
public class ApplicationAccessUrlDto {
	
	  /**
     * method:module:url,分隔符使用：FrameworkConstants.ACL_RESOUCCE_SPLIT
     * 
     */
    
    private String[] dafaultUrl;    //获取默认的url    
    

	public String[] getDafaultUrl() {
		return dafaultUrl;
	}

	public void setDafaultUrl(String[] dafaultUrl) {
		this.dafaultUrl = dafaultUrl;
	}
    
    
    

    
    

}
