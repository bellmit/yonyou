
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TmVehicleStockOrderPO.java
*
* @Author : yangjie
*
* @Date : 2017年2月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月6日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * TODO description
 * 
 * @author yangjie
 * @date 2017年2月6日
 */

@Table("TM_VEHICLE_STOCK_ORDER")
@CompositePK({ "DEALER_CODE", "PO_NO" })
public class TmVehicleStockOrderPO extends BaseModel {

}
