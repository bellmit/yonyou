
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : StoreDTO.java
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
	
package com.yonyou.dms.part.domains.DTO.basedata;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 仓库表
 * 
 * @author zhengcong
 * @date 2017年3月21日
 */

public class StoreDTO {
    
//    private Long storage_id;//仓库ID
    
    private String dealer_code;//经销商代码
    @Required
    private String storage_code;//仓库代码
    @Required
    private String storage_name;//仓库名称
    
    private Integer storage_type;//仓库类型
    
    private Integer lead_time;//订货周期
    
    private Integer is_negative;//是否允许负库存
    
    private Integer oem_tag;//是否OEM
    
    private Integer workshop_tag;//车间发料仓库标志
    

    
    
    public String getDealer_code() {
        return dealer_code;
    }
    
    public void setDealer_code(String dealer_code) {
        this.dealer_code = dealer_code;
    }
    
    public String getStorage_code() {
        return storage_code;
    }
    
    public void setStorage_code(String storage_code) {
        this.storage_code = storage_code;
    }
    
    public String getStorage_name() {
        return storage_name;
    }
    
    public void setStorage_name(String storage_name) {
        this.storage_name = storage_name;
    }
    
    public Integer getStorage_type() {
        return storage_type;
    }

    
    public void setStorage_type(Integer storage_type) {
        this.storage_type = storage_type;
    }

    
    public Integer getLead_time() {
        return lead_time;
    }

    
    public void setLead_time(Integer lead_time) {
        this.lead_time = lead_time;
    }

    
    public Integer getIs_negative() {
        return is_negative;
    }

    
    public void setIs_negative(Integer is_negative) {
        this.is_negative = is_negative;
    }

    
    public Integer getOem_tag() {
        return oem_tag;
    }

    
    public void setOem_tag(Integer oem_tag) {
        this.oem_tag = oem_tag;
    }

	public Integer getWorkshop_tag() {
		return workshop_tag;
	}

	public void setWorkshop_tag(Integer workshop_tag) {
		this.workshop_tag = workshop_tag;
	}





}
