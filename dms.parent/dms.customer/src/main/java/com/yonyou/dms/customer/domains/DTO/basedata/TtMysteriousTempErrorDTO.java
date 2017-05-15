package com.yonyou.dms.customer.domains.DTO.basedata;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;

public class TtMysteriousTempErrorDTO extends DataImportDto{
	@ExcelColumnDefine(value = 4)
	private String phone;
	@ExcelColumnDefine(value = 2)
	private String dealerName;
	private Integer errorRow;
	@ExcelColumnDefine(value = 3)
	private String execAuthor;
	private Long errorId;
	@ExcelColumnDefine(value = 1)
	private String dealerCode;
	private String errorReason;

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

	public void setErrorRow(Integer errorRow){
		this.errorRow=errorRow;
	}

	public Integer getErrorRow(){
		return this.errorRow;
	}

	public void setExecAuthor(String execAuthor){
		this.execAuthor=execAuthor;
	}

	public String getExecAuthor(){
		return this.execAuthor;
	}

	public void setErrorId(Long errorId){
		this.errorId=errorId;
	}

	public Long getErrorId(){
		return this.errorId;
	}

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}

	public void setErrorReason(String errorReason){
		this.errorReason=errorReason;
	}

	public String getErrorReason(){
		return this.errorReason;
	}

}
