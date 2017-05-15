
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : VsProductPO.java
*
* @Author : yangjie
*
* @Date : 2017年2月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月7日    yangjie    1.0
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
* TODO 整车库存-产品定义
* @author yangjie
* @date 2017年1月9日
*/

@Table("TM_VS_PRODUCT")
@CompositePK({"DEALER_CODE","PRODUCT_CODE"})
public class VsProductPO extends BaseModel {

}
