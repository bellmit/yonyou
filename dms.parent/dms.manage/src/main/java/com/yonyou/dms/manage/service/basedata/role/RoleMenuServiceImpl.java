
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : RoleMenuServiceImpl.java
*
* @Author : yll
*
* @Date : 2016年8月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月7日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.service.basedata.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.PO.basedata.role.RoleMenuActionPO;
import com.yonyou.dms.manage.domains.PO.basedata.role.RoleMenuPO;
import com.yonyou.dms.manage.domains.PO.basedata.role.RoleMenuRangePO;
import com.yonyou.dms.manage.domains.PO.basedata.role.RolePO;

/**
* 角色菜单接口实现类
* @author yll
* @date 2016年8月7日
*/
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

	/**
	 * 增加角色菜单权限
	* @author yll
	* @date 2016年8月7日
	* @param RoleID
	* @param MenuID
	* @return
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.basedata.role.RoleMenuService#addRoleMenu(java.lang.String, java.lang.String)
	 */
	@Override
	public Long addRoleMenu(String RoleID, String MenuID) throws ServiceBizException {
		if(StringUtils.isNullOrEmpty(RoleID)){
	        throw new ServiceBizException("角色id不能为空");
	    }
		if(StringUtils.isNullOrEmpty(MenuID)){
	        throw new ServiceBizException("菜单id不能为空");
	    }
		
		RoleMenuPO roleMenuPO=new RoleMenuPO();
		roleMenuPO.setLong("ROLE_ID", Long.parseLong(RoleID));
		roleMenuPO.setLong("MENU_ID", Long.parseLong(MenuID));
	    roleMenuPO.setString("ROLE_CODE",RolePO.findById(RoleID).get("ROLE_CODE"));

		roleMenuPO.saveIt();
		return  roleMenuPO.getLongId();
	}

	/**
	 * 根据id删除所属角色菜单权限
	* @author yll
	* @date 2016年8月7日
	* @param id
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.basedata.role.RoleMenuService#deleteMenuByRoleId(java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void deleteMenuByRoleId(String id) throws ServiceBizException {
		StringBuilder  sqlSb=new StringBuilder("select ROLE_MENU_ID,DEALER_CODE from tm_role_menu where ROLE_ID=?");
		List<Object> params = new ArrayList<Object>();
		params.add(Long.parseLong(id));
		List<Map> list=DAOUtil.findAll(sqlSb.toString(),params);
		
		if(!StringUtils.isNullOrEmpty(FrameworkUtil.getLoginInfo().getDealerCode())){
		for(int i=0;i<list.size();i++){
			Object menuid=list.get(i).get("ROLE_MENU_ID");
			RoleMenuActionPO.delete("ROLE_MENU_ID=? ",menuid);
			RoleMenuRangePO.delete("ROLE_MENU_ID=? ", menuid);
		}
		   RoleMenuPO.delete("ROLE_ID=? and DEALER_CODE=?", id,FrameworkUtil.getLoginInfo().getDealerCode());
		}
		
	}
	/**
	 * 判断checkbox是否打勾
	* @author yll
	* @date 2016年8月7日
	* @param roleId
	* @param MenuID
	* @return
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.basedata.role.RoleMenuService#findMenu(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	public boolean findMenu(String roleId,String MenuID){
		StringBuilder sqlSb = new StringBuilder("select *  from tm_role_menu where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(roleId)){
			//sqlSb.append(" and ROLE_ID in ("+roleId+")");
			
				String[] idList=roleId.split(",");
				sqlSb.append(" and ROLE_ID in (");
				for(int i=0;i<idList.length-1;i++){
					sqlSb.append("?,");
				}
				sqlSb.append("?");
				sqlSb.append(")");
				for(int i=0;i<idList.length;i++){
					params.add(Long.parseLong(idList[i]));
				}
					
		}
		if(!StringUtils.isNullOrEmpty(MenuID)){
		//	sqlSb.append(" and MENU_ID in ("+MenuID+")");
			String[] idList=MenuID.split(",");
			sqlSb.append(" and MENU_ID in (");
			for(int i=0;i<idList.length-1;i++){
				sqlSb.append("?,");
			}
			sqlSb.append("?");
			sqlSb.append(")");
			for(int i=0;i<idList.length;i++){
				params.add(Long.parseLong(idList[i]));
			}
			
		}
		
		List<Map> list=DAOUtil.findAll(sqlSb.toString(),params);
		if(list.size()>0){
		return true;
		}
		return false;
	}
	
	/**
	 * 判断是否是父节点
	* @author yll
	* @date 2016年8月12日
	* @param MenuID
	* @return
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.basedata.role.RoleMenuService#parentNode(java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	public boolean parentNode(String MenuID){
		StringBuilder sqlSb = new StringBuilder("select *  from tc_menu where PARENT_ID=");
		sqlSb.append(MenuID);
		
		List<String> params = new ArrayList<String>();
		List<Map> list=Base.findAll(sqlSb.toString(),params.toArray());
		if(list.size()>0){
		return true;
		}
		return false;
	}

}
