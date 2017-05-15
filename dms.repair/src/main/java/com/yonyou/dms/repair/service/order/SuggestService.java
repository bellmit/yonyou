
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : SuggestService.java
*
* @Author : jcsi
*
* @Date : 2016年9月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月9日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.order;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.order.SuggestDTO;

/**
* 维修建议  （接口）
* @author jcsi
* @date 2016年9月9日
*/

public interface SuggestService {

    public Long saveSuggest(SuggestDTO suggest)throws ServiceBizException;
    
    public List<Map> findSuggestLabour(String vin)throws ServiceBizException;
    
    public List<Map> findSuggestPart(String vin)throws ServiceBizException;
    
    public Long editSuggest(SuggestDTO suggest)throws ServiceBizException;
    
}
