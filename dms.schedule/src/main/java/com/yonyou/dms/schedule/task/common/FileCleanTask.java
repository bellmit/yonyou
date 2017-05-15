
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.schedule
*
* @File name : FileCleanTask.java
*
* @Author : zhangxc
*
* @Date : 2016年11月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月8日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.schedule.task.common;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.framework.domains.PO.file.FileUploadInfoPO;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.schedule.manager.AbstractTenantSingletonTask;
import com.yonyou.f4.common.database.DBService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* 文件清理的task
* @author zhangxc
* @date 2016年11月8日
*/
@TxnConn
@Component
public class FileCleanTask extends AbstractTenantSingletonTask{

 // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(FileCleanTask.class);
  /*  @Autowired
    FileStoreService fileService;*/
    @Autowired
    DBService dbService;
    
    /**
     * 执行定时任务
    * @author zhangxc
    * @date 2017年1月14日
    * @throws Exception
    * (non-Javadoc)
    * @see com.yonyou.f4.schedule.task.Task#execute()
     */
    public void tenantExceute(String tenantId) {
        //查询无效的文件
        List<FileUploadInfoPO> invalidFiles = FileUploadInfoPO.find("IS_VALID = ? and CREATED_AT < ?", DictCodeConstants.STATUS_NOT_VALID,DateUtil.addDay(new Date(),-1));
        
        if(!CommonUtils.isNullOrEmpty(invalidFiles)){
            //查询无效的文件
            for(FileUploadInfoPO fileUploadInfo:invalidFiles){
                String fileId = null;
                try{
                    fileId = fileUploadInfo.getString("FILE_ID");
                    //fileService.deleteFile(fileId);
                    fileUploadInfo.delete();
                }catch(Exception e){
                    logger.error("清理文件失败:"+fileId,e);
                }
            }
        }
    }
}
