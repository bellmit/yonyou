
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : SystemParamService.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月12日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.service.baseData;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 
* @author zhanshiwei
* @date 2016年9月12日
*/

public interface SystemParamService {
    public List<BasicParametersDTO> queryBasicParameterByType( Long type) throws ServiceBizException;
    public BasicParametersDTO queryBasicParameterByTypeandCode(Long type, String code) throws ServiceBizException;
    public Map<String, Map<String, Object>> queryBasicParameters();

}
