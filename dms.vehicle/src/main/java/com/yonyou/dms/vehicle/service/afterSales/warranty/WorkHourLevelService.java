package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWorrkhourLevelDTO;
/**
 * 工时等级维护
 * @author zhanghongyi 
 *
 */
public interface WorkHourLevelService {
	//查询
	public PageInfoDto query(Map<String,String> queryParam) throws ServiceBizException;
	
	//新增
	public Long add(TtWrWorrkhourLevelDTO dto)throws  ServiceBizException;
	
	//修改
	public void update(TtWrWorrkhourLevelDTO dto)throws  ServiceBizException;
	
	//查询保修类型
	public List<Map> getWarrantyType(Map<String, String> queryParams) throws ServiceBizException;
}
