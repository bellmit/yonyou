
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : RoleMenuActionServiceImpl.java
 *
 * @Author : yll
 *
 * @Date : 2016年8月9日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月9日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.service.basedata.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.PO.basedata.role.RoleMenuActionPO;

/**
 * 角色菜单操作权限service接口实现类
 * @author yll
 * @date 2016年8月9日
 */
@Service
public class RoleMenuActionServiceImpl implements RoleMenuActionService{

	/**
	 * 根据menuId加载角色菜单操作按钮复选框列表
	 * @author yll
	 * @date 2016年8月12日
	 * @param menuId
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.role.RoleMenuActionService#remoteUrl(java.lang.Long)
	 */
	public List<Map> remoteUrl(Long menuId) throws ServiceBizException {
		StringBuilder sql = new StringBuilder("SELECT ACTION_NAME,MENU_CURING_ID FROM tc_menu_action  where 1=1 and ACTION_CTL=10041001");
		List<Object> params = new ArrayList<>();

		if(!StringUtils.isNullOrEmpty(menuId)){
			sql.append(" and MENU_ID = ?");
			params.add(menuId);
		}
		
		List<Map> result = Base.findAll(sql.toString(),params.toArray());
		return result;
	}

	/**
	 * 添加一条操作按钮权限
	 * @author yll
	 * @date 2016年8月31日
	 * @param oneAction
	 * @param roleId
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.role.RoleMenuActionService#addOneAction(java.lang.String, java.lang.String)
	 */
	@Override
	public void addOneAction(String oneAction,String roleId) throws ServiceBizException {

		String[] arry=oneAction.split("-");
		String menuId=arry[0];
		StringBuilder sqlSb2 = new StringBuilder("select ROLE_MENU_ID,DEALER_CODE  from tm_role_menu where 1=1 ");
		List<Object> params2 = new ArrayList<>();
		sqlSb2.append(" and MENU_ID = ?");
		params2.add(Long.parseLong(menuId));
		sqlSb2.append(" and ROLE_ID = ?");
		params2.add(Long.parseLong(roleId));

		List <Map> tmRoleMenu = DAOUtil.findAll(sqlSb2.toString(),params2);
		if(!CommonUtils.isNullOrEmpty(tmRoleMenu)){
		Integer roleMenuId=(Integer) tmRoleMenu.get(0).get("ROLE_MENU_ID");

		String checkids=arry[1];
		String[] checkid=checkids.split(",");
		RoleMenuActionPO.delete("ROLE_MENU_ID=?", roleMenuId);
		for(int i=0;i<checkid.length;i++)
		{
			if(StringUtils.isNullOrEmpty(menuId)){
				throw new ServiceBizException("菜单代码为空");
			}
			if(StringUtils.isNullOrEmpty(checkid[i])){
				throw new ServiceBizException("操作按钮check为空");
			}

			RoleMenuActionPO roleMenuActionPO =new RoleMenuActionPO();
			roleMenuActionPO.setInteger("ROLE_MENU_ID", roleMenuId);
			roleMenuActionPO.setInteger("MENU_CURING_ID",Integer.parseInt(checkid[i].trim()));
			roleMenuActionPO.saveIt();
		}
		}
	}

	/**
	 * 加载角色菜单操作权限
	 * @author yll
	 * @date 2016年8月31日
	 * @param menuId
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.role.RoleMenuActionService#queryRoleMenuAction(java.lang.String)
	 */
	@Override
	public Map<String, String> queryRoleMenuAction(String menuId) throws ServiceBizException {
		Integer roleMenuId = null ;
		String check=null;
		Map<String,String> basicresult = new HashMap<>();
		if(StringUtils.isNullOrEmpty(menuId)){
			StringBuilder sqlSb2 = new StringBuilder("select ROLE_MENU_ID,DEALER_CODE  from tm_role_menu where 1=1 ");
			List<Object> params2 = new ArrayList<>();
			sqlSb2.append(" and MENU_ID = ?");
			params2.add(Long.parseLong(menuId));

			List <Map> tmRoleMenu = DAOUtil.findAll(sqlSb2.toString(),params2);
			roleMenuId =(Integer) tmRoleMenu.get(0).get("ROLE_MENU_ID");
		}
		if(StringUtils.isNullOrEmpty(roleMenuId)){
			StringBuilder sqlSb = new StringBuilder("select ACTION_CODE,DEALER_CODE from tm_role_menu_action where 1=1");
			List<Object> params = new ArrayList<>();
			sqlSb.append(" and ROLE_MENU_ID = ?");
			params.add(roleMenuId);
			List<Map> list=DAOUtil.findAll(sqlSb.toString(),params);
			for(int i=0;i<list.size();i++){
				String actionCode=(String) list.get(i).get("ACTION_CODE");
				if(check==null){
					check=actionCode;
				}else{
					check+=",";
					check+=actionCode;
				}
			}
		}
		basicresult.put("treeMenuAction", check);
		return basicresult;
	}

	/**
	 * 查找角色菜单操作权限
	 * @author yll
	 * @date 2016年8月31日
	 * @param roleId
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.role.RoleMenuActionService#findMenuAction(java.lang.String)
	 */
	@Override
	public String[] findMenuAction(String roleId) throws ServiceBizException {
		StringBuilder sb=new StringBuilder("SELECT trm.MENU_ID as MENU_ID, group_concat(tma.MENU_CURING_ID) as ACTION_CODE,trm.DEALER_CODE as DEALER_CODE FROM  tm_role_menu_action trma,	tm_role_menu trm,tm_role tr,tc_menu_action tma  WHERE	tr.ROLE_ID = trm.ROLE_ID AND tma.MENU_CURING_ID=trma.MENU_CURING_ID  AND trm.ROLE_MENU_ID = trma.ROLE_MENU_ID and tr.ROLE_ID in ("+roleId+")");
		List<Object> param=new ArrayList<>();

		sb.append(" GROUP BY trm.MENU_ID,trm.DEALER_CODE");
		List<Map> listmap=DAOUtil.findAll(sb.toString(), param);
		String[] treeMenuAction=new String[listmap.size()];
		for(int i=0;i<listmap.size();i++){
			String oneAction=null;
			Map map=listmap.get(i);
			String actionCode=(String) map.get("ACTION_CODE");//去重
			String aaArray[] = actionCode.split(",");
			HashSet<String> hs = new HashSet<>();
			for(String s : aaArray){
				hs.add(s);
			}
			Iterator<String> it = hs.iterator();
			if(it.hasNext()){
				actionCode = hs.toString().replace("[", "").replace("]", "");//去除相同项的字符串
			}
			oneAction=map.get("MENU_ID")+"-"+actionCode;
			treeMenuAction[i]=oneAction;
		}
		return treeMenuAction;
	}



}
