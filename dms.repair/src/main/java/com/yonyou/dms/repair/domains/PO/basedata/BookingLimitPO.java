
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BookingLimitPO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年10月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月12日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.repair.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 预约限量
 * 
 * @author zhanshiwei
 * @date 2016年10月12日
 */
@Table("TM_BOOKING_LIMIT")
@CompositePK({"DEALER_CODE","BOOKING_ID"})
public class BookingLimitPO extends BaseModel{

}
