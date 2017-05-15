
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : RoAddItemDTO.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年8月17日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月17日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.commonAS.domains.DTO.order;

import javax.validation.constraints.Digits;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 附加项目dto
 * @author ZhengHe
 * @date 2016年8月17日
 */

public class RoAddItemDTO {
    @Required
    private Integer roAddItemId;

    private Long roId;

    private String chargePartitionCode;

    @Required
    private String addItemCode;

    @Required
    private String addItemName;

    @Digits(integer=12,fraction=2)
    private Double addItemAmount;

    @Digits(integer=12,fraction=2)
    private Double receivableAmount;

    private String remark;

    public Integer getRoAddItemId() {
        return roAddItemId;
    }

    public void setRoAddItemId(Integer roAddItemId) {
        this.roAddItemId = roAddItemId;
    }

    public Long getRoId() {
        return roId;
    }

    public void setRoId(Long roId) {
        this.roId = roId;
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
