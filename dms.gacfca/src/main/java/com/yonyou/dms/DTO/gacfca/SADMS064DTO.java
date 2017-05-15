package com.yonyou.dms.DTO.gacfca;

import java.util.Date;
import java.util.LinkedList;

public class SADMS064DTO {
	private String dealerCode;
	private String vin;
	private Date pdiCheckDate;
	private Date pdiSubmitDate;
	private Integer pdiResult;
	private String pdiUrl;
	private String thReportNo;
	private Double mileage;
	private LinkedList<TtVehiclePdiResultDetailDTO>  vprdList;
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
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
	public LinkedList<TtVehiclePdiResultDetailDTO> getVprdList() {
		return vprdList;
	}
	public void setVprdList(LinkedList<TtVehiclePdiResultDetailDTO> vprdList) {
		this.vprdList = vprdList;
	}
}
