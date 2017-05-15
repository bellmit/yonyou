
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartInventoryService.java
*
* @Author : ZhengHe
*
* @Date : 2016年7月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月26日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TtPartInventoryPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.PartCodesDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartInventoryDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartInventoryItemDTO;
import com.yonyou.dms.part.domains.PO.basedata.PartInventoryItemPO;

/**
* 配件盘点单Service
* @author ZhengHe
* @date 2016年7月26日
*/

public interface PartInventoryService {
    public PageInfoDto queryPartInventory(Map<String, String> queryParam) throws ServiceBizException;
    public PageInfoDto queryPartInventoryItem(Map<String, String> queryParam)throws ServiceBizException;
    public TtPartInventoryPO addPartInventory(List<PartCodesDTO> partCodes,PartInventoryDTO piDto,String systemOrderNo)throws ServiceBizException;
    public TtPartInventoryPO queryPartInventoryById(Long id)throws ServiceBizException;
    public PageInfoDto queryPartInventoryItemById(Long id,Map<String, String> queryParam)throws ServiceBizException;
    public void modifyPartInventory(String status,PartInventoryDTO piDto)throws ServiceBizException;
    public Double getAllStockQuantity()throws ServiceBizException;
    public Double getStockQuantity(List<PartCodesDTO> partCodes)throws ServiceBizException;
    public void deletePartInventoryById(Long id)throws ServiceBizException;
    public void deletePartInventoryItemById(Long inventoryId,Long id)throws ServiceBizException;
    public PartInventoryItemPO queryPartInventoryByItemId(Long inventoryId,Long id)throws ServiceBizException;
    public void modifyPartInventoryItem(Long inventoryId,Long id,PartInventoryItemDTO piiDto)throws ServiceBizException;
    public List<Map> qryInventoryItemById(Long id) throws ServiceBizException;
    public List<Map> queryItem(Long id,Map<String, String> queryParam)throws ServiceBizException;
    public void addPartInventoryById(Long id,List<PartCodesDTO> partCodes,PartInventoryDTO piDto)throws ServiceBizException;
    //获取打印信息--配件
    public List<Map> queryPartInventoryItemPrintById(Long id)throws ServiceBizException;
}
