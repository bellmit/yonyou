
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.part
 *
 * @File name : PartInventoryDTO.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年7月26日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月26日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 配件盘点单Dto
 * @author ZhengHe
 * @date 2016年7月26日
 */

public class PartInventoryDTO {
    @Required
    private Long partInventoryId;

    @Required
    private String inventoryNo;

    private Date inventoryDate;

    @Digits(integer=12,fraction=2)
    private Double profitAmount;

    @Digits(integer=8,fraction=0)
    private Double lossCount;

    @Digits(integer=12,fraction=2)
    private Double lossAmount;

    private String handler;

    @Digits(integer=8,fraction=0)
    private Double profitCount;

    private Integer isConfirmed;

    private Integer profitTag;

    private Integer lossTag;

    private String remark;

    private List<PartInventoryItemDTO> piiDtoList;

    private List<PartCodesDTO> partCodeList;

    private Integer number;

    public Long getPartInventoryId() {
        return partInventoryId;
    }

    public void setPartInventoryId(Long partInventoryId) {
        this.partInventoryId = partInventoryId;
    }

    public String getInventoryNo() {
        return inventoryNo;
    }

    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    public Date getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(Date inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public Double getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(Double profitAmount) {
        this.profitAmount = profitAmount;
    }

    public Double getLossCount() {
        return lossCount;
    }

    public void setLossCount(Double lossCount) {
        this.lossCount = lossCount;
    }

    public Double getLossAmount() {
        return lossAmount;
    }

    public void setLossAmount(Double lossAmount) {
        this.lossAmount = lossAmount;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public Double getProfitCount() {
        return profitCount;
    }

    public void setProfitCount(Double profitCount) {
        this.profitCount = profitCount;
    }

    public Integer getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Integer isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public Integer getProfitTag() {
        return profitTag;
    }

    public void setProfitTag(Integer profitTag) {
        this.profitTag = profitTag;
    }

    public Integer getLossTag() {
        return lossTag;
    }

    public void setLossTag(Integer lossTag) {
        this.lossTag = lossTag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public List<PartInventoryItemDTO> getPiiDtoList() {
        return piiDtoList;
    }

    public void setPiiDtoList(List<PartInventoryItemDTO> piiDtoList) {
        this.piiDtoList = piiDtoList;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<PartCodesDTO> getPartCodeList() {
        return partCodeList;
    }

    public void setPartCodeList(List<PartCodesDTO> partCodeList) {
        this.partCodeList = partCodeList;
    }

}
