package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TmWxMaintainPartDTO {

	private Integer isMain;// 主因
	private Integer partDealType;// 零售处理类型)
	private Double partPrice;// 配件单价
	private String partName;// 零件名称
	private String partCode;// 零件代码
	private String labourCode;// 工时代码
	private Double amount;// 数量
	private Double partFee;// 费用
	private Date partCreateDate;// 配件创建时间

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getIsMain() {
		return isMain;
	}

	public void setIsMain(Integer isMain) {
		this.isMain = isMain;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public Date getPartCreateDate() {
		return partCreateDate;
	}

	public void setPartCreateDate(Date partCreateDate) {
		this.partCreateDate = partCreateDate;
	}

	public Integer getPartDealType() {
		return partDealType;
	}

	public void setPartDealType(Integer partDealType) {
		this.partDealType = partDealType;
	}

	public Double getPartFee() {
		return partFee;
	}

	public void setPartFee(Double partFee) {
		this.partFee = partFee;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Double getPartPrice() {
		return partPrice;
	}

	public void setPartPrice(Double partPrice) {
		this.partPrice = partPrice;
	}

	public String getLabourCode() {
		return labourCode;
	}

	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
	}
}
