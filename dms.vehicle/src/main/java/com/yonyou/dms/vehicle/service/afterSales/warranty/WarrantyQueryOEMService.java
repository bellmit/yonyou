package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.math.BigDecimal;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 保修单管理OEM
 * @author zhanghongyi
 *
 */
public interface WarrantyQueryOEMService {
	//保修单查询
	public PageInfoDto WarrantyQuery(Map<String, String> queryParam);
	
	//保修单明细
	public Map<String, Object> WarrantyDetailInfoById(BigDecimal id)throws  ServiceBizException;
	
	//保修单故障明细
	public Map<String, Object> WarrantyFaultInfoById(BigDecimal id)throws  ServiceBizException;
	
	//作废
	public void delete(String wcCode)throws  ServiceBizException;
}
