
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CarownerPO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月5日    zhanshiwei    1.0
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
* 车主信息PO
* @author zhanshiwei
* @date 2016年8月5日
*/
@Table("tm_owner")
@CompositePK({"OWNER_NO","DEALER_CODE"})
public class CarownerPO  extends BaseModel{
        
}
