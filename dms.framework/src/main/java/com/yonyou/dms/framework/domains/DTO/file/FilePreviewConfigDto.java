
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : FilePreviewConfigDto.java
*
* @Author : zhangxc
*
* @Date : 2016年10月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月27日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.domains.DTO.file;



/**
* 定义文件预览配置对象
* @author zhangxc
* @date 2016年10月27日
*/

public class FilePreviewConfigDto {

    private String caption;
    private Integer size;
    private Long key;
    private String type;
    private boolean showZoom;
    private String url;
    
    
    public String getCaption() {
        return caption;
    }
    
    public void setCaption(String caption) {
        this.caption = caption;
    }
    
    public Integer getSize() {
        return size;
    }
    
    public void setSize(Integer size) {
        this.size = size;
    }
    
    public Long getKey() {
        return key;
    }
    
    public void setKey(Long key) {
        this.key = key;
    }

    
    public String getType() {
        return type;
    }

    
    public void setType(String type) {
        this.type = type;
    }

    
    public boolean isShowZoom() {
        return showZoom;
    }

    
    public void setShowZoom(boolean showZoom) {
        this.showZoom = showZoom;
    }

    
    public String getUrl() {
        return url;
    }

    
    public void setUrl(String url) {
        this.url = url;
    }
    
}
