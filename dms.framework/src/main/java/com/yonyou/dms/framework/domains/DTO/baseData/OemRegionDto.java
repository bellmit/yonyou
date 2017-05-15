/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.web
*
* @File name : RegionDto.java
*
* @Author : rongzoujie
*
* @Date : 2016年7月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月19日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.framework.domains.DTO.baseData;

import java.util.Map;

public class OemRegionDto {
    
    private Long region_id;
    private Long region_code;
    private String region_name;
    private Integer region_type;
    private Long parent_region_id;
    private Map<Long,OemRegionDto> children;
    
    public Long getRegion_code() {
		return region_code;
	}

	public void setRegion_code(Long region_code) {
		this.region_code = region_code;
	}

	public Long getRegion_id() {
        return region_id;
    }
    
    public void setRegion_id(Long region_id) {
        this.region_id = region_id;
    }
    
    public String getRegion_name() {
        return region_name;
    }
    
    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
    
    public Integer getRegion_type() {
        return region_type;
    }
    
    public void setRegion_type(Integer region_type) {
        this.region_type = region_type;
    }
    
    public Long getParent_region_id() {
        return parent_region_id;
    }
    
    public void setParent_region_id(Long parent_region_id) {
        this.parent_region_id = parent_region_id;
    }

    
    public Map<Long,OemRegionDto> getChildren() {
        return children;
    }

    
    public void setChildren(Map<Long,OemRegionDto> children) {
        this.children = children;
    }
}
