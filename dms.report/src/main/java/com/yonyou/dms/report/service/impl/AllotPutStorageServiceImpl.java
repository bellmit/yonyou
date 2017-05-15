
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.report
 *
 * @File name : AllotPutStorageServiceImpl.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年10月14日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年10月14日    DuPengXin   1.0
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
 * 调拨入库单统计实现类
 * @author DuPengXin
 * @date 2016年10月14日
 */
@Service
public class AllotPutStorageServiceImpl implements AllotPutStorageService{

    /**
     * 查询调拨入库单统计
     * @author DuPengXin
     * @date 2016年10月14日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.AllotPutStorageService#queryAllotPutStorage(java.util.Map)
     */

    @Override
    public PageInfoDto queryAllotPutStorage(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT tpaii.ITEM_ID,tpaii.DEALER_CODE,ts.STORAGE_CODE,ts.STORAGE_NAME,tpaii.STORAGE_POSITION_CODE,tpai.ALLOCATE_IN_NO,tpaii.FINISHED_DATE,tpc.CUSTOMER_CODE,tpc.CUSTOMER_NAME,tpaii.PART_NO,tpaii.PART_NAME,tpaii.IN_PRICE,tpaii.IN_QUANTITY,tpaii.IN_AMOUNT from tt_part_allocate_in_item tpaii LEFT JOIN tt_part_allocate_in tpai ON tpai.PART_ALLOCATE_IN_ID=tpaii.PART_ALLOCATE_IN_ID LEFT JOIN tm_store ts ON tpaii.STORAGE_CODE=ts.STORAGE_CODE LEFT JOIN tm_part_customer tpc ON tpai.CUSTOMER_CODE=tpc.CUSTOMER_CODE where 1=1");
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))){
            sb.append(" and tpc.CUSTOMER_CODE like ?");
            params.add("%"+queryParam.get("customerCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("customerName"))){
            sb.append(" and tpc.CUSTOMER_NAME like ?");
            params.add("%"+queryParam.get("customerName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("dateFrom"))){
            sb.append(" and tpaii.FINISHED_DATE >= ? ");
            params.add(DateUtil.parseDefaultDate(queryParam.get("dateFrom")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("dateTo"))){
            sb.append(" and tpaii.FINISHED_DATE < ? ");
            params.add(DateUtil.addOneDay(queryParam.get("dateTo")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partNo"))){
            sb.append(" and tpaii.PART_NO like ?");
            params.add("%"+queryParam.get("partNo")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partName"))){
            sb.append(" and tpaii.PART_NAME like ?");
            params.add("%"+queryParam.get("partName")+"%");
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(),params);
        return pageInfoDto;
    }

    /**
     * 导出调拨入库单统计
    * @author DuPengXin
    * @date 2016年10月14日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.service.impl.AllotPutStorageService#queryAllotPutStorageExport(java.util.Map)
    */
    	
    @SuppressWarnings("rawtypes")
    @Override
    public List<Map> queryAllotPutStorageExport(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = ("SELECT tpaii.ITEM_ID,tpaii.DEALER_CODE,ts.STORAGE_CODE,ts.STORAGE_NAME,tpaii.STORAGE_POSITION_CODE,tpai.ALLOCATE_IN_NO,tpaii.FINISHED_DATE,tpc.CUSTOMER_CODE,tpc.CUSTOMER_NAME,tpaii.PART_NO,tpaii.PART_NAME,tpaii.IN_PRICE,tpaii.IN_QUANTITY,tpaii.IN_AMOUNT from tt_part_allocate_in_item tpaii LEFT JOIN tt_part_allocate_in tpai ON tpai.PART_ALLOCATE_IN_ID=tpaii.PART_ALLOCATE_IN_ID LEFT JOIN tm_store ts ON tpaii.STORAGE_CODE=ts.STORAGE_CODE LEFT JOIN tm_part_customer tpc ON tpai.CUSTOMER_CODE=tpc.CUSTOMER_CODE where 1=1");
        List<Map> resultList = DAOUtil.findAll(sql,params);
        return resultList;
    }
}
