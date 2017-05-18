
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月16日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.domains.DTO.customerManage;

import java.util.Date;

/**
* 保有客户维护
* @author zhanshiwei
* @date 2016年8月16日
*/

public class CustomerDTO {
    private Long customerId;

    private Long vehicleId;


    private String customerNo;//客户编号

    private Integer cusSource;//客户来源

    private Integer mediaType;//信息渠道

    private Integer buyPurpose;//购车目的

    private String buyReason;//购车因素

    private Integer isFirstBuy;//是否首次购车

    private String contractNo;//合同编号

    private Date stockOutDate;//出库日期

    private Date insuranceExpireDate;//保险到期

    private Double vehiclePrice;//车辆价格
    
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }


    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public Integer getCusSource() {
        return cusSource;
    }

    public void setCusSource(Integer cusSource) {
        this.cusSource = cusSource;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getBuyPurpose() {
        return buyPurpose;
    }

    public void setBuyPurpose(Integer buyPurpose) {
        this.buyPurpose = buyPurpose;
    }

    public String getBuyReason() {
        return buyReason;
    }

    public void setBuyReason(String buyReason) {
        this.buyReason = buyReason == null ? null : buyReason.trim();
    }

    public Integer getIsFirstBuy() {
        return isFirstBuy;
    }

    public void setIsFirstBuy(Integer isFirstBuy) {
        this.isFirstBuy = isFirstBuy;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public Date getStockOutDate() {
        return stockOutDate;
    }

    public void setStockOutDate(Date stockOutDate) {
        this.stockOutDate = stockOutDate;
    }

    public Date getInsuranceExpireDate() {
        return insuranceExpireDate;
    }

    public void setInsuranceExpireDate(Date insuranceExpireDate) {
        this.insuranceExpireDate = insuranceExpireDate;
    }

    public Double getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(Double vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }
}
