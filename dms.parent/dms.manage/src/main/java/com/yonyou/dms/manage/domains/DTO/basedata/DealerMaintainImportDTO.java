package com.yonyou.dms.manage.domains.DTO.basedata;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class DealerMaintainImportDTO extends DataImportDto {
	@ExcelColumnDefine(value=1)
	@Required
	private String dealerCode;
	@ExcelColumnDefine(value=2)
	@Required
	private String brandCode;
	@ExcelColumnDefine(value=3)
	@Required
	private String groupCode;
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
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	

}
