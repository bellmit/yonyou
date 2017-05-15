package com.yonyou.dms.vehicle.domains.DTO.allot;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.VIN;

public class ResourceAllotImportDto extends DataImportDto{
	
	@ExcelColumnDefine(value = 1)
	@VIN
	private String vin;
	
	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}
	
	

}
