
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : InspectPdiItemDTO.java
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
 * pdi检查明细DTO
 * 
 * @author zhanshiwei
 * @date 2016年12月8日
 */

public class InspectPdiItemDTO {

    private Integer inspectPdiId;

    private Integer inspectCategory;

    private Integer inspectItem;

    private Integer isProblem;

    private String  inspectDescribe;

    public Integer getInspectPdiId() {
        return inspectPdiId;
    }

    public void setInspectPdiId(Integer inspectPdiId) {
        this.inspectPdiId = inspectPdiId;
    }

    public Integer getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(Integer inspectCategory) {
        this.inspectCategory = inspectCategory;
    }

    public Integer getInspectItem() {
        return inspectItem;
    }

    public void setInspectItem(Integer inspectItem) {
        this.inspectItem = inspectItem;
    }

    public Integer getIsProblem() {
        return isProblem;
    }

    public void setIsProblem(Integer isProblem) {
        this.isProblem = isProblem;
    }

    public String getInspectDescribe() {
        return inspectDescribe;
    }

    public void setInspectDescribe(String inspectDescribe) {
        this.inspectDescribe = inspectDescribe == null ? null : inspectDescribe.trim();
    }
}
