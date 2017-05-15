
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : VinspectionMarDTO.java
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

/**
 * 质损明细
 * 
 * @author zhanshiwei
 * @date 2016年12月8日
 */

public class VinspectionMarDTO {

    private Integer vehicleInspectId;

    private Integer marPosition;

    private Integer marDegree;

    private Integer marType;

    private String  marRemark;

    public Integer getVehicleInspectId() {
        return vehicleInspectId;
    }

    public void setVehicleInspectId(Integer vehicleInspectId) {
        this.vehicleInspectId = vehicleInspectId;
    }

    public Integer getMarPosition() {
        return marPosition;
    }

    public void setMarPosition(Integer marPosition) {
        this.marPosition = marPosition;
    }

    public Integer getMarDegree() {
        return marDegree;
    }

    public void setMarDegree(Integer marDegree) {
        this.marDegree = marDegree;
    }

    public Integer getMarType() {
        return marType;
    }

    public void setMarType(Integer marType) {
        this.marType = marType;
    }

    public String getMarRemark() {
        return marRemark;
    }

    public void setMarRemark(String marRemark) {
        this.marRemark = marRemark == null ? null : marRemark.trim();
    }

}
