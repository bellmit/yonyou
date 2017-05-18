
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : BasicComService.java
*
* @Author : zhanshiwei
*
* @Date : 2016年7月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月19日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.service.basedata.parameter;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.BasicParametersListDTO;

/**
 * 基础参数设置
 * 
 * @author zhanshiwei
 * @date 2016年7月19日
 */

public interface BasicComService {

    public Map<String, Map<String, Object>> queryBasicParameters(Map<String, String> queryParam) throws ServiceBizException;

    public void modifyBasicParametersListDTO(BasicParametersListDTO basilistdto) throws ServiceBizException;
    
    public List<Map> queryBank(Map<String, String> queryParam) throws ServiceBizException;

}
