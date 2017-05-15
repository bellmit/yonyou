package com.infoservice.dms.cgcsl.vo;

public class SEDCS015VO extends BaseVO  {
	
	private static final long serialVersionUID = 1L;
	private String manhourCode;//工时代码
	private String manhourName;//工时中文名称
	private String manhourEnglishName;//工时英文名称
	private String manhourNum;//标准工时数
	private String seriesCode;//车系
	private String modelCode;//车型
	private String modelYear;//年款
	private String oneCode;//一级分类代码
	private String twoCode;//二级分类代码
	private String threeCode;//三级分类代码
	private String fourCode;//四级分类代码
	private String groupType;//分类类型
	private String cliamNum;//索赔工时数
	
	public String getCliamNum() {
		return cliamNum;
	}
	public void setCliamNum(String cliamNum) {
		this.cliamNum = cliamNum;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getOneCode() {
		return oneCode;
	}
	public void setOneCode(String oneCode) {
		this.oneCode = oneCode;
	}
	public String getTwoCode() {
		return twoCode;
	}
	public void setTwoCode(String twoCode) {
		this.twoCode = twoCode;
	}
	public String getThreeCode() {
		return threeCode;
	}
	public void setThreeCode(String threeCode) {
		this.threeCode = threeCode;
	}
	public String getFourCode() {
		return fourCode;
	}
	public void setFourCode(String fourCode) {
		this.fourCode = fourCode;
	}
	public String getManhourCode() {
		return manhourCode;
	}
	public void setManhourCode(String manhourCode) {
		this.manhourCode = manhourCode;
	}
	public String getManhourName() {
		return manhourName;
	}
	public void setManhourName(String manhourName) {
		this.manhourName = manhourName;
	}
	public String getManhourEnglishName() {
		return manhourEnglishName;
	}
	public void setManhourEnglishName(String manhourEnglishName) {
		this.manhourEnglishName = manhourEnglishName;
	}
	public String getManhourNum() {
		return manhourNum;
	}
	public void setManhourNum(String manhourNum) {
		this.manhourNum = manhourNum;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public String getModelYear() {
		return modelYear;
	}
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
}
