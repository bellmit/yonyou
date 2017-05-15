package com.infoservice.dms.cgcsl.vo;

import java.util.Date;
import java.util.LinkedList;


public class TtVehiclePdiResultVO extends BaseVO{
	
	private static final long serialVersionUID = 1L;
	private String vin;//VIN码
	private Date pdiCheckDate;//PDI检查时间
	private Date pdiSubmitDate;//PDI检查提交时间
	private Integer pdiResult;//PDI检查结果 70161001  通过   70161002  不通过
	private String pdiUrl;//PDI检查结果附件URL
	private String thReportNo;//技术报告号
	private Double mileage;//公里数
	private LinkedList<TtVehiclePdiResultDetailVO> vprdList;
	
	public LinkedList<TtVehiclePdiResultDetailVO> getVprdList() {
		return vprdList;
	}
	public void setVprdList(LinkedList<TtVehiclePdiResultDetailVO> vprdList) {
		this.vprdList = vprdList;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Date getPdiCheckDate() {
		return pdiCheckDate;
	}
	public void setPdiCheckDate(Date pdiCheckDate) {
		this.pdiCheckDate = pdiCheckDate;
	}
	public Date getPdiSubmitDate() {
		return pdiSubmitDate;
	}
	public void setPdiSubmitDate(Date pdiSubmitDate) {
		this.pdiSubmitDate = pdiSubmitDate;
	}
	public Integer getPdiResult() {
		return pdiResult;
	}
	public void setPdiResult(Integer pdiResult) {
		this.pdiResult = pdiResult;
	}
	public String getPdiUrl() {
		return pdiUrl;
	}
	public void setPdiUrl(String pdiUrl) {
		this.pdiUrl = pdiUrl;
	}
	public String getThReportNo() {
		return thReportNo;
	}
	public void setThReportNo(String thReportNo) {
		this.thReportNo = thReportNo;
	}
	public Double getMileage() {
		return mileage;
	}
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	
}
