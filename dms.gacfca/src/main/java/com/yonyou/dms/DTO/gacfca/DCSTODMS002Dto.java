
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : DCSTODMS002Dto.java
*
* @Author : LiGaoqi
*
* @Date : 2017年4月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月19日    LiGaoqi    1.0
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
* @date 2017年4月19日
*/

public class DCSTODMS002Dto {
    private String ecOrderNo;//电商订单号
    private String entityCode;//经销商代码
    private String dealerName;//经销商名称
    private String customerName;//客户名称
    private String idCard;//身份证
    private String tel;//手机号码
    private String brandCode;//品牌代码
    private String seriesCode;//车系代码
    private String modelCode;//车型代码
    private String configCode;//配置
    private String modelYear;//年款
    private String colorCode;//颜色
    private String trimCode;//内饰
    private String retailFinancial;//零售金融
    private Float depositAmount;//定金金额
    private Date depositDate;//下定日期（YYYY-MM-DD HH24:MI:SS）
    private Integer operationFlag;//操作标识    0：新增 1：修改
    private Integer ecOrderType;//电商订单类型16191001：有效,16181002：期货,16191002,：逾期 16191003：撤销
    private String message;
    
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
     * @return the dealerName
     */
    public String getDealerName() {
        return dealerName;
    }
    
    /**
     * @param dealerName the dealerName to set
     */
    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }
    
    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }
    
    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    /**
     * @return the idCard
     */
    public String getIdCard() {
        return idCard;
    }
    
    /**
     * @param idCard the idCard to set
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    
    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }
    
    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    /**
     * @return the brandCode
     */
    public String getBrandCode() {
        return brandCode;
    }
    
    /**
     * @param brandCode the brandCode to set
     */
    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }
    
    /**
     * @return the seriesCode
     */
    public String getSeriesCode() {
        return seriesCode;
    }
    
    /**
     * @param seriesCode the seriesCode to set
     */
    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }
    
    /**
     * @return the modelCode
     */
    public String getModelCode() {
        return modelCode;
    }
    
    /**
     * @param modelCode the modelCode to set
     */
    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }
    
    /**
     * @return the configCode
     */
    public String getConfigCode() {
        return configCode;
    }
    
    /**
     * @param configCode the configCode to set
     */
    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }
    
    /**
     * @return the modelYear
     */
    public String getModelYear() {
        return modelYear;
    }
    
    /**
     * @param modelYear the modelYear to set
     */
    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }
    
    /**
     * @return the colorCode
     */
    public String getColorCode() {
        return colorCode;
    }
    
    /**
     * @param colorCode the colorCode to set
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
    
    /**
     * @return the trimCode
     */
    public String getTrimCode() {
        return trimCode;
    }
    
    /**
     * @param trimCode the trimCode to set
     */
    public void setTrimCode(String trimCode) {
        this.trimCode = trimCode;
    }
    
    /**
     * @return the retailFinancial
     */
    public String getRetailFinancial() {
        return retailFinancial;
    }
    
    /**
     * @param retailFinancial the retailFinancial to set
     */
    public void setRetailFinancial(String retailFinancial) {
        this.retailFinancial = retailFinancial;
    }
    
    /**
     * @return the depositAmount
     */
    public Float getDepositAmount() {
        return depositAmount;
    }
    
    /**
     * @param depositAmount the depositAmount to set
     */
    public void setDepositAmount(Float depositAmount) {
        this.depositAmount = depositAmount;
    }
    
    /**
     * @return the depositDate
     */
    public Date getDepositDate() {
        return depositDate;
    }
    
    /**
     * @param depositDate the depositDate to set
     */
    public void setDepositDate(Date depositDate) {
        this.depositDate = depositDate;
    }
    
    /**
     * @return the operationFlag
     */
    public Integer getOperationFlag() {
        return operationFlag;
    }
    
    /**
     * @param operationFlag the operationFlag to set
     */
    public void setOperationFlag(Integer operationFlag) {
        this.operationFlag = operationFlag;
    }
    
    /**
     * @return the ecOrderType
     */
    public Integer getEcOrderType() {
        return ecOrderType;
    }
    
    /**
     * @param ecOrderType the ecOrderType to set
     */
    public void setEcOrderType(Integer ecOrderType) {
        this.ecOrderType = ecOrderType;
    }
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    

}
