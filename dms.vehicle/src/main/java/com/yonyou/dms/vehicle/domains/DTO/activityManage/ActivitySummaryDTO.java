package com.yonyou.dms.vehicle.domains.DTO.activityManage;

public class ActivitySummaryDTO {
	
	private String 	activityId;
	private String 	inAmount;
	private String  inGuarantee;
	private String	outGuarantee;
	private String	evaluate;
	private String	measures;
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getInAmount() {
		return inAmount;
	}
	public void setInAmount(String inAmount) {
		this.inAmount = inAmount;
	}
	public String getInGuarantee() {
		return inGuarantee;
	}
	public void setInGuarantee(String inGuarantee) {
		this.inGuarantee = inGuarantee;
	}
	public String getOutGuarantee() {
		return outGuarantee;
	}
	public void setOutGuarantee(String outGuarantee) {
		this.outGuarantee = outGuarantee;
	}
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	public String getMeasures() {
		return measures;
	}
	public void setMeasures(String measures) {
		this.measures = measures;
	}
	
}
