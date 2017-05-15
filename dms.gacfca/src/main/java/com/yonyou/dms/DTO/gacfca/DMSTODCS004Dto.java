/**
 * 
 */
package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class DMSTODCS004Dto {
	private static final long serialVersionUID = 1L;
	private String modelCode;//车型代码 
	private String modelYear;//年款
	private String dealerCode;//DMS端经销商代码
	private String trimCode;//内饰代码
	private String ecOrderNo;//电商订单号
	private String seriesCode;//车系代码
	private String brandCode;//品牌代码
	private String colorCode;//颜色代码
	private String groupCode;//车款代码
	private Date callDate;//CALL车日期
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getModelYear() {
		return modelYear;
	}
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getTrimCode() {
		return trimCode;
	}
	public void setTrimCode(String trimCode) {
		this.trimCode = trimCode;
	}
	public String getEcOrderNo() {
		return ecOrderNo;
	}
	public void setEcOrderNo(String ecOrderNo) {
		this.ecOrderNo = ecOrderNo;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public Date getCallDate() {
		return callDate;
	}
	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
