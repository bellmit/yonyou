
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : AppendProjectService.java
*
* @Author : zhongsw
*
* @Date : 2016年8月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月19日    zhongsw    1.0
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
import com.yonyou.dms.repair.domains.DTO.basedata.AppendProjectDTO;
import com.yonyou.dms.repair.domains.PO.basedata.AppendProjectPO;

/**
* 附加项目定义接口
* @author zhongsw
* @date 2016年8月19日
*/

@SuppressWarnings("rawtypes")
public interface AppendProjectService {
    
    public AppendProjectPO findById(String id) throws ServiceBizException;//根据ID查询

    public PageInfoDto searchAppendProject(Map<String, String> queryParam) throws ServiceBizException;//查询
    
    public String addModel(AppendProjectDTO modeldto) throws ServiceBizException;//新增
    
    public void updateModel(String id,AppendProjectDTO modeldto) throws ServiceBizException;//修改
    
	public List<Map> selectAppendProject()throws ServiceBizException;
    
    public Map selectAppendProjectByCode(String addItemCode)throws ServiceBizException;
    
}
