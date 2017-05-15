package com.yonyou.dms.vehicle.service.afterSales.maintenance;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.maintenance.TmWxReserveSpecialistDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.maintenance.TmWxReserveSpecialistPO;

/**
 * 微信预约专员查询
 * @author Administrator
 *
 */
public interface VehicleBookInfoMaintenanceService {
	//微信预约专员查询
	public PageInfoDto BookInfoMaintenanceQuery(Map<String, String> queryParam) ;
	//新增微信预约专员维护信息
	public Long addMaintenance(TmWxReserveSpecialistDTO ptdto);
	//微信预约专员修改
	public void edit(Long id,TmWxReserveSpecialistDTO dto);
	//微信预约专员修改信息回显
	public TmWxReserveSpecialistPO getMaintenanceById(Long id); 
	//微信预约专员信息删除
	public void delete(Long id);

}
