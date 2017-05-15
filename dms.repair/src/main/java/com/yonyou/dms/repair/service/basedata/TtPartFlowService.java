
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : PartFlowService.java
*
* @Author : DuPengXin
*
* @Date : 2016年8月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月22日    DuPengXin   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.commonAS.domains.DTO.basedata.PartFlowDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 配件流水账查询接口
* @author DuPengXin
* @date 2016年8月22日
*/
@SuppressWarnings("rawtypes")
public interface TtPartFlowService {
    public PageInfoDto queryPartflows(Map<String, String> queryParam) throws ServiceBizException;
    public PageInfoDto queryPartdetails(Map<String, String> queryParam,String storageCode) throws ServiceBizException;
    public List<Map> queryPartdetailExport(Map<String, String> queryParam,String storageCode) throws ServiceBizException;
    public Map queryPartflowsById(Long id) throws ServiceBizException;
    public void addTtPartFlow(PartFlowDTO cudto)throws ServiceBizException;///新增
}
