
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : StorePo.java
*
* @Author : zhongsw
*
* @Date : 2016年7月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月10日    zhongsw    1.0
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
 * 业务往来客户经销商表
 * 
 * @author chenwei
 * @date 2017年3月29日
 */
@Table("TM_PART_CUSTOMER_DEALER")
@CompositePK({"DEALER_CODE","BRAND_CODE","DEALER2_CODE"})
public class PartCustomerDealerPO extends BaseModel {

}
