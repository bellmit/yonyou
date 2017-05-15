
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalanceLabourServiceImpl.java
*
* @Author : ZhengHe
*
* @Date : 2016年10月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月10日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.balance;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtBalanceLabourPO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.balance.BalanceLabourDTO;

/**
* 结算单维修项目
* @author ZhengHe
* @date 2016年10月10日
*/
@Service
public class BalanceLabourServiceImpl implements BalanceLabourService{

    
    /**
    * 新增tt_balance_labour表
    * @author ZhengHe
    * @date 2016年10月10日
    * @param bLDto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceLabourService#addBalanceLabour(com.yonyou.dms.repair.domains.DTO.balance.BalanceLabourDTO)
    */
    	
    @Override
    public Long addBalanceLabour(BalanceLabourDTO bLDto) throws ServiceBizException {
       TtBalanceLabourPO bLPo=new TtBalanceLabourPO();
       setBLPo(bLPo, bLDto);
       bLPo.saveIt(); 
       return bLPo.getLongId();
    }
    
    
    /**
    * 设置TtBalanceLabourPO
    * @author ZhengHe
    * @date 2016年10月10日
    * @param bLPo
    * @param bLDto
    */
    	
    public void setBLPo(TtBalanceLabourPO bLPo,BalanceLabourDTO bLDto){
        bLPo.setLong("BALANCE_ID",bLDto.getBalanceId());
        bLPo.setLong("RO_LABOUR_ID",bLDto.getRoLabourId());
        bLPo.setInteger("REPAIR_TYPE_CODE",bLDto.getRepairTypeCode());
        bLPo.setString("CHARGE_PARTITION_CODE",bLDto.getChargePartitionCode());
        bLPo.setString("LABOUR_CODE",bLDto.getLabourCode());
        bLPo.setString("LABOUR_NAME",bLDto.getLabourName());
        bLPo.setString("LOCAL_LABOUR_CODE",bLDto.getLocalLabourCode());
        bLPo.setString("LOCAL_LABOUR_NAME",bLDto.getLocalLabourName());
        bLPo.setDouble("STD_LABOUR_HOUR",bLDto.getStdLabourHour());
        bLPo.setDouble("ASSIGN_LABOUR_HOUR",bLDto.getAssignLabourHour());
        bLPo.setDouble("LABOUR_PRICE",bLDto.getLabourPrice());
        bLPo.setDouble("LABOUR_AMOUNT",bLDto.getLabourAmount());
        bLPo.setDouble("DISCOUNT",bLDto.getDiscount());
        bLPo.setDouble("AFTER_DISCOUNT_AMOUNT",bLDto.getAfterDiscountAmount());
        bLPo.setString("TROUBLE_DESC",bLDto.getTroubleDesc());
        bLPo.setString("TECHNICIAN",bLDto.getTechnician());
        bLPo.setString("WORKER_TYPE_CODE",bLDto.getWorkerTypeCode());
        bLPo.setString("REMARK",bLDto.getRemark());
        bLPo.setInteger("ASSIGN_TAG",bLDto.getAssignTag());
        bLPo.setString("ACTIVITY_CODE",bLDto.getActivityCode());
        bLPo.setString("PACKAGE_CODE",bLDto.getPackageCode());
        bLPo.setString("MODEL_LABOUR_CODE",bLDto.getModelLabourCode());
    }

}
