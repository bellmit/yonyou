package com.yonyou.dms.report.service.impl;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 整车销售报表接口
 * @author wangliang
 * @date 2017年1月17日
 */

public interface VehicleSalesService {
	
	//分页查询信息
	public PageInfoDto queryVehicleSalesChecked(Map<String, String> queryParam) throws ServiceBizException;
	
	//导出整车销售报表
	public List<Map> queryVehicleSalesExport(Map<String, String> queryParam) throws ServiceBizException;

}
