
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BookingLimitDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年10月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月12日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.basedata;

/**
* 预约限量DTO
* @author zhanshiwei
* @date 2016年10月12日
*/

public class BookingLimitDTO {
    private Integer bookingId;

    private String bookingTypeCode;

    private String beginTime;

    private String endTime;

    public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public String getBookingTypeCode() {
		return bookingTypeCode;
	}

	public void setBookingTypeCode(String bookingTypeCode) {
		this.bookingTypeCode = bookingTypeCode;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getSaturation() {
		return saturation;
	}

	public void setSaturation(Integer saturation) {
		this.saturation = saturation;
	}

	private Integer saturation;

}
