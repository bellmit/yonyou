
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SalesOrderPO.java
*
* @Author : xukl
*
* @Date : 2016年9月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月5日    xukl    1.0
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
* SalesOrderPO
* @author xukl
* @date 2016年9月5日
*/
@Table("tt_sales_order")
@CompositePK({"DEALER_CODE","SO_NO"})
public class TtSalesOrderPO extends Model {
    
}
