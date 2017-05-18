package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 客户意向综合查询
 * 
 * @author wangxin
 * @date 2016年12月26日
 */
public interface IntentVehicleService {

	public PageInfoDto queryintentVehicle(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> queryintentVehiclerExport(Map<String, String> queryParam) throws ServiceBizException;
}
