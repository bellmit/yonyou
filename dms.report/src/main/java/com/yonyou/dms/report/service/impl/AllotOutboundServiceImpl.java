
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : AllotOutboundServiceImpl.java
*
* @Author : DuPengXin
*
* @Date : 2016年10月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月17日    DuPengXin   1.0
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
* 调拨出库统计实现类
* @author DuPengXin
* @date 2016年10月17日
*/
@Service
public class AllotOutboundServiceImpl implements AllotOutboundService{

    
    /**
     * 查询
    * @author DuPengXin
    * @date 2016年10月17日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.service.impl.AllotOutboundService#queryAllotOutbound(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryAllotOutbound(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("select tpaoi.ITEM_ID,tpaoi.DEALER_CODE,ts.STORAGE_NAME,tpaoi.STORAGE_POSITION_CODE,tpao.ALLOCATE_OUT_NO,tpc.CUSTOMER_CODE,tpc.CUSTOMER_NAME,tpaoi.FINISHED_DATE,tpaoi.PART_NO,tpaoi.PART_NAME,tpaoi.OUT_PRICE,tpaoi.OUT_QUANTITY,tpaoi.OUT_AMOUNT,tpaoi.COST_PRICE,tpaoi.COST_AMOUNT from tt_part_allocate_out_item tpaoi LEFT JOIN tt_part_allocate_out tpao ON tpaoi.ALLOCATE_OUT_ID=tpao.ALLOCATE_OUT_ID and tpaoi.DEALER_CODE=tpao.DEALER_CODE LEFT JOIN tm_store ts ON tpaoi.STORAGE_CODE=ts.STORAGE_CODE and tpaoi.DEALER_CODE=ts.DEALER_CODE LEFT JOIN tm_part_customer tpc ON tpao.CUSTOMER_CODE=tpc.CUSTOMER_CODE and tpao.DEALER_CODE=tpc.DEALER_CODE where 1=1");
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
            sb.append(" and tpaoi.FINISHED_DATE >= ? ");
            params.add(DateUtil.parseDefaultDate(queryParam.get("dateFrom")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("dateTo"))){
            sb.append(" and tpaoi.FINISHED_DATE < ? ");
            params.add(DateUtil.addOneDay(queryParam.get("dateTo")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partNo"))){
            sb.append(" and tpaoi.PART_NO like ?");
            params.add("%"+queryParam.get("partNo")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partName"))){
            sb.append(" and tpaoi.PART_NAME like ?");
            params.add("%"+queryParam.get("partName")+"%");
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(),params);
        return pageInfoDto;
    }

    
    /**
     * 导出
    * @author DuPengXin
    * @date 2016年10月17日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.service.impl.AllotOutboundService#queryAllotOutboundExport(java.util.Map)
    */
    	
    @SuppressWarnings("rawtypes")
    @Override
    public List<Map> queryAllotOutboundExport(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = ("select tpaoi.ITEM_ID,tpaoi.DEALER_CODE,ts.STORAGE_NAME,tpaoi.STORAGE_POSITION_CODE,tpao.ALLOCATE_OUT_NO,tpc.CUSTOMER_CODE,tpc.CUSTOMER_NAME,tpaoi.FINISHED_DATE,tpaoi.PART_NO,tpaoi.PART_NAME,tpaoi.OUT_PRICE,tpaoi.OUT_QUANTITY,tpaoi.OUT_AMOUNT,tpaoi.COST_PRICE,tpaoi.COST_AMOUNT from tt_part_allocate_out_item tpaoi LEFT JOIN tt_part_allocate_out tpao ON tpaoi.ALLOCATE_OUT_ID=tpao.ALLOCATE_OUT_ID and tpaoi.DEALER_CODE=tpao.DEALER_CODE LEFT JOIN tm_store ts ON tpaoi.STORAGE_CODE=ts.STORAGE_CODE and tpaoi.DEALER_CODE=ts.DEALER_CODE LEFT JOIN tm_part_customer tpc ON tpao.CUSTOMER_CODE=tpc.CUSTOMER_CODE and tpao.DEALER_CODE=tpc.DEALER_CODE where 1=1");
        List<Map> resultList = DAOUtil.findAll(sql,params);
        return resultList;
    }

}
