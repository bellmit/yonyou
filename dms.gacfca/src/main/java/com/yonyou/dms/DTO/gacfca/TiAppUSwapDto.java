
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : TiAppUSwapDto.java
*
* @Author : Administrator
*
* @Date : 2017年3月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月7日    Administrator    1.0
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
* @author Administrator
* @date 2017年3月7日
*/

public class TiAppUSwapDto {
	
	private Long swapId;
    private String uniquenessID;//DMS客户唯一ID
    private Integer FCAID;//售中APP的客户ID
    private String ownBrandID;//现有品牌
    private String ownModelID;//现有车型
    private String ownCarStyleID;//现有车款
    private String VINCode;//现有的车辆VIN  
    private Date licenceIssueDate;//上牌时间
    private Long travlledDistance;//里程数
    private Long isEstimated;//是否评估
    private Long estimatedPrice;//评估金额
    private String estimatedOne;//评估报告1
    private String estimatedTwo;//评估报告2
    private String driveLicense;//旧车行驶证
    private Date updateDate;
    private String dealerUserID;//销售人员的ID
    private String dealerCode;
    private String ownCarColor;//现有车色
    
  
    public Long getSwapId() {
		return swapId;
	}

	public void setSwapId(Long swapId) {
		this.swapId = swapId;
	}

	public String getUniquenessID() {
        return uniquenessID;
    }
    
    public void setUniquenessID(String uniquenessID) {
        this.uniquenessID = uniquenessID;
    }
    
    public Integer getFCAID() {
        return FCAID;
    }
    
    public void setFCAID(Integer fCAID) {
        FCAID = fCAID;
    }
    
    public String getOwnBrandID() {
        return ownBrandID;
    }
    
    public void setOwnBrandID(String ownBrandID) {
        this.ownBrandID = ownBrandID;
    }
    
    public String getOwnModelID() {
        return ownModelID;
    }
    
    public void setOwnModelID(String ownModelID) {
        this.ownModelID = ownModelID;
    }
    
    public String getOwnCarStyleID() {
        return ownCarStyleID;
    }
    
    public void setOwnCarStyleID(String ownCarStyleID) {
        this.ownCarStyleID = ownCarStyleID;
    }
    
    public String getVINCode() {
        return VINCode;
    }
    
    public void setVINCode(String vINCode) {
        VINCode = vINCode;
    }
    
    public Date getLicenceIssueDate() {
        return licenceIssueDate;
    }
    
    public void setLicenceIssueDate(Date licenceIssueDate) {
        this.licenceIssueDate = licenceIssueDate;
    }
    
    public Long getTravlledDistance() {
        return travlledDistance;
    }
    
    public void setTravlledDistance(Long travlledDistance) {
        this.travlledDistance = travlledDistance;
    }
    
    public Long getIsEstimated() {
        return isEstimated;
    }
    
    public void setIsEstimated(Long isEstimated) {
        this.isEstimated = isEstimated;
    }
    
    public Long getEstimatedPrice() {
        return estimatedPrice;
    }
    
    public void setEstimatedPrice(Long estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }
    
    public String getEstimatedOne() {
        return estimatedOne;
    }
    
    public void setEstimatedOne(String estimatedOne) {
        this.estimatedOne = estimatedOne;
    }
    
    public String getEstimatedTwo() {
        return estimatedTwo;
    }
    
    public void setEstimatedTwo(String estimatedTwo) {
        this.estimatedTwo = estimatedTwo;
    }
    
    public String getDriveLicense() {
        return driveLicense;
    }
    
    public void setDriveLicense(String driveLicense) {
        this.driveLicense = driveLicense;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    public String getDealerUserID() {
        return dealerUserID;
    }
    
    public void setDealerUserID(String dealerUserID) {
        this.dealerUserID = dealerUserID;
    }
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getOwnCarColor() {
        return ownCarColor;
    }
    
    public void setOwnCarColor(String ownCarColor) {
        this.ownCarColor = ownCarColor;
    }
    
}
