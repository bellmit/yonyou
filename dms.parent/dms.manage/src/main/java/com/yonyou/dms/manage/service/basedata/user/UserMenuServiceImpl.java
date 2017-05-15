
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : UserMenuServiceImpl.java
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
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.PO.basedata.user.UserMenuActionPO;
import com.yonyou.dms.manage.domains.PO.basedata.user.UserMenuPO;
import com.yonyou.dms.manage.domains.PO.basedata.user.UserMenuRangePO;

/**
* 用户菜单权限实现类
* @author yll
* @date 2016年8月19日
*/
@Service
public class UserMenuServiceImpl implements UserMenuService{

	/**
	 * 添加用户菜单信息
	* @author yll
	* @date 2016年8月31日
	* @param UserID
	* @param MenuID
	* @return
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.basedata.user.UserMenuService#addUserMenu(java.lang.String, java.lang.String)
	 */
	@Override
	public Long addUserMenu(String UserID, String MenuID) throws ServiceBizException {
		if(StringUtils.isNullOrEmpty(UserID)){
	        throw new ServiceBizException("用户id不能为空");
	    }
		if(StringUtils.isNullOrEmpty(MenuID)){
	        throw new ServiceBizException("菜单id不能为空");
	    }
		
		UserMenuPO userMenuPO=new UserMenuPO();
		userMenuPO.setLong("USER_ID",Long.parseLong( UserID));
		userMenuPO.setLong("MENU_ID",Long.parseLong( MenuID));
		userMenuPO.saveIt();
		return  userMenuPO.getLongId();
	}

	/**
	 * 根据用户id删除菜单
	* @author yll
	* @date 2016年8月31日
	* @param id
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.basedata.user.UserMenuService#deleteMenuByUserId(java.lang.String)
	 */
	@Override
	public void deleteMenuByUserId(String id) throws ServiceBizException {
		StringBuilder  sqlSb=new StringBuilder("select USER_MENU_ID,DEALER_CODE from tm_user_menu where USER_ID='");
		sqlSb.append(Long.parseLong(id));
		sqlSb.append("'");
		List<String> params = new ArrayList<>();
		List<Map> list=DAOUtil.findAll(sqlSb.toString(),params);
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(loginInfo)&&!StringUtils.isNullOrEmpty(loginInfo.getDealerCode())){
			 String dealerCode = loginInfo.getDealerCode();
			 for(int i=0;i<list.size();i++){
			     
					Object menuid=list.get(i).get("USER_MENU_ID");
					UserMenuActionPO.delete("USER_MENU_ID=? ", menuid);
					UserMenuRangePO.delete("USER_MENU_ID=? ", menuid);
				}
				UserMenuPO.delete("USER_ID=? and DEALER_CODE=?", id,dealerCode);
		
		}
		
	}

	/**
	 * 判断菜单（*）
	* @author yll
	* @date 2016年8月31日
	* @param UserId
	* @param MenuID
	* @return
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.basedata.user.UserMenuService#findMenu(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean findMenu(String UserId, String MenuID) {
		StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,MENU_ID  from tm_user_menu where USER_ID=");
		sqlSb.append(Long.parseLong(UserId));
		sqlSb.append(" and MENU_ID=");
		sqlSb.append(Long.parseLong(MenuID));
		
		List<String> params = new ArrayList<>();
		List<Map> list=DAOUtil.findAll(sqlSb.toString(),params);
		if(list.size()>0){
		return true;
		}
		return false;
	}

	/**
	 * 判断是否为父节点
	* @author yll
	* @date 2016年8月31日
	* @param MenuID
	* @return
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.basedata.user.UserMenuService#parentNode(java.lang.String)
	 */
	@Override
	public boolean parentNode(String MenuID) {
		StringBuilder sqlSb = new StringBuilder("select * from tc_menu where PARENT_ID=");
		sqlSb.append(MenuID);
		List<String> params = new ArrayList<>();
		List<Map> list=Base.findAll(sqlSb.toString(),params.toArray());
		if(list.size()>0){
		return true;
		}
		return false;
	}

}
