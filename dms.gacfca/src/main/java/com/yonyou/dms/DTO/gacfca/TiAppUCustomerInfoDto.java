
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : TiAppUCustomerInfoDto.java
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

public class TiAppUCustomerInfoDto {
	
	private Long customerId;
	private String dealerCode;
    private String entityCode;
    private String uniquenessID;//DMS客户唯一ID
    private Integer FCAID;//售中APP的客户ID
    private String clientType;//客户类型
    private String name;//客户的姓名
    private String gender;//性别
    private String phone;//手机号
    private String telephone;//手机号//固定电话
    private Integer provinceID;//省份ID
    private Integer cityID;//城市ID
    private Date birthday;//生日
    private String oppLevelID;//客户级别ID
    private String sourceType;//客户来源
    private String secondSourceType;//客户来源
    private String dealerUserID;//销售人员的ID
    private String buyCarBudget;//购车预算
    private String brandID;//品牌ID
    private String modelID;//车型ID
    private String carStyleID;//车款ID
    private String intentCarColor;//车辆颜色ID
    private Integer buyCarcondition;//购车类型
    private Date updateDate;//更新时间

    private String giveUpType;//休眠类型
    private String giveUpCause;//休眠原因
    private String contendCar;//竞品车型
    private Date giveUpDate;//休眠时间
    private Integer isToShop;//是否到店  2016-6-17 潜客改造
    private Date timeToShop;//到店时间
    
    
    public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getEntityCode() {
        return entityCode;
    }
    
    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
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

	public String getClientType() {
        return clientType;
    }
    
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public Integer getProvinceID() {
        return provinceID;
    }
    
    public void setProvinceID(Integer provinceID) {
        this.provinceID = provinceID;
    }
    
    public Integer getCityID() {
		return cityID;
	}

	public void setCityID(Integer cityID) {
		this.cityID = cityID;
	}

	public Date getBirthday() {
        return birthday;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public String getOppLevelID() {
        return oppLevelID;
    }
    
    public void setOppLevelID(String oppLevelID) {
        this.oppLevelID = oppLevelID;
    }
    
    public String getSourceType() {
        return sourceType;
    }
    
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
    
    public String getSecondSourceType() {
        return secondSourceType;
    }
    
    public void setSecondSourceType(String secondSourceType) {
        this.secondSourceType = secondSourceType;
    }
    
    public String getDealerUserID() {
        return dealerUserID;
    }
    
    public void setDealerUserID(String dealerUserID) {
        this.dealerUserID = dealerUserID;
    }
    
    public String getBuyCarBudget() {
        return buyCarBudget;
    }
    
    public void setBuyCarBudget(String buyCarBudget) {
        this.buyCarBudget = buyCarBudget;
    }
    
    public String getBrandID() {
        return brandID;
    }
    
    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }
    
    public String getModelID() {
        return modelID;
    }
    
    public void setModelID(String modelID) {
        this.modelID = modelID;
    }
    
    public String getCarStyleID() {
        return carStyleID;
    }
    
    public void setCarStyleID(String carStyleID) {
        this.carStyleID = carStyleID;
    }
    
    public String getIntentCarColor() {
        return intentCarColor;
    }
    
    public void setIntentCarColor(String intentCarColor) {
        this.intentCarColor = intentCarColor;
    }
    
    public Integer getBuyCarcondition() {
        return buyCarcondition;
    }
    
    public void setBuyCarcondition(Integer buyCarcondition) {
        this.buyCarcondition = buyCarcondition;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    public String getGiveUpType() {
        return giveUpType;
    }
    
    public void setGiveUpType(String giveUpType) {
        this.giveUpType = giveUpType;
    }
    
    public String getGiveUpCause() {
        return giveUpCause;
    }
    
    public void setGiveUpCause(String giveUpCause) {
        this.giveUpCause = giveUpCause;
    }
    
    public String getContendCar() {
        return contendCar;
    }
    
    public void setContendCar(String contendCar) {
        this.contendCar = contendCar;
    }
    
    public Date getGiveUpDate() {
        return giveUpDate;
    }
    
    public void setGiveUpDate(Date giveUpDate) {
        this.giveUpDate = giveUpDate;
    }
    
    public Integer getIsToShop() {
        return isToShop;
    }
    
    public void setIsToShop(Integer isToShop) {
        this.isToShop = isToShop;
    }
    
    public Date getTimeToShop() {
        return timeToShop;
    }
    
    public void setTimeToShop(Date timeToShop) {
        this.timeToShop = timeToShop;
    }
    
    
}
