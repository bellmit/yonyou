/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : WorkerTypeServiceImpl.java
*
* @Author : jcsi
*
* @Date : 2016年7月1日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月1日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.WorkerTypeDto;
import com.yonyou.dms.repair.domains.PO.basedata.WorkerTypePo;


/**
* 工种定义 接口 实现类
* @author jcsi
* @date 2016年7月29日
 */

@Service
@SuppressWarnings("rawtypes")
public class WorkerTypeServiceImpl implements WorkerTypeService {

    /**
     *根据条件 查询工种信息
    * @author jcsi
    * @date 2016年7月29日
    * @param queryParam
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.WorkerTypeService#queryWorkerType(java.util.Map)
     */
	@Override
	public PageInfoDto queryWorkerType(Map<String,String> queryParam) throws ServiceBizException{
		StringBuilder sb=new StringBuilder("select type.WORKER_TYPE_CODE,type.WORKER_TYPE_NAME,type.WORKER_TYPE,type.DEALER_CODE from  tm_worker_type type where 1=1  ");		
		List<Object> queryList=new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(queryParam.get("workerTypeCode"))){
			  sb.append(" and WORKER_TYPE_CODE like ?");
			  queryList.add("%"+queryParam.get("workerTypeCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("workerTypeName"))){
			  sb.append(" and WORKER_TYPE_NAME like ? ");
			  queryList.add("%"+queryParam.get("workerTypeName")+"%");
		}
        if(!StringUtils.isNullOrEmpty(queryParam.get("workerType"))){
			  sb.append(" and WORKER_TYPE = ?");
			  queryList.add(Integer.parseInt(queryParam.get("workerType")));
		}
//        if(!StringUtils.isNullOrEmpty(queryParam.get("isValid"))){
//            sb.append(" and IS_VALID = ? ");
//            queryList.add(Integer.parseInt(queryParam.get("isValid")));
//        }             		
		PageInfoDto pageInfoDto=DAOUtil.pageQuery(sb.toString(), queryList);
		return pageInfoDto;
	}

	/**
	 * 新增
	* @author jcsi
	* @date 2016年7月29日
	* @param workerTypeDto
	* @return
	* (non-Javadoc)
	* @see com.yonyou.dms.repair.service.basedata.WorkerTypeService#insertWOrkerType(com.yonyou.dms.repair.domains.DTO.basedata.WorkerTypeDto)
	 */
	@Override
	public String insertWOrkerType(WorkerTypeDto workerTypeDto)throws ServiceBizException {
		WorkerTypePo workerTypePo=new WorkerTypePo();
		if(SearchByWorkTypeCode(workerTypeDto.getWorkerTypeCode())){
		    if(SearchByWorkTypeName(null,workerTypeDto.getWorkerTypeName())){
		        assignmentWtpo(workerTypePo,workerTypeDto);
	            workerTypePo.saveIt();
	            return workerTypePo.getInteger("WORKER_TYPE").toString();
		    }else{
		        throw new ServiceBizException("该工种名称已经存在");
		    }
		}else{
		    throw new ServiceBizException("该工种代码已经存在");
		}
		
		
		
	}
	/**
     * 查询数据库中是否已经存在此工种代码
     * @author jcsi
     * @date 2016年8月1日
     * @param employeeNo
     * @return 
     * @throws ServiceBizException
     */
    public boolean SearchByWorkTypeCode(String workTypeCode)throws ServiceBizException{
        StringBuilder sb=new StringBuilder("SELECT t.WORKER_TYPE_CODE,t.DEALER_CODE from TM_WORKER_TYPE t where t.WORKER_TYPE_CODE=? ");
        List<Object> param=new ArrayList<Object>();
        param.add(workTypeCode);
		List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()==0){
            return true;
        }
        return false;
    }
    /**
     * 查询数据库中是否已经存在此工种名称
     * @author jcsi
     * @date 2016年8月1日
     * @param employeeNo
     * @return 
     * @throws ServiceBizException
     */
    public boolean SearchByWorkTypeName(Long id,String workTypeName)throws ServiceBizException{
        StringBuilder sb=new StringBuilder("SELECT t.WORKER_TYPE_CODE,t.DEALER_CODE from TM_WORKER_TYPE t where  t.WORKER_TYPE_NAME=? ");
        List<Object> param=new ArrayList<Object>();
        param.add(workTypeName);
        if(!StringUtils.isNullOrEmpty(id)){
            sb.append(" and t.WORKER_TYPE_ID <> ?");
            param.add(id);
        }
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()==0){
            return true;
        }
        return false;
    }
    

    /**
     * 更新
    * @author Administrator
    * @date 2016年7月29日
    * @param id
    * @param workerTypeDto
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.WorkerTypeService#updateWorkerType(java.lang.Long, com.yonyou.dms.repair.domains.DTO.basedata.WorkerTypeDto)
     */
	@Override
	public void updateWorkerType(String id, WorkerTypeDto workerTypeDto) throws ServiceBizException {
		 WorkerTypePo wtp=WorkerTypePo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
		 if((SearchByWorkTypeCode(workerTypeDto.getWorkerTypeName()))){
		     assignmentWtpo(wtp,workerTypeDto);
		 }else{
		     throw new ServiceBizException("该工种名称已经存在");
		 }
		 wtp.saveIt();
	}
    /**
     * 删除
    * @author jcsi
    * @date 2016年7月29日
    * @param id
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.WorkerTypeService#deleteWOrkerType(java.lang.Long)
     */
	@Override
	public void deleteWOrkerType(String id)throws ServiceBizException {
		WorkerTypePo wtp=WorkerTypePo.findById(id);
		wtp.delete();
	}
    /**
     * 根据id查找工种信息
    * @author jcsi
    * @date 2016年7月29日
    * @param id
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.WorkerTypeService#findById(java.lang.Long)
     */
    @Override
    public Map findById(String id)throws ServiceBizException {       
    	List<Object> query=new ArrayList<Object>();
        query.add(id);
		Map first = DAOUtil.findFirst("SELECT DEALER_CODE,WORKER_TYPE,WORKER_TYPE_CODE,WORKER_TYPE_NAME FROM tm_worker_type WHERE WORKER_TYPE_CODE=?", query);
        return first;
    }
    
    /**
     * WorkerTypePo对象赋值
    * @author jcsi
    * @date 2016年7月7日
    * @param wtp
    * @param workerTypeDto
     */
    private void assignmentWtpo(WorkerTypePo wtp,WorkerTypeDto workerTypeDto)throws ServiceBizException{
        wtp.setString("WORKER_TYPE_CODE", workerTypeDto.getWorkerTypeCode());
        wtp.setString("WORKER_TYPE_NAME", workerTypeDto.getWorkerTypeName());
        wtp.setInteger("WORKER_TYPE", workerTypeDto.getWorkerType());
//        wtp.setInteger("IS_VALID", workerTypeDto.getIsValid());
    }

    /**
     * 工种 （下拉框显示）
    * @author jcsi
    * @date 2016年8月1日
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.WorkerTypeService#findAllWorkerType()
     */
    @Override
    public List<Map> findAllWorkerType() throws ServiceBizException{
        StringBuilder sb=new StringBuilder("SELECT DEALER_CODE,WORKER_TYPE_CODE,WORKER_TYPE_NAME from tm_worker_type where 1=1");
        List<Object> queryParam=new ArrayList<Object>();
        return  DAOUtil.findAll(sb.toString(), queryParam);
    }
    
}
