package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * @description 工单未结算维修项目列表
 * @author Administrator
 *
 */
public class RoNotSettleAccountsLabourDTO {


	private String labourCode; //下端：维修项目代码  VARCHAR(30)  上端：LON VARCHAR（60） 下端不提供索赔项目代码，只提供维修项目代码
	private String labourName; //下端：维修项目名称  VARCHAR(150)  上端：  
	private Double stdLabourHour; //下端：标准工时  NUMERIC(10,2)  上端：FRT DECIMAL (8, 2) 
	private String Remark; //下端：备注  VARCHAR(300)  上端：  
	private String activityCode; //下端：活动编号  VARCHAR(15)  上端：？  上端活动编号在工单主表中
	private Integer oemTag;		//写入是否oem信息 。下端12781001 是集团 12781002 或者 其他情况都不是集团
	public String getLabourCode() {
		return labourCode;
	}
	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
	}
	public String getLabourName() {
		return labourName;
	}
	public void setLabourName(String labourName) {
		this.labourName = labourName;
	}
	public Double getStdLabourHour() {
		return stdLabourHour;
	}
	public void setStdLabourHour(Double stdLabourHour) {
		this.stdLabourHour = stdLabourHour;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public Integer getOemTag() {
		return oemTag;
	}
	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
	}
}
