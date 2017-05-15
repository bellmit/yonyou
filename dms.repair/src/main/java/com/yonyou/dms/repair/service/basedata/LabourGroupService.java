
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : LabourGroupService.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年8月4日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月4日    DuPengXin   1.0
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
import com.yonyou.dms.repair.domains.DTO.basedata.LabourGroupDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.LabourSubgroupDTO;
import com.yonyou.dms.repair.domains.PO.basedata.LabourGroupPO;

/**
 * 维修项目分类 接口
 * @author DuPengXin
 * @date 2016年8月4日
 */

@SuppressWarnings("rawtypes")
public interface LabourGroupService {
    public PageInfoDto QueryLabourGroup(Map<String, String> queryParam) throws ServiceBizException;
    public PageInfoDto queryLabourSubgroup(String id) throws ServiceBizException;
    public String addLabourGroup(LabourGroupDTO labourgroupdto) throws ServiceBizException;
    public String addLabourSubgroup(LabourSubgroupDTO laboursubgroupdto) throws ServiceBizException;
    public LabourGroupPO findById(String id) throws ServiceBizException;
    public Map<String, Object> findByIditem(String id) throws ServiceBizException;
    public void updateLabourGroup(String id,LabourGroupDTO labourgroupdto) throws ServiceBizException;
    public void updateLabourSubgroup(String id,LabourSubgroupDTO laboursubgroupdto) throws ServiceBizException;
	public Map<String,List<Map>> queryAlllabourGroup();
    public List<Map> getGroupList();
    public List<Map> subGroupList(String mainGroupCode);
}
