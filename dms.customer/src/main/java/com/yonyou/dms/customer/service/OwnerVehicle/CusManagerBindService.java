/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : LabourPriceService1.java
*
 * @Author : zhengcong
 *
 * @Date : 2017年3月27日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月27日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.customer.service.OwnerVehicle;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.ChargeTypeDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
*专属客户经理绑定service 
 * @author zhengcong
 * @date 2017年3月27日
 */
public interface CusManagerBindService {
	
	public PageInfoDto queryAll(Map<String, String> queryParam)throws ServiceBizException;
	void setCusManager(String[] allvin,String manager);
	List<Map> queryToExport(Map<String, String> queryParam) throws ServiceBizException;//使用excel导出
	
//	Map<String, String> findByCode(String MANAGE_SORT_CODE,Object IS_MANAGING) throws ServiceBizException;//根据CODE查询详细信息
//    public void modifyByCode(String MANAGE_SORT_CODE,Object IS_MANAGING,ChargeTypeDTO ctdto) throws ServiceBizException;///根据CODE修改
//    void addFee(ChargeTypeDTO ctdto)throws ServiceBizException;///新增
}

