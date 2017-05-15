package com.yonyou.dms.vehicle.service.orderManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface FactoryOrderService {

	/**
	 * 工厂订单查询条件
	 * 
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto orderQuery(Map<String, String> queryParam);

	/**
	 * 工厂订单下载
	 * 
	 * @param queryParam
	 * @return
	 */
	public List<Map> findFactroyOrderDownload(Map<String, String> queryParam);

}
