
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalanceRepairPartServiceImpl.java
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

import com.yonyou.dms.common.domains.PO.basedata.TtBalanceRepairPartPO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.balance.BalanceRepairPartDTO;

/**
*  结算单维修配件
* @author ZhengHe
* @date 2016年10月10日
*/
@Service
public class BalanceRepairPartServiceImpl implements BalanceRepairPartService{

    
    /**
    * 新增结算单维修配件信息
    * @author ZhengHe
    * @date 2016年10月10日
    * @param bRPDto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceRepairPartService#addBalanceRepairPart(com.yonyou.dms.repair.domains.DTO.balance.BalanceRepairPartDTO)
    */
    	
    @Override
    public Long addBalanceRepairPart(BalanceRepairPartDTO bRPDto) throws ServiceBizException {
        TtBalanceRepairPartPO bRPPo=new TtBalanceRepairPartPO();
        setbRPPo(bRPPo, bRPDto);
        bRPPo.saveIt();
        return bRPPo.getLongId();
    }

    
    /**
    * 设置TtBalanceRepairPartPO
    * @author ZhengHe
    * @date 2016年10月10日
    * @param bRPPo
    * @param bRPDto
    */
    	
    public void setbRPPo(TtBalanceRepairPartPO bRPPo,BalanceRepairPartDTO bRPDto){
        bRPPo.setLong("BALANCE_ID", bRPDto.getBalanceId());
        bRPPo.setLong("BALANCE_LABOUR_ID", bRPDto.getBalanceLabourId());
        bRPPo.setString("STORAGE_CODE", bRPDto.getStorageCode());
        bRPPo.setString("STORAGE_POSITION_CODE", bRPDto.getStoragePositionCode());
        bRPPo.setString("PART_NO", bRPDto.getPartNo());
        bRPPo.setString("PART_NAME", bRPDto.getPartName());
        bRPPo.setString("CHARGE_PARTITION_CODE", bRPDto.getChargePartitionCode());
        bRPPo.setString("UNIT", bRPDto.getUnit());
        bRPPo.setInteger("IS_MAIN_PART", bRPDto.getIsMainPart());
        bRPPo.setDouble("PART_QUANTITY", bRPDto.getPartQuantity());
        bRPPo.setDouble("PRICE_RATE", bRPDto.getPriceRate());
        bRPPo.setDouble("PART_COST_PRICE", bRPDto.getPartCostPrice());
        bRPPo.setDouble("PART_COST_AMOUNT", bRPDto.getPartCostAmount());
        bRPPo.setDouble("PART_SALES_PRICE", bRPDto.getPartSalesPrice());
        bRPPo.setDouble("PART_SALES_AMOUNT", bRPDto.getPartSalesAmount());
        bRPPo.setDouble("DISCOUNT", bRPDto.getDiscount());
        bRPPo.setDouble("AFTER_DISCOUNT_AMOUNT", bRPDto.getAfterDiscountAmount());
    }
}
