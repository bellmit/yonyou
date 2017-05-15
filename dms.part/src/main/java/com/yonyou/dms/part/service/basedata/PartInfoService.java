
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartInfoService.java
*
* @Author : xukl
*
* @Date : 2016年7月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月5日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.PartInfoDTO;


/**
* 配件基础信息service
* @author xukl
* @date 2016年7月5日
*/

@SuppressWarnings("rawtypes")
public interface PartInfoService {

    public PageInfoDto queryPartInfos(Map<String, String> queryParam) throws ServiceBizException;

    public TmPartInfoPO getPartInfoById(String id) throws ServiceBizException;

    public String addPartInfo(PartInfoDTO partInfoDTO) throws ServiceBizException;

    public void deletePartInfoById(String id) throws ServiceBizException;

    public void modifyPartInfo(String id, PartInfoDTO partInfoDTO) throws ServiceBizException;

    public List<Map> queryPartInfoForExport(Map<String, String> queryParam) throws ServiceBizException;
    
    public List<Map> queryPartInfoList(Map<String, Object> queryParam) throws ServiceBizException;

    public PageInfoDto qryPartInfos(Map<String, String> queryParam) throws ServiceBizException;
    
    public List<Map> searchByPartCode(String partCode)throws ServiceBizException;
    
    public List<Map> queryUnitCode(Map<String, String> queryParam)throws ServiceBizException;
    
    public List<Map> queryPartGroupCodes(Map<String, String> queryParam)throws ServiceBizException;
    
    public PageInfoDto queryPartGroupCode(Map<String, String> queryParam)throws ServiceBizException;
    
    public PageInfoDto queryModel(Map<String, String> queryParam)throws ServiceBizException;
}
