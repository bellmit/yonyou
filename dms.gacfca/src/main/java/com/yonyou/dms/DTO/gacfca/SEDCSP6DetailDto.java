package com.yonyou.dms.DTO.gacfca;

/**
 * 经销商交货单发送接口 SEDCSP6
 * @author luoyang
 *
 */
public class SEDCSP6DetailDto {
	
	private String dealerCode;
	private String partCode;//配件代码
	private String partName;//配件名称
	private String orderNo;//订单号
	private Long planNum;//计划量
	private Double instorPrice;//入库单价
	private Double discount;//折扣额
	private Integer iposnr;//交货项目
	private String transStock;//库存发货地点
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Long getPlanNum() {
		return planNum;
	}
	public void setPlanNum(Long planNum) {
		this.planNum = planNum;
	}
	public Double getInstorPrice() {
		return instorPrice;
	}
	public void setInstorPrice(Double instorPrice) {
		this.instorPrice = instorPrice;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Integer getIposnr() {
		return iposnr;
	}
	public void setIposnr(Integer iposnr) {
		this.iposnr = iposnr;
	}
	public String getTransStock() {
		return transStock;
	}
	public void setTransStock(String transStock) {
		this.transStock = transStock;
	}
	
	

}
