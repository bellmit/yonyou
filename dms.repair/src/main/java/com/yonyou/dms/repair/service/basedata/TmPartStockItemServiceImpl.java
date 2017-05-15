
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : RepairTypeServiceImpl.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.TmPartStockItemDTO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockItemPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author DuPengXin
 * @date 2016年7月27日
 */
@Service
@SuppressWarnings("rawtypes")
public class TmPartStockItemServiceImpl implements TmPartStockItemService {

    @Override
    public List<Map> selectPartStockItem(Map<String, Object> queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("SELECT A.DEALER_CODE as dealerCode,A.PART_BATCH_NO as partBatchNo,A.PART_NO as partNo,A.STORAGE_CODE as storageCode,A.D_KEY as dKey,A.STOCK_QUANTITY as stockQuantity,A.COST_PRICE as costPrice,A.COST_AMOUNT as costAmount FROM TM_PART_STOCK_ITEM A where 1=1 ");
        if(!StringUtils.isNullOrEmpty(queryParams.get("partBatchNo"))){
            sqlSb.append(" AND A.PART_BATCH_NO = ? ");
            params.add(queryParams.get("partBatchNo"));
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("partNo"))){
            sqlSb.append(" AND A.PART_NO = ? ");
            params.add(queryParams.get("partNo"));
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("storageCode"))){
            sqlSb.append(" AND A.STORAGE_CODE = ? ");
            params.add(queryParams.get("storageCode"));
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("dKey"))){
            sqlSb.append(" AND A.D_KEY = ? ");
            params.add(queryParams.get("dKey"));
        }
        return DAOUtil.findAll(sqlSb.toString(), params);
    }
    
    @Override
    public void modifyTmPartStockrItemByParams(String sqlStr, String sqlWhere, List paramsList) throws ServiceBizException {
        TmPartStockItemPO.update(sqlStr, sqlWhere, paramsList);
    }
    
    /**
     * 
    * TODO description
    * @author chenwei
    * @date 2017年4月22日
    * @param partNo
    * @param storageCode
    * @param cudto
    * @throws ServiceBizException
     */
    @Override
    public void modifyByPrimaryId(String partNo, String storageCode, String partBatchNo, TmPartStockItemDTO cudto) throws ServiceBizException {
        TmPartStockItemPO lap = TmPartStockItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
                                                              partNo, storageCode, partBatchNo);
        lap.setDate("LAST_STOCK_OUT", cudto.getLastStockOut());
        lap.saveIt();
        
    }


   
}
