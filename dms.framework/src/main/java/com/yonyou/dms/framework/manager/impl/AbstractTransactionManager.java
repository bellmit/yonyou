
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : AbstractTransactionManager.java
*
* @Author : zhangxc
*
* @Date : 2017年1月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月14日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.manager.impl;

import java.sql.Connection;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.function.exception.ApplicationException;
import com.yonyou.dms.function.exception.DALException;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.common.database.DBService;

/**
* 事务管理器抽象类
* @author zhangxc
* @date 2017年1月14日
*/

public abstract class AbstractTransactionManager {
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(TransactionDealerManagerImpl.class);
    
    /**
     * 
    * 开始事务
    * @author zhangxc
    * @date 2016年12月22日
    * @param dealerCode
     * @throws Exception 
     */
    void startTransAction(DBService dbService,String tenantId){
        //根据DealerCode 获得teantId
        try{
            dbService.beginTxn(tenantId);
            Connection conn = dbService.openConn(tenantId);
            Base.attach(conn);
        }catch(Exception e){
            throw new ApplicationException("开启事务失败",e);
        }
        
    }

    /**
     * 
    * 结束事务
    * @author zhangxc
     * @throws Exception 
    * @date 2016年12月22日
     */
    void endTransAction(DBService dbService){
        try{
            //结束事务
            dbService.endTxn(true);
        }catch(Exception e){
            throw new ApplicationException("结束事务失败",e);
        }
    }
    
    /**
     * 
    * 异常处理
    * @author zhangxc
    * @date 2016年12月22日
    * @param e
     */
    void exceptionTransAction(DBService dbService,Exception e){
        try{
            dbService.endTxn(false);
        }catch(Exception ex){
            logger.error(ex.getMessage(),ex);
        }
        //如果是业务异常
        if (e instanceof ServiceBizException ) {
           throw (ServiceBizException)e;
        };
        //如果是操作数据异常
        if(e instanceof DALException){
            throw (DALException)e;
        }
        throw new ApplicationException("执行出错"+e.getMessage(),e);
    }
}
