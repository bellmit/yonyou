package com.yonyou.dms.manage.domains.DTO.basedata;

/**
 * 支付方式DTO
 * @author Benzc
 * @date 2016年12月21日
 */
public class PayTypeDto {
	
	private String dealerCode;

	private String payTypeCode;
	
	private String payTypeName;
	
	private Integer writeoffTag;
	
	private Integer isReserved;
	
    private String isIntegral;
    
    public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getPayTypeCode() {
		return payTypeCode;
	}

	public void setPayTypeCode(String payTypeCode) {
		this.payTypeCode = payTypeCode;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public Integer getWriteoffTag() {
		return writeoffTag;
	}

	public void setWriteoffTag(Integer writeoffTag) {
		this.writeoffTag = writeoffTag;
	}

	public Integer getIsReserved() {
		return isReserved;
	}

	public void setIsReserved(Integer isReserved) {
		this.isReserved = isReserved;
	}

	public String getisIntegral() {
		return isIntegral;
	}

	public void setisIntegral(String isIntegral) {
		this.isIntegral = isIntegral;
	}

}
