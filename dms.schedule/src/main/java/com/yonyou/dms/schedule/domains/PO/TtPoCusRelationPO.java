
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TtPoCusRelationPO.java
*
* @Author : Administrator
*
* @Date : 2017年1月4日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月4日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.schedule.domains.PO;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;


/**
* TODO description
* @author Administrator
* @date 2017年1月4日
*/
@Table("tt_po_cus_relation")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtPoCusRelationPO extends Model{

}
