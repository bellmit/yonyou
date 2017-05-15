/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : WorkGroupServiceImpl.java
*
* @Author : xukl
*
* @Date : 2016年6月30日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年6月30日    xukl    1.0
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
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.WorkGroupDto;
import com.yonyou.dms.repair.domains.PO.basedata.WorkGroupPO;


/**
*WorkGroupServiceImpl
* @author xukl
* @date 2016年7月11日
*/
	
@Service
public class WorkGroupServiceImpl implements WorkGroupService{
	
	/**
	 * 查询单条班组信息
	* @author xukl
	* @date 2016年7月11日
	* @param id
	* @return
	* (non-Javadoc)
	* @see com.yonyou.dms.repair.service.workgroup.WorkGroupService#getWorkGroupById(java.lang.Long)
	*/
		
	@Override
	public Map getWorkGroupById(String id) throws ServiceBizException{
		List<Object> params = new ArrayList<>();
        params.add(id);
    	StringBuilder sqlSb = new StringBuilder("SELECT tmw.WORKGROUP_CODE,tmw.WORKGROUP_NAME,tmw.ORG_CODE,tmo.ORG_NAME,tmw.DEALER_CODE,tmw.WORKER_TYPE_CODE,tmy.WORKER_TYPE FROM TM_WORKGROUP  as tmw ");
		sqlSb.append("LEFT JOIN tm_organization  as tmo on tmw.ORG_CODE = tmo.ORG_CODE ");
		sqlSb.append("LEFT JOIN tm_worker_type  as tmy on tmw.WORKER_TYPE_CODE = tmy.WORKER_TYPE_CODE where 1=1");
		sqlSb.append(" and tmw.WORKGROUP_CODE = ? ");
		Map first = DAOUtil.findFirst(sqlSb.toString(),params);
		return first;
	}
	
	
	
	/**
	 * 查询班组信息
	* @author xukl
	* @date 2016年7月11日
	* @param queryParam
	* @return
	* @see com.yonyou.dms.repair.service.workgroup.WorkGroupService#queryWorkGroups(java.util.Map)
	*/
		
	@Override
	public PageInfoDto queryWorkGroups(Map<String,String> queryParam) throws ServiceBizException{
		StringBuilder sqlSb = new StringBuilder("SELECT tmw.WORKGROUP_CODE,tmw.WORKGROUP_NAME,tmw.ORG_CODE,tmo.ORG_NAME,tmw.DEALER_CODE,tmw.WORKER_TYPE_CODE,tmy.WORKER_TYPE FROM TM_WORKGROUP  as tmw ");
		sqlSb.append("LEFT JOIN tm_organization  as tmo on tmw.ORG_CODE = tmo.ORG_CODE ");
		sqlSb.append("LEFT JOIN tm_worker_type  as tmy on tmw.WORKER_TYPE_CODE = tmy.WORKER_TYPE_CODE where 1=1");
		List<Object> params = new ArrayList<Object>();
	    if(!StringUtils.isNullOrEmpty(queryParam.get("workgroupCode"))){
	        sqlSb.append(" AND tmw.WORKGROUP_CODE like ?");
	        params.add("%"+queryParam.get("workgroupCode")+"%");
	    }
	    if(!StringUtils.isNullOrEmpty(queryParam.get("workgroupName"))){
            sqlSb.append(" AND tmw.WORKGROUP_NAME like ?");
            params.add("%"+queryParam.get("workgroupName")+"%");
        }
	    if(!StringUtils.isNullOrEmpty(queryParam.get("workerType"))){
	    	sqlSb.append(" and tmy.WORKER_TYPE = ?");
	    	params.add(queryParam.get("workerType"));
		}
	    if(!StringUtils.isNullOrEmpty(queryParam.get("orgCode"))){
	    	sqlSb.append(" and tmw.ORG_CODE = ?");
	    	params.add(queryParam.get("orgCode"));
		}
	       PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(),params);

		return pageInfoDto;
	}

	
	/**修改保存班组信息
	* @author xukl
	* @date 2016年7月11日
	* @param id
	* @param workGroupDto
	* @see com.yonyou.dms.repair.service.workgroup.WorkGroupService#modifyWorkGroup(java.lang.Long, com.yonyou.dms.repair.domains.DTO.basedata.WorkGroupDto)
	*/
		
	@Override
	public void modifyWorkGroup(String id,WorkGroupDto workGroupDto) throws ServiceBizException{
		WorkGroupPO workGroup = WorkGroupPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
		String workgroupname = workGroupDto.getWorkgroupName();
        StringBuffer sbstr= new StringBuffer("select * from tm_workgroup where WORKGROUP_NAME = ? and WORKGROUP_CODE = ?");
        List<Object> params = new ArrayList<>();
        params.add(workgroupname);
        params.add(id);
        if(!CommonUtils.isNullOrEmpty(DAOUtil.findAll(sbstr.toString(), params))){
            throw new ServiceBizException("该班组名称已存在！");
        }
        
		workGroup.setString("workgroup_name", workGroupDto.getWorkgroupName());
		workGroup.setString("org_code", workGroupDto.getOrgCode());
		workGroup.setString("worker_type_code", workGroupDto.getWorkerTypeCode());
		workGroup.saveIt();
	}

	
	/**
	 * 新增班组信息
	* @author xukl
	* @date 2016年7月11日
	* @param workGroupDto
	* @return
	* (non-Javadoc)
	* @see com.yonyou.dms.repair.service.workgroup.WorkGroupService#addWorkGroup(com.yonyou.dms.repair.domains.DTO.basedata.WorkGroupDto)
	*/
		
	@Override
	public String addWorkGroup(WorkGroupDto workGroupDto) throws ServiceBizException{
		WorkGroupPO workGroup = new WorkGroupPO();
		String workgroupcode = workGroupDto.getWorkgroupCode();
		StringBuffer sb= new StringBuffer("select * from tm_workgroup where WORKGROUP_CODE = ?");
		List<Object> param = new ArrayList<>();
		param.add(workgroupcode);
		List list = DAOUtil.findAll(sb.toString(), param);
		if(!CommonUtils.isNullOrEmpty(list)){
		    throw new ServiceBizException("该班组代码已存在！");
		}
		String workgroupname = workGroupDto.getWorkgroupName();
        StringBuffer sbstr= new StringBuffer("select * from tm_workgroup where WORKGROUP_NAME = ?");
        List<Object> params = new ArrayList<>();
        params.add(workgroupname);
        if(!CommonUtils.isNullOrEmpty(DAOUtil.findAll(sbstr.toString(), params))){
            throw new ServiceBizException("该班组名称已存在！");
        }
		workGroup.setString("workgroup_code", workgroupcode);
		workGroup.setString("workgroup_name", workGroupDto.getWorkgroupName());
		workGroup.setString("org_code", workGroupDto.getOrgCode());
		workGroup.setString("worker_type_code", workGroupDto.getWorkerTypeCode());
		workGroup.saveIt();
		return  workGroup.getString("WORKGROUP_CODE");
	}



	/**
	 * 班组下拉框
	* @author jcsi
	* @date 2016年10月11日
	* @return
	* (non-Javadoc)
	* @see com.yonyou.dms.repair.service.basedata.WorkGroupService#selectWorkGroupDicts()
	 */
    @Override
    public List<Map> selectWorkGroupDicts() {
        String sql = " SELECT DEALER_CODE,WORKGROUP_CODE,WORKGROUP_NAME,ORG_CODE,WORKER_TYPE_CODE FROM TM_WORKGROUP WHERE 1=1";
        List<Object> queryParam=new ArrayList<Object>();
        return DAOUtil.findAll(sql, queryParam);
    }


    /**
	 * 部门查询
	* @author jcsi
	* @date 2016年10月11日
	* @return
	* (non-Javadoc)
	* @see com.yonyou.dms.repair.service.basedata.WorkGroupService#selectWorkGroupDicts()
	 */
	@Override
	public List<Map> getWorkerGroupCode(String workerType)  {
		StringBuffer sbstr= new StringBuffer("select DEALER_CODE,WORKER_TYPE_CODE,WORKER_TYPE from tm_worker_type where 1=1 ");
		List<Object> params = new ArrayList<Object>();
	    if(!StringUtils.isNullOrEmpty(workerType)){
	    	sbstr.append(" AND WORKER_TYPE = ?");
	        params.add(workerType);
	       
	    }
	    return DAOUtil.findAll(sbstr.toString(), params);
	}
	
	

}
