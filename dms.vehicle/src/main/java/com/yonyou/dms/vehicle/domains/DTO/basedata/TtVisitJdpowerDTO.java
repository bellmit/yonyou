package com.yonyou.dms.vehicle.domains.DTO.basedata;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TtVisitJdpowerDTO extends DataImportDto{
	@ExcelColumnDefine(value = 1)
	@Required
	private String dealerCode;
	
	@ExcelColumnDefine(value = 2)
	@Required
	private String linkName;
	
	@ExcelColumnDefine(value = 3)
	private String linkPhone;
	
	@ExcelColumnDefine(value = 4)
	@Required
	private String vin;
	
	@ExcelColumnDefine(value = 5)
	private Date backDate;
	
	@ExcelColumnDefine(value = 6)
	private String backType;
	@ExcelColumnDefine(value = 7)
	private String backContent;
	@ExcelColumnDefine(value = 8)
	@Required
	private String VisitType;
	@ExcelColumnDefine(value = 9)
	@Required
	private String visitSource;
	
	private String resultContent;
	
	private String disposeName;
	
	private String disposeResult;
	
	
	
	public String getResultContent() {
		return resultContent;
	}
	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}
	public String getDisposeName() {
		return disposeName;
	}
	public void setDisposeName(String disposeName) {
		this.disposeName = disposeName;
	}
	public String getDisposeResult() {
		return disposeResult;
	}
	public void setDisposeResult(String disposeResult) {
		this.disposeResult = disposeResult;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Date getBackDate() {
		return backDate;
	}
	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}
	public String getBackType() {
		return backType;
	}
	public void setBackType(String backType) {
		this.backType = backType;
	}
	public String getBackContent() {
		return backContent;
	}
	public void setBackContent(String backContent) {
		this.backContent = backContent;
	}
	public String getVisitType() {
		return VisitType;
	}
	public void setVisitType(String visitType) {
		VisitType = visitType;
	}
	public String getVisitSource() {
		return visitSource;
	}
	public void setVisitSource(String visitSource) {
		this.visitSource = visitSource;
	}
	
}
