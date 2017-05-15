
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : ReceiveMoneyDTO.java
*
* @Author : jcsi
*
* @Date : 2016年9月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月28日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.balance;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
*
* @author jcsi
* @date 2016年9月28日
*/

public class ReceiveMoneyDTO {
    
    private Long balaPayobjId; //
    
    @Required
    private Long payTypeCode;  //付款方式代码
    
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date receiveDate;  //收款日期
    
    private String handler;  //经手人
    
    private Long invoiceTypeCode;  //发票类型
    
    private String billNo;  //票据号码
    
    private String checkNo;  //支票号码
    
    @Required
    private Double receiveAmount;  //收款金额
    
    private Long invoiceTag;  //已开票
    
    private Long writeoffTag;  //销账标志
    
    private Date writeoffDate;  //销账日期
    
    private String remark;  //备注

    
    public Long getPayTypeCode() {
        return payTypeCode;
    }

    
    
    public Long getBalaPayobjId() {
        return balaPayobjId;
    }


    
    public void setBalaPayobjId(Long balaPayobjId) {
        this.balaPayobjId = balaPayobjId;
    }


    public void setPayTypeCode(Long payTypeCode) {
        this.payTypeCode = payTypeCode;
    }

    
    public Date getReceiveDate() {
        return receiveDate;
    }

    
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    
    public String getHandler() {
        return handler;
    }

    
    public void setHandler(String handler) {
        this.handler = handler;
    }

    
    public Long getInvoiceTypeCode() {
        return invoiceTypeCode;
    }

    
    public void setInvoiceTypeCode(Long invoiceTypeCode) {
        this.invoiceTypeCode = invoiceTypeCode;
    }

    
    public String getBillNo() {
        return billNo;
    }

    
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    
    public String getCheckNo() {
        return checkNo;
    }

    
    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    
    public Double getReceiveAmount() {
        return receiveAmount;
    }

    
    public void setReceiveAmount(Double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    
    public Long getInvoiceTag() {
        return invoiceTag;
    }

    
    public void setInvoiceTag(Long invoiceTag) {
        this.invoiceTag = invoiceTag;
    }

    
    public Long getWriteoffTag() {
        return writeoffTag;
    }

    
    public void setWriteoffTag(Long writeoffTag) {
        this.writeoffTag = writeoffTag;
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
        this.remark = remark;
    }

    
    
}
