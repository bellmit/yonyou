
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : SaleReportServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月27日    zhanshiwei    1.0
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
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 销售
 * 
 * @author zhanshiwei
 * @date 2016年9月27日
 */
@Service
public class SaleReportServiceImpl implements SaleReportService {

    /**
     * 整车销售
     * 
     * @author zhanshiwei
     * @date 2016年9月27日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.SaleReportService#queryVehicleSales(java.util.Map)
     */

    @Override
    public PageInfoDto queryVehicleSales(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("");
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();        
        
        sb.append(" select * from ( ");
        sb.append(" select  (COALESCE(A.DIRECTIVE_PRICE,0)-COALESCE(a.VEHICLE_PRICE,0)) as preferential_price,  "
        		+ " (COALESCE(A.VEHICLE_PRICE,0)-COALESCE(A.PURCHASE_PRICE,0))/(CASE WHEN COALESCE(A.VEHICLE_PRICE,0)=0 THEN 1 ELSE VEHICLE_PRICE END)  AS POSS_PROFIT_RATE , "
        		+ " A.PAY_MODE,A.OTHER_AMOUNT,A.ORG_CODE,A.ZIP_CODE,A.GENDER,A.PHONE,A.ADDRESS,A.INVOICE_AMOUNT,A.ENGINE_NO,(select s.status_desc from tc_code s where s.status_code=a.business_type ) AS BUSINESS_TYPE,A.PRODUCT_CODE, "
        		+ " A.CUSTOMER_NAME,"
        		+ " C.BRAND_CODE,C.SERIES_CODE,C.MODEL_CODE,C.CONFIG_CODE,C.COLOR_CODE,A.VIN,A.ORG_CODE AS SALES_GROUP,A.SOLD_BY,A.SO_NO ,A.STOCK_OUT_DATE,A.ORDER_SALES_DATE,  "
        		+ " A.ENTITY_CODE, A.FACTORY_DATE,A.DIRECTIVE_PRICE,a.VEHICLE_PRICE,A.PURCHASE_PRICE,(COALESCE(a.VEHICLE_PRICE,0)-COALESCE(A.PURCHASE_PRICE,0)) AS POSS_PROFIT " 
        		+ " from (SELECT DISTINCT M.*,Y.ZIP_CODE,Y.GENDER,B.PURCHASE_PRICE,B.ENGINE_NO, B.PRODUCT_CODE,COALESCE(Y.CUSTOMER_NAME,M.CUSTOMER_NAME_OTHER) AS CUSTOMER_NAME,Y.PHONE,Y.ADDRESS,B.FACTORY_DATE,  " 
        		+ " B.LATEST_STOCK_OUT_DATE AS STOCK_OUT_DATE, " 
        		+ " B.LATEST_STOCK_OUT_DATE AS ORDER_SALES_DATE,B.DIRECTIVE_PRICE FROM " 
        		+ " (select A.SO_NO,A.vin,A.OTHER_AMOUNT,A.VEHICLE_PRICE,A.PAY_MODE,A.BUSINESS_TYPE ,Z.ORG_CODE,A.SOLD_BY,A.CUSTOMER_NAME AS CUSTOMER_NAME_OTHER,A.ENTITY_CODE,A.INVOICE_AMOUNT from TT_SALES_ORDER  A, (SELECT X.ENTITY_CODE,X.USER_ID,X.ORG_CODE   " 
        		+ " FROM TM_USER X,TM_ORGANIZATION Y WHERE X.ENTITY_CODE=Y.ENTITY_CODE AND X.ORG_CODE=Y.ORG_CODE)Z, " 
        		+ " (select max(create_date) AS create_date,vin,ENTITY_CODE from TT_SALES_ORDER where BUSINESS_TYPE=13001001 AND ( SO_STATUS = 13011035 or SO_STATUS = 13011075) "  
        		+ " group by vin,ENTITY_CODE)H "
        		+ " where A.ENTITY_CODE=Z.ENTITY_CODE " 
        		+ " AND A.SOLD_BY=Z.USER_ID  AND A.BUSINESS_TYPE=13001001 AND ( SO_STATUS = 13011035 or SO_STATUS = 13011075)AND "  
        		+ " A.VIN=H.VIN AND A.CREATE_DATE=H.CREATE_DATE AND A.ENTITY_CODE=H.ENTITY_CODE " 
        		+ " union all " 
        		+ " select DISTINCT  B.SEC_SO_NO AS SO_NO,A.vin,B.OTHER_AMOUNT,A.FETCH_PURCHASE_PRICE AS VEHICLE_PRICE,0 AS PAY_MODE,B.BILL_TYPE AS BUSINESS_TYPE,Z.ORG_CODE,B.SOLD_BY,B.CUSTOMER_NAME AS CUSTOMER_NAME_OTHER,A.ENTITY_CODE,A.INVOICE_AMOUNT from " 
        		+ " (SELECT A.*,D.INVOICE_AMOUNT FROM TT_SEC_SALES_ORDER_ITEM A LEFT JOIN TT_SEC_INVOICE D ON D.VIN=A.VIN AND A.ENTITY_CODE=D.ENTITY_CODE and D.IS_VALID=12781001) A, " 
        		+ " (SELECT X.ENTITY_CODE,X.USER_ID, X.ORG_CODE FROM TM_USER X,TM_ORGANIZATION Y  WHERE X.ENTITY_CODE=Y.ENTITY_CODE AND X.ORG_CODE=Y.ORG_CODE)Z,  TT_SECOND_SALES_ORDER B, " 
        		+ " (SELECT MAX(B.CREATE_DATE) AS CREATE_DATE,VIN,A.ENTITY_CODE FROM TT_SEC_SALES_ORDER_ITEM A " 
        		+ " INNER JOIN TT_SECOND_SALES_ORDER B ON A.ENTITY_CODE=B.ENTITY_CODE AND A.SEC_SO_NO=B.SEC_SO_NO " 
        		+ " WHERE  (SO_STATUS = 13011035 or SO_STATUS = 13011075) and B.BILL_TYPE=19001001 " 
        		+ " GROUP BY VIN,A.ENTITY_CODE) H " 
        		+ " where B.ENTITY_CODE=Z.ENTITY_CODE AND B.SOLD_BY=Z.USER_ID  and A.SEC_SO_NO=B.SEC_SO_NO " 
        		+ " and B.BILL_TYPE=19001001 AND (SO_STATUS = 13011035 or SO_STATUS = 13011075) AND H.VIN=A.VIN AND H.ENTITY_CODE=A.ENTITY_CODE AND H.CREATE_DATE=B.CREATE_DATE "
        		+ " )M " 
        		+ " inner join TM_VS_STOCK B ON M.VIN=B.VIN AND M.ENTITY_CODE=B.ENTITY_CODE AND STOCK_STATUS=13041001 " 
        		+ " LEFT JOIN (select DISTINCT Y.ENTITY_CODE,M.VIN,Y.CUSTOMER_NAME,Y.ZIP_CODE,Y.GENDER,Y.CONTACTOR_MOBILE AS PHONE,Y.ADDRESS from tm_vehicle M " 
        		+ " inner join TM_CUSTOMER Y on Y.ENTITY_CODE=M.ENTITY_CODE  AND Y.CUSTOMER_NO=M.CUSTOMER_NO) " 
        		+ " Y ON Y.ENTITY_CODE=M.ENTITY_CODE  AND Y.VIN=M.VIN " 
        		+ " )A " 
        		+ " LEFT JOIN VM_VS_PRODUCT C ON A.PRODUCT_CODE = C.PRODUCT_CODE AND A.ENTITY_CODE = C.ENTITY_CODE " 
        		+ " ) TA1 WHERE 1 = 1  and  TA1.DEALER_CODE='" + dealerCode
				+ "'   " );
        sb.append(" where 1==1 ");
        List<Object> queryList = new ArrayList<Object>();
        this.setVehicleSaleWhere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    /**
     * 整车销售查询条件设置
     * 
     * @author zhanshiwei
     * @date 2016年9月27日
     * @param sb
     * @param queryParam
     * @param queryList
     */

    public void setVehicleSaleWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
    	
        /*if (!StringUtils.isNullOrEmpty(queryParam.get("salesGroup"))) {
            sb.append(" AND TA1.SALES_GROUP='" + salesGroup + "'");
            queryList.add(queryParam.get("salesGroup"));
        }*/
        if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
            sb.append(" and veh.SERIES_CODE = ?");
            queryList.add(queryParam.get("seriesCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))) {
            sb.append(" and veh.MODEL_CODE = ?");
            queryList.add(queryParam.get("modelCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("configCode"))) {
            sb.append(" and veh.CONFIG_CODE = ?");
            queryList.add(queryParam.get("configCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("color"))) {
            sb.append(" and veh.COLOR = ?");
            queryList.add(queryParam.get("color"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and veh.VIN like ?");
            queryList.add("%" + queryParam.get("vin") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
            sb.append(" and sale.CONSULTANT= ?");
            queryList.add(queryParam.get("consultant"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("orgCode"))) {
            sb.append(" and e.ORG_CODE= ?");
            queryList.add(queryParam.get("orgCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("stockin_startdate"))) {
            sb.append(" and sto.LATEST_STOCK_IN_DATE>= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("stockin_startdate")));

            sb.append(" and sto.LATEST_STOCK_IN_DATE<?");
            queryList.add(DateUtil.addOneDay(queryParam.get("stockin_enddate")));
        }

        if (!StringUtils.isNullOrEmpty(queryParam.get("stockout_startdate"))) {
            sb.append(" and sto.LATEST_STOCK_OUT_DATE>= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("stockout_startdate")));
            sb.append(" and sto.LATEST_STOCK_OUT_DATE<?");
            queryList.add(DateUtil.addOneDay(queryParam.get("stockout_enddate")));
        }

        if (!StringUtils.isNullOrEmpty(queryParam.get("invoice_startdate"))) {
            sb.append(" and sce.INVOICE_DATE>= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("invoice_startdate")));
            sb.append(" and sce.INVOICE_DATE<?");
            queryList.add(DateUtil.addOneDay(queryParam.get("invoice_enddate")));
        }

        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and sale.CUSTOMER_NAME like ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
    }

    /**
     * 销售统计分析
     * 
     * @author zhanshiwei
     * @date 2016年9月28日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.SaleReportService#querySaleStatistics(java.util.Map)
     */

    @Override
    public PageInfoDto querySaleStatistics(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();
        StringBuffer sb=new StringBuffer("");
        this.getSaleStatisticSql(sb, queryList, queryParam);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }
    
    /**
    * 销售统计分析SQL
    * @author zhanshiwei
    * @date 2016年11月30日
    * @param sb
    * @param queryList
    * @param queryParam
    */
    	
    public void getSaleStatisticSql(StringBuffer sb, List<Object> queryList,Map<String, String> queryParam){
        sb.append("  select COUNT(DISTINCT vre.VISITING_RECORD_ID) as VISITING_NUM,\n");
        sb.append("       COUNT(DISTINCT pcus.POTENTIAL_CUSTOMER_ID) as POTENTIAL_NUM,\n");
        sb.append("       COUNT(DISTINCT sale.SO_NO_ID) as SALE_NUM ,\n");
        sb.append("       COUNT(DISTINCT sie.SO_INVOICE_ID) as month_num,\n");
        sb.append("       COUNT(DISTINCT sto.VS_STOCK_ID) as send_back_num,\n");
        sb.append("       COUNT(DISTINCT sie.SO_INVOICE_ID)-COUNT(DISTINCT sto.VS_STOCK_ID) as NEWCAR_NUM,\n");
        sb.append("       COUNT(DISTINCT pcus.POTENTIAL_CUSTOMER_NO)/COUNT(DISTINCT vre.VISITING_RECORD_ID)as POTENTIAL_PERCENT,\n");
        sb.append("       COUNT(DISTINCT sale.SO_NO_ID)/COUNT(DISTINCT pcus.POTENTIAL_CUSTOMER_ID) as CONTRACT_PERCENT,\n");
        sb.append("       (COUNT(DISTINCT sie.SO_INVOICE_ID)-COUNT(DISTINCT sto.VS_STOCK_ID))/COUNT(DISTINCT pcus.POTENTIAL_CUSTOMER_ID) as DEAL_PERCENT\n");
        sb.append("      , vre.DEALER_CODE,vre.CUS_SOURCE\n");
        sb.append("from TT_VISITING_RECORD vre\n");
        sb.append("left join   TM_POTENTIAL_CUSTOMER pcus on vre.CUSTOMER_NO=pcus.POTENTIAL_CUSTOMER_NO and pcus.INTENT_LEVEL!=").append(DictCodeConstants.F_LEVEL).append("\n");
        // 建档时间
        sb.append(" and pcus.FOUND_DATE>= ?\n");
        queryList.add(DateUtil.parseDefaultDate(queryParam.get("startdate")));
        sb.append(" and pcus.FOUND_DATE<?\n");
        sb.append(" and vre.DEALER_CODE=pcus.DEALER_CODE\n");
        queryList.add(DateUtil.addOneDay(queryParam.get("enddate")));
        sb.append("left join   TT_SALES_ORDER sale on pcus.POTENTIAL_CUSTOMER_NO= sale.CUSTOMER_NO and sale.SO_STATUS not in (").append(DictCodeConstants.ORDER_CANCEL).append(",").append(DictCodeConstants.ORDER_CANCEL).append(")\n").append(" and vre.DEALER_CODE=sale.DEALER_CODE\n");
        ;

        // sb.append("left join TM_VEHICLE veh on sale.VEHICLE_ID=veh.VEHICLE_ID\n");
        sb.append("left join   TT_SO_INVOICE  sie  on sale.SO_NO_ID=sie.SO_NO_ID and sie.INVOICE_CHARGE_TYPE=").append(DictCodeConstants.INVOICE_CHARGE_TYPE_BUYCAR).append("\n").append(" and sale.DEALER_CODE=sie.DEALER_CODE\n");
        ;

        // 开票日期
        sb.append(" and sie.INVOICE_DATE>= ?\n");
        queryList.add(DateUtil.parseDefaultDate(queryParam.get("startdate")));
        sb.append(" and sie.INVOICE_DATE<?\n");
        queryList.add(DateUtil.addOneDay(queryParam.get("enddate")));

        sb.append("left join   TM_VS_STOCK    sto  on sie.VIN =sto.VIN  and ENTRY_TYPE=").append(DictCodeConstants.ENTRY_TYPE_SENDBACK).append("\n").append(" and sie.DEALER_CODE=sto.DEALER_CODE\n");
        ;
        sb.append("where 1=1\n");
        // 来访时间
        sb.append(" and vre.VISIT_TIME>= ?\n");
        queryList.add(DateUtil.parseDefaultDate(queryParam.get("startdate")));
        sb.append(" and vre.VISIT_TIME<?\n");
        queryList.add(DateUtil.addOneDay(queryParam.get("enddate")));
        if (!StringUtils.isNullOrEmpty(queryParam.get("cusSource"))) {
            sb.append(" and vre.CUS_SOURCE= ?\n");
            queryList.add(Integer.parseInt(queryParam.get("cusSource")));
        }
        sb.append("GROUP BY  vre.DEALER_CODE,vre.CUS_SOURCE\n");
    }

    
    /**
     * 销售顾问业绩统计
    * @author zhanshiwei
    * @date 2016年12月5日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.service.impl.SaleReportService#queryconsultantDeeds(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryconsultantDeeds(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();
        StringBuffer sb = new StringBuffer("");
        this.getConsultantDeedSql(sb, queryParam, queryList);
 
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }
    
    /**
    * 销售顾问业绩统计SQl
    * @author zhanshiwei
    * @date 2016年12月5日
    * @param sb
    * @param queryParam
    * @param queryList
    */
    	
    public void getConsultantDeedSql(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
        sb.append(" select sale.DEALER_CODE,org1.ORG_SHORT_NAME,\n");// -- 部门
        sb.append("       em.EMPLOYEE_NAME, \n");// -- 销售顾问
        sb.append("       COUNT(sale.SO_NO_ID) as RESIDUE_ORDER_NUM,\n");// --  剩余订单数
        sb.append("       COUNT(sale.SO_NO_ID) as VALID_ORDER_NUM, \n");// --  车辆发生数
        sb.append("       COUNT(SO_INVOICE_ID) as INVOICE_NUM,\n");//  开票数
        sb.append("       sum(sale.ORDER_SUM) as  ORDER_SUM, \n");// -- 订单总额
        sb.append("       sum(sale.UPHOLSTER_SUM) as UPHOLSTER_SUM,\n");// -- 装潢金额
        sb.append("       sum(t1.AFTER_DISCOUNT_AMOUNT) as  AFTER_DISCOUNT_AMOUNT,\n"); // -- 工时 实收金额
        sb.append("       sum(t2.AFTER_DISCOUNT_AMOUNT)  as PRAFTER_DISCOUNT_AMOUNT, \n");// -- 配件金额
        sb.append("       sum(sale.SERVICE_SUM) as SERVICE_SUM\n");
        sb.append("from TT_SALES_ORDER sale\n");
        // sb.append("left join TM_VEHICLE veh on sale.VEHICLE_ID=veh.VEHICLE_ID\n");
        sb.append("INNER join TT_SO_INVOICE  sce on sale.SO_NO_ID=sce.SO_NO_ID and sce.INVOICE_CHARGE_TYPE=").append(DictCodeConstants.INVOICE_CHARGE_TYPE_BUYCAR).append("\n");
        // 开票日期
        sb.append(" and sce.INVOICE_DATE>= ?\n");
        queryList.add(DateUtil.parseDefaultDate(queryParam.get("startdate")));
        sb.append(" and sce.INVOICE_DATE<?\n");
        queryList.add(DateUtil.addOneDay(queryParam.get("enddate")));
        sb.append("left join TT_SO_DECRODATE t1 on  sale.SO_NO_ID=t1.SO_NO_ID\n");
        sb.append("left join TT_SO_DECRODATE_PART t2 on sale.SO_NO_ID=t2.SO_NO_ID\n");
        sb.append("left join TM_EMPLOYEE em  on sale.CONSULTANT=em.EMPLOYEE_NO\n");
        sb.append("left join TM_ORGANIZATION  org1 on org1.ORG_CODE=em.ORG_CODE\n");
        sb.append("where  sale.SO_STATUS not in (").append(DictCodeConstants.ORDER_CANCEL).append(",").append(DictCodeConstants.ORDER_CANCEL).append(")\n");
        if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
            sb.append(" and sale.CONSULTANT= ?");
            queryList.add(queryParam.get("consultant"));
        }
        sb.append("GROUP BY sale.CONSULTANT\n");
    }

    /**
     * 车辆进销存
     * 
     * @author zhanshiwei
     * @date 2016年9月29日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.SaleReportService#queryVehicleStock(java.util.Map)
     */

    @Override
    public PageInfoDto queryVehicleStock(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();
        StringBuffer sb = new StringBuffer("");
        this.getVehicleStockSql(sb, queryList, queryParam);
        System.out.println(sb+" $$$$$$$$$$$$$$$$$$$$$$$$$$$44");
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    /**
     * 车辆进销存SQL
     * 
     * @author zhanshiwei
     * @date 2016年11月30日
     * @param sb
     * @param queryList
     * @param queryParam
     * @throws ServiceBizException
     */

    public void getVehicleStockSql(StringBuffer sb, List<Object> queryList,
                                   Map<String, String> queryParam) throws ServiceBizException {
        sb.append("select sto.DEALER_CODE,sto.VS_STOCK_ID,\n");
        sb.append("       sto.VIN,\n");
        sb.append("       sto.OWN_STOCK_STATUS,\n");
        sb.append("       ven.ENTRY_ID,\n");
        sb.append("       ven.FINISHED_DATE,\n");
        sb.append("       vot.DELIVERY_ID,\n");
        sb.append("       vp.BRAND_NAME,vp.SERIES_NAME,vp.MODEL_NAME,vp.CONFIG_NAME,vp.COLOR\n");
        sb.append("      ,SUM(sto.DISPATCHED_STATUS=").append(DictCodeConstants.DISPATCHED_STATUS_INSTOCK).append(") as  EXIST_NUM,\n");
        sb.append("       COUNT(DISTINCT sto.VS_STOCK_ID) OUT_NUM,\n");
        sb.append("       COUNT(DISTINCT ven.ENTRY_ID)    IN_NUM,\n");
        sb.append("      SUM(sto.DISPATCHED_STATUS=").append(DictCodeConstants.DISPATCHED_STATUS_INSTOCK).append(") +COUNT(DISTINCT sto.VS_STOCK_ID)-COUNT(DISTINCT ven.ENTRY_ID) as  MONTH_NUM\n");
        sb.append("from TM_VS_STOCK sto,\n");
        sb.append("     TT_VS_ENTRY ven,\n");
        sb.append("     TT_VS_DELIVERY vot,\n");
        sb.append("     vw_productinfo vp\n");
        sb.append("\n");
        sb.append("where \n");
        sb.append("  sto.PRODUCT_ID = vp.PRODUCT_ID\n");
        sb.append("and   ven.FINISHED_DATE>?\n");
        sb.append("and   ven.FINISHED_DATE<?\n");
        sb.append("and   sto.LATEST_STOCK_OUT_DATE>?\n");
        sb.append("and   sto.LATEST_STOCK_OUT_DATE<?\n");
        sb.append("GROUP BY vp.BRAND_NAME,vp.SERIES_NAME,vp.MODEL_NAME,vp.CONFIG_NAME,vp.COLOR,sto.DEALER_CODE\n");

        queryList.add(DateUtil.parseDefaultDateMonth(queryParam.get("startdate")));
        queryList.add(DateUtil.getPerFirstDayOfMonth(DateUtil.parseDefaultDateMonth(queryParam.get("startdate"))));
        queryList.add(DateUtil.parseDefaultDateMonth(queryParam.get("startdate")));
        queryList.add(DateUtil.getPerFirstDayOfMonth(DateUtil.parseDefaultDateMonth(queryParam.get("startdate"))));
    }

    /**
     * 整车销售报表导出
     * 
     * @author zhanshiwei
     * @date 2016年11月30日
     * @param queryParam
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.SaleReportService#exportVehicleSales(java.util.Map)
     */

    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> exportVehicleSales(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("");
        List<Object> queryList = new ArrayList<Object>();
        this.setVehicleSaleWhere(sb, queryParam, queryList);
        return DAOUtil.findAll(sb.toString(), queryList);

    }

    /**
     * 车辆进销存导出
     * 
     * @author zhanshiwei
     * @date 2016年11月30日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.SaleReportService#exportVehicleStock(java.util.Map)
     */

    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> exportVehicleStock(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("");
        List<Object> queryList = new ArrayList<Object>();
        this.getVehicleStockSql(sb, queryList, queryParam);
        return DAOUtil.findAll(sb.toString(), queryList);
    }

    
    /**
     * 销售统计分析导出
    * @author zhanshiwei
    * @date 2016年11月30日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.service.impl.SaleReportService#exportSaleStatistics(java.util.Map)
    */
    	
    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> exportSaleStatistics(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("");
        List<Object> queryList = new ArrayList<Object>();
        this.getSaleStatisticSql(sb, queryList, queryParam);
        return DAOUtil.findAll(sb.toString(), queryList);
    }

    
    /**
     * 销售顾问业绩统计导出
    * @author zhanshiwei
    * @date 2016年12月5日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.service.impl.SaleReportService#exporconsultantDeeds(java.util.Map)
    */
    	
    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> exporconsultantDeeds(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("");
        List<Object> queryList = new ArrayList<Object>();
        this.getConsultantDeedSql(sb, queryParam, queryList);
        return DAOUtil.findAll(sb.toString(), queryList);
    }

}
