
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : PositionService.java
*
* @Author : ZhengHe
*
* @Date : 2016年7月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月15日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.service.basedata.position;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.PositionDTO;
import com.yonyou.dms.manage.domains.PO.basedata.PositionPo;

/**
* 职位service
* @author ZhengHe
* @date 2016年7月15日
*/

public interface PositionService {

    public PageInfoDto queryPosition(Map<String, String> queryParams)throws ServiceBizException;
    public Long addPosition(PositionDTO ptdto)throws ServiceBizException;
    public PositionPo getPositionById(Long id)throws ServiceBizException;
    public void modifyPosition(Long id,PositionDTO ptdto)throws ServiceBizException;
    @SuppressWarnings("rawtypes")
	public List<Map> findAllPosition()throws ServiceBizException;
    public void deletePositionById(Long id) throws ServiceBizException;
}
