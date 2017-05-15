
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BookingLimitDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年10月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月12日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;

/**
 * 配件预留单 TODO description
 * 
 * @author chenwei
 * @date 2017年4月30日
 */

public class TtPartObligatedDTO {

    private String  dealerCode;        // 经销商code
    private String  obligatedNo;
    private String  sheetNo;
    private String  applicant;
    private Date    applyDate;
    private Double  quantity;
    private Double  costAmount;
    private Integer obligatedClose;
    private String  license;
    private Integer dKey;
    private String  ownerName;
    private Integer ver;
    private String  ownerNo;
    private Date    balanceCloseTime;
    private Date    obligatedCloseDate;
    private String  obligatedOperator;
    private String  remark;
    private Integer sheetNoType;

    
    public String getDealerCode() {
        return dealerCode;
    }

    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    
    public Integer getdKey() {
        return dKey;
    }

    
    public void setdKey(Integer dKey) {
        this.dKey = dKey;
    }

    public Integer getSheetNoType() {
        return sheetNoType;
    }

    public void setSheetNoType(Integer sheetNoType) {
        this.sheetNoType = sheetNoType;
    }

    public void setLicense(String license){
        this.license=license;
    }

    public String getLicense(){
        return this.license;
    }

    public void setDKey(Integer dKey){
        this.dKey=dKey;
    }

    public Integer getDKey(){
        return this.dKey;
    }

    public void setOwnerName(String ownerName){
        this.ownerName=ownerName;
    }

    public String getOwnerName(){
        return this.ownerName;
    }

    public void setVer(Integer ver){
        this.ver=ver;
    }

    public Integer getVer(){
        return this.ver;
    }

    public void setOwnerNo(String ownerNo){
        this.ownerNo=ownerNo;
    }

    public String getOwnerNo(){
        return this.ownerNo;
    }

    public void setObligatedNo(String obligatedNo){
        this.obligatedNo=obligatedNo;
    }

    public String getObligatedNo(){
        return this.obligatedNo;
    }


    public void setApplyDate(Date applyDate){
        this.applyDate=applyDate;
    }

    public Date getApplyDate(){
        return this.applyDate;
    }

    public void setObligatedClose(Integer obligatedClose){
        this.obligatedClose=obligatedClose;
    }

    public Integer getObligatedClose(){
        return this.obligatedClose;
    }

    public void setSheetNo(String sheetNo){
        this.sheetNo=sheetNo;
    }

    public String getSheetNo(){
        return this.sheetNo;
    }

    public void setCostAmount(Double costAmount){
        this.costAmount=costAmount;
    }

    public Double getCostAmount(){
        return this.costAmount;
    }

    public void setBalanceCloseTime(Date balanceCloseTime){
        this.balanceCloseTime=balanceCloseTime;
    }

    public Date getBalanceCloseTime(){
        return this.balanceCloseTime;
    }


    public void setQuantity(Double quantity){
        this.quantity=quantity;
    }

    public Double getQuantity(){
        return this.quantity;
    }

    public void setObligatedCloseDate(Date obligatedCloseDate){
        this.obligatedCloseDate=obligatedCloseDate;
    }

    public Date getObligatedCloseDate(){
        return this.obligatedCloseDate;
    }

    public void setObligatedOperator(String obligatedOperator){
        this.obligatedOperator=obligatedOperator;
    }

    public String getObligatedOperator(){
        return this.obligatedOperator;
    }

    public void setApplicant(String applicant){
        this.applicant=applicant;
    }

    public String getApplicant(){
        return this.applicant;
    }

    public void setRemark(String remark){
        this.remark=remark;
    }

    public String getRemark(){
        return this.remark;
    }


    @Override
    public String toString() {
        return "TtPartObligatedDTO [dealerCode=" + dealerCode + ", obligatedNo=" + obligatedNo + ", sheetNo=" + sheetNo
               + ", applicant=" + applicant + ", applyDate=" + applyDate + ", quantity=" + quantity + ", costAmount="
               + costAmount + ", obligatedClose=" + obligatedClose + ", license=" + license + ", dKey=" + dKey
               + ", ownerName=" + ownerName + ", ver=" + ver + ", ownerNo=" + ownerNo + ", balanceCloseTime="
               + balanceCloseTime + ", obligatedCloseDate=" + obligatedCloseDate + ", obligatedOperator="
               + obligatedOperator + ", remark=" + remark + ", sheetNoType=" + sheetNoType + "]";
    }


}
