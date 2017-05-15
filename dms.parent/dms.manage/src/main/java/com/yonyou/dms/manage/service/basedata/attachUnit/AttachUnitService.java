
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : AttachUnitService.java
*
* @Author : Administrator
*
* @Date : 2016年12月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月16日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.service.basedata.attachUnit;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.AttachUnitDto;
import com.yonyou.dms.manage.domains.PO.basedata.AttachUnitPo;

/**
 * 车辆信息挂靠单位service
* TODO description
* @author Administrator
* @date 2016年12月16日
*/

public interface AttachUnitService {
    public PageInfoDto queryAttachUnit(Map<String, String> queryParams)throws ServiceBizException;
    public Long addAttachUnit(AttachUnitDto ptdto)throws ServiceBizException;
    public AttachUnitPo getAttachUnitById(Long id)throws ServiceBizException;
    public void modifyAttachUnit(Long id,AttachUnitDto ptdto)throws ServiceBizException;
    @SuppressWarnings("rawtypes")
	public List<Map> findAllAttachUnit()throws ServiceBizException;
    public void deleteAttachUnitById(Long id) throws ServiceBizException;
    
    
}
