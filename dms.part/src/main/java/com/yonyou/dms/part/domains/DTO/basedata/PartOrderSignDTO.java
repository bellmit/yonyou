
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartOrderSignDTO.java
*
* @Author : zhongsw
*
* @Date : 2016年7月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月27日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.DTO.basedata;

/**
* 货运单主表
* @author zhongsw
* @date 2016年7月27日
*/

public class PartOrderSignDTO { 
    
//  private Date delivery_time;//发运日期(主)
//  
//  private Integer delivery_status;//发运单状态
//  
//  private Integer is_verification;//是否核销
//  
//  private Integer is_valid;//是否有效
//  
//  private String stock_in_no;//入库单号(主)
      
//  private String remark;//备注
  
//  private List<PartOrderSignItemDTO> partordersignitemlist;
  
    private String dealer_code;//经销商代码(主)
    
    private String orderRegeditNo;//发运单号
    
    private Double receivableCases;//应收箱数
    
    private Double factCases;//实收箱数
    
    private String deliveryPdc;//发运地址
    
    private String deliveryCompany;//货运公司

	public String getDealer_code() {
		return dealer_code;
	}

	public void setDealer_code(String dealer_code) {
		this.dealer_code = dealer_code;
	}

	public String getOrderRegeditNo() {
		return orderRegeditNo;
	}

	public void setOrderRegeditNo(String orderRegeditNo) {
		this.orderRegeditNo = orderRegeditNo;
	}

	public Double getReceivableCases() {
		return receivableCases;
	}

	public void setReceivableCases(Double receivableCases) {
		this.receivableCases = receivableCases;
	}

	public Double getFactCases() {
		return factCases;
	}

	public void setFactCases(Double factCases) {
		this.factCases = factCases;
	}

	public String getDeliveryPdc() {
		return deliveryPdc;
	}

	public void setDeliveryPdc(String deliveryPdc) {
		this.deliveryPdc = deliveryPdc;
	}

	public String getDeliveryCompany() {
		return deliveryCompany;
	}

	public void setDeliveryCompany(String deliveryCompany) {
		this.deliveryCompany = deliveryCompany;
	}
    





}
