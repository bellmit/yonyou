package com.yonyou.dms.vehicle.service.afterSales.maintenance;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.maintenance.TmWxReserverItemDefDTO;

/**
 * 微信预约时段空闲度设定
 * @author Administrator
 *
 */
public interface DealerMakeAnPointmentService {
	//微信预约时段空闲度设定查询
	public PageInfoDto DealerMakeAnPointmentQuery(Map<String, String> queryParam);
	//微信预约时段空闲度设定更新
	public void edit(TmWxReserverItemDefDTO dto);

}
