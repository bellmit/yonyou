/*
* Copyright 2016 Yonyou Auto Information Technology（Shanghai） Co., Ltd. All Rights Reserved.
*
* This software is published under the terms of the YONYOU Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : marketing-service-interface
*
* @File name : RepairBookingTimeDto.java
*
* @Author : sangdeliang
*
* @Date : 2016年12月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月5日    sangdeliang    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.interf.domains.DTO.demo;

import java.io.Serializable;

/**
*
* @author zhangxc
* @ description 养修预约时间段获取DTO
* @date 2016年12月5日
*/

public class RepairBookTimeDto  implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 3184036356281866257L;
    //开始时间
    private String beginTime;
    //结束时间
    private String endTime;
    //空闲工位数
    private Long spareStationNum;
    //总工位数
    private Long totalStationNum;
    
    /**
     * @return the beginTime
     */
    public String getBeginTime() {
        return beginTime;
    }
    
    /**
     * @param beginTime the beginTime to set
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
    
    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }
    
    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    /**
     * @return the spareStationNum
     */
    public Long getSpareStationNum() {
        return spareStationNum;
    }
    
    /**
     * @param spareStationNum the spareStationNum to set
     */
    public void setSpareStationNum(Long spareStationNum) {
        this.spareStationNum = spareStationNum;
    }
    
    /**
     * @return the totalStationNum
     */
    public Long getTotalStationNum() {
        return totalStationNum;
    }
    
    /**
     * @param totalStationNum the totalStationNum to set
     */
    public void setTotalStationNum(Long totalStationNum) {
        this.totalStationNum = totalStationNum;
    }
    
    
}
