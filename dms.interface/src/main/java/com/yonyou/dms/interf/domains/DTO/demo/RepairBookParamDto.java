/*
* Copyright 2016 Yonyou Auto Information Technology（Shanghai） Co., Ltd. All Rights Reserved.
*
* This software is published under the terms of the YONYOU Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : marketing-service-interface
*
* @File name : RepairBookParamDto.java
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
* @ description 养修预约 参数DTO
* @date 2016年12月5日
*/

public class RepairBookParamDto  implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -8005124113152874712L;
    //系统标志
    private String systemId;
    //预约日期(例：2016-10-01)
    private String bookDate;
    //经销商编号
    private String dealerCode;
    
    /**
     * @return the systemId
     */
    public String getSystemId() {
        return systemId;
    }
    
    /**
     * @param systemId the systemId to set
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    
    /**
     * @return the bookDate
     */
    public String getBookDate() {
        return bookDate;
    }
    
    /**
     * @param bookDate the bookDate to set
     */
    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }
    
    /**
     * @return the dealerCode
     */
    public String getDealerCode() {
        return dealerCode;
    }
    
    /**
     * @param dealerCode the dealerCode to set
     */
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    
}
