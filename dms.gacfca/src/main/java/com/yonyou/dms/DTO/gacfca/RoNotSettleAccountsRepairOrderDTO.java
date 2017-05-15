package com.yonyou.dms.DTO.gacfca;

import java.util.Date;
import java.util.LinkedList;

public class RoNotSettleAccountsRepairOrderDTO {
	private String roNo; //下端：工单号  CHAR(12)  上端：REPAIR_NO VARCHAR (30) 
	private Integer isClaim;// 上下端都新增 是否索赔 字段 下端12781001 是   12781002 否  上端 0是  1否
	private String entityCode;
	private Integer isValid;
	private Date downTimestamp;
	private Integer roType; //下端：工单类型  NUMERIC(8) 上端单据类型 ORDER_TYPE CHAR(8)  40041001; //保养工单  40041002; //维修工单	
	private String repairTypeCode; 
	private String newOrderType;//下端：维修类型代码（上端无法区分）  CHAR(4) PTWX 一般维修
	private String newRepairType;
	/*XCZH 新车装潢
	SQWX 售前维修
	DQBY 定期保养
	NBXL 内部修理
	WCWX 外出维修
	MFBY 走合保养
	FWHD 服务活动
	SGWX 事故维修 上端：REPAIR_TYPE CHARACTER (8) 40011001; //标准维修
	40011002; //售前维修
	40011003; //返修
	40011004; //服务活动"
	 */	
	private String repairTypeName; //下端：维修类型名称  VARCHAR(30)  上端：
	private String serviceAdvisor; //下端：服务专员  CHAR(4)  上端：REPORT_MAN VARCHAR(60) 上端需要报修员、下端需要翻译成具体人员
	private String serviceAdvisorAss; //下端：开单人员  CHAR(4)  上端：  下端开单人员在上端不存在，也可以在上端加一个字段
	private Integer roStatus; //下端：工单状态  NUMERIC(8) 12551001 在修
	/* 12551005 已提交结算  12551010 已结算 上端：status CHARACTER (8) 40021001; //未结算 40021002; //已结算"
	 */	
	private Date roCreateDate; //下端：工单开单日期  TIMESTAMP  上端：MAKE_DATE TIMESTAMP 
	private String vin; //下端：VIN  VARCHAR(17)  上端：VIN VARCHAR (20) 
	private Double inMileage; //下端：进厂行驶里程  NUMERIC(10,2)  上端：MILLEAGE NUMERIC(12,2) 
	private String deliverer; //下端：送修人  VARCHAR(30)  上端：CUSTOMER_NAME VARCHAR (60) 缺少送修人地址（下端缺少该字段）
	private String delivererPhone; //下端：送修人电话  VARCHAR(30)  上端：CUSTOMER_TEL VARCHAR (60) 
	private Date forBalanceTime; //下端：提交结算时间  TIMESTAMP  上端：BALANCE_DATE TIMESTAMP 
	private Float labourPrice; //下端：工时单价  NUMERIC(8,2)  上端：LAB_PAY NUMERIC(12,2) 
	private Double labourAmount; //下端：工时费  NUMERIC(12,2)  上端：LABOR_FEE NUMERIC(12,2) 
	private Double repairPartAmount; //下端：维修材料费  NUMERIC(12,2)  上端：PART_FEE NUMERIC(12,2) 
	private Double additemAmount; //下端：附加项目费  NUMERIC(12,2)  上端：OTHER_FEE NUMERIC(12,2) 
	private String remark; //下端：备注  VARCHAR(300)  上端：  
	//private Date downTimestamp; //下端：下发时序  TIMESTAMP  上端：  
	//private Integer isValid; //下端：有效状态   下端传以下值 10011001 有效 上端：  "
	private LinkedList<RoNotSettleAccountsLabourDTO> labourVoList; //下端：维修项目列表    上端：  
	private LinkedList<RoNotSettleAccountsRepairPartDTO> repairPartVoList; //下端：维修配件列表    上端：  
	private LinkedList<RoNotSettleAccountsAddItemDTO> addItemVoList; //下端：附加项目列表    上端：  

	/*如下字段，下端没有，需要下端定义*/
	private String customerAddress; //下端：客户地址    上端：CUSTOMER_ADDR VARCHAR (300) 下端需增加
	private Date faultDate; //下端：故障发生日    上端：FAULT_DATE TIMESTAMP 下端需增加
	private Integer betweenDays; //下端：故障发生天数    上端：BETWEEN_DAYS DECIMAL (8, 0) 故障发生日-保修开始日（可以接口计算）
	private Date repairDate; //下端：保修开始日    上端：REPAIR_DATE TIMESTAMP 下端需增加
	private String packageCode; //下端：保养套餐代码/活动编码    上端：PACKAGE_CODE VARCHAR (60) 下端需增加
	private String packageName; //下端：保养套餐名称    上端：PACKAGE_NAME VARCHAR (200) 下端需增加
	private Date finishDate;//维修工单完工日期
	private String bookingOrderNo;;//预约单号
	//	private Integer isSeriousTrouble;//是否严重安全故障 下端传值12781001代表 是 12781002或者空 为否
	private Double outMileage; //出厂里程  add by dnegweili 20140329 

	private LinkedList<RoNotSettleAccountsReasonDTO> notSettleAccountsReason; //工单未结算原因

	public String getRoNo() {
		return roNo;
	}

	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}

	public Integer getIsClaim() {
		return isClaim;
	}

	public void setIsClaim(Integer isClaim) {
		this.isClaim = isClaim;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Date getDownTimestamp() {
		return downTimestamp;
	}

	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}

	public Integer getRoType() {
		return roType;
	}

	public void setRoType(Integer roType) {
		this.roType = roType;
	}

	public String getRepairTypeCode() {
		return repairTypeCode;
	}

	public void setRepairTypeCode(String repairTypeCode) {
		this.repairTypeCode = repairTypeCode;
	}

	public String getNewOrderType() {
		return newOrderType;
	}

	public void setNewOrderType(String newOrderType) {
		this.newOrderType = newOrderType;
	}

	public String getNewRepairType() {
		return newRepairType;
	}

	public void setNewRepairType(String newRepairType) {
		this.newRepairType = newRepairType;
	}

	public String getRepairTypeName() {
		return repairTypeName;
	}

	public void setRepairTypeName(String repairTypeName) {
		this.repairTypeName = repairTypeName;
	}

	public String getServiceAdvisor() {
		return serviceAdvisor;
	}

	public void setServiceAdvisor(String serviceAdvisor) {
		this.serviceAdvisor = serviceAdvisor;
	}

	public String getServiceAdvisorAss() {
		return serviceAdvisorAss;
	}

	public void setServiceAdvisorAss(String serviceAdvisorAss) {
		this.serviceAdvisorAss = serviceAdvisorAss;
	}

	public Integer getRoStatus() {
		return roStatus;
	}

	public void setRoStatus(Integer roStatus) {
		this.roStatus = roStatus;
	}

	public Date getRoCreateDate() {
		return roCreateDate;
	}

	public void setRoCreateDate(Date roCreateDate) {
		this.roCreateDate = roCreateDate;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Double getInMileage() {
		return inMileage;
	}

	public void setInMileage(Double inMileage) {
		this.inMileage = inMileage;
	}

	public String getDeliverer() {
		return deliverer;
	}

	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
	}

	public String getDelivererPhone() {
		return delivererPhone;
	}

	public void setDelivererPhone(String delivererPhone) {
		this.delivererPhone = delivererPhone;
	}

	public Date getForBalanceTime() {
		return forBalanceTime;
	}

	public void setForBalanceTime(Date forBalanceTime) {
		this.forBalanceTime = forBalanceTime;
	}

	public Float getLabourPrice() {
		return labourPrice;
	}

	public void setLabourPrice(Float labourPrice) {
		this.labourPrice = labourPrice;
	}

	public Double getLabourAmount() {
		return labourAmount;
	}

	public void setLabourAmount(Double labourAmount) {
		this.labourAmount = labourAmount;
	}

	public Double getRepairPartAmount() {
		return repairPartAmount;
	}

	public void setRepairPartAmount(Double repairPartAmount) {
		this.repairPartAmount = repairPartAmount;
	}

	public Double getAdditemAmount() {
		return additemAmount;
	}

	public void setAdditemAmount(Double additemAmount) {
		this.additemAmount = additemAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LinkedList<RoNotSettleAccountsLabourDTO> getLabourVoList() {
		return labourVoList;
	}

	public void setLabourVoList(LinkedList<RoNotSettleAccountsLabourDTO> labourVoList) {
		this.labourVoList = labourVoList;
	}

	public LinkedList<RoNotSettleAccountsRepairPartDTO> getRepairPartVoList() {
		return repairPartVoList;
	}

	public void setRepairPartVoList(LinkedList<RoNotSettleAccountsRepairPartDTO> repairPartVoList) {
		this.repairPartVoList = repairPartVoList;
	}

	public LinkedList<RoNotSettleAccountsAddItemDTO> getAddItemVoList() {
		return addItemVoList;
	}

	public void setAddItemVoList(LinkedList<RoNotSettleAccountsAddItemDTO> addItemVoList) {
		this.addItemVoList = addItemVoList;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public Date getFaultDate() {
		return faultDate;
	}

	public void setFaultDate(Date faultDate) {
		this.faultDate = faultDate;
	}

	public Integer getBetweenDays() {
		return betweenDays;
	}

	public void setBetweenDays(Integer betweenDays) {
		this.betweenDays = betweenDays;
	}

	public Date getRepairDate() {
		return repairDate;
	}

	public void setRepairDate(Date repairDate) {
		this.repairDate = repairDate;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getBookingOrderNo() {
		return bookingOrderNo;
	}

	public void setBookingOrderNo(String bookingOrderNo) {
		this.bookingOrderNo = bookingOrderNo;
	}

	public Double getOutMileage() {
		return outMileage;
	}

	public void setOutMileage(Double outMileage) {
		this.outMileage = outMileage;
	}

	public LinkedList<RoNotSettleAccountsReasonDTO> getNotSettleAccountsReason() {
		return notSettleAccountsReason;
	}

	public void setNotSettleAccountsReason(LinkedList<RoNotSettleAccountsReasonDTO> notSettleAccountsReason) {
		this.notSettleAccountsReason = notSettleAccountsReason;
	}
}
