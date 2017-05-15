package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrDealerLevelDTO;
/**
 * 经销商等级维护
 * @author zhiahongmiao 
 *
 */
public interface DealerLevelMaintainService {
	//查询
	public PageInfoDto DealerLevelQuery(Map<String,String> queryParam) throws ServiceBizException;
	
	//新增
	public Long add(TtWrDealerLevelDTO dto)throws  ServiceBizException;
	
	//修改
	public void update(TtWrDealerLevelDTO dto)throws  ServiceBizException;
}
