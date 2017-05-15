
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : RepairTypeService.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年7月27日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月27日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TmRepairTypePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.RepairTypeDTO;

/**
 * @author DuPengXin
 * @date 2016年7月27日
 */

public interface RepairTypeService {
    public PageInfoDto QueryRepairType(Map<String, String> queryParam) throws ServiceBizException;
    public void updateRepairType(Map<String, String> map) throws ServiceBizException;
    public TmRepairTypePO findByCode(String id) throws ServiceBizException;
    public List<Map> queryRepairType()throws ServiceBizException;
    public List<Map> queryLabourPrice()throws ServiceBizException;
}
