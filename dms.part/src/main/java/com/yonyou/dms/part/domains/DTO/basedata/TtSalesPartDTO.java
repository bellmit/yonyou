
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartMaintainPickingDTO.java
*
* @Author : chenwei
*
* @Date : 2017年3月30日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月30日    chenwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.List;

import com.yonyou.dms.common.domains.DTO.basedata.TtSalesPartItemDTO;

/**
* TODO description
* @author chenwei
* @date 2017年3月30日
*/

public class TtSalesPartDTO {

    private String dealerCode;//全局唯一,区分不同实体,由系统部署时候指定
    
    private String salesPartNo;//配件销售单号
    
    private String roNo;//工单号
    
    private String soNo;//销售订单编号
    
    private String customerCode;//客户代码
    
    private String customerName;//客户名称
    
    private double salesPartAmount;//销售材料费
    
    private double balanceStatus;//是否已结算
    
    private String consultant;//销售顾问
    
    private String remark;//备注
    
    private String remark1;//备注1
    
    private String lockUser;//锁定人
    
    private double dKey;//用来数据库分区，可根据业务分为 交易数据，历史数据，归档数据等，分别用 0 ，1，2表示 。默认为0，表示当前交易数据
    
    private Integer recordVersion;//默认0
    
    private String ddcnUpdateDate;//DDC最新日期
    
    private List<TtSalesPartItemDTO> partSalestable;
    
    private String updateStatus;
    
    private String vin;
    
    
    
    public String getVin() {
        return vin;
    }




    
    public void setVin(String vin) {
        this.vin = vin;
    }




    public String getUpdateStatus() {
        return updateStatus;
    }



    
    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    
    public List<TtSalesPartItemDTO> getPartSalestable() {
        return partSalestable;
    }





    
    public void setPartSalestable(List<TtSalesPartItemDTO> partSalestable) {
        this.partSalestable = partSalestable;
    }





    public String getDealerCode() {
        return dealerCode;
    }

    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    
    public String getSalesPartNo() {
        return salesPartNo;
    }

    
    public void setSalesPartNo(String salesPartNo) {
        this.salesPartNo = salesPartNo;
    }

    
    public String getRoNo() {
        return roNo;
    }

    
    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    
    public String getSoNo() {
        return soNo;
    }

    
    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    
    public String getCustomerCode() {
        return customerCode;
    }

    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    
    public String getCustomerName() {
        return customerName;
    }

    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    
    public double getSalesPartAmount() {
        return salesPartAmount;
    }

    
    public void setSalesPartAmount(double salesPartAmount) {
        this.salesPartAmount = salesPartAmount;
    }

    
    public double getBalanceStatus() {
        return balanceStatus;
    }

    
    public void setBalanceStatus(double balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    
    public String getConsultant() {
        return consultant;
    }

    
    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    
    public String getRemark1() {
        return remark1;
    }

    
    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    
    public String getLockUser() {
        return lockUser;
    }

    
    public void setLockUser(String lockUser) {
        this.lockUser = lockUser;
    }

    
    public double getdKey() {
        return dKey;
    }

    
    public void setdKey(double dKey) {
        this.dKey = dKey;
    }

    
    public Integer getRecordVersion() {
        return recordVersion;
    }

    
    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }

    
    public String getDdcnUpdateDate() {
        return ddcnUpdateDate;
    }

    
    public void setDdcnUpdateDate(String ddcnUpdateDate) {
        this.ddcnUpdateDate = ddcnUpdateDate;
    }
    
}
