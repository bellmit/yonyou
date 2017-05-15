
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : StockOutListDTO.java
*
* @Author : DuPengXin
*
* @Date : 2016年9月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月27日    DuPengXin   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.vehicle.domains.DTO.basedata;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author DuPengXin
* @date 2016年9月27日
*/

@SuppressWarnings("rawtypes")
public class StockOutListDTO {
    private String                    sdNo;         // 出库单号

    private Integer                   outType;      // 出库类型

    private Date                      createDate;   // 开单日期

    private String                    createBy;     // 开单人

    private List<Map>                 dms_table;    // 出库单明细信息

    
    public String getSdNo() {
        return sdNo;
    }

    
    public void setSdNo(String sdNo) {
        this.sdNo = sdNo;
    }

    
    public Integer getOutType() {
        return outType;
    }

    
    public void setOutType(Integer outType) {
        this.outType = outType;
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

    
    public List<Map> getDms_table() {
        return dms_table;
    }

    
    public void setDms_table(List<Map> dms_table) {
        this.dms_table = dms_table;
    }
}
