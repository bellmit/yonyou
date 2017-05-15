
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : OverFlowLossServiceImpl.java
*
* @Author : DuPengXin
*
* @Date : 2016年10月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月11日    DuPengXin   1.0
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
* 报溢报损实现类
* @author DuPengXin
* @date 2016年10月11日
*/
@Service
@SuppressWarnings("rawtypes")
public class OverFlowLossServiceImpl implements OverFlowLossService{

    
    /**
     * 查询
    * @author DuPengXin
    * @date 2016年10月14日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.service.impl.OverFlowLossService#queryOverFlowLoss(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryOverFlowLoss(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("select tppi.ITEM_ID,tpp.DEALER_CODE,ts.STORAGE_NAME,ts.STORAGE_CODE,tppi.STORAGE_POSITION_CODE,tppi.PART_NO,tppi.PART_NAME,tps.STOCK_QUANTITY,tppi.COST_PRICE,tpli.LOSS_AMOUNT,tpli.LOSS_QUANTITY,tppi.PROFIT_PRICE,tppi.PROFIT_QUANTITY,tpp.ORDER_DATE from tt_part_profit_item tppi LEFT JOIN tt_part_profit tpp ON tppi.PART_PROFIT_ID=tpp.PART_PROFIT_ID and tppi.DEALER_CODE=tpp.DEALER_CODE LEFT JOIN tm_store ts ON tppi.STORAGE_CODE=ts.STORAGE_CODE and tppi.DEALER_CODE=ts.DEALER_CODE LEFT JOIN tt_part_loss_item tpli ON tppi.STORAGE_CODE=tpli.STORAGE_CODE and tppi.DEALER_CODE=tpli.DEALER_CODE LEFT JOIN tt_part_stock tps ON tppi.STORAGE_POSITION_CODE=tps.STORAGE_POSITION_CODE and tppi.DEALER_CODE=tps.DEALER_CODE where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("storageCode"))){
            sb.append(" and ts.STORAGE_CODE like ?");
            params.add("%"+queryParam.get("storageCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("dateFrom"))){
            sb.append(" and tpp.ORDER_DATE >= ? ");
            params.add(DateUtil.parseDefaultDate(queryParam.get("dateFrom")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("dateTo"))){
            sb.append(" and tpp.ORDER_DATE < ? ");
            params.add(DateUtil.addOneDay(queryParam.get("dateTo")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partNo"))){
            sb.append(" and tppi.PART_NO like ?");
            params.add("%"+queryParam.get("partNo")+"%");
        }
        sb.append(" GROUP BY tppi.ITEM_ID DESC ");
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(),params);
        return pageInfoDto;
    }

    
    /**
     * 导出
    * @author DuPengXin
    * @date 2016年10月12日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.service.impl.OverFlowLossService#queryOverFlowLossExport(java.util.Map)
    */
    	
    @Override
    public List<Map> queryOverFlowLossExport(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = ("select tpli.ITEM_ID,tpli.DEALER_CODE,tpli.STORAGE_CODE,ts.STORAGE_NAME,tpli.STORAGE_POSITION_CODE,tpli.PART_NO as PART_NO,tpli.PART_NAME,tps.STOCK_QUANTITY,tps.COST_PRICE as price,tpli.LOSS_AMOUNT,tpli.LOSS_QUANTITY,'' as PROFIT_AMOUNT,'' as PROFIT_QUANTITY,tpl.ORDER_DATE as ORDER_DATE from TT_PART_LOSS_ITEM tpli left join tt_part_stock tps on PART_NO = tps.PART_CODE and tpli.STORAGE_CODE = tps.STORAGE_CODE LEFT JOIN tt_part_loss tpl ON tpli.PART_LOSS_ID=tpl.PART_LOSS_ID LEFT JOIN tm_store ts ON tpli.STORAGE_CODE=ts.STORAGE_CODE UNION select tppi.PART_PROFIT_ID,tppi.DEALER_CODE,tppi.STORAGE_CODE,ts.STORAGE_NAME,tppi.STORAGE_POSITION_CODE,tppi.PART_NO as PART_NO,tppi.PART_NAME,tps.STOCK_QUANTITY,tps.COST_PRICE as price,'' as LOSS_AMOUNT,'' as LOSS_QUANTITY ,tppi.PROFIT_AMOUNT,tppi.PROFIT_QUANTITY,tpp.ORDER_DATE as ORDER_DATE from TT_PART_PROFIT_ITEM tppi left join tt_part_stock tps on PART_NO = tps.PART_CODE and tppi.STORAGE_CODE = tps.STORAGE_CODE LEFT JOIN tt_part_profit tpp ON tppi.PART_PROFIT_ID=tpp.PART_PROFIT_ID  LEFT JOIN tm_store ts ON tppi.STORAGE_CODE=ts.STORAGE_CODE where 1=1");
        List<Map> resultList = DAOUtil.findAll(sql,params);
        return resultList;
    }
}
