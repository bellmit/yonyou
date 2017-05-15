
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : RepairTypeService.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年7月27日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月27日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.TmPartStockItemDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.RepairTypeDTO;


/**
 * 配件库存批次信息
* TODO description
* @author chenwei
* @date 2017年4月17日
 */

public interface TmPartStockItemService {
    public List<Map> selectPartStockItem(Map<String, Object> queryParams) throws ServiceBizException;//查询配件库存批次表	
    /**
     * 更新配件库存数量成本单价成本金额
    * TODO description
    * @author chenwei
    * @date 2017年4月22日
    * @param sqlStr
    * @param sqlWhere
    * @throws ServiceBizException
     */
    public void modifyTmPartStockrItemByParams(String sqlStr, String sqlWhere, List paramsList) throws ServiceBizException;
    
    /**
     * 
    * TODO description
    * @author chenwei
    * @date 2017年4月22日
    * @param partNo
    * @param storageCode
    * @param partBatchNo
    * @param cudto
    * @throws ServiceBizException
     */
    public void modifyByPrimaryId(String partNo, String storageCode, String partBatchNo, TmPartStockItemDTO cudto) throws ServiceBizException;
}
