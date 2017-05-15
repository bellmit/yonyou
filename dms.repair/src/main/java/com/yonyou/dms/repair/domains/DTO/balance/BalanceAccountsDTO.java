
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalanceAccountsDTO.java
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

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;
import com.yonyou.dms.function.utils.validate.define.MaxDigit;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
* 结算单DTO
* @author ZhengHe
* @date 2016年9月26日
*/

public class BalanceAccountsDTO {
    @Required
    private Long balanceId;
    
    @Required
    private String balanceNo;

    @MaxDigit(11)
    private Long roId;
    
    private Integer roType;

    @MaxDigit(11)
    private Long salesPartId;

    @MaxDigit(11)
    private Long allocateOutId;

    private String discountModeCode;

    private String remark;

    @Digits(integer=12,fraction=2)
    private Double labourAmount;

    @Digits(integer=12,fraction=2)
    private Double repairPartAmount;

    @Digits(integer=12,fraction=2)
    private Double salesPartAmount;
    
    @Digits(integer=12,fraction=2)
    private Double addItemAmount;

    @Digits(integer=10,fraction=2)
    private Double overItemAmount;

    @Digits(integer=12,fraction=2)
    private Double repairAmount;

    @Digits(integer=12,fraction=2)
    private Double amount;

    @Digits(integer=12,fraction=2)
    private Double disLabourAmount;

    @Digits(integer=12,fraction=2)
    private Double disRepairPartAmount;

    @Digits(integer=12,fraction=2)
    private Double disSalesPartAmount;

    @Digits(integer=12,fraction=2)
    private Double disAddItemAmount;

    @Digits(integer=10,fraction=2)
    private Double disOverItemAmount;

    @Digits(integer=12,fraction=2)
    private Double disRepairAmount;

    @Digits(integer=12,fraction=2)
    private Double disAmount;

    @Digits(integer=12,fraction=2)
    private Double balanceAmount;

    @Digits(integer=12,fraction=2)
    private Double receiveAmount;

    @Digits(integer=12,fraction=2)
    private Double subObbAmount;

    @Digits(integer=12,fraction=2)
    private Double derateAmount;

    @Digits(integer=3,fraction=2)
    private Double tax;

    @Digits(integer=12,fraction=2)
    private Double taxAmount;

    @Digits(integer=12,fraction=2)
    private Double netAmount;

    private Integer payOffMain;

    private String balanceHandler;

    private Integer isRed;

    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date balanceTime;

    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date squareDate;

    private Integer arrBalance;

    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date printBalanceTime;

    private Integer isHasClaim;
    //新增优惠率
    private Double labourAmountDiscount;
    
    private List<BalancePayobjDTO> bPODtoList;
    
    private List<BalanceAddItemDTO> bAIDtoList;
    
    private List<BalanceLabourDTO> bLDtoList;
    
    private List<BalanceRepairPartDTO> bRPDtoList;
    
    private List<BalanceSalesPartDTO> bSPDtoList;
    
    private List<BalanceAllocatePartDTO> bAPDtoList;
    
    private String customerType;  //客户类型
    
    

    
    
    
    public String getCustomerType() {
        return customerType;
    }


    
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }


    public List<BalanceAllocatePartDTO> getbAPDtoList() {
        return bAPDtoList;
    }

    
    public void setbAPDtoList(List<BalanceAllocatePartDTO> bAPDtoList) {
        this.bAPDtoList = bAPDtoList;
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public String getBalanceNo() {
        return balanceNo;
    }

    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    public Long getRoId() {
        return roId;
    }

    public void setRoId(Long roId) {
        this.roId = roId;
    }

    public Long getSalesPartId() {
        return salesPartId;
    }

    public void setSalesPartId(Long salesPartId) {
        this.salesPartId = salesPartId;
    }

    public Long getAllocateOutId() {
        return allocateOutId;
    }

    public void setAllocateOutId(Long allocateOutId) {
        this.allocateOutId = allocateOutId;
    }

    public String getDiscountModeCode() {
        return discountModeCode;
    }

    public void setDiscountModeCode(String discountModeCode) {
        this.discountModeCode = discountModeCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getLabourAmount() {
        return labourAmount==null?0:labourAmount;
    }

    public void setLabourAmount(Double labourAmount) {
        this.labourAmount = labourAmount;
    }

    public Double getRepairPartAmount() {
        return repairPartAmount==null?0:repairPartAmount;
    }

    public void setRepairPartAmount(Double repairPartAmount) {
        this.repairPartAmount = repairPartAmount;
    }

    public Double getSalesPartAmount() {
        return salesPartAmount==null?0:salesPartAmount;
    }

    public void setSalesPartAmount(Double salesPartAmount) {
        this.salesPartAmount = salesPartAmount;
    }

    public Double getAddItemAmount() {
        return addItemAmount==null?0:addItemAmount;
    }

    public void setAddItemAmount(Double addItemAmount) {
        this.addItemAmount = addItemAmount;
    }

    public Double getOverItemAmount() {
        return overItemAmount==null?0:overItemAmount;
    }

    public void setOverItemAmount(Double overItemAmount) {
        this.overItemAmount = overItemAmount;
    }

    public Double getRepairAmount() {
        return repairAmount==null?0:repairAmount;
    }

    public void setRepairAmount(Double repairAmount) {
        this.repairAmount = repairAmount;
    }

    public Double getAmount() {
        return amount==null?0:amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDisLabourAmount() {
        return disLabourAmount==null?0:disLabourAmount;
    }

    public void setDisLabourAmount(Double disLabourAmount) {
        this.disLabourAmount = disLabourAmount;
    }

    public Double getDisRepairPartAmount() {
        return disRepairPartAmount==null?0:disRepairPartAmount;
    }

    public void setDisRepairPartAmount(Double disRepairPartAmount) {
        this.disRepairPartAmount = disRepairPartAmount;
    }

    public Double getDisSalesPartAmount() {
        return disSalesPartAmount==null?0:disSalesPartAmount;
    }

    public void setDisSalesPartAmount(Double disSalesPartAmount) {
        this.disSalesPartAmount = disSalesPartAmount;
    }

    public Double getDisAddItemAmount() {
        return disAddItemAmount==null?0:disAddItemAmount;
    }

    public void setDisAddItemAmount(Double disAddItemAmount) {
        this.disAddItemAmount = disAddItemAmount;
    }

    public Double getDisOverItemAmount() {
        return disOverItemAmount==null?0:disOverItemAmount;
    }

    public void setDisOverItemAmount(Double disOverItemAmount) {
        this.disOverItemAmount = disOverItemAmount;
    }

    public Double getDisRepairAmount() {
        return disRepairAmount==null?0:disRepairAmount;
    }

    public void setDisRepairAmount(Double disRepairAmount) {
        this.disRepairAmount = disRepairAmount;
    }

    public Double getDisAmount() {
        return disAmount==null?0:disAmount;
    }

    public void setDisAmount(Double disAmount) {
        this.disAmount = disAmount;
    }

    public Double getBalanceAmount() {
        return balanceAmount==null?0:balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Double getReceiveAmount() {
        return receiveAmount==null?0:receiveAmount;
    }

    public void setReceiveAmount(Double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Double getSubObbAmount() {
        return subObbAmount==null?0:subObbAmount;
    }

    public void setSubObbAmount(Double subObbAmount) {
        this.subObbAmount = subObbAmount;
    }

    public Double getDerateAmount() {
        return derateAmount==null?0:derateAmount;
    }

    public void setDerateAmount(Double derateAmount) {
        this.derateAmount = derateAmount;
    }

    public Double getTax() {
        return tax==null?0:tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTaxAmount() {
        return taxAmount==null?0:taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getNetAmount() {
        return netAmount==null?0:netAmount;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public String getBalanceHandler() {
        return balanceHandler;
    }

    public void setBalanceHandler(String balanceHandler) {
        this.balanceHandler = balanceHandler;
    }

    public Integer getIsRed() {
        return isRed;
    }

    public void setIsRed(Integer isRed) {
        this.isRed = isRed;
    }

    public Date getBalanceTime() {
        return balanceTime;
    }

    public void setBalanceTime(Date balanceTime) {
        this.balanceTime = balanceTime;
    }

    public Date getSquareDate() {
        return squareDate;
    }

    public void setSquareDate(Date squareDate) {
        this.squareDate = squareDate;
    }

    public Integer getArrBalance() {
        return arrBalance;
    }

    public void setArrBalance(Integer arrBalance) {
        this.arrBalance = arrBalance;
    }

    public Date getPrintBalanceTime() {
        return printBalanceTime;
    }

    public void setPrintBalanceTime(Date printBalanceTime) {
        this.printBalanceTime = printBalanceTime;
    }

    public Integer getIsHasClaim() {
        return isHasClaim;
    }

    public void setIsHasClaim(Integer isHasClaim) {
        this.isHasClaim = isHasClaim;
    }

    public List<BalancePayobjDTO> getbPODtoList() {
        return bPODtoList;
    }

    public void setbPODtoList(List<BalancePayobjDTO> bPODtoList) {
        this.bPODtoList = bPODtoList;
    }

    public List<BalanceAddItemDTO> getbAIDtoList() {
        return bAIDtoList;
    }

    public void setbAIDtoList(List<BalanceAddItemDTO> bAIDtoList) {
        this.bAIDtoList = bAIDtoList;
    }

    public List<BalanceLabourDTO> getbLDtoList() {
        return bLDtoList;
    }

    public void setbLDtoList(List<BalanceLabourDTO> bLDtoList) {
        this.bLDtoList = bLDtoList;
    }

    public List<BalanceRepairPartDTO> getbRPDtoList() {
        return bRPDtoList;
    }

    public void setbRPDtoList(List<BalanceRepairPartDTO> bRPDtoList) {
        this.bRPDtoList = bRPDtoList;
    }

    public List<BalanceSalesPartDTO> getbSPDtoList() {
        return bSPDtoList;
    }

    public void setbSPDtoList(List<BalanceSalesPartDTO> bSPDtoList) {
        this.bSPDtoList = bSPDtoList;
    }

    public Double getLabourAmountDiscount() {
        return labourAmountDiscount;
    }

    public void setLabourAmountDiscount(Double labourAmountDiscount) {
        this.labourAmountDiscount = labourAmountDiscount;
    }

    public Integer getPayOffMain() {
        return payOffMain;
    }

    public void setPayOffMain(Integer payOffMain) {
        this.payOffMain = payOffMain;
    }

    public Integer getRoType() {
        return roType;
    }

    public void setRoType(Integer roType) {
        this.roType = roType;
    }

}
