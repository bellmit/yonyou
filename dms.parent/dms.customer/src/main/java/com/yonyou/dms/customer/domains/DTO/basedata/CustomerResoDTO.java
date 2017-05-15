
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerResoDTO.java
*
* @Author : zhengcong
*
* @Date : 2017年3月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月22日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.domains.DTO.basedata;

/**
 * 业务往来客户DTO
 * 
* @Author : zhengcong
*
* @Date : 2017年3月22日
 */

public class CustomerResoDTO {

	
	private String  dealer_code;

    private String  customer_type_code;      // 客户代码

    private String  customer_type_name;      // 客户名称

	public String getDealer_code() {
		return dealer_code;
	}

	public void setDealer_code(String dealer_code) {
		this.dealer_code = dealer_code;
	}

	public String getCustomer_type_code() {
		return customer_type_code;
	}

	public void setCustomer_type_code(String customer_type_code) {
		this.customer_type_code = customer_type_code;
	}

	public String getCustomer_type_name() {
		return customer_type_name;
	}

	public void setCustomer_type_name(String customer_type_name) {
		this.customer_type_name = customer_type_name;
	}

   


}
