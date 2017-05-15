package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * 更新客户信息（试乘试驾）DMS更新
 * 
 * @author wjs
 * @date 2015年6月10日上午10:07:51
 */
public class TiDmsUTestDriveDTO {
	
	private static final long serialVersionUID = 1L;
	private String dealerCode;
	private String uniquenessID;
	private Integer FCAID;
	private Date testDriveTime;
	private String testBrandId;
	private String testModelID;
	private String testCarStyleID;
	private String driveRoadDicID;
	private String identificationNO;
	private String dealerUserID;
	private Date updateDate;

	public String getDealerUserID() {
		return dealerUserID;
	}

	public void setDealerUserID(String dealerUserID) {
		this.dealerUserID = dealerUserID;
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

	public Date getTestDriveTime() {
		return testDriveTime;
	}

	public void setTestDriveTime(Date testDriveTime) {
		this.testDriveTime = testDriveTime;
	}

	public String getTestBrandId() {
		return testBrandId;
	}

	public void setTestBrandId(String testBrandId) {
		this.testBrandId = testBrandId;
	}

	public String getTestModelID() {
		return testModelID;
	}

	public void setTestModelID(String testModelID) {
		this.testModelID = testModelID;
	}

	public String getTestCarStyleID() {
		return testCarStyleID;
	}

	public void setTestCarStyleID(String testCarStyleID) {
		this.testCarStyleID = testCarStyleID;
	}

	public String getDriveRoadDicID() {
		return driveRoadDicID;
	}

	public void setDriveRoadDicID(String driveRoadDicID) {
		this.driveRoadDicID = driveRoadDicID;
	}

	public String getIdentificationNO() {
		return identificationNO;
	}

	public void setIdentificationNO(String identificationNO) {
		this.identificationNO = identificationNO;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
}
