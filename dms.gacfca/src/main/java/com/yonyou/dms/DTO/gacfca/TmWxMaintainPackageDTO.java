package com.yonyou.dms.DTO.gacfca;

import java.util.Date;
import java.util.LinkedList;

public class TmWxMaintainPackageDTO {
	private Integer oemType;// 0是车厂,1是经销商
	private Double maintainEndmileage;// 保养结束里程
	private Date maintainEnddate;// 保养开始日期
	private Date maintainStartdate;// 保养结束日期
	private Double maintainStartmileage;// 保养开始里程
	private String dealerCode;// 经销商代码(这个其实是entityCode,BaseVo里面有这个字段，这个字段不是必须的)
	private Integer oileType;// 燃油类型
	private String packageCode;// 套餐代码
	private Integer packageType;// 0配件作为套餐,1正常套餐
	private Long oemCompanyId;// 公司ID
	private Double totalAmount;// 套餐金额
	private String seriesCode;// 车系code
	private String modelYear;// 年款
	private String packageName;// 套餐名称
	private String engineDesc;// 发动机描述
	private Date createDate;// 套餐创建时间
	private LinkedList<TmWxMaintainLabourDTO> tmWxMaintainLabourDTOs;// 工时列表
	private LinkedList<TmWxMaintainPartDTO> tmWxMaintainPartDTOs;// 配件列表

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getEngineDesc() {
		return engineDesc;
	}
	public void setEngineDesc(String engineDesc) {
		this.engineDesc = engineDesc;
	}

	public Double getMaintainEndmileage() {
		return maintainEndmileage;
	}
	public void setMaintainEndmileage(Double maintainEndmileage) {
		this.maintainEndmileage = maintainEndmileage;
	}

	public Double getMaintainStartmileage() {
		return maintainStartmileage;
	}
	public void setMaintainStartmileage(Double maintainStartmileage) {
		this.maintainStartmileage = maintainStartmileage;
	}
	public String getModelYear() {
		return modelYear;
	}
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
	
	public Long getOemCompanyId() {
		return oemCompanyId;
	}
	public void setOemCompanyId(Long oemCompanyId) {
		this.oemCompanyId = oemCompanyId;
	}
	public Integer getOileType() {
		return oileType;
	}
	public void setOileType(Integer oileType) {
		this.oileType = oileType;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Date getMaintainEnddate() {
		return maintainEnddate;
	}
	public void setMaintainEnddate(Date maintainEnddate) {
		this.maintainEnddate = maintainEnddate;
	}
	public Date getMaintainStartdate() {
		return maintainStartdate;
	}
	public void setMaintainStartdate(Date maintainStartdate) {
		this.maintainStartdate = maintainStartdate;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public Integer getOemType() {
		return oemType;
	}
	public void setOemType(Integer oemType) {
		this.oemType = oemType;
	}
	public Integer getPackageType() {
		return packageType;
	}
	public void setPackageType(Integer packageType) {
		this.packageType = packageType;
	}
	public LinkedList<TmWxMaintainLabourDTO> getTmWxMaintainLabourDTOs() {
		return tmWxMaintainLabourDTOs;
	}
	public void setTmWxMaintainLabourDTOs(LinkedList<TmWxMaintainLabourDTO> tmWxMaintainLabourDTOs) {
		this.tmWxMaintainLabourDTOs = tmWxMaintainLabourDTOs;
	}
	public LinkedList<TmWxMaintainPartDTO> getTmWxMaintainPartDTOs() {
		return tmWxMaintainPartDTOs;
	}
	public void setTmWxMaintainPartDTOs(LinkedList<TmWxMaintainPartDTO> tmWxMaintainPartDTOs) {
		this.tmWxMaintainPartDTOs = tmWxMaintainPartDTOs;
	}
}
