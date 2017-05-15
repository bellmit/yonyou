
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : DCSTODMS003Dto.java
*
* @Author : LiGaoqi
*
* @Date : 2017年4月20日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月20日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
* TODO description
* @author LiGaoqi
* @date 2017年4月20日
*/

public class DCSTODMS003Dto {
    private String ecOrderNo;//电商订单号
    private String dealerCode;
    private String entityCode;
    private Integer revokeType;//撤单类型： 1 撤单 2 逾期
    private Date revokeDate;//撤单日期
    
    /**
     * @return the ecOrderNo
     */
    public String getEcOrderNo() {
        return ecOrderNo;
    }
    
    /**
     * @param ecOrderNo the ecOrderNo to set
     */
    public void setEcOrderNo(String ecOrderNo) {
        this.ecOrderNo = ecOrderNo;
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
     * @return the entityCode
     */
    public String getEntityCode() {
        return entityCode;
    }
    
    /**
     * @param entityCode the entityCode to set
     */
    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }
    
    /**
     * @return the revokeType
     */
    public Integer getRevokeType() {
        return revokeType;
    }
    
    /**
     * @param revokeType the revokeType to set
     */
    public void setRevokeType(Integer revokeType) {
        this.revokeType = revokeType;
    }
    
    /**
     * @return the revokeDate
     */
    public Date getRevokeDate() {
        return revokeDate;
    }
    
    /**
     * @param revokeDate the revokeDate to set
     */
    public void setRevokeDate(Date revokeDate) {
        this.revokeDate = revokeDate;
    }
    
    

}
