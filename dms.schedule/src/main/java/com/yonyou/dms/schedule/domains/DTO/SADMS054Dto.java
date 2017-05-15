package com.yonyou.dms.schedule.domains.DTO;

import java.util.Date;
/**
 * 克莱斯勒明检和神秘上报
 * @author wangliang
 * @date 2017年2月14日
 */
public class SADMS054Dto {
	private Integer  mysteriousId; 
	private String dealerCode; //经销商代码
	private String dealerName; //经销商简称
	private String execAuthor; //执行人员姓名
	private String phone; //留店电话
	private Integer isInputDms; //是否录入DMS
	private String inputPhone; //录入电话
	private String inputName; //录入姓名
	private Date inputDate; //录入时间
	
	public Integer getMysteriousId() {
		return mysteriousId;
	}
	public void setMysteriousId(Integer mysteriousId) {
		this.mysteriousId = mysteriousId;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getExecAuthor() {
		return execAuthor;
	}
	public void setExecAuthor(String execAuthor) {
		this.execAuthor = execAuthor;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getIsInputDms() {
		return isInputDms;
	}
	public void setIsInputDms(Integer isInputDms) {
		this.isInputDms = isInputDms;
	}
	public String getInputPhone() {
		return inputPhone;
	}
	public void setInputPhone(String inputPhone) {
		this.inputPhone = inputPhone;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	
}
