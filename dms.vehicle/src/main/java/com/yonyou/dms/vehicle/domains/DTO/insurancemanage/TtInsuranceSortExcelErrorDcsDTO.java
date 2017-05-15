package com.yonyou.dms.vehicle.domains.DTO.insurancemanage;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TtInsuranceSortExcelErrorDcsDTO extends DataImportDto{
	
	private String dealerName;
	private Integer errorRow;
	@ExcelColumnDefine(value=2)
	@Required
	private String insuranceSortName;
	private Integer oemTag;
	@ExcelColumnDefine(value=1)
	@Required
	private String insuranceSortCode;
	private Long errorId;
	private String dealerCode;
	private String errorReason;
	@ExcelColumnDefine(value=3)
	@Required
	private String isComInsurance;
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public Integer getErrorRow() {
		return errorRow;
	}
	public void setErrorRow(Integer errorRow) {
		this.errorRow = errorRow;
	}
	public String getInsuranceSortName() {
		return insuranceSortName;
	}
	public void setInsuranceSortName(String insuranceSortName) {
		this.insuranceSortName = insuranceSortName;
	}
	public Integer getOemTag() {
		return oemTag;
	}
	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
	}
	public String getInsuranceSortCode() {
		return insuranceSortCode;
	}
	public void setInsuranceSortCode(String insuranceSortCode) {
		this.insuranceSortCode = insuranceSortCode;
	}
	public Long getErrorId() {
		return errorId;
	}
	public void setErrorId(Long errorId) {
		this.errorId = errorId;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public String getIsComInsurance() {
		return isComInsurance;
	}
	public void setIsComInsurance(String isComInsurance) {
		this.isComInsurance = isComInsurance;
	}
	
}
