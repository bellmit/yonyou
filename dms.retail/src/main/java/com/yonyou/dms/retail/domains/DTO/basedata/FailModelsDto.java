
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.retail
 *
 * @File name : FailModelsDto.java
 *
 * @Author : yll
 *
 * @Date : 2016年7月5日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月5日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */
package com.yonyou.dms.retail.domains.DTO.basedata;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 
 * 战败车型信息Dto
 * @author yll
 * @date 2016年6月30日
 */
public class FailModelsDto {
	private Integer failModelId;
	private String  dealerCode;//经销商代码
	private String  intentSeries;//意向车系
	private String  intentBrand;//意向品牌
	@Required
	private String  failModel;//战败车型
	@Required
	private String  failModelDesc;//战败车型描述
	private Integer OemTag;//oem标志
	private Integer isValid;//是否有效




	public Integer getFailModelId() {
		return failModelId;
	}
	public void setFailModelId(Integer failModelId) {
		this.failModelId = failModelId;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode == null ? null : dealerCode.trim();
	}
	
	public String getIntentSeries() {
		return intentSeries;
	}
	public void setIntentSeries(String intentSeries) {
		this.intentSeries = intentSeries;
	}
	public String getFailModel() {
		return failModel;
	}
	public void setFailModel(String failModel) {
		this.failModel = failModel== null ? null : failModel.trim();
	}
	public String getFailModelDesc() {
		return failModelDesc;
	}
	public void setFailModelDesc(String failModelDesc) {
		this.failModelDesc = failModelDesc;
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
    public String getIntentBrand() {
        return intentBrand;
    }
    public void setIntentBrand(String intentBrand) {
        this.intentBrand = intentBrand;
    }









}
