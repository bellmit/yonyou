package com.yonyou.dms.vehicle.domains.DTO.oldPart;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;

public class TmpOldpartImpDTO  extends DataImportDto{
	@ExcelColumnDefine(value=2)
	private String partName;
	private Long createBy;
	@ExcelColumnDefine(value=1)
	private String partNo;
	private Date createDate;
	private String errorDesc;

	public void setPartName(String partName){
		this.partName=partName;
	}

	public String getPartName(){
		return this.partName;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setPartNo(String partNo){
		this.partNo=partNo;
	}

	public String getPartNo(){
		return this.partNo;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
}
