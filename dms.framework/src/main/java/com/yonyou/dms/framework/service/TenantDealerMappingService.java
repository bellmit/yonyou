
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : TenantDealerMappingService.java
*
* @Author : zhangxc
*
* @Date : 2016年11月4日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月4日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.service;

import java.util.List;
import java.util.Map;

/**
* 定时加载Tenanat与Dealer 的Mapping
* @author zhangxc
* @date 2016年11月4日
*/

public interface TenantDealerMappingService {

    public Map<String,Map<String,String>> getAll();
    public Map<String,Map<String,String>> getValid();
    public List<String> getDealersByTenantId(String tenantId);
    public String getTenantIdByDealerCode(String dealerCode);
    
}
