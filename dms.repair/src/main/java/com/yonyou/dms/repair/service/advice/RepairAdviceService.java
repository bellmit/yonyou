/** 
 *Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 *
 * @Author : zhengcong
 *
 * @Date : 2017年5月3日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年5月3日    zhengcong    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */
package com.yonyou.dms.repair.service.advice;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.advice.RepairAdviceLabourDTO;
import com.yonyou.dms.repair.domains.DTO.advice.RepairAdvicePartDTO;
/**
 * 工单维修建议service
 * 
 * @author zhengcong	
 * @date 2017年5月3日
 */

public interface RepairAdviceService {

	public PageInfoDto queryPart(String vin)throws ServiceBizException;//工单-建议配件查询
	public PageInfoDto queryLabour(String vin)throws ServiceBizException;//工单-建议维修项目查询
	public PageInfoDto queryPartImport(String vin)throws ServiceBizException;//工单-建议配件导入查询
	public PageInfoDto queryLabourImport(String vin,String code)throws ServiceBizException;//工单-建议项目导入查询
	void pSaveData(RepairAdvicePartDTO dataDTO)throws ServiceBizException;//新增、修改、删除配件-保存
	void lSaveData(RepairAdviceLabourDTO dataDTO)throws ServiceBizException;//新增、修改、删除项目-保存

}
