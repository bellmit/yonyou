package com.yonyou.dms.vehicle.domains.DTO.orderManager;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;
import com.yonyou.dms.function.utils.validate.define.VIN;

public class RemarkImpDto extends DataImportDto {
	@ExcelColumnDefine(value = 1)
	@VIN
	private String vin;
	@ExcelColumnDefine(value = 2)
	@Required
	private String remark;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
