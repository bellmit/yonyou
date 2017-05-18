package com.yonyou.dms.vehicle.domains.DTO.claimManage;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class ClaimApplyDTO {
	
	private Long 			claimId; 		//索赔单ID
	private String 			claimNo; 		//索赔单号
	private String 			dealerCode;		//经销商ID
	private String 			dealerName;		//经销商代码
	private String 			repairNo;		//维修工单号
	private Date 			roStratDate;	//工单开始时间
	private Date 			roEndDate;		//工单开始时间
	private String 			foreapprovalNo;	//授权单号
	private Integer 		milleage;
	private Date			purchaseDate; 	//PURCHASE_DATE
	private String 			serveAdvisor;		//服务顾问
	private String 			vin;
	private String 			plateNo;
	private String 			engineNo;
	private String 			brand;				//品牌
	private String 			series;				//车系
	private String 			model;				//车型
	private String 			claimType;		   //索赔类型
	private Integer 		claimCintinueFlag; //延续标识
	
	
	private String 			customerComplain;	//客户抱怨
	private String 			checkRepairProcedures; //检测和维修步骤
	private String 			dealScheme;				//处理方案
	private String 			applyReasons;		//申请理由
	
	
	
	
	private Double          customerBearFeePart;	//客户分担费用
	private Double          dealerBearFeePart;		//经销商分担费用
	private Double          oemBearFeePart;			//总部分担费用
	private Double          customerBearFeeLabour;	//客户分担费用
	private Double          dealerBearFeeLabour;	//经销商分担费用
	private Double          oemBearFeeLabour;		//总部分担费用
	private Double          customerBearFeeOther;	//客户分担费用
	private Double          dealerBearFeeOther;		//经销商分担费用
	private Double          oemBearFeeOther;		//总部分担费用
	private String			claimRemark;			//申请备注  
	
	private Double  		labourNum;
	private Double  		labourPice;			
	private Double  		partMangeFee;
	private Double  		labTaxRate;
	private Double  		partTaxRate;
	private Double  		labourFee;		//工时总费用
	private Double  		partFee; 		//配件总费用
	private Double  		otherFee; 		//其他项目总费用
	private Double  		totalFee; 		//索赔申请总金额
	
	private List<Map>    	claimCaseTable;		//情形
	private List<Map>    	claimPartsTable;	//配件
	private List<Map>    	claimLabourTable;	//工时
	private List<Map>    	claimOtherTable;	//其他项目
	//索赔规则校验
	private Date 			balanceDate;
	private Integer			repairType;			//
	private String			activityCode;  
	private String			packageCode;
	private String			isApprove;
	private Double 			betweenDays;
	private Integer 		partTotal;
	private String          lastroDate;      //上次工单时间
	private Integer 		lastroMilleage;		//上次行驶里程
	private String 			preClaimNo; 		////先前索赔单号 PRE_CLAIM_NO
	private String			upd;			//验证是否修改操作  1为修改
	
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getRepairNo() {
		return repairNo;
	}
	public void setRepairNo(String repairNo) {
		this.repairNo = repairNo;
	}
	public Date getRoStratDate() {
		return roStratDate;
	}
	public void setRoStratDate(Date roStratDate) {
		this.roStratDate = roStratDate;
	}
	public Date getRoEndDate() {
		return roEndDate;
	}
	public void setRoEndDate(Date roEndDate) {
		this.roEndDate = roEndDate;
	}
	public String getForeapprovalNo() {
		return foreapprovalNo;
	}
	public void setForeapprovalNo(String foreapprovalNo) {
		this.foreapprovalNo = foreapprovalNo;
	}
	public Integer getMilleage() {
		return milleage;
	}
	public void setMilleage(Integer milleage) {
		this.milleage = milleage;
	}
	
	public Date getBalanceDate() {
		return balanceDate;
	}
	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}
	public String getServeAdvisor() {
		return serveAdvisor;
	}
	public void setServeAdvisor(String serveAdvisor) {
		this.serveAdvisor = serveAdvisor;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public Integer getClaimCintinueFlag() {
		return claimCintinueFlag;
	}
	public void setClaimCintinueFlag(Integer claimCintinueFlag) {
		this.claimCintinueFlag = claimCintinueFlag;
	}
	public String getCustomerComplain() {
		return customerComplain;
	}
	public void setCustomerComplain(String customerComplain) {
		this.customerComplain = customerComplain;
	}
	public String getCheckRepairProcedures() {
		return checkRepairProcedures;
	}
	public void setCheckRepairProcedures(String checkRepairProcedures) {
		this.checkRepairProcedures = checkRepairProcedures;
	}
	public String getDealScheme() {
		return dealScheme;
	}
	public void setDealScheme(String dealScheme) {
		this.dealScheme = dealScheme;
	}
	public String getApplyReasons() {
		return applyReasons;
	}
	public void setApplyReasons(String applyReasons) {
		this.applyReasons = applyReasons;
	}
	public Double getCustomerBearFeePart() {
		return customerBearFeePart;
	}
	public void setCustomerBearFeePart(Double customerBearFeePart) {
		this.customerBearFeePart = customerBearFeePart;
	}
	public Double getDealerBearFeePart() {
		return dealerBearFeePart;
	}
	public void setDealerBearFeePart(Double dealerBearFeePart) {
		this.dealerBearFeePart = dealerBearFeePart;
	}
	public Double getOemBearFeePart() {
		return oemBearFeePart;
	}
	public void setOemBearFeePart(Double oemBearFeePart) {
		this.oemBearFeePart = oemBearFeePart;
	}
	public Double getCustomerBearFeeLabour() {
		return customerBearFeeLabour;
	}
	public void setCustomerBearFeeLabour(Double customerBearFeeLabour) {
		this.customerBearFeeLabour = customerBearFeeLabour;
	}
	public Double getDealerBearFeeLabour() {
		return dealerBearFeeLabour;
	}
	public void setDealerBearFeeLabour(Double dealerBearFeeLabour) {
		this.dealerBearFeeLabour = dealerBearFeeLabour;
	}
	public Double getOemBearFeeLabour() {
		return oemBearFeeLabour;
	}
	public void setOemBearFeeLabour(Double oemBearFeeLabour) {
		this.oemBearFeeLabour = oemBearFeeLabour;
	}
	public Double getCustomerBearFeeOther() {
		return customerBearFeeOther;
	}
	public void setCustomerBearFeeOther(Double customerBearFeeOther) {
		this.customerBearFeeOther = customerBearFeeOther;
	}
	public Double getDealerBearFeeOther() {
		return dealerBearFeeOther;
	}
	public void setDealerBearFeeOther(Double dealerBearFeeOther) {
		this.dealerBearFeeOther = dealerBearFeeOther;
	}
	public Double getOemBearFeeOther() {
		return oemBearFeeOther;
	}
	public void setOemBearFeeOther(Double oemBearFeeOther) {
		this.oemBearFeeOther = oemBearFeeOther;
	}
	public String getClaimRemark() {
		return claimRemark;
	}
	public void setClaimRemark(String claimRemark) {
		this.claimRemark = claimRemark;
	}
	public Double getLabourNum() {
		return labourNum;
	}
	public void setLabourNum(Double labourNum) {
		this.labourNum = labourNum;
	}
	public Double getLabourPice() {
		return labourPice;
	}
	public void setLabourPice(Double labourPice) {
		this.labourPice = labourPice;
	}
	public Double getPartMangeFee() {
		return partMangeFee;
	}
	public void setPartMangeFee(Double partMangeFee) {
		this.partMangeFee = partMangeFee;
	}
	public Double getLabTaxRate() {
		return labTaxRate;
	}
	public void setLabTaxRate(Double labTaxRate) {
		this.labTaxRate = labTaxRate;
	}
	public Double getPartTaxRate() {
		return partTaxRate;
	}
	public void setPartTaxRate(Double partTaxRate) {
		this.partTaxRate = partTaxRate;
	}
	public Double getLabourFee() {
		return labourFee;
	}
	public void setLabourFee(Double labourFee) {
		this.labourFee = labourFee;
	}
	public Double getPartFee() {
		return partFee;
	}
	public void setPartFee(Double partFee) {
		this.partFee = partFee;
	}
	public Double getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}
	
	public Double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	public List<Map> getClaimCaseTable() {
		return claimCaseTable;
	}
	public void setClaimCaseTable(List<Map> claimCaseTable) {
		this.claimCaseTable = claimCaseTable;
	}
	public List<Map> getClaimPartsTable() {
		return claimPartsTable;
	}
	public void setClaimPartsTable(List<Map> claimPartsTable) {
		this.claimPartsTable = claimPartsTable;
	}
	public List<Map> getClaimLabourTable() {
		return claimLabourTable;
	}
	public void setClaimLabourTable(List<Map> claimLabourTable) {
		this.claimLabourTable = claimLabourTable;
	}
	public List<Map> getClaimOtherTable() {
		return claimOtherTable;
	}
	public void setClaimOtherTable(List<Map> claimOtherTable) {
		this.claimOtherTable = claimOtherTable;
	}
	public Long getClaimId() {
		return claimId;
	}
	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public Integer getRepairType() {
		return repairType;
	}
	public void setRepairType(Integer repairType) {
		this.repairType = repairType;
	}
	public String getIsApprove() {
		return isApprove;
	}
	public void setIsApprove(String isApprove) {
		this.isApprove = isApprove;
	}
	public Double getBetweenDays() {
		return betweenDays;
	}
	public void setBetweenDays(Double betweenDays) {
		this.betweenDays = betweenDays;
	}
	public Integer getPartTotal() {
		return partTotal;
	}
	public void setPartTotal(Integer partTotal) {
		this.partTotal = partTotal;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getLastroDate() {
		return lastroDate;
	}
	public void setLastroDate(String lastroDate) {
		this.lastroDate = lastroDate;
	}
	public Integer getLastroMilleage() {
		return lastroMilleage;
	}
	public void setLastroMilleage(Integer lastroMilleage) {
		this.lastroMilleage = lastroMilleage;
	}
	public String getPreClaimNo() {
		return preClaimNo;
	}
	public void setPreClaimNo(String preClaimNo) {
		this.preClaimNo = preClaimNo;
	}
	public String getUpd() {
		return upd;
	}
	public void setUpd(String upd) {
		this.upd = upd;
	}
	
	
	
}
