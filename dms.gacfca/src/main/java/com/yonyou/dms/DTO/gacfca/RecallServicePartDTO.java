package com.yonyou.dms.DTO.gacfca;

import com.infoservice.dms.cgcsl.vo.BaseVO;

public class RecallServicePartDTO extends BaseVO  {
	private static final long serialVersionUID = 1L;
	private String partCode; //配件代码
	private String partName; //配件名称
	private Long partNum; //配件数量 
	private Integer checkStatus; //类型 
	private Double changeRatio; //更换比例  
	private Integer goupNo;//组合编号
	private Double partSalesPrice;// 销售单价
	private Double partSalesAmount;// 销售金额

	
	public Double getPartSalesPrice() {
		return partSalesPrice;
	}
	public void setPartSalesPrice(Double partSalesPrice) {
		this.partSalesPrice = partSalesPrice;
	}
	public Double getPartSalesAmount() {
		return partSalesAmount;
	}
	public void setPartSalesAmount(Double partSalesAmount) {
		this.partSalesAmount = partSalesAmount;
	}
	public Integer getGoupNo() {
		return goupNo;
	}
	public void setGoupNo(Integer goupNo) {
		this.goupNo = goupNo;
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
	public Long getPartNum() {
		return partNum;
	}
	public void setPartNum(Long partNum) {
		this.partNum = partNum;
	}
	public Integer getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Double getChangeRatio() {
		return changeRatio;
	}
	public void setChangeRatio(Double changeRatio) {
		this.changeRatio = changeRatio;
	}
}
