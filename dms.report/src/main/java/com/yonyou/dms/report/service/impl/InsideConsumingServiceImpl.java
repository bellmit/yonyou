
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.report
 *
 * @File name : InsideConsumingServiceImpl.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年10月12日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年10月12日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 内部领用统计实现类
 * @author DuPengXin
 * @date 2016年10月12日
 */
@Service
@SuppressWarnings("rawtypes")
public class InsideConsumingServiceImpl implements InsideConsumingService{


    /**
     * 查询
     * @author DuPengXin
     * @date 2016年10月12日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.InsideConsumingService#queryInsideConsuming(java.util.Map)
     */

    @Override
    public PageInfoDto queryInsideConsuming(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("select tpi.PART_INNER_ID,tpi.DEALER_CODE,ts.STORAGE_NAME,ts.STORAGE_CODE,tpii.PART_NO,tpi.RECEIPT_DATE,tdo.ORG_NAME,te.EMPLOYEE_NAME,tpi.COST_AMOUNT from tt_part_inner tpi  LEFT JOIN tt_part_inner_item tpii ON tpii.PART_INNER_ID=tpi.PART_INNER_ID and tpii.DEALER_CODE=tpi.DEALER_CODE LEFT JOIN tm_store ts ON tpii.STORAGE_CODE=ts.STORAGE_CODE and tpii.DEALER_CODE=ts.DEALER_CODE LEFT JOIN tm_employee te ON tpi.RECEIPTOR=te.EMPLOYEE_NO and tpi.DEALER_CODE=te.DEALER_CODE LEFT JOIN tm_organization tdo ON te.ORG_CODE=tdo.ORG_CODE and te.DEALER_CODE=tdo.DEALER_CODE where 1=1");
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("storageName"))){
            sb.append(" and ts.STORAGE_CODE like ?");
            params.add("%"+queryParam.get("storageName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("dateFrom"))){
            sb.append(" and tpi.RECEIPT_DATE >= ? ");
            params.add(DateUtil.parseDefaultDate(queryParam.get("dateFrom")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("dateTo"))){
            sb.append(" and tpi.RECEIPT_DATE < ? ");
            params.add(DateUtil.addOneDay(queryParam.get("dateTo")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partNo"))){
            sb.append(" and tpii.PART_NO like ?");
            params.add("%"+queryParam.get("partNo")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("orgName"))){
            sb.append(" and tdo.ORG_NAME like ?");
            params.add("%"+queryParam.get("orgName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("employeeName"))){
            sb.append(" and te.EMPLOYEE_NAME like ?");
            params.add("%"+queryParam.get("employeeName")+"%");
        }
       // sb.append(" and GROUP BY tpi.PART_INNER_ID");
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(),params);
        return pageInfoDto;
    }

    /**
     * 查询内部领用统计明细
     * @author DuPengXin
     * @date 2016年10月12日
     * @param id
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.InsideConsumingService#getInsideConsumingItemsById(java.lang.Long)
     */

    @Override
    public List<Map> getInsideConsumingItems(Long id) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT tpi.PART_INNER_ID,tpi.DEALER_CODE,tpii.ITEM_ID,tpi.RECEIPT_NO,e.EMPLOYEE_NAME,tpi.RECEIPTOR,tpii.STORAGE_CODE,ts.STORAGE_NAME,tpii.STORAGE_POSITION_CODE,tpii.PART_NO,tpii.PART_NAME,tpii.OUT_QUANTITY,tpii.COST_PRICE,tpii.COST_AMOUNT  FROM tt_part_inner tpi  LEFT JOIN tm_employee e ON tpi.RECEIPTOR = e.EMPLOYEE_NO and tpi.DEALER_CODE=e.DEALER_CODE LEFT JOIN tt_part_inner_item tpii ON tpi.PART_INNER_ID = tpii.PART_INNER_ID and tpi.DEALER_CODE=tpii.DEALER_CODE LEFT JOIN tm_store ts ON tpii.STORAGE_CODE = ts.STORAGE_CODE and tpii.DEALER_CODE=ts.DEALER_CODE where tpi.PART_INNER_ID=? ");
        List<Object> param = new ArrayList<Object>();
        param.add(id);
        List<Map> list = DAOUtil.findAll(sb.toString(), param);
        return list;
    }

    /**
     * 导出内部领用统计信息
     * @author DuPengXin
     * @date 2016年10月13日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.InsideConsumingService#queryInsideConsumingExport(java.util.Map)
     */

    @Override
    public List<Map> queryInsideConsumingExport(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = ("select tpi.PART_INNER_ID,tpi.DEALER_CODE,ts.STORAGE_NAME,tpii.PART_NO,tpii.FINISHED_DATE,tdo.ORG_NAME,te.EMPLOYEE_NAME,tpi.COST_AMOUNT from tt_part_inner tpi  LEFT JOIN tt_part_inner_item tpii ON tpii.PART_INNER_ID=tpi.PART_INNER_ID and tpii.DEALER_CODE=tpi.DEALER_CODE LEFT JOIN tm_store ts ON tpii.STORAGE_CODE=ts.STORAGE_CODE and tpii.DEALER_CODE=ts.DEALER_CODE LEFT JOIN tm_employee te ON tpi.RECEIPTOR=te.EMPLOYEE_NO and tpi.DEALER_CODE=te.DEALER_CODE LEFT JOIN tm_organization tdo ON te.ORG_CODE=tdo.ORG_CODE and te.DEALER_CODE=tdo.DEALER_CODE where 1=1");
        List<Map> resultList = DAOUtil.findAll(sql,params);
        return resultList;
    }

    /**
     * 导出内部领用统计信息明细
     * @author DuPengXin
     * @date 2016年10月13日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.InsideConsumingService#queryInsideConsumingExportItem(java.util.Map)
     */

    @Override
    public List<Map> queryInsideConsumingExportItems(Long id) throws ServiceBizException {
        String sql = ("SELECT tpi.PART_INNER_ID,tpi.DEALER_CODE,tpi.RECEIPT_NO,e.EMPLOYEE_NAME,tpi.RECEIPTOR,tpii.STORAGE_CODE,ts.STORAGE_NAME,tpii.STORAGE_POSITION_CODE,tpii.PART_NO,tpii.PART_NAME,tpii.OUT_QUANTITY,tpii.COST_PRICE,tpii.COST_AMOUNT  FROM tt_part_inner tpi  left JOIN tm_employee e ON tpi.RECEIPTOR = e.EMPLOYEE_NO LEFT JOIN tt_part_inner_item tpii ON tpi.PART_INNER_ID=tpii.PART_INNER_ID LEFT JOIN tm_store ts ON tpii.STORAGE_CODE=ts.STORAGE_CODE where tpi.PART_INNER_ID=?");
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        List<Map> resultList = DAOUtil.findAll(sql,params);
        return resultList;
    }

}
