
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ServiceProjectService.java
*
* @Author : zhongsw
*
* @Date : 2016年9月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月11日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.ordermanage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.ordermanage.ServiceProjectDTO;
import com.yonyou.dms.retail.domains.PO.ordermanage.ServiceProjectPO;

/**
* TODO description
* @author zhongsw
* @date 2016年9月11日
*/

public interface ServiceProjectService {
    
    public PageInfoDto searchServiceProject(Map<String, String> queryParam)throws ServiceBizException;///查询
    
    public Long add(ServiceProjectDTO serviceprojectdto,ServiceProjectPO lap)throws ServiceBizException;///增加
    
    public void update(Long id,ServiceProjectDTO serviceprojectdto)throws ServiceBizException;///修改
    
    public void deleteById(Long id)throws ServiceBizException;///删除
    
    public Map editSearch(Long id)throws ServiceBizException;//编辑明细界面带数据

}
