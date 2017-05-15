package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class DMSTODCS001DTO {
	
	private String ecOrderNo;//电商订单号
	private String dealerCode;//DMS端经销商代码
	private String tel;//客户联系电话
	private Date trailDate;//跟进日期
	public String getEcOrderNo() {
		return ecOrderNo;
	}
	public void setEcOrderNo(String ecOrderNo) {
		this.ecOrderNo = ecOrderNo;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Date getTrailDate() {
		return trailDate;
	}
	public void setTrailDate(Date trailDate) {
		this.trailDate = trailDate;
	}

}
