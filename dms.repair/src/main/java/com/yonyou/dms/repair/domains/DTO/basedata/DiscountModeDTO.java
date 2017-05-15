
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : DiscountModeDTO.java
*
 * @Author : zhengcong
 *
 * @Date : 2017年3月23日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月23日   zhengcong   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.basedata;

/**
 * 优惠模式
     * @author zhengcong
     * @date 2017年3月23日
*/

public class DiscountModeDTO {
	

    private String discountModeCode;
    
    private String discountModeName;
    
    private Double labourAmountDiscount;
    
    private Double repairPartDiscount;
    
    private Double salesPartDiscount;
    
    private Double upholsterAmountDiscount;
    private Double upholsterPartDiscount;
    private Double addItemDiscount;
    private Integer noDiscountByInsurance;
    
    
	public String getDiscountModeCode() {
		return discountModeCode;
	}
	public void setDiscountModeCode(String discountModeCode) {
		this.discountModeCode = discountModeCode;
	}
	public String getDiscountModeName() {
		return discountModeName;
	}
	public void setDiscountModeName(String discountModeName) {
		this.discountModeName = discountModeName;
	}
	public Double getLabourAmountDiscount() {
		return labourAmountDiscount;
	}
	public void setLabourAmountDiscount(Double labourAmountDiscount) {
		this.labourAmountDiscount = labourAmountDiscount;
	}
	public Double getRepairPartDiscount() {
		return repairPartDiscount;
	}
	public void setRepairPartDiscount(Double repairPartDiscount) {
		this.repairPartDiscount = repairPartDiscount;
	}
	public Double getSalesPartDiscount() {
		return salesPartDiscount;
	}
	public void setSalesPartDiscount(Double salesPartDiscount) {
		this.salesPartDiscount = salesPartDiscount;
	}
	public Double getUpholsterAmountDiscount() {
		return upholsterAmountDiscount;
	}
	public void setUpholsterAmountDiscount(Double upholsterAmountDiscount) {
		this.upholsterAmountDiscount = upholsterAmountDiscount;
	}
	public Double getUpholsterPartDiscount() {
		return upholsterPartDiscount;
	}
	public void setUpholsterPartDiscount(Double upholsterPartDiscount) {
		this.upholsterPartDiscount = upholsterPartDiscount;
	}
	public Double getAddItemDiscount() {
		return addItemDiscount;
	}
	public void setAddItemDiscount(Double addItemDiscount) {
		this.addItemDiscount = addItemDiscount;
	}
	public Integer getNoDiscountByInsurance() {
		return noDiscountByInsurance;
	}
	public void setNoDiscountByInsurance(Integer noDiscountByInsurance) {
		this.noDiscountByInsurance = noDiscountByInsurance;
	}


  
}
