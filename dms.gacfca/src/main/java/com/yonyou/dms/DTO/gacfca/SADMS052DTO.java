package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/*
 * 吸收率报表上报VO
 */
public class SADMS052DTO {
	// VO字段类型为DB2定义的类型 上端ORACLE需自己定义类型 /*为字段属性说明*/
	private Double entityAmount; // NUMERIC(14,2) /*经销商4S店费用*/
	private Double serviceBusinessAmount; // NUMERIC(14,2) /*售后营业额*/
	private Integer dKey; // 下端DKEY 标志 上端无需使用
	private Double bpValueMonth; // NUMERIC(14,2) /*当月钣喷产值（元）*/
	private Date updateDate; // 下端更新时间
	private Double serviceGrossProfitRate; // NUMERIC(14,2) /*售后毛利率*/
	private String absorbYear; // CHAR(4) /*年份*/
	private Double degreeBpValueMonth; // NUMERIC(14,2) /*当月钣喷产值贡献度*/
	private Long createBy; // 下端创建人 user_id
	private String entityCode; // CHAR(8) /*经销商代码*/
	private Double customerDepotRate; // NUMERIC(14,2) /*客户回厂率*/
	private Integer ver; // 下端VER 标志 上端无需使用
	private Date createDate; // 下端创建时间
	private Double degreeJpValueMonth; // NUMERIC(14,2) /*当月精品养护品贡献度*/
	private Double jpValueMonth; // NUMERIC(14,2) /*当月精品养护品总产值（元）*/
	private Long updateBy; // 下端更新人 user_id
	private String absorbMonth; // CHAR(2) /*月份*/
	private Double absorbRate; // NUMERIC(14,2) /*吸收率*/
	private String entityName; // VARCHAR(150) /*经销商名称*/
	private Double serviceGrossProfit; // NUMERIC(14,2) /*售后毛利*/

	public Double getEntityAmount() {
		return entityAmount;
	}

	public void setEntityAmount(Double entityAmount) {
		this.entityAmount = entityAmount;
	}

	public Double getServiceBusinessAmount() {
		return serviceBusinessAmount;
	}

	public void setServiceBusinessAmount(Double serviceBusinessAmount) {
		this.serviceBusinessAmount = serviceBusinessAmount;
	}

	public Integer getdKey() {
		return dKey;
	}

	public void setdKey(Integer dKey) {
		this.dKey = dKey;
	}

	public Double getBpValueMonth() {
		return bpValueMonth;
	}

	public void setBpValueMonth(Double bpValueMonth) {
		this.bpValueMonth = bpValueMonth;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Double getServiceGrossProfitRate() {
		return serviceGrossProfitRate;
	}

	public void setServiceGrossProfitRate(Double serviceGrossProfitRate) {
		this.serviceGrossProfitRate = serviceGrossProfitRate;
	}

	public String getAbsorbYear() {
		return absorbYear;
	}

	public void setAbsorbYear(String absorbYear) {
		this.absorbYear = absorbYear;
	}

	public Double getDegreeBpValueMonth() {
		return degreeBpValueMonth;
	}

	public void setDegreeBpValueMonth(Double degreeBpValueMonth) {
		this.degreeBpValueMonth = degreeBpValueMonth;
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

	public Double getCustomerDepotRate() {
		return customerDepotRate;
	}

	public void setCustomerDepotRate(Double customerDepotRate) {
		this.customerDepotRate = customerDepotRate;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getDegreeJpValueMonth() {
		return degreeJpValueMonth;
	}

	public void setDegreeJpValueMonth(Double degreeJpValueMonth) {
		this.degreeJpValueMonth = degreeJpValueMonth;
	}

	public Double getJpValueMonth() {
		return jpValueMonth;
	}

	public void setJpValueMonth(Double jpValueMonth) {
		this.jpValueMonth = jpValueMonth;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public String getAbsorbMonth() {
		return absorbMonth;
	}

	public void setAbsorbMonth(String absorbMonth) {
		this.absorbMonth = absorbMonth;
	}

	public Double getAbsorbRate() {
		return absorbRate;
	}

	public void setAbsorbRate(Double absorbRate) {
		this.absorbRate = absorbRate;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Double getServiceGrossProfit() {
		return serviceGrossProfit;
	}

	public void setServiceGrossProfit(Double serviceGrossProfit) {
		this.serviceGrossProfit = serviceGrossProfit;
	}

}
