package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.math.BigDecimal;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWarrantyTypeDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtActivityDTO;

/**
 * 保修类型（活动）
 * @author zhanghongyi
 *
 */
public interface WarrantyTypeActService {
	//保修类型查询
	public PageInfoDto warrantyTypeQuery(Map<String, String> queryParam)throws  ServiceBizException;
	
	//保修类型新增
	public Long addWarrantyType(TtWrWarrantyTypeDTO dto)throws  ServiceBizException;
	
	//保修类型修改
	public void updateWarrantyType(TtWrWarrantyTypeDTO dto)throws  ServiceBizException;
	
	//活动新增
	public void addAct(TtWrWtActivityDTO dto)throws  ServiceBizException;
	
	//删除活动
	public void deleteAct(BigDecimal id)throws  ServiceBizException;
	
	//活动修改
	public void updateAct(TtWrWtActivityDTO dto)throws  ServiceBizException;
}
