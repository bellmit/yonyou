
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartCustomerDealerDTO.java
*
* @Author : chenwei
*
* @Date : 2017年2月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月29日    chenwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.DTO.basedata;



/**
* TODO description
* @author chenwei
* @date 2017年3月29日
*/

public class PartCustomerDealerDTO {

    private String dealerCode;//全局唯一,区分不同实体,由系统部署时候指定
    
    private String brandCode;//品牌代码
    
    private String dealer2Code;//维修站代码
    
    private String dealerShortName;//经销商简称
    
    private String dealerFullName;//经销商全称
    
    private Integer dKey;//distribution key 用来数据库分区，可根据业务分为 交易数据，历史数据，归档数据等，分别用 0 ，1，2表示 。默认为0，表示当前交易数据

    
    public String getDealerCode() {
        return dealerCode;
    }

    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    
    public String getBrandCode() {
        return brandCode;
    }

    
    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    
    public String getDealer2Code() {
        return dealer2Code;
    }

    
    public void setDealer2Code(String dealer2Code) {
        this.dealer2Code = dealer2Code;
    }

    
    public String getDealerShortName() {
        return dealerShortName;
    }

    
    public void setDealerShortName(String dealerShortName) {
        this.dealerShortName = dealerShortName;
    }

    
    public String getDealerFullName() {
        return dealerFullName;
    }

    
    public void setDealerFullName(String dealerFullName) {
        this.dealerFullName = dealerFullName;
    }

    
    public Integer getdKey() {
        return dKey;
    }

    
    public void setdKey(Integer dKey) {
        this.dKey = dKey;
    }

}
