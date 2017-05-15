
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : CommonNoService.java
*
* @Author : jcsi
*
* @Date : 2016年7月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月19日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.framework.service;

import java.util.Date;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author jcsi
 * @date 2016年7月19日
 */

public interface CommonNoService {

    public String getSystemOrderNo(String orderPrefix, String... titles) throws ServiceBizException;

    public String getDefalutPara(String para) throws ServiceBizException;

    public String getWSOrderNo(String orderPrefix, String entityCode) throws ServiceBizException;

    public long getTimeDiff(String type, Date t1, Date t2) throws ServiceBizException;

    public long getId(String type) throws ServiceBizException;

    public String GetBillNo(String type);

}
