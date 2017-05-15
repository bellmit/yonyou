
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

import com.yonyou.dms.common.domains.DTO.basedata.TmPartStockDTO;
import com.yonyou.dms.commonAS.domains.DTO.basedata.PartStockDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 配件库存批次信息
* TODO description
* @author chenwei
* @date 2017年4月17日
 */

public interface TmPartStockService {
    public List<Map> selectPartStock(Map<String, Object> queryParams) throws ServiceBizException;//查询配件库存表
    
    public List<TmPartStockDTO> changeMapToTmPartStockDTO(List<Map> queryParams);
    /**
     * 更新配件库存数量成本单价成本金额
    * TODO description
    * @author chenwei
    * @date 2017年4月22日
    * @param sqlStr
    * @param sqlWhere
    * @throws ServiceBizException
     */
    public int modifyTmPartStockrByParams(String sqlStr, String sqlWhere) throws ServiceBizException;//
    
    /**
     * 根据主键修改配件库存表
    * TODO description
    * @author chenwei
    * @date 2017年4月22日
    * @param ItemId
    * @param cudto
    * @throws ServiceBizException
     */
    public void modifyByItemId(String partNo, String storageCode,TmPartStockDTO cudto) throws ServiceBizException;
}
