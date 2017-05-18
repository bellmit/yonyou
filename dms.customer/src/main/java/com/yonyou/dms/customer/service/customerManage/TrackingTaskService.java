
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : TrackingTaskService.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月6日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TrackingTaskPO;
import com.yonyou.dms.customer.domains.DTO.customerManage.TrackingTaskDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 跟进任务定义
 * 
 * @author zhanshiwei
 * @date 2016年9月6日
 */

public interface TrackingTaskService {

    public PageInfoDto queryTrackingTask(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto queryTrackingTaskp(Map<String, String> queryParam) throws ServiceBizException;

    public TrackingTaskPO queryTrackingTaskByid(Long id) throws ServiceBizException;

    public Long addTrackingTask(TrackingTaskDTO trackTaskDto) throws ServiceBizException;

    public Long modifyTrackingTask(Long id, TrackingTaskDTO trackTaskDto) throws ServiceBizException;

    public Map<String, Object> queryTrackingTaskByIntentLevel(Integer intentLevel) throws ServiceBizException;

    public List<Map> queryTrackingTaskBylevel(long lecel, Long taskid);
    
    public void deleteTrackingTaskById(Long id)throws ServiceBizException;///删除
}
