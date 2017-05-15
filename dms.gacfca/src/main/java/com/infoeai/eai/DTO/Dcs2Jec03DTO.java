package com.infoeai.eai.DTO;

import java.util.List;

public class Dcs2Jec03DTO {
	
	// 车辆维修信息
	private Long sequenceId; // 序列号
	private String code; // 客户唯一ID
	private String isScan; // 扫描状态
	private List<JECRecommendDTO> recommendations;// 推介信息
	private List<JECMaintainDTO> maintains;// 车辆维修信息
	
	public Long getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIsScan() {
		return isScan;
	}
	public void setIsScan(String isScan) {
		this.isScan = isScan;
	}
	public List<JECRecommendDTO> getRecommendations() {
		return recommendations;
	}
	public void setRecommendations(List<JECRecommendDTO> recommendations) {
		this.recommendations = recommendations;
	}
	public List<JECMaintainDTO> getMaintains() {
		return maintains;
	}
	public void setMaintains(List<JECMaintainDTO> maintains) {
		this.maintains = maintains;
	}
	
	
	
	
	

}
