
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : AbsentDetailService.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月26日    dingchaoyu    1.0
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
import com.yonyou.dms.part.domains.DTO.basedata.TtShortPartDTO;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月26日
*/

public interface AbsentDetailService {
    
    public PageInfoDto queryShortPart(Map map)throws ServiceBizException;
    
    public List<Map> queryShortPartImport(Map map)throws ServiceBizException;
    
    public void save(TtShortPartDTO dto)throws ServiceBizException;

}
