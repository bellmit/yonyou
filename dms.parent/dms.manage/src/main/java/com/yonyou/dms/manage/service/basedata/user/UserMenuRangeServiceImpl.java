
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : UserMenuRangeServiceImpl.java
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.PO.basedata.user.UserMenuRangePO;

/**
 * 用户菜单操作范围实现类
 * @author yll
 * @date 2016年8月19日
 */
@Service
public class UserMenuRangeServiceImpl implements UserMenuRangeService{

	/**
	 * 加载菜单操作范围下拉框列表
	 * @author yll
	 * @date 2016年8月31日
	 * @param menuId
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.user.UserMenuRangeService#remoteUrl(java.lang.Long)
	 */
	@Override
	public List<Map> remoteUrl(Long menuId) throws ServiceBizException {
		StringBuilder sql = new StringBuilder("SELECT CODE_CN_DESC,CODE_ID FROM tc_code where  CODE_ID in (select RANGE_CODE from tc_menu_range where  ");
		List<Object> params = new ArrayList<>(); 
		if(!StringUtils.isNullOrEmpty(menuId)){
			sql.append(" MENU_ID = ?");
			params.add(menuId);
		}
		sql.append(" )");
		List<Map> result = Base.findAll(sql.toString(),params.toArray());
		return result;
	}
	@Override
	public String[] findMenuRange(String userId) throws ServiceBizException {
		StringBuilder sb=new StringBuilder("SELECT trm.MENU_ID AS MENU_ID, group_concat(tmr.MENU_RANGE_ID) AS RANGE_CODE, trm.DEALER_CODE AS DEALER_CODE FROM tm_user_menu_range trma, tm_user_menu trm, tm_user tr, tc_menu_range tmr WHERE tr.USER_ID = trm.USER_ID AND tmr.MENU_RANGE_ID=trma.MENU_RANGE_ID AND trm.USER_MENU_ID = trma.USER_MENU_ID AND trm.DEALER_CODE = tr.DEALER_CODE AND tr.USER_ID in ("+userId+")");
		List<Object> param=new ArrayList<>();
		sb.append(" GROUP BY trm.MENU_ID,trm.DEALER_CODE");
		List<Map> listmap=DAOUtil.findAll(sb.toString(), param);
		String[] treeMenuRange=new String[listmap.size()];
		for(int i=0;i<listmap.size();i++){
			String oneRange=null;
			Map map=listmap.get(i);
			String rangeCode=(String) map.get("RANGE_CODE");//去重
			String aaArray[] = rangeCode.split(",");
			HashSet<String> hs = new HashSet<>();
			for(String s : aaArray){
				hs.add(s);
			}
			Iterator<String> it = hs.iterator();
			if(it.hasNext()){
				rangeCode = hs.toString().replace("[", "").replace("]", "");//去除相同项的字符串
			}
			oneRange=map.get("MENU_ID")+"-"+rangeCode;
			treeMenuRange[i]=oneRange;
		}
		return treeMenuRange;
	}

	@Override
	public void addOneRange(String oneRange,String userId) throws ServiceBizException {

		String[] arry=oneRange.split("-");
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
		String[] checkid=checkids.split(",");
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(loginInfo)&&!StringUtils.isNullOrEmpty(loginInfo.getDealerCode())){
			String dealerCode = loginInfo.getDealerCode();
			UserMenuRangePO.delete("USER_MENU_ID=? ", userMenuId);
		}
		for(int i=0;i<checkid.length;i++)
		{
			if(StringUtils.isNullOrEmpty(menuId)){
				throw new ServiceBizException("菜单代码为空");
			}
			if(StringUtils.isNullOrEmpty(checkid[i])){
				throw new ServiceBizException("操作按钮check为空");
			}

			UserMenuRangePO UserMenuRangePO =new UserMenuRangePO();
			UserMenuRangePO.setInteger("USER_MENU_ID", userMenuId);
			UserMenuRangePO.setInteger("MENU_RANGE_ID", Integer.parseInt(checkid[i].trim()));
			UserMenuRangePO.saveIt();
		}
	}
	}
}
