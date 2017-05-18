
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : RoleMenuRangeServiceImpl.java
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.PO.basedata.role.RoleMenuRangePO;

/**
 * 角色数据范围实现类
 * 
 * @author yll
 * @date 2016年8月9日
 */
@Service
public class RoleMenuRangeServiceImpl implements RoleMenuRangeService {

    /**
     * 根绝menuId加载角色数据范围复选框组
     * 
     * @author yll
     * @date 2016年8月12日
     * @param menuId
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.role.RoleMenuRangeService#remoteUrl(java.lang.Long)
     */
    public List<Map> remoteUrl(Long menuId) throws ServiceBizException {
        StringBuilder sql = new StringBuilder("select tmr.MENU_RANGE_ID, tc.CODE_CN_DESC FROM  tc_menu_range tmr LEFT JOIN tc_code tc  ON  tc.CODE_ID=tmr.RANGE_CODE where 1=1 ");
        List<Object> params = new ArrayList<Object>();

        if (!StringUtils.isNullOrEmpty(menuId)) {
            sql.append(" and tmr.MENU_ID = ?");
            params.add(menuId);
        }
        sql.append(" order by tmr.RANGE_CODE asc");
        List<Map> map = Base.findAll(sql.toString(), params.toArray());
        return map;
    }

    @Override
    public void addOneRange(String oneRange, String roleId) throws ServiceBizException {

        String[] arry = oneRange.split("-");
        String menuId = arry[0];
        StringBuilder sqlSb2 = new StringBuilder("select ROLE_MENU_ID,DEALER_CODE  from tm_role_menu where 1=1 ");
        List<Object> params2 = new ArrayList<Object>();
        sqlSb2.append(" and MENU_ID = ?");
        params2.add(Long.parseLong(menuId));
        sqlSb2.append(" and ROLE_ID = ?");
        params2.add(Long.parseLong(roleId));

        List<Map> tmRoleMenu = DAOUtil.findAll(sqlSb2.toString(), params2);
        Integer roleMenuId = (Integer) tmRoleMenu.get(0).get("ROLE_MENU_ID");

        String checkids = arry[1];
        String[] checkid = checkids.split(",");
        RoleMenuRangePO.delete("ROLE_MENU_ID=?", roleMenuId);
        for (int i = 0; i < checkid.length; i++) {
            if (StringUtils.isNullOrEmpty(menuId)) {
                throw new ServiceBizException("菜单代码为空");
            }
            if (StringUtils.isNullOrEmpty(checkid[i])) {
                throw new ServiceBizException("操作按钮check为空");
            }

            RoleMenuRangePO RoleMenuRangePO = new RoleMenuRangePO();
            RoleMenuRangePO.setInteger("ROLE_MENU_ID", roleMenuId);
            RoleMenuRangePO.setInteger("MENU_RANGE_ID", Integer.parseInt(checkid[i].trim()));
            RoleMenuRangePO.saveIt();
        }
    }

    @Override
    public String[] findMenuRange(String roleId) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT trm.MENU_ID as MENU_ID, group_concat(tmr.MENU_RANGE_ID) as RANGE_CODE,trm.DEALER_CODE as DEALER_CODE FROM  tm_role_menu_range trma,tm_role_menu trm,tm_role tr , tc_menu_range tmr WHERE 1=1 AND tmr.MENU_RANGE_ID=trma.MENU_RANGE_ID and trm.DEALER_CODE=tr.DEALER_CODE and tr.ROLE_ID = trm.ROLE_ID AND trm.ROLE_MENU_ID = trma.ROLE_MENU_ID and tr.ROLE_ID in ("
                                             + roleId + ")");
        List<Object> param = new ArrayList<Object>();
        sb.append(" GROUP BY trm.MENU_ID,trm.DEALER_CODE");
        List<Map> listmap = DAOUtil.findAll(sb.toString(), param);
        String[] treeMenuRange = new String[listmap.size()];
        for (int i = 0; i < listmap.size(); i++) {
            String oneRange = null;
            Map map = listmap.get(i);
            String rangeCode = (String) map.get("RANGE_CODE");// 去重
            String aaArray[] = rangeCode.split(",");
            HashSet<String> hs = new HashSet<String>();
            for (String s : aaArray) {
                hs.add(s);
            }
            Iterator<String> it = hs.iterator();
            if (it.hasNext()) {
                rangeCode = hs.toString().replace("[", "").replace("]", "");// 去除相同项的字符串
            }

            oneRange = map.get("MENU_ID") + "-" + rangeCode;
            treeMenuRange[i] = oneRange;

        }
        return treeMenuRange;
    }
}
