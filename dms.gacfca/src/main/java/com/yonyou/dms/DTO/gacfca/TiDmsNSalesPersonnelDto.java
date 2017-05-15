package com.yonyou.dms.DTO.gacfca;

//import com.infoservice.de2.vo.VOUtil;

/**
 * 展厅销售人员信息同步(DMS新增)
 */
public class TiDmsNSalesPersonnelDto {

	private String userId;
	private String dealerCode;
	private String userName;
	private String roleId;
	private String email;
	private String mobile;
	private String roleName;
	private String isDccView;
	private String userStatus;
	private String salPassword;
	private String userCode;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getIsDccView() {
		return isDccView;
	}

	public void setIsDccView(String isDccView) {
		this.isDccView = isDccView;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getSalPassword() {
		return salPassword;
	}

	public void setSalPassword(String salPassword) {
		this.salPassword = salPassword;
	}

//	public String toXMLString() {
//		return VOUtil.vo2Xml(this);
//	}

}
