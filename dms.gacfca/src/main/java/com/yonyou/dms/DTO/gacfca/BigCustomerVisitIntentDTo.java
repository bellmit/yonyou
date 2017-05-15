package com.yonyou.dms.DTO.gacfca;

public class BigCustomerVisitIntentDTo {
	private Long itemId;// 拜访记录ID
	private String intentProduct;// 产品代码
	private String intentBrand;// 品牌代码
	private String intentSeries;// 车系代码
	private String intentModel;// 车型代码
	private String intentConfig;// 配置代码
	private String intentColor;// 颜色代码
	private Integer purchaseCount;// 采购数量
	private Integer intentBuyTime;// 预计采购时间段
	private String competitorBrand;// 竞争品牌

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getIntentProduct() {
		return intentProduct;
	}

	public void setIntentProduct(String intentProduct) {
		this.intentProduct = intentProduct;
	}

	public String getIntentBrand() {
		return intentBrand;
	}

	public void setIntentBrand(String intentBrand) {
		this.intentBrand = intentBrand;
	}

	public String getIntentSeries() {
		return intentSeries;
	}

	public void setIntentSeries(String intentSeries) {
		this.intentSeries = intentSeries;
	}

	public String getIntentModel() {
		return intentModel;
	}

	public void setIntentModel(String intentModel) {
		this.intentModel = intentModel;
	}

	public String getIntentConfig() {
		return intentConfig;
	}

	public void setIntentConfig(String intentConfig) {
		this.intentConfig = intentConfig;
	}

	public String getIntentColor() {
		return intentColor;
	}

	public void setIntentColor(String intentColor) {
		this.intentColor = intentColor;
	}

	public Integer getPurchaseCount() {
		return purchaseCount;
	}

	public void setPurchaseCount(Integer purchaseCount) {
		this.purchaseCount = purchaseCount;
	}

	public Integer getIntentBuyTime() {
		return intentBuyTime;
	}

	public void setIntentBuyTime(Integer intentBuyTime) {
		this.intentBuyTime = intentBuyTime;
	}

	public String getCompetitorBrand() {
		return competitorBrand;
	}

	public void setCompetitorBrand(String competitorBrand) {
		this.competitorBrand = competitorBrand;
	}

}
