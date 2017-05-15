package com.yonyou.dms.DTO.gacfca;

/**
 * 呆滞件下架or变更数量上报接口
 * @author luoyang
 *
 */
public class SEDMS069Dto {
	
	private String dealerCode;
	private String partNo;//配件代码
	private String storageCode;//仓库
	private Float stockQuantity;//配件库存数量STOCK_QUANTITY
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getStorageCode() {
		return storageCode;
	}
	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
	}
	public Float getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(Float stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	
	

}
