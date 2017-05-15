package com.yonyou.dms.part.domains.DTO.partOrder;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TtPtOrderDetailDcsDTO extends DataImportDto{
	@ExcelColumnDefine(value=1)
	@Required
	private String partCode;
	
	@ExcelColumnDefine(value=2)
	private String partName;
	
	@ExcelColumnDefine(value=3)
	private String orderNum;
	
	private String orderType2; 
	private String partCodes;
	
	
	
	
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrderType2() {
		return orderType2;
	}
	public void setOrderType2(String orderType2) {
		this.orderType2 = orderType2;
	}
	public String getPartCodes() {
		return partCodes;
	}
	public void setPartCodes(String partCodes) {
		this.partCodes = partCodes;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	} 
	
	
}
