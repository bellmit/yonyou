package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TiDmsNTestDriveDTO {
	
	private String uniquenessId;
	private Long FCAID;
	private Date testDriveTime;
	private String testBrandId;
	private String testModelId;
	private String testCarStyleId;
	private String driveRoadDicId;
	private String identificationNo;
	private String dealerCode;
	private String dealerUserId;
	private Date createDate;
	
	
	public Long getFCAID() {
		return FCAID;
	}

	public void setFCAID(Long fcaid) {
		FCAID = fcaid;
	}

	public String getUniquenessId() {
		return uniquenessId;
	}
	public void setUniquenessId(String uniquenessId) {
		this.uniquenessId = uniquenessId;
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
	public String getTestModelId() {
		return testModelId;
	}
	public void setTestModelId(String testModelId) {
		this.testModelId = testModelId;
	}
	public String getTestCarStyleId() {
		return testCarStyleId;
	}
	public void setTestCarStyleId(String testCarStyleId) {
		this.testCarStyleId = testCarStyleId;
	}
	public String getDriveRoadDicId() {
		return driveRoadDicId;
	}
	public void setDriveRoadDicId(String driveRoadDicId) {
		this.driveRoadDicId = driveRoadDicId;
	}
	public String getIdentificationNo() {
		return identificationNo;
	}
	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getDealerUserId() {
		return dealerUserId;
	}
	public void setDealerUserId(String dealerUserId) {
		this.dealerUserId = dealerUserId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
