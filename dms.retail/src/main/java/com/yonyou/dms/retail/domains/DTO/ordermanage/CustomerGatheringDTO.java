
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SettleCollectionDTO.java
*
* @Author : xukl
*
* @Date : 2016年10月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月14日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.domains.DTO.ordermanage;

import java.util.Date;

/**
* 收款登记模型
* @author xukl
* @date 2016年10月14日
*/

public class CustomerGatheringDTO {
    private Integer receiveId;

    private String dealerCode;

    private String receiveNo;

    private Integer soNoId;

    private String payTypeCode;

    private Double receiveAmount;

    private Date receiveDate;

    private Integer gatheringType;

    private String billNo;

    private String customerNo;

    private Integer writeoffTag;

    private String writeoffBy;//销账人

    private Date writeoffDate;

    private String remark;

    private String recorder;//经办人

    private Date recordDate;

 
	public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public String getReceiveNo() {
        return receiveNo;
    }

    public void setReceiveNo(String receiveNo) {
        this.receiveNo = receiveNo == null ? null : receiveNo.trim();
    }

    public Integer getSoNoId() {
        return soNoId;
    }

    public void setSoNoId(Integer soNoId) {
        this.soNoId = soNoId;
    }

    public String getPayTypeCode() {
        return payTypeCode;
    }

    public void setPayTypeCode(String payTypeCode) {
        this.payTypeCode = payTypeCode;
    }

    public Double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(Double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Integer getGatheringType() {
        return gatheringType;
    }

    public void setGatheringType(Integer gatheringType) {
        this.gatheringType = gatheringType;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public Integer getWriteoffTag() {
        return writeoffTag;
    }

    public void setWriteoffTag(Integer writeoffTag) {
        this.writeoffTag = writeoffTag;
    }

    public String getWriteoffBy() {
        return writeoffBy;
    }

    public void setWriteoffBy(String writeoffBy) {
        this.writeoffBy = writeoffBy == null ? null : writeoffBy.trim();
    }

    public Date getWriteoffDate() {
        return writeoffDate;
    }

    public void setWriteoffDate(Date writeoffDate) {
        this.writeoffDate = writeoffDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder == null ? null : recorder.trim();
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
}
