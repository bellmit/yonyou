
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

import com.yonyou.dms.common.domains.DTO.basedata.TmPartStockDTO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.commonAS.domains.DTO.basedata.PartStockDTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 配件库存表操作类
* TODO description
* @author chenwei
* @date 2017年4月21日
 */
@Service
@SuppressWarnings("rawtypes")
public class TmPartStockServiceImpl implements TmPartStockService {

    @Override
    public List<Map> selectPartStock(Map<String, Object> queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("SELECT A.LOCKED_QUANTITY as lockedQuantity, A.DEALER_CODE as dealerCode,A.PART_NO as partNo,A.STORAGE_CODE as storageCode,A.D_KEY as dKey,A.STOCK_QUANTITY as stockQuantity,A.COST_PRICE as costPrice,A.COST_AMOUNT as costAmount FROM TM_PART_STOCK A where 1=1 ");
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
    public int modifyTmPartStockrByParams(String sqlStr, String sqlWhere) throws ServiceBizException {
        int record = TmPartStockPO.update(sqlStr, sqlWhere, new ArrayList());
        return record;
    }

    @Override
    public void modifyByItemId(String partNo, String storageCode, TmPartStockDTO cudto) throws ServiceBizException {
        TmPartStockPO lap = TmPartStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
                                                              partNo,storageCode);
        lap.setDate("LAST_STOCK_OUT", cudto.getLastStockOut());
        lap.setDate("SLOW_MOVING_DATE", cudto.getSlowMovingDate());
        lap.saveIt();
        
    }

    @Override
    public List<TmPartStockDTO> changeMapToTmPartStockDTO(List<Map> list) {
        List<TmPartStockDTO> dtoList = new ArrayList<TmPartStockDTO>();
        if(null != list && list.size() > 0){
            for(int i = 0; i<list.size(); i++){
                Map map = list.get(i);
                TmPartStockDTO dto = new TmPartStockDTO();
                if(!StringUtils.isNullOrEmpty(map.get("lockedQuantity")))
                    dto.setLockedQuantity(Float.valueOf(map.get("lockedQuantity").toString()));
                dtoList.add(dto);
            }
            return dtoList;
        }else{
            return dtoList;
        }
    }


   
}
