package com.yonyou.dms.DTO.gacfca;

public class BalancePayobjDTO {
	private String BalaPayobjId;//收费对象
	private Double RealTotalAmount;//收费金额
	

	public Double getRealTotalAmount() {
		return RealTotalAmount;
	}

	public void setRealTotalAmount(Double realTotalAmount) {
		RealTotalAmount = realTotalAmount;
	}


	public String getBalaPayobjId() {
		return BalaPayobjId;
	}

	public void setBalaPayobjId(String balaPayobjId) {
		BalaPayobjId = balaPayobjId;
	}
}
