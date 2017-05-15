
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TtVsShippingNotifyPO.java
*
* @Author : yangjie
*
* @Date : 2017年1月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月17日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.PO.stockmanage;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* TODO description
* @author yangjie
* @date 2017年1月17日
*/

@Table("tt_vs_shipping_notify")
@CompositePK({"DEALER_CODE","VIN"})
public class TtVsShippingNotifyPO extends BaseModel {

}
