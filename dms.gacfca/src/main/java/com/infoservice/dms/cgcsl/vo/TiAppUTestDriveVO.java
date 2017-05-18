package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class TiAppUTestDriveVO extends BaseVO{
	
	private Long testDriveId;
	private String uniquenessID;
	private Integer FCAID;
	private Date testDriveTime;
	private String testBrandId;
	private String testModelID;
	private String testCarStyleID;
	private String driveRoadDicID;
	private String identificationNO;
	private String dealerUserID;
	private String dealerCode;
	private Date updateDate;
	
	
	public Long getTestDriveId() {
		return testDriveId;
	}
	public void setTestDriveId(Long testDriveId) {
		this.testDriveId = testDriveId;
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
	public String getDealerUserID() {
		return dealerUserID;
	}
	public void setDealerUserID(String dealerUserID) {
		this.dealerUserID = dealerUserID;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	

}
