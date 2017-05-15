/**
 * 
 */
package com.infoservice.dms.cgcsl.vo;

/**
 * @author Administrator
 *
 */
public class ProperServiceManageVO extends BaseVO {
	private String vin;//
	private String serviceAdviser;// 客户经理ID
	private String employeeName;// 客户经理姓名
	private String mobile;// 联系电话
	private String DealerCode; // 下端：经销商代码 CHAR(8) 上端：

	
	// new add by wangjian 2015-5-27
	private String dispatchTime;// 分派时间
	private String dmsOwnerId;// 车主编号
	private String name;// 车主名称
	private String cellphone;// 车主电话
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getServiceAdviser() {
		return serviceAdviser;
	}
	public void setServiceAdviser(String serviceAdviser) {
		this.serviceAdviser = serviceAdviser;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDealerCode() {
		return DealerCode;
	}
	public void setDealerCode(String dealerCode) {
		DealerCode = dealerCode;
	}
	public String getDispatchTime() {
		return dispatchTime;
	}
	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	public String getDmsOwnerId() {
		return dmsOwnerId;
	}
	public void setDmsOwnerId(String dmsOwnerId) {
		this.dmsOwnerId = dmsOwnerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	
}
