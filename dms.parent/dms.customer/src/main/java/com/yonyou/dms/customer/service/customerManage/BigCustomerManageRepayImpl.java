
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : BigCustomerManageRepayImpl.java
*
* @Author : Administrator
*
* @Date : 2017年1月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月18日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.customerManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.BigCustomerRepayDTO;
import com.yonyou.dms.common.domains.DTO.basedata.BigCustomerSalesDTO;
import com.yonyou.dms.common.domains.PO.basedata.WholesaleRepayDteailPO;
import com.yonyou.dms.common.domains.PO.basedata.WholesaleRepayPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.DSO0401;
import com.yonyou.dms.gacfca.DSO0402;

/**
* TODO description
* @author Administrator
* @date 2017年1月18日
*/
@Service
public class BigCustomerManageRepayImpl implements BigCustomerManageRepayService {
    @Autowired
    private CommonNoService commonNoService;
    @Autowired
    DSO0402 DSO0402;
    @Override
    public PageInfoDto queryBigCustomer(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sql=new StringBuffer();
        
        
        //StringBuffer sq2=new StringBuffer();
        sql.append(
                "SELECT 12781002 AS IS_SELECTED,\n" +
                "       A.WS_NO,\n" + 
                "       A.WS_TYPE,\n" + 
                "       A.dealer_code,\n" + 
                "       A.WS_STATUS,\n" + 
                "       A.WS_APP_TYPE,\n" + 
                "       A.CUSTOMER_NO,\n" + 
                "       A.LARGE_CUSTOMER_NO,\n" + 
                "       A.FAX_TIME,\n" + 
                "       A.FAX,\n" + 
                "       A.COMPANY_NAME,\n" + 
                "       A.IS_FAX_RECEIVED,\n" + 
                "       A.CUSTOMER_NAME,\n" + 
                "       A.CUSTOMER_CHARACTER,\n" + 
                "       A.CONTACTOR_NAME,\n" + 
                "       A.POSITION_NAME,\n" + 
                "       (case when (A.PHONE is null)or(trim(A.PHONE)='') then B.CONTACTOR_MOBILE else A.PHONE end) PHONE ,\n" + 
                "       A.MOBILE,\n" + 
                "       A.IS_SECOND_REPORT,\n" + 
                "       A.WS_AUDITOR,\n" + 
                "       A.WS_AUDITING_REMARK,\n" + 
                "       A.AUDITING_DATE,\n" + 
                "       A.SUBMIT_TIME,\n" + 
                "       A.FIRST_SUBMIT_TIME,\n" + 
                "       A.VER,\n" + 
                "       A.OWNED_BY,\n" + 
                "       A.CONFIGURE_REMARK,\n" + 
                "       A.PROJECT_REMARK,\n" + 
                "       A.WS_CONTENT,\n" + 
                "       A.DLR_PRINCIPAL,\n" + 
                "       a.POS_REMARK,\n" + 
                "       A.DLR_PRINCIPAL_PHONE,\n" + 
                "       A.POS_NAME,\n" + 
                "       A.POS_LINKMAN,\n" + 
                "       A.POS_LINKMAN_PHONE,\n" + 
                "       A.ORGAN_TYPE_CODE,\n" + 
                "       A.CUSTOMER_KIND,\n" + 
                "       B.ZIP_CODE,\n" + 
                "       B.ADDRESS,\n" + 
                "       A.WSTHREE_TYPE\n" + 
                "\n"
                +" FROM TT_PO_CUS_WHOLESALE A inner join TM_POTENTIAL_CUSTOMER B on A.CUSTOMER_NO=B.CUSTOMER_NO and A.dealer_code=B.dealer_code WHERE A.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' " +        
                " AND A.D_KEY=0 AND A.WS_STATUS='15981003' ");
        //查询条件
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("wsNo")))
        {
            sql.append(" AND A.WS_NO LIKE ?");
            queryList.add("%" + queryParam.get("wsNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo")))
        {
            sql.append(" AND A.CUSTOMER_NO LIKE ?");
            queryList.add("%" + queryParam.get("customerNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName")))
        {
            sql.append(" AND A.CUSTOMER_NAME LIKE ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("phone")))
        {
            sql.append(" AND A.phone LIKE ?");
            queryList.add("%" + queryParam.get("phone") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("mobile")))
        {
            sql.append(" AND A.mobile LIKE ?");
            queryList.add("%" + queryParam.get("mobile") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("companyName")))
        {
            sql.append(" AND A.COMPANY_NAME LIKE ?");
            queryList.add("%" + queryParam.get("companyName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("beginSubmitTime"))) {
            sql.append(" and c.SUBMIT_TIME>= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("beginSubmitTime")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("endSubmitTime"))) {
            sql.append(" and c.SUBMIT_TIME<?");
            queryList.add(DateUtil.addOneDay(queryParam.get("endSubmitTime")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("beginAuditingDate"))) {
            sql.append(" and c.AUDITING_DATE>= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("beginAuditingDate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("endAuditingDate"))) {
            sql.append(" and c.AUDITING_DATE<?");
            queryList.add(DateUtil.addOneDay(queryParam.get("endAuditingDate")));
        }
        
        sql.append(" AND A.WS_NO NOT IN (SELECT WS_NO FROM TT_WHOLESALE_REPAY WHERE dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' )");     
        PageInfoDto id = DAOUtil.pageQuery(sql.toString(), queryList);
        System.out.println(id);
        return id;
    }
    @Override
    public PageInfoDto querySales(Map<String, String> queryParam,String wsAppType,String firstsubmittime) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();       
        StringBuffer sql=new StringBuffer();
        sql.append("select '' IS_SELECTED,A.DEALER_CODE,A.SO_NO,A.VIN,E.COMPANY_NAME,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,A.VEHICLE_PRICE,C.BRAND BRAND_CODE,C.SERIES SERIES_CODE,C.MODEL MODEL_CODE,C.APACKAGE CONFIG_CODE," +
                " B.FILE_ID FILE_INVOICE_ID,B.FILE_URL FILE_INVOICE_URL,'' FILE_CARD_ID,'' FILE_CARD_URL,'' FILE_SFZ_ID,'' FILE_SFZ_URL,'' FILE_G_ID,'' FILE_G_URL,'' FILE_H_ID,'' FILE_H_URL,'' FILE_H2_ID,'' FILE_H2_URL,'' FILE_H3_ID,'' FILE_H3_URL,'' FILE_H4_ID,'' FILE_H4_URL,'' FILE_I_ID,'' FILE_I_URL," +
                " '' FILE_SB_ID,'' FILE_SB_URL,'' FILE_SB1_ID,'' FILE_SB1_URL,B.INVOICE_DATE from tt_sales_order A" +
                " LEFT JOIN TT_SO_INVOICE B ON A.dealer_code=B.dealer_code AND A.SO_NO=B.SO_NO" +
                " LEFT JOIN TM_POTENTIAL_CUSTOMER E ON A.dealer_code=E.dealer_code AND A.CUSTOMER_NO=E.CUSTOMER_NO" +
                " LEFT JOIN TM_VEHICLE C ON A.VIN=C.VIN AND A.dealer_code=C.dealer_code" +
                " left  join   tm_brand   br   on   C.BRAND = br.BRAND_CODE and C.DEALER_CODE=br.DEALER_CODE" +
                " left  join   TM_SERIES  se   on   C.SERIES=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and C.DEALER_CODE=se.DEALER_CODE" +
                " left  join   TM_MODEL   mo   on   C.MODEL=mo.MODEL_CODE and se.BRAND_CODE=mo.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and C.DEALER_CODE=mo.DEALER_CODE" +
                " left  join   tm_configuration pa   on   C.APACKAGE=pa.CONFIG_CODE and mo.MODEL_CODE=pa.MODEL_CODE and mo.SERIES_CODE=pa.SERIES_CODE and mo.BRAND_CODE=pa.BRAND_CODE and C.DEALER_CODE=pa.DEALER_CODE" +
                " LEFT JOIN tm_big_customer_DEFINITION tb ON tb.BRAND_CODE=C.BRAND AND tb.SERIES_CODE=C.SERIES AND tb.dealer_code=C.dealer_code" +
                
                "  WHERE A.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND A.D_KEY=0 AND tb.IS_VALID=10011001 AND tb.IS_DELETE=0 and tb.PS_TYPE='"+wsAppType+"'");
        //必须开票登记
        String defautParam = Utility.getDefaultValue("1412");
        String strData = "2015";
        String subData[] = firstsubmittime.split("-");  
        System.out.println(firstsubmittime);//defautParam.equals(DictCodeConstants.STATUS_IS_YES)
       if(subData[0].compareTo(strData)>0 && defautParam != null && StringUtils.isEquals(DictCodeConstants.STATUS_IS_YES, defautParam)){ 
            sql.append(" AND DATEDIFF(B.INVOICE_DATE,'"+firstsubmittime+"') >=0");
        }
        sql.append(" AND A.SO_NO IN (SELECT SO_NO FROM TT_SO_INVOICE WHERE dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' and INVOICE_CHARGE_TYPE="+DictCodeConstants.DICT_INVOICE_FEE_VEHICLE+") ");
        //过滤已保存的返利申请中的车辆
        sql.append(" AND NOT EXISTS (SELECT 1 FROM TT_WHOLESALE_REPAY_DTEAIL D WHERE A.dealer_code=D.dealer_code AND A.SO_NO=D.SO_NO) ");
        //查询条件
        if (!StringUtils.isNullOrEmpty(queryParam.get("soNo")))
        {
            sql.append(" AND A.SO_NO LIKE ?");
            queryList.add("%" + queryParam.get("soNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo")))
        {
            sql.append(" AND A.CUSTOMER_NO LIKE ?");
            queryList.add("%" + queryParam.get("customerNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName")))
        {
            sql.append(" AND A.CUSTOMER_NAME LIKE ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("phone")))
        {
            sql.append(" AND A.PHONE LIKE ?");
            queryList.add("%" + queryParam.get("phone") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("mobile")))
        {
            sql.append(" AND A.mobile LIKE ?");
            queryList.add("%" + queryParam.get("mobile") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("company")))
        {
            sql.append(" AND E.COMPANY_NAME LIKE ?");
            queryList.add("%" + queryParam.get("company") + "%");
        }    
        PageInfoDto id = DAOUtil.pageQuery(sql.toString(), queryList);
        System.out.println(id);
        return id;
    }
    @Override
    public Map<String, Object> employeSaveBeforeEvent(Map<String, String> queryParam,String brandCode,String seriesCode,String wsType) throws ServiceBizException {
      //同一政策类别、同一公司的报备单，您确定继续保存
        Map<String, Object> result = new HashMap<String, Object>();
        List<Object> cus1 = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        sql.append(" SELECT  BRAND_CODE,SERIES_CODE,dealer_code FROM tm_big_customer_DEFINITION where BRAND_CODE='"+brandCode+"' AND SERIES_CODE='"+seriesCode+"'" +
                " AND IS_VALID=10011001 AND IS_DELETE=0 and dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' and PS_TYPE='"+wsType+"'"
                    );
      
        List<Map> sql1 = DAOUtil.findAll(sql.toString(), cus1);
        if(sql1.size()==0){
            throw new ServiceBizException("该品牌车系车辆无法进行申请"); 
        }
        return result;
    }
    @Override
    public Map<String, Object> employeSaveBeforeEventAmount(Map<String, String> queryParam) throws ServiceBizException {
      //同一政策类别、同一公司的报备单，您确定继续保存
        Map<String, Object> result = new HashMap<String, Object>();
        List<Object> cus1 = new ArrayList<Object>();
//        StringBuffer sql = new StringBuffer("");
//        sql.append(" SELECT  NUMBER,dealer_code FROM tm_big_customer_AMOUNT where " +                                                                 
//                " IS_VALID=10011001 AND IS_DELETE=0 and dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' and ps_type='"+queryParam.get("wsAppType")+"' and employee_Type='"+queryParam.get("customerKind")+"'" 
//                    );
//      
//        List<Map> sql1 = DAOUtil.findAll(sql.toString(), cus1);
//        if(sql1.size()==0){
//            throw new ServiceBizException("客户类型对应的最低申请数量为0，不允许创建报备单"); 
//        }
//        if(sql1.size()>0 ){
//      int sum=  Integer.parseInt((sql1.get(0).get("NUMBER")).toString());
//      int st=  Integer.parseInt(queryParam.get("amount"));
//            if(st-sum>=0){
//                throw new ServiceBizException("大客户申请时，需达到最低申请数量，才能提交");  
//            }
//           
//        }
        return result;
    }
    @Override
    public String modifyBiGCusInfo(BigCustomerRepayDTO bigCustomerDto,String wsNo) throws ServiceBizException {
    	WholesaleRepayPO poCuspo = WholesaleRepayPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),wsNo);       
       Long intentId = commonNoService.getId("ID");    
       poCuspo.setString("WS_NO", bigCustomerDto.getWsNo());                          
       poCuspo.setInteger("Ws_Employee_Type", bigCustomerDto.getCustomerKind());
       poCuspo.setString("customer_Name", bigCustomerDto.getCustomerName());
       poCuspo.setInteger("Wsthree_Type", bigCustomerDto.getWsthreeType());
       poCuspo.setInteger("Ws_App_Type", bigCustomerDto.getWsAppType());
       poCuspo.setString("Customer_No", bigCustomerDto.getCustomerNo());
       poCuspo.setString("amount", bigCustomerDto.getAmount());
       poCuspo.setString("Company_Name", bigCustomerDto.getCompanyName());
       poCuspo.setString("Contactor_Name", bigCustomerDto.getContactorName());
       poCuspo.setString("Position_Name", bigCustomerDto.getPositionName());
       poCuspo.setString("Phone", bigCustomerDto.getPhone());
       poCuspo.setString("Mobile", bigCustomerDto.getMobile());
       poCuspo.setInteger("SO_STATUS", 15971001);
    
        poCuspo.saveIt();
        WholesaleRepayDteailPO.delete(" ws_no= ? AND DEALER_CODE= ?", wsNo,FrameworkUtil.getLoginInfo().getDealerCode());
        if(bigCustomerDto.getInvent1().size()>0 && bigCustomerDto.getInvent1() !=null){
            for(BigCustomerSalesDTO bigCustomerCardto : bigCustomerDto.getInvent1()){
                this.setConfig(bigCustomerCardto,bigCustomerDto.getWsNo(),intentId).saveIt(); 
            } 
        }
        return null;
    }
    @Override
    public String addBiGCusRepayInfo(BigCustomerRepayDTO bigCustomerDto) throws ServiceBizException {
        
        
       Long intentId = commonNoService.getId("ID");
       WholesaleRepayPO poCuspo = new WholesaleRepayPO();
       poCuspo.setString("WS_NO", bigCustomerDto.getWsNo());
                           
       poCuspo.setInteger("Ws_Employee_Type", bigCustomerDto.getCustomerKind());
       poCuspo.setString("customer_Name", bigCustomerDto.getCustomerName());
       poCuspo.setInteger("Wsthree_Type", bigCustomerDto.getWsthreeType());
       poCuspo.setInteger("Ws_App_Type", bigCustomerDto.getWsAppType());
       poCuspo.setString("Customer_No", bigCustomerDto.getCustomerNo());
       poCuspo.setString("amount", bigCustomerDto.getAmount());
       poCuspo.setString("Company_Name", bigCustomerDto.getCompanyName());
       poCuspo.setString("Contactor_Name", bigCustomerDto.getContactorName());
       poCuspo.setString("Position_Name", bigCustomerDto.getPositionName());
       poCuspo.setString("Phone", bigCustomerDto.getPhone());
       poCuspo.setString("Mobile", bigCustomerDto.getMobile());
       if(StringUtils.isNullOrEmpty(bigCustomerDto.getFileUrlA())){
           poCuspo.setString("FILE_URL_A", bigCustomerDto.getFileUrlA());
       }else{
           poCuspo.setString("FILE_URL_A", bigCustomerDto.getFileUrlA1());
       }
       if(StringUtils.isNullOrEmpty(bigCustomerDto.getFileUrlB())){
           poCuspo.setString("FILE_URL_B", bigCustomerDto.getFileUrlB());
       }else{
           poCuspo.setString("FILE_URL_B", bigCustomerDto.getFileUrlB1());
       }
       if(StringUtils.isNullOrEmpty(bigCustomerDto.getFileUrlC())){
           poCuspo.setString("FILE_URL_C", bigCustomerDto.getFileUrlC());
       }else{
           poCuspo.setString("FILE_URL_C", bigCustomerDto.getFileUrlC1());
       }
       if(StringUtils.isNullOrEmpty(bigCustomerDto.getFileUrlD())){
           poCuspo.setString("FILE_URL_D", bigCustomerDto.getFileUrlD());
       }else{
           poCuspo.setString("FILE_URL_D", bigCustomerDto.getFileUrlD1());
       }
       if(StringUtils.isNullOrEmpty(bigCustomerDto.getFileUrlE())){
           poCuspo.setString("FILE_URL_E", bigCustomerDto.getFileUrlE());
       }else{
           poCuspo.setString("FILE_URL_E", bigCustomerDto.getFileUrlE1());
       }
       if(StringUtils.isNullOrEmpty(bigCustomerDto.getFileUrlN())){
           poCuspo.setString("FILE_URL_N", bigCustomerDto.getFileUrlN());
       }else{
           poCuspo.setString("FILE_URL_N", bigCustomerDto.getFileUrlN1());
       }
       if(StringUtils.isNullOrEmpty(bigCustomerDto.getFileUrlO())){
           poCuspo.setString("FILE_URL_O", bigCustomerDto.getFileUrlO());
       }else{
           poCuspo.setString("FILE_URL_O", bigCustomerDto.getFileUrlO1());
       }
       if(StringUtils.isNullOrEmpty(bigCustomerDto.getFileUrlP())){
           poCuspo.setString("FILE_URL_P", bigCustomerDto.getFileUrlP());
       }else{
           poCuspo.setString("FILE_URL_P", bigCustomerDto.getFileUrlP1());
       }
       
       if(StringUtils.isNullOrEmpty(bigCustomerDto.getFileUrlQ())){
           poCuspo.setString("FILE_URL_Q", bigCustomerDto.getFileUrlQ());
       }else{
           poCuspo.setString("FILE_URL_Q", bigCustomerDto.getFileUrlQ1());
       }
       if(StringUtils.isNullOrEmpty(bigCustomerDto.getFileUrlR())){
           poCuspo.setString("FILE_URL_R", bigCustomerDto.getFileUrlR());
       }else{
           poCuspo.setString("FILE_URL_R", bigCustomerDto.getFileUrlR1());
       }
       poCuspo.setInteger("SO_STATUS", 16101001);
    
        poCuspo.saveIt();
        System.out.println(bigCustomerDto.getInvent1().size());
        if(bigCustomerDto.getInvent1().size()>0 && bigCustomerDto.getInvent1() !=null){
            for(BigCustomerSalesDTO bigCustomerCardto : bigCustomerDto.getInvent1()){
                this.setConfig(bigCustomerCardto,bigCustomerDto.getWsNo(),intentId).saveIt(); 
            } 
        }
        return null;
    }
    public WholesaleRepayDteailPO setConfig(BigCustomerSalesDTO Dto,String wsNo,Long intentId){       
        WholesaleRepayDteailPO wsconfiginfopo=new WholesaleRepayDteailPO();
        wsconfiginfopo.setLong("ITEM_ID", intentId);
        wsconfiginfopo.setString("WS_NO",wsNo); 
        wsconfiginfopo.setString("BRAND_CODE",Dto.getBrandCode());
        wsconfiginfopo.setString("MODEL_CODE",Dto.getModelCode());
        wsconfiginfopo.setString("SERIES_CODE",Dto.getSeriesCode());
        wsconfiginfopo.setString("CONFIG_CODE",Dto.getConfigCode());
        wsconfiginfopo.setString("Vehicle_Price",Dto.getVehiclePrice());
        
        wsconfiginfopo.setString("FILE_CARD_URL",Dto.getFileCardUrl());
        wsconfiginfopo.setString("FILE_G_URL",Dto.getFileGUrl());
        wsconfiginfopo.setString("FILE_H2_URL",Dto.getFileH2Url());
        wsconfiginfopo.setString("FILE_H3_URL",Dto.getFileH3Url());
        wsconfiginfopo.setString("FILE_H4_URL",Dto.getFileH4Url());
        wsconfiginfopo.setString("FILE_H_URL",Dto.getFileHUrl());
        wsconfiginfopo.setString("FILE_INVOICE_URL",Dto.getFileInvoiceUrl());
        wsconfiginfopo.setString("FILE_I_URL",Dto.getFileIUrl());
        wsconfiginfopo.setString("FILE_SB1_URL",Dto.getFileSb1Url());
        wsconfiginfopo.setString("FILE_SB_URL",Dto.getFileSbUrl());
        wsconfiginfopo.setString("FILE_SFZ_URL",Dto.getFileSfzUrl());
        wsconfiginfopo.setString("VIN",Dto.getVin());
        wsconfiginfopo.setString("so_no",Dto.getSoNo());
        return wsconfiginfopo;
    }
    public PageInfoDto queryBigCustomerWs(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sql = new StringBuilder();
        sql.append(
                   "SELECT A.*,b.SUBMIT_TIME,b.FIRST_SUBMIT_TIME FROM TT_WHOLESALE_REPAY A LEFT JOIN TT_PO_CUS_WHOLESALE B ON A.Dealer_CODE=B.Dealer_CODE AND A.WS_NO=B.WS_NO WHERE A.Dealer_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' " +
                   " AND A.D_KEY=0 ");
           //查询条件
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("wsNo")))
        {
            sql.append(" AND A.WS_NO LIKE ?");
            queryList.add("%" + queryParam.get("wsNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo")))
        {
            sql.append(" AND A.CUSTOMER_NO LIKE ?");
            queryList.add("%" + queryParam.get("customerNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName")))
        {
            sql.append(" AND A.CUSTOMER_NAME LIKE ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("phone")))
        {
            sql.append(" AND A.PHONE LIKE ?");
            queryList.add("%" + queryParam.get("phone") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("mobile")))
        {
            sql.append(" AND A.MOBILE LIKE ?");
            queryList.add("%" + queryParam.get("mobile") + "%");
        }
        
           if(!StringUtils.isNullOrEmpty(queryParam.get("soStatus"))){
               sql.append(" AND A.SO_STATUS="+queryParam.get("soStatus")+" ");
               queryList.add( queryParam.get("soStatus") );
           }
           if (!StringUtils.isNullOrEmpty(queryParam.get("beginSubmitTime"))) {
               sql.append(" and c.SUBMIT_TIME>= ?");
               queryList.add(DateUtil.parseDefaultDate(queryParam.get("beginSubmitTime")));
           }
           if (!StringUtils.isNullOrEmpty(queryParam.get("endSubmitTime"))) {
               sql.append(" and c.SUBMIT_TIME<?");
               queryList.add(DateUtil.addOneDay(queryParam.get("endSubmitTime")));
           }
           if (!StringUtils.isNullOrEmpty(queryParam.get("beginAuditingDate"))) {
               sql.append(" and c.AUDIT_DATE>= ?");
               queryList.add(DateUtil.parseDefaultDate(queryParam.get("beginAuditingDate")));
           }
           if (!StringUtils.isNullOrEmpty(queryParam.get("endAuditingDate"))) {
               sql.append(" and c.AUDIT_DATE<?");
               queryList.add(DateUtil.addOneDay(queryParam.get("endAuditingDate")));
           }
          
        return  DAOUtil.pageQuery(sql.toString(), queryList);
    }
    public PageInfoDto queryBigCustomerWsCar(String id) throws ServiceBizException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  * FROM TT_WHOLESALE_REPAY_dteail  ");
        sql.append(" WHERE DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'  ");
        List<Object> queryList = new ArrayList<Object>();
        sql.append(" AND WS_NO='"+id+"'");
           
        return  DAOUtil.pageQuery(sql.toString(), queryList);
    }
    public PageInfoDto queryBigCustomerWsCarbyWsNo(Map<String, String> queryParam,String wsNo) throws ServiceBizException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  * FROM TT_WHOLESALE_REPAY_dteail  ");
        
        sql.append(" WHERE DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'  ");
        List<Object> queryList = new ArrayList<Object>();
        sql.append(" AND WS_NO='"+wsNo+"'");        
        return  DAOUtil.pageQuery(sql.toString(), queryList);
    }
    public List<Map> queryOwnerCusBywsNo(String wsNo)
            throws ServiceBizException {
    	   StringBuilder sql = new StringBuilder();
    	   sql.append(
                   "SELECT A.*,b.SUBMIT_TIME,b.FIRST_SUBMIT_TIME FROM TT_WHOLESALE_REPAY A LEFT JOIN TT_PO_CUS_WHOLESALE B ON A.Dealer_CODE=B.Dealer_CODE AND A.WS_NO=B.WS_NO WHERE A.Dealer_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' " +
                   " AND A.D_KEY=0 AND A.WS_NO='"+wsNo+"'"); 
           //查询条件
        List<Object> queryList = new ArrayList<Object>();
        return Base.findAll(sql.toString());
    }
    
    /**
    * @author LiGaoqi
    * @date 2017年4月27日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.BigCustomerManageRepayService#CheckStatus(java.lang.String)
    */
    	
    @Override
    public String CheckStatus(String id) throws ServiceBizException {
        String msg="0";
        StringBuffer sb = new StringBuffer();
        sb.append("select A.DEALER_CODE,COUNT(1) AS STATUS_ONE from TT_WHOLESALE_REPAY A inner join TM_POTENTIAL_CUSTOMER B on A.CUSTOMER_NO=B.CUSTOMER_NO AND A.dealer_code=B.dealer_code WHERE A.SO_STATUS=16101004 AND A.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND A.D_KEY=0 ");
        List<Object> queryList = new ArrayList<Object>();
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-6);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
            sb.append(" and A.AUDIT_DATE>= ?");
            queryList.add(DateUtil.parseDefaultDate(dateString));

        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if(result!=null&&result.size()>0){
            
            msg=result.get(0).get("STATUS_ONE").toString();
        }
 

return msg;
}
    
    /**
    * @author LiGaoqi
    * @date 2017年4月27日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.BigCustomerManageRepayService#CheckStatus1(java.lang.String)
    */
    	
    @Override
    public String CheckStatus1(String id) throws ServiceBizException {

        String msg="0";
        StringBuffer sb = new StringBuffer();
        sb.append("select A.DEALER_CODE,COUNT(1) AS STATUS_ONE from TT_WHOLESALE_REPAY A inner join TM_POTENTIAL_CUSTOMER B on A.CUSTOMER_NO=B.CUSTOMER_NO AND A.dealer_code=B.dealer_code WHERE A.SO_STATUS=16101005 AND A.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND A.D_KEY=0 ");
        List<Object> queryList = new ArrayList<Object>();
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-6);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
            sb.append(" and A.AUDIT_DATE>= ?");
            queryList.add(DateUtil.parseDefaultDate(dateString));

        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if(result!=null&&result.size()>0){
           
            msg=result.get(0).get("STATUS_ONE").toString();
        }
 

return msg;

    }
    
    /**
    * @author LiGaoqi
    * @date 2017年5月5日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.BigCustomerManageRepayService#CheckDateDetail(java.lang.String)
    */
    	
    @Override
    public List<Map> CheckDateDetail(String wsNo) throws ServiceBizException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  * FROM TT_WHOLESALE_REPAY_dteail  ");
        
        sql.append(" WHERE DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'  ");
        List<Object> queryList = new ArrayList<Object>();
        sql.append(" AND WS_NO='"+wsNo+"'"); 
        List<Map> result = DAOUtil.findAll(sql.toString(), queryList);
        // TODO Auto-generated method stub
        return result;
    }
    
    /**
    * @author LiGaoqi
    * @date 2017年5月5日
    * @param wsNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.BigCustomerManageRepayService#uploanBigCustomer(java.lang.String)
    */
    	
    @Override
    public String uploanBigCustomer(String wsNo) throws ServiceBizException {
        int flag=1;
        try {   
            flag= DSO0402.getDSO0402(wsNo);
        } catch (Exception e) {
            throw new ServiceBizException(e.getMessage()); 
        }
        if("0".equals(flag)){
            throw new ServiceBizException("大客户申请上报失败"); 
        }
        return null;
    }
    
    
    
    
    
    
}
