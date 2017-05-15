
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : ReceiveMoneyPO.java
*
* @Author : jcsi
*
* @Date : 2016年9月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月28日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
*
* @author jcsi
* @date 2016年9月28日
*/
@Table("TT_RECEIVE_MONEY")
@CompositePK({ "DEALER_CODE", "RECEIVE_NO" })
public class ReceiveMoneyPO extends BaseModel{

}
