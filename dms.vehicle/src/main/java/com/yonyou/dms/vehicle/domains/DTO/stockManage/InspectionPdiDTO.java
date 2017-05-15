
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : InspectionPdiDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年12月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月8日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.vehicle.domains.DTO.stockManage;

import java.util.Date;

/**
* pdi检查DTO
* @author zhanshiwei
* @date 2016年12月8日
*/

public class InspectionPdiDTO {

    private Integer inspectPdiId;

    private Integer inspectType;

    private Integer vsStockId;

    private String  bussinessNo;

    private String  inspectPerson;
    private Date    inspectDate;

    public Integer getInspectPdiId() {
        return inspectPdiId;
    }

    public void setInspectPdiId(Integer inspectPdiId) {
        this.inspectPdiId = inspectPdiId;
    }

    public Integer getInspectType() {
        return inspectType;
    }

    public void setInspectType(Integer inspectType) {
        this.inspectType = inspectType;
    }

    public Integer getVsStockId() {
        return vsStockId;
    }

    public void setVsStockId(Integer vsStockId) {
        this.vsStockId = vsStockId;
    }

    public String getBussinessNo() {
        return bussinessNo;
    }

    public void setBussinessNo(String bussinessNo) {
        this.bussinessNo = bussinessNo == null ? null : bussinessNo.trim();
    }

    public String getInspectPerson() {
        return inspectPerson;
    }

    public void setInspectPerson(String inspectPerson) {
        this.inspectPerson = inspectPerson == null ? null : inspectPerson.trim();
    }

    public Date getInspectDate() {
        return inspectDate;
    }

    public void setInspectDate(Date inspectDate) {
        this.inspectDate = inspectDate;
    }

}
