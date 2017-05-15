
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : FileUploadInfoDto.java
*
* @Author : zhangxc
*
* @Date : 2016年10月25日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月25日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.domains.DTO.file;



/**
* 文件上传的DTO 信息
* @author zhangxc
* @date 2016年10月25日
*/

public class FileUploadInfoDto {

    private Integer billType;
    private Integer fileSize;
    private String fileId;
    private Long billId;
    private Integer isValid;
    private Integer fileTypeDetail;
    private String fileName;
    
    public Integer getBillType() {
        return billType;
    }
    
    public void setBillType(Integer billType) {
        this.billType = billType;
    }
    
    public Integer getFileSize() {
        return fileSize;
    }
    
    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }
    
    public String getFileId() {
        return fileId;
    }
    
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    
    public Long getBillId() {
        return billId;
    }
    
    public void setBillId(Long billId) {
        this.billId = billId;
    }
    
    public Integer getIsValid() {
        return isValid;
    }
    
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
    
    public Integer getFileTypeDetail() {
        return fileTypeDetail;
    }
    
    public void setFileTypeDetail(Integer fileTypeDetail) {
        this.fileTypeDetail = fileTypeDetail;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
