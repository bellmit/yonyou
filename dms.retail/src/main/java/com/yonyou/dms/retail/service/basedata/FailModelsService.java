
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.retail
 *
 * @File name : FailModelsService.java
 *
 * @Author : yll
 *
 * @Date : 2016年7月5日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月5日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */
package com.yonyou.dms.retail.service.basedata;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.basedata.FailModelsDto;
import com.yonyou.dms.retail.domains.PO.basedata.FailModels;

/**
 * 
 * 战败车型信息service 接口
 * @author yll
 * @date 2016年6月30日
 */
public interface FailModelsService {
	public PageInfoDto queryFailModel(Map<String, String> queryParam);

	public FailModels getFailModelById(Long id) throws ServiceBizException;

	public Long addFailModel(FailModelsDto user) throws ServiceBizException;

	public void modifyFailModel(Long id,FailModelsDto user) throws ServiceBizException;

	public void deleteFailModelById(Long id) throws ServiceBizException;


}
