
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : NewSalesOrderServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2017年2月20日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月20日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * *新增订单简表
 * 
 * @author zhanshiwei
 * @date 2017年2月20日
 */
@Service
public class NewSalesOrderServiceImpl implements NewSalesOrderService {

    @Autowired
    private RemainingOrderDetailService remainingOrderDetailService;

    /**
     * 车型查询
     * 
     * @author zhanshiwei
     * @date 2017年2月20日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.NewSalesOrderService#queryNewSalesOrdre(java.util.Map)
     */

    @Override
    public PageInfoDto queryNewSalesOrdre(Map<String, String> queryParam) throws ServiceBizException {
    	 StringBuffer sql = new StringBuffer();
    	 StringBuffer sql1 = new StringBuffer();
         getsql(queryParam, sql);
         getrow(queryParam, sql1);
         List<Object> params = new ArrayList<>();
         List<Object> params1 = new ArrayList<>();
         PageInfoDto id = DAOUtil.pageQuery(sql1.toString(), params1);
         List<Map> map=DAOUtil.findAll(sql.toString(), params);
         List<Map> records = getRecords(map, "SOLD_BY", "VEHICLE_CODE", "NEW_ORDER_TOTAL",queryParam);
         id.setRows(records);
         return id;
    }

    @SuppressWarnings("unchecked")
	public List<Map> getRecords(List<Map> list, String sPKF, String sFKF, String sVF,Map<String,String> qeryparam) {
        List<Map> results = new ArrayList<Map>();

        if (list instanceof List && list.size() > 0) {
            Map record, preRecord, rowDataCatch = new HashMap();
            Set<String> colSet = getColSet(list, sFKF, sVF,qeryparam);
            Object pk = list.get(0).get(sPKF);

            for (int i = 0, len = list.size(); i < len; i++) {
                record = list.get(i);

                // 当主键发生跳变或最后一条记录时，行转列输出
                if (i == len - 1 || !record.get(sPKF).equals(pk)) {
                    /*
                     * 若最后一行触发的输出，提前写入缓存。因为写缓存 操作发生在判断之后，若不这样最后一条数据会被漏掉
                     */
                    if (i == len - 1) {
                        rowDataCatch.put(record.get(sFKF), record.get(sVF));
                    }
                    preRecord = list.get(i - 1);
                    preRecord.remove(sFKF);
                    preRecord.remove(sVF);
                    preRecord.putAll(rowDataCatch);

                    addRecord(results, colSet, preRecord);

                    // 重新设置主键标记和行数据缓存
                    rowDataCatch.clear();
                    pk = record.get(sPKF);
                }

                // 将外键值值字段写入行数据缓存
                if (i < len - 1) {
                    rowDataCatch.put(record.get(sFKF), record.get(sVF));
                }
                if (StringUtils.isNullOrEmpty(rowDataCatch.get("comm"))) {
                    rowDataCatch.put("comm", record.get(sVF));
                } else {
                    rowDataCatch.put("comm", Integer.parseInt(record.get(sVF).toString())
                                             + Integer.parseInt(rowDataCatch.get("comm").toString()));
                }
            }
        }

        return results;
    }

    /**
     * 统计所有输出列
     * 
     * @param list 数据列表
     * @param sFKF 外键字段名称
     * @param sVF 值字段名称
     * @return
     */
    private Set<String> getColSet(List<Map> list, String sFKF, String sVF,Map<String,String> qeryparam) {

        Set<String> colSet = new HashSet<String>();

        if (list instanceof List && list.size() > 0) {
            Map record = list.get(0);

            // 非变化字段集合
            for (Object key : record.keySet()) {
                if (!key.equals(sFKF) && !key.equals(sVF)) colSet.add(key.toString());
            }

            /*
             * // 变化字段集合 for (Map item : list) { colSet.add((String) item.get(sFKF)); }
             */
        //    qeryparam.put("isCheck", "50331001");
            List<Map> results = remainingOrderDetailService.queryModel(qeryparam);
            for (Map item : results) {
                colSet.add((String) item.get("CODE"));
            }
        }

        return colSet;
    }

    /**
     * 将行数据进行列补齐操作后写入输出列表
     * 
     * @param list
     * @param colSet
     * @param rowData
     */
    private static void addRecord(List<Map> list, Set<String> colSet, Map rowData) {
        // 补齐不存在的列
        for (String column : colSet) {
            if (!rowData.containsKey(column)) {
                rowData.put(column, "0");
            }
        }

        list.add(rowData);
    }

    /**
     * 车系查询
     * 
     * @author zhanshiwei
     * @date 2017年2月20日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.NewSalesOrderService#queryNewSalesOrdreSeries(java.util.Map)
     */

    @Override
    public PageInfoDto queryNewSalesOrdreSeries(Map<String, String> queryParam) throws ServiceBizException {
    	 StringBuffer sql = new StringBuffer();
         StringBuffer sql1 = new StringBuffer();
         getsql(queryParam, sql);
         getrow(queryParam, sql1);
         List<Object> params = new ArrayList<>();
         List<Object> params1 = new ArrayList<>();
         PageInfoDto id = DAOUtil.pageQuery(sql1.toString(), params1);
         //params.add(id.getRows().size());
         /*params.add(0);
         params.add(2);*/
         List<Map> map=DAOUtil.findAll(sql.toString(), params);
         List<Map> records = getRecords(map, "SOLD_BY", "VEHICLE_CODE", "NEW_ORDER_TOTAL",queryParam);
         id.setRows(records);
         return id;
    }

    /**
     * sql
     * 
     * @author zhanshiwei
     * @date 2017年2月21日
     * @param queryParam
     * @param sql
     */

    public void getsql(Map<String, String> queryParam, StringBuffer sql) {
        String begindate1 = queryParam.get("yearMonth");
        String beginDate = begindate1 + "-01 00:00:00";// 本月开始
        String endDate = DateUtil.formatDefaultDate(DateUtil.getLastDayOfMonth(DateUtil.parseDefaultDate(beginDate)))
                         + " 23:59:59";// 本月结束
        String soldby = queryParam.get("soldBy");// Long.parseLong();//销售顾问
        if (StringUtils.isEquals(queryParam.get("isCheck"), "50331001")) {
            sql.append(" SELECT A.DEALER_CODE,A.SOLD_BY,tu.USER_NAME,B.SERIES_CODE AS VEHICLE_CODE,COUNT(1) AS NEW_ORDER_TOTAL FROM TT_SALES_ORDER A ");
            sql.append(" LEFT JOIN TM_USER tu ON A.SOLD_BY=tu.USER_ID AND A.DEALER_CODE=tu.DEALER_CODE\n");
            sql.append( " LEFT JOIN TM_VS_PRODUCT B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE  ");
            sql.append( " WHERE A.BUSINESS_TYPE = 13001001 AND A.SO_STATUS NOT IN (13011055,13011060) AND A.PRODUCT_CODE IS NOT NULL  ");
            sql.append( Utility.getDateCond("A", "CREATED_AT", beginDate, endDate));
            if (!StringUtils.isNullOrEmpty(soldby)) {
                sql.append(" AND A.SOLD_BY=" + soldby + "");
            }
            sql.append(" GROUP BY A.SOLD_BY,B.SERIES_CODE ORDER BY A.SOLD_BY  ");
        } else {
            sql.append(" SELECT A.DEALER_CODE,A.SOLD_BY,tu.USER_NAME,B.MODEL_CODE AS VEHICLE_CODE,COUNT(1) AS NEW_ORDER_TOTAL FROM TT_SALES_ORDER A ");
            		sql.append( " LEFT JOIN TM_VS_PRODUCT B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE  ");
                       sql.append(" LEFT JOIN TM_USER tu ON A.SOLD_BY=tu.USER_ID AND A.DEALER_CODE=tu.DEALER_CODE\n");
                       sql.append(" WHERE A.BUSINESS_TYPE = 13001001 AND A.SO_STATUS NOT IN (13011055,13011060) AND A.PRODUCT_CODE IS NOT NULL  ");
                    		   sql.append( Utility.getDateCond("A", "CREATED_AT", beginDate, endDate));
            if (!StringUtils.isNullOrEmpty(soldby)) {
                sql.append(" AND A.SOLD_BY=" + soldby + "");
            }
            sql.append(" GROUP BY A.SOLD_BY,B.MODEL_CODE ORDER BY A.SOLD_BY ");
        }
    }
    
    /**
     * sql
     * 
     * @author zhanshiwei
     * @date 2017年2月21日
     * @param queryParam
     * @param sql
     */

    public void getrow(Map<String, String> queryParam, StringBuffer sql) {
        String begindate1 = queryParam.get("yearMonth");
        String beginDate = begindate1 + "-01 00:00:00";// 本月开始
        String endDate = DateUtil.formatDefaultDate(DateUtil.getLastDayOfMonth(DateUtil.parseDefaultDate(beginDate)))
                         + " 23:59:59";// 本月结束
        String soldby = queryParam.get("soldBy");// Long.parseLong();//销售顾问
        if (StringUtils.isEquals(queryParam.get("isCheck"), "50331001")) {
            sql.append(" SELECT A.DEALER_CODE,A.SOLD_BY,tu.USER_NAME,B.SERIES_CODE AS VEHICLE_CODE,COUNT(1) AS NEW_ORDER_TOTAL FROM TT_SALES_ORDER A ");
            sql.append(" LEFT JOIN TM_USER tu ON A.SOLD_BY=tu.USER_ID AND A.DEALER_CODE=tu.DEALER_CODE\n");
            sql.append( " LEFT JOIN TM_VS_PRODUCT B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE  ");
            sql.append( " WHERE A.BUSINESS_TYPE = 13001001 AND A.SO_STATUS NOT IN (13011055,13011060) AND A.PRODUCT_CODE IS NOT NULL  ");
            sql.append( Utility.getDateCond("A", "CREATED_AT", beginDate, endDate));
            if (!StringUtils.isNullOrEmpty(soldby)) {
                sql.append(" AND A.SOLD_BY=" + soldby + "");
            }
            sql.append(" GROUP BY A.SOLD_BY ORDER BY A.SOLD_BY ");
        } else {
            sql.append(" SELECT A.DEALER_CODE,A.SOLD_BY,tu.USER_NAME,B.MODEL_CODE AS VEHICLE_CODE,COUNT(1) AS NEW_ORDER_TOTAL FROM TT_SALES_ORDER A ");
            		sql.append( " LEFT JOIN TM_VS_PRODUCT B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE  ");
                       sql.append(" LEFT JOIN TM_USER tu ON A.SOLD_BY=tu.USER_ID AND A.DEALER_CODE=tu.DEALER_CODE\n");
                       sql.append(" WHERE A.BUSINESS_TYPE = 13001001 AND A.SO_STATUS NOT IN (13011055,13011060) AND A.PRODUCT_CODE IS NOT NULL  ");
                    		   sql.append( Utility.getDateCond("A", "CREATED_AT", beginDate, endDate));
            if (!StringUtils.isNullOrEmpty(soldby)) {
                sql.append(" AND A.SOLD_BY=" + soldby + "");
            }
            sql.append(" GROUP BY A.SOLD_BY ORDER BY A.SOLD_BY ");
        }
    }

    
    /**
     * 导出
    * @author zhanshiwei
    * @date 2017年2月21日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.service.impl.NewSalesOrderService#queryNewSalesOrdreList(java.util.Map)
    */
    	
    @Override
    public List<Map> queryNewSalesOrdreList(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        getsql(queryParam, sql);
        List<Object> params = new ArrayList<Object>();
        List<Map> id = DAOUtil.findAll(sql.toString(), params);
        List<Map> records = getRecords(id, "SOLD_BY", "VEHICLE_CODE", "NEW_ORDER_TOTAL",queryParam);
        return records;
    }
}
