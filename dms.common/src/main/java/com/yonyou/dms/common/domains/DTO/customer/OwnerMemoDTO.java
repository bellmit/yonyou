/**
 * 
 */
package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;

/**
 * @author sqh
 *
 */
public class OwnerMemoDTO {
	private String dealercode;

	private String ownerNo;//车主编号
	
	private String memoInfo;
	
	private Date createDat;//CREATE_DATE

	private Date updateDat;//UPDATE_DATE

	private Double updatedBy;//UPDATE_BY

	private Double createdBy;//CREATE_BY

	private Double ver;//VER

	public String getDealercode() {
		return dealercode;
	}

	public void setDealercode(String dealercode) {
		this.dealercode = dealercode;
	}

	public String getOwnerNo() {
		return ownerNo;
	}

	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}

	public String getMemoInfo() {
		return memoInfo;
	}

	public void setMemoInfo(String memoInfo) {
		this.memoInfo = memoInfo;
	}

	public Date getCreateDat() {
		return createDat;
	}

	public void setCreateDat(Date createDat) {
		this.createDat = createDat;
	}

	public Date getUpdateDat() {
		return updateDat;
	}

	public void setUpdateDat(Date updateDat) {
		this.updateDat = updateDat;
	}

	public Double getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Double updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Double getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Double createdBy) {
		this.createdBy = createdBy;
	}

	public Double getVer() {
		return ver;
	}

	public void setVer(Double ver) {
		this.ver = ver;
	}
	
	
}
