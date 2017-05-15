
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartParaService.java
*
* @Author : zhanshiwei
*
* @Date : 2017年4月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月13日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.ListTtAdPartParaDTO;

/**
* 配件订货参数
* @author zhanshiwei
* @date 2017年4月13日
*/

public interface PartParaService {
    public PageInfoDto queryParPara(Map<String, String> queryParams) throws ServiceBizException;
    public void addTtAdPartPara(ListTtAdPartParaDTO partPdto)throws ServiceBizException;
}
