
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : balancePayobjServiceImpl.java
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

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.NumberUtil;
import com.yonyou.dms.repair.domains.DTO.balance.BalancePayobjDTO;
import com.yonyou.dms.repair.domains.PO.balance.BalancePayobjPO;

/**
* 结算单收费对象
* @author ZhengHe
* @date 2016年10月11日
*/
@Service
public class balancePayobjServiceImpl implements balancePayobjService{

    
    /**
    * 新增结算单收费对象信息
    * @author ZhengHe
    * @date 2016年10月11日
    * @param bPODto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.balancePayobjService#addBalancePayobj(com.yonyou.dms.repair.domains.DTO.balance.BalancePayobjDTO)
    */
    	
    @Override
    public Long addBalancePayobj(BalancePayobjDTO bPODto) throws ServiceBizException {
        BalancePayobjPO bPOPo=new BalancePayobjPO();
        BigDecimal notReceivedAmount=NumberUtil.sub(new BigDecimal(bPODto.getReceivableAmount().toString()),new BigDecimal(bPODto.getReceivedAmount()==null?"0":bPODto.getReceivedAmount().toString()));
        bPODto.setNotReceivedAmount(notReceivedAmount.doubleValue());
        setBalancePayobjPO(bPOPo, bPODto);
        bPOPo.saveIt();
        return bPOPo.getLongId();
    }

    
    /**
    * 设置BalancePayobjPO
    * @author ZhengHe
    * @date 2016年10月11日
    * @param bPOPo
    * @param bPODto
    */
    	
    public void setBalancePayobjPO( BalancePayobjPO bPOPo,BalancePayobjDTO bPODto){
        bPOPo.setLong("BALANCE_ID", bPODto.getBalanceId());
        bPOPo.setInteger("PAYMENT_OBJECT_TYPE", bPODto.getPaymentObjectType());
        bPOPo.setString("PAYMENT_OBJECT_CODE", bPODto.getPaymentObjectCode());
        bPOPo.setString("PAYMENT_OBJECT_NAME", bPODto.getPaymentObjectName());
        bPOPo.setDouble("DIS_AMOUNT", bPODto.getDisAmount());
        bPOPo.setDouble("SUB_OBB_AMOUNT", bPODto.getSubObbAmount());
        bPOPo.setDouble("RECEIVABLE_AMOUNT", bPODto.getReceivableAmount());
        bPOPo.setDouble("RECEIVED_AMOUNT", bPODto.getReceivedAmount());
        bPOPo.setDouble("NOT_RECEIVED_AMOUNT", bPODto.getNotReceivedAmount());
        bPOPo.setDouble("DERATE_AMOUNT", bPODto.getDerateAmount());
        bPOPo.setDouble("TAX", bPODto.getTax());
        bPOPo.setDouble("TAX_AMOUNT", bPODto.getTaxAmount());
        bPOPo.setInteger("PAY_OFF", bPODto.getPayOff());
        bPOPo.setInteger("INVOICE_TAG", bPODto.getInvoiceTag());
        bPOPo.setInteger("IS_RED", bPODto.getIsRed());
    }
}
