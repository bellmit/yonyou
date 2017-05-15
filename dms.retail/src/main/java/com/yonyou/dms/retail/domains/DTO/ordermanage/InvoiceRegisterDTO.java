
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.retail
 *
 * @File name : InvoiceRegisterDTO.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年9月28日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月28日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.retail.domains.DTO.ordermanage;

import java.util.Date;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 开票登记DTO
 * @author DuPengXin
 * @date 2016年9月28日
 */

public class InvoiceRegisterDTO {

   

    private String soNo;
    
    @Required
    private String dealerCode;
    
    @Required
    private String vin;
    
    @Required
    private String invoiceTypeCode;
    
    @Required
    private String invoiceNo;
    
    @Required
    private Integer invoiceChargeType;
    
    @Required
    private Double invoiceAmount;
    
    @Required
    private String invoiceCustomer;
    
    @Required
    private Date invoiceDate;
    
    @Required
    private Long transactor;

    private Integer isValid;

    private String remark;

    private String invoiceWriter;
 
    private Integer ctCode;
    private String certificateNo;
    private Long recorder;
    private Integer invoiceKind;
   
    private Date recordDate;
    private String wxFlag;
    
    private String oldCerNo;
    
    
    private String fileUrl;
    
    
  






    
    
    
    /**
     * @return the fileUrl
     */
    public String getFileUrl() {
        return fileUrl;
    }








    
    /**
     * @param fileUrl the fileUrl to set
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }








    public String getOldCerNo() {
        return oldCerNo;
    }







    
    public void setOldCerNo(String oldCerNo) {
        this.oldCerNo = oldCerNo;
    }







    public String getWxFlag() {
        return wxFlag;
    }






    
    public void setWxFlag(String wxFlag) {
        this.wxFlag = wxFlag;
    }






    public Date getRecordDate() {
        return recordDate;
    }





    
    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }





    public Integer getInvoiceKind() {
        return invoiceKind;
    }




    
    public void setInvoiceKind(Integer invoiceKind) {
        this.invoiceKind = invoiceKind;
    }




    public Integer getCtCode() {
        return ctCode;
    }



    
    public void setCtCode(Integer ctCode) {
        this.ctCode = ctCode;
    }



    
    public String getCertificateNo() {
        return certificateNo;
    }



    
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }



 


  


    
    
    
    public Long getRecorder() {
        return recorder;
    }







    
    public void setRecorder(Long recorder) {
        this.recorder = recorder;
    }







    public String getSoNo() {
        return soNo;
    }





    
    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }





  


    
    
    public String getInvoiceTypeCode() {
        return invoiceTypeCode;
    }








    
    public void setInvoiceTypeCode(String invoiceTypeCode) {
        this.invoiceTypeCode = invoiceTypeCode;
    }








    public String getInvoiceWriter() {
        return invoiceWriter;
    }


    
    public void setInvoiceWriter(String invoiceWriter) {
        this.invoiceWriter = invoiceWriter;
    }


    public String getDealerCode() {
        return dealerCode;
    }


    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }


    public String getVin() {
        return vin;
    }


    public void setVin(String vin) {
        this.vin = vin;
    }


  


    public String getInvoiceNo() {
        return invoiceNo;
    }


    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }


    public Integer getInvoiceChargeType() {
        return invoiceChargeType;
    }


    public void setInvoiceChargeType(Integer invoiceChargeType) {
        this.invoiceChargeType = invoiceChargeType;
    }


    public Double getInvoiceAmount() {
        return invoiceAmount;
    }


    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }


    public String getInvoiceCustomer() {
        return invoiceCustomer;
    }


    public void setInvoiceCustomer(String invoiceCustomer) {
        this.invoiceCustomer = invoiceCustomer;
    }


    public Date getInvoiceDate() {
        return invoiceDate;
    }


    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }


    


    
    public Long getTransactor() {
        return transactor;
    }







    
    public void setTransactor(Long transactor) {
        this.transactor = transactor;
    }







    public Integer getIsValid() {
        return isValid;
    }


    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }


    public String getRemark() {
        return remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


  
}
