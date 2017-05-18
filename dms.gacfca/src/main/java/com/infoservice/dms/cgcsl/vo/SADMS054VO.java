/**
 * 
 */
package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class SADMS054VO extends BaseVO{
	private String entityCode;// 下端经销code
	private String dealerName;// 经销商简称
	private String execAuthor;// 执行人员姓名
	private String phone;// 留店电话
	private Integer isInputDms;// 是否录入DMS
	private String inputPhone;// 录入电话
	private String inputName;// 录入姓名
	private Date inputDate;// 录入时间
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
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
