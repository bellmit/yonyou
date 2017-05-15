
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : MaintainModelService.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年7月12日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月12日    DuPengXin   1.0
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
import com.yonyou.dms.repair.domains.DTO.basedata.MaintainModelDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.ModelGroupItemDTO;
import com.yonyou.dms.repair.domains.PO.basedata.MaintainModelPO;

/**
 * @author DuPengXin
 * @date 2016年7月12日
 */

public interface MaintainModelService {
    public PageInfoDto QueryModel(Map<String, String> queryParam) throws ServiceBizException;
    public Long addModel(MaintainModelDTO modeldto) throws ServiceBizException;
    public void updateModel(Long id,MaintainModelDTO modeldto) throws ServiceBizException;
    public MaintainModelPO findById(Long id) throws ServiceBizException;
    public PageInfoDto queryGroupItem(Long id) throws ServiceBizException;
    public Long addGroup(ModelGroupItemDTO groupdto) throws ServiceBizException;
    public void deleteGroupById(Long id) throws ServiceBizException;
    public String getModelGroupCode(String modelCode)throws ServiceBizException;//获取车型名
    List<Map> getModelName()throws ServiceBizException;
}
