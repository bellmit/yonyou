
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : StoreService.java
*
* @Author : zhongsw
*
* @Date : 2016年7月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月10日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.PartModelGroupPo;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.PartModelGroupDTO;
import com.yonyou.dms.part.domains.DTO.basedata.StoreDTO;

/**
* 配件车型组接口
* @author chenwei
* @date 2017年3月23日
*/

public interface PartModelGroupService {

    public PartModelGroupPo findByPrimaryKey(String partModelGroupCode)throws ServiceBizException;
    
    public PageInfoDto searchPartModelGroup(Map<String, String> queryParam)throws ServiceBizException;///查询
    
    public String insertPartModelGroupPo(PartModelGroupDTO partModelGroupto)throws ServiceBizException;///增加
    
    public void updatePartModelGroup(String partModelGroupCode,PartModelGroupDTO partModelGroupto)throws ServiceBizException;///修改
    
    public void deletePartModelGroupById(Long id)throws ServiceBizException;///删除

    public List<Map> queryStore(Map<String, String> queryParam) throws ServiceBizException;
}
