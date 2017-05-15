/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
 * @Author : zhengcong
 *
 * @Date : 2017年3月24日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月24日   zhengcong   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.common.domains.DTO.basedata;

/**
 * 收费类别DTO
     * @author zhengcong
     * @date 2017年3月24日
*/
public class ChargeTypeDTO {
	

    private String manageSortCode;
    
    private String manageSortName;
    
    private Double labourRate;
    
    private Double repairPartRate;
    
    private Double salesPartRate;
    
    private Double addItemRate;
    
    private Double labourAmountRate;
    
    private Double overheadExpensesRate;

    private Integer isManaging;

	public String getManageSortCode() {
		return manageSortCode;
	}

	public void setManageSortCode(String manageSortCode) {
		this.manageSortCode = manageSortCode;
	}

	public String getManageSortName() {
		return manageSortName;
	}

	public void setManageSortName(String manageSortName) {
		this.manageSortName = manageSortName;
	}

	public Double getLabourRate() {
		return labourRate;
	}

	public void setLabourRate(Double labourRate) {
		this.labourRate = labourRate;
	}

	public Double getRepairPartRate() {
		return repairPartRate;
	}

	public void setRepairPartRate(Double repairPartRate) {
		this.repairPartRate = repairPartRate;
	}

	public Double getSalesPartRate() {
		return salesPartRate;
	}

	public void setSalesPartRate(Double salesPartRate) {
		this.salesPartRate = salesPartRate;
	}

	public Double getAddItemRate() {
		return addItemRate;
	}

	public void setAddItemRate(Double addItemRate) {
		this.addItemRate = addItemRate;
	}

	public Double getLabourAmountRate() {
		return labourAmountRate;
	}

	public void setLabourAmountRate(Double labourAmountRate) {
		this.labourAmountRate = labourAmountRate;
	}

	public Double getOverheadExpensesRate() {
		return overheadExpensesRate;
	}

	public void setOverheadExpensesRate(Double overheadExpensesRate) {
		this.overheadExpensesRate = overheadExpensesRate;
	}

	public Integer getIsManaging() {
		return isManaging;
	}

	public void setIsManaging(Integer isManaging) {
		this.isManaging = isManaging;
	}
	
	
	

}

