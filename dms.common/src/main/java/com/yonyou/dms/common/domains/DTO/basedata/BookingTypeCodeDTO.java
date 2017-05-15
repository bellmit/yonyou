package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

public class BookingTypeCodeDTO {


	private String bookingTypeName;
	private Date updateDate;
	private Long updateBy;
	private Long createBy;
	private Integer ver;
	private String bookingTypeCode;
	private Date createDate;

	public void setBookingTypeName(String bookingTypeName){
		this.bookingTypeName=bookingTypeName;
	}

	public String getBookingTypeName(){
		return this.bookingTypeName;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setBookingTypeCode(String bookingTypeCode){
		this.bookingTypeCode=bookingTypeCode;
	}

	public String getBookingTypeCode(){
		return this.bookingTypeCode;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

//	public String toXMLString(){
//		return VOUtil.vo2Xml(this);
//	}

}
