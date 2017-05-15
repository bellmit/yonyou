
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : MasterDataDTO.java
*
* @Author : DuPengXin
*
* @Date : 2016年9月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月8日    DuPengXin   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.vehicle.domains.DTO.basedata;

import java.util.Date;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
* 整车产品信息
* @author DuPengXin
* @date 2016年9月8日
*/

public class MasterDataDTO {
    
    private Long productId;

    private String dealerCode; //经销商代码、
    
    @Required
    private String productCode;//产品代码
    
    @Required
    private String productName;//产品名称

    private Integer oemTag;//OEM标志
    
    @Required
    private String configCode;//配置ID

    private String color;//颜色
    
    @Required
    private String brandCode;//品牌
    
    @Required
    private String seriesCode;//车系
    
    @Required
    private String modelCode;//车型
    
    @Required
    private Integer productType;//产品类别
    
    @Required
    private Integer productStatus;//产品状态

    private Integer isValid;//是否有效

    private Double oemDirectivePrice;//车厂指导价

    private Double directivePrice;//销售指导价

    private Date enterDate;//上市日期

    private Date exeuntDate; //退市日期

    private String remark;//备注
    
    private String userIds;//接收选中的参数
    
    
    
   


    
   
    


    
    public String getConfigCode() {
        return configCode;
    }



    
    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }



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


    

    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Integer getOemTag() {
        return oemTag;
    }

    public void setOemTag(Integer oemTag) {
        this.oemTag = oemTag;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Double getOemDirectivePrice() {
        return oemDirectivePrice;
    }

    public void setOemDirectivePrice(Double oemDirectivePrice) {
        this.oemDirectivePrice = oemDirectivePrice;
    }

    public Double getDirectivePrice() {
        return directivePrice;
    }

    public void setDirectivePrice(Double directivePrice) {
        this.directivePrice = directivePrice;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public Date getExeuntDate() {
        return exeuntDate;
    }

    public void setExeuntDate(Date exeuntDate) {
        this.exeuntDate = exeuntDate;
    }


    public String getUserIds() {
        return userIds;
    }


    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }


}
