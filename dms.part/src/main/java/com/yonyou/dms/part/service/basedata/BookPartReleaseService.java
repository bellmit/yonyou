
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : BookPartReleaseService.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月24日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月24日    dingchaoyu    1.0
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
import com.yonyou.dms.part.domains.DTO.basedata.BookPartReleaseDTO;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月24日
*/

public interface BookPartReleaseService {
    
    public PageInfoDto queryPartBookingSec(Map map) throws ServiceBizException; 
    
    public PageInfoDto QueryPartObligatedSec(Map map) throws ServiceBizException; 

    public PageInfoDto QueryPartObligatedSecRo(Map map) throws ServiceBizException; 
    
    public List<Map> queryPartBookReleaseForExportall(Map<String, String> map) throws ServiceBizException;
    
    public List<Map> queryPartBookReleaseForExportro(Map<String, String> map) throws ServiceBizException;
    
    public List<Map> queryPartBookReleaseForExportbooking(Map<String, String> map) throws ServiceBizException;
    
    public void performExecute(BookPartReleaseDTO bookPartReleaseDTO) throws ServiceBizException;
}
