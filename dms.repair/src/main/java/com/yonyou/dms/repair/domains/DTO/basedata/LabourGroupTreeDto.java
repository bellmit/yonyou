
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : LabourGroupTreeDto.java
*
* @Author : rongzoujie
*
* @Date : 2016年8月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月9日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.basedata;

/**
* TODO description
* @author rongzoujie
* @date 2016年8月9日
*/

public class LabourGroupTreeDto {
    private String id;
    private String parent; //父节点
    private String text; //显示的内容
    
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
