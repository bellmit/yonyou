
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : DealerBasicinfoService1.java
*
* @Author : ZhengHe
*
* @Date : 2016年7月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月13日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.service.basedata.dealer;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.DealerBasicinfoDTO;

/**
 * 
* 经销商信息Service
* @author ZhengHe
* @date 2016年7月6日
 */
public interface DealerBasicinfoService {
    
    public Map<String,Object> getDealerBasicinfo()throws ServiceBizException;
    public PageInfoDto getDealerBasicinfoMain(Map<String, String> queryParams)throws ServiceBizException;
    public void modifyBasicinfo(DealerBasicinfoDTO dbDto)throws ServiceBizException;
}
