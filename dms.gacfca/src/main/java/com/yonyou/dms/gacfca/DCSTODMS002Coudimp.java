
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : DCSTODMS002imp.java
*
* @Author : LiGaoqi
*
* @Date : 2017年4月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月19日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.DCSTODMS002Dto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TrackingTaskPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerIntentDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtEsCustomerOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusLinkmanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanPO;
import com.yonyou.dms.common.service.monitor.Utility;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
* TODO description
* @author LiGaoqi
* @date 2017年4月19日
*/
@Service
public class DCSTODMS002Coudimp implements DCSTODMS002Coud {
    
    private static final Logger logger = LoggerFactory.getLogger(DCSTODMS002Coudimp.class);
    @Autowired
    private CommonNoService     commonNoService;
    /**
    * @author LiGaoqi
    * @date 2017年4月19日
    * @param voList
    * @param dealerCode
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.gacfca.DCSTODMS002Coud#getSADMS061(java.util.LinkedList, java.lang.String)
    */

    @Override
    public int getSADMS061(LinkedList<DCSTODMS002Dto> voList, String dealerCode) throws ServiceBizException {
        logger.info("*************************** 开始 ******************************");
        try {
            if (voList != null && voList.size() > 0){
                for (int i = 0; i < voList.size(); i++){
                    DCSTODMS002Dto vo = voList.get(i);
                  //经销商代码，官网订单号，手机号码不能为空
                    if(!StringUtils.isNullOrEmpty(vo.getEntityCode())&&!StringUtils.isNullOrEmpty(vo.getEcOrderNo())&&!StringUtils.isNullOrEmpty(vo.getTel())){
//                      1.检验entityCode是否有效
                        //判断是否撞单
                        List<Object> CusPOList = new ArrayList<Object>();
                        CusPOList.add(vo.getTel());
                        CusPOList.add(vo.getTel());
                        CusPOList.add(dealerCode);
                        CusPOList.add(DictCodeConstants.D_KEY);
                        List<PotentialCusPO> tpcs = PotentialCusPO.findBySQL("select * from TM_POTENTIAL_CUSTOMER where (CONTACTOR_MOBILE= ? or CONTACTOR_PHONE= ?)  AND DEALER_CODE= ? AND D_KEY= ? ", CusPOList.toArray());
                        if(!StringUtils.isNullOrEmpty(tpcs)&&tpcs.size()>0){
//                          一、校验DMS是否存在官网客户手机的潜客，如果存在：
                //          （1）在DMS新增官网订单表，保存LMS下发的官网客户和订单信息，其中"是否已取消"默认为否
                            PotentialCusPO oldtpc=tpcs.get(0);
                           // 3.判断官网订单号在这家经销商是否存在，存在判断状态
                            List<Object> querytecoList = new ArrayList<Object>();
                            querytecoList.add(vo.getEcOrderNo());
                            querytecoList.add(vo.getTel());
                            querytecoList.add(dealerCode);
                            querytecoList.add(DictCodeConstants.D_KEY);
                            List<TtEsCustomerOrderPO> queryteco = TtEsCustomerOrderPO.findBySQL("select * from TT_ES_CUSTOMER_ORDER where EC_ORDER_NO= ? AND CONTACTOR_MOBILE= ?  AND DEALER_CODE= ? AND D_KEY= ? ", querytecoList.toArray());
                          //如果官网订单存在，那么做覆盖操作
                            if(!StringUtils.isNullOrEmpty(queryteco)&&queryteco.size()>0){
                                TtEsCustomerOrderPO querytecoPO = queryteco.get(0);
                                //TtEsCustomerOrderPO newtecoPO=new TtEsCustomerOrderPO();
                                if(!StringUtils.isNullOrEmpty(vo.getBrandCode())){
                                    querytecoPO.setString("INTENT_BRAND", vo.getBrandCode());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getSeriesCode())){
                                    querytecoPO.setString("INTENT_SERIES",vo.getSeriesCode());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getModelCode())){
                                    querytecoPO.setString("INTENT_MODEL", vo.getModelCode());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getConfigCode())){
                                    querytecoPO.setString("INTENT_CONFIG",vo.getConfigCode());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getColorCode())){
                                    querytecoPO.setString("INTENT_COLOR", vo.getColorCode());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getTrimCode())){
                                    querytecoPO.setString("INTENT_TRIM",vo.getTrimCode());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getModelYear())){
                                    querytecoPO.setString("MODEL_YEAR", vo.getModelYear());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getRetailFinancial())){
                                    querytecoPO.setString("RETAIL_FINANCE",vo.getRetailFinancial());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getDepositAmount())){
                                    querytecoPO.setFloat("DEPOSIT_AMOUNT",vo.getDepositAmount());
                                }
                                if (vo.getEcOrderType()==16181002){
                                    //querytecoPO.setEscOrderStatus(querytecoPO.getEscOrderStatus());//官网订单状态，有效
                                    querytecoPO.setInteger("ESC_TYPE",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_TYPE_FUTURES));//官网订单类型 期货
                                }else if(vo.getEcOrderType()==16191002){
                                    //querytecoPO.setEscOrderStatus(querytecoPO.getEscType());//官网订单状态，有效
                                    querytecoPO.setInteger("ESC_ORDER_STATUS",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_STATUS_OVERDUE));
                                }else{
                                    //querytecoPO.setEscOrderStatus(querytecoPO.getEscOrderStatus());//官网订单状态，有效
                                    querytecoPO.setInteger("ESC_TYPE",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_TYPE_STOCK));//官网订单类型 现货
                                }
                                querytecoPO.setString("UPDATED_BY", 1);
                                querytecoPO.setDate("UPDATED_AT", new Date());
                                querytecoPO.saveIt();
                                
                              //修改官网客户的类型：期货还是现货
                                PotentialCusPO oldtpcPO = PotentialCusPO.findByCompositeKeys(dealerCode,querytecoPO.getString("CUSTOMER_NO"));
                                if(oldtpcPO!=null){
                                    if(!StringUtils.isNullOrEmpty(querytecoPO.getInteger("ESC_TYPE"))){
                                        oldtpcPO.setInteger("ESC_TYPE", querytecoPO.getInteger("ESC_TYPE"));
                                        oldtpcPO.setInteger("ESC_ORDER_STATUS", querytecoPO.getInteger("ESC_ORDER_STATUS"));
                                        oldtpcPO.setString("CREATED_BY", "1");
                                        oldtpcPO.setDate("CREATED_AT", new Date());
                                        oldtpcPO.saveIt();
                                    }
                                  //修改客户中意向的官网意向
                                    //意向品牌和意向车系必须有
                                    List<Object> intentList = new ArrayList<Object>();
                                    intentList.add(oldtpcPO.getString("INTENT_ID"));
                                    intentList.add(querytecoPO.getString("CUSTOMER_NO"));
                                    intentList.add(dealerCode);
                                    intentList.add(Long.parseLong(DictCodeConstants.D_KEY));
                                    List<TtCusIntentPO> intentPo = TtCusIntentPO.findBySQL("select * from TT_CUSTOMER_INTENT where INTENT_ID= ? AND CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", intentList.toArray());
                                    if(intentPo!=null&&intentPo.size()>0){
                                        //并且把其他的意向修改为子意向
//                                      4.同时把这个客户名下其他主意向修改为子意向
                                        List<Object> intentDetaiList = new ArrayList<Object>();
                                        intentDetaiList.add(oldtpcPO.getString("INTENT_ID"));
                                        intentDetaiList.add(Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                        intentDetaiList.add(dealerCode);
                                        intentDetaiList.add(Long.parseLong(DictCodeConstants.D_KEY));
                                        List<TtCustomerIntentDetailPO> intentDetaiListPo = TtCustomerIntentDetailPO.findBySQL("select * from TT_CUSTOMER_INTENT_DETAIL where INTENT_ID= ? AND IS_MAIN_MODEL= ? AND DEALER_CODE= ? AND D_KEY= ? ", intentDetaiList.toArray());

                                        if(intentDetaiListPo!=null&&intentDetaiListPo.size()>0){
                                            for(int j=0;j<intentDetaiListPo.size();j++){
                                                TtCustomerIntentDetailPO querydetail1 =intentDetaiListPo.get(i);
                                                querydetail1.setInteger("IS_MAIN_MODEL", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                                                querydetail1.saveIt();
                                            }
                                           
                                        }
                                        List<Object> olddetailist = new ArrayList<Object>();
                                        olddetailist.add(oldtpcPO.getString("INTENT_ID"));
                                        olddetailist.add(vo.getEcOrderNo());
                                        olddetailist.add(dealerCode);
                                        olddetailist.add(Long.parseLong(DictCodeConstants.D_KEY));
                                        List<TtCustomerIntentDetailPO> iolddetailist = TtCustomerIntentDetailPO.findBySQL("select * from TT_CUSTOMER_INTENT_DETAIL where INTENT_ID= ? AND EC_ORDER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", olddetailist.toArray());
                                        if(iolddetailist!=null&&iolddetailist.size()>0){
                                            TtCustomerIntentDetailPO olddetail = iolddetailist.get(0);
                                            if(!StringUtils.isNullOrEmpty(vo.getBrandCode())){
                                                olddetail.setString("INTENT_BRAND", vo.getBrandCode());
                                            }
                                            if(!StringUtils.isNullOrEmpty(vo.getSeriesCode())){
                                                olddetail.setString("INTENT_SERIES",vo.getSeriesCode());
                                            }
                                            if(!StringUtils.isNullOrEmpty(vo.getModelCode())){
                                                olddetail.setString("INTENT_MODEL", vo.getModelCode());
                                            }
                                            if(!StringUtils.isNullOrEmpty(vo.getConfigCode())){
                                                olddetail.setString("INTENT_CONFIG",vo.getConfigCode());
                                            }
                                            if(!StringUtils.isNullOrEmpty(vo.getColorCode())){
                                                olddetail.setString("INTENT_COLOR", vo.getColorCode());
                                            }
                                            olddetail.setInteger("IS_MAIN_MODEL", Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                            if(!StringUtils.isNullOrEmpty(vo.getRetailFinancial())){
                                                olddetail.setString("RETAIL_FINANCE",vo.getRetailFinancial());
                                            }
                                            olddetail.setDate("DETERMINED_TIME", vo.getDepositDate());
                                            if(!StringUtils.isNullOrEmpty(vo.getDepositAmount())){
                                                olddetail.setFloat("DEPOSIT_AMOUNT",vo.getDepositAmount());
                                            }
                                            olddetail.setString("UPDATED_BY", "1");
                                            olddetail.setDate("UPDATED_AT", new Date());
                                            olddetail.setInteger("IS_ECO_INTENT_MODEL", Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                            olddetail.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                                            olddetail.saveIt();
                                            
                                        }else{
//                                          新增潜客意向明细表
                                            TtCustomerIntentDetailPO adddetail =new TtCustomerIntentDetailPO();
                                            adddetail.setLong("INTENT_ID", oldtpcPO.getString("INTENT_ID"));
                                            if(!StringUtils.isNullOrEmpty(vo.getBrandCode())){
                                                adddetail.setString("INTENT_BRAND", vo.getBrandCode());
                                            }
                                            if(!StringUtils.isNullOrEmpty(vo.getSeriesCode())){
                                                adddetail.setString("INTENT_SERIES",vo.getSeriesCode());
                                            }
                                            if(!StringUtils.isNullOrEmpty(vo.getModelCode())){
                                                adddetail.setString("INTENT_MODEL", vo.getModelCode());
                                            }
                                            if(!StringUtils.isNullOrEmpty(vo.getConfigCode())){
                                                adddetail.setString("INTENT_CONFIG",vo.getConfigCode());
                                            }
                                            if(!StringUtils.isNullOrEmpty(vo.getColorCode())){
                                                adddetail.setString("INTENT_COLOR", vo.getColorCode());
                                            }
                                            adddetail.setInteger("IS_MAIN_MODEL", Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                            if(!StringUtils.isNullOrEmpty(vo.getRetailFinancial())){
                                                adddetail.setString("RETAIL_FINANCE",vo.getRetailFinancial());
                                            }
                                            adddetail.setDate("DETERMINED_TIME", vo.getDepositDate());
                                            if(!StringUtils.isNullOrEmpty(vo.getDepositAmount())){
                                                adddetail.setFloat("DEPOSIT_AMOUNT",vo.getDepositAmount());
                                            }
                                            adddetail.setString("CREATED_BY", "1");
                                            adddetail.setString("DEALER_CODE", dealerCode);
                                            adddetail.setDate("CREATED_AT", new Date());
                                            adddetail.setInteger("IS_ECO_INTENT_MODEL", Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                            adddetail.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                                            adddetail.saveIt();
                                            
                                        }
                                        //
                                        
                                    }
                                    

                                }
                            }else{
                                TtEsCustomerOrderPO tecoPO=new TtEsCustomerOrderPO();
                                if(!StringUtils.isNullOrEmpty(vo.getBrandCode())){
                                    tecoPO.setString("INTENT_BRAND", vo.getBrandCode());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getSeriesCode())){
                                    tecoPO.setString("INTENT_SERIES",vo.getSeriesCode());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getModelCode())){
                                    tecoPO.setString("INTENT_MODEL", vo.getModelCode());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getConfigCode())){
                                    tecoPO.setString("INTENT_CONFIG",vo.getConfigCode());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getColorCode())){
                                    tecoPO.setString("INTENT_COLOR", vo.getColorCode());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getTrimCode())){
                                    tecoPO.setString("INTENT_TRIM",vo.getTrimCode());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getModelYear())){
                                    tecoPO.setString("MODEL_YEAR", vo.getModelYear());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getRetailFinancial())){
                                    tecoPO.setString("RETAIL_FINANCE",vo.getRetailFinancial());
                                }
                                if(!StringUtils.isNullOrEmpty(vo.getDepositAmount())){
                                    tecoPO.setFloat("DEPOSIT_AMOUNT",vo.getDepositAmount());
                                }
                                tecoPO.setDate("DETERMINED_TIME",vo.getDepositDate());//下定时间
                                tecoPO.setString("EC_ORDER_NO",vo.getEcOrderNo());//官网订单号
                                if(!StringUtils.isNullOrEmpty(vo.getIdCard())){
                                    tecoPO.setString("CT_CODE",Integer.parseInt(DictCodeConstants.DICT_CERTIFICATE_TYPE_IDENTITY_CARD));//证件类型
                                    tecoPO.setString("CERTIFICATE_NO",vo.getIdCard());//身份证号码
                                }
                                if (vo.getEcOrderType()==16181002){
                                    tecoPO.setInteger("ESC_ORDER_STATUS",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_STATUS_VALID));//官网订单状态，有效
                                    tecoPO.setInteger("ESC_TYPE",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_TYPE_FUTURES));//官网订单类型 期货
                                }else{
                                    tecoPO.setInteger("ESC_ORDER_STATUS",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_STATUS_VALID));//官网订单状态，有效
                                    tecoPO.setInteger("ESC_TYPE",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_TYPE_STOCK));//官网订单类型 现货
                                }
                                tecoPO.setString("CONTACTOR_MOBILE", vo.getTel());
                                if(!StringUtils.isNullOrEmpty(vo.getCustomerName()))
                                tecoPO.setString("CUSTOMER_NAME", vo.getCustomerName());
                                
                                tecoPO.setString("CUSTOMER_NO", oldtpc.getString("CUSTOMER_NO"));
                                tecoPO.setString("CREATED_BY", "1");
                                tecoPO.setDate("CREATED_AT", new Date());
                                tecoPO.setString("DEALER_CODE", dealerCode);
                                tecoPO.saveIt();
                              //2.（2）DMS潜客表中以官网客户覆盖原有的客户信息，包括：客户姓名、客户手机、客户来源(“官网客户”)、客户级别(默认为H级)
                                List<Object> newtpcList = new ArrayList<Object>();
                                newtpcList.add(vo.getTel());
                                newtpcList.add(vo.getTel());
                                newtpcList.add(dealerCode);
                                newtpcList.add(DictCodeConstants.D_KEY);
                                List<PotentialCusPO> newtpcPo = PotentialCusPO.findBySQL("select * from TM_POTENTIAL_CUSTOMER where (CONTACTOR_MOBILE= ? or CONTACTOR_PHONE= ?)  AND DEALER_CODE= ? AND D_KEY= ? ", newtpcList.toArray());
                                if(newtpcPo!=null&&newtpcPo.size()>0){
                                    for(int m=0;m<newtpcPo.size();m++){
                                        PotentialCusPO newtpc = newtpcPo.get(m);
                                        if(!StringUtils.isNullOrEmpty(vo.getCustomerName()))
                                            newtpc.setString("CUSTOMER_NAME",vo.getCustomerName());
                                            newtpc.setInteger("CUS_SOURCE",13111021);//客户来源
                                            if(!StringUtils.isNullOrEmpty(vo.getIdCard())){
                                                tecoPO.setString("CT_CODE",Integer.parseInt(DictCodeConstants.DICT_CERTIFICATE_TYPE_IDENTITY_CARD));//证件类型
                                                tecoPO.setString("CERTIFICATE_NO",vo.getIdCard());//身份证号码
                                            }
                                            newtpc.setInteger("INTENT_LEVEL",Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_H));
                                            newtpc.setString("CONTACTOR_MOBILE",vo.getTel());//手机号码
                                            newtpc.setString("EC_ORDER_NO",vo.getEcOrderNo());//官网订单号
                                            newtpc.setInteger("ESC_TYPE",tecoPO.getInteger("ESC_TYPE"));//官网订单类型：期货和现货
                                            newtpc.setInteger("ESC_ORDER_STATUS",tecoPO.getInteger("ESC_ORDER_STATUS"));//官网订单状态：默认为有效
                                            newtpc.setInteger("IS_HIT_FOLLOW_UPLOAD",Integer.parseInt(DictCodeConstants.DICT_IS_NO));//撞单是否第一次上报
                                            newtpc.setDate("HIT_ORDER_ARRIVE",new Date());//接收的时间
                                            if(oldtpc.getInteger("IS_TO_SHOP")==Integer.parseInt(DictCodeConstants.DICT_IS_NO)){
                                                newtpc.set("IS_TO_SHOP",Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                                newtpc.setDate("TIME_TO_SHOP",new Date());
                                            }
                                            newtpc.saveIt();
                                        
                                    }
                                }
                              //3. DMS直接新增官网客户的意向车型信息，包括：车型信息（品牌、车系、车型、配置、颜色、内饰），
                                //”是否商城意向车型”为”是”，并且该商城意向不可编辑、不可删除，并且"是否主要车型"为"是"；并将原有意向的"是否只要车型"更新为否
//                                  意向品牌和意向车系必须有,同时把这个客户名下其他主意向修改为子意向
                                Long intentId=null;
                                if(!StringUtils.isNullOrEmpty(tecoPO.getString("INTENT_BRAND"))&&!StringUtils.isNullOrEmpty(tecoPO.getString("INTENT_SERIES"))){
                                    if(StringUtils.isNullOrEmpty(oldtpc.getLong("INTENT_ID"))){
//                                      新增潜客意向
                                        TtCusIntentPO intentNewpo = new TtCusIntentPO();
                                        intentNewpo.setString("DEALER_CODE",dealerCode);
                                        intentId = commonNoService.getId("ID");
                                        
                                        intentNewpo.setLong("INTENT_ID",intentId);//意向id
                                        intentNewpo.setInteger("D_KEY",Integer.parseInt(DictCodeConstants.D_KEY));
                                        intentNewpo.setString("CUSTOMER_NO",oldtpc.getString("CUSTOMER_NO"));
                                        intentNewpo.setString("CREATED_BY","1");
                                        intentNewpo.setDate("CREATED_AT",new Date());
                                        intentNewpo.setString("DEALER_CODE",dealerCode);
                                        intentNewpo.saveIt();
                                    }else{
                                        intentId=oldtpc.getLong("INTENT_ID");
                                    }
                                  //4.同时把这个客户名下其他主意向修改为子意向
                                    List<Object> olddetailsList = new ArrayList<Object>();
                                    olddetailsList.add(intentId);
                                    olddetailsList.add(Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                    olddetailsList.add(dealerCode);
                                    olddetailsList.add(DictCodeConstants.D_KEY);
                                    List<TtCustomerIntentDetailPO> olddetails = TtCustomerIntentDetailPO.findBySQL("select * from tt_customer_intent_detail where INTENT_ID= ? and IS_MAIN_MODEL= ?  AND DEALER_CODE= ? AND D_KEY= ? ", olddetailsList.toArray());
                                     if(olddetails!=null&&olddetails.size()>0){
                                         for(int k=0;k<olddetails.size();k++){
                                             TtCustomerIntentDetailPO  olddetail=olddetails.get(k);
                                             olddetail.setInteger("IS_MAIN_MODEL",Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                                             olddetail.saveIt();
                                         }
                                     }
                                   //如果这个客户名下的意向里面已经有这个官网订单的意思，那么删除掉
                                     List<Object> olddetail1sList = new ArrayList<Object>();
                                     olddetail1sList.add(intentId);
                                     olddetail1sList.add(Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                     olddetail1sList.add(dealerCode);
                                     olddetail1sList.add(DictCodeConstants.D_KEY);
                                     olddetail1sList.add(vo.getEcOrderNo());
                                     List<TtCustomerIntentDetailPO> olddetail1s = TtCustomerIntentDetailPO.findBySQL("select * from TT_CUSTOMER_INTENT_DETAIL where INTENT_ID= ? and IS_ECO_INTENT_MODEL= ?  AND DEALER_CODE= ? AND D_KEY= ? AND EC_ORDER_NO= ?", olddetail1sList.toArray());
                                     if(olddetail1s!=null&&olddetail1s.size()>0){
                                         for(int z=0;z<olddetail1s.size();z++){
                                             TtCustomerIntentDetailPO olddetail1=olddetail1s.get(z);
                                             olddetail1.delete();
                                         }
                                     }
                                     //                      新增潜客意向明细表
                                     TtCustomerIntentDetailPO adddetail =new TtCustomerIntentDetailPO();
                                     adddetail.setString("DEALER_CODE",vo.getEntityCode());
                                    
                                     adddetail.setLong("INTENT_ID",intentId);
                                     adddetail.setString("INTENT_BRAND",vo.getBrandCode());//意向品牌
                                     adddetail.setString("INTENT_SERIES",vo.getSeriesCode());//又想你车系
                                     if(!StringUtils.isNullOrEmpty(vo.getModelCode()))
                                     adddetail.setString("INTENT_MODEL",vo.getModelCode());//意向车型
                                     if(!StringUtils.isNullOrEmpty(vo.getConfigCode()))
                                     adddetail.setString("INTENT_CONFIG",vo.getConfigCode());//意向配置
                                     if(!StringUtils.isNullOrEmpty(vo.getColorCode()))
                                     adddetail.setString("INTENT_COLOR",vo.getColorCode());//意向颜色
                                     //是否官网意向
                                     adddetail.setInteger("IS_MAIN_MODEL",Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                     adddetail.setInteger("IS_ECO_INTENT_MODEL",Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                     //零售金融文本
                                     if(!StringUtils.isNullOrEmpty(vo.getRetailFinancial()))
                                     adddetail.setString("RETAIL_FINANCE",vo.getRetailFinancial());
                                     adddetail.setDate("DETERMINED_TIME",vo.getDepositDate());//下定日期
                                     adddetail.setString("EC_ORDER_NO",vo.getEcOrderNo());//官网订单号
                                     if(!StringUtils.isNullOrEmpty(vo.getDepositAmount()))
                                     adddetail.setFloat("DEPOSIT_AMOUNT",vo.getDepositAmount());//
                                     adddetail.setString("CREATED_BY","1");
                                     adddetail.setDate("CREATED_AT",new Date());
                                     adddetail.setString("DEALER_CODE",dealerCode);
                                     adddetail.setInteger("D_KEY",Integer.parseInt(DictCodeConstants.D_KEY));
                                     adddetail.saveIt();
                                     //
                                }
                              //4.新增DMS联系人信息,首先把原有的默认联系人修改为否
                                List<Object> linkList = new ArrayList<Object>();
                                linkList.add(oldtpc.getString("CUSTOMER_NO"));
                                linkList.add(dealerCode);
                                linkList.add(DictCodeConstants.D_KEY);
                                linkList.add(Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                List<TtPoCusLinkmanPO> oldcustomerlinkmaninfos = TtPoCusLinkmanPO.findBySQL("select * from TT_PO_CUS_LINKMAN where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? AND IS_DEFAULT_CONTACTOR= ?", linkList.toArray());
                                if(oldcustomerlinkmaninfos!=null&&oldcustomerlinkmaninfos.size()>0){
                                    for(int d=0;d<oldcustomerlinkmaninfos.size();d++){
                                        TtPoCusLinkmanPO  oldcustomerlinkmaninfo=oldcustomerlinkmaninfos.get(d);
                                        oldcustomerlinkmaninfo.setInteger("IS_DEFAULT_CONTACTOR", Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                        oldcustomerlinkmaninfo.saveIt();
                                    }
                                }
                                TtPoCusLinkmanPO customerlinkmaninfo = new TtPoCusLinkmanPO();// 联系人信息PO
                                
                                customerlinkmaninfo.setString("DEALER_CODE",dealerCode);
                                //customerlinkmaninfo.setItemId(itemid);
                                customerlinkmaninfo.setString("CUSTOMER_NO", oldtpc.getString("CUSTOMER_NO"));
                                customerlinkmaninfo.setString("CONTACTOR_NAME",vo.getCustomerName());
                                if(!StringUtils.isNullOrEmpty(vo.getTel()))
                                customerlinkmaninfo.setString("MOBILE",vo.getTel());
                                customerlinkmaninfo.setInteger("IS_DEFAULT_CONTACTOR",Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                customerlinkmaninfo.setString("OWNED_BY",oldtpc.getString("OWNED_BY"));
                                customerlinkmaninfo.setString("CREATED_BY","1");
                                customerlinkmaninfo.setDate("CREATED_AT",new Date());
                                customerlinkmaninfo.setDate("DEALER_CODE",dealerCode);
                                customerlinkmaninfo.saveIt();// 新增联系人信息
                              //5.新增H级别客户的默认跟进信息
                                if(newtpcPo!=null&&newtpcPo.size()>0){
                                  //6.如果有未跟进的跟进记录，请先把未跟进的跟进的记录删除掉，在添加
                                    if(!StringUtils.isNullOrEmpty(dealerCode)&&!StringUtils.isNullOrEmpty(oldtpc.getString("CUSTOMER_NO"))){
                                        List<Object> salesPromotionList = new ArrayList<Object>();
                                        salesPromotionList.add(oldtpc.getString("CUSTOMER_NO"));
                                        salesPromotionList.add(dealerCode);  
                                        salesPromotionList.add(DictCodeConstants.D_KEY);
                                        List<TtSalesPromotionPlanPO> salesPromotionPO=TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where  CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ?  AND (PROM_RESULT IS NULL OR PROM_RESULT = 0)", salesPromotionList.toArray());
                                        if(salesPromotionPO!=null&&salesPromotionPO.size()>0){
                                            for(int a=0;a<salesPromotionPO.size();a++){
                                                TtSalesPromotionPlanPO salesPromotion = salesPromotionPO.get(a);
                                                salesPromotion.delete();
                                            }
                                        }
                                        
                                        //
                                    }
                                    String groupCodeTask = Utility.getGroupEntity(dealerCode, "TM_TRACKING_TASK");
                                    List<Object> task2 = new ArrayList<Object>();
                                    task2.add(newtpcPo.get(0).getInteger("INTENT_LEVEL"));
                                    //task2.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
                                    task2.add(DictCodeConstants.IS_YES);
                                    task2.add(groupCodeTask);
                                    List<TrackingTaskPO> taskPO2=TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ?  AND IS_VALID= ? AND DEALER_CODE= ? ", task2.toArray());
                                    if(taskPO2!=null&&taskPO2.size()>0){

                                        Calendar c = Calendar.getInstance();
                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                        for (int jj = 0; jj < taskPO2.size(); jj++) {
                                            TrackingTaskPO tt2 = (TrackingTaskPO) taskPO2.get(jj);
                                            String dates = new String();
                                            if (tt2.getInteger("INTERVAL_DAYS") != null && !"".equals(tt2.getInteger("INTERVAL_DAYS")) && !tt2.getInteger("INTERVAL_DAYS").equals("0")) {
                                                c.setTime(new Date());
                                                c.add(c.DAY_OF_WEEK, tt2.getInteger("INTERVAL_DAYS"));
                                                dates = format.format(c.getTime()).toString();
                                            }
                                    
                                    TtSalesPromotionPlanPO spro = new TtSalesPromotionPlanPO();
                                    //Long salesitemid = POFactory.getLongPriKey(conn, spro);
                                    spro.setString("DEALER_CODE",dealerCode);
                                    //spro.setItemId(salesitemid);
                                    spro.setLong("INTENT_ID",intentId);
                                    spro.setString("CUSTOMER_NO",oldtpc.getString("CUSTOMER_NO"));
                                    spro.setString("CUSTOMER_NAME",newtpcPo.get(0).getString("CUSTOMER_NAME"));
                                    // 促进前级别
                                    spro.setLong("TASK_ID",tt2.getLong("TASK_ID"));
                                    spro.setInteger("PRIOR_GRADE",tt2.getInteger("INTENT_LEVEL"));

                                    if (dates != null && !"".equals(dates)) {
                                        spro.setDate("SCHEDULE_DATE",format.parse(dates));
                                    }
                                    // 跟进任务中的任务内容添加到跟进活动内容
                                    if (tt2.getString("TASK_CONTENT") != null && !"".equals(tt2.getString("TASK_CONTENT"))) {
                                        spro.setString("PROM_CONTENT",tt2.getString("TASK_CONTENT"));
                                    }
                                    // 跟进任务中的执行方式添加到跟进活动跟进方式
                                    if (!StringUtils.isNullOrEmpty(tt2.getInteger("EXECUTE_TYPE"))) {
                                        spro.set("PROM_WAY",tt2.getInteger("EXECUTE_TYPE"));
                                    }
                                    // 创建方式为系统创建
                                    spro.set("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                                    // 联系人
                                            spro.setString("CONTACTOR_NAME",oldtpc.getString("CUSTOMER_NAME"));
//                                              ADD BY LNY 判断电话或手机中带“-”的，则存入电话字段，否则存入手机字段
                                            if(!StringUtils.isNullOrEmpty(oldtpc.getString("CONTACTOR_PHONE"))){
                                                if(oldtpc.getString("CONTACTOR_PHONE").indexOf("-")!=-1) 
                                                    spro.setString("PHONE",oldtpc.getString("CONTACTOR_PHONE"));
                                                else
                                                    spro.setString("MOBILE",oldtpc.getString("CONTACTOR_PHONE"));
                                            }
                                            if(!StringUtils.isNullOrEmpty(oldtpc.getString("CONTACTOR_MOBILE"))){
                                                if(oldtpc.getString("CONTACTOR_MOBILE").indexOf("-")!=-1 && "".equals(spro.getString("PHONE"))) 
                                                    spro.setString("PHONE",oldtpc.getString("CONTACTOR_MOBILE"));
                                                else
                                                    spro.setString("MOBILE",oldtpc.getString("CONTACTOR_MOBILE"));
                                            }
                                            //spro.setPhone(updatepo.getContactorPhone());
                                            //spro.setMobile(updatepo.getContactorMobile());
                                    spro.set("IS_AUDITING",Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                                    spro.setString("CREATED_BY","1");
                                    spro.setDate("CREATED_AT",new Date());
                                    spro.setString("DEALER_CODE",dealerCode);
                                    spro.saveIt();
                                        }
                                        
                                    }
                                    
                                }
                                //
                            }
                            
                            
                        }else{
//                          一、校验DMS是否存在官网客户手机的潜客，如果不存在：
                    //      （1）在DMS新增官网订单表，保存LMS下发的官网客户和订单信息，订单状态为有效
                    //      3.判断官网订单号在这家经销商是否存在，存在线删除
                            List<Object> querytecoPOList = new ArrayList<Object>();
                            querytecoPOList.add(vo.getEcOrderNo());
                            querytecoPOList.add(vo.getTel());
                            querytecoPOList.add(dealerCode);
                            querytecoPOList.add(DictCodeConstants.D_KEY);
                            boolean b=false;
                            List<TtEsCustomerOrderPO> querytecoPOs = TtEsCustomerOrderPO.findBySQL("select * from TT_ES_CUSTOMER_ORDER where EC_ORDER_NO= ? AND CONTACTOR_MOBILE= ?  AND DEALER_CODE= ? AND D_KEY= ? ", querytecoPOList.toArray());
                            if(querytecoPOs!=null&&querytecoPOs.size()>0){
                                TtEsCustomerOrderPO querytecoPO =querytecoPOs.get(0);
                              //如果存在官网订单号，并且不是取消状态，那么跳过
                                if(querytecoPO.getInteger("ESC_ORDER_STATUS")!=16191003){
                                    logger.debug(querytecoPO.getString("EC_ORDER_NO")+"官网订单号在DMS已存在！");
                                    continue;
                                }else{
                                  //如果是取消状态，有可能业务是变更提车经销商代码，那么删除做新增
                                    querytecoPO.delete();
                                }
                                
                            }
                            String customeNO= commonNoService.getSystemOrderNo(CommonConstants.POTENTIAL_CUSTOMER_PREFIX);
                            TtEsCustomerOrderPO tecoPO=new TtEsCustomerOrderPO();
                            if(!StringUtils.isNullOrEmpty(vo.getBrandCode())){
                                tecoPO.setString("INTENT_BRAND", vo.getBrandCode());
                            }
                            if(!StringUtils.isNullOrEmpty(vo.getSeriesCode())){
                                tecoPO.setString("INTENT_SERIES",vo.getSeriesCode());
                            }
                            if(!StringUtils.isNullOrEmpty(vo.getModelCode())){
                                tecoPO.setString("INTENT_MODEL", vo.getModelCode());
                            }
                            if(!StringUtils.isNullOrEmpty(vo.getConfigCode())){
                                tecoPO.setString("INTENT_CONFIG",vo.getConfigCode());
                            }
                            if(!StringUtils.isNullOrEmpty(vo.getColorCode())){
                                tecoPO.setString("INTENT_COLOR", vo.getColorCode());
                            }
                            if(!StringUtils.isNullOrEmpty(vo.getTrimCode())){
                                tecoPO.setString("INTENT_TRIM",vo.getTrimCode());
                            }
                            if(!StringUtils.isNullOrEmpty(vo.getModelYear())){
                                tecoPO.setString("MODEL_YEAR", vo.getModelYear());
                            }
                            if(!StringUtils.isNullOrEmpty(vo.getRetailFinancial())){
                                tecoPO.setString("RETAIL_FINANCE",vo.getRetailFinancial());
                            }
                            if(!StringUtils.isNullOrEmpty(vo.getDepositAmount())){
                                tecoPO.setFloat("DEPOSIT_AMOUNT",vo.getDepositAmount());
                            }
                            tecoPO.setDate("DETERMINED_TIME",vo.getDepositDate());//下定时间
                            tecoPO.setString("EC_ORDER_NO",vo.getEcOrderNo());//官网订单号
                            if(!StringUtils.isNullOrEmpty(vo.getIdCard())){
                                tecoPO.setString("CT_CODE",Integer.parseInt(DictCodeConstants.DICT_CERTIFICATE_TYPE_IDENTITY_CARD));//证件类型
                                tecoPO.setString("CERTIFICATE_NO",vo.getIdCard());//身份证号码
                            }
                            if (vo.getEcOrderType()==16181002){
                                tecoPO.setInteger("ESC_ORDER_STATUS",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_STATUS_VALID));//官网订单状态，有效
                                tecoPO.setInteger("ESC_TYPE",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_TYPE_FUTURES));//官网订单类型 期货
                            }else{
                                tecoPO.setInteger("ESC_ORDER_STATUS",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_STATUS_VALID));//官网订单状态，有效
                                tecoPO.setInteger("ESC_TYPE",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_TYPE_STOCK));//官网订单类型 现货
                            }
                            tecoPO.setString("CONTACTOR_MOBILE", vo.getTel());
                            if(!StringUtils.isNullOrEmpty(vo.getCustomerName()))
                            tecoPO.setString("CUSTOMER_NAME", vo.getCustomerName());
                            
                           // tecoPO.setString("CUSTOMER_NO", oldtpc.getString("CUSTOMER_NO"));
                            tecoPO.setString("DEALER_CODE", dealerCode);
                            tecoPO.setString("CREATED_BY", 1);
                            tecoPO.setDate("CREATED_AT", new Date());
                            tecoPO.setString("CUSTOMER_NO", customeNO);
                            tecoPO.saveIt();
                            //          2)DMS潜客表中新增官网客户信息，包括：客户姓名、客户手机、客户来源(“官网客户”)、客户级别(默认为H级)
                            PotentialCusPO addepo= new PotentialCusPO();
                            addepo.setDate("VALIDITY_BEGIN_DATE",new Date());
                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            System.out.println("日期"+format1.format(new Date()));
                            addepo.setString("FOUND_DATE",  format1.format(new Date()));
                          
                            addepo.setString("DEALER_CODE",dealerCode);
                            
                            System.out.println(customeNO+"-----------------");
                            addepo.setString("CUSTOMER_NO",customeNO);
                            addepo.setInteger("D_KEY",Integer.parseInt(DictCodeConstants.D_KEY));
                            if(!StringUtils.isNullOrEmpty(vo.getCustomerName()))
                            addepo.setString("CUSTOMER_NAME",vo.getCustomerName());//客户姓名
                            if(!StringUtils.isNullOrEmpty(vo.getIdCard())){
                                addepo.setInteger("CT_CODE",Integer.parseInt(DictCodeConstants.DICT_CERTIFICATE_TYPE_IDENTITY_CARD));//证件类型
                                addepo.setString("CERTIFICATE_NO",vo.getIdCard());//身份证号码
                            }
                            addepo.setInteger("INIT_LEVEL",Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_H));//官网客户初始级别H
                            addepo.setInteger("IntentLevel",Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_H));//官网客户意向级别H
                    //      ADD BY LNY 判断电话或手机中带“-”的，则存入电话字段，否则存入手机字段
                            if(!StringUtils.isNullOrEmpty(vo.getTel())){
                                if(vo.getTel().indexOf("-")!=-1) 
                                    addepo.setString("CONTACTOR_PHONE",vo.getTel());//联系电话
                                else//联系手机
                                    addepo.setString("CONTACTOR_MOBILE",vo.getTel());
                            }
                            addepo.setInteger("CUS_SOURCE",13111021);//官网客户
                            addepo.setInteger("CUSTOMER_STATUS",Integer.parseInt(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));//潜客
                            addepo.setInteger("CUSTOMER_TYPE",Integer.parseInt(DictCodeConstants.DICT_CUSTOMER_TYPE_INDIVIDUAL));//个人
                            addepo.setString("CREATED_BY","1");
                            addepo.setDate("CREATED_AT",new Date());
                            addepo.setString("EC_ORDER_NO",vo.getEcOrderNo());//官网订单号
                            addepo.setInteger("ESC_TYPE",tecoPO.getInteger("ESC_TYPE"));//官网订单类型：期货和现货
                            addepo.setInteger("ESC_ORDER_STATUS",tecoPO.getInteger("ESC_ORDER_STATUS"));//官网订单状态：默认为有效
                            addepo.setInteger("IS_TO_SHOP",Integer.parseInt(DictCodeConstants.DICT_IS_YES));//是否到店（默认为是）
                            addepo.setDate("TIME_TO_SHOP",new Date());
                            addepo.saveIt();
                            //3.修改原始的官网订单号的客户编号
                          //4.DMS直接新增官网客户的意向车型信息，包括：车型信息（品牌、车系、车型、配置、颜色、内饰）
                            //”是否商城意向车型”为”是”，并且该商城意向不可编辑、不可删除，并且"是否主要车型"为"是"
                            Long intentId=null;
                            //意向品牌和意向车系必须有
                            if(!StringUtils.isNullOrEmpty(vo.getBrandCode())&&!StringUtils.isNullOrEmpty(vo.getSeriesCode())){
                                TtCusIntentPO intentNewpo = new TtCusIntentPO();
                                intentNewpo.setString("DEALER_CODE",dealerCode);
                                intentId = commonNoService.getId("ID");
                                
                                intentNewpo.setLong("INTENT_ID",intentId);//意向id
                                intentNewpo.setInteger("D_KEY",Integer.parseInt(DictCodeConstants.D_KEY));
                                intentNewpo.setString("CUSTOMER_NO",customeNO);
                                intentNewpo.setString("CREATED_BY","1");
                                intentNewpo.setDate("CREATED_AT",new Date()); 
                                intentNewpo.saveIt();
                                
                                //                      新增潜客意向明细表
                                TtCustomerIntentDetailPO adddetail =new TtCustomerIntentDetailPO();
                                adddetail.setString("DEALER_CODE",vo.getEntityCode());
                               
                                adddetail.setLong("INTENT_ID",intentId);
                                adddetail.setString("INTENT_BRAND",vo.getBrandCode());//意向品牌
                                adddetail.setString("INTENT_SERIES",vo.getSeriesCode());//又想你车系
                                if(!StringUtils.isNullOrEmpty(vo.getModelCode()))
                                adddetail.setString("INTENT_MODEL",vo.getModelCode());//意向车型
                                if(!StringUtils.isNullOrEmpty(vo.getConfigCode()))
                                adddetail.setString("INTENT_CONFIG",vo.getConfigCode());//意向配置
                                if(!StringUtils.isNullOrEmpty(vo.getColorCode()))
                                adddetail.setString("INTENT_COLOR",vo.getColorCode());//意向颜色
                                //是否官网意向
                                adddetail.setInteger("IS_MAIN_MODEL",Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                adddetail.setInteger("IS_ECO_INTENT_MODEL",Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                //零售金融文本
                                if(!StringUtils.isNullOrEmpty(vo.getRetailFinancial()))
                                adddetail.setString("RETAIL_FINANCE",vo.getRetailFinancial());
                                adddetail.setDate("DETERMINED_TIME",vo.getDepositDate());//下定日期
                                adddetail.setString("EC_ORDER_NO",vo.getEcOrderNo());//官网订单号
                                if(!StringUtils.isNullOrEmpty(vo.getDepositAmount()))
                                adddetail.setFloat("DEPOSIT_AMOUNT",vo.getDepositAmount());//
                                adddetail.setString("CREATED_BY","1");
                                adddetail.setDate("CREATED_AT",new Date());
                                adddetail.setString("DEALER_CODE",dealerCode);
                                adddetail.setInteger("D_KEY",Integer.parseInt(DictCodeConstants.D_KEY));
                                adddetail.saveIt();  
                                
                            }
                            //
                            TtPoCusLinkmanPO customerlinkmaninfo = new TtPoCusLinkmanPO();// 联系人信息PO
                            
                            customerlinkmaninfo.setString("DEALER_CODE",dealerCode);
                            //customerlinkmaninfo.setItemId(itemid);
                            customerlinkmaninfo.setString("CUSTOMER_NO", addepo.getString("CUSTOMER_NO"));
                            customerlinkmaninfo.setString("CONTACTOR_NAME",vo.getCustomerName());
                            if(!StringUtils.isNullOrEmpty(vo.getTel()))
                            customerlinkmaninfo.setString("MOBILE",vo.getTel());
                            customerlinkmaninfo.setInteger("IS_DEFAULT_CONTACTOR",Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                            customerlinkmaninfo.setString("OWNED_BY",addepo.getString("OWNED_BY"));
                            customerlinkmaninfo.setString("CREATED_BY","1");
                            customerlinkmaninfo.setDate("CREATED_AT",new Date());
                            customerlinkmaninfo.setDate("DEALER_CODE",dealerCode);
                            customerlinkmaninfo.saveIt();// 新增联系人信息
                          //5.新增H级别客户的默认跟进信息
                            String groupCodeTask = Utility.getGroupEntity(dealerCode, "TM_TRACKING_TASK");
                            List<Object> task2 = new ArrayList<Object>();
                            task2.add(addepo.getInteger("INTENT_LEVEL"));
                            //task2.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
                            task2.add(DictCodeConstants.IS_YES);
                            task2.add(groupCodeTask);
                            List<TrackingTaskPO> taskPO2=TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ?  AND IS_VALID= ? AND DEALER_CODE= ? ", task2.toArray());
                            if(taskPO2!=null&&taskPO2.size()>0){

                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                for (int jj = 0; jj < taskPO2.size(); jj++) {
                                    TrackingTaskPO tt2 = (TrackingTaskPO) taskPO2.get(jj);
                                    String dates = new String();
                                    if (tt2.getInteger("INTERVAL_DAYS") != null && !"".equals(tt2.getInteger("INTERVAL_DAYS")) && !tt2.getInteger("INTERVAL_DAYS").equals("0")) {
                                        c.setTime(new Date());
                                        c.add(c.DAY_OF_WEEK, tt2.getInteger("INTERVAL_DAYS"));
                                        dates = format.format(c.getTime()).toString();
                                    }
                            
                            TtSalesPromotionPlanPO spro = new TtSalesPromotionPlanPO();
                            //Long salesitemid = POFactory.getLongPriKey(conn, spro);
                            spro.setString("DEALER_CODE",dealerCode);
                            //spro.setItemId(salesitemid);
                            spro.setLong("INTENT_ID",intentId);
                            spro.setString("CUSTOMER_NO",addepo.getString("CUSTOMER_NO"));
                            spro.setString("CUSTOMER_NAME",addepo.getString("CUSTOMER_NAME"));
                            // 促进前级别
                            spro.setLong("TASK_ID",tt2.getLong("TASK_ID"));
                            spro.setInteger("PRIOR_GRADE",tt2.getInteger("INTENT_LEVEL"));

                            if (dates != null && !"".equals(dates)) {
                                spro.setDate("SCHEDULE_DATE",format.parse(dates));
                            }
                            // 跟进任务中的任务内容添加到跟进活动内容
                            if (tt2.getString("TASK_CONTENT") != null && !"".equals(tt2.getString("TASK_CONTENT"))) {
                                spro.setString("PROM_CONTENT",tt2.getString("TASK_CONTENT"));
                            }
                            // 跟进任务中的执行方式添加到跟进活动跟进方式
                            if (!StringUtils.isNullOrEmpty(tt2.getInteger("EXECUTE_TYPE"))) {
                                spro.set("PROM_WAY",tt2.getInteger("EXECUTE_TYPE"));
                            }
                            // 创建方式为系统创建
                            spro.set("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                            // 联系人
                                    spro.setString("CONTACTOR_NAME",addepo.getString("CUSTOMER_NAME"));
//                                      ADD BY LNY 判断电话或手机中带“-”的，则存入电话字段，否则存入手机字段
                                    if(!StringUtils.isNullOrEmpty(addepo.getString("CONTACTOR_PHONE"))){
                                        if(addepo.getString("CONTACTOR_PHONE").indexOf("-")!=-1) 
                                            spro.setString("PHONE",addepo.getString("CONTACTOR_PHONE"));
                                        else
                                            spro.setString("MOBILE",addepo.getString("CONTACTOR_PHONE"));
                                    }
                                    if(!StringUtils.isNullOrEmpty(addepo.getString("CONTACTOR_MOBILE"))){
                                        if(addepo.getString("CONTACTOR_MOBILE").indexOf("-")!=-1 && "".equals(spro.getString("PHONE"))) 
                                            spro.setString("PHONE",addepo.getString("CONTACTOR_MOBILE"));
                                        else
                                            spro.setString("MOBILE",addepo.getString("CONTACTOR_MOBILE"));
                                    }
                                    //spro.setPhone(updatepo.getContactorPhone());
                                    //spro.setMobile(updatepo.getContactorMobile());
                            spro.set("IS_AUDITING",Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                            spro.setString("CREATED_BY","1");
                            spro.setDate("CREATED_AT",new Date());
                            spro.setString("DEALER_CODE",dealerCode);
                            spro.saveIt();
                                }
                                
                            }
                            
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

}
