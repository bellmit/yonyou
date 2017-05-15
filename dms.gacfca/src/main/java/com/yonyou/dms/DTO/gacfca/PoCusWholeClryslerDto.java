package com.yonyou.dms.DTO.gacfca;

import java.util.Date;
import java.util.LinkedList;

public class PoCusWholeClryslerDto {
	//批售审批单主VO
	private String wsNo;//	批售审批单号	CHAR(12)
	private String wsAuditor;//批售审核人		VARCHAR(60)
	private String dealerCode;//经销商代码 	CHAR(8)
	private Integer wsStatus;//批售状态	NUMERIC(8)
	private Date auditingDate;//审核日期 TIMESTAMP
	private String wsAuditingRemark;//	审核备注		VARCHAR(255)
	private String companyName;
	private Date submitTime;//上报日期  TIMESTAMP
	private String projectRemark;//项目注释  备注	VARCHAR(100)
	private Integer wsType;//批售类型		NUMERIC(8)
	private String largeCustomerNo;//大客户代码	CHAR(20)
	private String customerName;//客户名称 VARCHAR(120)
	private Integer isSecondReport;//是否资源申请		NUMERIC(8)
	private String mobile;//手机		VARCHAR(30)
	private String phone;//	电话	VARCHAR(120)
	private String contactorName;//联系人	 VARCHAR(30)
	private String positionName;//	职务名称	VARCHAR(30)
	private String fax;	//传真 VARCHAR(30)
	private String dlrPrincipalPhone;//	负责人电话	VARCHAR(30)
	private String dlrPrincipal;//经销商业务负责人	VARCHAR(30)
	
	private Integer customerCharacter;//客户性质	NUMERIC(8)
	private Integer customerKind;//客户类别	NUMERIC(8) 
	private String lmEmail;//	联系人邮箱
	private Integer lmSex;//	联系人性别
	private Integer ctCode;//证件类别
	private String certificateNo;//证件号码
	private Date lmbirthDay;//出生年月日  TIMESTAMP
	
	private String applyPic; //申请表
	private String saleContractPic; //销售合同
	private String applyPicId; //申请表 - ID
	private String saleContractPicId; //合同表 - ID
	private String customerCardPicId; //身份证明 - ID
	private String customerCardPic; //客户身份证明 暂时没传
	
	private String contractFileAid; //销售合同a附件ID
	private String contractFileAurl; //销售合同a附件URL
	private String contractFileBid; //销售合同b附件ID
	private String contractFileBurl; //销售合同b附件URL
	private Date estimateApplyTime;// 预计申请时间
	private Integer wsthreeType;
	
	private LinkedList<WsConfigInfoClryslerDto> configList;//批售配置VO
	
	public String getWsNo() {
		return wsNo;
	}
	public void setWsNo(String wsNo) {
		this.wsNo = wsNo;
	}
	public String getWsAuditor() {
		return wsAuditor;
	}
	public void setWsAuditor(String wsAuditor) {
		this.wsAuditor = wsAuditor;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Integer getWsStatus() {
		return wsStatus;
	}
	public void setWsStatus(Integer wsStatus) {
		this.wsStatus = wsStatus;
	}
	public Date getAuditingDate() {
		return auditingDate;
	}
	public void setAuditingDate(Date auditingDate) {
		this.auditingDate = auditingDate;
	}
	public String getWsAuditingRemark() {
		return wsAuditingRemark;
	}
	public void setWsAuditingRemark(String wsAuditingRemark) {
		this.wsAuditingRemark = wsAuditingRemark;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public String getProjectRemark() {
		return projectRemark;
	}
	public void setProjectRemark(String projectRemark) {
		this.projectRemark = projectRemark;
	}
	public Integer getWsType() {
		return wsType;
	}
	public void setWsType(Integer wsType) {
		this.wsType = wsType;
	}
	public String getLargeCustomerNo() {
		return largeCustomerNo;
	}
	public void setLargeCustomerNo(String largeCustomerNo) {
		this.largeCustomerNo = largeCustomerNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getIsSecondReport() {
		return isSecondReport;
	}
	public void setIsSecondReport(Integer isSecondReport) {
		this.isSecondReport = isSecondReport;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContactorName() {
		return contactorName;
	}
	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getDlrPrincipalPhone() {
		return dlrPrincipalPhone;
	}
	public void setDlrPrincipalPhone(String dlrPrincipalPhone) {
		this.dlrPrincipalPhone = dlrPrincipalPhone;
	}
	public String getDlrPrincipal() {
		return dlrPrincipal;
	}
	public void setDlrPrincipal(String dlrPrincipal) {
		this.dlrPrincipal = dlrPrincipal;
	}
	public Integer getCustomerCharacter() {
		return customerCharacter;
	}
	public void setCustomerCharacter(Integer customerCharacter) {
		this.customerCharacter = customerCharacter;
	}
	public Integer getCustomerKind() {
		return customerKind;
	}
	public void setCustomerKind(Integer customerKind) {
		this.customerKind = customerKind;
	}
	public String getLmEmail() {
		return lmEmail;
	}
	public void setLmEmail(String lmEmail) {
		this.lmEmail = lmEmail;
	}
	public Integer getLmSex() {
		return lmSex;
	}
	public void setLmSex(Integer lmSex) {
		this.lmSex = lmSex;
	}
	public Integer getCtCode() {
		return ctCode;
	}
	public void setCtCode(Integer ctCode) {
		this.ctCode = ctCode;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public Date getLmbirthDay() {
		return lmbirthDay;
	}
	public void setLmbirthDay(Date lmbirthDay) {
		this.lmbirthDay = lmbirthDay;
	}
	public String getApplyPic() {
		return applyPic;
	}
	public void setApplyPic(String applyPic) {
		this.applyPic = applyPic;
	}
	public String getSaleContractPic() {
		return saleContractPic;
	}
	public void setSaleContractPic(String saleContractPic) {
		this.saleContractPic = saleContractPic;
	}
	public String getApplyPicId() {
		return applyPicId;
	}
	public void setApplyPicId(String applyPicId) {
		this.applyPicId = applyPicId;
	}
	public String getSaleContractPicId() {
		return saleContractPicId;
	}
	public void setSaleContractPicId(String saleContractPicId) {
		this.saleContractPicId = saleContractPicId;
	}
	public String getCustomerCardPicId() {
		return customerCardPicId;
	}
	public void setCustomerCardPicId(String customerCardPicId) {
		this.customerCardPicId = customerCardPicId;
	}
	public String getCustomerCardPic() {
		return customerCardPic;
	}
	public void setCustomerCardPic(String customerCardPic) {
		this.customerCardPic = customerCardPic;
	}
	public String getContractFileAid() {
		return contractFileAid;
	}
	public void setContractFileAid(String contractFileAid) {
		this.contractFileAid = contractFileAid;
	}
	public String getContractFileAurl() {
		return contractFileAurl;
	}
	public void setContractFileAurl(String contractFileAurl) {
		this.contractFileAurl = contractFileAurl;
	}
	public String getContractFileBid() {
		return contractFileBid;
	}
	public void setContractFileBid(String contractFileBid) {
		this.contractFileBid = contractFileBid;
	}
	public String getContractFileBurl() {
		return contractFileBurl;
	}
	public void setContractFileBurl(String contractFileBurl) {
		this.contractFileBurl = contractFileBurl;
	}
	public Date getEstimateApplyTime() {
		return estimateApplyTime;
	}
	public void setEstimateApplyTime(Date estimateApplyTime) {
		this.estimateApplyTime = estimateApplyTime;
	}
	public Integer getWsthreeType() {
		return wsthreeType;
	}
	public void setWsthreeType(Integer wsthreeType) {
		this.wsthreeType = wsthreeType;
	}
	public LinkedList<WsConfigInfoClryslerDto> getConfigList() {
		return configList;
	}
	public void setConfigList(LinkedList<WsConfigInfoClryslerDto> configList) {
		this.configList = configList;
	}

}
