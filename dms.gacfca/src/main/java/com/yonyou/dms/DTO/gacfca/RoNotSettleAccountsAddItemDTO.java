package com.yonyou.dms.DTO.gacfca;

/**
 * @description 未结算工单附加项目列表 
 * @author Administrator
 *
 */
public class RoNotSettleAccountsAddItemDTO {
	private String addItemCode; //下端：附加项目代码  CHAR(4)  上端：ITEM_CODE VARCHAR(60) 
	private String addItemName; //下端：附加项目名称  VARCHAR(90)  上端：ITEM_NAME VARCHAR(200) 
	private Double addItemAmount; //下端：附加项目费  NUMERIC(12,2)  上端：ITEM_FEE NUMERIC(12,2) 上端下发的附加项目，下端不可以修改 其费用
	private String activityCode; //下端：活动编号  VARCHAR(15)  上端：  上端活动编号在工单主表中
	private String remark; //下端：备注  VARCHAR(300)  上端：  
	private Integer oemTag;		//写入是否oem信息 。下端12781001 是集团 12781002 或者 其他情况都不是集团
	public String getAddItemCode() {
		return addItemCode;
	}
	public void setAddItemCode(String addItemCode) {
		this.addItemCode = addItemCode;
	}
	public String getAddItemName() {
		return addItemName;
	}
	public void setAddItemName(String addItemName) {
		this.addItemName = addItemName;
	}
	public Double getAddItemAmount() {
		return addItemAmount;
	}
	public void setAddItemAmount(Double addItemAmount) {
		this.addItemAmount = addItemAmount;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getOemTag() {
		return oemTag;
	}
	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
	}
	
}
