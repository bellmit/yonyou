
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : RoleServiceImpl.java
 *
 * @Author : yll
 *
 * @Date : 2016年7月28日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月28日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.service.basedata.role;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DefinedRowProcessor;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.controller.basedata.RoleManageController;
import com.yonyou.dms.manage.domains.DTO.basedata.CommonTreeDto;
import com.yonyou.dms.manage.domains.DTO.basedata.CommonTreeStateDto;
import com.yonyou.dms.manage.domains.DTO.basedata.role.RoleDto;
import com.yonyou.dms.manage.domains.PO.basedata.role.RolePO;

/**
 * 角色信息实现类
 * 
 * @author yll
 * @date 2016年7月28日
 */
@Service
public class RoleServiceImpl implements RoleService {

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(RoleManageController.class);
    String                      str    = "";

    /**
     * 角色查询方法
     * 
     * @author yll
     * @date 2016年8月7日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.role.RoleService#queryRole(java.util.Map)
     */
    @Override
    public PageInfoDto queryRole(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = getQuerySql(queryParam, params);
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql, params);
        return pageInfoDto;
    }

    /**
     * 根据id获取角色信息
     * 
     * @author yll
     * @date 2016年8月7日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.role.RoleService#getRoleById(java.lang.Long)
     */
    @Override
    public RolePO getRoleById(Long id) throws ServiceBizException {

        return RolePO.findById(id);
    }

    /**
     * 角色添加方法
     * 
     * @author yll
     * @date 2016年8月7日
     * @param roleDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.role.RoleService#addRole(com.yonyou.dms.manage.domains.DTO.basedata.role.RoleDto)
     */
    @Override
    public Long addRole(RoleDto roleDto) throws ServiceBizException {
        String roleCode = roleDto.getRoleCode();
        String roleName = roleDto.getRoleName();
        if (StringUtils.isNullOrEmpty(roleDto.getRoleCode())) {
            throw new ServiceBizException("角色代码");
        }
        if (StringUtils.isNullOrEmpty(roleDto.getRoleName())) {
            throw new ServiceBizException("角色名称");
        }
        if (searchBrandCode(roleCode, roleName)) {
            RolePO rolePO = new RolePO();
            setRolePO(rolePO, roleDto);
            rolePO.saveIt();
            return rolePO.getLongId();
        } else {
            throw new ServiceBizException("角色代码或名称不能重复");
        }

    }

    /**
     * 取得sql语句
     * 
     * @author yll
     * @date 2016年8月12日
     * @param queryParam
     * @param params
     * @return
     */
    private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
        StringBuilder sqlSb = new StringBuilder("select ROLE_ID,DEALER_CODE,ROLE_CODE,ROLE_NAME,ROLE_DESC from TM_ROLE where 1=1 ");

        if (!StringUtils.isNullOrEmpty(queryParam.get("roleCode"))) {
            sqlSb.append(" and ROLE_CODE like ?");
            params.add("%" + queryParam.get("roleCode") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("roleName"))) {
            sqlSb.append(" and ROLE_NAME like ?");
            params.add("%" + queryParam.get("roleName") + "%");
        }
        sqlSb.append(" order by ROLE_CODE asc");
        return sqlSb.toString();
    }

    /**
     * 设置checkbox选中状态的方法
     * 
     * @author yll
     * @date 2016年8月7日
     * @param role
     * @param roleDto
     */
    public void setRolePO(RolePO role, RoleDto roleDto) {
        role.setString("ROLE_CODE", roleDto.getRoleCode());
        role.setString("ROLE_NAME", roleDto.getRoleName());
        role.setString("ROLE_DESC", roleDto.getRoleDesc());
    }

    /**
     * 根据parentid菜单树节点查询
     * 
     * @author yll
     * @date 2016年8月7日
     * @param parentId
     * @return
     */
    public List<Map> queryByParentId(Integer parentId) {
        StringBuilder sqlSb = new StringBuilder("select MENU_ID,MENU_NAME,MENU_URL,MENU_ICON,PARENT_ID,MENU_TYPE from tc_menu where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(Long.toString(parentId))) {
            sqlSb.append(" and PARENT_ID = ?");
            params.add(parentId);
        }

        List<Map> list = Base.findAll(sqlSb.toString(), params.toArray());

        return list;

    }

    /**
     * 菜单树节点查询
     * 
     * @author yll
     * @date 2016年8月7日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.role.RoleService#queryMenu()
     */
    @Override
    public List<Map> queryMenu() throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select MENU_ID,MENU_NAME,MENU_URL,MENU_ICON,PARENT_ID,MENU_TYPE from tc_menu where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        sqlSb.append(" and menu_status = ? ");
        params.add(DictCodeConstants.STATUS_IS_VALID);
        List<Map> list = Base.findAll(sqlSb.toString(), params.toArray());
        return list;
    }

    /**
     * 删除角色信息及对应的权限
     * 
     * @author yll
     * @date 2016年10月19日
     * @param id
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.role.RoleService#deleteRoleById(java.lang.Long)
     */
    @Override
    public void deleteRoleById(Long id) throws ServiceBizException {
        RolePO role = RolePO.findById(id);
        if(!StringUtils.isNullOrEmpty(role.get("ROLE_CODE"))){
        	 List<Object> params = new ArrayList<Object>();
             params.add(role.get("ROLE_CODE"));
             params.add(FrameworkUtil.getLoginInfo().getDealerCode());
             List<Map> list=DAOUtil.findAll("select *  from  tt_user_role_mapping where role_code=? and dealer_code=?",params);
             if(list!=null&&list.size()>0){
            	 throw new ServiceBizException("该角色权限已关联用户,不能删除！");
             }
        }
       
        
        role.delete();

    }

    /**
     * 判断是否存在已有的角色代码和名称
     * 
     * @author yll
     * @date 2016年10月19日
     * @param repairTypeName
     * @return
     */
    private boolean searchBrandCode(String roleCode, String roleName) {
        StringBuilder sqlSb = new StringBuilder("select ROLE_ID,DEALER_CODE from tm_role where 1=1");
        List<Object> params = new ArrayList<Object>();
        sqlSb.append(" and ROLE_CODE = ?");
        params.add(roleCode);
        sqlSb.append(" or ROLE_NAME = ?");
        params.add(roleName);
        List<Map> map = DAOUtil.findAll(sqlSb.toString(), params);
        if (map.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * @author zhanshiwei
     * @date 2016年12月22日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.role.RoleService#authOptionDate()
     */

    @Override
    public List<CommonTreeDto> authOptionDate(String roleid) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sb = new StringBuffer("select a.OPTION_CODE,a.DEALER_CODE,a.OPTION_NAME,a.TYPE_NAME,b.CTRL_CODE,\n");
        sb.append("CASE a.OPTION_CODE\n");
        sb.append("   when '10000000' THEN '#'\n");
        sb.append("   when '20000000' THEN '#'\n");
        sb.append("   when '30000000' THEN '#'\n");
        sb.append("   when '70000000' THEN '#'\n");
        sb.append("   when '80000000' THEN '#'\n");
        sb.append("   when '90000000' THEN '#' else a.OPTION_TYPE END  as OPTION_TYPE\n");
        sb.append("   from tm_auth_option a\n");
        String[] roleids = roleid.split(",");
        if (roleids.length > 1) {
            sb.append("   left join tm_role c on 1=1 and ROLE_ID in( \n");
            for (int i = 0; i < roleids.length - 1; i++) {
                sb.append("?,");
                params.add(roleids[i]);
            }
            sb.append("?)");
            params.add(roleids[roleids.length - 1]);

        } else {
            sb.append("   left join tm_role c on 1=1 and ROLE_ID =? \n");
            params.add(Long.parseLong(roleid));
        }
        sb.append("   left join TM_ROLE_CTRL b on a.OPTION_CODE=b.CTRL_CODE and b.ROLE_ID=c.ROLE_ID where 1=1\n");
        final List<CommonTreeDto> authOption = new ArrayList<CommonTreeDto>();
        DAOUtil.findAll(sb.toString(), params, new DefinedRowProcessor() {

            @Override
            protected void process(Map<String, Object> row) {
                CommonTreeDto orgTreeOrg = new CommonTreeDto();
                CommonTreeStateDto CommonTreeStateDto = new CommonTreeStateDto();
                String parent = (String) row.get("OPTION_TYPE").toString();
                if (!StringUtils.isEquals("#", parent)) {
                    parent = row.get("OPTION_TYPE") + "0000000";
                }
                orgTreeOrg.setParent(parent);
                orgTreeOrg.setId(row.get("OPTION_CODE").toString());
                orgTreeOrg.setText(row.get("OPTION_NAME").toString());
                if (!StringUtils.isNullOrEmpty(row.get("CTRL_CODE"))) {
                    CommonTreeStateDto.setChecked(true);
                } else {
                    CommonTreeStateDto.setChecked(false);
                }
                orgTreeOrg.setState(CommonTreeStateDto);
                authOption.add(orgTreeOrg);
            }
        });
        return authOption;
    }

    /**
     * 角色菜单
     * 
     * @author zhanshiwei
     * @date 2017年1月10日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.role.RoleService#queryGFkMenu()
     */

    @SuppressWarnings("rawtypes")
    @Override
    public List<CommonTreeDto> queryGFkMenu(String roleId) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sb = new StringBuffer("select tcm.MENU_ID,tcm.MENU_NAME,tcm.MENU_URL,tcm.MENU_ICON,tcm.PARENT_ID,tcm.MENU_TYPE,tcm.MENU_DESC \n");
        sb.append(" ,tru.ROLE_MENU_ID\n");
        sb.append("from tc_menu tcm\n");
        sb.append("left JOIN tm_role_menu tru on tcm.MENU_ID=tru.MENU_ID ");
        String[] roleids = roleId.split(",");
        if (roleids.length > 1) {
            sb.append("  and tru.ROLE_ID in(");
            for (int i = 0; i < roleids.length - 1; i++) {
                sb.append("?,");
                params.add(roleids[i]);
            }
            sb.append("?)\n");
            params.add(roleids[roleids.length - 1]);
        } else {
            sb.append("   and tru.ROLE_ID =? \n");
            if (!StringUtils.isEquals(roleId, "-1")) {
                params.add(Long.parseLong(roleId));
            }else{
                params.add(roleId); 
            }
        }
        sb.append("where 1=1 and tcm.menu_status=").append(DictCodeConstants.STATUS_IS_VALID);
        List<Map> list = Base.findAll(sb.toString(), params.toArray());
        Iterator<Map> it = list.iterator();
        List<CommonTreeDto> orgList = new ArrayList<CommonTreeDto>();
        while (it.hasNext()) {
            CommonTreeDto orgTreeOrg = new CommonTreeDto();
            CommonTreeStateDto CommonTreeStateDto = new CommonTreeStateDto();
            Map menuMap = it.next();
            orgTreeOrg.setId(menuMap.get("MENU_ID").toString());
            String parent = menuMap.get("PARENT_ID").toString();
            if ("0".equals(parent)) {
                parent = "#";
            }
            if (!StringUtils.isNullOrEmpty(menuMap.get("ROLE_MENU_ID"))) {
                CommonTreeStateDto.setChecked(true);
            } else {
                CommonTreeStateDto.setChecked(false);
            }
            orgTreeOrg.setParent(parent);
            orgTreeOrg.setText(menuMap.get("MENU_NAME").toString());
            orgTreeOrg.setState(CommonTreeStateDto);
            orgList.add(orgTreeOrg);
        }
        return orgList;
    }
}
