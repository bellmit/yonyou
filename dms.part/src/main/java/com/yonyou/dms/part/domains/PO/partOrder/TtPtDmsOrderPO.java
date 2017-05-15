
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : TtPtDmsOrderPO.java
*
* @Author : zhanshiwei
*
* @Date : 2017年4月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月18日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.PO.partOrder;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* 
* @author zhanshiwei
* @date 2017年4月18日
*/

@Table("TT_PT_DMS_ORDER")
@CompositePK({"ORDER_NO","DEALER_CODE"})
public class TtPtDmsOrderPO  extends BaseModel{

}
