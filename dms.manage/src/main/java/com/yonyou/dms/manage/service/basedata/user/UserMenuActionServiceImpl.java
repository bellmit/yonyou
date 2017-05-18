
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : UserMenuActionServiceImpl.java
 *
 * @Author : yll
 *
 * @Date : 2016年8月19日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月19日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.service.basedata.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.PO.basedata.user.UserMenuActionPO;

/**
 * 用户菜单操作权限实现类
 * @author yll
 * @date 2016年8月19日
 */
@Service
public class UserMenuActionServiceImpl implements UserMenuActionService{

	@Override
	public List<Map> remoteUrl(Long menuId) throws ServiceBizException {
		StringBuilder sql = new StringBuilder("SELECT ACTION_NAME,MENU_CURING_ID FROM tc_menu_action  where 1=1 and ACTION_CTL=10041001");
		List<Object> params = new ArrayList<>();    
		if(!StringUtils.isNullOrEmpty(menuId)){
			sql.append(" and MENU_ID = ?");
			params.add(menuId);
		}

		 List<Map> result =Base.findAll(sql.toString(),params.toArray());

		return result;
	}

	/**
	 * 添加一个菜单操作信息
	 * @author yll
	 * @date 2016年8月31日
	 * @param oneAction
	 * @param userId
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.user.UserMenuActionService#addOneAction(java.lang.String, java.lang.String)
	 */
	@Override
	public void addOneAction(String oneAction,String userId) throws ServiceBizException {
		String[] arry=oneAction.split("-");
		String menuId=arry[0];
		StringBuilder sqlSb2 = new StringBuilder("select USER_MENU_ID,DEALER_CODE  from tm_user_menu where 1=1 ");
		List<Object> params2 = new ArrayList<>();
		sqlSb2.append(" and MENU_ID = ?");
		params2.add(Long.parseLong(menuId));
		sqlSb2.append(" and USER_ID = ?");
		params2.add(Long.parseLong(userId));
		List <Map> tmUserMenu = DAOUtil.findAll(sqlSb2.toString(),params2);
		if(!CommonUtils.isNullOrEmpty(tmUserMenu)){
		Integer userMenuId=(Integer) tmUserMenu.get(0).get("USER_MENU_ID");

		String checkids=arry[1];
		checkids=checkids.replace(" ","");
		String[] checkid=checkids.split(",");
		UserMenuActionPO.delete("USER_MENU_ID=?", userMenuId);
		for(int i=0;i<checkid.length;i++)
		{
			if(StringUtils.isNullOrEmpty(menuId)){
				throw new ServiceBizException("菜单代码为空");
			}
			if(StringUtils.isNullOrEmpty(checkid[i])){
				throw new ServiceBizException("操作按钮check为空");
			}

			UserMenuActionPO userMenuActionPO =new UserMenuActionPO();
			userMenuActionPO.setInteger("USER_MENU_ID", userMenuId);
			userMenuActionPO.setInteger("MENU_CURING_ID",Integer.parseInt(checkid[i].trim()));
			userMenuActionPO.saveIt();
		}
		}
	}

	/**
	 * 加载用户菜单操作信息
	* @author yll
	* @date 2016年8月31日
	* @param menuId
	* @return
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.basedata.user.UserMenuActionService#queryUserMenuAction(java.lang.String)
	 */
	@Override
	public Map<String, String> queryUserMenuAction(String menuId) throws ServiceBizException {
		Integer userMenuId = null;
		String check=null;
		Map<String,String> basicresult = new HashMap<>();
		if(StringUtils.isNullOrEmpty(menuId)){
			StringBuilder sqlSb2 = new StringBuilder("select USER_MENU_ID,DEALER_CODE  from tm_user_menu where 1=1 ");
			List<Object> params2 = new ArrayList<>();
			sqlSb2.append(" and MENU_ID = ?");
			params2.add(Long.parseLong(menuId));

			List <Map> tmUserMenu = DAOUtil.findAll(sqlSb2.toString(),params2);
			userMenuId =(Integer) tmUserMenu.get(0).get("USER_MENU_ID");
		}
		if(StringUtils.isNullOrEmpty(userMenuId)){
			StringBuilder sqlSb = new StringBuilder("select ACTION_CODE,DEALER_CODE from tm_user_menu_action where 1=1");
			List<Object> params = new ArrayList<>();
			sqlSb.append(" and USER_MENU_ID = ?");
			params.add(userMenuId);
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
	 * 查找用户菜单操作权限
	* @author yll
	* @date 2016年8月31日
	* @param userId
	* @return
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.basedata.user.UserMenuActionService#findMenuAction(java.lang.String)
	 */
	@Override
	public String[] findMenuAction(String userId) throws ServiceBizException {
		StringBuilder sb=new StringBuilder("SELECT trm.MENU_ID as MENU_ID, group_concat(tma.MENU_CURING_ID) as ACTION_CODE,trm.DEALER_CODE as DEALER_CODE FROM  tm_user_menu_action trma,	tm_user_menu trm,	tm_user tr,tc_menu_action tma  WHERE	tr.USER_ID = trm.USER_ID AND tma.MENU_CURING_ID=trma.MENU_CURING_ID AND trm.USER_MENU_ID = trma.USER_MENU_ID and trm.DEALER_CODE=tr.DEALER_CODE   and tr.USER_ID=?");
		List<Object> param=new ArrayList<>();
		param.add(Long.parseLong(userId));
		sb.append(" GROUP BY trm.MENU_ID,trm.DEALER_CODE");
		List<Map> listmap=DAOUtil.findAll(sb.toString(), param);
		String[] treeMenuAction=new String[listmap.size()];
		for(int i=0;i<listmap.size();i++){
			String oneAction=null;
			Map map=listmap.get(i);
			oneAction=map.get("MENU_ID")+"-"+map.get("ACTION_CODE");
			treeMenuAction[i]=oneAction;

		}
		return treeMenuAction;
	}


}
