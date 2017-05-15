
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SEDMS058Impl.java
*
* @Author : LiGaoqi
*
* @Date : 2017年5月4日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月4日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.gacfca;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS061Cloud;
import com.yonyou.dms.DTO.gacfca.OrderCarDTO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO description
* @author LiGaoqi
* @date 2017年5月4日
*/
@Service
public class SEDMS058Impl implements SEDMS058 {
    
    private static final Logger logger = LoggerFactory.getLogger(SEDMS058Impl.class);
    @Autowired
    SADCS061Cloud SADCS061Cloud;

    /**
    * @author LiGaoqi
    * @date 2017年5月4日
    * @param soNo
    * @param vin
    * @return
    * @throws Exception
    * (non-Javadoc)
    * @see com.yonyou.dms.gacfca.SEDMS058#getSEDMS058(java.lang.String, java.lang.String)
    */

    @Override
    public String getSEDMS058(String soNo, String vin) throws ServiceBizException {
        String msg="1";
        try {
            System.err.println(soNo);
            System.err.println(vin);
            logger.info("====================进入接口SADCS061Cloud");
            boolean havaSoNo = false;
            boolean haveVin = false;
            boolean haveCus =false;
            boolean haveSoName=false;
            List<OrderCarDTO> resultList = new LinkedList();
            String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
            OrderCarDTO vo = new OrderCarDTO();
            SalesOrderPO selectpo = SalesOrderPO.findByCompositeKeys(dealerCode,soNo);
            System.err.println(selectpo);
            if(!StringUtils.isNullOrEmpty(selectpo)){
                havaSoNo = true;
            }
            System.err.println("=======================1");
            VsProductPO selectpro =VsProductPO.findByCompositeKeys(dealerCode,selectpo.getString("PRODUCT_CODE"));
            if(!StringUtils.isNullOrEmpty(selectpro)){
                haveVin = true;
                System.err.println("=======================2");
            }
            if(havaSoNo&&haveVin){
                System.err.println("=======================3");
                if(!StringUtils.isNullOrEmpty(vin)){
                    VsStockPO selectsto =VsStockPO.findByCompositeKeys(dealerCode,vin);
                    if(!StringUtils.isNullOrEmpty(selectsto)){
                        System.err.println("=======================4");
                        vo.setStockDate(selectsto.getDate("FIRST_STOCK_IN_DATE"));
                    }
                }
                System.err.println("=======================5");
               PotentialCusPO cuspo2 =  PotentialCusPO.findByCompositeKeys(dealerCode,selectpo.getString("CUSTOMER_NO"));
               System.err.println("=======================6");
               if(!StringUtils.isNullOrEmpty(cuspo2)){
                   System.err.println("=======================7");
                   haveCus = true;
                   if(!StringUtils.isNullOrEmpty(cuspo2.getString("CONTACTOR_MOBILE"))){
                       vo.setCustomerMobile(cuspo2.getString("CONTACTOR_MOBILE"));
                   }else{
                       vo.setCustomerMobile(cuspo2.getString("CONTACTOR_PHONE"));
                   }
                   System.err.println("=======================77");
                   UserPO user = UserPO.findByCompositeKeys(dealerCode,cuspo2.getString("SOLD_BY"));
                   System.err.println("=======================777");
                   if(!StringUtils.isNullOrEmpty(user)){
                       haveSoName=true;
                      vo.setSoldBy(user.getString("USER_NAME")); 
                   }
               }
               System.err.println("=======================8");
              vo.setEntityCode(dealerCode);
              vo.setCustomerName(selectpo.getString("CUSTOMER_NAME"));
              vo.setPhone(selectpo.getString("PHONE"));   //po.getph  PHONE
              vo.setCardType(selectpo.getInteger("CT_CODE")); //po.getc   CT_CODE
              vo.setCardNo(selectpo.getString("CERTIFICATE_NO"));    //po.get CERTIFICATE_NO
              vo.setBrand(selectpro.getString("BRAND_CODE"));//  BRAND_CODE
              vo.setCarModel(selectpro.getString("CONFIG_CODE"));//  配置
              vo.setFirstColor(selectpro.getString("COLOR_CODE"))    ;// COLOR_CODE
              vo.setDeliverMode(selectpo.getInteger("DELIVERY_MODE"));//  po. get//DELIVERY_MODE
              vo.setAppDeliverDate(selectpo.getDate("DELIVERING_DATE"));   //po.get    DELIVERING_DATE         
              vo.setDealDate(selectpo.getDate("SHEET_CREATE_DATE"));    //po.   SHEET_CREATE_DATE
              vo.setVin(selectpo.getString("VIN"));//po.VIN
              vo.setSoNo(selectpo.getString("SO_NO"));            
              resultList.add(vo);

            }
            if(havaSoNo&&haveVin&&haveCus&&haveSoName){
                logger.info("====================SADCS061Cloud开始上报");
                 msg =SADCS061Cloud.handleExecutor(resultList);
                 System.out.println(msg);
                logger.info("====================SADCS061Cloud结束上报");
            }
        } catch (Exception e) {
            logger.info("====================SADCS061Cloud失败");
            return "0";
        }
       
        return msg;
    }

}
