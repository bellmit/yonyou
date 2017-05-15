
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.schedule
*
* @File name : TransactionAutoManagerImpl.java
*
* @Author : zhangxc
*
* @Date : 2016年11月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月9日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.manager.TransactionTenantManager;
import com.yonyou.dms.framework.manager.interf.AutoTransactionAction;
import com.yonyou.dms.framework.manager.interf.AutoTransactionDataAction;
import com.yonyou.dms.framework.manager.interf.AutoTransactionListAction;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.f4.common.database.DBService;

/**
* TODO description
* @author zhangxc
 * @param <E>
 * @param <E>
* @date 2016年11月9日
*/
@Service
public class TransactionTenantManagerImpl<T extends List<E>,E> extends AbstractTransactionManager implements TransactionTenantManager<T,E> {

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(TransactionTenantManagerImpl.class);
    @Autowired
    DBService dbService;
    
    /**
     * 获取查询数据
    * @author zhangxc
    * @date 2017年1月14日
    * @param tenantId
    * @param autoTransaction
    * @return
    * @throws Exception
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.manager.TransactionTenantManager#autoTransListExcute(java.lang.String, com.yonyou.dms.framework.manager.interf.AutoTransactionListAction)
     */
    public T autoTransListExcute(String tenantId,AutoTransactionListAction<T> autoTransaction) {
        //执行业务逻辑
        T returnList = null;
        try{
            //根据DealerCode 获得teantId
            //开始事务
            startTransAction(dbService,tenantId);
            T returnResult =  autoTransaction.autoTransAction();
            if(returnResult instanceof LazyList){
                if(!CommonUtils.isNullOrEmpty(returnResult)){
                    List<Object> conventList = new ArrayList<>();
                    for(Object fileUploadInfo:returnResult){
                        conventList.add(fileUploadInfo);
                    }
                    returnList = (T)conventList;
                }else{
                    return null;
                }
            }else{
                returnList = returnResult;
            }
            //结束事务
            endTransAction(dbService);
        }catch(Exception e){
            //异常处理
            exceptionTransAction(dbService,e);
        }finally{
            Base.detach();
            dbService.clean();
        }
        return returnList;
    }

    /**
     * 执行数据库操作，但是不返回值
    * @author zhangxc
    * @date 2016年11月9日
    * @param tenantId
    * @param action
    * @throws Exception
    * (non-Javadoc)
    * @see com.yonyou.dms.schedule.manager.TransactionAutoManager#autoTransExcute(java.lang.String, com.yonyou.dms.schedule.service.AutoTransactionAction)
     */
    @Override
    public void autoTransExcute(String tenantId,E dataValue, AutoTransactionDataAction<E> autoTransaction){
        try{
            //根据DealerCode 获得teantId
            //开始事务
            startTransAction(dbService,tenantId);
            
            //执行业务逻辑
            autoTransaction.autoTransAction(dataValue);
            
            //结束事务
            endTransAction(dbService);
        }catch(Exception e){
            //异常处理
            exceptionTransAction(dbService,e);
        }finally{
            Base.detach();
            dbService.clean();
        }
        
    }

    /**
     * 自动事务执行
    * @author zhangxc
    * @date 2017年1月14日
    * @param tenantId
    * @param autoTransaction
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.manager.TransactionTenantManager#autoTransExcute(java.lang.String, com.yonyou.dms.framework.manager.interf.AutoTransactionAction)
     */
    @Override
    public void autoTransExcute(String tenantId, AutoTransactionAction autoTransaction){
        try{
            //开始事务
            startTransAction(dbService,tenantId);
            
            //执行业务逻辑
            autoTransaction.autoTransAction();
            
            //结束事务
            endTransAction(dbService);
        }catch(Exception e){
            //异常处理
            exceptionTransAction(dbService,e);
        }finally{
            Base.detach();
            dbService.clean();
        }
        
    }

}
