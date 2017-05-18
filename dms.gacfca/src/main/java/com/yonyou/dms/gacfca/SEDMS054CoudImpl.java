
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SEDMS054Imp.java
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

import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SEDMS054DTO;
import com.yonyou.dms.common.domains.PO.basedata.TmMysteriousCustomerPO;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.impl.CommonNoServiceImpl;
import com.yonyou.dms.function.exception.ServiceBizException;


/**
 * 接受下发的神秘客户信息
* TODO description
* @author LiGaoqi
* @date 2017年4月19日
*/
@Service
public class SEDMS054CoudImpl implements SEDMS054Coud {
    @Autowired
    private CommonNoService     commonNoService;

    /**
     *  
    * @author LiGaoqi
    * @date 2017年4月19日
    * @param voList
    * @param dealerCode
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.gacfca.SEDMS054Coud#getSEDMS054(java.util.LinkedList, java.lang.String)
    */
    @Override
    public int getSEDMS054(LinkedList<SEDMS054DTO> voList, String dealerCode) throws ServiceBizException {
       try {
           if(voList !=null && voList.size()>0){
               for(int i=0;i<voList.size();i++){
                   SEDMS054DTO vo =new SEDMS054DTO();
                   vo = (SEDMS054DTO)voList.get(i);
                   TmMysteriousCustomerPO myCustomer =new TmMysteriousCustomerPO();
                   Long mysteriousId = commonNoService.getId("TM_MY_CUSTOMER");
                   
                   myCustomer.setString("DEALER_CODE",dealerCode);
                   myCustomer.setString("DEALER_NAME",vo.getDealerName());
                   myCustomer.setString("EXEC_NAME",vo.getExecAuthor());
                   myCustomer.setLong("MYSTERIOUS_ID",mysteriousId);
                   myCustomer.setString("Phone",vo.getPhone());
                   myCustomer.setString("D_KEY","0");
                   myCustomer.setString("CREATED_BY","1");
                   myCustomer.setDate("CREATED_AT",new Date());
                   myCustomer.setString("UPDATED_BY","1");
                   myCustomer.setDate("UPDATED_AT",new Date());       
                   myCustomer.saveIt();
               } 
           }
           
           return 1;
    } catch (Exception e) {
        return 0;
    }
   
    }

}
