
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : StockInDTO.java
*
* @Author : yangjie
*
* @Date : 2017年1月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月19日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.domains.DTO.stockManage;

import java.util.Date;
import java.util.List;

/**
 * TODO description
 * 
 * @author yangjie
 * @date 2017年1月19日
 */

public class StockInDTO {

    private String                seNo;

    private Integer               inType;

    private Date                  createDate;

    private String                createBy;

    private String                remark;

    private List<StockInItemsDTO> dms_table;

    public List<StockInItemsDTO> getDms_table() {
        return dms_table;
    }

    public void setDms_table(List<StockInItemsDTO> dms_table) {
        this.dms_table = dms_table;
    }

    public String getSeNo() {
        return seNo;
    }

    public void setSeNo(String seNo) {
        this.seNo = seNo;
    }

    public Integer getInType() {
        return inType;
    }

    public void setInType(Integer inType) {
        this.inType = inType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
