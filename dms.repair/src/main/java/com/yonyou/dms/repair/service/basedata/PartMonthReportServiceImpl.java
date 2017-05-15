
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : AppendProjectServiceImpl.java
*
* @Author : zhongsw
*
* @Date : 2016年8月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月19日    zhongsw    1.0
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

import com.yonyou.dms.common.domains.PO.basedata.TtPartMonthReportPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.TtPartMonthReportDTO;

/**
 * 
* TODO description
* @author chenwei
* @date 2017年4月24日
 */
@Service
@SuppressWarnings("rawtypes")
public class PartMonthReportServiceImpl implements PartMonthReportService {

    @Override
    public TtPartMonthReportPO findByPrimaryKeys(String reportYear, String reportMonth, String storageCode, String partBatchNo, String partNo) throws ServiceBizException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageInfoDto searchPartMonthReport(Map<String, Object> queryParam) throws ServiceBizException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long addPartMonthReport(TtPartMonthReportDTO modeldto) throws ServiceBizException {
     // TODO Auto-generated method stub
        TtPartMonthReportPO typo = new TtPartMonthReportPO();
        setRoRepairPart(typo, modeldto);
        typo.saveIt();
        return typo.getLongId();
    }
    
    /**
     * 设置TtPartMonthReportPO属性
     * 
     * @author chenwei
     * @date 2017年4月24日
     * @param typo
     * @param pyto
     */
    public void setRoRepairPart(TtPartMonthReportPO typo, TtPartMonthReportDTO pyto) {
        typo.setString("REPORT_YEAR", pyto.getReportYear());
        typo.setString("REPORT_MONTH", pyto.getReportMonth());
        typo.setFloat("IN_QUANTITY", pyto.getInQuantity());
        typo.setDouble("STOCK_IN_AMOUNT", pyto.getStockInAmount());
        typo.setFloat("INVENTORY_QUANTITY", pyto.getInventoryQuantity());
        typo.setDouble("INVENTORY_AMOUNT", pyto.getInventoryAmount());
        typo.setFloat("CLOSE_QUANTITY", pyto.getCloseQuantity());
        typo.setFloat("CLOSE_AMOUNT", pyto.getCloseAmount());
        typo.setString("PART_BATCH_NO", pyto.getPartBatchNo());
        typo.setString("PART_NO", pyto.getPartNo());
        typo.setString("PART_NAME", pyto.getPartName());
        typo.setString("STORAGE_CODE", pyto.getStorageCode());
        typo.setFloat("OUT_QUANTITY", pyto.getOutQuantity());
        typo.setDouble("OUT_AMOUNT", pyto.getOutAmount());
        typo.setFloat("OPEN_QUANTITY", pyto.getOpenQuantity());
        typo.setDouble("OPEN_PRICE", pyto.getOpenPrice());
        typo.setDouble("OPEN_AMOUNT", pyto.getOpenAmount());
        typo.setDouble("CLOSE_PRICE", pyto.getClosePrice());
    }

    @Override
    public int updateModel(String sqlStr, String sqlWhere, List paramsList) throws ServiceBizException {
        //TtPartMonthReportPO lap = TtPartMonthReportPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode()
        //                                                                  , reportYear, reportMonth, storageCode, partBatchNo, partNo);
        //lap.setString("PART_NAME", modeldto.getPartName());
        //lap.setDouble("OUT_QUANTITY", Math.round(modeldto.getOutQuantity() * 100) * 0.01);
        //lap.setDouble("OUT_AMOUNT", Math.round(modeldto.getOutAmount() * 100) * 0.01);
        //lap.setFloat("OPEN_QUANTITY", modeldto.getOpenQuantity());
        //lap.setDouble("OPEN_PRICE", modeldto.getOpenPrice());
        //lap.setDouble("OPEN_AMOUNT", modeldto.getOpenAmount());
        //lap.setDouble("CLOSE_PRICE", modeldto.getClosePrice());
        //lap.setString("REPORT_YEAR", modeldto.getReportYear());
        //lap.setString("REPORT_MONTH", modeldto.getReportMonth());
        //lap.saveIt();
        int record = TtPartMonthReportPO.update(sqlStr, sqlWhere, paramsList);
        return record;
    }

    @Override
    public List<Map> selectPartMonthReport(TtPartMonthReportDTO queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("SELECT ENTITY_CODE FROM TT_PART_MONTH_REPORT where 1=1 ");
        if(!StringUtils.isNullOrEmpty(queryParams.getStorageCode())){
            sqlSb.append("AND STORAGE_CODE = ? ");
            params.add(queryParams.getStorageCode());
        }
        if(!StringUtils.isNullOrEmpty(queryParams.getPartBatchNo())){
            sqlSb.append("AND PART_BATCH_NO = ? ");
            params.add(queryParams.getPartBatchNo());
        }
        if(!StringUtils.isNullOrEmpty(queryParams.getPartNo())){
            sqlSb.append("AND PART_NO = ? ");
            params.add(queryParams.getPartNo());
        }
        if(!StringUtils.isNullOrEmpty(queryParams.getReportYear())){
            sqlSb.append("AND REPORT_YEAR = ? ");
            params.add("'" + queryParams.getReportYear() + "'");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.getReportMonth())){
            sqlSb.append("AND REPORT_MONTH = ? ");
            params.add("'" + queryParams.getReportMonth() + "'");
        }
        sqlSb.append(" FOR UPDATE ");
        return DAOUtil.findAll(sqlSb.toString(), params);
    }
    
    

}
