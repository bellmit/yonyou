
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SADMS022VO.java
*
* @Author : yangjie
*
* @Date : 2017年1月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月16日    yangjie    1.0
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
 * 
 * @author yangjie
 * @date 2017年1月16日
 */

public class SADMS022Dto {

    private static final Long serialVersionUID = 1L;
    private String            id;                   // ID
    private String            bankCode;             // 银行
    private Double            dpmS;                 // 首付比例开始
    private Double            dpmE;                 // 首付比例结束
    private Double            rate;                 // 贴息利率
    private Date              effectiveDateS;       // 有效日期开始
    private Date              effectiveDateE;       // 有效日期结束
    private String            brandCode;            // 品牌
    private String            seriesCode;           // 车系
    private String            modelCode;            // 车型
    private String            styleCode;            // 车款/配置
    private Integer           installmentNumber;    // 分期期数
    private Date              createDate;           // 创建时间
    private Date              updateDate;           // 修改时间
    private Long              createBy;             // 创建时间
    private Long              updateBy;             // 修改时间
    private Integer           isValid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Double getDpmS() {
        return dpmS;
    }

    public void setDpmS(Double dpmS) {
        this.dpmS = dpmS;
    }

    public Double getDpmE() {
        return dpmE;
    }

    public void setDpmE(Double dpmE) {
        this.dpmE = dpmE;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getEffectiveDateS() {
        return effectiveDateS;
    }

    public void setEffectiveDateS(Date effectiveDateS) {
        this.effectiveDateS = effectiveDateS;
    }

    public Date getEffectiveDateE() {
        return effectiveDateE;
    }

    public void setEffectiveDateE(Date effectiveDateE) {
        this.effectiveDateE = effectiveDateE;
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

    public String getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode;
    }

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public static Long getSerialversionuid() {
        return serialVersionUID;
    }

}
