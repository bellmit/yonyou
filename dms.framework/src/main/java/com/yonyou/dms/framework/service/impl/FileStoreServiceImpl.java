
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : FileStoreServiceImpl.java
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
	
package com.yonyou.dms.framework.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yonyou.dms.framework.domains.DTO.file.FileDownloadInfoDto;
import com.yonyou.dms.framework.domains.DTO.file.FilePreviewConfigDto;
import com.yonyou.dms.framework.domains.DTO.file.FileUploadInfoDto;
import com.yonyou.dms.framework.domains.PO.file.FileUploadInfoPO;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.f4.filestore.FileStore;
import com.yonyou.f4.filestore.FileStoreException;

/**
* 文件服务器实现类
* @author zhangxc
* @date 2016年10月13日
*/
@Service 
public class FileStoreServiceImpl implements FileStoreService {
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(FileStoreServiceImpl.class);
    //定义文件存储的实现类
    @Autowired
    FileStore fileStore;
    /**
     * 实现MultipartFile保存实现类
    * @author zhangxc
    * @date 2016年10月13日
    * @param importFile
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.service.FileStoreService#writeFile(org.springframework.web.multipart.MultipartFile)
    */

    @Override
    public String writeFile(MultipartFile importFile) {
        try{
            String fileId =  fileStore.write("BucketNode1", importFile.getOriginalFilename(), importFile.getInputStream());
            return fileId;
        }catch(Exception e){
            throw new ServiceBizException("将文件写入文件服务器失败",e);
        }
        
    }
    /**
     * 写入文件上传信息
    * @author zhangxc
    * @date 2016年10月25日
    * @param fileUploadDto
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.service.FileStoreService#insertFileUploadInfo(com.yonyou.dms.framework.domains.DTO.file.FileUploadInfoDto)
     */
    @Override
    public Long insertFileUploadInfo(FileUploadInfoDto fileUploadDto) {
        FileUploadInfoPO fileUploadPo = new FileUploadInfoPO();
        //设置对象属性
        fileUploadPo.setInteger("BILL_TYPE", fileUploadDto.getBillType());
        fileUploadPo.setInteger("FILE_SIZE", fileUploadDto.getFileSize());
        fileUploadPo.setString("FILE_ID", fileUploadDto.getFileId());
        fileUploadPo.setInteger("IS_VALID", fileUploadDto.getIsValid());
        fileUploadPo.setString("FILE_NAME", fileUploadDto.getFileName());
        //保存文件
        fileUploadPo.saveIt();
        return fileUploadPo.getLongId();
    }
    
    /**
     * 更新文件单据信息通过文件传的ID
    * @author zhangxc
    * @date 2016年10月25日
    * @param fileUploadId
    * @param billId
    * @param billType
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.service.FileStoreService#updateFileUploadInfoById(java.lang.Long, java.lang.Long, java.lang.Integer)
     */
    private void updateFileUploadInfoById(Long fileUploadId, String billId, Integer billType) {
        if(fileUploadId!=null){
            FileUploadInfoPO fileUploadPo = FileUploadInfoPO.findById(fileUploadId);
            if(fileUploadPo!=null){
                fileUploadPo.setString("BILL_ID", billId);
                fileUploadPo.setInteger("BILL_TYPE", billType);
                fileUploadPo.setInteger("IS_VALID", DictCodeConstants.STATUS_IS_VALID);
                fileUploadPo.saveIt();
            }else{
                throw new ServiceBizException("附件ID:"+fileUploadId.longValue()+"不存在");
            }
        }
        
    }
    /**
     * 获得上传到文件服务器上的信息
    * @author zhangxc
    * @date 2016年10月27日
    * @param fillType
    * @param billId
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.service.FileStoreService#getUploadFiles(java.lang.Integer, java.lang.Long)
     */
    @Override
    public FileDownloadInfoDto getUploadFiles(Integer fileType, String billId) {
        List<Object> paramList = new ArrayList<>();
        paramList.add(billId);
        paramList.add(fileType);
        //查询对应的附件列表
//        List<Map> fileUploadList =  DAOUtil.findAll("select t.DEALER_CODE,t.FILE_ID,t.FILE_NAME,t.FILE_SIZE,t.FILE_UPLOAD_INFO_ID from tc_file_upload_info t where t.BILL_ID = ? and t.BILL_TYPE = ? ", paramList);
        LazyList<FileUploadInfoPO> fileUploadList = FileUploadInfoPO.find("BILL_TYPE = ? and BILL_ID = ? and IS_VALID = ?", fileType,billId,DictCodeConstants.STATUS_IS_VALID).orderBy("FILE_UPLOAD_INFO_ID");
        if(!CommonUtils.isNullOrEmpty(fileUploadList)){
            //构建返回的对象信息
            FileDownloadInfoDto fileDownInfo = new FileDownloadInfoDto();
            List<String> initialPreviewList = new ArrayList<>();
            List<FilePreviewConfigDto> initialPreviewConfig  =new ArrayList<>();
            for(FileUploadInfoPO fileUploadInfo:fileUploadList){
                initialPreviewList.add("/basedata/download/billFilesReview/"+fileType.intValue()+"/"+fileUploadInfo.get("FILE_UPLOAD_INFO_ID").toString());
                //增加附件上传配置信息
                FilePreviewConfigDto configDto = new FilePreviewConfigDto();
                configDto.setCaption(fileUploadInfo.getString("FILE_NAME"));
                configDto.setSize(fileUploadInfo.getInteger("FILE_SIZE"));
                configDto.setKey(fileUploadInfo.getLong("FILE_UPLOAD_INFO_ID"));
                configDto.setUrl("/basedata/upload/delete/"+fileType.intValue()+"/"+fileUploadInfo.get("FILE_UPLOAD_INFO_ID").toString());
                String fileTypeCode = getFileTypeByFileName(fileUploadInfo.getString("FILE_NAME"));
                configDto.setType(fileTypeCode);
                if("image".equals(fileTypeCode)){
                    configDto.setShowZoom(true);
                }else{
                    configDto.setShowZoom(false);
                }
                initialPreviewConfig.add(configDto);
            }
            //设置预览信息
            fileDownInfo.setInitialPreview(initialPreviewList);
            fileDownInfo.setInitialPreviewConfig(initialPreviewConfig);
            return fileDownInfo;
        }
        return null;
    }
    
    
    /**
     * 
    * 根据文件名返回文件类型
    * @author zhangxc
    * @date 2016年10月27日
    * @return
     */
    private String getFileTypeByFileName(String fileName){
        if(!StringUtils.isNullOrEmpty(fileName)){
            if(fileName.lastIndexOf(".")!=-1){
                String extensions = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
                logger.debug(extensions);
                if(StringUtils.isMatcherPatten("^(?i)(doc|docx|xls|xlsx|csv|ppt|pptx|zip|rar|tar|gzip|gz|7z)$",extensions)){
                    return "other";
                }
                if(StringUtils.isMatcherPatten("^(?i)(jp?g|png|gif|bmp)$",extensions)){
                    return "image";
                }
                if(StringUtils.isMatcherPatten("^(?i)(txt|ini|md)$",extensions)){
                    return "text";
                }
                if(StringUtils.isMatcherPatten("^(?i)(pdf)$",extensions)){
                    return "pdf";
                }
                return "other";
            }else{
                return "other";
            }
        }else{
            return "other";
        }
    }
    
    /**
     * 将附件插入到系统中
    * @author zhangxc
    * @date 2016年10月27日
    * @param fileIds
    * @param billId
    * @param billType
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.service.FileStoreService#addFileUploadInfo(java.lang.String, java.lang.Long, java.lang.Integer)
     */
    @Override
    public void addFileUploadInfo(String fileIds, String billId, Integer billType) {
        if(!StringUtils.isNullOrEmpty(fileIds)){
            String[] newFileIds = fileIds.split(CommonConstants.FILEUPLOADID_SPLIT_STR_TYPE);
            //如果存在新上传的附件
            if(newFileIds.length==2){
                //更新文件上传单据ID及单据类型
                for(String fileUploadId:newFileIds[1].split(CommonConstants.FILEUPLOADID_SPLIT_STR)){
                    this.updateFileUploadInfoById(Long.parseLong(fileUploadId), billId, billType);
                }
            }
        }
    }
    
    /**
     * 将附件更新到系统中--用于修改操作
    * @author zhangxc
    * @date 2016年10月27日
    * @param fileIds
    * @param billId
    * @param billType
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.service.FileStoreService#addFileUploadInfo(java.lang.String, java.lang.Long, java.lang.Integer)
     */
    @Override
    public void updateFileUploadInfo(String fileIds, String billId, Integer billType) {
        
        //先将这个单据的所有的附件更新为无效
        this.updateNotValidByBillId(billId,billType);
        
        if(!StringUtils.isNullOrEmpty(fileIds)){
            String[] newFileIds = fileIds.split(CommonConstants.FILEUPLOADID_SPLIT_STR_TYPE);
            //如果存在已经上传的附件
            if(newFileIds.length>=1){
                //修改保留附件的状态为有效
                for(String fileUploadId:newFileIds[0].split(CommonConstants.FILEUPLOADID_SPLIT_STR)){
                    if(!StringUtils.isNullOrEmpty(fileUploadId)){
                        this.updateValidByFileInfoId(Long.parseLong(fileUploadId));
                    }
                }
            }
            
            //如果存在新上传的附件
            if(newFileIds.length==2){
                //更新文件上传单据ID及单据类型
                for(String fileUploadId:newFileIds[1].split(CommonConstants.FILEUPLOADID_SPLIT_STR)){
                    if(!StringUtils.isNullOrEmpty(fileUploadId)){
                        this.updateFileUploadInfoById(Long.parseLong(fileUploadId), billId, billType);
                    }
                }
            }
        }
    }
    
    
    /**
     * 
    * 将单据对应的附件的状态改为无效
    * @author zhangxc
    * @date 2016年10月28日
    * @param billId
    * @param billType
     */
    @Override
    public void updateNotValidByBillId(String billId, Integer billType){
        FileUploadInfoPO.update("IS_VALID = ? ", "BILL_TYPE = ? and BILL_ID = ? and IS_VALID = ?", DictCodeConstants.STATUS_NOT_VALID,billType,billId,DictCodeConstants.STATUS_IS_VALID);
    }
    
    /**
     * 
    * 将单据对应的附件的状态改为无效
    * @author zhangxc
    * @date 2016年10月28日
    * @param billId
    * @param billType
     */
    private void updateValidByFileInfoId(Long fileInfoId){
        FileUploadInfoPO fileUploadInfo = FileUploadInfoPO.findById(fileInfoId);
        fileUploadInfo.setString("IS_VALID", DictCodeConstants.STATUS_IS_VALID);
        fileUploadInfo.saveIt();
    }
    
    /**
     * 将单据对应的附件
    * @author zhangxc
    * @date 2016年11月3日
    * @param fileId
    * @param billId
    * @param billType
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.service.FileStoreService#addFileUploadInfo(java.lang.Long, java.lang.Long, java.lang.Integer)
     */
    @Override
    public void addFileUploadInfo(Long fileId, String billId, Integer billType) {
        //更新文件上传单据ID及单据类型
        this.updateFileUploadInfoById(fileId, billId, billType);
    }
    /**
     * 删除附件内容
    * @author zhangxc
    * @date 2016年11月9日
    * @param fileId
    * @return
    * (non-Javadoc)
     * @throws FileStoreException 
    * @see com.yonyou.dms.framework.service.FileStoreService#deleteFile(java.lang.String)
     */
    @Override
    public boolean deleteFile(String fileId) throws FileStoreException {
        try {
            return fileStore.delete(fileId);
        } catch (FileStoreException e) {
           throw e;
        }
    }
    
	public byte[] readFileByteByFileId(String fileId) throws FileStoreException {
			return fileStore.read(fileId);
	}
}
