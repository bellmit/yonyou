
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.schedule
*
* @File name : AbstractTenantSingletonTask.java
*
* @Author : zhangxc
*
* @Date : 2017年2月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月11日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.schedule.manager;

import com.yonyou.f4.schedule.task.TenantSingletonTask;

/**
* 定义对于租户唯一的定义任务的抽象类
* @author zhangxc
* @date 2017年2月11日
*/

public abstract class AbstractTenantSingletonTask extends TenantSingletonTask{

    /**
     * 实现F4中需要实现的接口
    * @author zhangxc
    * @date 2017年2月11日
    * @throws Exception
    * (non-Javadoc)
    * @see com.yonyou.f4.schedule.task.Task#execute()
     */
    @Override
    public void execute() throws Exception {
        String tenantId = super.getTriggerInfo().getTenants();
        //实现租户下的定时任务逻辑
        tenantExceute(tenantId);
    }

    /**
     * 
    * 定义租户模式下定时任务实现
    * @author zhangxc
    * @date 2017年2月11日
    * @param tenantId
     */
    public abstract void tenantExceute(String tenantId);
    
}
