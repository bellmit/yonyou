package com.yonyou.dms.vehicle.domains.DTO.orderManager;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;
import com.yonyou.dms.function.utils.validate.define.VIN;

public class ResourceOrderUploadDTO extends DataImportDto {
	@ExcelColumnDefine(value = 1)
	@VIN
	@Required
	private String vin;
	@ExcelColumnDefine(value = 2)
	@Required
	private String resourceScope;
	@ExcelColumnDefine(value = 3)
	@Required
	private Integer resourceType;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getResourceScope() {
		return resourceScope;
	}

	public void setResourceScope(String resourceScope) {
		this.resourceScope = resourceScope;
	}

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

}
