
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : LabourPriceDTO1.java
 *
 * @Author : zhengcong
 *
 * @Date : 2017年3月23日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月23日    zhengcong    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.domains.DTO.basedata;

/**
 * 
 * 工时单价DTO
 * @author ZhengHe
 * @date 2016年6月30日
 */
public class LabourPriceDTO {


    private String labourPriceCode;

    private Double labourPrice;

    private Integer oemTag;

    public String getLabourPriceCode() {
        return labourPriceCode;
    }

    public void setLabourPriceCode(String labourPriceCode) {
        this.labourPriceCode = labourPriceCode ;
    }

    public Double getLabourPrice() {
        return labourPrice;
    }

    public void setLabourPrice(Double labourPrice) {
        this.labourPrice = labourPrice;
    }

    public Integer getOemTag() {
        return oemTag;
    }

    public void setOemTag(Integer oemTag) {
        this.oemTag = oemTag;
    }
}
