/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
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
 * @Date : 2017年3月24日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月24日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.repair.service.basedata;

import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.ChargeTypeDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
*收费类别service 
 * @author zhengcong
 * @date 2017年3月24日
 */
public interface ChargeTypeService {

	
    public PageInfoDto queryFee(Map<String, String> queryParam)throws ServiceBizException;
    Map<String, String> findByCode(String MANAGE_SORT_CODE,Object IS_MANAGING) throws ServiceBizException;//根据CODE查询详细信息
    public void modifyByCode(String MANAGE_SORT_CODE,Object IS_MANAGING,ChargeTypeDTO ctdto) throws ServiceBizException;///根据CODE修改
    void addFee(ChargeTypeDTO ctdto)throws ServiceBizException;///新增

}
