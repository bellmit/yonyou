
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalanceAddDTO.java
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
* 结算单附加项目明细DTO
* @author ZhengHe
* @date 2016年9月26日
*/

public class BalanceAddItemDTO {
    @Required
    private Long balanceAddItemId;

    @Required
    private Long balanceId;

    @Required
    private Long roAddItemId;

    private String chargePartitionCode;

    private String addItemCode;

    private String addItemName;

    @Digits(integer=12,fraction=2)
    private Double addItemAmount;

    @Digits(integer=12,fraction=2)
    private Double receivableAmount;

    private String remark;

    public Long getBalanceAddItemId() {
        return balanceAddItemId;
    }

    public void setBalanceAddItemId(Long balanceAddItemId) {
        this.balanceAddItemId = balanceAddItemId;
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Long getRoAddItemId() {
        return roAddItemId;
    }

    public void setRoAddItemId(Long roAddItemId) {
        this.roAddItemId = roAddItemId;
    }

    public String getChargePartitionCode() {
        return chargePartitionCode;
    }

    public void setChargePartitionCode(String chargePartitionCode) {
        this.chargePartitionCode = chargePartitionCode;
    }

    public String getAddItemCode() {
        return addItemCode;
    }

    public void setAddItemCode(String addItemCode) {
        this.addItemCode = addItemCode;
    }

    public String getAddItemName() {
        return addItemName;
    }

    public void setAddItemName(String addItemName) {
        this.addItemName = addItemName;
    }

    public Double getAddItemAmount() {
        return addItemAmount;
    }

    public void setAddItemAmount(Double addItemAmount) {
        this.addItemAmount = addItemAmount;
    }

    public Double getReceivableAmount() {
        return receivableAmount;
    }

    public void setReceivableAmount(Double receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
