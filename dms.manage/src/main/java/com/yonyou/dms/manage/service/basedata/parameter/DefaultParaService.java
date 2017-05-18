
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : DefaultParaService.java
*
* @Author : zhanshiwei
*
* @Date : 2017年1月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月18日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.service.basedata.parameter;

import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.TmDefaultParaListDTO;

/**
 * @author zhanshiwei
 * @date 2017年1月18日
 */

public interface DefaultParaService {

    public Map<String, Map<String, Object>> queryDefaultPara(Map<String, String> queryParam) throws ServiceBizException;

    public void modifyBasicParametersListDTO(TmDefaultParaListDTO basilistdto) throws ServiceBizException;

}
