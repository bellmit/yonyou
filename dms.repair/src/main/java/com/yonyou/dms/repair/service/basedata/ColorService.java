
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : ColorService.java
*
* @Author : DuPengXin
*
* @Date : 2016年8月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月11日    DuPengXin   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TmColorPo;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.ColorDTO;

/**
* 车辆颜色接口
* @author Benzc
* @date 2016年12月22日
*/

public interface ColorService {
	
    public PageInfoDto QueryColor(Map<String, String> queryParam) throws ServiceBizException;
    
    public void addColor(ColorDTO colordto) throws ServiceBizException;
    
    public void updateColor(String id,ColorDTO colordto) throws ServiceBizException;

	TmColorPo findById(String id) throws ServiceBizException;
	
	@SuppressWarnings("rawtypes")
	public List<Map> selectColor(Map<String, String> queryParam) throws ServiceBizException;

}
