package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TiDmsUSalesQuotasDto {


	private String entityCode;
	private String uniquenessID;// DMS客户唯一ID
	private Integer FCAID;// 售中客户唯一标识
	private String oldDealerUserID;// 销售人员的ID//原销售人员ID
	private String dealerCode;
	private String DealerUserID;// 销售人员的ID
	private Date updateDate;

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getUniquenessID() {
		return uniquenessID;
	}

	public void setUniquenessID(String uniquenessID) {
		this.uniquenessID = uniquenessID;
	}

	public Integer getFCAID() {
		return FCAID;
	}

	public void setFCAID(Integer fCAID) {
		FCAID = fCAID;
	}

	public String getOldDealerUserID() {
		return oldDealerUserID;
	}

	public void setOldDealerUserID(String oldDealerUserID) {
		this.oldDealerUserID = oldDealerUserID;
	}

	public String getDealerUserID() {
		return DealerUserID;
	}

	public void setDealerUserID(String dealerUserID) {
		DealerUserID = dealerUserID;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	// public String toXMLString() {
	// return VOUtil.vo2Xml(this);
	// }

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

}
