/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
 * @Author : zhengcong
 *
 * @Date : 2017年5月9日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年5月9日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.repair.service.maintenancePackage;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 *保养套餐管理service 
 * @author zhengcong
 * @date 2017年5月9日
 */
public interface MaintenancePackageService {
	
	public PageInfoDto query(Map<String, String> queryParam)throws ServiceBizException; //查询
	public PageInfoDto queryLabourDtail(String PACKAGE_CODE)throws ServiceBizException;//查询项目明细
	public PageInfoDto queryPartDtail(String PACKAGE_CODE)throws ServiceBizException;//查询配件明细
}

