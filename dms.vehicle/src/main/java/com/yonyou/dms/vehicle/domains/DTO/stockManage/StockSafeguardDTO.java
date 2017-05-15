
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : StockSafeguardDTO.java
*
* @Author : yangjie
*
* @Date : 2016年12月31日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月31日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.domains.DTO.stockManage;

import java.io.Serializable;

/**
 * TODO 用于树状图
 * 
 * @author yangjie
 * @date 2016年12月31日
 */

@SuppressWarnings("serial")
public class StockSafeguardDTO implements Serializable {

    private String id;
    private String parent;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
