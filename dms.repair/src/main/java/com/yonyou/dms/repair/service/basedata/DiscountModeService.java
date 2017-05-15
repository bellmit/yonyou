
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : DiscountModeService.java
*
 * @Author : zhengcong
 *
 * @Date : 2017年3月23日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月23日   zhengcong   1.0
 *
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.DiscountModeDTO;

/**
 * @author zhengcong
 * @date 2017年3月23日
*/

public interface DiscountModeService {
    public PageInfoDto QueryDiscountMode(Map<String, String> queryParam) throws ServiceBizException;
    Map<String, String> findByCode(String DISCOUNT_MODE_CODE) throws ServiceBizException;//根据CODE查询详细信息
    public void modifyByCode(String DISCOUNT_MODE_CODE,DiscountModeDTO cudto) throws ServiceBizException;///根据CODE修改
    void addDiscountMode(DiscountModeDTO cudto)throws ServiceBizException;///新增
    List<Map> queryDiscountMode();

}
