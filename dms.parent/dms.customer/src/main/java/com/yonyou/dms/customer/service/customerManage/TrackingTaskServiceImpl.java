
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : TrackingTaskServiceImpl.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TrackingTaskPO;
import com.yonyou.dms.customer.domains.DTO.customerManage.TrackingTaskDTO;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * 跟进任务定义
 * 
 * @author zhanshiwei
 * @date 2016年9月6日
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
public class TrackingTaskServiceImpl implements TrackingTaskService {

    /**
     * 跟进任务定义 查询潜客
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.TrackingTaskService#queryTrackingTask(java.util.Map)
     */

    @Override
    public PageInfoDto queryTrackingTask(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("SELECT T.DEALER_CODE,T.TASK_ID,T.CUSTOMER_STATUS,T.INTENT_LEVEL,T.TASK_NAME,T.EXECUTE_TYPE,T.TASK_CONTENT,"
        		+ "T.INTERVAL_DAYS,T.KEEP_DAYS,T.TASK_TYPE,T.CONTACTOR_TYPE,T.IS_VALID,"
        		+ "T.BIG_CUSTOMER_INTERVAL_DAYS FROM ("+CommonConstants.VM_TRACKING_TASK+") T where 1=1 AND CUSTOMER_STATUS = '13211001'");
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("intentLevel"))) {
            sb.append(" and INTENT_LEVEL = ?");
            queryList.add(Integer.parseInt(queryParam.get("intentLevel")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("taskName"))) {
            sb.append(" and TASK_NAME like ?");
            queryList.add("%" + queryParam.get("taskName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("taskType"))) {
            sb.append(" and TASK_TYPE = ?");
            queryList.add(Integer.parseInt(queryParam.get("taskType")));
        }
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        
        System.err.println("111111:  "+sb.toString());
        return id;
    }
    
    /**
     * 跟进任务定义 查询保客
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.TrackingTaskService#queryTrackingTask(java.util.Map)
     */

    @Override
    public PageInfoDto queryTrackingTaskp(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("SELECT T.DEALER_CODE,T.TASK_ID,T.CUSTOMER_STATUS,T.INTENT_LEVEL,T.TASK_NAME,T.EXECUTE_TYPE,T.TASK_CONTENT,"
        		+ "T.INTERVAL_DAYS,T.KEEP_DAYS,T.TASK_TYPE,T.CONTACTOR_TYPE,T.IS_VALID,"
        		+ "T.BIG_CUSTOMER_INTERVAL_DAYS FROM ("+CommonConstants.VM_TRACKING_TASK+") T where 1=1 AND CUSTOMER_STATUS = '13211002'");
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("intentLevel"))) {
            sb.append(" and INTENT_LEVEL = ?");
            queryList.add(Integer.parseInt(queryParam.get("intentLevel")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("taskName"))) {
            sb.append(" and TASK_NAME like ?");
            queryList.add("%" + queryParam.get("taskName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("taskType"))) {
            sb.append(" and TASK_TYPE = ?");
            queryList.add(Integer.parseInt(queryParam.get("taskType")));
        }
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }
    

    /**
     * 根据ID 查询 跟进任务定义
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.TrackingTaskService#queryTrackingTaskByid(java.lang.Long)
     */

    @Override
    public TrackingTaskPO queryTrackingTaskByid(Long id) throws ServiceBizException {
        return TrackingTaskPO.findById(id);
    }

    /**
     * 跟进任务定义 新增
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param trackTaskDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.TrackingTaskService#addTrackingTask(com.yonyou.dms.customer.domains.DTO.customerManage.TrackingTaskDTO)
     */

    @Override
    public Long addTrackingTask(TrackingTaskDTO trackTaskDto) throws ServiceBizException {
        TrackingTaskPO trackTaskPo = new TrackingTaskPO();
//        List<Map> result = this.queryTrackingTaskBylevel(trackTaskDto.getIntentLevel(), null);
//        if (result.size() > 0) {
//            throw new ServiceBizException("已存在同一客户级别数据!");
//        }
        this.setTrackingTask(trackTaskPo, trackTaskDto);
        trackTaskPo.setInteger("TASK_TYPE", trackTaskDto.getTaskType());//任务状态
        trackTaskPo.saveIt();
        return trackTaskPo.getLongId();
    }

    /**
     * 设置 TrackingTaskPO 属性
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param trackTaskPo
     * @param trackTaskDto
     */

    public void setTrackingTask(TrackingTaskPO trackTaskPo, TrackingTaskDTO trackTaskDto) {
        trackTaskPo.setInteger("IS_VALID", trackTaskDto.getIsValid());//是否有效
        trackTaskPo.setString("TASK_CONTENT", trackTaskDto.getTaskContent());// 任务内容
        trackTaskPo.setInteger("INTERVAL_DAYS", trackTaskDto.getIntervalDays());// 间隔天数
        trackTaskPo.setInteger("CONTACTOR_TYPE", trackTaskDto.getContactorType());// 联系人类型
        trackTaskPo.setInteger("EXECUTE_TYPE", trackTaskDto.getExecuteType());// 执行方式
        trackTaskPo.setString("TASK_NAME", trackTaskDto.getTaskName());// 任务名称
        trackTaskPo.setInteger("KEEP_DAYS", trackTaskDto.getKeepDays());// 持续时间
        trackTaskPo.setInteger("BIG_CUSTOMER_INTERVAL_DAYS", trackTaskDto.getBigCustomerIntervalDays());// 大客户间隔天数
        trackTaskPo.setInteger("INTENT_LEVEL", trackTaskDto.getIntentLevel());// 意向级别      
        trackTaskPo.setInteger("CUSTOMER_STATUS", trackTaskDto.getCustomerStatus());//客户状态

    }

    /**
     * 跟进任务定义 修改
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param id
     * @param trackTaskDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.TrackingTaskService#modifyTrackingTask(java.lang.Long,
     * com.yonyou.dms.customer.domains.DTO.customerManage.TrackingTaskDTO)
     */

    @Override
    public Long modifyTrackingTask(Long id, TrackingTaskDTO trackTaskDto) throws ServiceBizException {
        TrackingTaskPO trackTaskPo = TrackingTaskPO.findById(id);
        trackTaskPo.setInteger("IS_VALID", trackTaskDto.getIsValid());//是否有效
        trackTaskPo.setString("TASK_CONTENT", trackTaskDto.getTaskContent());// 任务内容
        trackTaskPo.setInteger("INTERVAL_DAYS", trackTaskDto.getIntervalDays());// 间隔天数
        trackTaskPo.setInteger("CONTACTOR_TYPE", trackTaskDto.getContactorType());// 联系人类型
        trackTaskPo.setInteger("EXECUTE_TYPE", trackTaskDto.getExecuteType());// 执行方式
        trackTaskPo.setString("TASK_NAME", trackTaskDto.getTaskName());// 任务名称
        trackTaskPo.setInteger("KEEP_DAYS", trackTaskDto.getKeepDays());// 持续时间
        trackTaskPo.setInteger("BIG_CUSTOMER_INTERVAL_DAYS", trackTaskDto.getBigCustomerIntervalDays());// 大客户间隔天数
        trackTaskPo.setInteger("INTENT_LEVEL", trackTaskDto.getIntentLevel());// 意向级别      
    	
    	
        
        trackTaskPo.saveIt();
        return id;
    }

    /**
     * 同一客户级别 有效数据验证
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param lecel
     * @return
     */
    public List<Map> queryTrackingTaskBylevel(long lecel, Long taskid) {
        StringBuffer sb = new StringBuffer();
        sb.append("select TASK_ID,DEALER_CODE,TASK_NAME,INTERVAL_DAYS from  TM_TRACKING_TASK\n");
        sb.append(" where IS_VALID =? \n");
        sb.append(" and INTENT_LEVEL=? \n");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(DictCodeConstants.STATUS_IS_YES);
        queryList.add(lecel);
        if (taskid != null) {
            sb.append(" and TASK_ID <>  ? \n");
            queryList.add(taskid);
        }
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        return result;
    }

    /**
     * 根据级别查询间隔天数
     * 
     * @author zhanshiwei
     * @date 2016年9月20日
     * @param intentLevel
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.TrackingTaskService#queryTrackingTaskByIntentLevel(java.lang.Integer)
     */

    @Override
    public Map<String, Object> queryTrackingTaskByIntentLevel(Integer intentLevel) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("select TASK_ID,DEALER_CODE,INTERVAL_DAYS,INTENT_LEVEL,TASK_NAME from  TM_TRACKING_TASK\n");
        sb.append(" where IS_VALID =? \n");
        sb.append(" and INTENT_LEVEL=? \n");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(DictCodeConstants.STATUS_IS_YES);
        queryList.add(intentLevel);
        List<Map> resulList = DAOUtil.findAll(sb.toString(), queryList);
        return resulList.size()>0 ? resulList.get(0) : null;
    }
    
    /**
     * 根据ID 删除
     * @author wangxin
     */
    @Override
    public void deleteTrackingTaskById(Long id) throws ServiceBizException{
    	TrackingTaskPO trackTaskPo=TrackingTaskPO.findById(id);
    	trackTaskPo.delete();
    }
}
