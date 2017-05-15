
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : LMSOrderServicePortWeberviceThread.java
*
* @Author : LiGaoqi
*
* @Date : 2017年5月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月8日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.infoeai.eai.wsClient.bsuv.lms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerIntentDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtEsCustomerOrderPO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO description
* @author LiGaoqi
* @date 2017年5月8日
*/

public class LMSOrderServicePortWeberviceThread {
    private static final Logger logger = LoggerFactory.getLogger(LMSOrderServicePortWeberviceThread.class);
    private String ecOrderNo;
    private String entityCode;
    private String customerNo;
    private String soNo;
    
    public void start() throws Exception{
        try {

            
            if(!StringUtils.isNullOrEmpty(ecOrderNo)){
                DepositVerificationRequest depositVerificationRequest = new DepositVerificationRequest();
                logger.debug("线程开始电商订单号"+ecOrderNo);
                depositVerificationRequest.setEC_ORDER_NO(ecOrderNo);
                OrderServicePortService service = new OrderServicePortServiceLocator();
                OrderServicePortSoap11Stub  webservices = (OrderServicePortSoap11Stub) service.getOrderServicePortSoap11();
                //调用LMS的webservice的方法
                DepositVerificationResponse response = webservices.depositVerification(depositVerificationRequest);
                if(response!=null){
                    SalesOrderPO tsoPO = SalesOrderPO.findByCompositeKeys(entityCode,soNo);
                    if(!StringUtils.isNullOrEmpty(tsoPO)){
                        double verIficationAmount=response.getVERIFICATION_AMOUNT();
                        Date VDate=response.getVERIFICATION_DATE();
                        boolean VTimeOut=response.isVERIFICATION_TIMEOUT();
                        boolean VERIFIED=response.isVERIFIED();
                        logger.debug("线程结束获取核销金额"+verIficationAmount+":核销日期"+VDate);
                        logger.debug("线程结束获取是否核销"+VERIFIED+":是否逾期"+VTimeOut);
                        List<Object> intentDetailList = new ArrayList<Object>();
                        intentDetailList.add(ecOrderNo);
                        intentDetailList.add(entityCode);
                        intentDetailList.add(customerNo);
                        intentDetailList.add(DictCodeConstants.D_KEY);
                        
                        List<TtEsCustomerOrderPO> intentDetailPo = TtEsCustomerOrderPO.findBySQL("select * from tt_es_customer_order where EC_ORDER_NO= ? AND DEALER_CODE= ? AND CUSTOMER_NO= ? AND D_KEY= ? ", intentDetailList.toArray());
                        if(!StringUtils.isNullOrEmpty(intentDetailPo)&&intentDetailPo.size()>0){
                            TtEsCustomerOrderPO tesoPO=intentDetailPo.get(0);
                          //是否逾期，如果为是：修改订单状态为已逾期
                            if(VTimeOut){
                                tesoPO.setInteger("ESC_ORDER_STATUS",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_STATUS_OVERDUE));
                                tsoPO.setInteger("ESC_ORDER_STATUS",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_STATUS_OVERDUE));
                            }
                          //是否核销，如果为是：修改状态为已核销
                            if(VERIFIED){
                                tsoPO.setInteger("VERIFICATION_TIMEOUT",Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                tesoPO.setInteger("VERIFIED",Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                            }else{
                                tsoPO.setInteger("VERIFICATION_TIMEOUT",Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                                tesoPO.setInteger("VERIFIED",Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                            }
                            tsoPO.setString("UPDATED_BY","1");
                            tsoPO.setDate("UPDATED_AT",new Date());
                            tesoPO.setString("UPDATED_BY","1");
                            tesoPO.setDate("UPDATED_AT",new Date());
                            tsoPO.saveIt();
                            tesoPO.saveIt();
                            

                        }
                     if(VTimeOut){
                         PotentialCusPO tpcpo =PotentialCusPO.findByCompositeKeys(entityCode,customerNo);
                         if(!StringUtils.isNullOrEmpty(tpcpo)){
                             tpcpo.setInteger("ESC_ORDER_STATUS", Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_STATUS_OVERDUE));
                             tpcpo.setString("UPDATED_BY","1");
                             tpcpo.setDate("UPDATED_AT",new Date());
                             tpcpo.saveIt();
                         }
                     }
                    }
                }
            }
         
        } catch (Exception e) {
            logger.debug("网络不通或者服务器连不上!webservice出错了!");
            throw e;
        }
    }
    /**
     * @return the ecOrderNo
     */
    public String getEcOrderNo() {
        return ecOrderNo;
    }
    
    /**
     * @param ecOrderNo the ecOrderNo to set
     */
    public void setEcOrderNo(String ecOrderNo) {
        this.ecOrderNo = ecOrderNo;
    }
    
    /**
     * @return the entityCode
     */
    public String getEntityCode() {
        return entityCode;
    }
    
    /**
     * @param entityCode the entityCode to set
     */
    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }
    
    /**
     * @return the customerNo
     */
    public String getCustomerNo() {
        return customerNo;
    }
    
    /**
     * @param customerNo the customerNo to set
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
    
    /**
     * @return the soNo
     */
    public String getSoNo() {
        return soNo;
    }
    
    /**
     * @param soNo the soNo to set
     */
    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }
    

}
