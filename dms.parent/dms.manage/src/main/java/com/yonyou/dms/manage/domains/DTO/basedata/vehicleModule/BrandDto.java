
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : BrandDto.java
 *
 * @Author : yll
 *
 * @Date : 2016年7月6日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月6日    yll         1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.domains.DTO.basedata.vehicleModule;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 品牌dto
 * @author yll
 * @date 2016年7月6日
 */

public class BrandDto {
	private Integer brandId;
	private String  dealerCode;
	@Required
	private String  brandCode;
	@Required
	private String  brandName;
	private Integer OemTag;
	private Integer isValid;
	

	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
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
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
