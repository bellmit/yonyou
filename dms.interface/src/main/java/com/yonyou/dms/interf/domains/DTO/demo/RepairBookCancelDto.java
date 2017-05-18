/*
* Copyright 2016 Yonyou Auto Information Technology（Shanghai） Co., Ltd. All Rights Reserved.
*
* This software is published under the terms of the YONYOU Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : marketing-service-interface
*
* @File name : RepairBookingCancelDto.java
*
* @Author : sangdeliang
*
* @Date : 2016年12月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月5日    sangdeliang    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.interf.domains.DTO.demo;

import java.io.Serializable;

/**
*
* @author zhangxc
* @ description 养修预约取消DTO
* @date 2016年12月5日
*/

public class RepairBookCancelDto  implements Serializable{
   
    /**
     * 
     */
    private static final long serialVersionUID = -3000024483562713783L;
    //系统标志
    private String systemId;
    //系统来源
    private String sourceType;
    //经销商编号
    private String dealerCode;
    //预约单号(微信端生成)
    private String customerBookNo;
    
    /**
     * @return the systemId
     */
    public String getSystemId() {
        return systemId;
    }
    
    /**
     * @param systemId the systemId to set
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    
    /**
     * @return the sourceType
     */
    public String getSourceType() {
        return sourceType;
    }
    
    /**
     * @param sourceType the sourceType to set
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
    
    /**
     * @return the dealerCode
     */
    public String getDealerCode() {
        return dealerCode;
    }
    
    /**
     * @param dealerCode the dealerCode to set
     */
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    /**
     * @return the customerBookNo
     */
    public String getCustomerBookNo() {
        return customerBookNo;
    }
    
    /**
     * @param customerBookNo the customerBookNo to set
     */
    public void setCustomerBookNo(String customerBookNo) {
        this.customerBookNo = customerBookNo;
    }
    
    
}
