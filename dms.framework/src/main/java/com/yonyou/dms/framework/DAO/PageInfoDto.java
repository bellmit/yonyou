
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : PageInfoDto.java
*
* @Author : zhangxc
*
* @Date : 2016年7月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月6日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
    
package com.yonyou.dms.framework.DAO;

import java.util.List;
import java.util.Map;

/**
* 定义分页对象信息
* @author zhangxc
* @date 2016年7月6日
*/

public class PageInfoDto {

    private Long total;
    private List<Map> rows;
    
    public Long getTotal() {
        return total;
    }
    
    public void setTotal(Long total) {
        this.total = total;
    }
    
    public List<Map> getRows() {
        return rows;
    }
    
    public void setRows(List<Map> rows) {
        this.rows = rows;
    }
    
}
