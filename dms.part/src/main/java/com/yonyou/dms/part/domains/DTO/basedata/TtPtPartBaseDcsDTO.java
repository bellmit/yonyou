package com.yonyou.dms.part.domains.DTO.basedata;

import java.math.BigDecimal;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
* 配件表
* @author ZhaoZ
* @date 2017年3月23日
*/
public class TtPtPartBaseDcsDTO extends DataImportDto{

	private BigDecimal id;
	
	@ExcelColumnDefine(value=1)
	@Required
	private String partCode;
	
	@ExcelColumnDefine(value=2)
	private String partName;
	
	private String partProperty;
	
	private String isPurchase;
	
	private String isSales;
	
	private Double partPrice;
	
	private Double dnpPrice;
	
	@ExcelColumnDefine(value=3)
	private String wbp;
	
	private Integer packageNum;
	
	private String partNuit;
	
	private String isNormal;
	
	private String modelCodes;
	
	private String oldPartCode;
	
	private String remark;
	
	
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartProperty() {
		return partProperty;
	}

	public void setPartProperty(String partProperty) {
		this.partProperty = partProperty;
	}

	public String getIsPurchase() {
		return isPurchase;
	}

	public void setIsPurchase(String isPurchase) {
		this.isPurchase = isPurchase;
	}

	public String getIsSales() {
		return isSales;
	}

	public void setIsSales(String isSales) {
		this.isSales = isSales;
	}

	public Double getPartPrice() {
		return partPrice;
	}

	public void setPartPrice(Double partPrice) {
		this.partPrice = partPrice;
	}

	public Double getDnpPrice() {
		return dnpPrice;
	}

	public void setDnpPrice(Double dnpPrice) {
		this.dnpPrice = dnpPrice;
	}

	public String getWbp() {
		return wbp;
	}

	public void setWbp(String wbp) {
		this.wbp = wbp;
	}

	public Integer getPackageNum() {
		return packageNum;
	}

	public void setPackageNum(Integer packageNum) {
		this.packageNum = packageNum;
	}

	public String getPartNuit() {
		return partNuit;
	}

	public void setPartNuit(String partNuit) {
		this.partNuit = partNuit;
	}

	public String getIsNormal() {
		return isNormal;
	}

	public void setIsNormal(String isNormal) {
		this.isNormal = isNormal;
	}

	public String getModelCodes() {
		return modelCodes;
	}

	public void setModelCodes(String modelCodes) {
		this.modelCodes = modelCodes;
	}

	public String getOldPartCode() {
		return oldPartCode;
	}

	public void setOldPartCode(String oldPartCode) {
		this.oldPartCode = oldPartCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}
