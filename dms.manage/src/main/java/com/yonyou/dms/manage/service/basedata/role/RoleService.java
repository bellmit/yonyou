
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : RoleService.java
*
* @Author : Administrator
*
* @Date : 2016年7月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月28日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.service.basedata.role;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.CommonTreeDto;
import com.yonyou.dms.manage.domains.DTO.basedata.role.RoleDto;
import com.yonyou.dms.manage.domains.PO.basedata.role.RolePO;

/**
 * 角色信息service接口
 * 
 * @author yll
 * @date 2016年7月28日
 */

public interface RoleService {

    public PageInfoDto queryRole(Map<String, String> queryParam) throws ServiceBizException;

    public RolePO getRoleById(Long id) throws ServiceBizException;

    public Long addRole(RoleDto roleDto) throws ServiceBizException;

    public List<Map> queryMenu() throws ServiceBizException;

    public void deleteRoleById(Long id) throws ServiceBizException;

    public List<CommonTreeDto> authOptionDate(String roleid) throws ServiceBizException;

    public List<CommonTreeDto> queryGFkMenu(String roleId) throws ServiceBizException;
}
