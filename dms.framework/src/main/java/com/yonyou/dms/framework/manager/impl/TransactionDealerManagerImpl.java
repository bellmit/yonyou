
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

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.manager.TransactionDealerManager;
import com.yonyou.dms.framework.manager.interf.AutoTransactionAction;
import com.yonyou.dms.framework.manager.interf.AutoTransactionDataAction;
import com.yonyou.dms.framework.service.TenantDealerMappingService;
import com.yonyou.f4.common.database.DBService;

/**
* 进行接口的事务控制
* @author zhangxc
 * @param <E>
 * @param <E>
* @date 2016年11月9日
*/
@Service
public class TransactionDealerManagerImpl<E> extends AbstractTransactionManager implements TransactionDealerManager<E> {

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(TransactionDealerManagerImpl.class);
    @Autowired
    DBService dbService;
    @Autowired
    private TenantDealerMappingService tenantDealerSerivce;
    
    
    /**
     * 执行数据库操作，但是不返回值
    * @author zhangxc
    * @date 2016年11月9日
    * @param tenantId
    * @param action
    * @throws Exception
    * (non-Javadoc)
    * @see com.yonyou.dms.schedule.manager.TransactionDealerManager#autoTransExcute(java.lang.String, com.yonyou.dms.schedule.service.AutoTransactionDataAction)
     */
    @Override
    public void autoTransExcute(String dealerCode,E dataValue, AutoTransactionDataAction<E> autoTransaction) {
        try{
            //根据DealerCode 获得teantId
            String tenantId = tenantDealerSerivce.getTenantIdByDealerCode(dealerCode);
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
     * 实现事务控制
    * @author zhangxc
    * @date 2016年12月22日
    * @param dealerCode
    * @param autoTransaction
    * @throws Exception
    * (non-Javadoc)
    * @see com.yonyou.dms.interf.manager.TransactionDealerManager#autoTransExcute(java.lang.String, com.yonyou.dms.interf.manager.AutoTransactionDataAction)
     */
    @Override
    public void autoTransExcute(String dealerCode, AutoTransactionAction autoTransaction){
        try{
            //根据DealerCode 获得teantId
            String tenantId = tenantDealerSerivce.getTenantIdByDealerCode(dealerCode);
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
