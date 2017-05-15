
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalanceManageDTO.java
*
* @Author : ZhengHe
*
* @Date : 2016年9月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月26日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.balance;

import javax.validation.constraints.Digits;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
* 结算单辅料管理费DTO
* @author ZhengHe
* @date 2016年9月26日
*/

public class BalanceManageDTO {
    @Required
    private Long balanceManageId;

    @Required
    private Long roManageId;

    @Required
    private Long balanceId;

    @Digits(integer=10,fraction=2)
    private Double overItemAmount;

    @Digits(integer=3,fraction=2)
    private Double labourRate;

    @Digits(integer=3,fraction=2)
    private Double repairPartRate;

    @Digits(integer=3,fraction=2)
    private Double salesPartRate;

    @Digits(integer=3,fraction=2)
    private Double addItemRate;

    @Digits(integer=3,fraction=2)
    private Double labourAmountRate;

    @Digits(integer=3,fraction=2)
    private Double overheadExpensesRate;

    private Integer isManaging;

    @Digits(integer=5,fraction=4)
    private Double discount;

    public Long getBalanceManageId() {
        return balanceManageId;
    }

    public void setBalanceManageId(Long balanceManageId) {
        this.balanceManageId = balanceManageId;
    }

    public Long getRoManageId() {
        return roManageId;
    }

    public void setRoManageId(Long roManageId) {
        this.roManageId = roManageId;
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Double getOverItemAmount() {
        return overItemAmount;
    }

    public void setOverItemAmount(Double overItemAmount) {
        this.overItemAmount = overItemAmount;
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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
