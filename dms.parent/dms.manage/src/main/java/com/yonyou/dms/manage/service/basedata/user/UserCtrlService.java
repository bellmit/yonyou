
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : UserCtrlService.java
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

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.CommonTreeDto;

/**
 * 用户受控权限service
 * @author yll
 * @date 2016年8月19日
 */

public interface UserCtrlService {

	public Long addUserCtrl(String UserID ,String ctrlCode) throws ServiceBizException;

	public void deleteMenuByUserId(String UserID) throws ServiceBizException;
    public List<CommonTreeDto> UserCtrlOption(String id) throws ServiceBizException;

	public Map<String, String> queryMenuCtrl(String roletId) throws ServiceBizException;
	public Map GetBusinessPurview(String ctrlCode)  throws ServiceBizException;
}
