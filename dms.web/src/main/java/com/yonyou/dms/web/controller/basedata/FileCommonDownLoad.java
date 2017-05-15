
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.web
*
* @File name : TempleteDownLoad.java
*
* @Author : zhangxc
*
* @Date : 2016年9月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月18日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.web.controller.basedata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.domains.DTO.file.FileDownloadInfoDto;
import com.yonyou.dms.framework.domains.PO.file.FileUploadInfoPO;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.f4.filestore.FileStore;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 模板下载功能
* @author zhangxc
* @date 2016年9月18日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/download")
public class FileCommonDownLoad extends BaseController{

    @Autowired
    SystemParamService paramService;
    
    //定义文件存储的实现类
    @Autowired
    FileStore fileStore;
    
    @Autowired
    FileStoreService fileStoreService;
    /**
     * 
    * 下载模板
    * @author zhangxc
    * @date 2016年9月18日
     */
    @RequestMapping(value = "/template/{type}",method = RequestMethod.GET)
    public void donwloadTemplte(@PathVariable(value = "type") String type,HttpServletRequest request,HttpServletResponse response){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //查询对应的参数
            BasicParametersDTO paramDto = paramService.queryBasicParameterByTypeandCode(ParamCodeConstants.TEMPLATE_DOWNLOAD, type);
            Resource resource = new ClassPathResource(paramDto.getParamValue()); 
            //获取文件名称
            FrameHttpUtil.setExportFileName(request,response, resource.getFilename());
            
            response.addHeader("Content-Length", "" + resource.getFile().length());
            
            bis = new BufferedInputStream(resource.getInputStream());
            byte[] bytes = new byte[1024];
            bos = new BufferedOutputStream(response.getOutputStream());
            while((bis.read(bytes))!=-1){
                bos.write(bytes);
            }
            bos.flush();
        } catch (Exception e) {
            throw new ServiceBizException("下载模板失败,请与管理员联系",e);
        }finally{
            IOUtils.closeStream(bis);
            IOUtils.closeStream(bos);
        }
    }
    
    
    /**
     * 
    * 下载模板
    * @author zhangxc
    * @date 2016年9月18日
     */
    @RequestMapping(value = "/billFiles/{fileType}/{billId}",method = RequestMethod.GET)
    @ResponseBody
    public FileDownloadInfoDto donwloadBillFiles(@PathVariable(value = "fileType") Integer fileType,@PathVariable(value = "billId") String billId){
        return fileStoreService.getUploadFiles(fileType, billId);
    }
    
    /**
     * 
    * 根据ID 下载对应的附件
    * @author zhangxc
    * @date 2016年10月27日
    * @param fileUpladInfoId
    * @param response
     */
    @RequestMapping(value = "/billFilesReview/{fileType}/{fileUpladInfoId}",method = RequestMethod.GET)
    public void reviewFileById(@PathVariable(value = "fileUpladInfoId") Long fileUpladInfoId,HttpServletResponse response){
        //文件预览
        commonFileDownLoad(fileUpladInfoId,false,response);
    }
    
    /**
     * 
    * 根据ID 下载对应的附件
    * @author zhangxc
    * @date 2016年10月27日
    * @param fileUpladInfoId
    * @param response
     */
    @RequestMapping(value = "/billFilesDownload/{fileType}/{fileUpladInfoId}",method = RequestMethod.GET)
    public void downLoadFileById(@PathVariable(value = "fileUpladInfoId") Long fileUpladInfoId,HttpServletResponse response){
        //文件下载
        commonFileDownLoad(fileUpladInfoId,true,response);
    }
   
    /**
     * 
    * 执行常规文件下载
    * @author zhangxc
    * @date 2016年11月7日
    * @param isDownLoad
     */
    private void commonFileDownLoad(Long fileUpladInfoId,boolean isDownLoad,HttpServletResponse response){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //查找对应的附件信息
            FileUploadInfoPO fileUploadInfo = FileUploadInfoPO.findById(fileUpladInfoId);
            
            String fileId = fileUploadInfo.getString("FILE_ID");
            String filename = new String(fileUploadInfo.getString("FILE_NAME").getBytes(), "ISO8859-1");
            if(isDownLoad){
                response.setHeader("Content-Disposition", "attachment;filename=\""
                        + filename+"\"");
                response.addHeader("Content-Length", "" + fileUploadInfo.getLong("FILE_SIZE"));
            }
            
            String mineType = new MimetypesFileTypeMap().getContentType(filename);
            response.setContentType(mineType);
           
            bis = new BufferedInputStream(fileStore.getInputStream(fileId));
            byte[] bytes = new byte[1024];
            bos = new BufferedOutputStream(response.getOutputStream());
            while((bis.read(bytes))!=-1){
                bos.write(bytes);
            }
            bos.flush();
        } catch (Exception e) {
            throw new ServiceBizException("下载附件失败,请与管理员联系",e);
        }finally{
            IOUtils.closeStream(bis);
            IOUtils.closeStream(bos);
        }
    }
    
    
    /**
     * 
    * 执行filestore中的文件下载
    * @author baojie
    * @date 2017年3月31日
    * @param fileid
     */
    @RequestMapping(value = "/{fileid1}/{fileid2}/{fileid3}/{fileid4}/filestore",method = RequestMethod.GET)
    public void donwloadFilestore(@PathVariable(value = "fileid1") String fileid1,@PathVariable(value = "fileid2") String fileid2,
                                  @PathVariable(value = "fileid3") String fileid3,@PathVariable(value = "fileid4") String fileid4,HttpServletRequest request,HttpServletResponse response){
    	  // response.setContentType("image/*");  
    			ByteArrayInputStream bis = null;  
    	        OutputStream os = null;
    	        String fileid = "/"+fileid1+"/"+fileid2+"/"+fileid3+"/"+fileid4;
    	        System.out.println(fileid);
    	        try {  
    	            bis = new ByteArrayInputStream(fileStoreService.readFileByteByFileId(fileid));  
    	            os = response.getOutputStream();  
    	            int count = 0;  
    	            byte[] buffer = new byte[1024 * 8];  
    	            while ((count = bis.read(buffer)) != -1) {  
    	                os.write(buffer, 0, count);  
    	                os.flush();  
    	            }  
    	        } catch (Exception e) {  
    	            e.printStackTrace();  
    	        }  
    	        try {  
    	            bis.close();  
    	            os.close();  
    	        } catch (IOException e) {  
    	            e.printStackTrace();  
    	        }  
    }
    
  
}
