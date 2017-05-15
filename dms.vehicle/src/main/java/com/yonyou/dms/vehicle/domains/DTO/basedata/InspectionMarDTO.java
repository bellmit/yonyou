
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : InspectionMarDTO.java
*
* @Author : yll
*
* @Date : 2016年9月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月28日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.domains.DTO.basedata;

/**
 * 验收质损明细
 * 
 * @author yll
 * @date 2016年9月28日
 */

public class InspectionMarDTO {

    private Integer marPosition;

    private Integer marDegree;

    private Integer marType;

    private String  marRemark;

    private Long itemId;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = Long.parseLong(itemId);
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
        this.marRemark = marRemark;
    }

}
