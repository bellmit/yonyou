
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartABCDService.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月17日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO 配件ABCD分析
* @author dingchaoyu
* @date 2017年4月17日
*/

public interface PartABCDService {
    
    public PageInfoDto findAll(Map map) throws ServiceBizException;
    
    public void findByid(String id) throws ServiceBizException;
    
    public void updateAll(Map map) throws ServiceBizException;
    
    public List<Map> queryPartInfoForExport(Map map) throws ServiceBizException;
}
