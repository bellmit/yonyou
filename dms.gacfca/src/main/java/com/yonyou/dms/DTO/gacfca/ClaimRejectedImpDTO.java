package com.yonyou.dms.DTO.gacfca;

/**
 * 索赔驳回VO（包括dealerId）
 * @author Administrator
 *
 */
public class ClaimRejectedImpDTO extends ClaimRejectedDTO{
	private Long dealerId; //上端经销商ID

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	
	
	
	
	
	


}
