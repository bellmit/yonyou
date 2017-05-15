
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : PositionService.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年7月15日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月15日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.MaintainWorkTypePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.RepairPositionDTO;

/**
 * @author DuPengXin
 * @date 2016年7月15日
 */

public interface RepairPositionService {
    public PageInfoDto QueryPosition(Map<String, String> queryParam) throws ServiceBizException;
    public Long addPosition(RepairPositionDTO positiondto) throws ServiceBizException;
    public void updatePosition(Long id,RepairPositionDTO positiondto) throws ServiceBizException;
    public MaintainWorkTypePO findById(Long id) throws ServiceBizException;
    public List<Map> findAllRepairPosition()throws ServiceBizException;
    public String queryPositionType(Long id)throws ServiceBizException;
    public List<Map> queryPosition() throws ServiceBizException;
}
