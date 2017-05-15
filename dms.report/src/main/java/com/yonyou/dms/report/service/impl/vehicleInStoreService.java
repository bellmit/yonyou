package com.yonyou.dms.report.service.impl;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 车辆入库明细表Service接口
 * @author Benzc
 * @date 2017年1月17日
 */
public interface vehicleInStoreService {
	
	//分页查询信息
	public PageInfoDto queryVehicleInStore(Map<String, String> queryParam) throws ServiceBizException;
	
	//查询仓库信息
	@SuppressWarnings("rawtypes")
	public List<Map> storeSelect(Map<String,String> queryParam) throws ServiceBizException;
	
	//导出
	@SuppressWarnings("rawtypes")
	public List<Map> queryInStoreExport(Map<String,String> queryParam) throws ServiceBizException;

}
