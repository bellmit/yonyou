
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SADMS049Imp.java
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
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.DCSBI003CloudImpl;
import com.yonyou.dcs.gacfca.DMSTODCS049Cloud;
import com.yonyou.dms.DTO.gacfca.SADCS049Dto;
import com.yonyou.dms.DTO.gacfca.SADCS060Dto;
import com.yonyou.dms.common.domains.DTO.basedata.PotentialCusDTO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerIntentDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerVehicleListPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
 * 业务描述：上报：二手车置换意向明细数据推送接口（DMS——DCS）
* TODO description
* @author LiGaoqi
* @date 2017年4月19日
*/
@Service
public class SADMS049Imp implements SADMS049 {
	
	@Autowired DMSTODCS049Cloud DMSTODCS049Cloud;
    
    private static final Logger logger = LoggerFactory.getLogger(SADMS049Imp.class);

    /**
    * @author LiGaoqi
    * @date 2017年4月19日
    * @param cusDto
    * @param vehStatus
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.gacfca.SADMS049#getSADMS061(com.yonyou.dms.common.domains.DTO.basedata.PotentialCusDTO, java.lang.String)
    */

    @Override
    public int getSADMS049(List<TtCustomerVehicleListPO> listPo, String vehStatus,String cusNo) throws ServiceBizException {
        //vehStatus:POST新增。vehStatus：PUT修改
        // TODO Auto-generated method stub
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        PotentialCusPO potenti1 = PotentialCusPO.findByCompositeKeys(dealerCode,cusNo);
        LinkedList<SADCS049Dto> resultList = new LinkedList<SADCS049Dto>();
        logger.info("*************************** 开始 ******************************");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        boolean b =false;
        String msg="1";
        try {
        	System.err.println(listPo);
        	System.err.println(cusNo);
        	
            if(!StringUtils.isNullOrEmpty(potenti1)){
                if(!StringUtils.isNullOrEmpty(potenti1.getString("INTENT_LEVEL"))){
                    if(potenti1.getString("INTENT_LEVEL").equals(DictCodeConstants.DICT_INTENT_LEVEL_H)
                            ||potenti1.getString("INTENT_LEVEL").equals(DictCodeConstants.DICT_INTENT_LEVEL_A)
                            ||potenti1.getString("INTENT_LEVEL").equals(DictCodeConstants.DICT_INTENT_LEVEL_B)
                            ||potenti1.getString("INTENT_LEVEL").equals(DictCodeConstants.DICT_INTENT_LEVEL_C)
                            ||potenti1.getString("INTENT_LEVEL").equals(DictCodeConstants.DICT_INTENT_LEVEL_O)
                            ||potenti1.getString("INTENT_LEVEL").equals(DictCodeConstants.DICT_INTENT_LEVEL_D)){
                        b=true;
                        
                    }
                }

                List<Object> intentDetailList = new ArrayList<Object>();
                intentDetailList.add(potenti1.getLong("INTENT_ID"));
                intentDetailList.add(dealerCode);
                intentDetailList.add(DictCodeConstants.D_KEY);
                intentDetailList.add(DictCodeConstants.DICT_IS_YES);
                List<TtCustomerIntentDetailPO> intentDetailPo = TtCustomerIntentDetailPO.findBySQL("select * from TT_CUSTOMER_INTENT_DETAIL where INTENT_ID= ? AND DEALER_CODE= ? AND D_KEY= ? AND IS_MAIN_MODEL= ?", intentDetailList.toArray());
                TtCustomerIntentDetailPO detail =null;
                if(intentDetailPo!=null&&intentDetailPo.size()>0){
                    detail=intentDetailPo.get(0);
                }
                if(listPo!=null&&listPo.size()>0){
                    for(int i=0;i<listPo.size();i++){
                        TtCustomerVehicleListPO vDPo = listPo.get(i);
                        SADCS049Dto DVO = new SADCS049Dto();
                        DVO.setCustomerNo(cusNo);
                        DVO.setDealerCode(dealerCode);
                        DVO.setCustomerType(potenti1.getInteger("CUSTOMER_TYPE"));
                        DVO.setReportType(3);
                        DVO.setItemId(vDPo.getLong("ITEM_ID"));
                        if(!StringUtils.isNullOrEmpty(potenti1.getString("PROVIENCE"))){
                            DVO.setCustomerProvince(potenti1.getString("PROVIENCE"));
                        }
                        if(!StringUtils.isNullOrEmpty(potenti1.getString("CITY"))){
                            DVO.setCustomerCity(potenti1.getString("CITY"));
                        }
                        if(!StringUtils.isNullOrEmpty(detail)){
                            if(!StringUtils.isNullOrEmpty(detail.getString("INTENT_BRAND"))){
                                DVO.setIntentionBrandCode(detail.getString("INTENT_BRAND"));
                            }
                            if(!StringUtils.isNullOrEmpty(detail.getString("INTENT_SERIES"))){
                                DVO.setIntentionSeriesCode(detail.getString("INTENT_SERIES"));
                            }
                            if(!StringUtils.isNullOrEmpty(detail.getString("INTENT_MODEL"))){
                                DVO.setIntentionModelCode(detail.getString("INTENT_MODEL"));
                            }
                            if(!StringUtils.isNullOrEmpty(detail.getDate("CREATED_AT"))){
                                DVO.setIntentionDate(format.parse(format.format(detail.getDate("CREATED_AT"))));
                            }
                        }
                        DVO.setUsedCarBrandCode(vDPo.getString("BRAND_NAME"));
                        
                        DVO.setUsedCarSeriesCode(vDPo.getString("SERIES_NAME"));
                        //System.out.println(vSeriesName[i].toString());
                        DVO.setUsedCarModelCode(vDPo.getString("MODEL_NAME"));
                    
                        DVO.setUsedCarLicense(vDPo.getString("LICENSE"));
                        if(!StringUtils.isNullOrEmpty(vDPo.getDate("PURCHASE_DATE"))){
                            DVO.setUsedCarLicenseDate(format.parse(format.format(vDPo.getDate("PURCHASE_DATE"))));
                        }
                        DVO.setUsedCarVin(vDPo.getString("VIN"));
                        if(!StringUtils.isNullOrEmpty(vDPo.getDouble("ASSESSED_PRICE"))){
                            DVO.setUsedCarAssessAmount(vDPo.getDouble("ASSESSED_PRICE"));
                        }
                        if(!StringUtils.isNullOrEmpty(vDPo.getLong("MILEAGE"))){
                            DVO.setUsedCarMileage(vDPo.getLong("MILEAGE"));
                        }
                        DVO.setUsedCarDescribe(vDPo.getString("REMARK"));
                    if(!StringUtils.isNullOrEmpty(vDPo.getString("FILE_URLMESSAGE_A"))&&!StringUtils.isNullOrEmpty(vDPo.getString("FILE_URLMESSAGE_B"))&&!StringUtils.isNullOrEmpty(vDPo.getString("FILE_URLMESSAGE_C")))
                    {
                        resultList.add(DVO);
                    }
                        
                    }
                }
                List<Object> keepList = new ArrayList<Object>();
                keepList.add(cusNo);
                keepList.add(FrameworkUtil.getLoginInfo().getDealerCode());
                List<TtCustomerVehicleListPO> keepCarPo = TtCustomerVehicleListPO.findBySQL("select * from TT_CUSTOMER_VEHICLE_LIST where CUSTOMER_NO= ? AND DEALER_CODE= ? ", keepList.toArray());
                if(keepCarPo!=null&&keepCarPo.size()>0){
                    for(int j=0;j<keepCarPo.size();j++){

                        TtCustomerVehicleListPO vDPo = keepCarPo.get(j);
                        System.err.println(vDPo);
                        SADCS049Dto DVO = new SADCS049Dto();
                        DVO.setCustomerNo(cusNo);
                        DVO.setDealerCode(dealerCode);
                        DVO.setCustomerType(potenti1.getInteger("CUSTOMER_TYPE"));
                        DVO.setReportType(1);
                        DVO.setItemId(vDPo.getLong("ITEM_ID"));
                        if(!StringUtils.isNullOrEmpty(potenti1.getString("PROVIENCE"))){
                            DVO.setCustomerProvince(potenti1.getString("PROVIENCE"));
                        }
                        if(!StringUtils.isNullOrEmpty(potenti1.getString("CITY"))){
                            DVO.setCustomerCity(potenti1.getString("CITY"));
                        }
                        if(!StringUtils.isNullOrEmpty(detail)){
                            if(!StringUtils.isNullOrEmpty(detail.getString("INTENT_BRAND"))){
                                DVO.setIntentionBrandCode(detail.getString("INTENT_BRAND"));
                            }
                            if(!StringUtils.isNullOrEmpty(detail.getString("INTENT_SERIES"))){
                                DVO.setIntentionSeriesCode(detail.getString("INTENT_SERIES"));
                            }
                            if(!StringUtils.isNullOrEmpty(detail.getString("INTENT_MODEL"))){
                                DVO.setIntentionModelCode(detail.getString("INTENT_MODEL"));
                            }
                            if(!StringUtils.isNullOrEmpty(detail.getDate("CREATED_AT"))){
                                DVO.setIntentionDate(format.parse(format.format(detail.getDate("CREATED_AT"))));
                            }
                        }
                        DVO.setUsedCarBrandCode(vDPo.getString("BRAND_NAME"));
                        
                        DVO.setUsedCarSeriesCode(vDPo.getString("SERIES_NAME"));
                        //System.out.println(vSeriesName[i].toString());
                        DVO.setUsedCarModelCode(vDPo.getString("MODEL_NAME"));
                    
                        DVO.setUsedCarLicense(vDPo.getString("LICENSE"));
                        if(!StringUtils.isNullOrEmpty(vDPo.getDate("PURCHASE_DATE"))){
                            DVO.setUsedCarLicenseDate(format.parse(format.format(vDPo.getDate("PURCHASE_DATE"))));
                        }
                        DVO.setUsedCarVin(vDPo.getString("VIN"));
                        if(!StringUtils.isNullOrEmpty(vDPo.getDouble("ASSESSED_PRICE"))){
                            DVO.setUsedCarAssessAmount(vDPo.getDouble("ASSESSED_PRICE"));
                        }
                        if(!StringUtils.isNullOrEmpty(vDPo.getLong("MILEAGE"))){
                            DVO.setUsedCarMileage(vDPo.getLong("MILEAGE"));
                        }
                        DVO.setUsedCarDescribe(vDPo.getString("REMARK"));
                    if(!StringUtils.isNullOrEmpty(vDPo.getString("FILE_URLMESSAGE_A"))&&!StringUtils.isNullOrEmpty(vDPo.getString("FILE_URLMESSAGE_B"))&&!StringUtils.isNullOrEmpty(vDPo.getString("FILE_URLMESSAGE_C")))
                    {
                        resultList.add(DVO);
                    }
                    }
                }
                
            
                //if(b){}
           
            }
            logger.info("=============================开始调用DMSTODCS049Cloud===============================");
            if(resultList!=null&&resultList.size()>0)
            msg=DMSTODCS049Cloud.handleExecutor(resultList);
        } catch (Exception e) {
            // TODO: handle exception
            return 0;
        }
        logger.info("*************************** 结束 ******************************");
        
        return Integer.parseInt(msg);
        
    }

}
