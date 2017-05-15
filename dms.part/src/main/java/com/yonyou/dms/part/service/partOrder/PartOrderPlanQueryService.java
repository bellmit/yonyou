
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartOrderPlanQueryService.java
*
* @Author : zhanshiwei
*
* @Date : 2017年5月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月5日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.service.partOrder;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 配件订货计划查询
 * 
 * @author zhanshiwei
 * @date 2017年5月5日
 */

public interface PartOrderPlanQueryService {

    public PageInfoDto queryDmsPtOrderPlan(Map<String, String> queryParam) throws ServiceBizException;

    public List<Map> queryDmsPtOrderPlanExport(Map<String, String> queryParam) throws ServiceBizException;
}
