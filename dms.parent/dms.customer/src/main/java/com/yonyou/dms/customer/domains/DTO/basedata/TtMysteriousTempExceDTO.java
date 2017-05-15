package com.yonyou.dms.customer.domains.DTO.basedata;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;

public class TtMysteriousTempExceDTO extends DataImportDto{
	@ExcelColumnDefine(value = 4)
	private String phone;
	@ExcelColumnDefine(value = 2)
	private String dealerName;
	@ExcelColumnDefine(value = 3)
	private String execAuthor;
	private Long tempId;
	@ExcelColumnDefine(value = 1)
	private String dealerCode;
	
	private Integer errorRow;

	private String  errorReson ;
	
	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setDealerName(String dealerName){
		this.dealerName=dealerName;
	}

	public String getDealerName(){
		return this.dealerName;
	}

	public void setExecAuthor(String execAuthor){
		this.execAuthor=execAuthor;
	}

	public String getExecAuthor(){
		return this.execAuthor;
	}

	public void setTempId(Long tempId){
		this.tempId=tempId;
	}

	public Long getTempId(){
		return this.tempId;
	}

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}

	public Integer getErrorRow() {
		return errorRow;
	}

	public void setErrorRow(Integer errorRow) {
		this.errorRow = errorRow;
	}

	public String getErrorReson() {
		return errorReson;
	}

	public void setErrorReson(String errorReson) {
		this.errorReson = errorReson;
	}	
}
