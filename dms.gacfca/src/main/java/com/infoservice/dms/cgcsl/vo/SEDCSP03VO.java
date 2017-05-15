package com.infoservice.dms.cgcsl.vo;

import java.util.Date;
import java.util.LinkedList;

public class SEDCSP03VO extends BaseVO{
	private static final long serialVersionUID = 1L;
	
	private String entityCode;//经销商代码
	private String orderNo;//订单号
	private String dealerUser;//特约店用户
	private Integer orderType;//订单类型
	private String vin;//VIN
	private String elecCode;//电子编码
	private String mechCode;//机械代码
	private String customerName;//车主姓名
	private String customerPhone;//联系电话
	private String licenseNo;//车牌号
	private Integer reportType;//提报方式
	private Date reportDate;//提报日期
	private String keyCode;//锁芯机械码
	private Integer isEmerg;//是否通过应急启动
	private Integer isRepairByself;//是否在未授权店私自更换
	private String iyj;//累计维修时间
	private String leaveWord;//留言
	private Double creditBalance;//信用余额
	private Double orderBalance;//本次提交金额
	private Double difBalance;//差额
	private String refNumber;//DMS数量
	private String codeOrderOneId;//CODE订单上传附件一
	private String codeOrderOneUrl;
	private String codeOrderTwoId;//CODE订单上传附件二
	private String codeOrderTwoUrl;
	private String codeOrderThreeId;//CODE订单上传附件三
	private String codeOrderThreeUrl;
	private LinkedList<SEDCSP03VOPartInfoVO>	tbInputPosition;//明细信息

	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getDealerUser() {
		return dealerUser;
	}
	public void setDealerUser(String dealerUser) {
		this.dealerUser = dealerUser;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getElecCode() {
		return elecCode;
	}
	public void setElecCode(String elecCode) {
		this.elecCode = elecCode;
	}
	public String getMechCode() {
		return mechCode;
	}
	public void setMechCode(String mechCode) {
		this.mechCode = mechCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public Integer getReportType() {
		return reportType;
	}
	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	public Integer getIsEmerg() {
		return isEmerg;
	}
	public void setIsEmerg(Integer isEmerg) {
		this.isEmerg = isEmerg;
	}
	public Integer getIsRepairByself() {
		return isRepairByself;
	}
	public void setIsRepairByself(Integer isRepairByself) {
		this.isRepairByself = isRepairByself;
	}
	public String getIyj() {
		return iyj;
	}
	public void setIyj(String iyj) {
		this.iyj = iyj;
	}
	public String getLeaveWord() {
		return leaveWord;
	}
	public void setLeaveWord(String leaveWord) {
		this.leaveWord = leaveWord;
	}
	public Double getCreditBalance() {
		return creditBalance;
	}
	public void setCreditBalance(Double creditBalance) {
		this.creditBalance = creditBalance;
	}
	public Double getOrderBalance() {
		return orderBalance;
	}
	public void setOrderBalance(Double orderBalance) {
		this.orderBalance = orderBalance;
	}
	public Double getDifBalance() {
		return difBalance;
	}
	public void setDifBalance(Double difBalance) {
		this.difBalance = difBalance;
	}
	public LinkedList<SEDCSP03VOPartInfoVO> getTbInputPosition() {
		return tbInputPosition;
	}
	public void setTbInputPosition(LinkedList<SEDCSP03VOPartInfoVO> tbInputPosition) {
		this.tbInputPosition = tbInputPosition;
	}
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getCodeOrderOneId() {
		return codeOrderOneId;
	}
	public void setCodeOrderOneId(String codeOrderOneId) {
		this.codeOrderOneId = codeOrderOneId;
	}
	public String getCodeOrderOneUrl() {
		return codeOrderOneUrl;
	}
	public void setCodeOrderOneUrl(String codeOrderOneUrl) {
		this.codeOrderOneUrl = codeOrderOneUrl;
	}
	public String getCodeOrderTwoId() {
		return codeOrderTwoId;
	}
	public void setCodeOrderTwoId(String codeOrderTwoId) {
		this.codeOrderTwoId = codeOrderTwoId;
	}
	public String getCodeOrderTwoUrl() {
		return codeOrderTwoUrl;
	}
	public void setCodeOrderTwoUrl(String codeOrderTwoUrl) {
		this.codeOrderTwoUrl = codeOrderTwoUrl;
	}
	public String getCodeOrderThreeId() {
		return codeOrderThreeId;
	}
	public void setCodeOrderThreeId(String codeOrderThreeId) {
		this.codeOrderThreeId = codeOrderThreeId;
	}
	public String getCodeOrderThreeUrl() {
		return codeOrderThreeUrl;
	}
	public void setCodeOrderThreeUrl(String codeOrderThreeUrl) {
		this.codeOrderThreeUrl = codeOrderThreeUrl;
	}
	
	
}
