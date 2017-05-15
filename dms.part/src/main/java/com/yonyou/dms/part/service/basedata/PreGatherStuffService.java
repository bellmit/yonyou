
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PreGatherStuffService.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月27日    dingchaoyu    1.0
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
* TODO description
* @author dingchaoyu
* @date 2017年4月27日
*/

public interface PreGatherStuffService {
    
    public PageInfoDto queryPick(Map map) throws ServiceBizException;
    
    public List<Map> queryPreGatherStuffExport(Map map) throws ServiceBizException;
    
    public List<Map> queryPreGatherStuffDetailExport(Map map) throws ServiceBizException;
    
    public PageInfoDto QueryPartPickDetail(String id) throws ServiceBizException;

}
