package com.yonyou.dms.vehicle.domains.DTO.activityManage;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
* @author liujm
* @date 2017年4月18日
*/
public class TmpWrActivityVehicleDcsImportDTO extends DataImportDto{
	
	
	@ExcelColumnDefine(value = 1)
	@Required
	private String vin;
	
	@ExcelColumnDefine(value = 2)
	@Required
	private String dealerCode;
	
	@ExcelColumnDefine(value = 3)
	@Required
	private String linkMan;
	
	@ExcelColumnDefine(value = 4)
	@Required
	private String linkPhone;

	
	
	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	
	
	
}
