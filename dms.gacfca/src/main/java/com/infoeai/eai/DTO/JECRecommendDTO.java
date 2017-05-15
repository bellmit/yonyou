package com.infoeai.eai.DTO;

public class JECRecommendDTO {
	
	private String dmsMappingId; // 推介ID
	private String recommendDate; // 推介日期
	private String recommendeeName; // 客户姓名(被推荐人)
	private String recommendModel; // 介绍车型
	
	public String getDmsMappingId() {
		return dmsMappingId;
	}
	public void setDmsMappingId(String dmsMappingId) {
		this.dmsMappingId = dmsMappingId;
	}
	public String getRecommendDate() {
		return recommendDate;
	}
	public void setRecommendDate(String recommendDate) {
		this.recommendDate = recommendDate;
	}
	public String getRecommendeeName() {
		return recommendeeName;
	}
	public void setRecommendeeName(String recommendeeName) {
		this.recommendeeName = recommendeeName;
	}
	public String getRecommendModel() {
		return recommendModel;
	}
	public void setRecommendModel(String recommendModel) {
		this.recommendModel = recommendModel;
	}
	
	
	

}
