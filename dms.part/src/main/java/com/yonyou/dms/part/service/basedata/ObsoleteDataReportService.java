
/** 
*Copyright 2017 Yonyou Auto Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : ObsoleteDataReportService.java
*
* @Author : Administrator
*
* @Date : 2017年4月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月13日    Administrator    1.0
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
import com.yonyou.dms.part.domains.DTO.basedata.ObsoleteDataReportDTO;

/**
 * 呆滞品数据上报
 * 
 * @author sunguowei
 * @date 2017年4月13日
 */

public interface ObsoleteDataReportService {

    public PageInfoDto queryObsoleteDataReport(Map<String, String> queryParams) throws ServiceBizException;
    
    public void addObsoleteDataReport(ObsoleteDataReportDTO obsoleteDataReportDTO) throws ServiceBizException;
    
    public PageInfoDto queryObsoleteDataReportById(String id) throws ServiceBizException;
    
    public List<Map> queryStorageCode() throws ServiceBizException;
    
    public Map<String, String> queryContacts() throws ServiceBizException;
    
    public List<Map> exportObsoleteDataReport(Map<String, String> queryParam) throws ServiceBizException;

    
}
