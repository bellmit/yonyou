
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : BigCustomerCarDTO.java
*
* @Author : Administrator
*
* @Date : 2017年1月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月16日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.DTO.basedata;



/**
* TODO description
* @author Administrator
* @date 2017年1月16日
*/

public class BigCustomerCarDTO {
      private String brandCode;
      
      private String seriesCode;
      
      private String modelCode;
      
      private String configCode;
      
      private String colorCode;
      
      private Integer purchaseCount;

    
    public String getBrandCode() {
        return brandCode;
    }

    
    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    
    public String getSeriesCode() {
        return seriesCode;
    }

    
    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }

    
    public String getModelCode() {
        return modelCode;
    }

    
    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    
    public String getConfigCode() {
        return configCode;
    }

    
    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    
    public String getColorCode() {
        return colorCode;
    }

    
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    
    public Integer getPurchaseCount() {
        return purchaseCount;
    }

    
    public void setPurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
    }
      
      
}
