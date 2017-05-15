
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
import com.yonyou.dms.framework.manager.TransactionTenantManager;
import com.yonyou.dms.framework.manager.interf.AutoTransactionDataAction;
import com.yonyou.dms.framework.manager.interf.AutoTransactionListAction;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.schedule.manager.AbstractTenantSingletonTask;

/**
* 文件清理的task，每task 试用于某个循环一个事务
* @author zhangxc
* @date 2016年11月8日
*/
@Component
public class FileCleanSingleTransctionTask extends AbstractTenantSingletonTask{

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(FileCleanSingleTransctionTask.class);
   /* @Autowired
    FileStoreService fileService;*/
    
    @Autowired
    TransactionTenantManager<List<FileUploadInfoPO>,FileUploadInfoPO> autoTransManager;
    
    /**
     * 执行文件清理
    * @author zhangxc
    * @date 2017年1月14日
    * @throws Exception
    * (non-Javadoc)
    * @see com.yonyou.f4.schedule.task.Task#execute()
     */
    public void tenantExceute(String tenantId) {
        //获得对应的列表
        List<FileUploadInfoPO> invalidFiles = queryValidateList(tenantId);
        
        //对数据进行独立处理
        if(!CommonUtils.isNullOrEmpty(invalidFiles)){
            for(FileUploadInfoPO fileUploadInfo:invalidFiles){
                //循环执行事务操作
                autoTransManager.autoTransExcute(tenantId,fileUploadInfo, new AutoTransactionDataAction<FileUploadInfoPO>() {
                    @Override
                    public void autoTransAction(FileUploadInfoPO fileUploadInfo) {
                        String fileId = null;
                        try{
                            fileId = fileUploadInfo.getString("FILE_ID");
                            //fileService.deleteFile(fileId);
                            fileUploadInfo.delete();
                        }catch(Exception e){
                            logger.error("清理文件失败:"+fileId,e);
                        }
                    }
                });
            }
        }
    }

    
    /**
     * 
    * 查询对应的数据
    * @author zhangxc
    * @date 2016年11月9日
    * @param tenantId
    * @return
    * @throws Exception
     */
    
    private List<FileUploadInfoPO> queryValidateList(String tenantId){
        
        //执行自动查询逻辑
        return autoTransManager.autoTransListExcute(tenantId, new AutoTransactionListAction<List<FileUploadInfoPO>>() {
            @Override
            public List<FileUploadInfoPO> autoTransAction() {
                //查询无效的文件
                return FileUploadInfoPO.find("IS_VALID = ? and CREATED_AT < ?", DictCodeConstants.STATUS_NOT_VALID,DateUtil.addDay(new Date(),-1));
            }
        });
    }
}
