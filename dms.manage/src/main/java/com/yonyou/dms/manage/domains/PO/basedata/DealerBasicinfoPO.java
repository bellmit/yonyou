
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : DealerBasicinfoPO1.java
*
* @Author : ZhengHe
*
* @Date : 2016年7月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月13日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 
* 经销商信息Model
* @author ZhengHe
* @date 2016年7月6日
 */
@Table("tm_dealer_basicinfo")
@IdName("DEALER_ID")
@CompositePK({"DEALER_CODE"})
public class DealerBasicinfoPO extends BaseModel{

}
