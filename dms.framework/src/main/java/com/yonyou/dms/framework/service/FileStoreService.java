
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : FileStoreService.java
*
* @Author : zhangxc
*
* @Date : 2016年10月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月13日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.service;

import org.springframework.web.multipart.MultipartFile;

import com.yonyou.dms.framework.domains.DTO.file.FileDownloadInfoDto;
import com.yonyou.dms.framework.domains.DTO.file.FileUploadInfoDto;
import com.yonyou.f4.filestore.FileStoreException;

/**
* 文件服务Servie 方法
* @author zhangxc
* @date 2016年10月13日
*/

public interface FileStoreService {

    /**
     * 
    * 将MultipartFile 写入文件服务器
    * @author zhangxc
    * @date 2016年10月13日
    * @param importFile
    * @return
     */
    String writeFile(MultipartFile importFile);
    
    /**
     * 
    * 删除文件
    * @author zhangxc
    * @date 2016年11月9日
    * @param fileId
    * @return
     * @throws FileStoreException 
     */
    boolean deleteFile(String fileId) throws FileStoreException;
    /**
     * 
    * 写入文件上传信息
    * @author zhangxc
    * @date 2016年10月25日
    * @param fileUploadDto
     */
    Long insertFileUploadInfo(FileUploadInfoDto fileUploadDto);
    
    
    /**
     * 
    * 新增单据附件信息--用于新增功能
    * @author zhangxc
    * @date 2016年10月25日
    * @param fileUploadId
    * @param billId
    * @param billType
     */
    void addFileUploadInfo(String fileIds,String billId,Integer billType);
    
    /**
     * 
    * 增加附件信息:fileId
    * @author zhangxc
    * @date 2016年11月3日
    * @param fileIds
    * @param billId
    * @param billType
     */
    void addFileUploadInfo(Long fileId,String billId,Integer billType);
    
    /**
     * 
    * 修改单据附件信息--用于修改功能
    * @author zhangxc
    * @date 2016年10月27日
    * @param fileIds
    * @param billId
    * @param billType
     */
    void updateFileUploadInfo(String fileIds, String billId, Integer billType);
    /**
     * 
    * 获得文件上传到服务器上的附件信息
    * @author zhangxc
    * @date 2016年10月27日
    * @param fillType
    * @param billId
    * @return
     */
    FileDownloadInfoDto getUploadFiles(Integer fillType,String billId);
    
    /**
     * 
    * 根据单据ID 更新单据状态
    * @author zhangxc
    * @date 2016年11月4日
    * @param billId
    * @param billType
     */
    void updateNotValidByBillId(String billId, Integer billType);
    
    /**
     * 
    * 根据FileID 获取文件流
    * @author baojie
    * @date 2017年3月28日
    * @param fileId
     */
    byte[] readFileByteByFileId(String fileId)  throws FileStoreException;
}
