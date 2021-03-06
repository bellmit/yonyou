
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CarownerService.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月8日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.stockmanage.CarownerDTO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 车主信息维护
 * 
 * @author zhanshiwei
 * @date 2016年8月8日
 */

public interface CarownerService {

	public PageInfoDto queryCarownerInfo(Map<String, String> queryParam) throws ServiceBizException;

	public long addcarownerInfo(CarownerDTO ownDto, String ownerNo) throws ServiceBizException;

	public CarownerPO customerResoInfoByid(long id) throws ServiceBizException;

	public void modifycarownerInfo(long id, CarownerDTO ownDto) throws ServiceBizException;

	public List<Map> queryCarownerInfoforExport(Map<String, String> queryParam) throws ServiceBizException;

	public PageInfoDto queryCarowner(Map<String, String> queryParam) throws ServiceBizException;

	public PageInfoDto queryCarownerSelectInfo(Map<String, String> queryParam) throws ServiceBizException;

	public void checkPhone(Object id, String phone, String mobile) throws ServiceBizException;

	public PageInfoDto queryCarownerFamily(Map<String, String> queryParam,String dealerCode) throws ServiceBizException;

}
