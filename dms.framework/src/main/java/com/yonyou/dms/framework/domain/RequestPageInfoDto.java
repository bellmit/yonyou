
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : RequestPageInfo.java
*
* @Author : zhangxc
*
* @Date : 2016年7月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月7日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
* 记录分页信息
* @author zhangxc
* @date 2016年7月7日
*/
@Component
@Scope("request")
public class RequestPageInfoDto {

    private String limit;
    private String sort;
    private String order;
    private String offset;
    
    public String getLimit() {
        return limit;
    }
    
    public void setLimit(String limit) {
        this.limit = limit;
    }
    
    public String getSort() {
        return sort;
    }
    
    public void setSort(String sort) {
        this.sort = sort;
    }
    
    public String getOrder() {
        return order;
    }
    
    public void setOrder(String order) {
        this.order = order;
    }
    
    public String getOffset() {
        return offset;
    }
    
    public void setOffset(String offset) {
        this.offset = offset;
    }
}
