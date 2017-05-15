package com.infoeai.eai.DTO;

public class SI39DTO {
	
	private String serialNumber;//生产序列号
    private String taskId;//生产任务编号
    private String createDate;//日期
    private String ctcaiCode;//中进代码
    private String companyName;//公司名称
    private String forecastYear;//年
    private String forecastMonth;//月
    private String modelCode;//车型
    private String colorCode;//颜色
    private String trimCode;//内饰
    private Long orderSerialNumberId;//主键
    
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCtcaiCode() {
		return ctcaiCode;
	}
	public void setCtcaiCode(String ctcaiCode) {
		this.ctcaiCode = ctcaiCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getForecastYear() {
		return forecastYear;
	}
	public void setForecastYear(String forecastYear) {
		this.forecastYear = forecastYear;
	}
	public String getForecastMonth() {
		return forecastMonth;
	}
	public void setForecastMonth(String forecastMonth) {
		this.forecastMonth = forecastMonth;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getTrimCode() {
		return trimCode;
	}
	public void setTrimCode(String trimCode) {
		this.trimCode = trimCode;
	}
	public Long getOrderSerialNumberId() {
		return orderSerialNumberId;
	}
	public void setOrderSerialNumberId(Long orderSerialNumberId) {
		this.orderSerialNumberId = orderSerialNumberId;
	}
    
    

}
