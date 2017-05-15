package com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TmpWrOperateDTO extends DataImportDto{
	
	@ExcelColumnDefine(value=1)
	@Required
	private String optCode;
	@ExcelColumnDefine(value=2)
	@Required
	private String optNameCn;
	private String lineNo;
	private Date createDate;
	private Long createBy;

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}


	public void setOptCode(String optCode){
		this.optCode=optCode;
	}

	public String getOptCode(){
		return this.optCode;
	}

	public void setOptNameCn(String optNameCn){
		this.optNameCn=optNameCn;
	}

	public String getOptNameCn(){
		return this.optNameCn;
	}

}
