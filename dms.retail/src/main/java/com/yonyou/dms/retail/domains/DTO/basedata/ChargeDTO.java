
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ChargeDTO.java
*
* @Author : zhongsw
*
* @Date : 2016年7月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月10日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.domains.DTO.basedata;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
* 收费区分
* @author zhongshiwei
* @date 2016年7月10日
*/

public class ChargeDTO {
    
    private String dealer_code;//经销商代码
    @Required
    private String charge_partition_code;//收费区分代码
    @Required
    private String charge_partition_name;//收费区分名称
    
    private Integer oem_tag;//OEM标志
    
    private Integer price_type;//价格类型
    
    private Integer is_print;//是否打印
    
    
    public String getDealer_code() {
        return dealer_code;
    }
    
    public void setDealer_code(String dealer_code) {
        this.dealer_code = dealer_code;
    }
    
    public String getCharge_partition_code() {
        return charge_partition_code;
    }

    
    public void setCharge_partition_code(String charge_partition_code) {
        this.charge_partition_code = charge_partition_code;
    }

    
    public String getCharge_partition_name() {
        return charge_partition_name;
    }

    
    public void setCharge_partition_name(String charge_partition_name) {
        this.charge_partition_name = charge_partition_name;
    }

    
    public Integer getOem_tag() {
        return oem_tag;
    }

    
    public void setOem_tag(Integer oem_tag) {
        this.oem_tag = oem_tag;
    }

    
    public Integer getPrice_type() {
        return price_type;
    }

    
    public void setPrice_type(Integer price_type) {
        this.price_type = price_type;
    }

	public Integer getIs_print() {
		return is_print;
	}

	public void setIs_prin(Integer is_print) {
		this.is_print = is_print;
	}

    
   
}
