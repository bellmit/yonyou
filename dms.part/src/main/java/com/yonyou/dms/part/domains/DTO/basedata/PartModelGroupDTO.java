
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

import java.util.Date;

/**
* 配件车型组表
* @author chenwei
* @date 2017年3月22日
*/

public class PartModelGroupDTO {
    
    private String dealer_code;//经销商代码
    
    private String part_model_group_code;//配件车型组代码

    private String part_model_group_name;//配件车型组名称
    
    private Integer oem_tag;//是否OEM
    
    private Date down_timestamp;
    
    public Integer getOem_tag() {
        return oem_tag;
    }

    
    public void setOem_tag(Integer oem_tag) {
        this.oem_tag = oem_tag;
    }


	public String getPart_model_group_code() {
		return part_model_group_code;
	}


	public void setPart_model_group_code(String part_model_group_code) {
		this.part_model_group_code = part_model_group_code;
	}


	public String getPart_model_group_name() {
		return part_model_group_name;
	}


	public void setPart_model_group_name(String part_model_group_name) {
		this.part_model_group_name = part_model_group_name;
	}


	public String getDealer_code() {
		return dealer_code;
	}


	public void setDealer_code(String dealer_code) {
		this.dealer_code = dealer_code;
	}


	public Date getDown_timestamp() {
		return down_timestamp;
	}


	public void setDown_timestamp(Date down_timestamp) {
		this.down_timestamp = down_timestamp;
	}

}
