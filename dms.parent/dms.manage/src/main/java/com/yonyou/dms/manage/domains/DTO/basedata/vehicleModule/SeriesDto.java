
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : SeriesDto.java
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
 *  车系Dto
 * @author yll
 * @date 2016年7月6日
 */

public class SeriesDto {
	private Integer seriesId;//车系id
	private String brandId;//品牌id
	private String  dealerCode;//经销商代码
	@Required
	private String  seriesCode;//车系代码
	@Required
	private String  seriesName;//车系名称
	private Integer OemTag;//oem标志
	private Integer isValid;//是否有效

	public Integer getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
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
