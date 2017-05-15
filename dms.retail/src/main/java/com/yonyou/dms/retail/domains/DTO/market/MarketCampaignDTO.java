package com.yonyou.dms.retail.domains.DTO.market;

import java.util.Date;

/**
 * 活动执行总结DTO
 * @author Benzc
 * @date 2017年2月24日
 */
public class MarketCampaignDTO {
	
	private String campaignCode;
	private Double applyPrice;
	private String applyMemo;
	private String camEffect;
	private String camRival;
	private String camGain;
	private String camLack;
    private Integer activeLevelCode;
    private Date evaluateDate;
    private String evaluater;
    private Double oemConfirmPrice;
    private Date meterialReceiveDate;
    private Integer curAuditingStatus;
    private String oemConfirmMemo;
    
	public String getCampaignCode() {
		return campaignCode;
	}
	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}
	public Double getApplyPrice() {
		return applyPrice;
	}
	public void setApplyPrice(Double applyPrice) {
		this.applyPrice = applyPrice;
	}
	public String getApplyMemo() {
		return applyMemo;
	}
	public void setApplyMemo(String applyMemo) {
		this.applyMemo = applyMemo;
	}
	public String getCamEffect() {
		return camEffect;
	}
	public void setCamEffect(String camEffect) {
		this.camEffect = camEffect;
	}
	public String getCamRival() {
		return camRival;
	}
	public void setCamRival(String camRival) {
		this.camRival = camRival;
	}
	public String getCamGain() {
		return camGain;
	}
	public void setCamGain(String camGain) {
		this.camGain = camGain;
	}
	public String getCamLack() {
		return camLack;
	}
	public void setCamLack(String camLack) {
		this.camLack = camLack;
	}
	public Integer getActiveLevelCode() {
		return activeLevelCode;
	}
	public void setActiveLevelCode(Integer activeLevelCode) {
		this.activeLevelCode = activeLevelCode;
	}
	public Date getEvaluateDate() {
		return evaluateDate;
	}
	public void setEvaluateDate(Date evaluateDate) {
		this.evaluateDate = evaluateDate;
	}
	public String getEvaluater() {
		return evaluater;
	}
	public void setEvaluater(String evaluater) {
		this.evaluater = evaluater;
	}
	public Double getOemConfirmPrice() {
		return oemConfirmPrice;
	}
	public void setOemConfirmPrice(Double oemConfirmPrice) {
		this.oemConfirmPrice = oemConfirmPrice;
	}
	public Date getMeterialReceiveDate() {
		return meterialReceiveDate;
	}
	public void setMeterialReceiveDate(Date meterialReceiveDate) {
		this.meterialReceiveDate = meterialReceiveDate;
	}
	public Integer getCurAuditingStatus() {
		return curAuditingStatus;
	}
	public void setCurAuditingStatus(Integer curAuditingStatus) {
		this.curAuditingStatus = curAuditingStatus;
	}
	public String getOemConfirmMemo() {
		return oemConfirmMemo;
	}
	public void setOemConfirmMemo(String oemConfirmMemo) {
		this.oemConfirmMemo = oemConfirmMemo;
	}
    
    
}
