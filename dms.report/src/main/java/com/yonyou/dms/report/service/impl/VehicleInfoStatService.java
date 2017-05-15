package com.yonyou.dms.report.service.impl;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

/**
* 车辆进销存分析
* @author wangliang
* @date 2017年01月18日
*/
public interface VehicleInfoStatService {

	public PageInfoDto queryVeicleInfoChecked(Map<String, String> queryParam, String startDate, String endDate) throws Exception;

	public List<Map> queryVehicleInfoStatExport(Map<String, String> queryParam);


	

}
