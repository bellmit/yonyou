
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.web
*
* @File name : FilesCommonUpload.java
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
	
package com.yonyou.dms.web.controller.basedata;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.yonyou.dms.framework.domains.DTO.file.FileUploadInfoDto;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 文件常规上传方法
* @author zhangxc
* @date 2016年10月13日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/upload")
public class FilesCommonUpload extends BaseController{

    @Autowired
    FileStoreService fileStoreService;
    /**
     * 
    * 上传文件，并返回ID
    * @author zhangxc
    * @date 2016年9月18日
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Map<String,String> uploadFiles(@RequestParam(value = "dmsFile") MultipartFile importFile){
        String fileId = fileStoreService.writeFile(importFile);
        //创建文件DTO 对象 
        FileUploadInfoDto fileUploadDto = new FileUploadInfoDto();
        fileUploadDto.setFileId(fileId);
        fileUploadDto.setFileName(importFile.getOriginalFilename());
        fileUploadDto.setFileSize((int)importFile.getSize());
        fileUploadDto.setIsValid(DictCodeConstants.STATUS_NOT_VALID);
        
        Long fileUploadInfoId = fileStoreService.insertFileUploadInfo(fileUploadDto);
        Map<String,String> returnMap = new HashMap<>();
        returnMap.put("fileUploadId", fileUploadInfoId.toString());
        return returnMap;
    }
    
    
    /**
     * 
    * 删除上传的附件，目前为空实现，什么都不做
    * @author zhangxc
    * @date 2016年9月18日
     */
    @RequestMapping(value = "/delete/{billType}/{fileId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUploadFiles(@PathVariable(value = "billType") Integer billType,@PathVariable(value = "fileId") Long fileId){
    }
}
