package com.yonyou.dms.DTO.gacfca;

import java.util.Date;
import java.util.LinkedList;

public class SEDCSP15DTO {

	private String entityCode;//经销商代码
	private String claimNo;//索赔单号
	private Date claimDate;//索赔日期
	private Integer claimProperty;//索赔性质
	private Integer claimRequire;//索赔要求
	private String deliverNo;//交货单号
	private String orderNo;//配件订单号
	private String transCompany;//运输公司
	private String transType;//运输方式
	private String transNo;//运单号
	private String boxNo;//箱号
	private Date arrivedDate;//到店日期
	private Date checkedDate;//开箱检验日期
	private String checkedBy;//经手人
	private String checkedPhone;//联系电话
	private String partCode;//配件编号
	private String partName;//配件名称
	private Integer transNum;//运单数量
	private Integer claimNum;//索赔数量
	private Double partPrice;//单价
	private Double amount;//总价
	private Integer partMdoel;//配件分类
	private Integer reportTimes;//提报次数
	private Date reportDate;//提报日期
	private Date transDate;//发货日期
	private Integer leaveTransDays;//距发货日期天数
	private String claimOptions;//索赔说明
	
	private String reissueTransNo;//补发运单号
	private String deliveryErrorPart;//误送货零部件编号
	private Date transSystemDate;//运单系统入库日期
	
	private String transStock;//库存发货地点
	
	private LinkedList<SEDCSP15InfoDTO> attachList;//附件信息
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public Date getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}
	public Integer getClaimProperty() {
		return claimProperty;
	}
	public void setClaimProperty(Integer claimProperty) {
		this.claimProperty = claimProperty;
	}
	public Integer getClaimRequire() {
		return claimRequire;
	}
	public void setClaimRequire(Integer claimRequire) {
		this.claimRequire = claimRequire;
	}
	public String getDeliverNo() {
		return deliverNo;
	}
	public void setDeliverNo(String deliverNo) {
		this.deliverNo = deliverNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTransCompany() {
		return transCompany;
	}
	public void setTransCompany(String transCompany) {
		this.transCompany = transCompany;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getBoxNo() {
		return boxNo;
	}
	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}
	public Date getArrivedDate() {
		return arrivedDate;
	}
	public void setArrivedDate(Date arrivedDate) {
		this.arrivedDate = arrivedDate;
	}
	public Date getCheckedDate() {
		return checkedDate;
	}
	public void setCheckedDate(Date checkedDate) {
		this.checkedDate = checkedDate;
	}
	public String getCheckedBy() {
		return checkedBy;
	}
	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}
	public String getCheckedPhone() {
		return checkedPhone;
	}
	public void setCheckedPhone(String checkedPhone) {
		this.checkedPhone = checkedPhone;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public Integer getTransNum() {
		return transNum;
	}
	public void setTransNum(Integer transNum) {
		this.transNum = transNum;
	}
	public Integer getClaimNum() {
		return claimNum;
	}
	public void setClaimNum(Integer claimNum) {
		this.claimNum = claimNum;
	}
	public Double getPartPrice() {
		return partPrice;
	}
	public void setPartPrice(Double partPrice) {
		this.partPrice = partPrice;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getPartMdoel() {
		return partMdoel;
	}
	public void setPartMdoel(Integer partMdoel) {
		this.partMdoel = partMdoel;
	}
	public Integer getReportTimes() {
		return reportTimes;
	}
	public void setReportTimes(Integer reportTimes) {
		this.reportTimes = reportTimes;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public Integer getLeaveTransDays() {
		return leaveTransDays;
	}
	public void setLeaveTransDays(Integer leaveTransDays) {
		this.leaveTransDays = leaveTransDays;
	}
	public String getClaimOptions() {
		return claimOptions;
	}
	public void setClaimOptions(String claimOptions) {
		this.claimOptions = claimOptions;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getReissueTransNo() {
		return reissueTransNo;
	}
	public void setReissueTransNo(String reissueTransNo) {
		this.reissueTransNo = reissueTransNo;
	}
	public String getDeliveryErrorPart() {
		return deliveryErrorPart;
	}
	public void setDeliveryErrorPart(String deliveryErrorPart) {
		this.deliveryErrorPart = deliveryErrorPart;
	}
	public Date getTransSystemDate() {
		return transSystemDate;
	}
	public void setTransSystemDate(Date transSystemDate) {
		this.transSystemDate = transSystemDate;
	}
	public String getTransStock() {
		return transStock;
	}
	public void setTransStock(String transStock) {
		this.transStock = transStock;
	}
	public LinkedList<SEDCSP15InfoDTO> getAttachList() {
		return attachList;
	}
	public void setAttachList(LinkedList<SEDCSP15InfoDTO> attachList) {
		this.attachList = attachList;
	}

}
