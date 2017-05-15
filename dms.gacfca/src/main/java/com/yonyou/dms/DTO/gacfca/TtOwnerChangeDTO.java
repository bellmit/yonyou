package com.yonyou.dms.DTO.gacfca;

/**
 * @description 上传车主关键信息变更历史
 * @author Administrator
 *
 */
public class TtOwnerChangeDTO {


	
	private Integer contactorProvince; //联系人省
	private String ownerName; //车主名称
	private String dealerCode;  //经销商代码
	private String contactorPhone; ////联系人电话
	private String ownerNo;  //车主编号
	private String contactorName;  //联系人名称
	private String contactorAddress;  //联系人地址
	private String contactorEmail;  //联系人EMAIL
 	private String contactorDistrict; //联系人区县
	private String contactorMobile;  //联系人手机
	private String contactorCity;  //联系人城市
	
	private String vin;
	
	
	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public void setContactorProvince(Integer contactorProvince){
		this.contactorProvince=contactorProvince;
	}

	public Integer getContactorProvince(){
		return this.contactorProvince;
	}

	public void setOwnerName(String ownerName){
		this.ownerName=ownerName;
	}

	public String getOwnerName(){
		return this.ownerName;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public void setContactorPhone(String contactorPhone){
		this.contactorPhone=contactorPhone;
	}

	public String getContactorPhone(){
		return this.contactorPhone;
	}

	public void setOwnerNo(String ownerNo){
		this.ownerNo=ownerNo;
	}

	public String getOwnerNo(){
		return this.ownerNo;
	}

	public void setContactorName(String contactorName){
		this.contactorName=contactorName;
	}

	public String getContactorName(){
		return this.contactorName;
	}

	public void setContactorAddress(String contactorAddress){
		this.contactorAddress=contactorAddress;
	}

	public String getContactorAddress(){
		return this.contactorAddress;
	}

	public void setContactorEmail(String contactorEmail){
		this.contactorEmail=contactorEmail;
	}

	public String getContactorEmail(){
		return this.contactorEmail;
	}

	public void setContactorDistrict(String contactorDistrict){
		this.contactorDistrict=contactorDistrict;
	}

	public String getContactorDistrict(){
		return this.contactorDistrict;
	}

	public void setContactorMobile(String contactorMobile){
		this.contactorMobile=contactorMobile;
	}

	public String getContactorMobile(){
		return this.contactorMobile;
	}

	public void setContactorCity(String contactorCity){
		this.contactorCity=contactorCity;
	}

	public String getContactorCity(){
		return this.contactorCity;
	}
}
