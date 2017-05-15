
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.web
*
* @File name : DictService.java
*
* @Author : rongzoujie
*
* @Date : 2016年7月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月6日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.web.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

public interface DictService {
	public List<Map<String, Object>> queryDicts() throws ServiceBizException;//获取字典所有内容
	List<Map<String, Object>> remoteUrl(Long type) throws ServiceBizException;
	List<Map> queryRegion() throws ServiceBizException;
}
