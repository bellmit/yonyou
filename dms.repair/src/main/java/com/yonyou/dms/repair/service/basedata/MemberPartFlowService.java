
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : repairOrderService.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年8月10日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月10日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.TtMemberPartFlowDTO;

/**
 * 会员配件项目
 * @author chenwei
 * @date 2017年4月18日
 */

public interface MemberPartFlowService {
	
    public List<Map> selectTtMemberPartFlow(Map<String, Object> queryParam) throws ServiceBizException;//查询会员配件项目信息
    
    public void deleteTtMemberPartFlow(Map<String, Object> deleteParams) throws ServiceBizException;
    
    public void modifyMemberPartFlowByParams(List updateParams) throws ServiceBizException;///根据map修改
    
    public void addMemberPartFlow(TtMemberPartFlowDTO cudto) throws ServiceBizException;//新增
}
