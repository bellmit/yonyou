
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : UserService.java
*
* @Author : yll
*
* @Date : 2016年8月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月15日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.service.basedata.user;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.CommonTreeDto;
import com.yonyou.dms.manage.domains.DTO.basedata.user.UserCopyDto;
import com.yonyou.dms.manage.domains.DTO.basedata.user.UserDto;
import com.yonyou.dms.manage.domains.DTO.basedata.user.UserRoleDTO;

/**
 * 用户接口
 * 
 * @author yll
 * @date 2016年8月15日
 */

public interface UserService {

    public UserPO getUserById(Long id) throws ServiceBizException;

    public void modifyUser(Long id, UserDto userDto) throws ServiceBizException;

    public UserPO addUser(UserDto userDto) throws ServiceBizException;

    public List<Map> queryMenu() throws ServiceBizException;

    public Long getUserIDByEmployeeId(String id) throws ServiceBizException;

    public String getEmployeeIdByUserCode(String userCode) throws ServiceBizException;

    public String getPasswordByUserCode(String userCode) throws ServiceBizException;

    public Long addUseRole(UserRoleDTO useRoleDto) throws ServiceBizException;

    public List<CommonTreeDto> queryUserMenu(Long userId) throws ServiceBizException;

    public Map<String, Object> findGFkUserCheById(String id) throws ServiceBizException;
    public void usercheckEmpNo(String id,String org,String userName,String userCode,Long userId) throws ServiceBizException;
    public Map<String, Object> copUserPermission(UserCopyDto userCopyDto) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> QueryBigCustomer(Long id) throws Exception;//判断该员工编号是否是大客户
    
    public void deleteUserCtrl(Long id) throws ServiceBizException;
}
