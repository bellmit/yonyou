
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : SalesMarginServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2017年2月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月7日    zhanshiwei    1.0
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 销售毛利
* @author zhanshiwei
* @date 2017年2月7日
*/
@Service
public class SalesMarginServiceImpl implements SalesMarginService{
	 @Autowired
	 private OperateLogService operateLogService;

    @Override
    public PageInfoDto querySalesMargin(Map<String, String> queryParam) throws ServiceBizException {
        String brandCode = queryParam.get("brandCode");// 品牌
        String seriesCode = queryParam.get("seriesCode");// 车系
        String modelCode = queryParam.get("modelCode");// 车型
        String configCode = queryParam.get("configCode");// 配置
        String colorCode = queryParam.get("color");// 颜色
        String vin = queryParam.get("vin");
        String consultant = queryParam.get("soldBy");// 销售顾问
        String soNO = queryParam.get("soNo");// 订单编号
        String salesDateBegin = queryParam.get("orderDateFrom");// 销售开始日期
        String salesDateEnd = queryParam.get("orderDateTo");// 销售开始日期
        String entityCode=FrameworkUtil.getLoginInfo().getDealerCode();

        StringBuffer sql = new StringBuffer("");
        /*zhb 修改毛利报表
         * 参考成本 ： 采购价格+附加成本（返利系统无法拆分 暂时不做为成本统计）
         * 车辆毛利润 ：车辆销售价格 - （车辆采购价格 + 车辆附加成本）
         * 装潢毛利润 ：订单里的装潢金额 - 装潢材料的成本
         * 精品毛利润 ：订单里的精品金额 - 精品的成本
         * 增值代办收入 : 服务项目里的应收金额
         * 成本总额 ：采购价格 + 附加成本 + 装潢成本 + 精品成本 + 服务项目 
         * 销售总额 ：订单应收 - 减免
         * 利润总额 ：订单应收 - 减免 - 采购价格 - 附加成本 - 装潢成本 - 精品成本 - 服务项目 
         * */
        
        /*
         * 2010.7.15 mod by dyz
         * 销售精品成本 统计转过去的配件销售单的出库成本
         * 精品收入 统计销售订单中的收入+转过去的配件销售单的实收金额；
         * 精品利润 精品收入-精品成本
         * 销售总额 ：订单应收 - 减免 + 转过去的配件销售单的实收金额
         * 利润总额 ：订单应收 - 减免 - 采购价格 - 附加成本 - 装潢成本 - 精品成本 - 服务项目 + 转过去的配件销售单的实收金额
         */

        sql.append(" SELECT * FROM ("); 
        sql
                .append(" SELECT A.PRODUCT_CODE,C.BRAND_CODE,C.SERIES_CODE,C.MODEL_CODE,C.CONFIG_CODE,C.COLOR_CODE,A.VIN,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME,"
                        //购车成本
                        + " ( CASE WHEN BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_SERVICE+" THEN 0 ELSE B.PURCHASE_PRICE END ) AS PURCHASE_PRICE, "
                        //购车参考成本
                        + " ( CASE WHEN BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_SERVICE+" THEN 0 ELSE COALESCE(PURCHASE_PRICE,0) + COALESCE(B.ADDITIONAL_COST,0) END) AS VEHCILE_REFERRENCE_PRICE,"
                        //销售价格
                        + " ( CASE WHEN BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_SERVICE+" THEN 0 ELSE A.VEHICLE_PRICE END ) AS VEHICLE_PRICE,"
                        //车辆销售利润
                        + " ( CASE WHEN BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_SERVICE+" THEN 0 ELSE (COALESCE(A.VEHICLE_PRICE,0) - COALESCE(PURCHASE_PRICE,0) - COALESCE(B.ADDITIONAL_COST,0) ) END ) AS VEHICLE_SALES_PROFIT,"
                        
                        + " D.PART_COST_AMOUNT,A.UPHOLSTER_SUM,"
                        + " (COALESCE(A.UPHOLSTER_SUM,0) - COALESCE(D.PART_COST_AMOUNT,0)) AS PART_SALES_PROFIT," 
                        //精品销售总价
                        //+ " E.GARNITURE_REAL_PRICE AS GARNITURE_SUM,"
                        
                        //精品成本
                        + " E.GARNITURE_COST_AMOUNT,"
                        
                        + " M.GARNITURE_FREE_COST_AMOUNT,M.GARNITURE_SALES_COSE_AMOUNT,"
                        //订单精品总额
                        + " G.GARNITURE_SUM,"  
                        //销售精品总额
                        + " E.GARNITURE_REAL_PRICE ,"
                        + " ( COALESCE(G.GARNITURE_SUM,0) + COALESCE(E.GARNITURE_REAL_PRICE,0) ) AS GARNITURE_REAL_SUM,"
                        + " (COALESCE(E.GARNITURE_REAL_PRICE,0) + COALESCE(G.GARNITURE_SUM,0) - COALESCE(E.GARNITURE_COST_AMOUNT,0) ) AS GARNITURE_SALES_PROFIT,F.RECEIVEABLE_AMOUNT,"
                        
                        //成本总额
                        + " CASE WHEN BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_SERVICE+" THEN (COALESCE(D.PART_COST_AMOUNT,0) + COALESCE(E.GARNITURE_COST_AMOUNT,0)) " 
                        + " ELSE (COALESCE(PURCHASE_PRICE,0) + COALESCE(B.ADDITIONAL_COST,0) + COALESCE(D.PART_COST_AMOUNT,0) + COALESCE(E.GARNITURE_COST_AMOUNT,0) + COALESCE(F.RECEIVEABLE_PRICE,0) )"
                        + " END AS ORDER_SALES_COST_AMOUNT, "
                        
                        //销售总额
                        + " (COALESCE(A.ORDER_RECEIVABLE_SUM,0)+COALESCE(E.GARNITURE_REAL_PRICE,0) - COALESCE(ORDER_DERATED_SUM,0)) AS ORDER_SALES_SUM,"
                        
                        + " A.ORDER_DERATED_SUM,"                       
                        //利润总额
                        //在服务订单中利润总额减去购车成本、附加成本
                        + " CASE   WHEN BUSINESS_TYPE = "+ DictCodeConstants.DICT_SO_TYPE_SERVICE
                        + " THEN (COALESCE(A.ORDER_RECEIVABLE_SUM,0)+COALESCE(E.GARNITURE_REAL_PRICE,0) - COALESCE(ORDER_DERATED_SUM,0) -  COALESCE(D.PART_COST_AMOUNT,0) - COALESCE(E.GARNITURE_COST_AMOUNT,0) - COALESCE(F.RECEIVEABLE_PRICE,0) ) "
                        + " ELSE (COALESCE(A.ORDER_RECEIVABLE_SUM,0)+COALESCE(E.GARNITURE_REAL_PRICE,0) - COALESCE(ORDER_DERATED_SUM,0) - COALESCE(B.PURCHASE_PRICE,0) - COALESCE(B.ADDITIONAL_COST,0) - COALESCE(D.PART_COST_AMOUNT,0) - COALESCE(E.GARNITURE_COST_AMOUNT,0) - COALESCE(F.RECEIVEABLE_PRICE,0) ) "
                        + " END AS ORDER_SALES_PROFIT,"
                                                
                        + " A.SOLD_BY,em.USER_NAME,A.SO_NO,A.ORDER_SALES_DATE,A.DEALER_CODE,BUSINESS_TYPE "
                        + " FROM "
                        +
                        //查找已经完成的订单
                        " ("
                        + " SELECT SOLD_BY,SO_NO,DEALER_CODE, ORDER_DERATED_SUM,UPHOLSTER_SUM,VIN,PRODUCT_CODE,"
                        + " D_KEY,BUSINESS_TYPE,VEHICLE_PRICE,ORDER_RECEIVABLE_SUM,CASE WHEN BUSINESS_TYPE = "
                        + DictCodeConstants.DICT_SO_TYPE_RERURN
                        + " THEN RETURN_IN_DATE ELSE STOCK_OUT_DATE END AS ORDER_SALES_DATE "
                        + " FROM TT_SALES_ORDER M"
                        + " WHERE SO_STATUS = "
                        + DictCodeConstants.DICT_SO_STATUS_CLOSED
                        + " OR SO_STATUS  = "
                        + DictCodeConstants.DICT_SO_STATUS_UNTREAT_HAVE_IN_STOCK
                        + " AND SO_STATUS != "
                        + DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD
                        +

                        " ) A "
                        +" LEFT JOIN TM_USER em ON em.USER_ID=A.SOLD_BY AND em.DEALER_CODE=A.DEALER_CODE "
                        + " LEFT JOIN "
                        +
                        //统计车辆（发运单及库存里的车）
                        " (SELECT DEALER_CODE,D_KEY,VIN,PURCHASE_PRICE,PRODUCT_CODE,ADDITIONAL_COST FROM TM_VS_STOCK  "
                        + " UNION ALL"
                        + " SELECT DEALER_CODE,D_KEY,VIN,PURCHASE_PRICE,PRODUCT_CODE,ADDITIONAL_COST FROM TT_VS_SHIPPING_NOTIFY A"
                        + " WHERE NOT EXISTS(SELECT C.DEALER_CODE,C.VIN FROM TM_VS_STOCK C WHERE C.DEALER_CODE = A.DEALER_CODE AND C.VIN = A.VIN )) B "
                        + " ON  A.VIN = B.VIN AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY "
                        + " LEFT JOIN TM_VS_PRODUCT C "
                        + " ON A.PRODUCT_CODE = C.PRODUCT_CODE AND A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY "
                        +" left  join   tm_brand   br   on   C.BRAND_CODE = br.BRAND_CODE and C.DEALER_CODE=br.DEALER_CODE"
                        +" left  join   TM_SERIES  se   on   C.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and br.DEALER_CODE=se.DEALER_CODE"
                        +" left  join   TM_MODEL   mo   on   C.MODEL_CODE=mo.MODEL_CODE and mo.BRAND_CODE=se.BRAND_CODE AND mo.series_code=se.series_code and se.DEALER_CODE=mo.DEALER_CODE"
                        +" left  join   tm_configuration pa   on   C.CONFIG_CODE=pa.CONFIG_CODE and pa.brand_code=mo.brand_code and pa.series_code=mo.series_code and pa.model_code=mo.model_code and mo.DEALER_CODE=pa.DEALER_CODE"
                        +" left  join   tm_color   co   on   C.COLOR_CODE = co.COLOR_CODE and C.DEALER_CODE=co.DEALER_CODE"
                        
                        
                        + " LEFT JOIN "
                        +
                        //装潢材料的成本
                        " (SELECT DEALER_CODE,D_KEY,SO_NO,SUM(PART_COST_PRICE * PART_QUANTITY ) AS PART_COST_AMOUNT  "
                        + " FROM TT_SO_PART GROUP BY DEALER_CODE,D_KEY,SO_NO ) D"
                        + " ON A.SO_NO = D.SO_NO AND A.DEALER_CODE = D.DEALER_CODE AND A.D_KEY = D.D_KEY "
                        +
                        //精品的成本
                        " LEFT JOIN "
                        
                        //销售单配件总成本、销售总价
                        + "( SELECT L.DEALER_CODE,L.D_KEY,J.SO_NO, "
                        + " SUM(L.PART_QUANTITY*L.PART_COST_PRICE) AS GARNITURE_COST_AMOUNT, "
                        + " SUM(L.PART_SALES_AMOUNT) AS GARNITURE_REAL_PRICE  "
                        + " FROM (TT_SALES_PART_ITEM L INNER JOIN TT_SALES_PART J ON L.SALES_PART_NO = J.SALES_PART_NO AND L.DEALER_CODE = J.DEALER_CODE) " 
                        + " WHERE J.SO_NO IS NOT NULL "
                        + " GROUP BY L.DEALER_CODE,L.D_KEY,J.SO_NO  "
                        
                        + ") E"
                        //+ " (SELECT DEALER_CODE,D_KEY,SO_NO,SUM(REAL_PRICE) as GARNITURE_REAL_PRICE,SUM(QUANTITY * COST_PRICE) AS GARNITURE_COST_AMOUNT "
                        //+ " FROM TT_SO_GARNITURE  GROUP BY DEALER_CODE,D_KEY,SO_NO) E"                        
                    
                        + " ON A.DEALER_CODE = E.DEALER_CODE AND A.D_KEY = E.D_KEY AND A.SO_NO = E.SO_NO "
                        
                        //精品的成本的拆分 销售成本和赠送成本2008-11-8
                        + " LEFT JOIN "
                        + " (SELECT DEALER_CODE,D_KEY,SO_NO,"
                        + "SUM(CASE WHEN ACCOUNT_MODE = "
                        + DictCodeConstants.DICT_ADD_ITEM_BALANCE_MODE_TYPE_FREE
                        + " THEN QUANTITY * COST_PRICE ELSE 0 END ) AS GARNITURE_FREE_COST_AMOUNT, "
                        + "SUM(CASE WHEN ACCOUNT_MODE != "
                        + DictCodeConstants.DICT_ADD_ITEM_BALANCE_MODE_TYPE_FREE
                        + " THEN QUANTITY * COST_PRICE ELSE 0 END ) AS GARNITURE_SALES_COSE_AMOUNT "
                        + " FROM TT_SO_GARNITURE  GROUP BY DEALER_CODE,D_KEY,SO_NO) M"
                        + " ON A.DEALER_CODE = M.DEALER_CODE AND A.D_KEY = M.D_KEY AND A.SO_NO = M.SO_NO "
                        +
                        //增值代办收入
                        " LEFT JOIN "
                        + " (SELECT DEALER_CODE,D_KEY,SO_NO,SUM(COST_PRICE) AS RECEIVEABLE_PRICE,SUM(RECEIVEABLE_AMOUNT) AS RECEIVEABLE_AMOUNT "
                        + " FROM TT_SO_SERVICE GROUP BY DEALER_CODE,D_KEY,SO_NO) F"
                        + " ON A.DEALER_CODE = F.DEALER_CODE AND A.D_KEY = F.D_KEY AND A.SO_NO = F.SO_NO "
                        + " LEFT JOIN "
                        + "  ("
                        + " SELECT DEALER_CODE,D_KEY,SO_NO,SUM(CASE WHEN (ACCOUNT_MODE="+DictCodeConstants.DICT_ADD_ITEM_BALANCE_MODE_TYPE_CONTAINED+"  or ACCOUNT_MODE="+DictCodeConstants.DICT_ADD_ITEM_BALANCE_MODE_TYPE_FREE+") then 0 else    REAL_PRICE*QUANTITY   end ) AS GARNITURE_SUM FROM TT_SO_GARNITURE " 
                        + " GROUP BY DEALER_CODE,D_KEY,SO_NO "
                        + " ) G ON A.DEALER_CODE = G.DEALER_CODE AND A.D_KEY = G.D_KEY AND A.SO_NO = G.SO_NO ");
        sql.append(" ) TA1 WHERE 1 = 1");
        sql.append(Utility.getStrCond("TA1", "DEALER_CODE", entityCode));
        sql.append(Utility.getStrCond("TA1", "BRAND_CODE", brandCode));
        sql.append(Utility.getStrCond("TA1", "SERIES_CODE", seriesCode));
        sql.append(Utility.getStrCond("TA1", "MODEL_CODE", modelCode));
        sql.append(Utility.getStrCond("TA1", "CONFIG_CODE", configCode));
        sql.append(Utility.getStrCond("TA1", "COLOR_CODE", colorCode));
        sql.append(Utility.getLikeCond("TA1", "VIN", vin, "AND"));
        if (salesDateBegin != null && !"".equals(salesDateBegin))
        {
            sql.append(" AND DATE(TA1.ORDER_SALES_DATE) >= '" + salesDateBegin + "'  ");
        }
        if (salesDateEnd != null && !"".equals(salesDateEnd))
        {
            sql.append(" AND DATE(TA1.ORDER_SALES_DATE) <= '" + salesDateEnd + "'  ");
        }
        sql.append(Utility.getintCond("TA1", "SOLD_BY", consultant));
        sql.append(Utility.getLikeCond("TA1", "SO_NO", soNO, "AND"));
        /*      sql
         .append("SELECT  (A.DIRECTIVE_PRICE-A.VEHICLE_PRICE) AS CAR_PROFIT,C.PRODUCT_CODE,C.BRAND_CODE,C.SERIES_CODE,C.MODEL_CODE,C.CONFIG_CODE,C.COLOR_CODE,A.VIN,"
         + "A.SO_NO,A.SOLD_BY,A.INVOICE_DATE,A.VEHICLE_PRICE,B.BOUNTY_AMOUNT,A.DIRECTIVE_PRICE,A.ORDER_SUM,"
         + "D.PART_COST_AMOUNT,(D.REAL_PRICE*D.PART_QUANTITY+E.LABOUR_AMOUNT) AS UPHOLSTER_AMOUNT,"
         + "(D.REAL_PRICE*D.PART_QUANTITY+E.LABOUR_AMOUNT-D.PART_COST_AMOUNT) AS UPHOLSTER_PROFIT,F.COST_AMOUNT,"
         + "F.RECEIVEABLE_AMOUNT,F.RECEIVEABLE_AMOUNT-F.COST_AMOUNT AS RECEIVEABLE_PROFIT,G.CONSIGNED_AMOUNT,"
         + "(B.BOUNTY_AMOUNT+D.PART_COST_AMOUNT+F.RECEIVEABLE_AMOUNT) AS TOTAL_COST_AMOUNT,"
         + "(A.ORDER_SUM+D.REAL_PRICE*D.PART_QUANTITY+E.LABOUR_AMOUNT+F.RECEIVEABLE_AMOUNT) AS TOTAL_SALES_AMOUNT,H.DERATE_AMOUNT,"
         + "((A.ORDER_SUM+D.REAL_PRICE*D.PART_QUANTITY+E.LABOUR_AMOUNT+F.RECEIVEABLE_AMOUNT)-(B.BOUNTY_AMOUNT+D.PART_COST_AMOUNT+F.RECEIVEABLE_AMOUNT)) AS TOTAL_PROFIT_AMOUNT "
         + "FROM TT_SALES_ORDER A LEFT JOIN TT_SB_SPLIT B ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.VIN=B.VIN "
         + "LEFT JOIN TM_VS_PRODUCT C ON A.DEALER_CODE=C.DEALER_CODE AND A.D_KEY=C.D_KEY AND A.PRODUCT_CODE=C.PRODUCT_CODE "
         + "LEFT JOIN TT_SO_PART D ON A.SO_NO=D.SO_NO AND A.DEALER_CODE=D.DEALER_CODE AND A.D_KEY=D.D_KEY "
         + "LEFT JOIN TT_SO_UPHOLSTER E ON A.SO_NO=D.SO_NO AND A.DEALER_CODE=E.DEALER_CODE AND A.D_KEY=E.D_KEY "
         + "LEFT JOIN TT_SO_GARNITURE F ON A.SO_NO=F.SO_NO AND A.DEALER_CODE=F.DEALER_CODE AND A.D_KEY=F.D_KEY "
         + "LEFT JOIN TT_SO_SERVICE G ON A.SO_NO=G.SO_NO AND A.DEALER_CODE=G.DEALER_CODE AND A.D_KEY=G.D_KEY "
         + "LEFT JOIN TT_SO_DERATE H ON A.SO_NO=H.SO_NO AND A.DEALER_CODE=H.DEALER_CODE AND A.D_KEY=H.D_KEY "
         + "WHERE A.DEALER_CODE='"
         + entityCode
         + "' AND A.D_KEY=" + CommonConstant.D_KEY + " ");
         sql.append(Utility.getLikeCond("B", "BRAND_CODE", brandCode, "AND"));
         sql.append(Utility.getLikeCond("B", "SERIES_CODE", seriesCode, "AND"));
         sql.append(Utility.getLikeCond("B", "MODEL_CODE", modelCode, "AND"));
         sql.append(Utility.getLikeCond("B", "CONFIG_CODE", configCode, "AND"));
         sql.append(Utility.getLikeCond("B", "COLOR_CODE", colorCode, "AND"));
         sql.append(Utility.getLikeCond("A", "VIN", vin, "AND"));
         
         if (salesDateBegin != null && !"".equals(salesDateBegin)) {
         sql.append(" AND DATE(B.INVOICE_DATE)>='" + salesDateBegin + "'  ");
         }
         if (salesDateEnd != null && !"".equals(salesDateEnd)) {
         sql.append(" AND DATE(B.INVOICE_DATE)<'" + salesDateEnd + "'  ");
         }
         sql.append(Utility.getLikeCond("A", "SOLD_BY", consultant, "AND"));
         sql.append(Utility.getLikeCond("A", "SO_NO", soNO, "AND"));*/

           List<Object> params=new ArrayList<Object>();

          return DAOUtil.pageQuery(sql.toString(), params);
    }
    
    /**
     * 导出查询
     * 
     * @author xiahaiyang
     * @date 2017年1月16日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryPotentialCusforExport(java.util.Map)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Map> querySalesMarginExport(Map<String, String> queryParam) throws ServiceBizException {
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	  String brandCode = queryParam.get("brandCode");// 品牌
          String seriesCode = queryParam.get("seriesCode");// 车系
          String modelCode = queryParam.get("modelCode");// 车型
          String configCode = queryParam.get("configCode");// 配置
          String colorCode = queryParam.get("color");// 颜色
          String vin = queryParam.get("vin");
          String consultant = queryParam.get("soldBy");// 销售顾问
          String soNO = queryParam.get("soNo");// 订单编号
          String salesDateBegin = queryParam.get("orderDateFrom");// 销售开始日期
          String salesDateEnd = queryParam.get("orderDateTo");// 销售开始日期
          String entityCode=FrameworkUtil.getLoginInfo().getDealerCode();

          StringBuffer sql = new StringBuffer("");
          /*zhb 修改毛利报表
           * 参考成本 ： 采购价格+附加成本（返利系统无法拆分 暂时不做为成本统计）
           * 车辆毛利润 ：车辆销售价格 - （车辆采购价格 + 车辆附加成本）
           * 装潢毛利润 ：订单里的装潢金额 - 装潢材料的成本
           * 精品毛利润 ：订单里的精品金额 - 精品的成本
           * 增值代办收入 : 服务项目里的应收金额
           * 成本总额 ：采购价格 + 附加成本 + 装潢成本 + 精品成本 + 服务项目 
           * 销售总额 ：订单应收 - 减免
           * 利润总额 ：订单应收 - 减免 - 采购价格 - 附加成本 - 装潢成本 - 精品成本 - 服务项目 
           * */
          
          /*
           * 2010.7.15 mod by dyz
           * 销售精品成本 统计转过去的配件销售单的出库成本
           * 精品收入 统计销售订单中的收入+转过去的配件销售单的实收金额；
           * 精品利润 精品收入-精品成本
           * 销售总额 ：订单应收 - 减免 + 转过去的配件销售单的实收金额
           * 利润总额 ：订单应收 - 减免 - 采购价格 - 附加成本 - 装潢成本 - 精品成本 - 服务项目 + 转过去的配件销售单的实收金额
           */

          sql.append(" SELECT * FROM ("); 
          sql
                  .append(" SELECT A.PRODUCT_CODE,C.BRAND_CODE,C.SERIES_CODE,C.MODEL_CODE,C.CONFIG_CODE,C.COLOR_CODE,A.VIN,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME,"
                          //购车成本
                          + " ( CASE WHEN BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_SERVICE+" THEN 0 ELSE B.PURCHASE_PRICE END ) AS PURCHASE_PRICE, "
                          //购车参考成本
                          + " ( CASE WHEN BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_SERVICE+" THEN 0 ELSE COALESCE(PURCHASE_PRICE,0) + COALESCE(B.ADDITIONAL_COST,0) END) AS VEHCILE_REFERRENCE_PRICE,"
                          //销售价格
                          + " ( CASE WHEN BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_SERVICE+" THEN 0 ELSE A.VEHICLE_PRICE END ) AS VEHICLE_PRICE,"
                          //车辆销售利润
                          + " ( CASE WHEN BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_SERVICE+" THEN 0 ELSE (COALESCE(A.VEHICLE_PRICE,0) - COALESCE(PURCHASE_PRICE,0) - COALESCE(B.ADDITIONAL_COST,0) ) END ) AS VEHICLE_SALES_PROFIT,"
                          
                          + " D.PART_COST_AMOUNT,A.UPHOLSTER_SUM,"
                          + " (COALESCE(A.UPHOLSTER_SUM,0) - COALESCE(D.PART_COST_AMOUNT,0)) AS PART_SALES_PROFIT," 
                          //精品销售总价
                          //+ " E.GARNITURE_REAL_PRICE AS GARNITURE_SUM,"
                          
                          //精品成本
                          + " E.GARNITURE_COST_AMOUNT,"
                          
                          + " M.GARNITURE_FREE_COST_AMOUNT,M.GARNITURE_SALES_COSE_AMOUNT,"
                          //订单精品总额
                          + " G.GARNITURE_SUM,"  
                          //销售精品总额
                          + " E.GARNITURE_REAL_PRICE ,"
                          + " ( COALESCE(G.GARNITURE_SUM,0) + COALESCE(E.GARNITURE_REAL_PRICE,0) ) AS GARNITURE_REAL_SUM,"
                          + " (COALESCE(E.GARNITURE_REAL_PRICE,0) + COALESCE(G.GARNITURE_SUM,0) - COALESCE(E.GARNITURE_COST_AMOUNT,0) ) AS GARNITURE_SALES_PROFIT,F.RECEIVEABLE_AMOUNT,"
                          
                          //成本总额
                          + " CASE WHEN BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_SERVICE+" THEN (COALESCE(D.PART_COST_AMOUNT,0) + COALESCE(E.GARNITURE_COST_AMOUNT,0)) " 
                          + " ELSE (COALESCE(PURCHASE_PRICE,0) + COALESCE(B.ADDITIONAL_COST,0) + COALESCE(D.PART_COST_AMOUNT,0) + COALESCE(E.GARNITURE_COST_AMOUNT,0) + COALESCE(F.RECEIVEABLE_PRICE,0) )"
                          + " END AS ORDER_SALES_COST_AMOUNT, "
                          
                          //销售总额
                          + " (COALESCE(A.ORDER_RECEIVABLE_SUM,0)+COALESCE(E.GARNITURE_REAL_PRICE,0) - COALESCE(ORDER_DERATED_SUM,0)) AS ORDER_SALES_SUM,"
                          
                          + " A.ORDER_DERATED_SUM,"                       
                          //利润总额
                          //在服务订单中利润总额减去购车成本、附加成本
                          + " CASE   WHEN BUSINESS_TYPE = "+ DictCodeConstants.DICT_SO_TYPE_SERVICE
                          + " THEN (COALESCE(A.ORDER_RECEIVABLE_SUM,0)+COALESCE(E.GARNITURE_REAL_PRICE,0) - COALESCE(ORDER_DERATED_SUM,0) -  COALESCE(D.PART_COST_AMOUNT,0) - COALESCE(E.GARNITURE_COST_AMOUNT,0) - COALESCE(F.RECEIVEABLE_PRICE,0) ) "
                          + " ELSE (COALESCE(A.ORDER_RECEIVABLE_SUM,0)+COALESCE(E.GARNITURE_REAL_PRICE,0) - COALESCE(ORDER_DERATED_SUM,0) - COALESCE(B.PURCHASE_PRICE,0) - COALESCE(B.ADDITIONAL_COST,0) - COALESCE(D.PART_COST_AMOUNT,0) - COALESCE(E.GARNITURE_COST_AMOUNT,0) - COALESCE(F.RECEIVEABLE_PRICE,0) ) "
                          + " END AS ORDER_SALES_PROFIT,"
                                                  
                          + " A.SOLD_BY,em.USER_NAME,A.SO_NO,A.ORDER_SALES_DATE,A.DEALER_CODE,BUSINESS_TYPE "
                          + " FROM "
                          +
                          //查找已经完成的订单
                          " ("
                          + " SELECT SOLD_BY,SO_NO,DEALER_CODE, ORDER_DERATED_SUM,UPHOLSTER_SUM,VIN,PRODUCT_CODE,"
                          + " D_KEY,BUSINESS_TYPE,VEHICLE_PRICE,ORDER_RECEIVABLE_SUM,CASE WHEN BUSINESS_TYPE = "
                          + DictCodeConstants.DICT_SO_TYPE_RERURN
                          + " THEN RETURN_IN_DATE ELSE STOCK_OUT_DATE END AS ORDER_SALES_DATE "
                          + " FROM TT_SALES_ORDER M"
                          + " WHERE SO_STATUS = "
                          + DictCodeConstants.DICT_SO_STATUS_CLOSED
                          + " OR SO_STATUS  = "
                          + DictCodeConstants.DICT_SO_STATUS_UNTREAT_HAVE_IN_STOCK
                          + " AND SO_STATUS != "
                          + DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD
                          +

                          " ) A "
                          +" LEFT JOIN TM_USER em ON em.USER_ID=A.SOLD_BY AND em.DEALER_CODE=A.DEALER_CODE "
                          + " LEFT JOIN "
                          +
                          //统计车辆（发运单及库存里的车）
                          " (SELECT DEALER_CODE,D_KEY,VIN,PURCHASE_PRICE,PRODUCT_CODE,ADDITIONAL_COST FROM TM_VS_STOCK  "
                          + " UNION ALL"
                          + " SELECT DEALER_CODE,D_KEY,VIN,PURCHASE_PRICE,PRODUCT_CODE,ADDITIONAL_COST FROM TT_VS_SHIPPING_NOTIFY A"
                          + " WHERE NOT EXISTS(SELECT C.DEALER_CODE,C.VIN FROM TM_VS_STOCK C WHERE C.DEALER_CODE = A.DEALER_CODE AND C.VIN = A.VIN )) B "
                          + " ON  A.VIN = B.VIN AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY "
                          + " LEFT JOIN TM_VS_PRODUCT C "
                          + " ON A.PRODUCT_CODE = C.PRODUCT_CODE AND A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY "
                          +" left  join   tm_brand   br   on   C.BRAND_CODE = br.BRAND_CODE and C.DEALER_CODE=br.DEALER_CODE"
                          +" left  join   TM_SERIES  se   on   C.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and br.DEALER_CODE=se.DEALER_CODE"
                          +" left  join   TM_MODEL   mo   on   C.MODEL_CODE=mo.MODEL_CODE and mo.BRAND_CODE=se.BRAND_CODE AND mo.series_code=se.series_code and se.DEALER_CODE=mo.DEALER_CODE"
                          +" left  join   tm_configuration pa   on   C.CONFIG_CODE=pa.CONFIG_CODE and pa.brand_code=mo.brand_code and pa.series_code=mo.series_code and pa.model_code=mo.model_code and mo.DEALER_CODE=pa.DEALER_CODE"
                          +" left  join   tm_color   co   on   C.COLOR_CODE = co.COLOR_CODE and C.DEALER_CODE=co.DEALER_CODE"
                          
                          
                          + " LEFT JOIN "
                          +
                          //装潢材料的成本
                          " (SELECT DEALER_CODE,D_KEY,SO_NO,SUM(PART_COST_PRICE * PART_QUANTITY ) AS PART_COST_AMOUNT  "
                          + " FROM TT_SO_PART GROUP BY DEALER_CODE,D_KEY,SO_NO ) D"
                          + " ON A.SO_NO = D.SO_NO AND A.DEALER_CODE = D.DEALER_CODE AND A.D_KEY = D.D_KEY "
                          +
                          //精品的成本
                          " LEFT JOIN "
                          
                          //销售单配件总成本、销售总价
                          + "( SELECT L.DEALER_CODE,L.D_KEY,J.SO_NO, "
                          + " SUM(L.PART_QUANTITY*L.PART_COST_PRICE) AS GARNITURE_COST_AMOUNT, "
                          + " SUM(L.PART_SALES_AMOUNT) AS GARNITURE_REAL_PRICE  "
                          + " FROM (TT_SALES_PART_ITEM L INNER JOIN TT_SALES_PART J ON L.SALES_PART_NO = J.SALES_PART_NO AND L.DEALER_CODE = J.DEALER_CODE) " 
                          + " WHERE J.SO_NO IS NOT NULL "
                          + " GROUP BY L.DEALER_CODE,L.D_KEY,J.SO_NO  "
                          
                          + ") E"
                          //+ " (SELECT DEALER_CODE,D_KEY,SO_NO,SUM(REAL_PRICE) as GARNITURE_REAL_PRICE,SUM(QUANTITY * COST_PRICE) AS GARNITURE_COST_AMOUNT "
                          //+ " FROM TT_SO_GARNITURE  GROUP BY DEALER_CODE,D_KEY,SO_NO) E"                        
                      
                          + " ON A.DEALER_CODE = E.DEALER_CODE AND A.D_KEY = E.D_KEY AND A.SO_NO = E.SO_NO "
                          
                          //精品的成本的拆分 销售成本和赠送成本2008-11-8
                          + " LEFT JOIN "
                          + " (SELECT DEALER_CODE,D_KEY,SO_NO,"
                          + "SUM(CASE WHEN ACCOUNT_MODE = "
                          + DictCodeConstants.DICT_ADD_ITEM_BALANCE_MODE_TYPE_FREE
                          + " THEN QUANTITY * COST_PRICE ELSE 0 END ) AS GARNITURE_FREE_COST_AMOUNT, "
                          + "SUM(CASE WHEN ACCOUNT_MODE != "
                          + DictCodeConstants.DICT_ADD_ITEM_BALANCE_MODE_TYPE_FREE
                          + " THEN QUANTITY * COST_PRICE ELSE 0 END ) AS GARNITURE_SALES_COSE_AMOUNT "
                          + " FROM TT_SO_GARNITURE  GROUP BY DEALER_CODE,D_KEY,SO_NO) M"
                          + " ON A.DEALER_CODE = M.DEALER_CODE AND A.D_KEY = M.D_KEY AND A.SO_NO = M.SO_NO "
                          +
                          //增值代办收入
                          " LEFT JOIN "
                          + " (SELECT DEALER_CODE,D_KEY,SO_NO,SUM(COST_PRICE) AS RECEIVEABLE_PRICE,SUM(RECEIVEABLE_AMOUNT) AS RECEIVEABLE_AMOUNT "
                          + " FROM TT_SO_SERVICE GROUP BY DEALER_CODE,D_KEY,SO_NO) F"
                          + " ON A.DEALER_CODE = F.DEALER_CODE AND A.D_KEY = F.D_KEY AND A.SO_NO = F.SO_NO "
                          + " LEFT JOIN "
                          + "  ("
                          + " SELECT DEALER_CODE,D_KEY,SO_NO,SUM(CASE WHEN (ACCOUNT_MODE="+DictCodeConstants.DICT_ADD_ITEM_BALANCE_MODE_TYPE_CONTAINED+"  or ACCOUNT_MODE="+DictCodeConstants.DICT_ADD_ITEM_BALANCE_MODE_TYPE_FREE+") then 0 else    REAL_PRICE*QUANTITY   end ) AS GARNITURE_SUM FROM TT_SO_GARNITURE " 
                          + " GROUP BY DEALER_CODE,D_KEY,SO_NO "
                          + " ) G ON A.DEALER_CODE = G.DEALER_CODE AND A.D_KEY = G.D_KEY AND A.SO_NO = G.SO_NO ");
          sql.append(" ) TA1 WHERE 1 = 1");
          sql.append(Utility.getStrCond("TA1", "DEALER_CODE", entityCode));
          sql.append(Utility.getStrCond("TA1", "BRAND_CODE", brandCode));
          sql.append(Utility.getStrCond("TA1", "SERIES_CODE", seriesCode));
          sql.append(Utility.getStrCond("TA1", "MODEL_CODE", modelCode));
          sql.append(Utility.getStrCond("TA1", "CONFIG_CODE", configCode));
          sql.append(Utility.getStrCond("TA1", "COLOR_CODE", colorCode));
          sql.append(Utility.getLikeCond("TA1", "VIN", vin, "AND"));
          if (salesDateBegin != null && !"".equals(salesDateBegin))
          {
              sql.append(" AND DATE(TA1.ORDER_SALES_DATE) >= '" + salesDateBegin + "'  ");
          }
          if (salesDateEnd != null && !"".equals(salesDateEnd))
          {
              sql.append(" AND DATE(TA1.ORDER_SALES_DATE) <= '" + salesDateEnd + "'  ");
          }
          sql.append(Utility.getintCond("TA1", "SOLD_BY", consultant));
          sql.append(Utility.getLikeCond("TA1", "SO_NO", soNO, "AND"));
        List<Map> list  = null;
        List<Object> queryList = new ArrayList<Object>();
        list = DAOUtil.findAll(sql.toString(), queryList);
      
            OperateLogDto operateLogDto=new OperateLogDto();
            operateLogDto.setOperateContent("销售毛利统计导出");
            operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
            operateLogService.writeOperateLog(operateLogDto);
       
         
       
    return list;
        
    }

}
