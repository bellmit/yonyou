package com.infoservice.dms.cgcsl.vo;

/**
 * 索赔驳回VO（包括dealerId）
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class ClaimRejectedImpVO extends ClaimRejectedVO {
	private Long dealerId; //上端经销商ID

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	
	
	
	
	
	


}
