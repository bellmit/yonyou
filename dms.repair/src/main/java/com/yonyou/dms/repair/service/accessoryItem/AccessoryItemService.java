/** 
 *Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Author : zhengcong
 *
 * @Date : 2017年4月20日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年4月20日    zhengcong    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */
package com.yonyou.dms.repair.service.accessoryItem;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 工单附件项目Service
 * 
  * @author zhengcong
 * @date 2017年4月20日
 */

public interface AccessoryItemService {

    public List<Map> aiList() throws ServiceBizException;//附加项目下拉列

    public List<Map> cpList() throws ServiceBizException;//收费区分下拉列
    

}