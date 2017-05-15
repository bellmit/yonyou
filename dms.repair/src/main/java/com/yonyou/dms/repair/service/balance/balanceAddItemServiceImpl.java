
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : balanceAddItemServiceImpl.java
*
* @Author : ZhengHe
*
* @Date : 2016年10月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月11日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.balance;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtBalanceAddItemPO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.balance.BalanceAddItemDTO;

/**
* 结算单附加项目
* @author ZhengHe
* @date 2016年10月11日
*/
@Service
public class balanceAddItemServiceImpl implements balanceAddItemService{

    
    /**
    * 新增结算单附加项目信息
    * @author ZhengHe
    * @date 2016年10月11日
    * @param bAIDto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.balanceAddItemService#addBalanceAddItem(com.yonyou.dms.repair.domains.DTO.balance.BalanceAddItemDTO)
    */
    	
    @Override
    public Long addBalanceAddItem(BalanceAddItemDTO bAIDto) throws ServiceBizException {
        TtBalanceAddItemPO bAIPo=new TtBalanceAddItemPO();
        setBAIPo(bAIPo, bAIDto);
        bAIPo.saveIt();
        return bAIPo.getLongId();
    }
    
    
    /**
    * 设置TtTtBalanceAddItemPO
    * @author ZhengHe
    * @date 2016年10月11日
    * @param bAIPo
    * @param bAIDto
    */
    	
    public void setBAIPo(TtBalanceAddItemPO bAIPo,BalanceAddItemDTO bAIDto){
        bAIPo.setLong("BALANCE_ID",bAIDto.getBalanceId());
        bAIPo.setLong("RO_ADD_ITEM_ID",bAIDto.getRoAddItemId());
        bAIPo.setString("CHARGE_PARTITION_CODE",bAIDto.getChargePartitionCode());
        bAIPo.setString("ADD_ITEM_CODE",bAIDto.getAddItemCode());
        bAIPo.setString("ADD_ITEM_NAME",bAIDto.getAddItemName());
        bAIPo.setDouble("ADD_ITEM_AMOUNT",bAIDto.getAddItemAmount());
        bAIPo.setDouble("RECEIVABLE_AMOUNT",bAIDto.getReceivableAmount());
        bAIPo.setString("REMARK",bAIDto.getRemark());
    }

}
