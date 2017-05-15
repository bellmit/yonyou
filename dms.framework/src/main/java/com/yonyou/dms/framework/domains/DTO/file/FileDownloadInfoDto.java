
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : FileDownloadInfoDto.java
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

import java.util.List;

/**
* 文件下载信息DTO
* @author zhangxc
* @date 2016年10月27日
*/

public class FileDownloadInfoDto {
    
    private List<String> initialPreview;
    private List<FilePreviewConfigDto> initialPreviewConfig;
    
    public List<String> getInitialPreview() {
        return initialPreview;
    }
    
    public void setInitialPreview(List<String> initialPreview) {
        this.initialPreview = initialPreview;
    }
    
    public List<FilePreviewConfigDto> getInitialPreviewConfig() {
        return initialPreviewConfig;
    }
    
    public void setInitialPreviewConfig(List<FilePreviewConfigDto> initialPreviewConfig) {
        this.initialPreviewConfig = initialPreviewConfig;
    }
}
