/**
 * 
 */
package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class SADMS051VO extends BaseVO{
	// VO字段类型为DB2定义的类型 上端ORACLE需自己定义类型 /*为字段属性说明*/
	private static final long serialVersionUID = 1L;
	private Double partSatisfyRate; // NUMERIC(14,2) /*配件满足率（%) */
	private String businessMonth; // CHAR(2) /*月份*/
	private Integer dKey; // 下端DKEY 标志 上端无需使用
	private Double cusComplaintRateMonth; // NUMERIC(14,2) /*当月客诉结案率*/
	private Long appointmentCarNum; // NUMERIC(14) /*预约台次*/
	private Double internalTrainHour; // NUMERIC(14,2) /*内训总课时（小时）*/
	private Long createBy; // 下端创建人 user_id
	private String entityCode; // CHAR(8) /*经销商代码*/
	private Integer ver; // 下端VER 标志 上端无需使用
	private Long customerManagerNum; // NUMERIC(14) /*客户经理数量*/
	private Double actuarCusPirce; // NUMERIC(18,6) /*实际客单价*/
	private Long bbdcCarInNum; // NUMERIC(14) /*bbdc进厂台次*/
	private Date createDate; // 下端创建时间
	private Long dodgeCarInNum; // NUMERIC(14) /*Dodge进厂台次*/
	private Long customerNum; // NUMERIC(14) /*保有客户量*/
	private Long serviceStationNum; // NUMERIC(14) /*售后工位数*/
	private Double targetValueMonth; // NUMERIC(18,6) /*当月目标产值（万元）*/
	private Double inRepairInsuranceRate; // NUMERIC(14,2) /*在保修期车辆保险续保率*/
	private Long chryslerCarInNum; // NUMERIC(14) /*Chrysler进厂台次*/
	private Double keepFitRateMonth; // NUMERIC(14,2) /*当月保养比率*/
	private Long claimNumMonth; // NUMERIC(14) /*当月索赔台次*/
	private Double partTurnoverRate; // NUMERIC(14,2) /*配件周转率*/
	private Long salesCarNum; // NUMERIC(14,2) /*当月整车销量*/
	private String businessYear; // CHAR(4) /*年份*/
	private Double benefitCustomerRate; // NUMERIC(14,2) /*效益客户率*/
	private Double onceRepairRate; // NUMERIC(14,2) /*一次修复率*/
	private Double outRepairInsuranceRate; // NUMERIC(14,2) /*出保修期车辆保险续保率*/
	private Long carInNumMonth; // NUMERIC(14) /*当月进厂台次*/
	private Long jeepCarInNum; // NUMERIC(14) /*Jeep进厂台次*/
	private Long repairTechnicianNum; // NUMERIC(14) /*维修技师数量*/
	private Date updateDate; // 下端更新时间
	private Double actuarValueMonth; // NUMERIC(18,6) /*当月实际产值（万元）*/
	private Double partSalesAmount; // NUMERIC(18,6) /*配件销售额（万元）*/
	private Double partSalesAmountGrowthRate; // NUMERIC(14,2) /*配件销售额增长率*/
	private Double appointmentCarRate; // NUMERIC(14,2) /*预约比率*/
	private Double customerKeepRate; // NUMERIC(14,2) /*客户保持率*/
	private Double firstInsuranceRate; // NUMERIC(14,2) /*首次保险渗透率*/
	private Long updateBy; // 下端更新人 user_id
	private Long loyalCustomerNum; // NUMERIC(14) /*忠诚客户数*/
	private String entityName; // VARCHAR(150) /*经销商名称*/
	private Long outInsuranceNumMonth; // NUMERIC(14) /*当月出保客户进厂数*/
	private Double actuarValueGrowthRateMonth; // NUMERIC(14,2) /*当月实际产值增长率*/
	private Double expenseCustomerRate; // NUMERIC(14,2) /*自费客户率*/
	private Double partStockMonth; // NUMERIC(18,6) /*当月配件库存（万元）*/
	private Long keepFitNumMonth; // NUMERIC(14) /*当月保养台次*/
	public Double getPartSatisfyRate() {
		return partSatisfyRate;
	}
	public void setPartSatisfyRate(Double partSatisfyRate) {
		this.partSatisfyRate = partSatisfyRate;
	}
	public String getBusinessMonth() {
		return businessMonth;
	}
	public void setBusinessMonth(String businessMonth) {
		this.businessMonth = businessMonth;
	}
	public Integer getdKey() {
		return dKey;
	}
	public void setdKey(Integer dKey) {
		this.dKey = dKey;
	}
	public Double getCusComplaintRateMonth() {
		return cusComplaintRateMonth;
	}
	public void setCusComplaintRateMonth(Double cusComplaintRateMonth) {
		this.cusComplaintRateMonth = cusComplaintRateMonth;
	}
	public Long getAppointmentCarNum() {
		return appointmentCarNum;
	}
	public void setAppointmentCarNum(Long appointmentCarNum) {
		this.appointmentCarNum = appointmentCarNum;
	}
	public Double getInternalTrainHour() {
		return internalTrainHour;
	}
	public void setInternalTrainHour(Double internalTrainHour) {
		this.internalTrainHour = internalTrainHour;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public Long getCustomerManagerNum() {
		return customerManagerNum;
	}
	public void setCustomerManagerNum(Long customerManagerNum) {
		this.customerManagerNum = customerManagerNum;
	}
	public Double getActuarCusPirce() {
		return actuarCusPirce;
	}
	public void setActuarCusPirce(Double actuarCusPirce) {
		this.actuarCusPirce = actuarCusPirce;
	}
	public Long getBbdcCarInNum() {
		return bbdcCarInNum;
	}
	public void setBbdcCarInNum(Long bbdcCarInNum) {
		this.bbdcCarInNum = bbdcCarInNum;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getDodgeCarInNum() {
		return dodgeCarInNum;
	}
	public void setDodgeCarInNum(Long dodgeCarInNum) {
		this.dodgeCarInNum = dodgeCarInNum;
	}
	public Long getCustomerNum() {
		return customerNum;
	}
	public void setCustomerNum(Long customerNum) {
		this.customerNum = customerNum;
	}
	public Long getServiceStationNum() {
		return serviceStationNum;
	}
	public void setServiceStationNum(Long serviceStationNum) {
		this.serviceStationNum = serviceStationNum;
	}
	public Double getTargetValueMonth() {
		return targetValueMonth;
	}
	public void setTargetValueMonth(Double targetValueMonth) {
		this.targetValueMonth = targetValueMonth;
	}
	public Double getInRepairInsuranceRate() {
		return inRepairInsuranceRate;
	}
	public void setInRepairInsuranceRate(Double inRepairInsuranceRate) {
		this.inRepairInsuranceRate = inRepairInsuranceRate;
	}
	public Long getChryslerCarInNum() {
		return chryslerCarInNum;
	}
	public void setChryslerCarInNum(Long chryslerCarInNum) {
		this.chryslerCarInNum = chryslerCarInNum;
	}
	public Double getKeepFitRateMonth() {
		return keepFitRateMonth;
	}
	public void setKeepFitRateMonth(Double keepFitRateMonth) {
		this.keepFitRateMonth = keepFitRateMonth;
	}
	public Long getClaimNumMonth() {
		return claimNumMonth;
	}
	public void setClaimNumMonth(Long claimNumMonth) {
		this.claimNumMonth = claimNumMonth;
	}
	public Double getPartTurnoverRate() {
		return partTurnoverRate;
	}
	public void setPartTurnoverRate(Double partTurnoverRate) {
		this.partTurnoverRate = partTurnoverRate;
	}
	public Long getSalesCarNum() {
		return salesCarNum;
	}
	public void setSalesCarNum(Long salesCarNum) {
		this.salesCarNum = salesCarNum;
	}
	public String getBusinessYear() {
		return businessYear;
	}
	public void setBusinessYear(String businessYear) {
		this.businessYear = businessYear;
	}
	public Double getBenefitCustomerRate() {
		return benefitCustomerRate;
	}
	public void setBenefitCustomerRate(Double benefitCustomerRate) {
		this.benefitCustomerRate = benefitCustomerRate;
	}
	public Double getOnceRepairRate() {
		return onceRepairRate;
	}
	public void setOnceRepairRate(Double onceRepairRate) {
		this.onceRepairRate = onceRepairRate;
	}
	public Double getOutRepairInsuranceRate() {
		return outRepairInsuranceRate;
	}
	public void setOutRepairInsuranceRate(Double outRepairInsuranceRate) {
		this.outRepairInsuranceRate = outRepairInsuranceRate;
	}
	public Long getCarInNumMonth() {
		return carInNumMonth;
	}
	public void setCarInNumMonth(Long carInNumMonth) {
		this.carInNumMonth = carInNumMonth;
	}
	public Long getJeepCarInNum() {
		return jeepCarInNum;
	}
	public void setJeepCarInNum(Long jeepCarInNum) {
		this.jeepCarInNum = jeepCarInNum;
	}
	public Long getRepairTechnicianNum() {
		return repairTechnicianNum;
	}
	public void setRepairTechnicianNum(Long repairTechnicianNum) {
		this.repairTechnicianNum = repairTechnicianNum;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Double getActuarValueMonth() {
		return actuarValueMonth;
	}
	public void setActuarValueMonth(Double actuarValueMonth) {
		this.actuarValueMonth = actuarValueMonth;
	}
	public Double getPartSalesAmount() {
		return partSalesAmount;
	}
	public void setPartSalesAmount(Double partSalesAmount) {
		this.partSalesAmount = partSalesAmount;
	}
	public Double getPartSalesAmountGrowthRate() {
		return partSalesAmountGrowthRate;
	}
	public void setPartSalesAmountGrowthRate(Double partSalesAmountGrowthRate) {
		this.partSalesAmountGrowthRate = partSalesAmountGrowthRate;
	}
	public Double getAppointmentCarRate() {
		return appointmentCarRate;
	}
	public void setAppointmentCarRate(Double appointmentCarRate) {
		this.appointmentCarRate = appointmentCarRate;
	}
	public Double getCustomerKeepRate() {
		return customerKeepRate;
	}
	public void setCustomerKeepRate(Double customerKeepRate) {
		this.customerKeepRate = customerKeepRate;
	}
	public Double getFirstInsuranceRate() {
		return firstInsuranceRate;
	}
	public void setFirstInsuranceRate(Double firstInsuranceRate) {
		this.firstInsuranceRate = firstInsuranceRate;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public Long getLoyalCustomerNum() {
		return loyalCustomerNum;
	}
	public void setLoyalCustomerNum(Long loyalCustomerNum) {
		this.loyalCustomerNum = loyalCustomerNum;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public Long getOutInsuranceNumMonth() {
		return outInsuranceNumMonth;
	}
	public void setOutInsuranceNumMonth(Long outInsuranceNumMonth) {
		this.outInsuranceNumMonth = outInsuranceNumMonth;
	}
	public Double getActuarValueGrowthRateMonth() {
		return actuarValueGrowthRateMonth;
	}
	public void setActuarValueGrowthRateMonth(Double actuarValueGrowthRateMonth) {
		this.actuarValueGrowthRateMonth = actuarValueGrowthRateMonth;
	}
	public Double getExpenseCustomerRate() {
		return expenseCustomerRate;
	}
	public void setExpenseCustomerRate(Double expenseCustomerRate) {
		this.expenseCustomerRate = expenseCustomerRate;
	}
	public Double getPartStockMonth() {
		return partStockMonth;
	}
	public void setPartStockMonth(Double partStockMonth) {
		this.partStockMonth = partStockMonth;
	}
	public Long getKeepFitNumMonth() {
		return keepFitNumMonth;
	}
	public void setKeepFitNumMonth(Long keepFitNumMonth) {
		this.keepFitNumMonth = keepFitNumMonth;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
