package com.yonyou.dms.vehicle.domains.DTO.insurancemanage;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TtInsuranceCompanyExcelTempDcsDTO extends DataImportDto{
	
	private String dealerName;
	@ExcelColumnDefine(value=1)
	@Required
	private String insuranceCompanyCode;
	private Integer oemTag;
	private String execAuthor;
	private Long tempId;
	private String dealerCode;
	@ExcelColumnDefine(value=2)
	@Required
	private String insuranceCompanyName;
	@ExcelColumnDefine(value=3)
	@Required
	private String insCompanyShortName;
	@ExcelColumnDefine(value=4)
	@Required
	private String isCoInsuranceCompany;
	private Integer errorRow;
	private Long errorId;
	private String errorReason;
	
	public Integer getErrorRow() {
		return errorRow;
	}
	public void setErrorRow(Integer errorRow) {
		this.errorRow = errorRow;
	}
	public Long getErrorId() {
		return errorId;
	}
	public void setErrorId(Long errorId) {
		this.errorId = errorId;
	}
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getInsuranceCompanyCode() {
		return insuranceCompanyCode;
	}
	public void setInsuranceCompanyCode(String insuranceCompanyCode) {
		this.insuranceCompanyCode = insuranceCompanyCode;
	}
	public Integer getOemTag() {
		return oemTag;
	}
	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
	}
	public String getExecAuthor() {
		return execAuthor;
	}
	public void setExecAuthor(String execAuthor) {
		this.execAuthor = execAuthor;
	}
	public Long getTempId() {
		return tempId;
	}
	public void setTempId(Long tempId) {
		this.tempId = tempId;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}
	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}
	public String getInsCompanyShortName() {
		return insCompanyShortName;
	}
	public void setInsCompanyShortName(String insCompanyShortName) {
		this.insCompanyShortName = insCompanyShortName;
	}
	public String getIsCoInsuranceCompany() {
		return isCoInsuranceCompany;
	}
	public void setIsCoInsuranceCompany(String isCoInsuranceCompany) {
		this.isCoInsuranceCompany = isCoInsuranceCompany;
	}
	
}
