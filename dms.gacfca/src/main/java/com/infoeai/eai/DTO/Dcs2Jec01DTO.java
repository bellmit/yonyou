package com.infoeai.eai.DTO;

/**
 * 车辆信息导入
 * @author luoyang
 *
 */
public class Dcs2Jec01DTO {
	
	private String sequenceId; // 序列号
	private String trunkCode; // VIN码
	private String model; // 车型
	private String style; // 车款
	private String buyDealer; // 车系
	private String isScan; // 扫描状态
	
	public String getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}
	public String getTrunkCode() {
		return trunkCode;
	}
	public void setTrunkCode(String trunkCode) {
		this.trunkCode = trunkCode;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getBuyDealer() {
		return buyDealer;
	}
	public void setBuyDealer(String buyDealer) {
		this.buyDealer = buyDealer;
	}
	public String getIsScan() {
		return isScan;
	}
	public void setIsScan(String isScan) {
		this.isScan = isScan;
	}
	
	

}
