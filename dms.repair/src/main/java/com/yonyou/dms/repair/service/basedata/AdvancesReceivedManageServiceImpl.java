
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : AdvancesReceivedManageServiceImpl.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月8日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.ReceiveMoneyPO;
import com.yonyou.dms.common.domains.PO.basedata.TTPreReceiveMoneyPO;
import com.yonyou.dms.common.domains.PO.basedata.TmOwnerSubclassPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartCustomerSubclassPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehicleSubclassPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月8日
*/
@SuppressWarnings("rawtypes")
@Service
public class AdvancesReceivedManageServiceImpl implements AdvancesReceivedManageService{
    
    @Autowired
    private CommonNoService commonNoService;

    @Override
    public PageInfoDto retrieveHasPrePay() throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select a.DEALER_CODE,a.OWNER_NO as CUSTOMER_CODE, a.OWNER_NAME as CUSTOMER_NAME, a.ADDRESS,a.ZIP_CODE,");
        sql.append("a.CONTACTOR_PHONE,a.CONTACTOR_MOBILE,a.PRE_PAY from ("+CommonConstants.VM_OWNER+") a");
        sql.append(" where a.PRE_PAY > 0 and a.DEALER_CODE = ? union ");
        sql.append(" select b.DEALER_CODE,b.CUSTOMER_CODE, b.CUSTOMER_NAME, b.ADDRESS,b.ZIP_CODE,b.CONTACTOR_PHONE,b.CONTACTOR_MOBILE,b.PRE_PAY from ("+CommonConstants.VM_PART_CUSTOMER+") b");
        sql.append(" where b.PRE_PAY > 0 and b.DEALER_CODE = ?");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public List<Map> retrieveHasPrePayExport() throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select a.DEALER_CODE,a.OWNER_NO as CUSTOMER_CODE, a.OWNER_NAME as CUSTOMER_NAME, a.ADDRESS,a.ZIP_CODE,");
        sql.append("a.CONTACTOR_PHONE,a.CONTACTOR_MOBILE,a.PRE_PAY from ("+CommonConstants.VM_OWNER+") a");
        sql.append(" where a.PRE_PAY > 0 and a.DEALER_CODE = ? union ");
        sql.append(" select b.DEALER_CODE,b.CUSTOMER_CODE, b.CUSTOMER_NAME, b.ADDRESS,b.ZIP_CODE,b.CONTACTOR_PHONE,b.CONTACTOR_MOBILE,b.PRE_PAY from ("+CommonConstants.VM_PART_CUSTOMER+") b");
        sql.append(" where b.PRE_PAY > 0 and b.DEALER_CODE = ?");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        return DAOUtil.findAll(sql.toString(), list);
    }

    @Override
    public PageInfoDto retrieveByCustomer(Map<String, String> map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select A.* from TT_PRE_RECEIVE_MONEY A ");
        sql.append(" where A.D_KEY = ? AND A.DEALER_CODE = ? ");
        list.add(CommonConstants.D_KEY);
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        if (!StringUtils.isNullOrEmpty(map.get("customerCode"))) {
            sql.append(" AND A.CUSTOMER_CODE like ? ");
            list.add("%"+map.get("customerCode")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("customerName"))) {
            sql.append(" AND A.CUSTOMER_NAME like ? ");
            list.add("%"+map.get("customerName")+"%");
        }
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public PageInfoDto retrieveInsuranceFeeByCustomer(Map<String, String> map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select A.* from TT_RECEIVE_MONEY A ");
        sql.append(" where A.D_KEY = ? AND A.RECEIVE_SORT = ? AND A.DEALER_CODE = ?");
        list.add(CommonConstants.D_KEY);
        list.add(DictCodeConstants.DICT_RECEIVE_SORT_INSURANCE);
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        if (!StringUtils.isNullOrEmpty(map.get("customerCode"))) {
            sql.append(" AND A.CUSTOMER_CODE like ? ");
            list.add("%"+map.get("customerCode")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("customerName"))) {
            sql.append(" AND A.CUSTOMER_NAME like ? ");
            list.add("%"+map.get("customerName")+"%");
        }
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public PageInfoDto queryByNoOrSpell(Map<String, String> map) throws ServiceBizException {
        StringBuffer sql = new StringBuffer("SELECT A.KEY_NUMBER, VIN, OWNER_NO, LICENSE,");
        sql.append(" ENGINE_NO, BRAND, SERIES, MODEL, DELIVERER, DELIVERER_PHONE ");
        sql.append(" DELIVERER_MOBILE,DEALER_CODE,LAST_MAINTAIN_DATE,SERVICE_ADVISOR,");
        sql.append(" CONSULTANT,A.CREATED_BY FROM ("+CommonConstants.VM_VEHICLE+") A WHERE EXISTS");
        sql.append(" (SELECT OWNER_NO FROM ("+CommonConstants.VM_OWNER+") B WHERE B.OWNER_NO = A.OWNER_NO AND B.DEALER_CODE = A.DEALER_CODE");
        if (!StringUtils.isNullOrEmpty(map.get("customerCode"))) {
            Utility.getLikeCond("B", "OWNER_NO", map.get("customerCode"), "AND");
        }
        sql.append(")");
        return DAOUtil.pageQuery(sql.toString(), null);
    }

    @Override
    public String inster(Map map) throws ServiceBizException {
        String preReceiveNo = "";
        // 新增预收款
        if (DictCodeConstants.DICT_RECEIVE_SORT_PRE_RECEIVE.equals(map.get("receiveSort"))) {
            TTPreReceiveMoneyPO preMoneyPo = new TTPreReceiveMoneyPO();
            preMoneyPo.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
            preReceiveNo = commonNoService.GetBillNo(CommonConstants.SRV_YSKDH);
            preMoneyPo.set("PRE_RECEIVE_NO",preReceiveNo);
            if (!StringUtils.isNullOrEmpty(map.get("receiveAmount"))) {
                preMoneyPo.set("Receive_Amount",map.get("receiveAmount"));
            }
            if (!StringUtils.isNullOrEmpty(map.get("payoutAmount"))) {
                preMoneyPo.set("Payout_Amount",map.get("payoutAmount"));
            }
            
            double prePay = 0d;
            //customerCode不为空的话是按客户新增预收款
            if (!StringUtils.isNullOrEmpty(map.get("customerCode"))){
                List<Map> list = queryOwnerAndCustomer(map.get("customerCode").toString());
                if (list != null && list.size() > 0) {
                    if (!StringUtils.isNullOrEmpty(list.get(0).get("PRE_PAY"))) {
                        prePay = Double.parseDouble(list.get(0).get("PRE_PAY").toString());
                    }
                }
            }
            preMoneyPo.set("AFTER_AMOUNT",Utility.round(String.valueOf(prePay+Utility.getDouble(map.get("receiveAmount").toString())),2));
            preMoneyPo.set("PAY_TYPE_CODE",map.get("payTypeCode"));
            preMoneyPo.set("CHECK_NO",map.get("checkNo"));
            preMoneyPo.set("RECEIVE_SORT",map.get("receiveSort"));
            preMoneyPo.set("INVOICE_TYPE_CODE",map.get("invoiceTypeCode"));
            preMoneyPo.set("BILL_NO",map.get("billNo"));
            preMoneyPo.set("Customer_Code",map.get("customerCode"));
            preMoneyPo.set("Customer_Name",map.get("customerName"));
            if (!StringUtils.isNullOrEmpty(map.get("customerType"))){
                preMoneyPo.set("PRE_PAY_CUS_TYPE",map.get("customerType"));
            }
            preMoneyPo.set("LICENSE",map.get("license"));
            preMoneyPo.set("Vin",map.get("vin"));
            preMoneyPo.set("Owner_No",map.get("ownerNo"));
            preMoneyPo.set("Owner_Name",map.get("ownerName"));
            preMoneyPo.set("Handler",map.get("handler"));
            preMoneyPo.set("Handle_Time",new Date(System.currentTimeMillis()));
            preMoneyPo.set("Remark",map.get("remark"));
            
            preMoneyPo.set("Card_No",map.get("cardId"));
            preMoneyPo.saveIt();
//          赠送积分
            /*if ((vin!=null && !vin.equals("")) || (license!=null && !license.equals(""))){
                IntegralProductionRules ipr=new IntegralProductionRules();
                if ((vin==null || vin.equals("")) && license!=null && !license.equals("")){
                    TmVehiclePO mVehiclePO=new TmVehiclePO();
                    //mVehiclePO.setEntityCode(entityCode);                     
                    String groupCode=Utility.getGroupEntity(conn, entityCode, "TM_VEHICLE");
                    mVehiclePO.setEntityCode(groupCode);
                    logger.debug(" GROUPCODE :"+groupCode);
                    mVehiclePO.setLicense(license);
                    List<TmVehiclePO> mList=POFactory.select(conn, mVehiclePO);
                    if (mList != null && mList.size() > 0 ){
                        vin =((TmVehiclePO) mList.get(0)).getVin();
                    }
                    
                }
                if (vin!=null && !vin.equals("")){
                    String cardId=ipr.queryCardIdByVin(conn, entityCode, vin);
                    if(!cardId.equals("NO")){
                        ipr.preReceiveMoneyCalculateIntegral(conn, entityCode, preReceiveNo, cardId, userId, empNo);
                    }
                }
            }*/
            

            // 对于保险费和预收款,都会在收款记录中增加一条记录,收款记录表相当于一个收款流水帐,当在收款时使用预收款时,也会在收款记录表中单独增加一条使用预收款(金额为负)的记录
            ReceiveMoneyPO receiveMoneyDb = new ReceiveMoneyPO();
            receiveMoneyDb.set("Bill_No",map.get("billNo"));
            receiveMoneyDb.set("Handler",map.get("handler"));
            receiveMoneyDb.set("Customer_Code",map.get("customerCode"));
            receiveMoneyDb.set("Customer_Name",map.get("customerName"));
            if (!StringUtils.isNullOrEmpty(map.get("customerType"))){
                receiveMoneyDb.set("Pre_Pay_Cus_Type",map.get("customerType"));
            }
            receiveMoneyDb.set("License",map.get("license"));
            receiveMoneyDb.set("Owner_Name",map.get("ownerName"));
            receiveMoneyDb.set("Owner_No",map.get("ownerNo"));
            receiveMoneyDb.set("Invoice_Type_Code",map.get("invoiceTypeCode"));
            receiveMoneyDb.set("Pay_Type_Code",map.get("payTypeCode"));
            if (!StringUtils.isNullOrEmpty(map.get("receiveAmount"))) {
                receiveMoneyDb.set("Receive_Amount",map.get("receiveAmount"));
            }
            receiveMoneyDb.set("Receive_Date",new Date(System.currentTimeMillis()));
            receiveMoneyDb.set("Check_No",map.get("checkNo"));
            receiveMoneyDb.set("Receive_No",commonNoService.GetBillNo(CommonConstants.SRV_SKDH));
            if (!StringUtils.isNullOrEmpty(map.get("receiveSort"))) {
                receiveMoneyDb.set("Receive_Sort",map.get("receiveSort"));
            }
            receiveMoneyDb.set("Remark",map.get("remark"));
            receiveMoneyDb.set("Vin",map.get("vin"));
            receiveMoneyDb.set("Check_No",map.get("checkNo"));
            receiveMoneyDb.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
            
            receiveMoneyDb.set("Card_No",map.get("cardId"));
            receiveMoneyDb.set("Consume_Fid",map.get("consumeFid"));
            receiveMoneyDb.set("Bank_Account",map.get("bankAccount"));
            receiveMoneyDb.set("Coupon_No",map.get("couponNo"));
            receiveMoneyDb.saveIt();
            
            //预收款的话要更新相应的预收款余额,这里不能根据基本参数设置的值来判断(因为潜在的并发问题),而是通过前台传的关键值来判断
            if (DictCodeConstants.DICT_RECEIVE_SORT_PRE_RECEIVE.equals(map.get("receiveSort"))){
                //customerCode不为空的话是按客户新增预收款
                if (!StringUtils.isNullOrEmpty(map.get("customerCode"))){
                    if (!StringUtils.isNullOrEmpty(map.get("customerType"))&& map.get("customerType").equals(DictCodeConstants.DICT_PRE_PAY_CUSTOMER_TYPE_CUSTOMER)){
                        TmPartCustomerSubclassPO.update("PRE_PAY =  (CASE WHEN PRE_PAY IS NULL THEN 0 ELSE PRE_PAY END)-?", "CUSTOMER_CODE = ?", map.get("receiveAmount"),map.get("customerCode"));
                    }else if (!StringUtils.isNullOrEmpty(map.get("customerType"))&& map.get("customerType").equals(DictCodeConstants.DICT_PRE_PAY_CUSTOMER_TYPE_OWNER)){
                        TmOwnerSubclassPO.update("PRE_PAY =  (CASE WHEN PRE_PAY IS NULL THEN 0 ELSE PRE_PAY END)-?", "OWNER_NO = ? ", map.get("receiveAmount"),map.get("customerCode"));
                    }
                }else{
                    TmVehicleSubclassPO.update("PRE_PAY = Coalesce(PRE_PAY,0)-?", "VIN=?", map.get("receiveAmount"),map.get("vin"));
                }
            }    
        }    
        return preReceiveNo;
    }
    
    public List<Map> queryOwnerAndCustomer(String s){
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select distinct * from ");
        sql.append(" (select " + DictCodeConstants.DICT_PRE_PAY_CUSTOMER_TYPE_OWNER + " AS PRE_PAY_CUS_TYPE,O.DEALER_CODE, ");
        sql.append(" O.OWNER_NO as CUSTOMER_CODE, OWNER_NAME as CUSTOMER_NAME, ADDRESS,O.ZIP_CODE,CONTACTOR_NAME,CONTACTOR_PHONE,");
        sql.append(" CONTACTOR_MOBILE,O.PRE_PAY,O.ARREARAGE_AMOUNT,CUS_RECEIVE_SORT from ("+CommonConstants.VM_OWNER+") O ");
        sql.append(" LEFT JOIN ("+CommonConstants.VM_VEHICLE+") V ON V.DEALER_CODE=O.DEALER_CODE AND O.OWNER_NO=V.OWNER_NO ");
        sql.append(" union select " + DictCodeConstants.DICT_PRE_PAY_CUSTOMER_TYPE_CUSTOMER + " AS PRE_PAY_CUS_TYPE,s.DEALER_CODE,");
        sql.append(" s.CUSTOMER_CODE, s.CUSTOMER_NAME, s.ADDRESS,s.ZIP_CODE,s.CONTACTOR_NAME,s.CONTACTOR_PHONE,s.CONTACTOR_MOBILE,s.PRE_PAY,");
        sql.append(" s.ARREARAGE_AMOUNT,s.CUS_RECEIVE_SORT from ("+CommonConstants.VM_PART_CUSTOMER+") s");
        sql.append(" where DEALER_CODE = ? ) A where 1 =1 ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        Utility.getLikeCond("A", "CUSTOMER_CODE", s, "AND");
        return DAOUtil.findAll(sql.toString(), list);
    }

}
