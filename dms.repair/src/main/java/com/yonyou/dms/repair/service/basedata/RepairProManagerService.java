
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : RepairProManagerService.java
*
* @Author : rongzoujie
*
* @Date : 2016年8月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月11日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.RepairPartsDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.RepairProManagerDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.SingleCopyDTO;
import com.yonyou.dms.repair.domains.PO.basedata.RepairProManagerPO;

/**
* TODO description
* @author rongzoujie
* @date 2016年8月11日
*/

public interface RepairProManagerService {
    public PageInfoDto queryRepairPros(Map<String, String> queryParam) throws ServiceBizException;
    public List<Map> getModelCode(String modeLabourCode)throws ServiceBizException;
    public Long addRepairPro(RepairProManagerDTO repairProManagerDTO) throws ServiceBizException;
    public void modifyRepairProMng(Long id,RepairProManagerDTO repairProManagerDTO) throws ServiceBizException;
    public RepairProManagerPO getRepairProMngById(Long id) throws ServiceBizException;
    public void deleteRepairMng(Long id)throws ServiceBizException;
    public PageInfoDto queryModeGroupByRepairProMngCode(String repairCode) throws ServiceBizException;
    public void singleCopy(SingleCopyDTO singleCopyDTO)throws ServiceBizException;
    public void totalCopy(String srcLabourGroupCode,String desLabourGroupCode) throws ServiceBizException;
    public PageInfoDto queryRepairProsByTree(String groupCode,String modelGroupCode) throws ServiceBizException;
    public List<Map> queryRepairProForExport(Map<String, String> queryParam) throws ServiceBizException;
    public void setRepairParts(RepairPartsDTO repairPartsDTO) throws ServiceBizException;
    
    PageInfoDto queryPartsByLabour(Long labourId) throws ServiceBizException;
    public void deleteRepairPart(Long id) throws ServiceBizException;
    public List<Map> exportRepairParts(Long labourId) throws ServiceBizException;
    public void addRepairPart(RepairPartsDTO repairPartsDTO);
    
    public List<Map> findByRoLabourByRoId(Long id)throws ServiceBizException;
    public void importRepairPro(RepairProManagerDTO rowDto)throws ServiceBizException;
    
    public void importAddRepairPart(RepairPartsDTO repairPartsDTO)throws ServiceBizException;
    
    
}
