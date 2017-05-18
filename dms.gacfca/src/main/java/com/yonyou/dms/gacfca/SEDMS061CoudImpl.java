
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SEDMS061Impl.java
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
	
package com.yonyou.dms.gacfca;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infoeai.eai.wsClient.bsuv.lms.LMSOrderServicePortWeberviceThread;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
* TODO description
* @author LiGaoqi
* @date 2017年5月8日
*/
@Service
public class SEDMS061CoudImpl implements SEDMS061Coud {
    private static final Logger logger = LoggerFactory.getLogger(SEDMS061CoudImpl.class);

    /**
    * @author LiGaoqi
    * @date 2017年5月8日
    * @param soNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.gacfca.SEDMS061Coud#getSEDMS061(java.lang.String)
    */

    @Override
    public int getSEDMS061(String soNo) throws ServiceBizException {
        try {
           String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode(); 
            SalesOrderPO tsoPOs=SalesOrderPO.findByCompositeKeys(dealerCode,soNo);
            if(!StringUtils.isNullOrEmpty(tsoPOs)&&!StringUtils.isNullOrEmpty(tsoPOs.getString("EC_ORDER"))&&DictCodeConstants.DICT_IS_YES.equals(tsoPOs.getString("EC_ORDER"))){
                if(!StringUtils.isNullOrEmpty(tsoPOs.getInteger("DELIVERY_MODE_ELEC"))&&(tsoPOs.getInteger("DELIVERY_MODE_ELEC")==16001001||tsoPOs.getInteger("DELIVERY_MODE_ELEC")==16001002)){
                    logger.debug("----webservice开始调用---");
                    LMSOrderServicePortWeberviceThread thread =new LMSOrderServicePortWeberviceThread();
                    thread.setEntityCode(dealerCode);
                    thread.setEcOrderNo(tsoPOs.getString("EC_ORDER_NO"));
                    thread.setSoNo(soNo);
                    thread.setCustomerNo(tsoPOs.getString("CUSTOMER_NO"));
                    thread.start();
                    logger.debug("----webservice调用结束---"); 
                }
            }
        } catch (Exception e) {
            return 0;
        }
        // TODO Auto-generated method stub
        return 1;
    }

}
