
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.retail
 *
 * @File name : DecroDateServiceImp.java
 *
 * @Author : xukl
 *
 * @Date : 2016年9月5日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月5日    xukl    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.retail.service.ordermanage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.SalesReturnDTO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.SalesPromotionPO;
import com.yonyou.dms.common.domains.PO.basedata.SoAuditingPO;
import com.yonyou.dms.common.domains.PO.basedata.TrackingTaskPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerGatheringPO;
import com.yonyou.dms.common.domains.PO.basedata.TtEsCustomerOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtIntentReceiveMoneyPO;
import com.yonyou.dms.common.domains.PO.basedata.TtIntentSalesOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtOrderStatusUpdatePO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusLinkmanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtWsConfigInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtWsSalesInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.StockInPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehicleInspectPO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.commonSales.service.basedata.OrgDeptService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.DMSTODCS004;
import com.yonyou.dms.gacfca.SEDMS058Impl;
import com.yonyou.dms.gacfca.SEDMS060;
import com.yonyou.dms.gacfca.SEDMS061;
import com.yonyou.dms.gacfca.SOTDCS015NO;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SoDecrodateDTO;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SoDecrodatePartDTO;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SoServicesDTO;
import com.yonyou.dms.retail.domains.PO.ordermanage.SoDecrodatePO;
import com.yonyou.dms.retail.domains.PO.ordermanage.SoDecrodatePartPO;
import com.yonyou.dms.retail.domains.PO.ordermanage.SoServicesPO;

/**
 * SalesOrderServiceImp
 * 
 * @author xukl
 * @date 2016年9月5日
 */
@Service
@SuppressWarnings("rawtypes")
public class SalesOrderServiceImp implements SalesOrderService {
    private static final Logger logger = LoggerFactory.getLogger(SalesOrderServiceImp.class);
    @Autowired
    SEDMS058Impl SEDMS058Impl;
    @Autowired
    SOTDCS015NO SOTDCS015NO;
    @Autowired
    private CommonNoService    commonNoService;

    @Autowired
    private OrgDeptService orgdeptservice;
    @Autowired
    SEDMS060 SEDMS060;
    @Autowired
    DMSTODCS004 DMSTODCS004;
    @Autowired
    SEDMS061 SEDMS061;
    

    /**
     * 销售订单查询
     * 
     * @author xukl
     * @date 2016年10月13日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#qrySalesOrders(java.util.Map)
     */
    @Override
    public PageInfoDto qrySalesOrders(Map<String, String> queryParam) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        loginInfo.getDealerName();
        StringBuffer sql = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();
    sql.append(" SELECT em.USER_NAME,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME,A.CUSTOMER_NAME,A.DEALER_CODE,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.DELIVERY_MODE_ELEC,A.IS_ELECTRICITY,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.EC_ORDER_NO,A.CUS_SOURCE,A.DEPOSIT_AMOUNT,A.EC_ORDER,A.VERIFICATION_TIMEOUT,B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A. VIN,A.SOLD_BY,A.IS_SPEEDINESS, A.SO_NO,A.SHEET_CREATE_DATE,A.SO_STATUS,A.CUSTOMER_NO,A.CONTRACT_NO,A.CONTRACT_DATE,A.OWNED_BY,A.LOCK_USER,A.CERTIFICATE_NO,A.CT_CODE, ");
    sql.append(" A.OWNER_NO, A.OWNER_NAME,A.DELIVERING_DATE,C.IS_DIRECT,A.BUSINESS_TYPE FROM TT_SALES_ORDER A LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY");
    sql.append( " LEFT JOIN TM_VS_STOCK C ON A.DEALER_CODE = C.DEALER_CODE AND A.VIN = C.VIN");
    sql.append( " left  join   tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
    sql.append( " left  join   TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and B.DEALER_CODE=se.DEALER_CODE");
    sql.append( " left  join   TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and se.BRAND_CODE=mo.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and B.DEALER_CODE=mo.DEALER_CODE");
    sql.append( " left  join   tm_configuration pa   on   B.CONFIG_CODE=pa.CONFIG_CODE and mo.MODEL_CODE=pa.MODEL_CODE and mo.SERIES_CODE=pa.SERIES_CODE and mo.BRAND_CODE=pa.BRAND_CODE and B.DEALER_CODE=pa.DEALER_CODE");
    sql.append( " left  join   tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
    sql.append( " left join TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE");                    
    sql.append(" WHERE  A.D_KEY = "+ DictCodeConstants.D_KEY+ " AND NOT EXISTS(SELECT C.SO_NO FROM TT_SALES_ORDER C WHERE C.DEALER_CODE = A.DEALER_CODE AND C.VIN = A.VIN AND C.SO_NO != A.SO_NO "); 
    sql.append(" AND A.BUSINESS_TYPE = 13001004 AND A.ORDER_SORT = 13861002 AND C.BUSINESS_TYPE = 13001001 AND C.SO_STATUS != 13011055 AND C.SO_STATUS != 13011060)");
    sql.append("   AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_CLOSED+ " AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL+ " AND A.SO_STATUS != "
            + DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD
            + " AND A.BUSINESS_TYPE = " + DictCodeConstants.DICT_SO_TYPE_GENERAL
            //Update By AndyTain 2013-3-8 begin
            + " AND NOT EXISTS ("
            + " SELECT SS.SO_NO FROM TT_SALES_ORDER SS WHERE SS.D_KEY = "
            + DictCodeConstants.D_KEY + " AND SS.BUSINESS_TYPE = "
            + DictCodeConstants.DICT_SO_TYPE_RERURN
            + " AND SS.DEALER_CODE = A.DEALER_CODE AND SS.VIN = A.VIN "
            + " AND SS.SO_STATUS != "
            + DictCodeConstants.DICT_SO_STATUS_NOT_AUDIT + " AND A.SO_NO = SS.OLD_SO_NO)"
            +"   AND A.SO_STATUS != "
            //+ DictDataConstant.DICT_SO_STATUS_HAVE_OUT_STOCK
            + DictCodeConstants.DICT_SO_STATUS_CLOSED
            + " AND A.SO_STATUS != "
            + DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL
            + " AND A.SO_STATUS != "
            + DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD
            + " AND A.BUSINESS_TYPE = " + DictCodeConstants.DICT_SO_TYPE_GENERAL                          
            //Update By AndyTain 2013-3-8 begin
            + " AND NOT EXISTS ("
            + " SELECT SS.SO_NO FROM TT_SALES_ORDER SS WHERE  "
            + " SS.DEALER_CODE = A.DEALER_CODE  "
            + " AND (SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_MANAGER_AUDITING
            + " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING
            + " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING
            + " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_MANAGER_REJECT
            + " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_REJECT+")"
            + " AND A.SO_NO = SS.SO_NO AND A.EC_ORDER = 12781001)"
    );
    sql.append("  AND COALESCE(A.DEAL_STATUS,12871001)=12871001 ");
    System.out.println(queryParam.get("customerName"));
    if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
        sql.append(" and A.CUSTOMER_NAME like ?");
        params.add("%" + queryParam.get("customerName") + "%");
    }
    if (!StringUtils.isNullOrEmpty(queryParam.get("soNo"))) {
        sql.append(" and A.SO_NO like ?");
        params.add("%" + queryParam.get("soNo") + "%");
    }
    if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
        sql.append(" and A.SOLD_BY= ?");
        params.add( queryParam.get("soldBy"));
    }
    
    if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
        sql.append(" and A.VIN like ?");
        params.add("%" + queryParam.get("vin") + "%");
    }
    if (!StringUtils.isNullOrEmpty(queryParam.get("brand"))) {
        sql.append(" and B.BRAND_CODE= ?");
        params.add( queryParam.get("brand"));
    }
    if (!StringUtils.isNullOrEmpty(queryParam.get("series"))) {
        sql.append(" and B.SERIES_CODE= ?");
        params.add( queryParam.get("series"));
    }
    if (!StringUtils.isNullOrEmpty(queryParam.get("model"))) {
        sql.append(" and B.MODEL_CODE= ?");
        params.add( queryParam.get("model"));
    }
    if (!StringUtils.isNullOrEmpty(queryParam.get("confi"))) {
        sql.append(" and B.CONFIG_CODE= ?");
        params.add( queryParam.get("confi"));
    }
    sql.append(DAOUtilGF.getOwnedByStr("A", loginInfo.getUserId(), loginInfo.getOrgCode(),  "70101000", loginInfo.getDealerCode()));
    //替换权限202003
     PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
     System.out.println(sql.toString());
        return pageInfoDto;
    }

    @Override
    public List<Map> qrySalesOrdersDetial(Map<String, String> queryParam,String oldSoNo) throws ServiceBizException {
        List<Map> orderList = new ArrayList();
        List<Object> cus = new ArrayList<Object>();
                   
        cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
        cus.add(oldSoNo);
  //      List<SalesOrderPO> salesOrderPO = SalesOrderPO.findBySQL("select * from tt_sales_order where  dealer_code=? and so_no=?  ", cus.toArray());    
        List<Map> salesOrderPO=  DAOUtil.findAll("SELECT * FROM tt_sales_order WHERE  dealer_code=? AND so_no=?", cus);
       if (salesOrderPO.size()>0) {
           orderList = querySalesOrderBySoNo(oldSoNo,salesOrderPO.get(0).get("Business_Type").toString(),salesOrderPO.get(0).get("vin").toString());
         //  PageInfoDto pageInfoDto = DAOUtil.pageQuery(orderList.toString(), queryList);
       }      
        return orderList;
    } 
    
    public List<Map> querySalesOrderBySoNo(String oldSoNo,String businessType,String vin){
        List<Map> result = new ArrayList();
        List<Object> queryList = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        
        if(businessType.equals(DictCodeConstants.DICT_SO_TYPE_RERURN)){
            sql.append(" SELECT DISTINCT ef.PRODUCTING_AREA, ef.ENGINE_NO, ef.CERTIFICATE_NUMBER,(case when TA1.ORDER_PAYED_AMOUNT=0  THEN  (TA1.ORDER_PAYED_AMOUNT-TA1.PENALTY_AMOUNT) ELSE (-1*TA1.ORDER_PAYED_AMOUNT-TA1.PENALTY_AMOUNT) END) AS BACK_PAYED ,TA1.*,B.WHOLESALE_DIRECTIVE_PRICE,B.REMARK as remark1,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE, "); 
            sql.append(" B.PRODUCT_NAME AS PRODUCT_NAME,COALESCE(TA1.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE1 FROM (SELECT A.*,C.VEHICLE_PRICE AS OLD_VEHICLE_PRICE,C.ORDER_PAYED_AMOUNT  AS OLD_ORDER_PAYED_AMOUNT FROM TT_SALES_ORDER A,TT_SALES_ORDER C");
            sql.append(" WHERE 1=1 AND A.OLD_SO_NO = C.SO_NO AND A.dealer_code = C.dealer_code AND A.dealer_code = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "'" );
            sql.append(" AND A.SO_NO = '" + oldSoNo + "' AND A.D_KEY = " + DictCodeConstants.D_KEY + ") TA1  LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B  ON TA1.PRODUCT_CODE = B.PRODUCT_CODE AND TA1.dealer_code = B.dealer_code AND TA1.D_KEY = B.D_KEY"); 
            sql.append(" left join TT_VS_STOCK_ENTRY_ITEM ef on (ef.dealer_code = TA1.dealer_code and ef.vin = TA1.vin) " );
            System.out.println(sql);
            result  = DAOUtil.findAll(sql.toString(), queryList);
        }else{
            sql.append(" SELECT DISTINCT ef.PRODUCTING_AREA, ef.ENGINE_NO, ef.CERTIFICATE_NUMBER,A.*, (case when A.ORDER_PAYED_AMOUNT=0  THEN  (A.ORDER_PAYED_AMOUNT-A.PENALTY_AMOUNT) ELSE (-1*A.ORDER_PAYED_AMOUNT-A.PENALTY_AMOUNT) END) AS BACK_PAYED,  B.WHOLESALE_DIRECTIVE_PRICE,B.REMARK as remark1,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,0 AS OLD_VEHICLE_PRICE,0  AS OLD_ORDER_PAYED_AMOUNT,B.CONFIG_CODE, B.PRODUCT_NAME AS PRODUCT_NAME,B.COLOR_CODE as color_code1,O.ORG_NAME " );
            if(null != businessType && businessType.equals(DictCodeConstants.DICT_SO_TYPE_GENERAL)){
                sql.append(" ,TA1.WS_NO,TA1.SALES_ITEM_ID,TA1.WS_TYPE");
            }
            if (DictCodeConstants.DICT_SO_TYPE_ALLOCATION.equals(businessType)) {
                sql.append(" ,TAB1.DEALER_CODE ");
            }
                            
            sql.append( " FROM TT_SALES_ORDER A  LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B  ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.dealer_code = B.dealer_code AND A.D_KEY= B.D_KEY LEFT JOIN TM_USER U ON A.dealer_code = U.dealer_code AND A.SOLD_BY= U.USER_ID  LEFT JOIN TM_ORGANIZATION O ON A.dealer_code = O.dealer_code AND U.ORG_CODE=O.ORG_CODE ");
            if(null != businessType && businessType.equals(DictCodeConstants.DICT_SO_TYPE_GENERAL)){
                sql.append(" LEFT JOIN ( SELECT DISTINCT N.dealer_code,N.SALES_ITEM_ID,M.WS_NO,N.SO_NO,N.VIN,K.WS_TYPE FROM TT_PO_CUS_WHOLESALE K, TT_WS_CONFIG_INFO M,TT_WS_SALES_INFO N WHERE M.dealer_code = N.dealer_code AND M.ITEM_ID = N.ITEM_ID AND K.dealer_code = M.dealer_code AND K.WS_NO = M.WS_NO AND K.WS_STATUS = " + DictCodeConstants.DICT_WHOLESALE_STATUS_PASS);
                if (!StringUtils.isNullOrEmpty(FrameworkUtil.getLoginInfo().getDealerCode())) {
                    sql.append(" and N.dealer_code = ?");
                    queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
                }
                if (!StringUtils.isNullOrEmpty(oldSoNo)) {
                    sql.append(" and N.SO_NO = ?");
                    queryList.add(oldSoNo);
                }
                if (!StringUtils.isNullOrEmpty(vin)) {
                    sql.append(" and N.VIN = ?");
                    queryList.add(vin);
                }
                sql.append(") TA1 ON A.dealer_code = TA1.dealer_code AND A.SO_NO = TA1.SO_NO ");
            }
            if (DictCodeConstants.DICT_SO_TYPE_ALLOCATION.equals(businessType)) {
                sql.append(" LEFT JOIN TM_PART_CUSTOMER TAB1 ON A.dealer_code=TAB1.dealer_code and A.CONSIGNEE_CODE=TAB1.CUSTOMER_CODE ");
            }
            sql.append(" left join TT_VS_STOCK_ENTRY_ITEM ef on (ef.dealer_code = a.dealer_code and ef.vin = a.vin) ");
            sql.append(" WHERE 1=1 AND  A.D_KEY = " + DictCodeConstants.D_KEY);
            if (!StringUtils.isNullOrEmpty(oldSoNo)) {
                sql.append(" and A.SO_NO = ?");
                queryList.add(oldSoNo);
            }
            if (!StringUtils.isNullOrEmpty(FrameworkUtil.getLoginInfo().getDealerCode())) {
                sql.append(" and A.dealer_code = ?");
                queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
            }
            System.out.println(sql);
            result  = DAOUtil.findAll(sql.toString(), queryList);
        }
        return result;
    }
    
    /**
    * @author LiGaoqi
    * @date 2017年2月13日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#getIntentSalesOrder(java.util.Map)
    */
    	
    @Override
    public PageInfoDto getIntentSalesOrder(Map<String, String> queryParam) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sql = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();
        sql.append("SELECT t.DEALER_CODE,t.vin,INTENT_SO_NO, t.CUSTOMER_NO,CUSTOMER_NAME, FETCH_CAR_DATE, SOLD_BY,  SO_STATUS, IS_SALES_ORDER, t.REMARK,ADDRESS,CUSTOMER_PHONE,CT_CODE,CERTIFICATE_NO, ORDER_SUM,t.COLOR_CODE,t.CUSTOMER_NO,t.PAY_MODE,t.PRODUCT_CODE,t1.BRAND_CODE, t1.SERIES_CODE, t1.MODEL_CODE, t1.PRODUCT_NAME,t1.CONFIG_CODE,t.CREATED_AT, ");
        sql.append(" t2.BRAND_NAME,t3.SERIES_NAME,t4.MODEL_NAME,t5.CONFIG_NAME,t.VEHICLE_PRICE,t.VER,t.AUDITED_BY,t.RE_AUDITED_BY,t.ORDER_RECEIVABLE_SUM, t.ORDER_PAYED_AMOUNT, t.PAY_OFF ");
        sql.append("FROM TT_INTENT_SALES_ORDER t LEFT JOIN TM_VS_PRODUCT t1 ON t.PRODUCT_CODE = t1.PRODUCT_CODE AND t.DEALER_CODE = t1.DEALER_CODE  LEFT JOIN TM_BRAND t2 ON t1.BRAND_CODE = t2.BRAND_CODE AND t2.DEALER_CODE = t1.DEALER_CODE ");
        sql.append("LEFT JOIN TM_SERIES t3 ON t3.SERIES_CODE = t1.SERIES_CODE AND t3.DEALER_CODE = t1.DEALER_CODE LEFT JOIN TM_MODEL t4 ON t4.MODEL_CODE = t1.MODEL_CODE AND t4.DEALER_CODE = t1.DEALER_CODE LEFT JOIN TM_CONFIGURATION t5 ON t5.CONFIG_CODE = t1.CONFIG_CODE AND t5.DEALER_CODE = t1.DEALER_CODE WHERE t.DEALER_CODE = '"+loginInfo.getDealerCode()+"'");
        if (!StringUtils.isNullOrEmpty(queryParam.get("intentSoNo"))) {
            sql.append( " AND INTENT_SO_NO LIKE '%"+queryParam.get("intentSoNo")+"%'");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sql.append( " AND CUSTOMER_NAME LIKE '%"+queryParam.get("customerName")+"%'");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))) {
            sql.append( " AND CUSTOMER_NO LIKE '%"+queryParam.get("customerNo")+"%'");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy")))
        {
            sql.append(" AND SOLD_BY = "+queryParam.get("soldBy")+"");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("soStatus")))
        {
            sql.append(" AND SO_STATUS = "+queryParam.get("soStatus")+"");
        } 
        if (!StringUtils.isNullOrEmpty(queryParam.get("isPayOff")))
        {
            sql.append(" AND PAY_OFF = "+queryParam.get("isPayOff")+" ");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("isSalesOrder")))
        {
            sql.append(" AND IS_SALES_ORDER = "+queryParam.get("isSalesOrder")+" ");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("fetchCar_startdate"))) {
            sql.append(" AND FETCH_CAR_DATE >= ? ");
            params.add(DateUtil.parseDefaultDate(queryParam.get("fetchCar_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("fetchCar_enddate"))) {
            sql.append(" AND FETCH_CAR_DATE < ? ");
            params.add(DateUtil.addOneDay(queryParam.get("fetchCar_enddate")));
        }

        return DAOUtil.pageQuery(sql.toString(), params);
    }

    


    
    /**
    * @author LiGaoqi
    * @date 2017年2月13日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#queryMatchVehicleByCodeDetail(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryMatchVehicleByCodeDetail(Map<String, String> queryParam) throws ServiceBizException {
        String storageCode = queryParam.get("storageCode");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        if(StringUtils.isNullOrEmpty(queryParam.get("storageCode"))){
            String str = "";
            
            StringBuilder sqlSb = new StringBuilder("select * from tm_user_CTRL where DEALER_CODE= ? AND USER_ID= ? AND CTRL_CODE LIKE '4010%' ");
            List<Object> params = new ArrayList<Object>();
            params.add(loginInfo.getUserId());
            params.add(loginInfo.getDealerCode());
            List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
            if(list!=null&&list.size()>0){
                for(int i=0;i<list.size();i++){
                    if(i>0){
                        str =str+",";
                    }
                    str = str+"'"+list.get(i).get("CTRL_CODE").toString().substring(4, list.get(i).get("CTRL_CODE").toString().length())+"'";
                }
            }
            storageCode = str;
        }else{
            storageCode="'" + storageCode + "'";
        }
        
        StringBuffer sql = new StringBuffer("");
        List<Object> paramsList = new ArrayList<Object>();
    
            //本地交车方式
            sql.append(" SELECT a.PURCHASE_PRICE,A.DEALER_CODE,A.VIN,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE, ef.PRODUCTING_AREA, ef.CERTIFICATE_NUMBER, ");
            sql.append(" B.PRODUCT_CODE,B.PRODUCT_NAME,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,case when IS_PRICE_ADJUSTED=12781001 then a.DIRECTIVE_PRICE else B.DIRECTIVE_PRICE end as DIRECTIVE_PRICE ,case when IS_PRICE_ADJUSTED=12781001 then a.DIRECTIVE_PRICE else B.DIRECTIVE_PRICE end as VEHICLE_PRICE ,");
            sql.append(" A.STORAGE_CODE,A.ENGINE_NO,A.STOCK_STATUS,A.MAR_STATUS,A.IS_SECONDHAND,A.IS_TEST_DRIVE_CAR,A.IS_VIP,A.IS_CONSIGNED,A.IS_PROMOTION, A.HAS_CERTIFICATE,A.LATEST_STOCK_OUT_DATE,");
            sql.append(" case when (A.LATEST_STOCK_IN_DATE is not null and A.LATEST_STOCK_IN_DATE>A.FIRST_STOCK_IN_DATE) then A.LATEST_STOCK_IN_DATE else A.FIRST_STOCK_IN_DATE end LATEST_STOCK_IN_DATE,");
            sql.append(" A.MANUFACTURE_DATE,A.REMARK," );
            if (!StringUtils.isNullOrEmpty(queryParam.get("wsNo1"))){
                
                sql.append("wsinfo.WHOLESALE_DIRECTIVE_PRICE,");
            }else{
                sql.append("  b.WHOLESALE_DIRECTIVE_PRICE, ");
                
            }
                    sql.append(" A.STORAGE_POSITION_CODE,A.IS_REFITTING_CAR, c.SO_STATUS,c.SO_NO,case  when c.SO_NO is null then '12781003'   else case  when (COALESCE(c.SO_STATUS,0) <> 0) and (COALESCE(c.SO_STATUS,0) = "+DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK+" or  COALESCE(c.SO_STATUS,0)="+DictCodeConstants.DICT_SO_STATUS_CLOSED+")  then   ");
                    sql.append("  '"+DictCodeConstants.DICT_IS_YES+"' else ");
                    sql.append("  case when COALESCE(c.SO_STATUS,0)<> 0 and COALESCE(c.SO_STATUS,0)="+DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL+" then "+" '12781003'  else  '" +DictCodeConstants.DICT_IS_NO+"'  end ");
                    sql.append("  end   ");
                    sql.append("  end  as SQZT,c.ORDER_SORT,"); 
                    sql.append(" CASE WHEN A.STOCK_STATUS = 13041003 THEN '12781001' ELSE '12781002' END AS IS_ON_PASSAGE_CAR ");
                    sql.append(" from   TM_VS_STOCK A  left join ("+CommonConstants.VM_VS_PRODUCT+") B  on  A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE and a.d_key = b.d_key ");
                    sql.append(" left join ( select * from   TT_SALES_ORDER where   ORDER_SORT="+DictCodeConstants.FDICT_SALES_ORDER_SORT_PRE+" and BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_SERVICE+"    ) c ");
                    sql.append("  on c.vin=a.vin and c.PRODUCT_CODE=a.PRODUCT_CODE and A.DEALER_CODE = c.DEALER_CODE and a.d_key= c.d_key  left join TT_VS_STOCK_ENTRY_ITEM ef on (ef.DEALER_CODE = a.DEALER_CODE and ef.vin = a.vin and ef.SE_NO=a.SE_NO )");
                    if (!StringUtils.isNullOrEmpty(queryParam.get("wsNo1"))){
                        sql.append("    left join  (   select a.DEALER_CODE,a.WS_APP_TYPE,b.PRODUCT_CODE, b.BRAND_CODE,b.SERIES_CODE,b.MODEL_CODE,b.CONFIG_CODE,a.WS_NO,   case when  (a.WS_APP_TYPE=13511001)  or (a.WS_APP_TYPE=13511004) then  b.WS_INTENT_PRICE  ");
                        sql.append("  when  (a.WS_APP_TYPE=13511002)  or (a.WS_APP_TYPE=13511003) then  ");
                        sql.append(" case  when "); 
                        sql.append(" COALESCE(b.WS_INTENT_PRICE,0)");
                        sql.append(" > ");
                        sql.append(" COALESCE(b.WHOLESALE_DIRECTIVE_PRICE,0)"); 
                        sql.append(" then  b.WHOLESALE_DIRECTIVE_PRICE  else b.WS_INTENT_PRICE  end  ");
                        sql.append("  else 0 end  WHOLESALE_DIRECTIVE_PRICE   ");
                        sql.append("   from TT_PO_CUS_WHOLESALE a,TT_WS_CONFIG_INFO b    ");
                        sql.append("   where a.WS_NO=b.WS_NO and a.DEALER_CODE=b.DEALER_CODE  and a.ws_no='"+queryParam.get("wsNo1")+"'  )    ");
                        sql.append(" wsinfo   on  b.BRAND_CODE=wsinfo.BRAND_CODE  and b.SERIES_CODE=wsinfo.SERIES_CODE and  b.MODEL_CODE=wsinfo.MODEL_CODE  and  b.CONFIG_CODE=wsinfo.CONFIG_CODE   ");
                        sql.append("  and wsinfo.DEALER_CODE= b.DEALER_CODE   ");
                    }

                    sql.append(" where A.D_KEY = "+ DictCodeConstants.D_KEY );
                            sql.append( " AND (A.STOCK_STATUS = "
                            + DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE
                            + " OR A.STOCK_STATUS = "
                            + DictCodeConstants.DICT_STORAGE_STATUS_ON_WAY
                            + " OR A.STOCK_STATUS = "
                            + DictCodeConstants.DICT_STORAGE_STATUS_LEND_TO
                            + ")"
                            + " AND A.DISPATCHED_STATUS = "
                            + DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED
                            + " AND A.IS_PURCHASE_RETURN = "
                            + DictCodeConstants.DICT_IS_NO);
                                                   
                    sql.append( " AND A.IS_VIP = "
                    + DictCodeConstants.DICT_IS_NO
                     );
            if(!StringUtils.isNullOrEmpty(queryParam.get("isRefittingCar"))){
                sql.append(" and A.IS_REFITTING_CAR= ? ");
                paramsList.add(queryParam.get("isRefittingCar"));
            }
            if(!StringUtils.isNullOrEmpty(storageCode)){
                sql.append( " AND A.STORAGE_CODE IN (" + storageCode + ")");
            }
            if(!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
                sql.append(" and B.BRAND_CODE= ? ");
                paramsList.add(queryParam.get("brandCode"));
            }
            if(!StringUtils.isNullOrEmpty(queryParam.get("brand1"))){
                sql.append(" and B.BRAND_CODE= ? ");
                paramsList.add(queryParam.get("brand1"));
            }
            if(!StringUtils.isNullOrEmpty(queryParam.get("series1"))){
                sql.append(" and B.SERIES_CODE= ? ");
                paramsList.add(queryParam.get("series1"));
            }
            if(!StringUtils.isNullOrEmpty(queryParam.get("model1"))){
                sql.append(" and B.MODEL_CODE= ? ");
                paramsList.add(queryParam.get("model1"));
            }
            if(!StringUtils.isNullOrEmpty(queryParam.get("config1"))){
                sql.append(" and B.CONFIG_CODE= ? ");
                paramsList.add(queryParam.get("config1"));
            }
            if(!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))){
                sql.append(" and B.SERIES_CODE= ? ");
                paramsList.add(queryParam.get("seriesCode"));
            }
            if(!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))){
                sql.append(" and B.MODEL_CODE= ? ");
                paramsList.add(queryParam.get("modelCode"));
            }
            if(!StringUtils.isNullOrEmpty(queryParam.get("configCode"))){
                sql.append(" and B.CONFIG_CODE= ? ");
                paramsList.add(queryParam.get("configCode"));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
                sql.append(" and A.VIN like ?");
                paramsList.add("%" + queryParam.get("vin") + "%");
            }      
        sql.append(" AND A.IS_LOCK="+DictCodeConstants.DICT_IS_NO+""); 
        return  DAOUtil.pageQuery(sql.toString(), paramsList);
    }




    /**
     * 经理审核查询销售订单
     * 
     * @author xukl
     * @date 2016年11月1日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#qrySRSForMangAudit(java.util.Map)
     */
    @Override
    public PageInfoDto qrySRSForMangAudit(Map<String, String> queryParam) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        List<Object> params = new ArrayList<Object>();
        StringBuffer str = getSQL(queryParam, params);
        str.append(" and tso.AUDITED_MANAGER = ? and tso.SO_STATUS = ?\n");
        params.add(loginInfo.getEmployeeNo());
        params.add(DictCodeConstants.MANAGER_IN_REVIEW);
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(str.toString(), params);
        return pageInfoDto;
    }

    /**
     * 财务审核查询销售订单
     * 
     * @author xukl
     * @date 2016年11月1日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#qrySRSForMangAudit(java.util.Map)
     */
    @Override
    public PageInfoDto qrySRSForFincAudit(Map<String, String> queryParam) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        List<Object> params = new ArrayList<Object>();
        StringBuffer str = getSQL(queryParam, params);
        str.append(" and tso.AUDITED_FINANCE = ? and tso.SO_STATUS = ?\n");
        params.add(loginInfo.getEmployeeNo());
        params.add(DictCodeConstants.FINANCE_IN_REVIEW);
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(str.toString(), params);
        return pageInfoDto;
    }

    /**
     * 销售退回查询销售订单
     * 
     * @author xukl
     * @date 2016年11月1日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#qrySRSForMangAudit(java.util.Map)
     */
    @Override
    public PageInfoDto qrySRSForSellBack(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
         if (null != queryParam.get("businessType") && queryParam.get("businessType").equals("secondcar")) {
        	 
             
             sql.append(" SELECT A.DEALER_CODE, A.CUSTOMER_NAME,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME,");

             sql.append(" B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A. VIN,A.SOLD_BY,A.IS_SPEEDINESS,A.DELIVERY_MODE_ELEC,A.IS_ELECTRICITY,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.EC_ORDER_NO,A.CUS_SOURCE,A.DEPOSIT_AMOUNT,A.EC_ORDER,A.VERIFICATION_TIMEOUT,");



             sql.append(" B.CONFIG_CODE,D.CUS_SOURCE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A. VIN,A.SOLD_BY,em.USER_NAME,tu.USER_NAME AS LOCK_NAME,");

             sql.append(" A.SO_NO,A.SHEET_CREATE_DATE,A.SO_STATUS,A.CUSTOMER_NO,A.CONTRACT_NO,A.CONTRACT_DATE,A.OWNED_BY,A.LOCK_USER,A.CERTIFICATE_NO,A.CT_CODE, ");
             sql.append( " A.OWNER_NO, A.OWNER_NAME,A.DELIVERING_DATE,C.IS_DIRECT,A.BUSINESS_TYPE FROM TT_SALES_ORDER A  ");
             sql.append(" LEFT JOIN  ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.dealer_code = B.dealer_code AND A.D_KEY = B.D_KEY");
             sql.append(" LEFT JOIN TM_VS_STOCK C ON A.dealer_code = C.dealer_code AND A.VIN = C.VIN");
             sql.append(" LEFT JOIN TM_POTENTIAL_CUSTOMER D ON A.dealer_code = D.dealer_code AND A.CUSTOMER_NO = D.CUSTOMER_NO");
             sql.append(" left  join   tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
             sql.append(" left  join   TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and B.DEALER_CODE=se.DEALER_CODE");
             sql.append(" left  join   TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and mo.BRAND_CODE=se.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and B.DEALER_CODE=mo.DEALER_CODE");
             sql.append(" left  join   tm_configuration pa   on   B.CONFIG_CODE=pa.CONFIG_CODE and pa.BRAND_CODE=mo.BRAND_CODE and pa.SERIES_CODE=pa.SERIES_CODE and pa.MODEL_CODE=mo.MODEL_CODE and B.DEALER_CODE=pa.DEALER_CODE");
             sql.append(" left  join   tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
             sql.append( " left join TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE"); 
             sql.append( " left join TM_USER tu  on A.LOCK_USER=tu.USER_ID AND A.DEALER_CODE=tu.DEALER_CODE"); 
             sql.append(" WHERE a.so_status !=13011055 and a.so_status !=13011060 and a.business_type !=13001005 and a.business_type !=13001004"); 
             sql.append(" and  not EXISTS (select 1 from TT_REPLACE_REPAY sm where A.dealer_code=sm.dealer_code and sm.so_no=a.so_no)");
             sql.append(" and exists (select 1 from TT_SO_INVOICE sv where sv.dealer_code=a.dealer_code and sv.vin=a.vin");
             sql.append(" and sv.INVOICE_CHARGE_TYPE="+DictCodeConstants.DICT_INVOICE_FEE_VEHICLE +" )");
             sql.append(" and a.IS_PERMUTED=12781001 and PERMUTED_VIN is not null " );
         }else
         {
        	 System.out.println("22222");
     sql.append(" SELECT  A.DEALER_CODE,A.CUSTOMER_NAME,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.DELIVERY_MODE_ELEC,A.IS_ELECTRICITY,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.EC_ORDER_NO,A.CUS_SOURCE,A.DEPOSIT_AMOUNT,A.EC_ORDER,A.VERIFICATION_TIMEOUT,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME,");
     sql.append(" B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A. VIN,A.SOLD_BY,A.IS_SPEEDINESS,em.USER_NAME,");
     sql.append(" A.SO_NO,A.SHEET_CREATE_DATE,A.SO_STATUS,A.CUSTOMER_NO,A.CONTRACT_NO,A.CONTRACT_DATE,A.OWNED_BY,A.LOCK_USER,tu.USER_NAME AS LOCK_NAME,A.CERTIFICATE_NO,A.CT_CODE, ");
     sql.append(" A.OWNER_NO, A.OWNER_NAME,A.DELIVERING_DATE,C.IS_DIRECT,A.BUSINESS_TYPE FROM TT_SALES_ORDER A  ");
     sql.append(" LEFT JOIN  ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.dealer_code = B.dealer_code AND A.D_KEY = B.D_KEY");
     sql.append(" LEFT JOIN TM_VS_STOCK C ON A.dealer_code = C.dealer_code AND A.VIN = C.VIN");
     sql.append(" left  join   tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
     sql.append(" left  join   TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and B.DEALER_CODE=se.DEALER_CODE");
     sql.append(" left  join   TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and mo.BRAND_CODE=se.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and B.DEALER_CODE=mo.DEALER_CODE");
     sql.append(" left  join   tm_configuration pa   on   B.CONFIG_CODE=pa.CONFIG_CODE and pa.BRAND_CODE=mo.BRAND_CODE and pa.SERIES_CODE=pa.SERIES_CODE and pa.MODEL_CODE=mo.MODEL_CODE and B.DEALER_CODE=pa.DEALER_CODE");
     sql.append(" left  join   tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
     sql.append( " left join TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE"); 
     sql.append( " left join TM_USER tu  on A.LOCK_USER=tu.USER_ID AND A.DEALER_CODE=tu.DEALER_CODE"); 
     sql.append(" WHERE  A.D_KEY = "+ DictCodeConstants.D_KEY+ " AND NOT EXISTS(SELECT C.SO_NO FROM TT_SALES_ORDER C WHERE C.dealer_code = A.dealer_code AND C.VIN = A.VIN AND C.SO_NO != A.SO_NO "); 
     sql.append(" AND A.BUSINESS_TYPE = 13001004 AND A.ORDER_SORT = 13861002 AND C.BUSINESS_TYPE = 13001001 AND C.SO_STATUS != 13011055 AND C.SO_STATUS != 13011060)");
         

     if (queryParam.get("soStatus") != null
             && queryParam.get("soStatus").trim().equals(
                     DictCodeConstants.DICT_SO_STATUS_CLOSED)) {
    	 
         // 新建退回单时，查询状态已关单的一般销售单据
         sql.append(" AND A.SO_STATUS = " + queryParam.get("soStatus")+ " AND A.BUSINESS_TYPE = "+ DictCodeConstants.DICT_SO_TYPE_GENERAL+ " AND NOT EXISTS(SELECT S.SO_NO FROM TT_SALES_ORDER S");
         sql.append(" WHERE S.BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_RERURN+" AND S.dealer_code=A.dealer_code ");
         sql.append(" AND S.D_KEY = "+DictCodeConstants.D_KEY+" AND S.SO_STATUS !="+DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL+" AND A.VIN = S.VIN AND A.SO_NO = S.OLD_SO_NO)");
         sql.append(" AND NOT EXISTS (SELECT 1 FROM TT_SO_INVOICE SV WHERE sv.dealer_code=a.dealer_code and sv.vin=a.vin and SV.INVOICE_CHARGE_TYPE="+DictCodeConstants.DICT_INVOICE_FEE_VEHICLE+")");

     } else {
    	 System.out.println("33333");

         if(null != queryParam.get("businessType") && queryParam.get("businessType").equals(DictCodeConstants.DICT_SO_TYPE_RERURN)){
             //查询退回订单
        	 System.out.println("11111111");
             sql.append(" AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_CLOSED+ " AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED+ " AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING+ " AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING+ " AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_REJECT+" AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD+ "  AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL+ " AND A.BUSINESS_TYPE = " + queryParam.get("businessType"));
         }else{
        	 System.out.println("444444");
             sql.append("   AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_CLOSED+ " AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL+ " AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD+ " AND A.BUSINESS_TYPE = " + queryParam.get("businessType")+ " AND NOT EXISTS (");
             sql.append(" SELECT SS.SO_NO FROM TT_SALES_ORDER SS WHERE SS.D_KEY = "+ DictCodeConstants.D_KEY + " AND SS.BUSINESS_TYPE = "+ DictCodeConstants.DICT_SO_TYPE_RERURN+ " AND SS.dealer_code = A.dealer_code AND SS.VIN = A.VIN ");
             sql.append(" AND SS.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_NOT_AUDIT + " AND A.SO_NO = SS.OLD_SO_NO) AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_CLOSED+ " AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL+ " AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD+ " AND A.BUSINESS_TYPE = " + queryParam.get("businessType")+ " AND NOT EXISTS (");
             sql.append(" SELECT SS.SO_NO FROM TT_SALES_ORDER SS WHERE  ");
             sql.append(" SS.dealer_code = A.dealer_code  ");
             sql.append(" AND (SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_MANAGER_AUDITING+ " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING+ " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING+ " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_MANAGER_REJECT + " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_REJECT+")"+ " AND A.SO_NO = SS.SO_NO AND A.EC_ORDER = 12781001)");
             sql.append("  AND A.DEAL_STATUS=12871001 ");
             //}
         }

     }
     
     if (null != queryParam.get("businessType") && queryParam.get("businessType").equals("allorder")){//做了一个公用查询销售订单查询页面，需过滤业务类型为销售退回和服务销售类的以及单据状态为已取消、已退回类的 

         sql = new StringBuffer("");
         sql.append(" SELECT  A.DEALER_CODE,A.CUSTOMER_NAME,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.IS_SPEEDINESS,A.DELIVERY_MODE_ELEC,A.IS_ELECTRICITY,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.EC_ORDER_NO,A.CUS_SOURCE,A.DEPOSIT_AMOUNT,A.EC_ORDER,A.VERIFICATION_TIMEOUT,");
         sql.append(" br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME,tu.USER_NAME AS LOCK_NAME,B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A. VIN,A.SOLD_BY,");
         sql.append(" A.SO_NO,A.SHEET_CREATE_DATE,A.SO_STATUS,A.CUSTOMER_NO,A.CONTRACT_NO,A.CONTRACT_DATE,A.OWNED_BY,em.USER_NAME,A.LOCK_USER,A.CERTIFICATE_NO,A.CT_CODE, ");
         sql.append(" A.OWNER_NO, A.OWNER_NAME,A.DELIVERING_DATE,C.IS_DIRECT,A.BUSINESS_TYPE FROM TT_SALES_ORDER A  ");
         sql.append(" LEFT JOIN  ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.dealer_code = B.dealer_code AND A.D_KEY = B.D_KEY");
         sql.append(" LEFT JOIN TM_VS_STOCK C ON A.dealer_code = C.dealer_code AND A.VIN = C.VIN");
         sql.append(" left  join   tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
         sql.append(" left  join   TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and B.DEALER_CODE=se.DEALER_CODE");
         sql.append(" left  join   TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and mo.BRAND_CODE=se.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and B.DEALER_CODE=mo.DEALER_CODE");
         sql.append(" left  join   tm_configuration pa   on   B.CONFIG_CODE=pa.CONFIG_CODE and pa.BRAND_CODE=mo.BRAND_CODE and pa.SERIES_CODE=pa.SERIES_CODE and pa.MODEL_CODE=mo.MODEL_CODE and B.DEALER_CODE=pa.DEALER_CODE");
         sql.append(" left  join   tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
         sql.append( " left join TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE"); 
         sql.append( " left join TM_USER tu  on A.LOCK_USER=tu.USER_ID AND A.DEALER_CODE=tu.DEALER_CODE"); 
         sql.append(" left join Tt_Customer_Intent_Detail D on A.dealer_code = D.dealer_code and a.EC_ORDER_NO = d.EC_ORDER_NO");
         sql.append(" WHERE a.so_status !=13011055 and a.so_status !=13011060 and a.business_type !=13001005 and a.business_type !=13001004 "); 
         sql.append(" and A.D_KEY = "+ DictCodeConstants.D_KEY );
         //按揭单那里用，不能查出做过按揭单的销售单号

         sql.append(" and not EXISTS (select sm.so_no from TT_MORT_ORDER sm where A.dealer_code=sm.dealer_code and (sm.SO_STATUS=10791001 or sm.SO_STATUS=10791002) AND A.SO_NO = SM.SO_NO ) ");


     }else{

                
               if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
                   sql.append(" and A.VIN like ?");
                    params.add("%" + queryParam.get("vin") + "%");
                }
               if (!StringUtils.isNullOrEmpty(queryParam.get("contractNo"))) {
                   sql.append(" and A.CONTRACT_NO like ?");
                    params.add("%" + queryParam.get("contractNo") + "%");
                }
               if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
                   sql.append(" and A.CUSTOMER_NAME like ?");
                    params.add("%" + queryParam.get("customerName") + "%");
                }
               if (!StringUtils.isNullOrEmpty(queryParam.get("soNo"))) {
                   sql.append(" and A.SO_NO like ?");
                    params.add("%" + queryParam.get("soNo") + "%");
                }
               
               sql.append(" and A.dealer_code ="+FrameworkUtil.getLoginInfo().getDealerCode()+"");
          	 sql.append(DAOUtilGF.getOwnedByStr("A", FrameworkUtil.getLoginInfo().getUserId(), FrameworkUtil.getLoginInfo().getOrgCode(),"70108000", FrameworkUtil.getLoginInfo().getDealerCode()));}

    }
        
          return  DAOUtil.pageQuery(sql.toString(), params);
        
    }

    @Override
    public PageInfoDto querySubmitSalesHis(Map<String, String> queryParam,String soNo) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT B.*,C.USER_NAME AS SUBMIT_NAME ,(case when (B.AUDITED_BY=11111111111111) then B.Auditing_Postil else A.user_name end)USER_NAME FROM TT_SO_AUDITING B  left join   TM_USER A on   B.AUDITED_BY = A.USER_ID and  a.DEALER_CODE=b.DEALER_CODE left join   TM_USER C on   B.SUBMIT_AUDITED_BY = C.USER_ID and  c.DEALER_CODE=b.DEALER_CODE   ");
        sql.append(" WHERE  b.D_KEY = 0"+ " AND B.DEALER_CODE = '" + loginInfo.getDealerCode()+ "' AND B.SO_NO = '" + soNo +"'");
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
        return pageInfoDto;
    } 
    
    
    /**
    * @author LiGaoqi
    * @date 2017年2月13日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#queryPoCusWholesaleInfo(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryPoCusWholesaleInfo(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT "+DictCodeConstants.DICT_IS_NO+ " AS IS_SELECTED,A.DEALER_CODE,A.WS_NO,A.WS_APP_TYPE as WS_TYPE,A.WS_STATUS,A.WS_APP_TYPE,");
        sql.append(" A.FAX,A.CUSTOMER_NO,A.LARGE_CUSTOMER_NO,A.IS_FAX_RECEIVED,A.FAX_TIME,A.CUSTOMER_NAME,A.CUSTOMER_CHARACTER,");
        sql.append(" A.CONTACTOR_NAME,A.POSITION_NAME,A.PHONE,A.MOBILE,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE,B.COLOR_CODE,");
        sql.append(" A.IS_SECOND_REPORT,A.WS_AUDITOR,A.WS_AUDITING_REMARK,A.AUDITING_DATE,A.SUBMIT_TIME,A.VER,A.OWNED_BY,");
        sql.append(" A.CONFIGURE_REMARK,A.PROJECT_REMARK,A.WS_CONTENT,A.DLR_PRINCIPAL,p.REBUY_CUSTOMER_TYPE,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,");

        sql.append("A.DLR_PRINCIPAL_PHONE,A.POS_NAME,A.POS_LINKMAN,A.POS_LINKMAN_PHONE,");
        sql.append(" A.ORGAN_TYPE_CODE,B.WS_INTENT_PRICE,B.SUG_RETAIL_PRICE,p.CUSTOMER_TYPE,p.CONTACTOR_PHONE,p.CONTACTOR_MOBILE,p.ADDRESS,p.CT_CODE,p.CERTIFICATE_NO,p.CUS_SOURCE");
        sql.append("  FROM TT_PO_CUS_WHOLESALE A, TT_WS_CONFIG_INFO B,tm_potential_customer p,tm_brand br,TM_SERIES se,TM_MODEL mo,tm_configuration pa");
        sql.append(" WHERE  A.DEALER_CODE = B.DEALER_CODE AND A.WS_NO = B.WS_NO AND p.DEALER_CODE=a.DEALER_CODE and a.customer_no=p.customer_no");
        sql.append(" AND B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
        sql.append(" AND B.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and B.DEALER_CODE=se.DEALER_CODE");
        sql.append(" AND B.MODEL_CODE =mo.MODEL_CODE and se.BRAND_CODE=mo.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and B.DEALER_CODE=mo.DEALER_CODE");
        sql.append(" AND B.CONFIG_CODE =pa.CONFIG_CODE and mo.MODEL_CODE=pa.MODEL_CODE and mo.SERIES_CODE=pa.SERIES_CODE and mo.BRAND_CODE=pa.BRAND_CODE and B.DEALER_CODE=pa.DEALER_CODE");
        sql.append(" and A.DEALER_CODE='"+loginInfo.getDealerCode()+ "' AND A.D_KEY="+DictCodeConstants.D_KEY +" AND A.WS_STATUS=15981003 ");
        if (!StringUtils.isNullOrEmpty(queryParam.get("wsNo")))
        {
            sql.append(" AND A.WS_NO LIKE ?");
            queryList.add("%" + queryParam.get("wsNo") + "%");
        }
      if (!StringUtils.isNullOrEmpty(queryParam.get("customerName")))
        {
            sql.append(" AND A.CUSTOMER_NAME LIKE ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
      if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo")))
        {
            sql.append(" AND A.CUSTOMER_NO LIKE ?");
            queryList.add("%" + queryParam.get("customerNo") + "%");
        }
      if (!StringUtils.isNullOrEmpty(queryParam.get("contactorName")))
        {
            sql.append(" AND A.CONTACTOR_NAME LIKE ?");
            queryList.add("%" + queryParam.get("contactorName") + "%");
        }
      if (!StringUtils.isNullOrEmpty(queryParam.get("mobile")))
        {
            sql.append(" AND A.MOBILE LIKE ?");
            queryList.add("%" + queryParam.get("mobile") + "%");
        }
      if (!StringUtils.isNullOrEmpty(queryParam.get("submitTime_startdate"))) {
          sql.append(" and A.SUBMIT_TIME>= ?");
          queryList.add(DateUtil.parseDefaultDate(queryParam.get("submitTime_startdate")));
      }
      if (!StringUtils.isNullOrEmpty(queryParam.get("submitTime_enddate"))) {
          sql.append(" and A.SUBMIT_TIME<?");
          queryList.add(DateUtil.addOneDay(queryParam.get("submitTime_enddate")));
      }
    
        
        return DAOUtil.pageQuery(sql.toString(), queryList);
    }

    /**
     * 销售退回选择销售订单查询
     * 
     * @author xukl
     * @date 2016年11月1日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#qrySRSForMangAudit(java.util.Map)
     */
    @Override
    public PageInfoDto qrySRSForSBKSlt(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer str = getSQL(queryParam, params);
        str.append(" and tso.SO_STATUS = ?\n");
        params.add(DictCodeConstants.ORDER_COMPLETED);
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(str.toString(), params);
        return pageInfoDto;
    }

    /**
     * 结算收款查询销售订单
     * 
     * @author xukl
     * @date 2016年11月1日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#qrySRSForMangAudit(java.util.Map)
     */
    @Override
    public PageInfoDto qrySRSForSettCo(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer str = getSQL(queryParam, params);
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(str.toString(), params);
        return pageInfoDto;
    }

    /**
     * 开票登记查询销售订单
     * 
     * @author xukl
     * @date 2016年11月1日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#qrySRSForMangAudit(java.util.Map)
     */
    @Override
    public PageInfoDto qrySRSForInvoice(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer("");
         List<Object> params = new ArrayList<Object>();
         if (null != queryParam.get("businessType") && queryParam.get("businessType").equals("secondcar")) {
           
             sql.append(" SELECT A.DEALER_CODE, A.CUSTOMER_NAME,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,");

             sql.append(" B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A. VIN,A.SOLD_BY,A.IS_SPEEDINESS,A.DELIVERY_MODE_ELEC,A.IS_ELECTRICITY,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.EC_ORDER_NO,A.CUS_SOURCE,A.DEPOSIT_AMOUNT,A.EC_ORDER,A.VERIFICATION_TIMEOUT,");



             sql.append(" B.CONFIG_CODE,D.CUS_SOURCE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A. VIN,A.SOLD_BY,");

             sql.append(" A.SO_NO,A.SHEET_CREATE_DATE,A.SO_STATUS,A.CUSTOMER_NO,A.CONTRACT_NO,A.CONTRACT_DATE,A.OWNED_BY,A.LOCK_USER,A.CERTIFICATE_NO,A.CT_CODE, ");
             sql.append(" A.OWNER_NO, A.OWNER_NAME,A.DELIVERING_DATE,C.IS_DIRECT,C.ENGINE_NO,C.CERTIFICATE_NUMBER,A.BUSINESS_TYPE FROM TT_SALES_ORDER A  ");
             sql.append(" LEFT JOIN  ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.dealer_code = B.dealer_code AND A.D_KEY = B.D_KEY");
             sql.append(" LEFT JOIN TM_VS_STOCK C ON A.dealer_code = C.dealer_code AND A.VIN = C.VIN");
             sql.append(" LEFT JOIN TM_POTENTIAL_CUSTOMER D ON A.dealer_code = D.dealer_code AND A.CUSTOMER_NO = D.CUSTOMER_NO");
             sql.append(" WHERE a.so_status !=13011055 and a.so_status !=13011060 and a.business_type !=13001005 and a.business_type !=13001004");
             sql.append(" and  not EXISTS (select 1 from TT_REPLACE_REPAY sm where A.dealer_code=sm.dealer_code and sm.so_no=a.so_no)");
             sql.append(" and exists (select 1 from TT_SO_INVOICE sv where sv.dealer_code=a.dealer_code and sv.vin=a.vin");
             sql.append(" and sv.INVOICE_CHARGE_TYPE="+DictCodeConstants.DICT_INVOICE_FEE_VEHICLE +" )");
             sql.append(" and a.IS_PERMUTED=12781001 and PERMUTED_VIN is not null " );
         }else
         {
     sql
             .append(" SELECT  A.DEALER_CODE,A.CUSTOMER_NAME,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.DELIVERY_MODE_ELEC,A.IS_ELECTRICITY,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.EC_ORDER_NO,A.CUS_SOURCE,A.DEPOSIT_AMOUNT,A.EC_ORDER,A.VERIFICATION_TIMEOUT,");
     sql.append(" B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A. VIN,A.SOLD_BY,A.IS_SPEEDINESS,");
     sql.append(" A.SO_NO,A.SHEET_CREATE_DATE,A.SO_STATUS,A.CUSTOMER_NO,A.CONTRACT_NO,A.CONTRACT_DATE,A.OWNED_BY,A.LOCK_USER,A.CERTIFICATE_NO,A.CT_CODE, ");
     sql.append(" A.OWNER_NO, A.OWNER_NAME,A.DELIVERING_DATE,C.IS_DIRECT,A.BUSINESS_TYPE FROM TT_SALES_ORDER A  ");
     sql.append(" LEFT JOIN  ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.dealer_code = B.dealer_code AND A.D_KEY = B.D_KEY");
     sql.append(" LEFT JOIN TM_VS_STOCK C ON A.dealer_code = C.dealer_code AND A.VIN = C.VIN");
     sql.append(" WHERE  A.D_KEY = "+ DictCodeConstants.D_KEY+ " AND NOT EXISTS(SELECT C.SO_NO FROM TT_SALES_ORDER C WHERE C.dealer_code = A.dealer_code AND C.VIN = A.VIN AND C.SO_NO != A.SO_NO "); 
     sql.append(" AND A.BUSINESS_TYPE = 13001004 AND A.ORDER_SORT = 13861002 AND C.BUSINESS_TYPE = 13001001 AND C.SO_STATUS != 13011055 AND C.SO_STATUS != 13011060)");
         
                      
     if (queryParam.get("soStatus") != null
             && queryParam.get("soStatus").trim().equals(
                     DictCodeConstants.DICT_SO_STATUS_CLOSED)) {
         // 新建退回单时，查询状态已关单的一般销售单据
         sql.append(" AND A.SO_STATUS = " + queryParam.get("soStatus")+ " AND A.BUSINESS_TYPE = "+ DictCodeConstants.DICT_SO_TYPE_GENERAL+ " AND NOT EXISTS(SELECT S.SO_NO FROM TT_SALES_ORDER S");
         sql.append(" WHERE S.BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_RERURN+" AND S.dealer_code=A.dealer_code ");
         sql.append(" AND S.D_KEY = "+DictCodeConstants.D_KEY+" AND S.SO_STATUS !="+DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL+" AND A.VIN = S.VIN AND A.SO_NO = S.OLD_SO_NO)");
         sql.append(" AND NOT EXISTS (SELECT 1 FROM TT_SO_INVOICE SV WHERE sv.dealer_code=a.dealer_code and sv.vin=a.vin and SV.INVOICE_CHARGE_TYPE="+DictCodeConstants.DICT_INVOICE_FEE_VEHICLE+")");

     } else {

         if(null != queryParam.get("businessType") && queryParam.get("businessType").equals(DictCodeConstants.DICT_SO_TYPE_RERURN)){
             //查询退回订单
             sql.append(" AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_CLOSED+ " AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL+ " AND A.BUSINESS_TYPE = " + queryParam.get("businessType"));
         }else{
             
             sql.append("   AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_CLOSED+ " AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL+ " AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD+ " AND A.BUSINESS_TYPE = " + queryParam.get("businessType")+ " AND NOT EXISTS (");
             sql.append(" SELECT SS.SO_NO FROM TT_SALES_ORDER SS WHERE SS.D_KEY = "+ DictCodeConstants.D_KEY + " AND SS.BUSINESS_TYPE = "+ DictCodeConstants.DICT_SO_TYPE_RERURN+ " AND SS.dealer_code = A.dealer_code AND SS.VIN = A.VIN "+ " AND SS.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_NOT_AUDIT + " AND A.SO_NO = SS.OLD_SO_NO)");
             sql.append("   AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_CLOSED+ " AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL+ " AND A.SO_STATUS != "+ DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD+ " AND A.BUSINESS_TYPE = " + queryParam.get("businessType")+ " AND NOT EXISTS (");
             sql.append(" SELECT SS.SO_NO FROM TT_SALES_ORDER SS WHERE  ");
             sql.append(" SS.dealer_code = A.dealer_code  ");
             sql.append(" AND (SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_MANAGER_AUDITING+ " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING+ " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING+ " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_MANAGER_REJECT+ " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_REJECT+")" + " AND A.SO_NO = SS.SO_NO AND A.EC_ORDER = 12781001)"
             );
             
             sql.append("  AND A.DEAL_STATUS=12871001 ");
             //}
         }

     }
     
     if (null != queryParam.get("businessType") && queryParam.get("businessType").equals("allorder")){//做了一个公用查询销售订单查询页面，需过滤业务类型为销售退回和服务销售类的以及单据状态为已取消、已退回类的 

         sql = new StringBuffer("");
         sql.append(" SELECT  A.DEALER_CODE,A.CUSTOMER_NAME,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.IS_SPEEDINESS,A.DELIVERY_MODE_ELEC,A.IS_ELECTRICITY,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.EC_ORDER_NO,A.CUS_SOURCE,A.DEPOSIT_AMOUNT,A.EC_ORDER,A.VERIFICATION_TIMEOUT,");
         sql.append(" B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A. VIN,A.SOLD_BY,");
         sql.append(" A.SO_NO,A.SHEET_CREATE_DATE,A.SO_STATUS,A.CUSTOMER_NO,A.CONTRACT_NO,A.CONTRACT_DATE,A.OWNED_BY,A.LOCK_USER,A.CERTIFICATE_NO,A.CT_CODE, ");
         sql.append(" A.OWNER_NO, A.OWNER_NAME,A.DELIVERING_DATE,C.IS_DIRECT,A.BUSINESS_TYPE FROM TT_SALES_ORDER A  ");
         sql.append(" LEFT JOIN  ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.dealer_code = B.dealer_code AND A.D_KEY = B.D_KEY");
         sql.append(" LEFT JOIN TM_VS_STOCK C ON A.dealer_code = C.dealer_code AND A.VIN = C.VIN");
         sql.append(" left join Tt_Customer_Intent_Detail D on A.dealer_code = D.dealer_code and a.EC_ORDER_NO = d.EC_ORDER_NO");
         sql.append(" WHERE a.so_status !=13011055 and a.so_status !=13011060 and a.business_type !=13001005 and a.business_type !=13001004  and A.D_KEY = "+ DictCodeConstants.D_KEY );
         //按揭单那里用，不能查出做过按揭单的销售单号

         sql.append(" and not EXISTS (select sm.so_no from TT_MORT_ORDER sm where A.dealer_code=sm.dealer_code and (sm.SO_STATUS=10791001 or sm.SO_STATUS=10791002) AND A.SO_NO = SM.SO_NO ) ");


     }else{
         DAOUtilGF.getOwnedByStr("A", FrameworkUtil.getLoginInfo().getUserId(), FrameworkUtil.getLoginInfo().getOrgCode(),"", FrameworkUtil.getLoginInfo().getDealerCode());}

                
               if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
                   sql.append(" and A.VIN like ?");
                    params.add("%" + queryParam.get("vin") + "%");
                }
               if (!StringUtils.isNullOrEmpty(queryParam.get("contractNo"))) {
                   sql.append(" and A.CONTRACT_NO like ?");
                    params.add("%" + queryParam.get("contractNo") + "%");
                }
               if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
                   sql.append(" and A.CUSTOMER_NAME like ?");
                    params.add("%" + queryParam.get("customerName") + "%");
                }
               if (!StringUtils.isNullOrEmpty(queryParam.get("soNo"))) {
                   sql.append(" and A.SO_NO like ?");
                    params.add("%" + queryParam.get("soNo") + "%");
                }
               sql.append(" and A.dealer_code ="+FrameworkUtil.getLoginInfo().getDealerCode()+"");
         
    }
          return  DAOUtil.pageQuery(sql.toString(), params);
    }

    /**
     * 设置SQL
     * 
     * @author xukl
     * @date 2016年11月1日
     * @param queryParam
     * @param params
     * @return
     */
    private StringBuffer getSQL(Map<String, String> queryParam, List<Object> params) {

				

				
    	
        StringBuffer sb = new StringBuffer("SELECT te.EMPLOYEE_NO, tso.* FROM TT_SALES_ORDER tso LEFT JOIN TM_POTENTIAL_CUSTOMER tpc ON tso.CUSTOMER_NO = tpc.CUSTOMER_NO and tpc.DEALER_CODE = tso.DEALER_CODE LEFT JOIN tm_vs_stock tvs ON tvs.VS_STOCK_ID = tso.VS_STOCK_ID and tvs.DEALER_CODE = tso.DEALER_CODE LEFT JOIN tm_employee te ON tso.CONSULTANT = te.EMPLOYEE_NO and te.DEALER_CODE = tso.DEALER_CODE WHERE 1 = 1");

        if (!StringUtils.isNullOrEmpty(queryParam.get("orgCode"))) {
            sb.append(" and te.ORG_CODE = ? ");
            params.add(queryParam.get("orgCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
            sb.append(" and tso.CONSULTANT = ?");
            params.add(queryParam.get("consultant"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("soNo"))) {
            sb.append(" and tso.SO_NO like ?");
            params.add("%" + queryParam.get("soNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and tso.CUSTOMER_NAME like ? ");
            params.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and tvs.VIN like ?");
            params.add("%" + queryParam.get("vin") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
            sb.append(" and vwp.BRAND_CODE = ? ");
            params.add(queryParam.get("brandCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
            sb.append(" and vwp.SERIES_CODE = ?");
            params.add(queryParam.get("seriesCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))) {
            sb.append(" and vwp.MODEL_CODE = ?");
            params.add(queryParam.get("modelCode"));
        }
        return sb;
    }

    /**
     * 新增
     * 
     * @author xukl
     * @date 2016年9月9日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#qryVIN(java.util.Map)
     */

    @SuppressWarnings("unchecked")
	@Override
    public Map addSalesOrders(SalesReturnDTO salesOrderDTO, String soNo,String fIsChanged) throws ServiceBizException {
        Map<String, String> queryParam =new HashMap();
        if(soNo!=null && !"".equals(soNo)){
            List<Object> cus = new ArrayList<Object>();
            cus.add(salesOrderDTO.getOldSoNo());           
            cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
            List<SalesOrderPO> salesOrderPO = SalesOrderPO.findBySQL("select * from tt_sales_order where so_no=?  and dealer_code=? and D_Key=0", cus.toArray());            
            if (salesOrderPO != null && salesOrderPO.size()>0)//lim
            {
                SalesOrderPO checkInvoice =(SalesOrderPO) salesOrderPO.get(0);
                //add by jll 2014-10-21 如果在销售订单界面订单不取消 这时候开票，开票后再回到销售订单界面点击保存按钮就会出现问题(销售订单的状态变了)
                if(checkInvoice.get("Invoice_Tag")!=null &&  checkInvoice.get("Invoice_Tag")=="12781001"){
                    throw new ServiceBizException("销售订单的状态已经变更,请重新查询下该订单！"); 
                }
            }               
            
        }
        if(!salesOrderDTO.getBusinessType().equals(DictCodeConstants.DICT_SO_TYPE_RERURN) && !salesOrderDTO.getBusinessType().equals(DictCodeConstants.DICT_SO_TYPE_ALLOCATION)){         
            if((StringUtils.isNullOrEmpty(salesOrderDTO.getCustomerNo())  || StringUtils.isNullOrEmpty(salesOrderDTO.getCustomerName()))
                    && (StringUtils.isNullOrEmpty(salesOrderDTO.getOwnerNo())  || StringUtils.isNullOrEmpty(salesOrderDTO.getOwnerName()))){
                throw new ServiceBizException("销售订单检查客户编号为空"); 
            }
            if (!StringUtils.isNullOrEmpty(salesOrderDTO.getCustomerNo()) 
                    && (StringUtils.isNullOrEmpty(salesOrderDTO.getOwnerNo()))){
                List<Object> cus = new ArrayList<Object>();
                cus.add(salesOrderDTO.getCustomerNo());           
                cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
                List<PotentialCusPO> potentialCusPo = PotentialCusPO.findBySQL("select * from Tm_potential_customer where customer_no=?  and dealer_code=? and D_Key=0", cus.toArray());
                if( null == potentialCusPo ){
                    throw new ServiceBizException("销售订单检查客户编号为空"); 
                }
            }
            else{
                List<Object> cus = new ArrayList<Object>();
                cus.add(salesOrderDTO.getOwnerNo());           
                cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
                List<CarownerPO> carownerPO = CarownerPO.findBySQL("select * from Tm_owner where owner_no=?  and dealer_code=? and D_Key=0", cus.toArray());
                if( null == carownerPO ){
                    throw new ServiceBizException("销售订单检查客户编号为空"); 
            }
            }    
        }
        SalesOrderPO oPo = new SalesOrderPO();
        oPo = this.getSales1(salesOrderDTO,fIsChanged);
        
     
        
        if(null != salesOrderDTO.getBusinessType() && salesOrderDTO.getBusinessType().equals(DictCodeConstants.DICT_SO_TYPE_GENERAL) 
                && !StringUtils.isNullOrEmpty(salesOrderDTO.getCustomerNo())){
            List<Object> cus5 = new ArrayList<Object>();
            cus5.add(salesOrderDTO.getCustomerNo());           
            cus5.add(FrameworkUtil.getLoginInfo().getDealerCode());
            cus5.add(DictCodeConstants.DICT_IS_NO);
            List<TtCusIntentPO> inlist = TtCusIntentPO.findBySQL("select * from tt_Customer_intent where customer_no=?  and dealer_code=? and Intent_Finished=? and D_Key=0", cus5.toArray());
            TtCusIntentPO inpo;          
            if(inlist.size() > 0){
                inpo = (TtCusIntentPO)inlist.get(0);
                oPo.setLong("Intent_Id",(inpo.get("Intent_Id")));          
            }
            
        }
        oPo.saveIt();
        if (!StringUtils.isNullOrEmpty(salesOrderDTO.getOldSoNo())){
            SalesOrderPO  oldOrderPo = new SalesOrderPO();
            List<Object> cus = new ArrayList<Object>();
            cus.add(salesOrderDTO.getOldSoNo());           
            cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
            List<SalesOrderPO> oldOrderPolist = SalesOrderPO.findBySQL("select * from tt_sales_order where so_no=?  and dealer_code=? and D_Key=0", cus.toArray());            
            if (oldOrderPolist!=null &&  oldOrderPolist.size()> 0){
                oldOrderPo =(SalesOrderPO)oldOrderPolist.get(0);
                if (!StringUtils.isNullOrEmpty(oldOrderPo.get("Pay_Off")) 
                        && oldOrderPo.get("Pay_Off")!=DictCodeConstants.DICT_IS_YES){          
                    oldOrderPo.setInteger("Pay_Off",DictCodeConstants.DICT_IS_YES);//是否结清
                    oldOrderPo.setDate("Balance_Time",new Date()) ;//结算时间LQian
                    oldOrderPo.saveIt();           
                }
            }
            
        }
        soNo = oPo.getString("So_No");
        queryParam.put("SO_NO", soNo);
     
        return queryParam;
    }
    @SuppressWarnings("unchecked")
	public Map addSalesOrdersSubmit(SalesReturnDTO salesOrderDTO) throws ServiceBizException {
        Map<String, String> queryParam =new HashMap();
      
        List<Object> cus = new ArrayList<Object>();
        cus.add(salesOrderDTO.getOldSoNo());    
        cus.add(salesOrderDTO.getVer());
        cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
        SalesOrderPO po =SalesOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),salesOrderDTO.getSoNo()); 
        List<SalesOrderPO> poResult = SalesOrderPO.findBySQL("select * from tt_sales_order where so_no=?  and ver=? and dealer_code=? and D_Key=0", cus.toArray()); 
        if (salesOrderDTO.getSoStatus().equals(DictCodeConstants.DICT_SO_STATUS_MANAGER_AUDITING)){
            po.setString("Audited_By", salesOrderDTO.getAuditedBy());
            po.setString("So_Status", salesOrderDTO.getSoStatus());
        }
        if (salesOrderDTO.getSoStatus().equals(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING)){
            po.setString("Re_audited_By", salesOrderDTO.getReAuditedBy());
            if (!StringUtils.isNullOrEmpty(poResult.get(0).get("business_type")) && poResult.get(0).get("business_type").toString().equals(DictCodeConstants.DICT_SO_TYPE_RERURN) ){
                po.setInteger("So_Status",DictCodeConstants.DICT_SO_STATUS_WAIT_UNTREAT_IN_STOCK);
            }
            po.setInteger("So_Status", salesOrderDTO.getSoStatus());
        }
        po.setString("SO_NO", salesOrderDTO.getSoNo()) ;
        po.setInteger("D_Key",DictCodeConstants.D_KEY);
        po.setInteger("Is_Upload",DictCodeConstants.DICT_IS_NO);
        if(poResult.get(0).get("Is_Firset_Summit").toString().equals(DictCodeConstants.DICT_IS_NO)){
            po.setInteger("Is_Firset_Summit",DictCodeConstants.DICT_IS_YES);
        }
        po.setDate("Last_Submit_Audit_Date", new Date());

        List<Object> cus1 = new ArrayList<Object>();
        cus1.add(poResult.get(0).get("Product_Code"));    
        cus1.add(DictCodeConstants.D_KEY);
        cus1.add(FrameworkUtil.getLoginInfo().getDealerCode());
        List<VsProductPO> productList = VsProductPO.findBySQL("select * from Tm_Vs_Product where Product_Code=?  and D_KEY=? and dealer_code=? and D_Key=0", cus1.toArray()); 
        if(productList != null && productList.size() > 0 && poResult.get(0).get("Is_Firset_Summit").toString().equals(DictCodeConstants.DICT_IS_NO)){
            VsProductPO productDeltail = (VsProductPO) productList.get(0);
            //销售顾问价
            if(!StringUtils.isNullOrEmpty(poResult.get(0).get("Sales_Consultant_Price"))){
                po.setString("Sales_Consultant_Price",productDeltail.get("Sales_Consultant_Price"));
            }
            //总经理价
            if(!StringUtils.isNullOrEmpty(poResult.get(0).get("General_Manager_Price"))){
                po.setString("General_Manager_Price",productDeltail.get("General_Manager_Price"));
            }
            //经理价
            if(!StringUtils.isNullOrEmpty(poResult.get(0).get("Manager_Price"))){
                po.setString("Manager_Price",productDeltail.get("Manager_Price"));
            }
            //最低限价
            if(!StringUtils.isNullOrEmpty(poResult.get(0).get("Discount_Rate"))){
                po.setString("Discount_Rate",productDeltail.get("Mininum_Price"));
            }
        }
        po.saveIt();
       

        SoAuditingPO apo = new SoAuditingPO();
     
        apo.setString("So_No",salesOrderDTO.getSoNo());    
        apo.setLong("Submit_Audited_By",FrameworkUtil.getLoginInfo().getUserId());
        apo.setLong("Audited_By",salesOrderDTO.getAuditedBy());
        apo.setDate("Commit_Time",new Date());
        
        if(salesOrderDTO.getSoStatus().equals(DictCodeConstants.DICT_SO_STATUS_MANAGER_AUDITING)){
            apo.setInteger("Audited_By_Identity",DictCodeConstants.DICT_AUDITING_TYPE_MANAGE);
        }
        else if(salesOrderDTO.getSoStatus().equals(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING)){
            apo.setInteger("Audited_By_Identity",DictCodeConstants.DICT_AUDITING_TYPE_FINANCE);
        }   
        System.out.println(apo);
//        if (TREATMENT_ADVICE!=null &&!"".equals(TREATMENT_ADVICE)){
//            apo.setTreatmentAdvice(TREATMENT_ADVICE);               
//        }
        apo.saveIt();
        return queryParam; 
    }
 @SuppressWarnings("unchecked")
public List<Map> querysoNo(String soNo,Map<String, String> queryParam) throws ServiceBizException {
	  List<Map> orderList = new ArrayList();
      List<Object> cus = new ArrayList<Object>();
                 
      cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
      cus.add(soNo);
      List<Map> salesOrderPO=  DAOUtil.findAll("SELECT * FROM tt_sales_order WHERE  dealer_code=? AND so_no=?", cus);
      if (salesOrderPO.size()>0) {
    	  orderList = querySalesOrderBySoNo(salesOrderPO.get(0).get("SO_NO").toString(),salesOrderPO.get(0).get("Business_Type").toString(),salesOrderPO.get(0).get("vin").toString());
    	  //  PageInfoDto pageInfoDto = DAOUtil.pageQuery(orderList.toString(), queryList);
      }      
       return orderList;
        

    }
    /**
     * 更新整车库存状态为已配车
     * 
     * @author xukl
     * @date 2016年10月13日
     * @param id
     */
    private void updateStockStatus(Integer id) throws ServiceBizException {
      /*  InvtrMaTncePO invtrMaTncePO = InvtrMaTncePO.findById(id);
        invtrMaTncePO.setInteger("DISPATCHED_STATUS", DictCodeConstants.ALREADY_DISPATCHED_STATUS);
        invtrMaTncePO.saveIt();*/
    }

    /**
     * 更新整车库存状态为未配车
     * 
     * @author xukl
     * @date 2016年10月13日
     * @param id
     */
    private void updStockStatus(Integer id) throws ServiceBizException {
      /*  InvtrMaTncePO invtrMaTncePO = InvtrMaTncePO.findById(id);
        invtrMaTncePO.setString("DISPATCHED_STATUS", DictCodeConstants.NOT_DISPATCHED_STATUS);
        invtrMaTncePO.saveIt();*/
    }

    /**
     * 生成客户跟进记录
     * 
     * @author xukl
     * @date 2016年9月26日
     * @param customerNo
     */
    private void addFollowRecord(String customerNo, String consultant) {
        StringBuffer sbsql = new StringBuffer("SELECT ttt.DEALER_CODE, ttt.INTENT_LEVEL, ttt.TASK_NAME, ttt.PROM_WAY, ttt.INTERVAL_DAYS, ttt.IS_VALID, ttt.TASK_ID from TM_TRACKING_TASK ttt where ttt.INTENT_LEVEL = ?");
        List<Object> params = new ArrayList<Object>();
        params.add(DictCodeConstants.O_LEVEL);
        List list = DAOUtil.findAll(sbsql.toString(), params);
        if (!CommonUtils.isNullOrEmpty(list)) {
            PotentialCusPO ptcp = qryPotentialCusPOByCode(customerNo);
            SalesPromotionPO sppo = new SalesPromotionPO();
            StringBuffer sb = new StringBuffer();
            sb.append("select TASK_ID,DEALER_CODE,TASK_NAME,INTERVAL_DAYS from  TM_TRACKING_TASK\n");
            sb.append(" where IS_VALID =? \n");
            sb.append(" and INTENT_LEVEL=? \n");
            List<Object> queryList = new ArrayList<Object>();
            queryList.add(DictCodeConstants.STATUS_IS_YES);
            queryList.add(DictCodeConstants.O_LEVEL);
            List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
            sppo.setDate("SCHEDULE_DATE",
                         DateUtil.addDay(new Date(), Integer.parseInt(result.get(0).get("interval_days").toString())));// 计划日期
            sppo.setString("TASK_NAME", result.get(0).get("task_name").toString());
            sppo.setString("CONSULTANT", consultant);
            sppo.setLong("ORGANIZATION_ID", orgdeptservice.getOrganizationIdByEmployeeNo(sppo.get("consultant").toString()));

            sppo.setString("PRIOR_GRADE", DictCodeConstants.O_LEVEL);
            sppo.setInteger("PROM_RESULT", DictCodeConstants.TRACKING_RESULT_NOT);// 未跟进
            sppo.setLong("POTENTIAL_CUSTOMER_ID", ptcp.getLongId());
            sppo.saveIt();
        }
    }

    /**
     * 更新潜客意向级别
     * 
     * @author xukl
     * @date 2016年9月20日
     */
    private void updateCstIntentLevel(String customerNo) throws ServiceBizException {
        PotentialCusPO ptcp = qryPotentialCusPOByCode(customerNo);
        ptcp.setInteger("INTENT_LEVEL", DictCodeConstants.O_LEVEL);
        ptcp.saveIt();
    }

    /**
     * 根据潜客编码查潜客信息
     * 
     * @author xukl
     * @date 2016年9月26日
     * @param customerNo
     * @return
     */

    private PotentialCusPO qryPotentialCusPOByCode(String customerNo) throws ServiceBizException {
        StringBuffer sbf = new StringBuffer("select tpc.DEALER_CODE,tpc.POTENTIAL_CUSTOMER_ID from TM_POTENTIAL_CUSTOMER tpc where tpc.POTENTIAL_CUSTOMER_NO = ?");
        List<String> params = new ArrayList<String>();
        params.add(customerNo);
        Map map = DAOUtil.findFirst(sbf.toString(), params);
        PotentialCusPO ptcp = PotentialCusPO.findById(map.get("POTENTIAL_CUSTOMER_ID"));
        return ptcp;
    }

    /**
     * 设置属性
     * 
     * @author xukl
     * @date 2016年9月18日
     * @param salesOrderPO
     * @param salesOrderDTO
     * @return
     */

    private SalesOrderPO setSalesOrderPO(SalesOrderPO salesOrderPO,
                                         SalesOrderDTO salesOrderDTO) throws ServiceBizException {
        salesOrderPO.setString("SO_NO", salesOrderDTO.getSoNo());// 销售单号
        salesOrderPO.setString("CUSTOMER_NO", salesOrderDTO.getCustomerNo());// 客户编号
        salesOrderPO.setString("CUSTOMER_NAME", salesOrderDTO.getCustomerName());// 客户名称
        salesOrderPO.setDate("SHEET_CREATE_DATE", salesOrderDTO.getSheetCreateDate());// 开单日期
        salesOrderPO.setString("SHEET_CREATED_BY", salesOrderDTO.getSheetCreatedBy());// 开单人
        salesOrderPO.setDate("CONTRACT_DATE", salesOrderDTO.getContractDate());// 签约日期
        salesOrderPO.setString("CONTRACT_NO", salesOrderDTO.getContractNo());// 合约编号
        salesOrderPO.setDouble("CONTRACT_EARNEST", salesOrderDTO.getContractEarnest());// 合约定金
        salesOrderPO.setString("CONSULTANT", salesOrderDTO.getConsultant());// 销售顾问
        salesOrderPO.setLong("ORGANIZATION_ID", orgdeptservice.getOrganizationIdByEmployeeNo(salesOrderPO.get("consultant").toString()));

        salesOrderPO.setDate("DELIVERING_DATE", salesOrderDTO.getDeliveringDate());// 预交车时间
        salesOrderPO.setString("PAY_MODE", salesOrderDTO.getPayMode());// 支付方式
        salesOrderPO.setString("VS_STOCK_ID", salesOrderDTO.getVsStockId());// 库存id
        salesOrderPO.setInteger("PRODUCT_ID", salesOrderDTO.getProductId());
        salesOrderPO.setDouble("VEHICLE_PRICE", salesOrderDTO.getVehiclePrice());// 车辆价格
        salesOrderPO.setDouble("UPHOLSTER_SUM", salesOrderDTO.getUpholsterSum());// 装潢金额
        salesOrderPO.setDouble("PRESENT_SUM", salesOrderDTO.getPresentSum());// 赠送金额
        salesOrderPO.setDouble("SERVICE_SUM", salesOrderDTO.getServiceSum());// 服务项目金额
        salesOrderPO.setDouble("OFFSET_AMOUNT", salesOrderDTO.getOffsetAmount());// 抵扣金额
        salesOrderPO.setDouble("ORDER_SUM", salesOrderDTO.getOrderSum());// 订单总额
        salesOrderPO.setDouble("ORDER_RECEIVABLE_SUM", salesOrderDTO.getOrderReceivableSum());// 订单应收

        salesOrderPO.setString("OLD_SO_NO", salesOrderDTO.getOldSoNo());// 原销售订单号
        salesOrderPO.setString("RETURN_REASON", salesOrderDTO.getReturnReason());// 退回原因
        salesOrderPO.setDouble("PENALTY_AMOUNT", salesOrderDTO.getPenaltyAmount());// 违约金
        salesOrderPO.setDouble("RO_RECEIVABLE_SUM", salesOrderDTO.getRoReceivableSum());// 退单应收
        return salesOrderPO;
    }

    /**
     * 设置装潢项目明细属性
     * 
     * @author xukl
     * @date 2016年9月23日
     * @param soDecrodateDTO
     * @return
     * @throws ServiceBizException
     */
    private SoDecrodatePO setDecrodate(SoDecrodateDTO soDecrodateDTO) throws ServiceBizException {
        SoDecrodatePO soDecrodatePO = new SoDecrodatePO();
        soDecrodatePO.setString("LABOUR_CODE", soDecrodateDTO.getLabourCode());// 维修项目代码
        soDecrodatePO.setString("LABOUR_NAME", soDecrodateDTO.getLabourName());// 维修项目名称
        soDecrodatePO.setDouble("STD_LABOUR_HOUR", soDecrodateDTO.getStdLabourHour());// 标准工时
        soDecrodatePO.setDouble("LABOUR_PRICE", soDecrodateDTO.getLabourPrice());// 工时单价
        soDecrodatePO.setDouble("DISCOUNT", soDecrodateDTO.getDiscount());// 折扣率
        soDecrodatePO.setDouble("AFTER_DISCOUNT_AMOUNT", soDecrodateDTO.getAfterDiscountAmount());// 实收金额
        soDecrodatePO.setString("REMARK", soDecrodateDTO.getRemark());// 备注
        return soDecrodatePO;
    }

    /**
     * 设置装潢材料明细属性
     * 
     * @author xukl
     * @date 2016年9月23日
     * @param soDecrodatePartDTO
     * @return
     * @throws ServiceBizException
     */

    private SoDecrodatePartPO setDecrodatePart(SoDecrodatePartDTO soDecrodatePartDTO) throws ServiceBizException {
        SoDecrodatePartPO soDecrodatePartPO = new SoDecrodatePartPO();
        soDecrodatePartPO.setString("STORAGE_CODE", soDecrodatePartDTO.getStorageCode());// 仓库代码
        soDecrodatePartPO.setString("STORAGE_POSITION_CODE", soDecrodatePartDTO.getStoragePositionCode());// 库位
        soDecrodatePartPO.setString("PART_NO", soDecrodatePartDTO.getPartNo());// 配件代码
        soDecrodatePartPO.setString("PART_NAME", soDecrodatePartDTO.getPartName());// 配件名称
        soDecrodatePartPO.setDouble("PART_QUANTITY", soDecrodatePartDTO.getPartQuantity());// 配件数量
        soDecrodatePartPO.setDouble("PART_SALES_PRICE", soDecrodatePartDTO.getPartSalesPrice());// 配件销售单价
        soDecrodatePartPO.setDouble("PART_SALES_AMOUNT", soDecrodatePartDTO.getPartSalesAmount());// 配件销售金额
        soDecrodatePartPO.setDouble("DISCOUNT", soDecrodatePartDTO.getDiscount());// 折扣率
        soDecrodatePartPO.setDouble("AFTER_DISCOUNT_AMOUNT", soDecrodatePartDTO.getAfterDiscountAmountPartList());// 折后总金额
        soDecrodatePartPO.setString("PART_SEQUENCE", soDecrodatePartDTO.getPartSequence());// 配件序列号
        soDecrodatePartPO.setInteger("ACCOUNT_MODE", soDecrodatePartDTO.getAccountMode());// 结算方式
        soDecrodatePartPO.setString("REMARK", soDecrodatePartDTO.getRemark());// 备注
        return soDecrodatePartPO;
    }

    /**
     * 设置服务项目明细属性
     * 
     * @author xukl
     * @date 2016年9月23日
     * @param soServicesDTO
     * @return
     * @throws ServiceBizException
     */

    private SoServicesPO setServices(SoServicesDTO soServicesDTO) throws ServiceBizException {
        SoServicesPO soServicesPO = new SoServicesPO();
        soServicesPO.setString("SERVICE_CODE", soServicesDTO.getServiceCode());// 服务项目代码
        soServicesPO.setString("SERVICE_NAME", soServicesDTO.getServiceName());// 服务项目名称
        soServicesPO.setInteger("SERVICE_TYPE", soServicesDTO.getServiceType());// 服务类别
        soServicesPO.setDouble("DIRECTIVE_PRICE", soServicesDTO.getDirectivePrice());// 销售指导价
        soServicesPO.setDouble("AFTER_DISCOUNT_AMOUNT", soServicesDTO.getAfterDiscountAmountServicesList());// 实收金额
        soServicesPO.setInteger("ACCOUNT_MODE", soServicesDTO.getAccountMode());// 结算方式
        soServicesPO.setString("REMARK", soServicesDTO.getRemark());// 备注
        return soServicesPO;
    }

    /**
     * 获取客户类型
     * 
     * @author xukl
     * @date 2016年9月19日
     * @param customerNo
     * @return
     */
    private Integer qryCstTypeByNo(String customerNo) throws ServiceBizException {
        StringBuffer sbf = new StringBuffer("select tpc.DEALER_CODE,tpc.CUSTOMER_TYPE from TM_POTENTIAL_CUSTOMER tpc where tpc.POTENTIAL_CUSTOMER_NO = ?");
        List<String> params = new ArrayList<String>();
        params.add(customerNo);
        List<Map> list = DAOUtil.findAll(sbf.toString(), params);
        Integer customertype = 0;
        if (!CommonUtils.isNullOrEmpty(list)) {
            customertype = Integer.parseInt(list.get(0).get("CUSTOMER_TYPE").toString());

        }
        return customertype;
    }

    /**
     * 根据id查询销售单
     * 
     * @author xukl
     * @date 2016年9月18日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#getSalesOrderById(java.lang.Long)
     */
    @Override
    public Map getSalesOrderById(String id) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT  A.STORAGE_POSITION_CODE,A.CUSTOMER_TYPE,A.SHEET_CREATED_BY,A.DISPATCHED_DATE,A.DISPATCHED_BY,A.OTHER_AMOUNT,A.CONTRACT_MATURITY,A.REMARK,A.PROVINCE,A.CITY,A.DISTRICT,A.CONTRACT_EARNEST,tda.DEFAULT_VALUE AS BANK_ACCOUNT,tdb.DEFAULT_VALUE AS MBANK_NAME,tdh.DEFAULT_VALUE AS HOT_LINE,tdp.DEFAULT_VALUE AS FOOT_ADDRESS,C.PRODUCTING_AREA,PO.GENDER,A.ADDRESS,A.OTHER_AMOUNT_OBJECT,A.PRESENT_SUM,A.OFFSET_AMOUNT,CASE WHEN A.MOBILE IS NULL THEN A.PHONE ELSE A.MOBILE END AS MOBILE,A.CONTACTOR_NAME,em.USER_NAME,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME,A.CUSTOMER_NAME,A.INVOICE_MODE,A.VEHICLE_PRICE,A.ORDER_RECEIVABLE_SUM,A.ORDER_SUM,A.PRODUCT_CODE,B.PRODUCT_NAME,A.DELIVERY_MODE,A.PAY_MODE,A.DEALER_CODE,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.DELIVERY_MODE_ELEC,A.IS_ELECTRICITY,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.EC_ORDER_NO,A.CUS_SOURCE,A.DEPOSIT_AMOUNT,A.EC_ORDER,A.VERIFICATION_TIMEOUT,");
        sql.append(" B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A. VIN,A.SOLD_BY,A.IS_SPEEDINESS,");
        sql.append(" A.SO_NO,A.STORAGE_CODE,DATE_FORMAT(A.SHEET_CREATE_DATE, '%Y-%m-%d' ) AS SHEET_CREATE_DATE,A.SO_STATUS,A.CUSTOMER_NO,A.CONTRACT_NO,A.CONTRACT_DATE,A.OWNED_BY,A.LOCK_USER,A.CERTIFICATE_NO,A.CT_CODE, ");
        sql.append(" A.OWNER_NO, A.OWNER_NAME,A.DELIVERING_DATE,C.IS_DIRECT,C.ENGINE_NO,C.CERTIFICATE_NUMBER,A.BUSINESS_TYPE FROM TT_SALES_ORDER A  ");
        sql.append(" LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY");
        sql.append(" LEFT JOIN TM_VS_STOCK C ON A.DEALER_CODE = C.DEALER_CODE AND A.VIN = C.VIN");
        sql.append(" left  join   tm_potential_customer  PO   on   A.CUSTOMER_NO=PO.CUSTOMER_NO and A.DEALER_CODE=po.DEALER_CODE");
        sql.append(" left  join   tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
        sql.append(" left  join   TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and B.DEALER_CODE=se.DEALER_CODE");
        sql.append(" left  join   TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and mo.BRAND_CODE=se.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and B.DEALER_CODE=mo.DEALER_CODE");
        sql.append(" left  join   tm_configuration pa   on   B.CONFIG_CODE=pa.CONFIG_CODE and pa.BRAND_CODE=mo.BRAND_CODE and pa.SERIES_CODE=pa.SERIES_CODE and pa.MODEL_CODE=mo.MODEL_CODE and B.DEALER_CODE=pa.DEALER_CODE");
        sql.append(" left  join   tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
        sql.append("  left  join   tm_default_para tdp on tdp.item_code=7003 and A.DEALER_CODE=tdp.DEALER_CODE");
        sql.append("  left  join   tm_default_para tdh on tdh.item_code=7002 and A.DEALER_CODE=tdh.DEALER_CODE");
        sql.append("  left  join   tm_default_para tdb on tdb.item_code=7006 and A.DEALER_CODE=tdb.DEALER_CODE");
        sql.append("  left  join   tm_default_para tda on tda.item_code=7007 and A.DEALER_CODE=tda.DEALER_CODE");
        sql.append(" left join TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE");
        sql.append(" WHERE  A.D_KEY = "+ DictCodeConstants.D_KEY+ " AND NOT EXISTS(SELECT C.SO_NO FROM TT_SALES_ORDER C WHERE C.DEALER_CODE = A.DEALER_CODE AND C.VIN = A.VIN AND C.SO_NO != A.SO_NO "); 
        sql.append(" AND A.BUSINESS_TYPE = 13001004 AND A.ORDER_SORT = 13861002 AND C.BUSINESS_TYPE = 13001001 AND C.SO_STATUS != 13011055 AND C.SO_STATUS != 13011060)");


sql.append("   AND A.SO_STATUS != "
        //+ DictDataConstant.DICT_SO_STATUS_HAVE_OUT_STOCK
        + DictCodeConstants.DICT_SO_STATUS_CLOSED
        + " AND A.SO_STATUS != "
        + DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL
        + " AND A.SO_STATUS != "
        + DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD
        + " AND A.BUSINESS_TYPE = " + DictCodeConstants.DICT_SO_TYPE_GENERAL
        
        //Update By AndyTain 2013-3-8 begin
        + " AND NOT EXISTS ("
        + " SELECT SS.SO_NO FROM TT_SALES_ORDER SS WHERE SS.D_KEY = "
        + DictCodeConstants.D_KEY + " AND SS.BUSINESS_TYPE = "
        + DictCodeConstants.DICT_SO_TYPE_RERURN
        + " AND SS.DEALER_CODE = A.DEALER_CODE AND SS.VIN = A.VIN "
        + " AND SS.SO_STATUS != "
        + DictCodeConstants.DICT_SO_STATUS_NOT_AUDIT + " AND A.SO_NO = SS.OLD_SO_NO)"
        +"   AND A.SO_STATUS != "
        //+ DictDataConstant.DICT_SO_STATUS_HAVE_OUT_STOCK
        + DictCodeConstants.DICT_SO_STATUS_CLOSED
        + " AND A.SO_STATUS != "
        + DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL
        + " AND A.SO_STATUS != "
        + DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD
        + " AND A.BUSINESS_TYPE = " + DictCodeConstants.DICT_SO_TYPE_GENERAL                          
        //Update By AndyTain 2013-3-8 begin
        + " AND NOT EXISTS ("
        + " SELECT SS.SO_NO FROM TT_SALES_ORDER SS WHERE  "
        + " SS.DEALER_CODE = A.DEALER_CODE  "
        + " AND (SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_MANAGER_AUDITING
        + " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING
        + " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING
        + " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_MANAGER_REJECT
        + " OR SS.SO_STATUS = "+ DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_REJECT+")"
        + " AND A.SO_NO = SS.SO_NO AND A.EC_ORDER = 12781001)"
);
sql.append("  AND COALESCE(A.DEAL_STATUS,12871001)=12871001 ");
        List<Object> params = new ArrayList<Object>();
        sql.append(" and A.SO_NO= ?");
        params.add(id);
        Map map = DAOUtil.findFirst(sql.toString(), params);
        return map;
    }

    /**
     * 查询装潢项目明细
     * 
     * @author xukl
     * @date 2016年9月22日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#qryDecorationProject(java.lang.Long)
     */

    @Override
    public List<Map> qryDecorationProject(Long id) throws ServiceBizException {
        StringBuffer sbsql = new StringBuffer("SELECT tsd.SO_DECRODATE_ID, tsd.SO_NO_ID, tsd.DEALER_CODE, tsd.LABOUR_CODE, tsd.LABOUR_NAME, tsd.STD_LABOUR_HOUR, tsd.LABOUR_PRICE, tsd.DISCOUNT, tsd.AFTER_DISCOUNT_AMOUNT from  tt_so_decrodate tsd where tsd.SO_NO_ID=?");
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        List<Map> list = DAOUtil.findAll(sbsql.toString(), params);
        return list;
    }

    /**
     * 查询装潢材料明细
     * 
     * @author xukl
     * @date 2016年9月22日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#qryDeracotionMaterial(java.lang.Long)
     */
    @Override
    public List<Map> qryDeracotionMaterial(Long id) throws ServiceBizException {
        StringBuffer sbsql = new StringBuffer("SELECT tsdp.DECRODATE_PART_ID, tsdp.SO_NO_ID, tsdp.DEALER_CODE, tsdp.STORAGE_CODE, tsdp.STORAGE_POSITION_CODE, tsdp.PART_NO AS PART_CODE, tsdp.PART_NAME, tsdp.PART_QUANTITY, tsdp.PART_SALES_PRICE as SALES_PRICE, tsdp.PART_SALES_AMOUNT, tsdp.DISCOUNT, tsdp.AFTER_DISCOUNT_AMOUNT, tsdp.PART_SEQUENCE, tsdp.ACCOUNT_MODE, tsdp.REMARK, ts.STORAGE_NAME FROM TT_SO_DECRODATE_PART tsdp LEFT JOIN tm_store ts on ts.STORAGE_CODE = tsdp.STORAGE_CODE and ts.DEALER_CODE = tsdp.DEALER_CODE where tsdp.SO_NO_ID = ?");
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        List<Map> list = DAOUtil.findAll(sbsql.toString(), params);
        return list;
    }

    /**
     * 查询服务项目明细
     * 
     * @author xukl
     * @date 2016年9月22日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#qryServiceProject(java.lang.Long)
     */
    @Override
    public List<Map> qryServiceProject(Long id) throws ServiceBizException {
        StringBuffer sbsql = new StringBuffer("SELECT tss.SO_SERVICES_ID, tss.DEALER_CODE, tss.SERVICE_CODE, tss.SERVICE_NAME, tss.SERVICE_TYPE, tss.DIRECTIVE_PRICE, tss.AFTER_DISCOUNT_AMOUNT, tss.REMARK, tss.ACCOUNT_MODE from TT_SO_SERVICES tss where tss.SO_NO_ID = ?");
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        List<Map> list = DAOUtil.findAll(sbsql.toString(), params);
        return list;
    }

    /**
     * 修改
     * 
     * @author xukl
     * @date 2016年9月18日
     * @param id
     * @param salesOrderDTO
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#updateSalesOrder(java.lang.Long,
     * com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO)
     */

    @Override
    public void updateSalesOrder(String fIsChanged, SalesReturnDTO salesOrderDTO) throws ServiceBizException {
        SalesOrderPO oPo =SalesOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),salesOrderDTO.getSoNo());  
        oPo.setString("ABORTING_REASON",salesOrderDTO.getAbortingReason());
        oPo.setString("VEHICLE_PRICE",salesOrderDTO.getVehiclePrice());
        oPo.setDouble("penalty_Amount",salesOrderDTO.getPenaltyAmount());
        oPo.setDouble("order_Arrearage_Amount",salesOrderDTO.getOrderArrearageAmount());
        oPo.setDouble("order_Receivable_Sum",salesOrderDTO.getOrderReceivableSum());
        oPo.setDouble("offset_Amount",salesOrderDTO.getOffsetAmount());
        oPo.setDouble("order_Payed_Amount",salesOrderDTO.getOrderPayedAmount());
        oPo.setDouble("Other_Service_Sum",salesOrderDTO.getOtherServiceSum());
        oPo.setDouble("ORDER_SUM", salesOrderDTO.getOrderSum());
        oPo.setString("REMARK",salesOrderDTO.getRemark());
        oPo.setString("SOLD_BY",salesOrderDTO.getSoldBy());
        if(fIsChanged != null && fIsChanged.trim().equals("1")){
            oPo.setInteger("So_Status",DictCodeConstants.DICT_SO_STATUS_SYSTEM_REJECT);
            oPo.setInteger("Is_Upload",DictCodeConstants.DICT_IS_NO);
        }
        oPo.saveIt();
    }
    //作废
    @Override
    public void cancelSalesOrder(String soNo) {
        SalesOrderPO oPo =SalesOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),soNo);  
        oPo.setString("SO_NO", soNo);
        oPo.setString("dealer_code", FrameworkUtil.getLoginInfo().getDealerCode());
        oPo.delete();
    }
    /**
     * 提交
     * 
     * @author xukl
     * @date 2016年9月20日
     * @param id
     * @param salesOrderDTO
     * @throws ServiceBizException
     */
    @Override
    public void submitSalesOrder(Long id, SalesOrderDTO salesOrderDTO,
                                 List<BasicParametersDTO> basiDtolist) throws ServiceBizException {
        SalesOrderPO salesOrderPO = SalesOrderPO.findById(id);
        String saleaudit = "";// 是否需要经理审核
        String financeaudit = "";// 是否需要财务经理审核
        String vehiclehandcar = "";// 是否自动交车确认
        Integer stockId = salesOrderPO.getInteger("VS_STOCK_ID");// 库存id，用来判断有没有vin号
        String oldSoNo = salesOrderPO.getString("OLD_SO_NO");// 原销售单号，存在说明此单是销售退回单
        for (BasicParametersDTO basicParametersDTO : basiDtolist) {
            if (StringUtils.isEquals(basicParametersDTO.getParamCode(), "vehicle_sale_audit")) {
                saleaudit = basicParametersDTO.getParamValue();
            }
            if (StringUtils.isEquals(basicParametersDTO.getParamCode(), "vehicle_finance_audit")) {
                financeaudit = basicParametersDTO.getParamValue();
            }
            if (StringUtils.isEquals(basicParametersDTO.getParamCode(), "vehicle_handcar")) {
                vehiclehandcar = basicParametersDTO.getParamValue();
            }
        }
        if (!StringUtils.isNullOrEmpty(saleaudit)) {// 必须销售经理审核
            salesOrderPO.setInteger("SO_STATUS", DictCodeConstants.MANAGER_IN_REVIEW);
            salesOrderPO.setString("AUDITED_MANAGER", salesOrderDTO.getAuditedManager());
        } else if (!StringUtils.isNullOrEmpty(financeaudit)) {// “是否必须销售经理审核”为否，“是否必须财务经理审核”为是，
            salesOrderPO.setInteger("SO_STATUS", DictCodeConstants.FINANCE_IN_REVIEW);
            salesOrderPO.setString("AUDITED_FINANCE", salesOrderDTO.getAuditedManager());
        } else if (StringUtils.isNullOrEmpty(financeaudit) && StringUtils.isNullOrEmpty(saleaudit)
                   && StringUtils.isNullOrEmpty(vehiclehandcar)) {// “是否必须销售经理审核”为否，“是否必须财务经理审核”为否

            salesOrderPO.setInteger("SO_STATUS", DictCodeConstants.CAR_IN_CONFIRMATION);
        } else if (StringUtils.isNullOrEmpty(financeaudit) && StringUtils.isNullOrEmpty(saleaudit)
                   && !StringUtils.isNullOrEmpty(vehiclehandcar)) {
            if (StringUtils.isNullOrEmpty(stockId)) {
                throw new ServiceBizException("vin不能为空");
            } else {
                salesOrderPO.setInteger("SO_STATUS", DictCodeConstants.CAR_CONFIRMED);// 是否自动交车确认打勾
                Long poId = salesOrderPO.getLong("VS_STOCK_ID");
           /*     InvtrMaTncePO po = InvtrMaTncePO.findById(poId);
                po.setInteger("DISPATCHED_STATUS", DictCodeConstants.FORWARDED_TO_CONFIRM);// 修改订单状态为已交车确认
*/               /* po.saveIt();*/
            }
        }
        salesOrderPO.saveIt();
    }

 


    /**
     * 新增销售退回单
     * 
     * @author xukl
     * @date 2016年10月10日
     * @param salesOrderDTO
     * @param soNo
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#addSellBack(com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO,
     * java.lang.String)
     */

    @Override
    public Map addSellBack(SalesOrderDTO salesOrderDTO, String soNo) throws ServiceBizException {
        SalesOrderPO salesOrderPO = new SalesOrderPO();
        salesOrderDTO.setSoNo(soNo);
        salesOrderPO = setSalesOrderPO(salesOrderPO, salesOrderDTO);
        salesOrderPO.setInteger("BUSINESS_TYPE", DictCodeConstants.SALES_RETURN_TYPE);// 业务类型
        salesOrderPO.setInteger("CUSTOMER_TYPE", qryCstTypeByNo((salesOrderDTO.getCustomerNo())));// 客户类型
        salesOrderPO.setInteger("SO_STATUS", DictCodeConstants.NO_SUBMITTED);// 订单状态

        // 保存销售订单
        salesOrderPO.saveIt();

        // 保存装潢项目明细
        if (salesOrderDTO.getSoDecrodateList() != null) {
            for (SoDecrodateDTO soDecrodateDTO : salesOrderDTO.getSoDecrodateList()) {
                SoDecrodatePO soDecrodatePO = setDecrodate(soDecrodateDTO);
                salesOrderPO.add(soDecrodatePO);
            }
        }

        // 保存装潢材料明细
        if (salesOrderDTO.getSoDecrodatePartList() != null) {
            for (SoDecrodatePartDTO soDecrodatePartDTO : salesOrderDTO.getSoDecrodatePartList()) {
                SoDecrodatePartPO soDecrodatePartPO = setDecrodatePart(soDecrodatePartDTO);
                salesOrderPO.add(soDecrodatePartPO);
            }
        }
        // 保存服务项目明细
        if (salesOrderDTO.getSoServicesList() != null) {
            for (SoServicesDTO soServicesDTO : salesOrderDTO.getSoServicesList()) {
                SoServicesPO soServicesPO = setServices(soServicesDTO);
                salesOrderPO.add(soServicesPO);
            }
        }
        return getSalesOrderById(salesOrderPO.getString("SN_NO"));
    }

    /**
     * 销售退回修改
     * 
     * @author xukl
     * @date 2016年10月11日
     * @param id
     * @param salesOrderDTO
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#updateSellBack(java.lang.Long,
     * com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO)
     */

    @Override
    public void updateSellBack(Long id, SalesOrderDTO salesOrderDTO) throws ServiceBizException {
        SalesOrderPO salesOrderPO = SalesOrderPO.findById(id);
        Integer orderStatus = salesOrderPO.getInteger("SO_STATUS");// 订单状态
        // 如果销售单号不为空
        if (!StringUtils.isNullOrEmpty(salesOrderDTO.getSoNo())) {
            // 单据状态为经理审核驳回，则修改保存后，单据状态为经理审核中。
            if (DictCodeConstants.MANAGER_REJECTED == orderStatus) {
                salesOrderDTO.setSoStatus(DictCodeConstants.MANAGER_IN_REVIEW);
            }
            // 单据状态为财务审核驳回，则修改保存后，单据状态为财务审核中。
            if (DictCodeConstants.FINANCE_REJECTED == orderStatus) {
                salesOrderDTO.setSoStatus(DictCodeConstants.FINANCE_IN_REVIEW);
            }
        }
        setSalesOrderPO(salesOrderPO, salesOrderDTO);
        salesOrderPO.saveIt();

        // 删除原采购入库明细
        SoDecrodatePO.delete("SO_NO_ID = ?", id);
        SoDecrodatePartPO.delete("SO_NO_ID = ?", id);
        SoServicesPO.delete("SO_NO_ID = ?", id);
        // 保存装潢项目明细
        if (salesOrderDTO.getSoDecrodateList() != null) {
            for (SoDecrodateDTO soDecrodateDTO : salesOrderDTO.getSoDecrodateList()) {
                SoDecrodatePO soDecrodatePO = setDecrodate(soDecrodateDTO);
                salesOrderPO.add(soDecrodatePO);
            }
        }

        // 保存装潢材料明细
        if (salesOrderDTO.getSoDecrodatePartList() != null) {
            for (SoDecrodatePartDTO soDecrodatePartDTO : salesOrderDTO.getSoDecrodatePartList()) {
                SoDecrodatePartPO soDecrodatePartPO = setDecrodatePart(soDecrodatePartDTO);
                salesOrderPO.add(soDecrodatePartPO);
            }
        }
        // 保存服务项目明细
        if (salesOrderDTO.getSoServicesList() != null) {
            for (SoServicesDTO soServicesDTO : salesOrderDTO.getSoServicesList()) {
                SoServicesPO soServicesPO = setServices(soServicesDTO);
                salesOrderPO.add(soServicesPO);
            }
        }
    }

    /**
     * 销售退回提交
     * 
     * @author xukl
     * @date 2016年10月11日
     * @param id
     * @param salesOrderDTO
     * @param basiDtolist
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#submitSellBack(java.lang.Long,
     * com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO, java.util.List)
     */
    @Override
    public void submitSellBack(Long id, SalesOrderDTO salesOrderDTO,
                               List<BasicParametersDTO> basiDtolist) throws ServiceBizException {
        SalesOrderPO salesOrderPO = SalesOrderPO.findById(id);
        String saleaudit = "";// 是否需要经理审核
        String financeaudit = "";// 是否需要财务经理审核
        String vehiclehandcar = "";// 是否自动交车确认
        Integer stockId = salesOrderPO.getInteger("VS_STOCK_ID");// 库存id，用来判断有没有vin号
        String oldSoNo = salesOrderPO.getString("OLD_SO_NO");// 原销售单号，存在说明此单是销售退回单
        for (BasicParametersDTO basicParametersDTO : basiDtolist) {
            if (StringUtils.isEquals(basicParametersDTO.getParamCode(), "vehicle_sale_audit")) {
                saleaudit = basicParametersDTO.getParamValue();
            }
            if (StringUtils.isEquals(basicParametersDTO.getParamCode(), "vehicle_finance_audit")) {
                financeaudit = basicParametersDTO.getParamValue();
            }
            if (StringUtils.isEquals(basicParametersDTO.getParamCode(), "vehicle_handcar")) {
                vehiclehandcar = basicParametersDTO.getParamValue();
            }
        }
        if (!StringUtils.isNullOrEmpty(saleaudit)) {// 必须销售经理审核
            salesOrderPO.setInteger("SO_STATUS", DictCodeConstants.MANAGER_IN_REVIEW);
            salesOrderPO.setString("AUDITED_MANAGER", salesOrderDTO.getAuditedManager());
        } else if (!StringUtils.isNullOrEmpty(financeaudit)) {// “是否必须销售经理审核”为否，“是否必须财务经理审核”为是，
            salesOrderPO.setInteger("SO_STATUS", DictCodeConstants.FINANCE_IN_REVIEW);
            salesOrderPO.setString("AUDITED_FINANCE", salesOrderDTO.getAuditedManager());
        } else if (StringUtils.isNullOrEmpty(financeaudit) && StringUtils.isNullOrEmpty(saleaudit)) {// “是否必须销售经理审核”为否，“是否必须财务经理审核”为否
            // 销售退回单改为等待退回入库，并往验收表里插一条数据?那此时库存状态是？
            salesOrderPO.setInteger("SO_STATUS", DictCodeConstants.ORDER_BACK_INWAREHOUSE);
            recordVsInspect(qryDataForStockIn(salesOrderDTO.getSoNoId()));
        }
        salesOrderPO.saveIt();
    }

    /**
     * 根据销售单id查询生成退后入库单数据
     * 
     * @author xukl
     * @date 2016年10月12日
     * @param id
     * @return
     */
    private Map qryDataForStockIn(Long id) {
        StringBuffer str = new StringBuffer("SELECT tvs.VS_STOCK_ID,tvs.VIN, vp.BRAND_ID, vp.BRAND_CODE, vp.SERIES_ID, vp.SERIES_CODE, vp.MODEL_ID, vp.MODEL_CODE, vp.PACKAGE_ID, vp.CONFIG_CODE, vp.COLOR_ID, vp.COLOR_CODE, vp.PRODUCT_ID, tvs.OEM_TAG, tvs.IS_DIRECT, tvs.STORAGE_CODE, tvs.STORAGE_POSITION_CODE, tvs.ENGINE_NO, tvs.PRODUCT_DATE, tvs.FACTORY_DATE, tvs.KEY_NO, tvs.DISCHARGE_STANDARD, tvs.EXHAUST_QUANTITY, tvs.HAS_CERTIFICATE, tvs.CERTIFICATE_NUMBER, tvs.CERTIFICATE_LOCUS, tso.DEALER_CODE FROM tt_sales_order tso LEFT JOIN tm_vs_stock tvs ON tvs.VS_STOCK_ID = tso.VS_STOCK_ID AND tso.DEALER_CODE = tvs.DEALER_CODE LEFT JOIN vw_productinfo vp ON vp.PRODUCT_ID = tso.PRODUCT_ID  where tso.SO_NO_ID = ?");
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        return DAOUtil.findFirst(str.toString(), params);
    }
    
    
    @Override
    public PageInfoDto qrySRSForFunction(Map<String, String> queryParam) throws ServiceBizException {
     
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT A.DEALER_CODE,A.USER_ID,A.USER_CODE,A.USER_NAME,A.EMPLOYEE_NO,A.ORG_CODE,B.EMPLOYEE_NAME, " +
                " CASE WHEN MENU_ID='70105000' THEN '经理审核' ELSE '财务审核' END AS FUNCTION_NAME" +
                " ,CASE WHEN MENU_ID = '70101300' THEN '70302000' ELSE MENU_ID END AS MENU_ID  "+
           " FROM TM_USER A ,TM_EMPLOYEE B,TM_USER_MENU C " +
           " WHERE A.DEALER_CODE = B.DEALER_CODE   " +//--A.ORG_CODE = B.ORG_CODE " +
                " AND A.DEALER_CODE = C.DEALER_CODE AND A.EMPLOYEE_NO = B.EMPLOYEE_NO " +
                
                " AND C.USER_ID = A.USER_ID  " +//AND A.D_KEY =" + CommonConstant.D_KEY +
                " AND A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "'" +
                " AND A.USER_STATUS = " + DictCodeConstants.DICT_IN_USE_START +
                " AND A.USER_ID != " + FrameworkUtil.getLoginInfo().getUserId());
        sql.append("  AND MENU_ID = '70105000' ");
       
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
        return pageInfoDto;
    }
    /**
     * 财务经理审批通过后产生整车退回验收单
     * 
     * @author yll
     * @date 2016年12月11日
     * @param map
     */
    private void recordVsInspect(Map map) {
        VehicleInspectPO VehicleInspectPO = new VehicleInspectPO();
        VehicleInspectPO.setString("BUSSINESS_NO", commonNoService.getSystemOrderNo(CommonConstants.SD_NO));// 入库单编号
        VehicleInspectPO.setTimestamp("BUSSINESS_DATE", new Date());// 单据日期为今天
        VehicleInspectPO.setInteger("INSPECT_STATUS", DictCodeConstants.INSPECTION_RESULT);// 验收状态为未验收
        VehicleInspectPO.setInteger("VB_TYPE", DictCodeConstants.ENTRY_TYPE_XSTH);// 业务类型为销售退回
        VehicleInspectPO.setInteger("VS_STOCK_ID", map.get("VS_STOCK_ID"));// 库存id
        VehicleInspectPO.saveIt();
    }

    /**
     * 财务经理审批通过后产生整车退回入库单
     * 
     * @author xukl
     * @date 2016年10月12日
     */
    private void recordVsEntry(Map map) {
        StockInPO stockInPO = new StockInPO();
        stockInPO.setString("SE_NO", commonNoService.getSystemOrderNo(CommonConstants.SD_NO));// 入库单编号
        stockInPO.setString("VIN", map.get("VIN"));// VIN号
        stockInPO.setString("ENGINE_NO", map.get("ENGINE_NO"));// 发动机号
        stockInPO.setTimestamp("PRODUCT_DATE", map.get("PRODUCT_DATE"));// 制造日期
        stockInPO.setTimestamp("FACTORY_DATE", map.get("FACTORY_DATE"));// 出厂日期
        stockInPO.setString("BRAND_CODE", map.get("BRAND_ID"));// 品牌代码
        stockInPO.setString("SERIES_CODE", map.get("SERIES_ID"));// 车系代码
        stockInPO.setString("MODEL_CODE", map.get("MODEL_ID"));// 车型代码
        stockInPO.setString("CONFIG_CODE", map.get("PACKAGE_ID"));// 配置代码
        stockInPO.setString("COLOR", map.get("COLOR_ID"));// 颜色
        stockInPO.setString("KEY_NO", map.get("KEY_NO"));// 钥匙编号
        stockInPO.setInteger("DISCHARGE_STANDARD", map.get("DISCHARGE_STANDARD"));// 排放标准
        stockInPO.setString("EXHAUST_QUANTITY", map.get("EXHAUST_QUANTITY"));// 排气量
        stockInPO.setString("HAS_CERTIFICATE", map.get("HAS_CERTIFICATE"));// 是否有合格证
        stockInPO.setString("CERTIFICATE_NUMBER", map.get("CERTIFICATE_NUMBER"));// 合格证号
        stockInPO.setString("CERTIFICATE_LOCUS", map.get("CERTIFICATE_LOCUS"));// 合格证存放地
        stockInPO.setInteger("PRODUCT_ID", map.get("PRODUCT_ID"));// 整车产品id
        stockInPO.setString("STORAGE_CODE", map.get("STORAGE_CODE"));// 仓库代码
        stockInPO.setString("STORAGE_POSITION_CODE", map.get("STORAGE_POSITION_CODE"));// 库位代码
        stockInPO.setInteger("IS_ENTRY", DictCodeConstants.STATUS_IS_NOT);// 是否入库
        stockInPO.setInteger("OEM_TAG", map.get("OEM_TAG"));// 是否OEM
        stockInPO.setInteger("IS_DIRECT", map.get("IS_DIRECT"));// 是否直销
        stockInPO.saveIt();
    }

    /**
     * 结算收款选择销售订单查询
     * 
     * @author xukl
     * @date 2016年10月13日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     */
    @Override
    public PageInfoDto slctSalesOrders(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT tso.DEALER_CODE, tso.SO_NO_ID, tso.SO_NO, tso.CUSTOMER_NAME, tpc.MOBILE, tvs.VIN, tso.VEHICLE_PRICE, tso.UPHOLSTER_SUM, tso.PRESENT_SUM, tso.SERVICE_SUM, tsi.INVOICE_AMOUNT, tso.OFFSET_AMOUNT, tso.ORDER_SUM, tso.ORDER_RECEIVABLE_SUM, ifnull(tso.ORDER_PAYED_AMOUNT, 0) AS ORDER_PAYED_AMOUNT, ifnull(tso.ORDER_RECEIVABLE_SUM, 0) - ifnull(tso.ORDER_PAYED_AMOUNT, 0) AS ARREARAGE_AMOUNT, tpc.GATHERED_SUM, tpc.ORDER_PAYED_SUM, tpc.USABLE_AMOUNT, tpc.UN_WRITEOFF_SUM, tso.SO_STATUS, tso.CUSTOMER_NO, tso.CONSULTANT, vwp.BRAND_NAME, vwp.SERIES_ID, vwp.BRAND_ID, vwp.SERIES_NAME, vwp.MODEL_ID, vwp.MODEL_NAME, vwp.PACKAGE_ID, vwp.CONFIG_NAME, vwp.COLOR_NAME, vwp.COLOR_ID, tme.EMPLOYEE_NAME, tso.SHEET_CREATE_DATE FROM tt_sales_order tso LEFT JOIN tm_potential_customer tpc ON tso.CUSTOMER_NO = tpc.POTENTIAL_CUSTOMER_NO  and tpc.DEALER_CODE = tso.DEALER_CODE LEFT JOIN tm_vs_stock tvs ON tvs.VS_STOCK_ID = tso.VS_STOCK_ID and tvs.DEALER_CODE = tso.DEALER_CODE LEFT JOIN tt_so_invoice tsi ON tsi.SO_NO_ID = tso.SO_NO_ID and tsi.DEALER_CODE = tso.DEALER_CODE AND tsi.INVOICE_CHARGE_TYPE = ? AND tsi.IS_VALID = ? LEFT JOIN vw_productinfo vwp ON vwp.PRODUCT_ID = tso.PRODUCT_ID LEFT JOIN tm_employee tme ON tme.EMPLOYEE_NO = tso.CONSULTANT and tme.DEALER_CODE = tso.DEALER_CODE WHERE tso.SO_STATUS in (?,?,?,?)");
        List<Object> params = new ArrayList<Object>();
        params.add(DictCodeConstants.INVOICE_CHARGE_TYPE_BUYCAR);
        params.add(DictCodeConstants.STATUS_IS_YES);
        params.add(DictCodeConstants.CAR_IN_CONFIRMATION);
        params.add(DictCodeConstants.CAR_CONFIRMED);
        params.add(DictCodeConstants.ORDER_COMPLETED);
        params.add(DictCodeConstants.ORDER_BACK_INWAREHOUSE);
        if (!StringUtils.isNullOrEmpty(queryParam.get("soNo"))) {
            sb.append(" and tso.SO_NO like ?");
            params.add("%" + queryParam.get("soNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and tso.CUSTOMER_NAME like ? ");
            params.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and tvs.VIN like ?");
            params.add("%" + queryParam.get("vin") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("isSettle"))) {
            if (Integer.parseInt(queryParam.get("isSettle").toString()) == DictCodeConstants.STATUS_IS_YES) {
                sb.append(" and (case when (tso.ORDER_RECEIVABLE_SUM IS NULL) THEN '0' ELSE  tso.ORDER_RECEIVABLE_SUM END)- (case when (tso.ORDER_PAYED_AMOUNT IS NULL) THEN '0' ELSE  tso.ORDER_PAYED_AMOUNT END)<=0");
            } else if (Integer.parseInt(queryParam.get("isSettle").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                sb.append(" and (case when (tso.ORDER_RECEIVABLE_SUM IS NULL) THEN '0' ELSE  tso.ORDER_RECEIVABLE_SUM END)- (case when (tso.ORDER_PAYED_AMOUNT IS NULL) THEN '0' ELSE  tso.ORDER_PAYED_AMOUNT END)>0");
            }
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("sheetCreateDateFrom"))) {
            sb.append(" and tso.SHEET_CREATE_DATE>=? ");
            params.add(DateUtil.parseDefaultDate(queryParam.get("sheetCreateDateFrom")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("sheetCreateDateTo"))) {
            sb.append(" and tso.SHEET_CREATE_DATE<? ");
            params.add(DateUtil.addOneDay(queryParam.get("sheetCreateDateTo")));
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), params);
        return pageInfoDto;
    }

    @Override
    public PageInfoDto qryComplaintSalesOrders(Map<String, String> queryParam) throws ServiceBizException {

        StringBuffer sb = new StringBuffer("SELECT\n");
        sb.append("  tso.SO_NO_ID,\n");
        sb.append("  tso.DEALER_CODE,\n");
        sb.append("  tso.SO_NO,\n");
        sb.append("  tpc.MOBILE,\n");
        sb.append("  tpc.PHONE,\n");
        sb.append("  tpc.ADDRESS,\n");
        sb.append("  tso.CUSTOMER_NAME,\n");
        sb.append("  te.EMPLOYEE_NAME,\n");
        sb.append("  tso.CONSULTANT,\n");
        sb.append("  tso.SHEET_CREATE_DATE as SALES_DATE,\n");
        sb.append("  tso.SO_STATUS,\n");
        sb.append("  tvs.VIN,\n");
        sb.append("  vwp.PRODUCT_NAME,tow.ADDRESS as LINK_ADDRESS,tv.license,tv.MILEAGE,tow.OWNER_NAME\n");

        sb.append("FROM\n");
        sb.append("  TT_SALES_ORDER tso\n");
        sb.append("LEFT JOIN TM_POTENTIAL_CUSTOMER tpc ON tso.CUSTOMER_NO = tpc.POTENTIAL_CUSTOMER_NO\n");
        sb.append("AND tpc.DEALER_CODE = tso.DEALER_CODE\n");
        sb.append("LEFT JOIN vw_productinfo vwp ON vwp.PRODUCT_ID = tso.PRODUCT_ID\n");
        sb.append("LEFT JOIN tm_vs_stock tvs ON tvs.VS_STOCK_ID = tso.VS_STOCK_ID\n");
        sb.append("AND tvs.DEALER_CODE = tso.DEALER_CODE\n");
        sb.append("LEFT JOIN  tm_vehicle tv ON tvs.DEALER_CODE=tv.DEALER_CODE AND tv.VIN=tvs.VIN\n");
        sb.append("left join tm_owner tow on tow.DEALER_CODE=tv.DEALER_CODE and tow.OWNER_ID=tv.OWNER_ID\n");

        sb.append("LEFT JOIN tm_employee te ON tso.CONSULTANT = te.EMPLOYEE_NO\n");
        sb.append("AND te.DEALER_CODE = tso.DEALER_CODE\n");
        sb.append("WHERE\n");
        sb.append("  1 = 1\n");
        List<String> params = new ArrayList<String>();
        // 客户投诉过滤条件
        if (!StringUtils.isNullOrEmpty(queryParam.get("NotINsoStatus"))) {
            sb.append(" and tso.SO_STATUS not in(?)\n");
            params.add(queryParam.get("NotINsoStatus"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("NotINbusinessType"))) {
            sb.append(" and tso.BUSINESS_TYPE not in (?)\n");
            params.add(queryParam.get("NotINbusinessType"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
            sb.append(" and tso.CONSULTANT = ?");
            params.add(queryParam.get("consultant"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("soNo"))) {
            sb.append(" and tso.SO_NO like ?");
            params.add("%" + queryParam.get("soNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and tso.CUSTOMER_NAME like ? ");
            params.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and tvs.VIN like ?");
            params.add("%" + queryParam.get("vin") + "%");
        }

        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), params);
        return pageInfoDto;

    }

    @Override
    public Map getPritSalesOrderById(Long id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("SELECT\n").append("  tso.SO_NO_ID,tvs.VIN,\n").append("  tso.DEALER_CODE,\n").append("  tso.SHEET_CREATE_DATE,\n").append("  tso.SO_NO,\n").append("  tso.OLD_SO_NO,\n").append("  tso.CONTRACT_DATE,tso.REMARK,\n").append("tso.VEHICLE_PRICE AS DIRECTIVE_PRICE,tso.SERVICE_SUM,tso.UPHOLSTER_SUM, tso.PRESENT_SUM,tso.OFFSET_AMOUNT,tso.ORDER_SUM, tso.ORDER_RECEIVABLE_SUM,tso.BUSINESS_TYPE,").append("  tso.CONTRACT_NO,\n").append("  tvs.ENGINE_NO,\n").append("  em.EMPLOYEE_NAME,\n").append("  tpc.CUSTOMER_NAME,\n").append("  tpc.GENDER,\n").append("  tpc.PHONE,\n").append("  vp.SERIES_NAME,\n").append("  vp.MODEL_NAME,\n").append("  vp.COLOR_NAME,vp.PRODUCT_CODE,\n").append("  vp.CONFIG_NAME,\n").append("  tpc.CONTACTOR_ADDRESS,\n").append("  tso.PENALTY_AMOUNT,\n").append("  tso.RO_PAYED_AMOUNT,\n").append("  tso.RETURN_REASON,\n").append("  tso.RO_RECEIVABLE_SUM\n").append("\n").append("FROM\n").append("  tt_sales_order tso\n").append("LEFT JOIN tm_potential_customer tpc ON tpc.POTENTIAL_CUSTOMER_NO = tso.CUSTOMER_NO\n").append("AND tpc.DEALER_CODE = tso.DEALER_CODE\n").append("LEFT JOIN vw_productinfo vp ON vp.PRODUCT_ID = tso.PRODUCT_ID\n").append("LEFT JOIN tm_vs_stock tvs ON tvs.VS_STOCK_ID = tso.VS_STOCK_ID\n").append("AND tvs.DEALER_CODE = tso.DEALER_CODE\n").append("left join TM_EMPLOYEE  em  on  tso.CONSULTANT=em.EMPLOYEE_NO   and tso.DEALER_CODE=em.DEALER_CODE\n").append("WHERE tso.SO_NO_ID = ?");
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        Map map = DAOUtil.findFirst(sb.toString(), params);
        return map;
    }
    public SalesOrderPO getSales1(SalesReturnDTO Dto,String fIsChanged){
        SalesOrderPO po = new SalesOrderPO();
        Map gxbpo=new HashMap();

        Dto.getOldSoNo();
    //    orderList1 = querySalesOrderBySoNoDetial(Dto.getOldSoNo());
        List<Map> orderList = new ArrayList();
        List<Object> cus = new ArrayList<Object>();
        cus.add(Dto.getOldSoNo());           
        cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
       List<SalesOrderPO> salesOrderPO = SalesOrderPO.findBySQL("select * from tt_sales_order where so_no=?  and dealer_code=?", cus.toArray());            
       if (salesOrderPO.size()>0) {           
           orderList = querySalesOrderBySoNo(Dto.getOldSoNo(),salesOrderPO.get(0).get("Business_Type").toString(),salesOrderPO.get(0).get("vin").toString());        
           gxbpo =  orderList.get(0); 
       }      
        po.setString("Customer_No", Dto.getCustomerNo()!= null ? Dto.getCustomerNo() : "");     
  //      po.setString("Owner_No",Dto.getOwnerNo()!= null ? Dto.getOwnerNo() : "");
  //      po.setIntentSoNo(intentSoNo);
  //      po.setApplyNo(applyNo);
  //      po.setInstallmentAmount(Utility.getDouble(installmentAmount));
 //       po.setInstallmentNumber(Utility.getInt(installmentNumber));;
        po.setInteger("Ver", Dto.getVer());
        po.setString("customer_Name",Dto.getCustomerName());
  //      po.setOwnerName(ownerName);
        po.setInteger("customer_Type",Dto.getCustomerType());
        po.setString("contactor_Name",Dto.getContactorName());
    //    po.setIsLocalUse(Utility.getInt(isLocalUse));
        po.setString("phone",gxbpo.get("phone"));
        po.setString("address",gxbpo.get("address"));
        po.setString("delivery_Address",gxbpo.get("delivery_Address"));//设置PO的运送地址
        po.setInteger("ct_Code",gxbpo.get("ct_Code"));
        po.setString("certificate_No",gxbpo.get("certificate_No"));
        po.setInteger("business_Type",Dto.getBusinessType());
        po.setTimestamp("sheet_Create_Date",gxbpo.get("sheet_Create_Date"));
        po.setLong("sheet_Created_By",gxbpo.get("sheet_Created_By"));
        po.setInteger("so_Status",Dto.getSoStatus());
        po.setString("contract_No",gxbpo.get("contract_No"));
        po.setDate("contract_Date",gxbpo.get("contract_Date"));
        po.setDouble("contract_Earnest",gxbpo.get("contract_Earnest"));
        po.setDate("contract_Maturity",gxbpo.get("contract_Maturity"));
        po.setInteger("pay_Mode",gxbpo.get("pay_Mode"));
        po.setString("loan_Org",gxbpo.get("loan_Org"));
        po.setString("Delivery_Mode",gxbpo.get("Delivery_Mode"));
 
        
        po.setString("consignee_Code",gxbpo.get("consignee_Code"));
        po.setString("consignee_Name",gxbpo.get("consignee_Name"));
        po.setDate("delivering_Date",gxbpo.get("delivering_Date"));
        po.setString("Sold_By",Dto.getSoldBy());
        po.setString("remark",Dto.getRemark());
        po.setString("product_Code",gxbpo.get("product_Code"));
        po.setString("color_code",gxbpo.get("color_code"));
  //      po.setPreInvoiceAmount(Utility.getDouble(preInvoiceAmount));
        po.setInteger("invoice_Mode",gxbpo.get("invoice_Mode"));
        po.setString("license",gxbpo.get("license"));
        po.setString("storage_Code",gxbpo.get("storage_Code"));
        po.setString("storage_Position_Code",gxbpo.get("storage_Position_Code"));
        po.setTimestamp("dispatched_Date",gxbpo.get("dispatched_Date"));
        po.setDate("Stock_Out_Date",gxbpo.get("Stock_Out_Date"));
        po.setString("dispatched_By",gxbpo.get("dispatched_By"));
        //po.setConfirmedDate(Utility.getTimeStamp(confirmedDate));
      //  po.setString("vin",gxbpo.get("vin"));
        po.setString("vin",gxbpo.get("vin"));
        po.setInteger("vehicle_Purpose",gxbpo.get("vehicle_Purpose"));
        po.setDouble("directive_Price",gxbpo.get("directive_Price"));
        po.setInteger("is_Permuted",gxbpo.get("is_Permuted"));
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        form.format(date);  
        if(!StringUtils.isNullOrEmpty(gxbpo.get("is_Permuted")) && !StringUtils.isNullOrEmpty(gxbpo.get("permuted_Vin")) && DictCodeConstants.DICT_IS_YES.equals(gxbpo.get("is_Permuted"))){
            po.setDate("Permuted_Date",form);
        }
        
        po.setString("old_Brand_Code",Dto.getBrand());
        po.setString("old_Series_Code",Dto.getSeries());
        po.setString("old_Model_Code",Dto.getModel() );
        po.setString("permuted_Vin",gxbpo.get("permuted_Vin"));
        po.setString("permuted_Desc",gxbpo.get("permuted_Desc"));
        po.setDouble("vehicle_Price",Dto.getVehiclePrice());
        po.setDouble("present_Sum",gxbpo.get("present_Sum"));        
        po.setDouble("offset_Amount",Dto.getOffsetAmount());
        po.setDouble("garniture_Sum",Dto.getGarnitureSum());
        po.setDouble("upholster_Sum",Dto.getUpholsterSum());
        po.setDouble("order_Receivable_Sum",Dto.getOrderReceivableSum());
        po.setDouble("insurance_Sum",Dto.getInsuranceSum());
        po.setDouble("order_Payed_Amount",Dto.getOrderPayedAmount());
        po.setDouble("tax_Sum",Dto.getTaxSum());
        po.setDouble("order_Derated_Sum",gxbpo.get("order_Derated_Sum"));
        po.setDouble("plate_Sum",Dto.getPlateSum());
        po.setDouble("order_Arrearage_Amount",Dto.getOrderArrearageAmount());

        
        if(StringUtils.isNullOrEmpty(gxbpo.get("other_Pay_Off")) || gxbpo.get("other_Pay_Off").equals("0")){
              po.setInteger("Other_Pay_Off",DictCodeConstants.DICT_IS_NO);
            }
   
        po.setDouble("loan_Sum",Dto.getLoanSum());
        po.setDouble("con_Receivable_Sum",gxbpo.get("con_Receivable_Sum"));
        po.setDouble("con_Payed_Amount",gxbpo.get("con_Payed_Amount"));
        po.setDouble("order_Sum",gxbpo.get("order_Sum"));
        po.setDouble("Other_Service_Sum",Dto.getOtherServiceSum());
        po.setDouble("Con_Arrearage_Amount",gxbpo.get("Con_Arrearage_Amount"));
        po.setInteger("pay_Off",gxbpo.get("pay_Off"));
        po.setDouble("consigned_Sum",gxbpo.get("consigned_Sum"));
        po.setString("owned_By",Dto.getSoldBy());
        po.setInteger("allocating_Type",gxbpo.get("allocating_Type"));
        po.setString("consigner_Code",gxbpo.get("consigner_Code"));
        po.setString("consigner_Name",gxbpo.get("consigner_Name"));
        po.setString("Old_So_No",Dto.getOldSoNo());
        po.setInteger("D_Key",DictCodeConstants.D_KEY);
        po.setInteger("Aborting_Flag",DictCodeConstants.DICT_IS_NO);
      //  po.setVehicleDesc("vehicleDesc");
        po.setString("aborting_Reason",Dto.getAbortingReason());
        po.setDouble("penalty_Amount",Dto.getPenaltyAmount());
    
        if(StringUtils.isNullOrEmpty(Dto.getVin())){
            po.setString("Vin",Dto.getVin());
            po.setString("product_Code",gxbpo.get("product_Code"));
        }
        if(fIsChanged != null && fIsChanged.trim().equals("1")){
            po.setInteger("So_Status",DictCodeConstants.DICT_SO_STATUS_SYSTEM_REJECT);
            po.setInteger("Is_Upload",DictCodeConstants.DICT_IS_NO);
        }
   //     if(status!=null&&status.trim().equals("A")){
          
            po.setString("SO_NO",commonNoService.getSystemOrderNo(CommonConstants.SAL_ZZXSDH));
     

           return po;
       }
    public TtOrderStatusUpdatePO getSubmit (SalesReturnDTO Dto,Long userId){
        TtOrderStatusUpdatePO apo = new TtOrderStatusUpdatePO();
        List<Object> cus = new ArrayList<Object>();
        cus.add(Dto.getSoNo());    
        cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
        cus.add(DictCodeConstants.D_KEY);
        List<SalesOrderPO> listapo = SalesOrderPO.findBySQL("select * from tt_sales_order where so_no=?   and dealer_code=? and D_Key=?", cus.toArray()); 
        SalesOrderPO apoquery =new SalesOrderPO();
        if (listapo!=null&&listapo.size()>0 ){
            apoquery=(SalesOrderPO) listapo.get(0);
            if (!StringUtils.isNullOrEmpty(apoquery.get("BusinessType")) && (apoquery.get("BusinessType")).toString().equals(DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY)
            || apoquery.get("BusinessType").toString().equals(DictCodeConstants.DICT_SO_TYPE_GENERAL) ){  
                Long intentId = commonNoService.getId("ID");
                apo.setLong("Item_Id",intentId);             
                apo.setString("SO_NO",Dto.getSoNo());
                apo.setInteger("SO_STATUS",Dto.getSoStatus());
                apo.setLong("Owned_By",userId);
                apo.setInteger("Is_Upload",DictCodeConstants.DICT_IS_NO);
                apo.setDate("Alteration_Time",new Date());
                apo.setInteger("D_Key",DictCodeConstants.D_KEY);      
                apo.saveIt();             
            }          
        } 
        return  apo;
    }

    
    /**
    * @author LiGaoqi
    * @date 2017年2月17日
    * @param salesOrderDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#submitAndCheckVerified(com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO)
    */
    	
    @Override
    public void submitAndCheckVerified(SalesOrderDTO salesOrderDTO) throws ServiceBizException {

        String soNo = salesOrderDTO.getSoNo();
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        SalesOrderPO salesOrderPo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),soNo);
        String deliveryModeElec ="";
        if(!StringUtils.isNullOrEmpty(salesOrderPo.getString("DELIVERY_MODE_ELEC"))){
            deliveryModeElec=salesOrderPo.getString("DELIVERY_MODE_ELEC");
        }
        if(salesOrderPo!=null&&!StringUtils.isNullOrEmpty(salesOrderPo.getInteger("EC_ORDER"))&&salesOrderPo.getInteger("EC_ORDER")==12781001&&!StringUtils.isNullOrEmpty(salesOrderPo.getInteger("DELIVERY_MODE_ELEC"))&&
                (salesOrderPo.getInteger("DELIVERY_MODE_ELEC")==16001001||salesOrderPo.getInteger("DELIVERY_MODE_ELEC")==16001002)){
            //CHECK_VERIFIED校验就webservise
        }
        
        if(!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8001"))&&!commonNoService.getDefalutPara("8001").equals("12781001")&&
                !StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8032"))&&!commonNoService.getDefalutPara("8032").equals("12781001")){
            SalesOrderPO tsoPOs = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),soNo);
            tsoPOs.setInteger("SO_STATUS",Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING));
            tsoPOs.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
            tsoPOs.saveIt();
            SalesOrderPO salesOrder= SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),soNo);
            if(!StringUtils.isNullOrEmpty(salesOrder)){
                if(!StringUtils.isNullOrEmpty(salesOrder.getString("INTENT_SO_NO"))&&(
                        !commonNoService.getDefalutPara("8036").equals("12781001")&&salesOrder.getInteger("SO_STATUS")>Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_NOT_AUDIT)||
                        commonNoService.getDefalutPara("8036").equals("12781001")&&salesOrder.getInteger("SO_STATUS")>Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING))) {
                    List<Object> intentReceiveMoneyList = new ArrayList<Object>();
                    intentReceiveMoneyList.add(loginInfo.getDealerCode());
                    intentReceiveMoneyList.add(salesOrder.getString("INTENT_SO_NO"));
                    List<TtIntentReceiveMoneyPO> receivePo = TtIntentReceiveMoneyPO.findBySQL("select * from TT_INTENT_RECEIVE_MONEY where DEALER_CODE= ? AND INTENT_SO_NO= ? ",intentReceiveMoneyList.toArray());
                   if(receivePo!=null&&receivePo.size()>0){
                       TtCustomerGatheringPO gatheringPO = new TtCustomerGatheringPO();
                       for(int j=0;j<receivePo.size();j++){
                           TtIntentReceiveMoneyPO receive = receivePo.get(j);
                           if(!StringUtils.isNullOrEmpty(receive.getInteger("IS_ADVANCE_PAYMENTS"))&&receive.getInteger("IS_ADVANCE_PAYMENTS")!=12781001){
                               String No = commonNoService.getSystemOrderNo(CommonConstants.SRV_SKDH);
                               gatheringPO.setString("BILL_NO",receive.getString("BILL_NO"));
                               gatheringPO.setString("CUSTOMER_NO",salesOrder.getString("CUSTOMER_NO"));
                               gatheringPO.setString("SO_NO",receive.getString("SO_NO"));
                               gatheringPO.setInteger("GATHERING_TYPE",receive.getInteger("GATHERING_TYPE"));
                               gatheringPO.setString("OWNED_BY",String.valueOf(loginInfo.getUserId()));
                               gatheringPO.setString("PAY_TYPE_CODE",receive.getString("0004"));
                               gatheringPO.setDouble("RECEIVE_AMOUNT",receive.getDouble("RECEIVE_AMOUNT"));
                               gatheringPO.setDate("RECEIVE_DATE",receive.getDate("RECEIVE_DATE"));
                               gatheringPO.setString("RECEIVE_NO",No);
                               gatheringPO.setDate("RECORD_DATE",new Date());
                               gatheringPO.setString("RECORDER",receive.getString("RECORDER"));
                               gatheringPO.setString("TRANSACTOR",receive.getString("TRANSACTOR"));
                               gatheringPO.setString("WRITEOFF_BY",receive.getString("WRITEOFF_BY"));
                               gatheringPO.setDate("WRITEOFF_DATE",new Date());
                               gatheringPO.setInteger("WRITEOFF_TAG",DictCodeConstants.STATUS_IS_YES);
                               gatheringPO.setString("REMARK",receive.getString("REMARK"));
                               gatheringPO.saveIt();
                               //更新客户资料表中收款总金额和帐户可用余额
                               if(!StringUtils.isNullOrEmpty(salesOrder.getString("CUSTOMER_NO"))){
                                   PotentialCusPO cusPo = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrder.getString("CUSTOMER_NO"));
                                   if(cusPo!=null){
                                       Double receiveAmount = receive.getDouble("RECEIVE_AMOUNT");
                                       Double gatheredSum = 0D;
                                       Double usableAmount = 0D;
                                       if(cusPo.getDouble("GATHERED_SUM")!=null){
                                           gatheredSum = cusPo.getDouble("GATHERED_SUM")+receiveAmount;
                                       }else{
                                           gatheredSum = receiveAmount;
                                       }
                                       cusPo.setDouble("GATHERED_SUM", gatheredSum);
                                       if(cusPo.getDouble("USABLE_AMOUNT")!=null){
                                           usableAmount = cusPo.getDouble("USABLE_AMOUNT")+receiveAmount;
                                       }else{
                                           usableAmount = receiveAmount;
                                       }
                                       cusPo.setDouble("USABLE_AMOUNT", usableAmount);
                                       cusPo.saveIt();
                                   }
                               }
                               receive.setInteger("IS_ADVANCE_PAYMENTS", 12781001);
                               receive.saveIt();
                           }   

                       }
                       List<Object> intentReceiveList = new ArrayList<Object>();
                       intentReceiveList.add(loginInfo.getDealerCode());
                       intentReceiveList.add(salesOrder.getString("INTENT_SO_NO"));
                       List<TtIntentReceiveMoneyPO> intentReceivePo = TtIntentReceiveMoneyPO.findBySQL("select * from TT_INTENT_RECEIVE_MONEY where DEALER_CODE= ? AND INTENT_SO_NO= ? AND (IS_ADVANCE_PAYMENTS != 12781001 OR IS_ADVANCE_PAYMENTS IS NULL) ",intentReceiveList.toArray());
                       if(!StringUtils.isNullOrEmpty(intentReceivePo)&&intentReceivePo.size()>0){
                           TtIntentSalesOrderPO intentOrderPO = TtIntentSalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrder.getString("INTENT_SO_NO"));
                           if(intentOrderPO!=null){
                               intentOrderPO.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_INTENT_SO_STATUS_HAVE_CLOSED));
                               intentOrderPO.saveIt();
                           }
                       }
                   }
                    
                }
             }
        int flag1=1;
        int flag2=1;
       // int flag2=1;
            try {
                logger.info("=================SEDMS060.getSEDMS060("+soNo+",12781001,1);");
                flag1 =SEDMS060.getSEDMS060(soNo, "12781002", "1");
                flag2=DMSTODCS004.getDMSTODCS004(soNo, deliveryModeElec);
            } catch (Exception e) {
                throw new ServiceBizException(e.getMessage()); 
            }
            if("0".equals(flag1)){
                throw new ServiceBizException("电商订单状态上报失败！"); 
            }
            if(flag2==0){
                throw new ServiceBizException("CALL车上报失败"); 
            }
        }  
    }

    
    

    
    /**
    * @author LiGaoqi
    * @date 2017年4月6日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#queryCustomerAndIntent1(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryCustomerAndIntent1(Map<String, String> queryParam) throws ServiceBizException {


        StringBuffer sql = new StringBuffer("");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        List<Object> paramsList = new ArrayList<Object>();
        String number = "";
        String cuName = "";
        String cuType = "";
        String cuStatus = "";
        String cuMobile ="";
        String mainModel ="";
        String isAf = "";
        String cuCustomerStatus = "";
        String cuSoldBy2 = "";
        String strBF = "";
        if(!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))){
            number =  " and A.CUSTOMER_NO LIKE '%"+queryParam.get("customerNo")+"%' ";
        }else{
            number = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("customerName"))){
            cuName =  " and A.CUSTOMER_NAME LIKE '%"+queryParam.get("customerName")+"%' ";
        }else{
            cuName = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("customerType"))){
            cuType =  " and A.CUSTOMER_TYPE  =  " + queryParam.get("customerType") + "  ";;
        }else{
            cuType = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("intentLevel"))){
            cuStatus =  " and A.INTENT_LEVEL  =  " + queryParam.get("intentLevel") + "  ";;
        }else{
            cuStatus = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("contactorMobile"))){
            cuMobile =  " and A.CONTACTOR_MOBILE  LIKE  '%" + queryParam.get("contactorMobile") + "%'  ";;
        }else{
            cuMobile = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("isMainModel"))){
            mainModel =  " and C.IS_MAIN_MODEL  =  " + queryParam.get("isMainModel") + "  ";;
        }else{
            mainModel = " and 1 = 1";
        }
        isAf = " and A.INTENT_LEVEL <> "+DictCodeConstants.DICT_INTENT_LEVEL_F+" and A.INTENT_LEVEL <> "+DictCodeConstants.DICT_INTENT_LEVEL_FO+"  and A.INTENT_LEVEL <> "+DictCodeConstants.DICT_INTENT_LEVEL_D+"";
        StringBuilder sqlSb = new StringBuilder("select * from tm_user_CTRL where DEALER_CODE= ? AND USER_ID= ? AND CTRL_CODE=80900000 ");
        List<Object> params = new ArrayList<Object>();
        params.add(loginInfo.getUserId());
        params.add(loginInfo.getDealerCode());
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
        if(list!=null&&list.size()>0){
            strBF = " and 1 = 1";
        }else{
            strBF = " and A.IS_BIG_CUSTOMER = "+DictCodeConstants.DICT_IS_NO+" ";
        }
        cuCustomerStatus = " and 1 = 1";
        DAOUtilGF.getOwnedByStr("A", loginInfo.getUserId(), loginInfo.getOrgCode(),  "45701500", loginInfo.getDealerCode());
      //替换权限
        sql.append(" SELECT DISTINCT A.CONTACTOR_MOBILE AS MOBILE1,CASE WHEN A.CONTACTOR_MOBILE IS NULL THEN A.CONTACTOR_PHONE ELSE A.CONTACTOR_MOBILE END AS MOBILE, A.*,tm.MEDIA_DETAIL_NAME, em.USER_NAME,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME, (CASE WHEN B.INTENT_ID IS NULL THEN 0 ELSE B.INTENT_ID END) AS BINTENT_ID,tl.CONTACTOR_NAME ");
                //000
        sql.append("  ,c.INTENT_BRAND,c.INTENT_SERIES,c.INTENT_MODEL,c.INTENT_CONFIG,c.INTENT_COLOR, c.IS_MAIN_MODEL,c.RETAIL_FINANCE,c.DEPOSIT_AMOUNT   ");
                //111
        sql.append(" FROM TM_POTENTIAL_CUSTOMER A INNER JOIN TT_CUSTOMER_INTENT B ON B.DEALER_CODE = A.DEALER_CODE AND B.D_KEY = A.D_KEY AND B.CUSTOMER_NO = A.CUSTOMER_NO AND B.INTENT_ID=A.INTENT_ID ");
                //000
        sql.append(" left join  TT_CUSTOMER_INTENT_DETAIL C on c.DEALER_CODE = A.DEALER_CODE and c.INTENT_ID=A.INTENT_ID AND C.IS_MAIN_MODEL=12781001");
        sql.append(" left join TT_PO_CUS_LINKMAN tl on A.CUSTOMER_NO=tl.CUSTOMER_NO and A.DEALER_CODE=tl.DEALER_CODE and tl.IS_DEFAULT_CONTACTOR=12781001");
        sql.append(" left  join   tm_brand   br   on   c.INTENT_BRAND = br.BRAND_CODE and b.DEALER_CODE=br.DEALER_CODE\n");
        sql.append(" left  join   TM_SERIES  se   on   c.INTENT_SERIES=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and b.DEALER_CODE=se.DEALER_CODE\n");
        sql.append(" left  join   TM_MODEL   mo   on   c.INTENT_MODEL=mo.MODEL_CODE and se.BRAND_CODE=mo.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and b.DEALER_CODE=mo.DEALER_CODE\n");
        sql.append(" left  join   tm_configuration pa   on   c.INTENT_CONFIG=pa.CONFIG_CODE and mo.MODEL_CODE=pa.MODEL_CODE and mo.SERIES_CODE=pa.SERIES_CODE and mo.BRAND_CODE=pa.BRAND_CODE and b.DEALER_CODE=pa.DEALER_CODE\n");
        sql.append(" left  join   tm_color   co   on   C.INTENT_COLOR = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
        sql.append(" left  join   tt_media_detail   tm   on   tm.MEDIA_DETAIL = A.MEDIA_DETAIL and tm.DEALER_CODE=A.DEALER_CODE");
        sql.append(" left  join TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE");  
        sql.append(" WHERE A.DEALER_CODE = '"+ loginInfo.getDealerCode()+ "' "+ " AND A.D_KEY =  "+ DictCodeConstants.D_KEY+ cuName+ number+ cuMobile+ cuType+ mainModel+ cuStatus+isAf+strBF+ cuCustomerStatus+cuSoldBy2);
        sql.append(DAOUtilGF.getOwnedByStr("A", loginInfo.getUserId(), loginInfo.getOrgCode(),  "45701500", loginInfo.getDealerCode()));//替换权限201003
        
        return DAOUtil.pageQuery(sql.toString(), paramsList);
    
    
    }

    /**
    * @author LiGaoqi
    * @date 2017年2月17日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#queryCustomerAndIntent(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryCustomerAndIntent(Map<String, String> queryParam) throws ServiceBizException {

        StringBuffer sql = new StringBuffer("");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        List<Object> paramsList = new ArrayList<Object>();
        String number = "";
        String cuName = "";
        String cuType = "";
        String cuStatus = "";
        String cuMobile ="";
        String mainModel ="";
        String isAf = "";
        String cuCustomerStatus = "";
        String cuSoldBy2 = "";
        String strBF = "";
        if(!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))){
            number =  " and A.CUSTOMER_NO LIKE '%"+queryParam.get("customerNo")+"%' ";
        }else{
            number = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("customerName"))){
            cuName =  " and A.CUSTOMER_NAME LIKE '%"+queryParam.get("customerName")+"%' ";
        }else{
            cuName = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("customerType"))){
            cuType =  " and A.CUSTOMER_TYPE  =  " + queryParam.get("customerType") + "  ";;
        }else{
            cuType = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("intentLevel"))){
            cuStatus =  " and A.INTENT_LEVEL  =  " + queryParam.get("intentLevel") + "  ";;
        }else{
            cuStatus = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("contactorMobile"))){
            cuMobile =  " and A.CONTACTOR_MOBILE  LIKE  '%" + queryParam.get("contactorMobile") + "%'  ";;
        }else{
            cuMobile = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("isMainModel"))){
            mainModel =  " and C.IS_MAIN_MODEL  =  " + queryParam.get("isMainModel") + "  ";;
        }else{
            mainModel = " and 1 = 1";
        }
        isAf = " and A.INTENT_LEVEL <> "+DictCodeConstants.DICT_INTENT_LEVEL_F+" and A.INTENT_LEVEL <> "+DictCodeConstants.DICT_INTENT_LEVEL_FO+"  ";
        StringBuilder sqlSb = new StringBuilder("select * from tm_user_CTRL where DEALER_CODE= ? AND USER_ID= ? AND CTRL_CODE=80900000 ");
        List<Object> params = new ArrayList<Object>();
        params.add(loginInfo.getUserId());
        params.add(loginInfo.getDealerCode());
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
        if(list!=null&&list.size()>0){
            strBF = " and 1 = 1";
        }else{
            strBF = " and A.IS_BIG_CUSTOMER = "+DictCodeConstants.DICT_IS_NO+" ";
        }
        cuCustomerStatus = " and 1 = 1";
        DAOUtilGF.getOwnedByStr("A", loginInfo.getUserId(), loginInfo.getOrgCode(),  "45701500", loginInfo.getDealerCode());
      //替换权限
        sql.append(" SELECT DISTINCT A.CONTACTOR_MOBILE AS MOBILE, A.*, em.USER_NAME,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME, (CASE WHEN B.INTENT_ID IS NULL THEN 0 ELSE B.INTENT_ID END) AS BINTENT_ID,tl.CONTACTOR_NAME ");
                //000
        sql.append("  ,c.INTENT_BRAND,c.INTENT_SERIES,c.INTENT_MODEL,c.INTENT_CONFIG,c.INTENT_COLOR, c.IS_MAIN_MODEL,c.RETAIL_FINANCE,c.DEPOSIT_AMOUNT   ");
                //111
        sql.append(" FROM TM_POTENTIAL_CUSTOMER A INNER JOIN TT_CUSTOMER_INTENT B ON B.DEALER_CODE = A.DEALER_CODE AND B.D_KEY = A.D_KEY AND B.CUSTOMER_NO = A.CUSTOMER_NO AND B.INTENT_ID=A.INTENT_ID ");
                //000
        sql.append(" left join  TT_CUSTOMER_INTENT_DETAIL C on c.DEALER_CODE = A.DEALER_CODE and c.INTENT_ID=A.INTENT_ID AND C.IS_MAIN_MODEL=12781001");
        sql.append(" left join TT_PO_CUS_LINKMAN tl on A.CUSTOMER_NO=tl.CUSTOMER_NO and A.DEALER_CODE=tl.DEALER_CODE and tl.IS_DEFAULT_CONTACTOR=12781001");
        sql.append(" left  join   tm_brand   br   on   c.INTENT_BRAND = br.BRAND_CODE and b.DEALER_CODE=br.DEALER_CODE\n");
        sql.append(" left  join   TM_SERIES  se   on   c.INTENT_SERIES=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and b.DEALER_CODE=se.DEALER_CODE\n");
        sql.append(" left  join   TM_MODEL   mo   on   c.INTENT_MODEL=mo.MODEL_CODE and se.BRAND_CODE=mo.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and b.DEALER_CODE=mo.DEALER_CODE\n");
        sql.append(" left  join   tm_configuration pa   on   c.INTENT_CONFIG=pa.CONFIG_CODE and mo.MODEL_CODE=pa.MODEL_CODE and mo.SERIES_CODE=pa.SERIES_CODE and mo.BRAND_CODE=pa.BRAND_CODE and b.DEALER_CODE=pa.DEALER_CODE\n");
        sql.append(" left  join   tm_color   co   on   C.INTENT_COLOR = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
        sql.append(" left  join TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE");  
        sql.append(" WHERE A.DEALER_CODE = '"+ loginInfo.getDealerCode()+ "' "+ " AND A.D_KEY =  "+ DictCodeConstants.D_KEY+ cuName+ number+ cuMobile+ cuType+ mainModel+ cuStatus+isAf+strBF+ cuCustomerStatus+cuSoldBy2);
        sql.append(DAOUtilGF.getOwnedByStr("A", loginInfo.getUserId(), loginInfo.getOrgCode(),  "45701500", loginInfo.getDealerCode()));//替换权限201003
        
        return DAOUtil.pageQuery(sql.toString(), paramsList);
    
    }

    
    /**
    * @author LiGaoqi
    * @date 2017年2月17日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#queryoldCustomerVin(java.lang.String)
    */
    	
    @Override
    public PageInfoDto queryoldCustomerVin(String id) throws ServiceBizException {

        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT A.*,C.MODEL_NAME AS MODEL,B.BRAND_NAME AS BRAND,A.LICENSE AS ASSESSED_LICENSE,A.VIN AS PERMUTED_VIN,A.BRAND_NAME AS OLD_BRAND_CODE,A.SERIES_NAME AS OLD_SERIES_CODE,A.MODEL_NAME AS OLD_MODEL_CODE FROM TT_CUSTOMER_VEHICLE_LIST A "); 
        sql.append(" LEFT JOIN TM_SC_BRAND B ON A.DEALER_CODE=B.DEALER_CODE AND A.BRAND_NAME=B.BRAND_CODE");
        sql.append(" LEFT JOIN TM_SC_MODEL C ON A.DEALER_CODE=B.DEALER_CODE AND A.MODEL_NAME=C.MODEL_CODE");
        sql.append(" WHERE  A.CUSTOMER_NO = '"+id+"' AND A.DEALER_CODE='"+loginInfo.getDealerCode()+"'"); 
        sql.append(" AND A.VIN IS NOT NULL AND A.LICENSE IS NOT NULL ");
        List<Object> paramsList = new ArrayList<Object>();
        return DAOUtil.pageQuery(sql.toString(), paramsList);
    
    }

    
    /**
    * @author LiGaoqi
    * @date 2017年2月17日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#queryallLoanRate(java.lang.String)
    */
    	
    @Override
    public PageInfoDto queryallLoanRate(String id) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        sql.append("SELECT distinct A.*,br.BARND_NAME,se.SERIES_NAME,mo.MODEL_NAME,A.BANK_CODE AS LOAN_ORG,A.DPM_S AS FIRST_PERMENT_RANGE_S,A.DPM_E AS FIRST_PERMENT_RANGE_E,A.RATE AS LOAN_RATE,B.CONFIG_NAME,C.BANK_NAME  FROM TM_BANK_LOAN_RATE A ");
        sql.append("inner join ("+CommonConstants.VM_CONFIGURATION+") B ON A.DEALER_CODE=B.DEALER_CODE AND ");
        sql.append(" A.STYLE_CODE=B.CONFIG_CODE inner join TM_PAYING_BANK C ON A.BANK_CODE=C.BANK_CODE ");
        sql.append( " left  join   tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and br.DEALER_CODE=A.DEALER_CODE");
        sql.append( " left  join   TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and A.DEALER_CODE=se.DEALER_CODE");
        sql.append( " left  join   TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and se.BRAND_CODE=mo.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and A.DEALER_CODE=mo.DEALER_CODE");
        sql.append(" WHERE A.DEALER_CODE = '" + loginInfo.getDealerCode() + "'"+ " AND A.STYLE_CODE = '" + id + "'"+ " AND A.IS_VALID = 12781001"+" AND A.EFFECTIVE_DATE_S <=CURDATE()"+" AND A.EFFECTIVE_DATE_E >=CURDATE()");
        List<Object> params = new ArrayList<Object>();
        return DAOUtil.pageQuery(sql.toString(), params);
    
    }

    //设置订单保存值
    private SalesOrderPO setSalesPO(SalesOrderDTO salesOrderDTO, String soNo) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        SalesOrderPO po = new SalesOrderPO();
        if(!StringUtils.isNullOrEmpty(salesOrderDTO.getSoNo())&&salesOrderDTO.getSoNo().equals(soNo)){
            po = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrderDTO.getSoNo());
        }
        
        po.setString("DISPATCHED_BY",salesOrderDTO.getSoldBy());
        po.setString("MOBILE", salesOrderDTO.getMobile());
        po.setString("CUSTOMER_NAME", salesOrderDTO.getCustomerName());
        po.setString("CUSTOMER_NO", salesOrderDTO.getCustomerNo());
        po.setString("INTENT_SO_NO", salesOrderDTO.getIntentSoNo());
        po.setInteger("CUSTOMER_TYPE", salesOrderDTO.getCustomerType());
        po.setInteger("CUS_SOURCE", salesOrderDTO.getCusSource());
        po.setInteger("CT_CODE", salesOrderDTO.getCtCode());
        po.setString("CERTIFICATE_NO", salesOrderDTO.getCertificateNo());
        po.setString("CONTACTOR_NAME", salesOrderDTO.getContactorName());
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(salesOrderDTO.getMobile());
        if(m.matches()){
            po.setString("MOBILE", salesOrderDTO.getMobile());// 手机
            po.setString("PHONE", null);// 电话
        }else{
            po.setString("MOBILE", null);// 手机
            po.setString("PHONE", salesOrderDTO.getMobile());// 电话 
        }
        po.setString("MOBILE",salesOrderDTO.getMobile());
        po.setString("ADDRESS", salesOrderDTO.getAddress());
        po.setString("STORAGE_CODE", salesOrderDTO.getStorageCode());
        po.setInteger("BUSINESS_TYPE", Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL));
       
     /*   if(!StringUtils.isNullOrEmpty(salesOrderDTO.getSheetCreateDate())){
            po.setDate("SHEET_CREATE_DATE", salesOrderDTO.getSheetCreateDate());
        }else{
            po.setDate("SHEET_CREATE_DATE", new Date());
        }*/
        po.setString("SHEET_CREATED_BY",loginInfo.getUserId().toString());
        if(!StringUtils.isNullOrEmpty(salesOrderDTO.getContractDate())){
            po.setDate("CONTRACT_DATE", salesOrderDTO.getContractDate());
        }else{
            po.setDate("CONTRACT_DATE", new Date());
        } 
        if(!StringUtils.isNullOrEmpty(salesOrderDTO.getSoldBy())){
            po.setString("SOLD_BY", salesOrderDTO.getSoldBy());
        }else{
            po.setString("SOLD_BY", loginInfo.getUserId().toString()); 
        }
        if(!StringUtils.isNullOrEmpty(salesOrderDTO.getSoldBy())){
            po.setString("OWNED_BY", salesOrderDTO.getSoldBy());
        }else{
            po.setString("OWNED_BY", loginInfo.getUserId().toString()); 
        }
        po.setString("CONTRACT_NO", salesOrderDTO.getContractNo());
        po.setDate("CONTRACT_MATURITY", salesOrderDTO.getContractMaturity());
        po.setDouble("CONTRACT_EARNEST", salesOrderDTO.getContractEarnest());
        po.setDate("DELIVERING_DATE", salesOrderDTO.getDeliveringDate());
        po.setInteger("DELIVERY_MODE_ELEC", salesOrderDTO.getDeliveryModeElec());
        po.setInteger("DELIVERY_MODE", salesOrderDTO.getDeliveryMode());
        po.setInteger("PAY_MODE", salesOrderDTO.getPayMode());
        po.setInteger("INVOICE_MODE", salesOrderDTO.getInvoiceMode());
        if(!StringUtils.isNullOrEmpty(salesOrderDTO.getSoNo())&&salesOrderDTO.getSoNo().equals(soNo)){
            SalesOrderPO salesPo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrderDTO.getSoNo());
            if(salesPo.getInteger("SO_STATUS")!=null&&(salesPo.getInteger("SO_STATUS")==13011015||salesPo.getInteger("SO_STATUS")==13011020||salesPo.getInteger("SO_STATUS")==13011025)){
                po.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_SYSTEM_REJECT)); 
                SoAuditingPO anPo = new SoAuditingPO();
                anPo.setString("SO_NO", salesOrderDTO.getSoNo());
                anPo.setLong("AUDITED_BY", loginInfo.getUserId());
                //anPo.setDate("AUDITING_DATE", new Date());
                anPo.setLong("SUBMIT_AUDITED_BY", loginInfo.getUserId());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String d = format.format(new Date());
                anPo.setString("COMMIT_TIME", d);
                anPo.setString("AUDITING_DATE", d);
                anPo.setString("AUDITING_POSTIL", "订单改动，系统自动驳回!");
                //anPo.setInteger("AUDITED_BY_IDENTITY", Integer.parseInt(DictCodeConstants.DICT_AUDITING_TYPE_FINANCE));
//                if(auditDto.getTreatmentAdvice()!=null){
//                    anPo.setString("TREATMENT_ADVICE", auditDto.getTreatmentAdvice());
//                }
                anPo.setInteger("AUDITING_RESULT", Integer.parseInt(DictCodeConstants.DICT_AUDITING_RESULT_REJECT));
                anPo.saveIt();
            }
            if(salesPo.getInteger("SO_STATUS")!=null&&salesPo.getInteger("SO_STATUS")==Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_MANAGER_REJECT)){
                po.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_MANAGER_REJECT)); 
            }
            if(salesPo.getInteger("SO_STATUS")!=null&&salesPo.getInteger("SO_STATUS")==Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_REJECT)){
                po.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_REJECT)); 
            }
        }else{
            po.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_NOT_AUDIT)); 
        }
        po.setString("PROVINCE", salesOrderDTO.getProvince());
        po.setString("CITY", salesOrderDTO.getCity());
        po.setString("DISTRICT", salesOrderDTO.getDistrict());
        //车辆信息
        po.setString("VIN", salesOrderDTO.getVin());
        po.setString("COLOR_CODE", salesOrderDTO.getColor());
        po.setString("PRODUCT_CODE", salesOrderDTO.getProductCode());
        //po.setString("PRODUCT_NAME", salesOrderDTO.getProductname());
        po.setInteger("VEHICLE_PURPOSE", salesOrderDTO.getVehiclePurpose());
        //po.setString("ENGINE_NO", salesOrderDTO.getEngineNo());
       // po.setString("CERTIFICATE_NUMBER", salesOrderDTO.getCertificateNumber());
        po.setInteger("LOAN_ORG", salesOrderDTO.getLoanOrg());
        po.setDouble("FIRST_PERMENT_RATIO", salesOrderDTO.getFirstPermentRatio());
        po.setDouble("FIRST_PERMENT_RANGE_E", salesOrderDTO.getFirstPermentRangeE());
        po.setDouble("FIRST_PERMENT_RANGE_S", salesOrderDTO.getFirstPermentRangeS());
        po.setDouble("INSTALLMENT_AMOUNT", salesOrderDTO.getInstallmentAmount());
        po.setInteger("INSTALLMENT_NUMBER", salesOrderDTO.getInstallmentNumber());
        po.setDouble("LOAN_RATE", salesOrderDTO.getLoanRate());
        //二手车信息
        if(!StringUtils.isNullOrEmpty(salesOrderDTO.getIsPermuted())){
            po.setInteger("IS_PERMUTED", salesOrderDTO.getIsPermuted());
        }else{
            po.setInteger("IS_PERMUTED", 12781002);
        }
        po.setString("STORAGE_POSITION_CODE", salesOrderDTO.getStoragePositionCode());
        po.setString("PERMUTED_VIN", salesOrderDTO.getPermutedVin());
        po.setString("OLD_BRAND_CODE", salesOrderDTO.getOldBrandCode());
        po.setString("OLD_SERIES_CODE", salesOrderDTO.getOldSeriesCode());
        po.setString("OLD_MODEL_CODE", salesOrderDTO.getOldModelCode());
        po.setString("ASSESSED_LICENSE", salesOrderDTO.getAssessedLicense());
        po.setDouble("ASSESSED_PRICE", salesOrderDTO.getAssessedPrice());
        po.setDouble("OLD_CAR_PURCHASE", salesOrderDTO.getOldCarPurchase());
        po.setString("PERMUTED_DESC", salesOrderDTO.getPermutedDesc());
        po.setDouble("VEHICLE_PRICE", salesOrderDTO.getVehiclePrice());
        po.setDouble("ORDER_RECEIVABLE_SUM", salesOrderDTO.getOrderReceivableSum());
        po.setDouble("ORDER_SUM", salesOrderDTO.getOrderSum());
        po.setDouble("PRESENT_SUM", salesOrderDTO.getPresentSum());
        po.setDouble("OFFSET_AMOUNT", salesOrderDTO.getOffsetAmount());
        po.setString("OTHER_AMOUNT_OBJECT", salesOrderDTO.getOtherAmountObject());
        po.setDouble("OTHER_AMOUNT", salesOrderDTO.getOtherAmount());
        po.setString("REMARK", salesOrderDTO.getRemark());
        po.setString("EC_ORDER_NO", salesOrderDTO.getEcOrderNo());
        po.setInteger("DELIVERY_MODE_ELEC", salesOrderDTO.getDeliveryModeElec());
        return po;
    }
    //跟进活动
    private void creatTrackingTask(String entitycode,String cusNo,String cusname,Long intentid,Long userid,String soldby) throws ServiceBizException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        PotentialCusPO cuspo = PotentialCusPO.findByCompositeKeys(entitycode,cusNo);
        String groupCode=Utility.getGroupEntity(entitycode, "TM_TRACKING_TASK");
        List<Object> taskList = new ArrayList<Object>();
        taskList.add(cuspo.getInteger("INTENT_LEVEL"));
        taskList.add(DictCodeConstants.IS_YES);
        taskList.add(groupCode);
        List<TrackingTaskPO> taskPO=TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ? AND  IS_VALID= ? AND DEALER_CODE= ? ", taskList.toArray());
        if(taskPO!=null&&taskPO.size()>0){
            for(int j = 0;j<taskPO.size();j++){
                System.out.println("——————5");
                TrackingTaskPO task = taskPO.get(j);
                TtSalesPromotionPlanPO sPlanPo = new TtSalesPromotionPlanPO();
                sPlanPo.setLong("TASK_ID", task.getLong("TASK_ID"));
                sPlanPo.setLong("INTENT_ID", cuspo.getLong("INTENT_ID"));
                sPlanPo.setString("CUSTOMER_NO", cusNo);
                sPlanPo.setString("CUSTOMER_NAME", cuspo.getString("CUSTOMER_NAME"));
                sPlanPo.setInteger("PRIOR_GRADE",cuspo.getInteger("INTENT_LEVEL"));
                sPlanPo.setString("PROM_CONTENT",task.getString("TASK_CONTENT"));
                sPlanPo.setInteger("PROM_WAY",task.getInteger("EXECUTE_TYPE"));
                sPlanPo.setInteger("IS_AUDITING",12781002);
                sPlanPo.setInteger("CREATE_TYPE",13291001);
                sPlanPo.setInteger("NEXT_GRADE",null);
                String dates=new String();
                sPlanPo.setDate("ACTION_DATE",null);
                if(task.getInteger("INTERVAL_DAYS")!=null){
                    c.setTime(new Date());
                    c.add(7, task.getInteger("INTERVAL_DAYS"));
                    dates = format.format(c.getTime()).toString();
                }
                try {
                    sPlanPo.setDate("SCHEDULE_DATE",format.parse(dates));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                sPlanPo.setString("PHONE", cuspo.getString("CONTACTOR_NAME"));
                sPlanPo.setInteger("PROM_RESULT",null);
                sPlanPo.setString("SCENE",null);
                sPlanPo.setString("SOLD_BY",soldby.toString());
                sPlanPo.setString("OWNED_BY",soldby.toString());
                List<Object> linkList = new ArrayList<Object>();
                linkList.add(cusNo);
                linkList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                linkList.add(entitycode);
                List<TtPoCusLinkmanPO> linkPO=TtPoCusLinkmanPO.findBySQL("select * from TT_PO_CUS_LINKMAN where CUSTOMER_NO= ? AND  D_KEY= ? AND DEALER_CODE= ? ", linkList.toArray());
                if(linkPO!=null&&linkPO.size()>0){
                    TtPoCusLinkmanPO PO = linkPO.get(0);
                    if(!StringUtils.isNullOrEmpty(PO.getString("CONTACTOR_NAME"))){
                        sPlanPo.setString("CONTACTOR_NAME", PO.getString("CONTACTOR_NAME"));
                    }
                    if(!StringUtils.isNullOrEmpty(PO.getString("PHONE"))){
                        sPlanPo.setString("PHONE", PO.getString("PHONE"));
                    }
                    if(!StringUtils.isNullOrEmpty(PO.getString("Mobile"))){
                        sPlanPo.setString("MOBILE", PO.getString("MOBILE"));
                    }
                }
                sPlanPo.saveIt();
                
            }
        }
    }
    
    public List<Map> queryMatchVehicleByCode(String productCode,
                                             String entityCode, String vin, String businessType,
                                             String brandCode, String seriesCode, String modelCode,
                                             String isTestDriverCar,String storageCode,String deliveryMode,String isSpeed,int isLike) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        List<Object> queryList = new ArrayList<Object>();
        //本地交车方式
        sql.append(" SELECT A.VIN,A.DEALER_CODE,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE, B.PRODUCT_CODE,B.PRODUCT_NAME,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,B.DIRECTIVE_PRICE, A.STORAGE_CODE,A.ENGINE_NO,A.STOCK_STATUS,A.MAR_STATUS,A.IS_SECONDHAND,");
        sql.append(" A.IS_TEST_DRIVE_CAR,A.IS_VIP,A.IS_CONSIGNED,A.IS_PROMOTION, A.HAS_CERTIFICATE,A.LATEST_STOCK_OUT_DATE,A.MANUFACTURE_DATE,A.REMARK,B.WHOLESALE_DIRECTIVE_PRICE, A.STORAGE_POSITION_CODE");
        sql.append(" FROM TM_VS_STOCK A, ("+CommonConstants.VM_VS_PRODUCT+") B WHERE  A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE ");
        sql.append(" AND A.D_KEY = "+ DictCodeConstants.D_KEY+ " AND (A.STOCK_STATUS = "+ DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE+ " OR A.STOCK_STATUS = "+ DictCodeConstants.DICT_STORAGE_STATUS_ON_WAY+ " OR A.STOCK_STATUS = "+ DictCodeConstants.DICT_STORAGE_STATUS_LEND_TO+ ")");
        sql.append(" AND A.DISPATCHED_STATUS = "+ DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED+ " AND A.IS_VIP = "+ DictCodeConstants.DICT_IS_NO+ " AND A.IS_PURCHASE_RETURN = "+ DictCodeConstants.DICT_IS_NO );
        if(null == storageCode || "".equals(storageCode.trim())){
            sql.append(" AND A.STORAGE_CODE = '  '");
        }else{
            sql.append( " AND A.STORAGE_CODE IN (" + storageCode + ")");
        }
        if (businessType != null
                && !businessType
                        .equals(DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY)) {
            sql.append(" AND A.IS_CONSIGNED = "
                    + DictCodeConstants.DICT_IS_NO                       
                    + " AND A.IS_DIRECT = "
                    + DictCodeConstants.DICT_IS_NO);
        } 
        if(!StringUtils.isNullOrEmpty(productCode)){
            sql.append(" and A.PRODUCT_CODE= ? ");
            queryList.add(productCode);
        }
        if(!StringUtils.isNullOrEmpty(vin)){
            sql.append(" and A.VIN= ? ");
            queryList.add(vin);
        }  
        return DAOUtil.findAll(sql.toString(), queryList);
    }
    
    
    /**
    * @author LiGaoqi
    * @date 2017年2月17日
    * @param salesOrderDTO
    * @param soNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#addSalesOrders(com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO, java.lang.String)
    */	
    @Override
    public Map addSalesOrder(SalesOrderDTO salesOrderDTO, String soNo) throws ServiceBizException {
        
        System.out.println(soNo);
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        SalesOrderPO updatePo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),soNo);
        String uodateVin="";
        if(updatePo!=null){
            if(!StringUtils.isNullOrEmpty(updatePo.getString("VIN"))){
                uodateVin = updatePo.getString("VIN");
            }
        }
        SalesOrderPO salesOrderPO = new SalesOrderPO();
        salesOrderPO =this.setSalesPO(salesOrderDTO, soNo);
        salesOrderPO.setString("SO_NO",soNo);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("日期"+format1.format(new Date()));
        //po.setString("SHEET_CREATE_DATE",  format1.format(new Date()));
        if(StringUtils.isNullOrEmpty(updatePo)){
            salesOrderPO.setString("DISPATCHED_DATE",format1.format(new Date()));
            System.out.println("日期"+format1.format(new Date()));
            salesOrderPO.setString("SHEET_CREATE_DATE",  format1.format(new Date()));
        }
        //如果是一般销售订单，将客户意向ID写到订单里
        PotentialCusPO cusPO = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrderDTO.getCustomerNo());
        if(cusPO!=null){
            salesOrderPO.setLong("INTENT_ID", cusPO.getLong("INTENT_ID")); 
        }
        salesOrderPO.saveIt();
        String salesid="";
        if(!StringUtils.isNullOrEmpty(salesOrderDTO.getWsNo())){
            String groupCode=Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_VS_PRODUCT");
            VsProductPO vspo  = VsProductPO.findByCompositeKeys(groupCode,salesOrderDTO.getProductCode());
            if(vspo!=null&&!StringUtils.isNullOrEmpty(vspo.getString("CONFIG_CODE"))){
                List<Object> queryList = new ArrayList<Object>();
                queryList.add(salesOrderDTO.getWsNo());
                queryList.add(loginInfo.getDealerCode()); 
                queryList.add(salesOrderDTO.getWsNo());
                queryList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                List<TtWsConfigInfoPO> wsConfigInfoPO = TtWsConfigInfoPO.findBySQL("select * from TT_WS_CONFIG_INFO where WS_NO= ? AND DEALER_CODE= ? AND CONFIG_CODE= ? AND D_KEY= ? ",queryList.toArray());           
                if(wsConfigInfoPO!=null&&wsConfigInfoPO.size()>0){
                    TtWsConfigInfoPO wsCPO =wsConfigInfoPO.get(0);
                    TtWsSalesInfoPO info = new TtWsSalesInfoPO();
                    info.setString("CUSTOMER_NO", salesOrderDTO.getCustomerNo());
                    info.setString("SO_NO", soNo);
                    info.setString("VIN", salesOrderDTO.getVin());
                    info.setString("CUSTOMER_NAME", salesOrderDTO.getCustomerName());
                    info.setString("PRODUCT_CODE", salesOrderDTO.getProductCode());
                    info.setString("REMARK","通过销售订单产生的信息");
                    info.setInteger("AUDITING_STATUS", Integer.parseInt(DictCodeConstants.DICT_WHOLESALE_CAR_STATUS_NOT));
                    info.setString("OWNED_BY", loginInfo.getUserId().toString());
                    info.saveIt();
                    salesid=info.getLongId().toString();
                }
                
            }
         
        }
         //保存关联订单编号-------end
        //add bcy  改展厅的级别是o及 不等于销售退回    
        if(!StringUtils.isNullOrEmpty(salesOrderDTO.getCustomerNo())&&salesOrderDTO.getCustomerNo().length()>0&&!StringUtils.isNullOrEmpty(salesOrderPO.getString("BUSINESS_TYPE"))){
          //修改潜在客户---开始
            PotentialCusPO cupo = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrderDTO.getCustomerNo());
            if(cupo!=null){
                if(cupo.getInteger("INTENT_LEVEL")==null||cupo.getInteger("INTENT_LEVEL")!=13101008){
                    cupo.setInteger("INTENT_LEVEL", 13101008);
                    cupo.setDate("DDCN_UPDATE_DATE", new Date());
                    cupo.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                    cupo.saveIt();
                }
            }
            //修改潜在客户---结束
            // 2010-1-22 begin
            //1，先删除跟进客户跟进结果是空的和值是零的
            //3，用这个方法来创建creatTrackingTask  跟进客户  TtSalesPromotionPlanPO
            List<Object> promotionList = new ArrayList<Object>();
            promotionList.add(salesOrderDTO.getCustomerNo());
            promotionList.add(loginInfo.getDealerCode());
            promotionList.add(Long.parseLong(DictCodeConstants.D_KEY));
            List<TtSalesPromotionPlanPO> promotionPo = TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? AND (PROM_RESULT is null or PROM_RESULT=0 )", promotionList.toArray());
            if(promotionPo!=null&&promotionPo.size()>0){
                for(int i = 0;i<promotionPo.size();i++){
                    TtSalesPromotionPlanPO salesPlanPO = promotionPo.get(i);
                    if(salesPlanPO!=null){
                        salesPlanPO.delete();
                        System.out.println("——————3将跟进结果为空的记录删除");
                    }
                } 
            } 
           this.creatTrackingTask(loginInfo.getDealerCode(), salesOrderDTO.getCustomerNo(), salesOrderDTO.getCustomerName(), null, loginInfo.getUserId(), salesOrderDTO.getSoldBy());  
        }
        if(!StringUtils.isNullOrEmpty(salesOrderDTO.getVin())&&salesOrderDTO.getVin().length()>0){
            //查看用户的仓库权限
            String str = "";
            StringBuilder sqlSb = new StringBuilder("select * from tm_user_CTRL where DEALER_CODE= ? AND USER_ID= ? AND CTRL_CODE LIKE '4010%' ");
            List<Object> params = new ArrayList<Object>();
            params.add(loginInfo.getUserId());
            params.add(loginInfo.getDealerCode());
            List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
            if(list!=null&&list.size()>0){
                for(int i=0;i<list.size();i++){
                    if(i>0){
                        str =str+",";
                    }
                    str = str+"'"+list.get(i).get("CTRL_CODE").toString().substring(4, list.get(i).get("CTRL_CODE").toString().length())+"'";
                }
            }
            List<Map> result = this.queryMatchVehicleByCode(salesOrderDTO.getProductCode(), loginInfo.getDealerCode(), salesOrderDTO.getVin(), "13001001", "", "", "", "", str, salesOrderDTO.getDeliveryMode().toString(), "", 2);  
            if(StringUtils.isNullOrEmpty(result)){
                throw new ServiceBizException("检查输入的VIN是否在车辆表里存在！");
            }
            VsStockPO vpo = VsStockPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrderDTO.getVin());
            if(!StringUtils.isNullOrEmpty(salesOrderDTO.getDeliveryMode())&&salesOrderDTO.getDeliveryMode()==13021001){
                if(vpo!=null){
                    
                    vpo.setString("SO_NO", soNo);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    System.out.println("日期"+format.format(new Date()));
                   // vpo.setString("DISPATCHED_DATE", format.format(new Date()));
                    vpo.setInteger("DISPATCHED_STATUS", DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DISPATCHED);//已配车
                    vpo.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                    vpo.saveIt();
                }
            }
            if(!StringUtils.isNullOrEmpty(salesOrderDTO.getVin())&&!StringUtils.isNullOrEmpty(uodateVin)&&!salesOrderDTO.getVin().equals(uodateVin)){
                VsStockPO updatevpo = VsStockPO.findByCompositeKeys(loginInfo.getDealerCode(),uodateVin);
                if(updatevpo!=null){
                    updatevpo.setString("SO_NO", null);
                    //updatevpo.setDate("DISPATCHED_DATE", null);
                    updatevpo.setInteger("DISPATCHED_STATUS", DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED);//未配车
                    updatevpo.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                    updatevpo.saveIt();
                }
            }
        }
        Map m = getSalesOrderById(salesOrderPO.getString("SO_NO"));
      String flag1="";
        int flag2=1;
      
            logger.info("=================SEDMS058Impl.getSEDMS058("+salesOrderPO.getString("SO_NO")+", "+salesOrderDTO.getVin()+");");
            flag1 =SEDMS058Impl.getSEDMS058(salesOrderPO.getString("SO_NO"), salesOrderDTO.getVin());
           
         
      
        if("0".equals(flag1)){
            throw new ServiceBizException("订车信息上报失败"); 
        }
        logger.info("================= SOTDCS015NO.getSOTDCS015NO("+salesOrderPO.getString("CUSTOMER_NO")+", "+salesOrderPO.getString("SHEET_CREATE_DATE")+");");
       flag2 =SOTDCS015NO.getSOTDCS015NO(salesOrderDTO.getCustomerNo(), salesOrderPO.getString("SHEET_CREATE_DATE"));
        if(flag2==0){
            throw new ServiceBizException("更新客户信息失败"); 
        }
         System.out.println("结束");
        return m;
    
    }

    
    /**
    * @author LiGaoqi
    *  业务描述：订单提交审核
    *1.将订单状态改为提交
    *2.如果客户级别不是N级，需要将客户级别改为O级
    *3.增加客户跟进记录
    * @date 2017年2月20日
    * @param salesOrderDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#saveCommitAudit(com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO)
    */
    	
    @Override
    public Map saveCommitAudit(SalesOrderDTO salesOrderDTO) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        String soStatus ="";
        if(salesOrderDTO.getFunctionCodeList().equals("70302000")){
            soStatus=DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING;
        }else{
            soStatus=DictCodeConstants.DICT_SO_STATUS_MANAGER_AUDITING;
        }
        SalesOrderPO po = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrderDTO.getAuditSoNo());
        if(!StringUtils.isNullOrEmpty(po)){
            po.setString("AUDITED_BY", salesOrderDTO.getAuditByList());
            po.setInteger("SO_STATUS", Integer.parseInt(soStatus));
            po.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
            po.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
            if(!StringUtils.isNullOrEmpty(po.getInteger("IS_FIRSET_SUMMIT"))&&po.getInteger("IS_FIRSET_SUMMIT")==Integer.parseInt(DictCodeConstants.DICT_IS_NO)){
                po.setInteger("IS_FIRSET_SUMMIT", Integer.parseInt(DictCodeConstants.DICT_IS_YES));       
            }
          //记录最近一次在订单中，提交审核的时间
            po.setDate("LAST_SUBMIT_AUDIT_DATE", new Date());
            VsProductPO productPO = VsProductPO.findByCompositeKeys(loginInfo.getDealerCode(),po.getString("PRODUCT_CODE"));
            if(!StringUtils.isNullOrEmpty(productPO)){
              //销售顾问价
                if(po.getDouble("SALES_CONSULTANT_PRICE")==null||po.getDouble("SALES_CONSULTANT_PRICE").equals("")||po.getDouble("SALES_CONSULTANT_PRICE")==0){
                    po.setDouble("SALES_CONSULTANT_PRICE",productPO.getDouble("SALES_CONSULTANT_PRICE"));
                }
              //总经理价
                if(po.getDouble("GENERAL_MANAGER_PRICE")==null||po.getDouble("GENERAL_MANAGER_PRICE").equals("")||po.getDouble("GENERAL_MANAGER_PRICE")==0){
                    po.setDouble("GENERAL_MANAGER_PRICE",productPO.getDouble("GENERAL_MANAGER_PRICE"));
                }
              //经理价
                if(po.getDouble("MANAGER_PRICE")==null||po.getDouble("MANAGER_PRICE").equals("")||po.getDouble("MANAGER_PRICE")==0){
                    po.setDouble("MANAGER_PRICE",productPO.getDouble("MANAGER_PRICE"));
                }
              //最低限价
                if(po.getDouble("DISCOUNT_RATE")==null||po.getDouble("DISCOUNT_RATE").equals("")||po.getDouble("DISCOUNT_RATE")==0){
                    po.setDouble("DISCOUNT_RATE",productPO.getDouble("MININUM_PRICE"));
                }
            }
            po.saveIt();
            if(!StringUtils.isNullOrEmpty(po.getInteger("BUSINESS_TYPE"))&&po.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL)){
                List<Object> orderStatusList = new ArrayList<Object>();
                orderStatusList.add(salesOrderDTO.getAuditSoNo());
                orderStatusList.add(loginInfo.getDealerCode());
                orderStatusList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                List<TtOrderStatusUpdatePO> orderStatuspo = TtOrderStatusUpdatePO.findBySQL("select * from TT_ORDER_STATUS_UPDATE where SO_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", orderStatusList.toArray());
                if((orderStatuspo!=null&&orderStatuspo.size()>0)||soStatus.equals(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING)){
                  //新增订单审核历史  
                  this.setOrderStatusPO(salesOrderDTO.getAuditSoNo(), soStatus);
                    
                }
                PotentialCusPO cpo = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrderDTO.getAuditCustomerNo());
                if(cpo!=null){
                    if(!StringUtils.isNullOrEmpty(cpo.getInteger("INTENT_LEVEL"))&&cpo.getInteger("INTENT_LEVEL")!=Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_O)){
                        cpo.setInteger("INTENT_LEVEL", Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_O));
                        cpo.setDate("DDCN_UPDATE_DATE", new Date());
                        cpo.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                        cpo.saveIt();
                        saveProPlanOrCr(loginInfo.getDealerCode(), salesOrderDTO.getAuditCustomerNo(), cpo.getInteger("INTENT_LEVEL"));
                        
                    }
                }  
            }
            
            SoAuditingPO aPo = new SoAuditingPO();
            aPo.setString("SO_NO", salesOrderDTO.getAuditSoNo());
            if(soStatus.equals(DictCodeConstants.DICT_SO_STATUS_MANAGER_AUDITING)){
                aPo.setInteger("AUDITED_BY_IDENTITY",Integer.parseInt(DictCodeConstants.DICT_AUDITING_TYPE_MANAGE));
            }
            if(soStatus.equals(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING)){
                aPo.setInteger("AUDITED_BY_IDENTITY",Integer.parseInt(DictCodeConstants.DICT_AUDITING_TYPE_FINANCE));
            }
            aPo.setLong("AUDITED_BY", salesOrderDTO.getAuditByList());
            //aPo.setDate("AUDITING_DATE", new Date());
            aPo.setLong("SUBMIT_AUDITED_BY", loginInfo.getUserId().toString());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String d = format.format(new Date());
            System.out.println("1222222222222222222222222222222");
            System.out.println(new Date());
            System.out.println(d);
            aPo.setString("COMMIT_TIME", d);
         
            if(salesOrderDTO.getTreatmentAdvice()!=null){
                aPo.setString("TREATMENT_ADVICE", salesOrderDTO.getTreatmentAdvice());
            }
            aPo.saveIt();
           // 
            
        }
        SalesOrderPO salesOrder= SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrderDTO.getAuditSoNo());
        if(!StringUtils.isNullOrEmpty(salesOrder)){
            if(!StringUtils.isNullOrEmpty(salesOrder.getString("INTENT_SO_NO"))&&(
                    !commonNoService.getDefalutPara("8036").equals("12781001")&&salesOrder.getInteger("SO_STATUS")>Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_NOT_AUDIT)||
                    commonNoService.getDefalutPara("8036").equals("12781001")&&salesOrder.getInteger("SO_STATUS")>Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING))) {
                List<Object> intentReceiveMoneyList = new ArrayList<Object>();
                intentReceiveMoneyList.add(loginInfo.getDealerCode());
                intentReceiveMoneyList.add(salesOrder.getString("INTENT_SO_NO"));
                List<TtIntentReceiveMoneyPO> receivePo = TtIntentReceiveMoneyPO.findBySQL("select * from TT_INTENT_RECEIVE_MONEY where DEALER_CODE= ? AND INTENT_SO_NO= ? ",intentReceiveMoneyList.toArray());
               if(receivePo!=null&&receivePo.size()>0){
                   TtCustomerGatheringPO gatheringPO = new TtCustomerGatheringPO();
                   for(int j=0;j<receivePo.size();j++){
                       TtIntentReceiveMoneyPO receive = receivePo.get(j);
                       if(!StringUtils.isNullOrEmpty(receive.getInteger("IS_ADVANCE_PAYMENTS"))&&receive.getInteger("IS_ADVANCE_PAYMENTS")!=12781001){
                           String No = commonNoService.getSystemOrderNo(CommonConstants.SRV_SKDH);
                           gatheringPO.setString("BILL_NO",receive.getString("BILL_NO"));
                           gatheringPO.setString("CUSTOMER_NO",salesOrder.getString("CUSTOMER_NO"));
                           gatheringPO.setString("SO_NO",receive.getString("SO_NO"));
                           gatheringPO.setInteger("GATHERING_TYPE",receive.getInteger("GATHERING_TYPE"));
                           gatheringPO.setString("OWNED_BY",String.valueOf(loginInfo.getUserId()));
                           gatheringPO.setString("PAY_TYPE_CODE",receive.getString("0004"));
                           gatheringPO.setDouble("RECEIVE_AMOUNT",receive.getDouble("RECEIVE_AMOUNT"));
                           gatheringPO.setDate("RECEIVE_DATE",receive.getDate("RECEIVE_DATE"));
                           gatheringPO.setString("RECEIVE_NO",No);
                           gatheringPO.setDate("RECORD_DATE",new Date());
                           gatheringPO.setString("RECORDER",receive.getString("RECORDER"));
                           gatheringPO.setString("TRANSACTOR",receive.getString("TRANSACTOR"));
                           gatheringPO.setString("WRITEOFF_BY",receive.getString("WRITEOFF_BY"));
                           gatheringPO.setDate("WRITEOFF_DATE",new Date());
                           gatheringPO.setInteger("WRITEOFF_TAG",DictCodeConstants.STATUS_IS_YES);
                           gatheringPO.setString("REMARK",receive.getString("REMARK"));
                           gatheringPO.saveIt();
                           //更新客户资料表中收款总金额和帐户可用余额
                           if(!StringUtils.isNullOrEmpty(salesOrder.getString("CUSTOMER_NO"))){
                               PotentialCusPO cusPo = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrder.getString("CUSTOMER_NO"));
                               if(cusPo!=null){
                                   Double receiveAmount = receive.getDouble("RECEIVE_AMOUNT");
                                   Double gatheredSum = 0D;
                                   Double usableAmount = 0D;
                                   if(cusPo.getDouble("GATHERED_SUM")!=null){
                                       gatheredSum = cusPo.getDouble("GATHERED_SUM")+receiveAmount;
                                   }else{
                                       gatheredSum = receiveAmount;
                                   }
                                   cusPo.setDouble("GATHERED_SUM", gatheredSum);
                                   if(cusPo.getDouble("USABLE_AMOUNT")!=null){
                                       usableAmount = cusPo.getDouble("USABLE_AMOUNT")+receiveAmount;
                                   }else{
                                       usableAmount = receiveAmount;
                                   }
                                   cusPo.setDouble("USABLE_AMOUNT", usableAmount);
                                   cusPo.saveIt();
                               }
                           }
                           receive.setInteger("IS_ADVANCE_PAYMENTS", 12781001);
                           receive.saveIt();
                       }   

                   }
                   List<Object> intentReceiveList = new ArrayList<Object>();
                   intentReceiveList.add(loginInfo.getDealerCode());
                   intentReceiveList.add(salesOrder.getString("INTENT_SO_NO"));
                   List<TtIntentReceiveMoneyPO> intentReceivePo = TtIntentReceiveMoneyPO.findBySQL("select * from TT_INTENT_RECEIVE_MONEY where DEALER_CODE= ? AND INTENT_SO_NO= ? AND (IS_ADVANCE_PAYMENTS != 12781001 OR IS_ADVANCE_PAYMENTS IS NULL) ",intentReceiveList.toArray());
                   if(!StringUtils.isNullOrEmpty(intentReceivePo)&&intentReceivePo.size()>0){
                       TtIntentSalesOrderPO intentOrderPO = TtIntentSalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrder.getString("INTENT_SO_NO"));
                       if(intentOrderPO!=null){
                           intentOrderPO.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_INTENT_SO_STATUS_HAVE_CLOSED));
                           intentOrderPO.saveIt();
                       }
                   }
               }
                
            }
         }
        int flag1=1;
        int flag2=1;
       // int flag2=1;
            try {
                logger.info("=================SEDMS060.getSEDMS060("+salesOrderDTO.getAuditSoNo()+",12781001,1);");
                flag1 =SEDMS060.getSEDMS060(salesOrderDTO.getAuditSoNo(), "12781002", "1");
                
                flag2=DMSTODCS004.getDMSTODCS004(salesOrderDTO.getAuditSoNo(), po.getString("DELIVERY_MODE_ELEC"));
            } catch (Exception e) {
                throw new ServiceBizException(e.getMessage()); 
            }
            if("0".equals(flag1)){
                throw new ServiceBizException("电商订单状态上报失败！"); 
            }
            if(flag2==0){
                throw new ServiceBizException("CALL车上报失败"); 
            }
        return getSalesOrderById(salesOrderDTO.getAuditSoNo()); 
    }

    
    /**
    * @author LiGaoqi
    * @date 2017年2月20日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#commidAudit(java.util.Map)
    */
    	
    @Override
    public List<Map> commidAudit(Map<String, String> queryParam) throws ServiceBizException {

        String pp1 = commonNoService.getDefalutPara("8001");
        String pp2 = commonNoService.getDefalutPara("8032");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sql = new StringBuffer("");/*70105000=202004*/
        sql.append(" SELECT A.USER_ID,A.USER_CODE,A.USER_NAME,A.EMPLOYEE_NO,A.ORG_CODE,B.EMPLOYEE_NAME, "
                   + " CASE WHEN MENU_ID='70105000' THEN '经理审核' ELSE '财务审核' END AS FUNCTION_NAME"
                   + " ,CASE WHEN MENU_ID = '70101300' THEN '70302000' ELSE MENU_ID END AS FUNCTION_CODE  "
                  /* + " ,CASE WHEN MENU_ID = '70101300' THEN '70302000' ELSE MENU_ID END AS FUNCTION_CODE  "*/
                   + " FROM TM_USER A ,TM_EMPLOYEE B,TM_USER_MENU C " + " WHERE A.DEALER_CODE = B.DEALER_CODE   " + // --A.ORG_CODE                                                                                                  // +
                   " AND A.DEALER_CODE = C.DEALER_CODE AND A.EMPLOYEE_NO = B.EMPLOYEE_NO " +
                   " AND C.USER_ID = A.USER_ID  " + // AND A.D_KEY =" + CommonConstant.D_KEY +
                   " AND A.DEALER_CODE = '" + loginInfo.getDealerCode() + "'" + " AND A.USER_STATUS = "
                   + DictCodeConstants.DICT_IN_USE_START + " AND A.USER_ID != " + loginInfo.getUserId());
        if (pp1.equals("12781001")&&pp2.equals("12781001")) {
              sql.append("  AND (MENU_ID = '70105000' OR MENU_ID = '70101300' )");
        }else if(pp1.equals("12781001")&&pp2.equals("12781002")){
              sql.append("  AND MENU_ID = '70105000' ");
        }else if(pp1.equals("12781002")&&pp2.equals("12781001")){
              sql.append("  AND MENU_ID = '70101300' ");
        }else{
            sql.append("  AND MENU_ID = '70105000' ");
        }
        System.out.println(sql);
        List<Object> queryParams = new ArrayList<Object>();
        return Base.findAll(sql.toString(), queryParams.toArray());
    }
    
    private TtOrderStatusUpdatePO setOrderStatusPO(String SO_NO,String SO_STATUS) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        SalesOrderPO apoquery = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),SO_NO);
        TtOrderStatusUpdatePO apo = new TtOrderStatusUpdatePO();
        if(!StringUtils.isNullOrEmpty(apoquery)){
            if(!StringUtils.isNullOrEmpty(apoquery.getInteger("BUSINESS_TYPE"))&&apoquery.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL)){
                apo.setString("SO_NO", SO_NO);
                apo.setInteger("SO_STATUS", Integer.parseInt(SO_STATUS));
                apo.setString("OWNED_BY", loginInfo.getUserId().toString());
                apo.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                apo.setDate("ALTERATION_TIME", new Date());
                apo.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                apo.saveIt();
            }
        }
        return apo;
    }
    
    private void saveProPlanOrCr(String entityCode, String cusNo, int intentLevel) throws ServiceBizException{
        System.out.println("——————2将跟进结果为空的记录删除");
        String groupCode = Utility.getGroupEntity(entityCode, "TM_TRACKING_TASK");
        List<Object> promotionList = new ArrayList<Object>();
        promotionList.add(cusNo);
        promotionList.add(entityCode);
        promotionList.add(Long.parseLong(DictCodeConstants.D_KEY));
        List<TtSalesPromotionPlanPO> promotionPo = TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? AND (PROM_RESULT is null or PROM_RESULT=0 )", promotionList.toArray());
        if(promotionPo!=null&&promotionPo.size()>0){
            for(int i = 0;i<promotionPo.size();i++){
                TtSalesPromotionPlanPO salesPlanPO = promotionPo.get(i);
                if(salesPlanPO!=null){
                    salesPlanPO.delete();
                    System.out.println("——————3将跟进结果为空的记录删除");
                }
            }
            
        }
        System.out.println("——————4");
        List<Object> taskList1 = new ArrayList<Object>();
        taskList1.add(intentLevel);
        taskList1.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
        taskList1.add(DictCodeConstants.IS_YES);
        taskList1.add(groupCode);
        List<TrackingTaskPO> taskPO=TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ? AND CUSTOMER_STATUS= ? AND IS_VALID= ? AND DEALER_CODE= ? ", taskList1.toArray());
        PotentialCusPO cpo = PotentialCusPO.findByCompositeKeys(entityCode,cusNo);
        for(int j = 0;j<taskPO.size();j++){
            System.out.println("——————5");
            TrackingTaskPO task = taskPO.get(j);
            TtSalesPromotionPlanPO sPlanPo = new TtSalesPromotionPlanPO();
            sPlanPo.setLong("TASK_ID", task.getLong("TASK_ID"));
            sPlanPo.setString("CUSTOMER_NAME", cpo.getString("CUSTOMER_NAME"));
            sPlanPo.setString("CUSTOMER_NO", cusNo);
            sPlanPo.setLong("INTENT_ID", cpo.getLong("INTENT_ID"));
            sPlanPo.setInteger("PRIOR_GRADE",task.getInteger("INTENT_LEVEL"));
            sPlanPo.setInteger("NEXT_GRADE",null);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dates=new String();
            sPlanPo.setDate("ACTION_DATE",null);
            if(task.getInteger("INTERVAL_DAYS")!=null){
                c.setTime(new Date());
                c.add(7, task.getInteger("INTERVAL_DAYS"));
                dates = format.format(c.getTime()).toString();
            }

            try {
                sPlanPo.setDate("SCHEDULE_DATE", format.parse(dates));
            } catch (ParseException e) {
                
                e.printStackTrace();
            }
            sPlanPo.setString("PROM_CONTENT",task.getString("TASK_CONTENT"));
            sPlanPo.setInteger("PROM_WAY",task.getInteger("EXECUTE_TYPE"));
            sPlanPo.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));        
            sPlanPo.setInteger("PROM_RESULT",null);
            sPlanPo.setString("SCENE",null);
            List<Object> linkList = new ArrayList<Object>();
            linkList.add(cusNo);
            linkList.add(Integer.parseInt(DictCodeConstants.D_KEY));
            linkList.add(entityCode);
            List<TtPoCusLinkmanPO> linkPO=TtPoCusLinkmanPO.findBySQL("select * from TT_PO_CUS_LINKMAN where CUSTOMER_NO= ? AND  D_KEY= ? AND DEALER_CODE= ? ", linkList.toArray());
            if(linkPO!=null&&linkPO.size()>0){
                TtPoCusLinkmanPO PO = linkPO.get(0);
                if(!StringUtils.isNullOrEmpty(PO.getString("CONTACTOR_NAME"))){
                    sPlanPo.setString("CONTACTOR_NAME", PO.getString("CONTACTOR_NAME"));
                }
                if(!StringUtils.isNullOrEmpty(PO.getString("PHONE"))){
                    sPlanPo.setString("PHONE", PO.getString("PHONE"));
                }
                if(!StringUtils.isNullOrEmpty(PO.getString("Mobile"))){
                    sPlanPo.setString("MOBILE", PO.getString("MOBILE"));
                }
            }
            sPlanPo.setInteger("IS_AUDITING",12781002);
            sPlanPo.setString("SOLD_BY",cpo.getString("SOLD_BY"));
            sPlanPo.setString("OWNED_BY",cpo.getString("SOLD_BY"));
            sPlanPo.saveIt(); 
        }
    
        //
    }

    
    /**
    * @author LiGaoqi
    * @date 2017年2月20日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#checktmPoTentialCustomer(java.lang.String)
    */
    	
    @Override
    public List<Map> checktmPoTentialCustomer(String id) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("select * from TM_POTENTIAL_CUSTOMER where CUSTOMER_NO='"+id+"' AND DEALER_CODE='"+loginInfo.getDealerCode()+"' AND BUY_REASON IS  NUll ");
        System.out.println(id);
        System.out.println(1222);
        System.out.println(sql);

        return DAOUtil.findAll(sql.toString(), params);

    }

    
    /**
    * @author LiGaoqi
    * @date 2017年2月20日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#checksaveEcOrder(java.lang.String)
    */
    	
    @Override
    public String checksaveEcOrder(String id) throws ServiceBizException {
        int flag = SEDMS061.getSEDMS061(id);
        if(flag==0){
            throw new ServiceBizException("获取定金码是否逾期以及是否核销状态失败！"); 
        }
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        String[] ids = id.split(",");
        String MSg = "0";
        String customerNo = ids[0];
        String brandCode = ids[1];
        String seriesCode = ids[2];
        String cusSource = "";
        String ecOrderNo ="";
        String intentBrand ="";
        String intentSeries= "";
        String escOrderStatus ="";
        String escType ="";
        PotentialCusPO tpcpo = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),customerNo);
        if(tpcpo!=null){
            cusSource = tpcpo.getInteger("CUS_SOURCE").toString();
            ecOrderNo = tpcpo.getString("EC_ORDER_NO");
        }
        if(!StringUtils.isNullOrEmpty(ecOrderNo)){
            List<Object> linkList = new ArrayList<Object>();
            linkList.add(ecOrderNo);
            linkList.add(Integer.parseInt(DictCodeConstants.D_KEY));
            linkList.add(loginInfo.getDealerCode());
            List<TtEsCustomerOrderPO> tecpo=TtEsCustomerOrderPO.findBySQL("select * from tt_es_customer_order where EC_ORDER_NO= ? AND  D_KEY= ? AND DEALER_CODE= ? ", linkList.toArray());
            if(tecpo!=null&&tecpo.size()>0){
                TtEsCustomerOrderPO po = tecpo.get(0);
                intentBrand = po.getString("INTENT_BRAND");
                intentSeries = po.getString("INTENT_SERIES");
                escOrderStatus =po.getInteger("ESC_ORDER_STATUS").toString();
                escType  =po.getInteger("ESC_TYPE").toString();
            }
        }
        if(!StringUtils.isNullOrEmpty(ecOrderNo)){
            if(!StringUtils.isNullOrEmpty(cusSource)&&cusSource.equals(DictCodeConstants.DICT_CUS_SOURCE_BY_WEB)){
                if(!StringUtils.isNullOrEmpty(escType)&&escType.equals(DictCodeConstants.DICT_DMS_ESC_TYPE_STOCK)){
                    if(!StringUtils.isNullOrEmpty(escOrderStatus)&&escOrderStatus.equals(DictCodeConstants.DICT_DMS_ESC_STATUS_VALID)){
                        if(!StringUtils.isNullOrEmpty(brandCode)&&brandCode.equals(intentBrand)&&!StringUtils.isNullOrEmpty(seriesCode)&&seriesCode.equals(intentSeries)){
                            MSg="12781001";
                        }else{
                            MSg="5";//订单车系与官网订单意向车系不匹配，请重新选择订单车系！
                        }
                    }else{
                       MSg ="4";//无效状态不能做官网订单，请重新选择客户！ 
                    }
                }else{
                    MSg="3";//不是现货不能做官网订单，请重新选择客户！
                }
            }else{
                MSg="2";//不是官网用户不能做官网订单，请重新选择客户！
            }
        }else{
            MSg="1";//非官网用户不能选择官网交车方式，请重新选择！
        }
        return MSg;
    }  
    
    //开票登记
    @Override
    public PageInfoDto qrySRSForInvoiceSet(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();  
        StringBuffer sql = new StringBuffer("");
     
            sql.append(" SELECT A.DEALER_CODE,A.CUSTOMER_NAME,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.IS_SPEEDINESS,A.DELIVERY_MODE_ELEC,A.IS_ELECTRICITY,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.EC_ORDER_NO,A.CUS_SOURCE,A.DEPOSIT_AMOUNT,A.EC_ORDER,A.VERIFICATION_TIMEOUT,");
            sql.append( " br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME,em.USER_NAME,tu.USER_NAME AS LOCK_NAME,B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE ,A.VIN,A.SOLD_BY,A.VEHICLE_PRICE, ");
            sql.append( " A.SO_NO,A.SHEET_CREATE_DATE,A.SO_STATUS,A.CUSTOMER_NO,A.CONTRACT_NO,A.CONTRACT_DATE,A.OWNED_BY,A.LOCK_USER,A.CT_CODE,A.CERTIFICATE_NO,A.DELIVERING_DATE,C.IS_DIRECT,A.BUSINESS_TYPE ");
            sql.append( " FROM TT_SALES_ORDER A  ");
            sql.append( " LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.dealer_code = B.dealer_code AND A.D_KEY = B.D_KEY ");
            sql.append( " LEFT JOIN TM_VS_STOCK C ON A.dealer_code = C.dealer_code AND A.VIN = C.VIN");
            sql.append(" left  join   tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
            sql.append(" left  join   TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and B.DEALER_CODE=se.DEALER_CODE");
            sql.append(" left  join   TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and mo.BRAND_CODE=se.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and B.DEALER_CODE=mo.DEALER_CODE");
            sql.append(" left  join   tm_configuration pa   on   B.CONFIG_CODE=pa.CONFIG_CODE and pa.BRAND_CODE=mo.BRAND_CODE and pa.SERIES_CODE=pa.SERIES_CODE and pa.MODEL_CODE=mo.MODEL_CODE and B.DEALER_CODE=pa.DEALER_CODE");
            sql.append(" left  join   tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
            sql.append( " left join TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE"); 
            sql.append( " left join TM_USER tu  on A.LOCK_USER=tu.USER_ID AND A.DEALER_CODE=tu.DEALER_CODE"); 
            sql.append( " WHERE  A.D_KEY = ");
    		sql.append( DictCodeConstants.D_KEY);
                
         
/**
 * 审核订单和实际开票的人不一定是一个人，开票的人一班都是收银的人，也就是系统上面收款的人，也就是随便哪个财务都可以，只要有收款权限，都可以开票
狂奔的大雨 12:10:28
所以这个地方有没有必要绑定需要去了解一下  
狂奔的大雨 13:02:32
确认好了，开票这个权限放给所有的销售财务吧

          
 */         //使用参数隐藏‘已退回’销售单，即后台不查询，前台不显示
            if (!commonNoService.getDefalutPara("3322").equals(DictCodeConstants.DICT_IS_YES)){
                
                sql.append(" and A.SO_STATUS <> "+DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD);
            }   
                sql.append(" and a.so_no not in  (select OLD_SO_NO from tt_sales_order where  dealer_code=A.dealer_code and OLD_SO_NO is not null and OLD_SO_NO<>'' and BUSINESS_TYPE=13001005) ");     
            sql.append(" and A.BUSINESS_TYPE <> "+DictCodeConstants.DICT_SO_TYPE_RERURN); 
            //销售开票登记，选择为：完成或关单状态的销售订单 新增需求 CHRYSLER-245  DICT_SO_STATUS_HAVE_OUT_STOCK
            sql.append(" and (A.SO_STATUS= '"+DictCodeConstants.DICT_SO_STATUS_CLOSED+"')");
                sql.append(Utility.getStrCond("A", "dealer_code", FrameworkUtil.getLoginInfo().getDealerCode()));
                sql.append(Utility.getLikeCond("A", "VIN", queryParam.get("vin"),"AND"));
                sql.append(Utility.getLikeCond("A", "CONTRACT_NO", queryParam.get("contractNo"),"AND"));
                sql.append(Utility.getLikeCond("A", "CUSTOMER_NAME",queryParam.get("customerName"),"AND"));
                sql.append(Utility.getLikeCond("A", "SO_NO", queryParam.get("soNo"),"AND"));
        
            
        return DAOUtil.pageQuery(sql.toString(), params);
        
    }
    /**
     * 销售退回作废权限
     * 
     * @author xhy
     * @date 2017年3月22日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryMenuAction()
     */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryMenuAction() throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long userId=loginInfo.getUserId();
		StringBuffer sql=new StringBuffer("");
		sql.append("select um.* from tm_user_menu um left join tm_user_menu_action uma on um.user_menu_id=uma.user_menu_id where um.user_id="+userId+" and uma.menu_curing_id=1020351000 ");
		List<Map> list=DAOUtil.findAll(sql.toString(), null);
		
		return list;
	}
    
    
}
