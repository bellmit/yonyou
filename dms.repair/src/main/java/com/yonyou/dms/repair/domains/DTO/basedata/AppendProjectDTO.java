
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : AppendProjectDTO.java
*
* @Author : zhongsw
*
* @Date : 2016年8月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月19日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.basedata;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
* 附加项目定义
* @author zhongsw
* @date 2016年8月19日
*/

public class AppendProjectDTO {
     
    private String dealer_code;//经销商代码
    @Required
    private String add_item_code;//附加项目代码
    @Required
    private String add_item_name;//附加项目名称
    @Required
    private Double add_item_amount;//附加项目费用
    
    private Integer oem_tag;//是否OEM
    
    private Integer is_valid;//是否有效

    public String getDealer_code() {
        return dealer_code;
    }

    public void setDealer_code(String dealer_code) {
        this.dealer_code = dealer_code;
    }

    public String getAdd_item_code() {
        return add_item_code;
    }

    public void setAdd_item_code(String add_item_code) {
        this.add_item_code = add_item_code;
    }

    public String getAdd_item_name() {
        return add_item_name;
    }

    public void setAdd_item_name(String add_item_name) {
        this.add_item_name = add_item_name;
    }

    public Double getAdd_item_amount() {
        return add_item_amount;
    }

    public void setAdd_item_amount(Double add_item_amount) {
        this.add_item_amount = add_item_amount;
    }

    public Integer getOem_tag() {
        return oem_tag;
    }

    public void setOem_tag(Integer oem_tag) {
        this.oem_tag = oem_tag;
    }

    public Integer getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(Integer is_valid) {
        this.is_valid = is_valid;
    }

}
