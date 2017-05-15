
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalanceSalesPartServiceImpl.java
*
* @Author : ZhengHe
*
* @Date : 2016年11月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月10日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.balance;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtBalanceSalesPartPO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.balance.BalanceSalesPartDTO;

/**
* 结算销售配件明细
* @author ZhengHe
* @date 2016年11月10日
*/
@Service
public class BalanceSalesPartServiceImpl implements BalanceSalesPartService{

    
    /**
    * 新增结算单销售配件信息
    * @author ZhengHe
    * @date 2016年11月10日
    * @param bSPDto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceSalesPartService#addBalanceSalesPart(com.yonyou.dms.repair.domains.DTO.balance.BalanceSalesPartDTO)
    */
    	
    @Override
    public Long addBalanceSalesPart(BalanceSalesPartDTO bSPDto) throws ServiceBizException {
        TtBalanceSalesPartPO bSPPO=new TtBalanceSalesPartPO();
        setbSPPo(bSPPO, bSPDto);
        bSPPO.saveIt();
        return bSPPO.getLongId();
    }
    
    /**
    * 设置TtBalanceSalesPartPO
    * @author ZhengHe
    * @date 2016年11月10日
    * @param bSPPO
    * @param bSPDto
    */
    	
    public void setbSPPo(TtBalanceSalesPartPO bSPPO,BalanceSalesPartDTO bSPDto){
        bSPPO.setLong("BALANCE_ID", bSPDto.getBalanceId());
        bSPPO.setLong("ITEM_ID", bSPDto.getItemId());
        bSPPO.setLong("SALES_PART_ID", bSPDto.getSalesPartId());
        bSPPO.setString("STORAGE_CODE", bSPDto.getStorageCode());
        bSPPO.setString("STORAGE_POSITION_CODE",bSPDto.getStoragePositionCode());
        bSPPO.setString("PART_NO",bSPDto.getPartNo());
        bSPPO.setString("PART_NAME", bSPDto.getPartName());
        bSPPO.setDouble("PART_QUANTITY", bSPDto.getPartQuantity());
        bSPPO.setString("UNIT",bSPDto.getUnit());
        bSPPO.setDouble("DISCOUNT", bSPDto.getDiscount());
        bSPPO.setInteger("PRICE_TYPE", bSPDto.getPriceType());
        bSPPO.setDouble("PRICE_RATE", bSPDto.getPriceRate());
        bSPPO.setDouble("COST_PRICE",bSPDto.getCostPrice());
        bSPPO.setDouble("PART_SALES_PRICE",bSPDto.getPartSalesPrice());
        bSPPO.setDouble("PART_COST_AMOUNT",bSPDto.getPartCostAmount());
        bSPPO.setDouble("PART_SALES_AMOUNT",bSPDto.getPartSalesAmount());
        bSPPO.setDouble("SALES_AMOUNT",bSPDto.getSalesAmount());
    }
}
