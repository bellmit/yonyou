
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : RoleCtrlServiceImpl.java
 *
 * @Author : yll
 *
 * @Date : 2016年8月8日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月8日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.service.basedata.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.PO.basedata.role.RoleCtrlPO;

/**
 * 角色受控权限实现类
 * @author yll
 * @date 2016年8月8日
 */
@Service
public class RoleCtrlServiceImpl implements RoleCtrlService{

	/**
	 * 添加角色受控权限
	 * @author yll
	 * @date 2016年8月12日
	 * @param RoleID
	 * @param ctrlCode
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.role.RoleCtrlService#addRoleCtrl(java.lang.String, java.lang.String)
	 */
	@Override
	public Long addRoleCtrl(String RoleID, String ctrlCode,Integer type) throws ServiceBizException {
		if(StringUtils.isNullOrEmpty(RoleID)){
			throw new ServiceBizException("角色id不能为空");
		}
		if(StringUtils.isNullOrEmpty(ctrlCode)){
			//throw new ServiceBizException("控制代码不能为空");
		    return null;
		}
		String[] sourceStrArray = ctrlCode.split(",");
		for(String ctrlCodeTree:sourceStrArray){
		    RoleCtrlPO roleCtrlPO=new RoleCtrlPO();
	        roleCtrlPO.setLong("ROLE_ID", Long.parseLong(RoleID));
	        roleCtrlPO.setString("CTRL_CODE", ctrlCodeTree);
	        roleCtrlPO.setInteger("TYPE", type);
	        roleCtrlPO.saveIt();
		}
		return null;
	}

	/**
	 * 根据角色id删除角色受控权限
	 * @author yll
	 * @date 2016年8月12日
	 * @param RoleID
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.role.RoleCtrlService#deleteMenuByRoleId(java.lang.String)
	 */
	@Override
	public void deleteMenuByRoleId(String RoleID) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("select ROLE_CTRL_ID,DEALER_CODE  from tm_ROLE_CTRL where ROLE_ID=?");
		List<Object> params = new ArrayList<>();
		params.add(Long.parseLong(RoleID));
		List<Map> list=DAOUtil.findAll(sqlSb.toString(),params);
		for(int i=0;i<list.size();i++){
			Object ctrlId=list.get(i).get("ROLE_CTRL_ID");
			RoleCtrlPO aa=RoleCtrlPO.findById(ctrlId);
			aa.delete();
		}

	}

	/**
	 * 加载角色受控权限
	* @author yll
	* @date 2016年8月31日
	* @param roletId
	* @return
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.basedata.role.RoleCtrlService#queryMenuCtrl(java.lang.String)
	 */
	public Map<String,String> queryMenuCtrl(String roletId) throws ServiceBizException{

		String maintain=null;//维修
		String accessories=null;//配件
		String vehicleWarehouse=null;//整车仓库
		String accessoriesWarehouse=null;//配件仓库
		String favorableModels=null;//优惠模式
		StringBuilder sqlSb = new StringBuilder("select ROLE_CTRL_ID,DEALER_CODE,ROLE_ID,CTRL_CODE,TYPE from tm_role_ctrl where 1=1");
		List<Object> params = new ArrayList<>();
		if(!StringUtils.isNullOrEmpty(roletId)){
			String[] idList=roletId.split(",");
			sqlSb.append(" and ROLE_ID in (");
			for(int i=0;i<idList.length-1;i++){
				sqlSb.append("?,");
			}
			sqlSb.append("?");
			sqlSb.append(")");
			for(int i=0;i<idList.length;i++){
				params.add(Long.parseLong(idList[i]));
			}
			//sqlSb.append(" and ROLE_ID in ("+roletId+")");

		}
		List<Map> list=DAOUtil.findAll(sqlSb.toString(),params);
		Map<String,String> basicresult = new HashMap<>();
		for(int i=0;i<list.size();i++){
			String ctrlCode=(String) list.get(i).get("CTRL_CODE");
			Integer type=(Integer) list.get(i).get("TYPE");
			//String tcCode=ctrlCode.substring(0, 4);
			if(type.equals(DictCodeConstants.MAINTAIN)){
				if(maintain==null){
					maintain=ctrlCode;
				}else{
					maintain+=",";
					maintain+=ctrlCode;
				}
			}
			if(type.equals(DictCodeConstants.ACCESSORIES)){
				
				if(accessories==null){
					accessories=ctrlCode;
				}else{
					accessories+=",";
					accessories+=ctrlCode;
				}
			}
			if(type.equals(DictCodeConstants.VEHICLE_WAREHOUSE)){
				if(vehicleWarehouse==null){
					vehicleWarehouse=ctrlCode;
				}else{
					vehicleWarehouse+=",";
					vehicleWarehouse+=ctrlCode;
				}
			}
			if(type.equals(DictCodeConstants.ACCESSORIES_WAREHOUSE)){
				if(accessoriesWarehouse==null){
					accessoriesWarehouse=ctrlCode;
				}else{
					accessoriesWarehouse+=",";
					accessoriesWarehouse+=ctrlCode;
				}
			}
			if(type.equals(DictCodeConstants.FAVORABLE_MODELS)){
				if(favorableModels==null){
					favorableModels=ctrlCode;
				}else{
					favorableModels+=",";
					favorableModels+=ctrlCode;
				}
			}

		}

		basicresult.put("maintain", maintain);
		basicresult.put("accessories", accessories);
		basicresult.put("vehicleWarehouse", vehicleWarehouse);
		basicresult.put("accessoriesWarehouse", accessoriesWarehouse);
		basicresult.put("favorableModels", favorableModels);

		return basicresult;
	}

	

}
