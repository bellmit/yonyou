
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

import com.yonyou.dms.common.domains.PO.basedata.TtPartMonthReportPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.TtPartMonthReportDTO;

/**
 * 配件月报表 TODO description
 * 
 * @author chenwei
 * @date 2017年4月24日
 */

@SuppressWarnings("rawtypes")
public interface PartMonthReportService {

    public TtPartMonthReportPO findByPrimaryKeys(String reportYear, String reportMonth, String storageCode,
                                                 String partBatchNo, String partNo) throws ServiceBizException;// 根据ID查询

    public PageInfoDto searchPartMonthReport(Map<String, Object> queryParam) throws ServiceBizException;// 查询

    public Long addPartMonthReport(TtPartMonthReportDTO modeldto) throws ServiceBizException;// 新增

    public int updateModel(String sqlStr, String sqlWhere, List paramsList) throws ServiceBizException;// 修改

    public List<Map> selectPartMonthReport(TtPartMonthReportDTO queryParams) throws ServiceBizException;

}
