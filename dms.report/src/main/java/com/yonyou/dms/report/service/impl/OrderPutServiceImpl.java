
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : OrderPutServiceImpl.java
*
* @Author : zhongsw
*
* @Date : 2016年8月25日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月25日    zhongsw    1.0
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
* 实现类
* @author zhongsw
* @date 2016年8月25日
*/ 
@Service
@SuppressWarnings("rawtypes")
public class OrderPutServiceImpl implements OrderPutService {
        
    /**
     * 根据查询条件查询
    * @author zhongsw
    * @date 2016年8月25日
    * @param param
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.controller.OrderPutController#search(java.util.Map)
    */
    @Override
    public PageInfoDto searchOrderPut(Map<String, String> param)throws ServiceBizException{
        StringBuilder sb=new StringBuilder("select tpb.PART_BUY_ID,tpb.DEALER_CODE,tpc.CUSTOMER_CODE,tpc.CUSTOMER_NAME, SUM(tpb.BEFORE_TAX_AMOUNT) as BEFORE_TAX_AMOUNT,SUM(tpb.TOTAL_AMOUNT) as TOTAL_AMOUNT,SUM(tpb.BEFORE_TAX_AMOUNT - tpb.TOTAL_AMOUNT) AS TAX_AMOUNT,tpbi.PART_NO,tpb.ORDER_DATE,tpi.PART_MAIN_TYPE from tt_part_buy tpb LEFT JOIN tm_part_customer tpc ON tpc.CUSTOMER_CODE=tpb.CUSTOMER_CODE and tpc.DEALER_CODE=tpb.DEALER_CODE LEFT JOIN tt_part_buy_item tpbi ON tpbi.PART_BUY_ID=tpb.PART_BUY_ID and tpbi.DEALER_CODE=tpb.DEALER_CODE LEFT JOIN tm_part_info tpi ON tpbi.PART_NO=tpi.PART_CODE and tpbi.DEALER_CODE=tpi.DEALER_CODE where 1=1 ");
        List<Object> queryParam=new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(param.get("customerName"))){
            sb.append(" and tpc.CUSTOMER_NAME like ? ");
            queryParam.add("%"+param.get("customerName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("PartMainType"))){
            sb.append(" and  tpi.PART_MAIN_TYPE = ? ");
            queryParam.add(Integer.parseInt(param.get("PartMainType")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("dateFrom"))){
            sb.append(" and tpb.ORDER_DATE >= ? ");
            queryParam.add(param.get("dateFrom"));
        }
        if(!StringUtils.isNullOrEmpty(param.get("dateTo"))){
            sb.append(" and tpb.ORDER_DATE < ? ");
            queryParam.add(DateUtil.addOneDay(param.get("dateTo")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("customer_code"))){
            sb.append(" and tpc.CUSTOMER_CODE like ? ");
            queryParam.add("%"+(param.get("customer_code"))+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("part_no"))){
            sb.append(" and tpbi.PART_NO like ? ");
            queryParam.add("%"+(param.get("part_no"))+"%");
        }
        sb.append(" GROUP BY tpb.CUSTOMER_CODE DESC ");
        return DAOUtil.pageQuery(sb.toString(), queryParam);
        
    }
    
    /**
     * 导出采购入库统计主表界面信息
     * @author zhongsw
     * @date 2016年7月20日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.manage.service.sample.UserService#queryUsersForExport(java.util.Map)
     */
    @Override
    public List<Map> queryUsersForExport(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = ("select tpb.PART_BUY_ID,tpb.DEALER_CODE,tpc.CUSTOMER_CODE,tpc.CUSTOMER_NAME, SUM(tpb.BEFORE_TAX_AMOUNT) as BEFORE_TAX_AMOUNT,SUM(tpb.TOTAL_AMOUNT) as TOTAL_AMOUNT,SUM(tpb.BEFORE_TAX_AMOUNT - tpb.TOTAL_AMOUNT) AS TAX_AMOUNT from tt_part_buy tpb LEFT JOIN tm_part_customer tpc ON tpc.CUSTOMER_CODE=tpb.CUSTOMER_CODE and tpc.DEALER_CODE=tpb.DEALER_CODE LEFT JOIN tt_part_buy_item tpbi ON tpbi.PART_BUY_ID=tpb.PART_BUY_ID and tpbi.DEALER_CODE=tpb.DEALER_CODE where 1=1 GROUP BY tpb.CUSTOMER_CODE DESC ");
        List<Map> resultList = DAOUtil.findAll(sql,params);
        return resultList;
    }
     
     /**
      * 导出采购入库子表信息
      * @author zhongsw
      * @date 2016年7月20日
      * @param queryParam
      * @return
      * @throws ServiceBizException
      * (non-Javadoc)
      * @see com.yonyou.dms.manage.service.sample.UserService#queryUsersForExport(java.util.Map)
      */
      @Override
      public List<Map> queryUsersForExport2(Map<String, String> queryParam) throws ServiceBizException {
          List<Object> params = new ArrayList<Object>();
          String sql = getQuerySql2(queryParam,params);
          List<Map> resultList = DAOUtil.findAll(sql,params);
          return resultList;
      }
      
      /**
       * 导出
      * 封装SQL 语句
      * @author zhongsw
      * @date 2016年7月20日
      * @param queryParam
      * @param params
      * @return
       */
      private String getQuerySql2(Map<String,String> queryParam,List<Object> params) throws ServiceBizException{
          StringBuilder sqlSb = new StringBuilder("SELECT tpb.PART_BUY_ID,tpb.DEALER_CODE,tpb.STOCK_IN_NO,tpbi.FINISHED_NO,tpbi.STORAGE_CODE,tpbi.STORAGE_POSITION_CODE,tpb.DELIVERY_NO,tpbi.PART_NO,tpbi.PART_NAME,tpbi.IN_QUANTITY,tpbi.CREATED_AT,tpbi.IN_AMOUNT,tpbi.IN_PRICE_TAXED,tpbi.IN_AMOUNT_TAXED,tpi.PART_MAIN_TYPE,tpbi.FINISHED_DATE,tpb.CUSTOMER_CODE,tpc.CONTACTOR_NAME,tpi.FIT_MODEL_CODE,tpbi.FROM_TYPE,tpb.CREATED_BY FROM     TT_PART_BUY tpb INNER JOIN     TT_PART_BUY_ITEM tpbi ON tpbi.PART_BUY_ID=tpb.PART_BUY_ID INNER JOIN      tm_part_info tpi ON tpi.PART_CODE=tpbi.PART_NO AND tpi.DEALER_CODE=tpbi.DEALER_CODE INNER JOIN    TM_PART_CUSTOMER tpc on tpc.CUSTOMER_CODE=tpb.CUSTOMER_CODE  ");
          return sqlSb.toString();
      }
    
    /**
     * 子表明细查询
    * @author zhongsw
    * @date 2016年8月29日
    * @param param
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.service.impl.OrderPutService#searchOrderPut2(java.util.Map)
    */
    	
    @Override
    public PageInfoDto searchOrderPut2(Long id) throws ServiceBizException {
        StringBuffer sql=new StringBuffer("select PART_BUY_ID,DEALER_CODE,CUSTOMER_CODE from TT_PART_BUY where PART_BUY_ID=?");
        List<Object> code=new ArrayList<Object>();
        code.add(id);
        Map map=DAOUtil.findFirst(sql.toString(), code);
        String s=map.get("CUSTOMER_CODE").toString();
        StringBuilder sqlSb = new StringBuilder("select tpbi.ITEM_ID,tpbi.DEALER_CODE,tpb.STOCK_IN_NO,ts.STORAGE_CODE,ts.STORAGE_NAME,tpbi.STORAGE_POSITION_CODE,tpb.DELIVERY_NO,tpbi.PART_NO,tpbi.PART_NAME,tpbi.IN_QUANTITY,tpbi.IN_PRICE,tpbi.IN_AMOUNT,tpbi.IN_PRICE_TAXED,tpi.PART_MAIN_TYPE,tpbi.FINISHED_DATE,tpc.CUSTOMER_NAME,tpc.CUSTOMER_CODE,tpi.FIT_MODEL_CODE,tpbi.FROM_TYPE,te.EMPLOYEE_NAME from tt_part_buy_item tpbi LEFT JOIN tt_part_buy tpb ON tpbi.PART_BUY_ID=tpb.PART_BUY_ID and tpbi.DEALER_CODE=tpb.DEALER_CODE LEFT JOIN tm_store ts ON tpbi.STORAGE_CODE=ts.STORAGE_CODE and tpbi.DEALER_CODE=ts.DEALER_CODE LEFT JOIN tm_part_info tpi ON tpi.PART_CODE=tpbi.PART_NO LEFT JOIN tm_employee te ON te.CREATED_BY=tpbi.CREATED_BY and te.DEALER_CODE=tpb.DEALER_CODE JOIN tm_part_customer tpc ON tpb.CUSTOMER_CODE=tpc.CUSTOMER_CODE and tpb.DEALER_CODE=tpc.DEALER_CODE where 1=1 and tpc.CUSTOMER_CODE=? GROUP BY tpbi.ITEM_ID");
        List<Object> list=new ArrayList<Object>();
        list.add(s);
        return DAOUtil.pageQuery(sqlSb.toString(), list);
    }
}
