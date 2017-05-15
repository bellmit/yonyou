package com.yonyou.dms.vehicle.domains.DTO.oldPart;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;

public class TmpUrgencyVinImpDTO extends DataImportDto{
	@ExcelColumnDefine(value=1)
	private String vin;
	private Long createBy;
	private Date createDate;
	private String errorDesc;

	public void setVin(String vin){
		this.vin=vin;
	}

	public String getVin(){
		return this.vin;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setErrorDesc(String errorDesc){
		this.errorDesc=errorDesc;
	}

	public String getErrorDesc(){
		return this.errorDesc;
	}

}
