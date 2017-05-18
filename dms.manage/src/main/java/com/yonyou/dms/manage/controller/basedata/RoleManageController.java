
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : RoleManageController.java
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

package com.yonyou.dms.manage.controller.basedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.CommonTreeDto;
import com.yonyou.dms.manage.domains.DTO.basedata.role.RoleDto;
import com.yonyou.dms.manage.domains.DTO.basedata.role.RolePDto;
import com.yonyou.dms.manage.domains.PO.basedata.role.RolePO;
import com.yonyou.dms.manage.service.basedata.role.RoleCtrlService;
import com.yonyou.dms.manage.service.basedata.role.RoleMenuActionService;
import com.yonyou.dms.manage.service.basedata.role.RoleMenuRangeService;
import com.yonyou.dms.manage.service.basedata.role.RoleMenuService;
import com.yonyou.dms.manage.service.basedata.role.RoleService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 角色权限管理controller
 * 
 * @author yll
 * @date 2016年7月28日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/roles")
public class RoleManageController {

    @Autowired
    private RoleService           roleService;
    @Autowired
    private RoleMenuService       RoleMenuService;
    @Autowired
    private RoleCtrlService       roleCtrlService;
    @Autowired
    private RoleMenuActionService roleMenuActionService;
    @Autowired
    private RoleMenuRangeService  roleMenuRangeService;

    /**
     * 加载角色的方法
     * 
     * @author yll
     * @date 2016年8月7日
     * @param queryParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryOperateLog(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = roleService.queryRole(queryParam);
        return pageInfoDto;
    }

    /**
     * 根据id获取角色信息
     * 
     * @author yll
     * @date 2016年8月7日
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getRoleById(@PathVariable(value = "id") Long id) {
        RolePO role = roleService.getRoleById(id);
        return role.toMap();
    }

    /**
     * 添加角色信息
     * 
     * @author yll
     * @date 2016年8月7日
     * @param roleDto
     * @param uriCB
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RoleDto> addRole(@RequestBody RoleDto roleDto, UriComponentsBuilder uriCB) {
        Long id = roleService.addRole(roleDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/roles/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<RoleDto>(roleDto, headers, HttpStatus.CREATED);

    }

    /**
     * 删除角色及对应权限
     * 
     * @author yll
     * @date 2016年10月19日
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBrand(@PathVariable("id") Long id) {
        RoleMenuService.deleteMenuByRoleId(id + "");
        roleCtrlService.deleteMenuByRoleId(id + "");
        roleService.deleteRoleById(id);
    }

    /**
     * 角色对应的权限的新增及修改
     * 
     * @author yll
     * @date 2016年8月12日
     * @param RolePDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.PUT)
    public ResponseEntity<RolePDto> ModifyOrg(@RequestBody RolePDto RolePDto, UriComponentsBuilder uriCB) {
        String roleId = RolePDto.getNowRole();
        String nowTree = RolePDto.getNowTree();
        String treeMenuAction = RolePDto.getTreeMenuAction();
        String treeMenuRange = RolePDto.getTreeMenuRange();/////

        if (StringUtils.isNullOrEmpty(nowTree)) {// 菜单权限不能为空
            throw new ServiceBizException("菜单权限不能为空");
        }
        // 如果改变checkbox才做操作
        if (!StringUtils.isNullOrEmpty(nowTree)) {
            RoleMenuService.deleteMenuByRoleId(roleId);
        }
        String[] sourceStrArray = nowTree.split(",");
        for (int i = 0; i < sourceStrArray.length; i++) {
            RoleMenuService.addRoleMenu(roleId, sourceStrArray[i]);
        }

        roleCtrlService.deleteMenuByRoleId(roleId);

        roleCtrlService.addRoleCtrl(roleId, RolePDto.getTreeCtrlCode(), DictCodeConstants.MAINTAIN);

        // treeMenuAction
        if (treeMenuAction != null) {
            String[] oneAction = treeMenuAction.split(";");
            if (oneAction.length > 0) {
                for (int i = 0; i < oneAction.length; i++) {
                    if (!StringUtils.isNullOrEmpty(oneAction[i])) {
                        roleMenuActionService.addOneAction(oneAction[i], roleId);
                    }
                }

            }
        }
        // treeMenuRange
        if (treeMenuRange != null) {
            String[] oneRange = treeMenuRange.split(";");
            if (oneRange.length > 0) {
                for (int i = 0; i < oneRange.length; i++) {
                    if (!StringUtils.isNullOrEmpty(oneRange[i])) {
                        roleMenuRangeService.addOneRange(oneRange[i], roleId);

                    }
                }

            }
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        return new ResponseEntity<RolePDto>(headers, HttpStatus.CREATED);
    }

    /**
     * 加载角色权限信息
     * 
     * @author yll
     * @date 2016年8月12日
     * @param data
     * @return
     */
    @RequestMapping(value = "/roleData/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> roleData(@PathVariable(value = "id") String id) {
        List<CommonTreeDto> roleMenu = roleService.queryGFkMenu(id);
        List<CommonTreeDto> RoleCtrlMenu = roleService.authOptionDate(id);
        Map<String, Object> roleData = new HashMap<String, Object>();
        roleData.put("treejson", roleMenu);
        roleData.put("treeRoleCtrl", RoleCtrlMenu);
        roleData.put("treeMenuAction", roleMenuActionService.findMenuAction(id));
        roleData.put("treeMenuRange", roleMenuRangeService.findMenuRange(id));
        return roleData;
    }

    /**
     * 系统选项权限
     * 
     * @author zhanshiwei
     * @date 2016年12月22日
     */

    @RequestMapping(value = "/authOptionDate/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> authOptionDate(@PathVariable(value = "id") String id) {
        Map<String, Object> autData = new HashMap<String, Object>();
        List<CommonTreeDto> authResult = roleService.authOptionDate(id);
        autData.put("authOptionDate", authResult);
        return autData;
    }

    /**
     * 查询角色菜单操作按钮 复选框组
     * 
     * @author yll
     * @date 2016年8月12日
     * @param menuId
     * @return
     */
    @RequestMapping(value = "/roleMenuAction/{menuId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> actionRemoteUrl(@PathVariable(value = "menuId") Long menuId) {
        return roleMenuActionService.remoteUrl(menuId);
    }

    /**
     * 查询角色菜单操作权限 复选框组
     * 
     * @author yll
     * @date 2016年8月12日
     * @param menuId
     * @return
     */
    @RequestMapping(value = "/roleMenuRange/{menuId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> rangeRemoteUrl2(@PathVariable(value = "menuId") Long menuId) {
        return roleMenuRangeService.remoteUrl(menuId);
    }
}
