package com.yonyou.dms.web.domains.DTO.basedata.authority;

import java.util.Date;

/*
 * 用户信息
 */
public class SysUserDTO {
	
	
	private Long companyId;
	private Date birthday;
	private String handPhone;
	private String addr;
	private Date updateDate;
	private String empNum;
	private String password;
	private Integer userType;
	private Long createBy;
	private Date lastsigninTime;
	private Integer userStatus;
	private Date createDate;
	private String acnt;
	private Integer approvalLevelCode;
	private String phone;
	private String email;
	private String zipCode;
	private Integer balanceLevelCode;
	private Long updateBy;
	private Integer gender;
	private Integer isDown;
	private Long userId;
	private String name;
	private String personCode;
	//add by huyu
	private Date loginTime;//登录时间
	//end
	
	private String addIds;
	
	
	

	public String getAddIds() {
		return addIds;
	}

	public void setAddIds(String addIds) {
		this.addIds = addIds;
	}

	public void setCompanyId(Long companyId){
		this.companyId=companyId;
	}

	public Long getCompanyId(){
		return this.companyId;
	}

	public void setBirthday(Date birthday){
		this.birthday=birthday;
	}

	public Date getBirthday(){
		return this.birthday;
	}

	public void setHandPhone(String handPhone){
		this.handPhone=handPhone;
	}

	public String getHandPhone(){
		return this.handPhone;
	}

	public void setAddr(String addr){
		this.addr=addr;
	}

	public String getAddr(){
		return this.addr;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setEmpNum(String empNum){
		this.empNum=empNum;
	}

	public String getEmpNum(){
		return this.empNum;
	}

	public void setPassword(String password){
		this.password=password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setUserType(Integer userType){
		this.userType=userType;
	}

	public Integer getUserType(){
		return this.userType;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setLastsigninTime(Date lastsigninTime){
		this.lastsigninTime=lastsigninTime;
	}

	public Date getLastsigninTime(){
		return this.lastsigninTime;
	}

	public void setUserStatus(Integer userStatus){
		this.userStatus=userStatus;
	}

	public Integer getUserStatus(){
		return this.userStatus;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setAcnt(String acnt){
		this.acnt=acnt;
	}

	public String getAcnt(){
		return this.acnt;
	}

	public void setApprovalLevelCode(Integer approvalLevelCode){
		this.approvalLevelCode=approvalLevelCode;
	}

	public Integer getApprovalLevelCode(){
		return this.approvalLevelCode;
	}

	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setEmail(String email){
		this.email=email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setZipCode(String zipCode){
		this.zipCode=zipCode;
	}

	public String getZipCode(){
		return this.zipCode;
	}

	public void setBalanceLevelCode(Integer balanceLevelCode){
		this.balanceLevelCode=balanceLevelCode;
	}

	public Integer getBalanceLevelCode(){
		return this.balanceLevelCode;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setGender(Integer gender){
		this.gender=gender;
	}

	public Integer getGender(){
		return this.gender;
	}

	public void setIsDown(Integer isDown){
		this.isDown=isDown;
	}

	public Integer getIsDown(){
		return this.isDown;
	}

	public void setUserId(Long userId){
		this.userId=userId;
	}

	public Long getUserId(){
		return this.userId;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return this.name;
	}

	public void setPersonCode(String personCode){
		this.personCode=personCode;
	}

	public String getPersonCode(){
		return this.personCode;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

}
