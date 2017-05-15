package com.infoeai.eai.DTO;

import java.util.List;

public class Dcs2Jec02DTO {
	
	// 客户信息
	private Long sequenceId; // 客户唯一ID
	private String code; // 客户唯一ID
	private String name; // 车主姓名/企业名称
	private String idOrCompCode; // 身份证号码/企业代码
	private String email; // 邮箱地址
	private String gender; // 性别
	private String province; // 省/直辖市
	private String city; // 市/区
	private String address; // 通讯地址
	private String postCode; // 邮政编码
	private String cellPhone; // 手机
	private String homePhone; // 住宅电话
	private String driveAge; // 驾龄
	private String hobby; // 兴趣爱好
	private String acceptPost; // 请勿打扰
	private String isScan; // 扫描状态

	private List<JECCarDTO> cars; // 购车信息
	private List<JECAddNVhclMaintainDTO> maintains; // 车辆维修信息
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdOrCompCode() {
		return idOrCompCode;
	}
	public void setIdOrCompCode(String idOrCompCode) {
		this.idOrCompCode = idOrCompCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getDriveAge() {
		return driveAge;
	}
	public void setDriveAge(String driveAge) {
		this.driveAge = driveAge;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getAcceptPost() {
		return acceptPost;
	}
	public void setAcceptPost(String acceptPost) {
		this.acceptPost = acceptPost;
	}
	public String getIsScan() {
		return isScan;
	}
	public void setIsScan(String isScan) {
		this.isScan = isScan;
	}
	public List<JECCarDTO> getCars() {
		return cars;
	}
	public void setCars(List<JECCarDTO> cars) {
		this.cars = cars;
	}
	public List<JECAddNVhclMaintainDTO> getMaintains() {
		return maintains;
	}
	public void setMaintains(List<JECAddNVhclMaintainDTO> maintains) {
		this.maintains = maintains;
	}
	
	
	

}
