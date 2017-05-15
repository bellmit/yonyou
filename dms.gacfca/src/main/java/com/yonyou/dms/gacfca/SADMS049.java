
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SADMS049.java
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

import java.util.List;

import com.yonyou.dms.common.domains.PO.basedata.TtCustomerVehicleListPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：上报：二手车置换意向明细数据推送接口（DMS——DCS）
* TODO description
* @author LiGaoqi
* @date 2017年4月19日
*/

public interface SADMS049 {
    public int getSADMS049(List<TtCustomerVehicleListPO> listPo,String vehStatus,String cusNo) throws ServiceBizException; 

}
