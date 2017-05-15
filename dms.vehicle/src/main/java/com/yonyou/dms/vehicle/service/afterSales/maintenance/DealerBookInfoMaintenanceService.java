package com.yonyou.dms.vehicle.service.afterSales.maintenance;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.maintenance.TmWxReserveSpecialistDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.maintenance.TmWxReserveSpecialistPO;

/**
 * 经销商微信预约专员维护
 * @author Administrator
 *
 */
public interface DealerBookInfoMaintenanceService {
	//经销商微信预约专员查询
	public PageInfoDto DealerBookInfoMaintenanceQuery(Map<String, String> queryParam) ;
	//经销商新增微信预约专员维护信息
	public Long addMaintenanceDealer(TmWxReserveSpecialistDTO ptdto);
	//经销商微信预约专员修改
	public void edit(Long id,TmWxReserveSpecialistDTO dto);
	//经销商微信预约专员修改信息回显
	public TmWxReserveSpecialistPO getMaintenanceDealerById(Long id); 
	//经销商微信预约专员信息删除
	public void delete(Long id);
}
