
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : SystemUser.java
*
* @Author : yll
*
* @Date : 2016年10月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月8日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.web.service.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 
* @author yll
* @date 2016年10月8日
*/

public interface LoginUserService {
	

    public Map logCheck(String dealerCode,String userCode,String password)throws ServiceBizException;

	public Map oemLogCheck(String dealerCode, String userName, String password) throws ServiceBizException;

	public List<Map> queryPose(Long userId) throws ServiceBizException;

	public List<Map> selectPose(Long userId, String id)  throws ServiceBizException;

	public String getPoseSeriesIDs(Long poseId) throws ServiceBizException;

	public void getUserDearId(Object orgID, Integer poseBusType, HashMap<String, String> hm) throws ServiceBizException;

	public String getDealerSeriesIDs(Long dealerId) throws ServiceBizException;

	public String getOemUserArea(Long poseId) throws ServiceBizException;

	public void getOemActionUrl() throws ServiceBizException;

	public String getXjbm(Long orgID, Long companyId) throws ServiceBizException;
	
	public String getEntityCode(String dealerCode);
}
