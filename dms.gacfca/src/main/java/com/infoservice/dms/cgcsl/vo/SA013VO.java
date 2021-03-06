package com.infoservice.dms.cgcsl.vo;

import java.util.Date;
import java.util.LinkedList;

import com.infoservice.dms.cgcsl.vo.BaseVO;

public class SA013VO extends BaseVO {
	private static final long serialVersionUID = 1L;
	private String entityCode;//经销商代码
	private Integer callIn;//展厅来电车系数量（来电有效客户数量）
	private Integer walkIn;//展厅来访车系数量(进店客流)
	private Integer noOfSc;//销售顾问人数（销售顾问数量）
	private Date currentDate;//当前时间
	private Date dataTime;//数据时间
	private LinkedList<SA012VO> list;//每日上报的展厅流量数据集合
	
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	
	public Integer getCallIn() {
		return callIn;
	}
	public void setCallIn(Integer callIn) {
		this.callIn = callIn;
	}
	public Integer getWalkIn() {
		return walkIn;
	}
	public void setWalkIn(Integer walkIn) {
		this.walkIn = walkIn;
	}
	public Integer getNoOfSc() {
		return noOfSc;
	}
	public void setNoOfSc(Integer noOfSc) {
		this.noOfSc = noOfSc;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	public Date getDataTime() {
		return dataTime;
	}
	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}
	public LinkedList<SA012VO> getList() {
		return list;
	}
	public void setList(LinkedList<SA012VO> list) {
		this.list = list;
	}
}
