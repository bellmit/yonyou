
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : ModelDto.java
*
* @Author : yll
*
* @Date : 2016年7月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月6日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.domains.DTO.basedata.vehicleModule;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 车型Dto
 * 
 * @author yll
 * @date 2016年7月6日
 */

public class ModelDto {

    private Integer modelId;// 车型id
    private String  brandId;// 品牌code
    private String  seriesId;       // 车系code
    private String  dealerCode;     // 经销商代码
    @Required
    private String  modelCode;      // 车型代码
    @Required
    private String  modelName;      // 车型名称
    private String  modelLabourCode;// 维修车型分组代码代码
    private String  modelGroupName; // 索赔车型组名称
    private String  modelGroupCode; // 索赔车型组代码
    private String localCode;       //行管车型代码
    private Double price;           //工时单价
    private Integer OemTag;         // oem标志
    private Integer isValid;        // 是否有效

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelLabourCode() {
        return modelLabourCode;
    }

    public void setModelLabourCode(String modelLabourCode) {
        this.modelLabourCode = modelLabourCode;
    }

    public String getModelGroupName() {
        return modelGroupName;
    }

    public void setModelGroupName(String modelGroupName) {
        this.modelGroupName = modelGroupName;
    }

    public String getModelGroupCode() {
        return modelGroupCode;
    }

    public void setModelGroupCode(String modelGroupCode) {
        this.modelGroupCode = modelGroupCode;
    }

    public Integer getOemTag() {
        return OemTag;
    }

    public void setOemTag(Integer oemTag) {
        OemTag = oemTag;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

}
