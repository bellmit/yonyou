package com.yonyou.dms.vehicle.domains.DTO.allot;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.VIN;

public class ResourceAllotImport2Dto extends DataImportDto{
	
	@ExcelColumnDefine(value = 1)
	@VIN
	private String vin;
	
	@ExcelColumnDefine(value = 2)
	private String dealerCode;
	
	@ExcelColumnDefine(value = 3)
	private Integer isSupport;

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

	public Integer getIsSupport() {
		return isSupport;
	}

	public void setIsSupport(Integer isSupport) {
		this.isSupport = isSupport;
	}
	
	

}
