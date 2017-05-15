package com.yonyou.dms.vehicle.domains.DTO.retailReportQuery;


import java.util.Date;

import javax.validation.constraints.Past;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.VIN;

/**
 * 车辆补传
 * @author DC
 *
 */
public class TmpVsNvdrDTO extends DataImportDto {

	@ExcelColumnDefine(value = 2)
	@Past
	private Date uploadDate;
	
	@ExcelColumnDefine(value = 1)
	@VIN
	private String vin;

	public void setUploadDate(Date uploadDate){
		this.uploadDate=uploadDate;
	}

	public Date getUploadDate(){
		return this.uploadDate;
	}

	public void setVin(String vin){
		this.vin=vin;
	}

	public String getVin(){
		return this.vin;
	}

}