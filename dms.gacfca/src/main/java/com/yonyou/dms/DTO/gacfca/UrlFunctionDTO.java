package com.yonyou.dms.DTO.gacfca;

public class UrlFunctionDTO  {
	
	private String dealerCode; //
	private String functionName; //下端：功能名称    上端：  
	private String url; //下端：URL    上端：  
	private String DcsFunctionId; //下端：上端功能ID    上端：  
	private String parentFunctionCode; //下端：下端父菜单编号   41150000 整车销售（一级菜单）
	private Integer sort; //下端：序号    上端：  
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDcsFunctionId() {
		return DcsFunctionId;
	}
	public void setDcsFunctionId(String dcsFunctionId) {
		DcsFunctionId = dcsFunctionId;
	}
	public String getParentFunctionCode() {
		return parentFunctionCode;
	}
	public void setParentFunctionCode(String parentFunctionCode) {
		this.parentFunctionCode = parentFunctionCode;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
