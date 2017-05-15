package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrSpecialVehwarrantyDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrSpecialVehwarrantyPO;

/**
 * 特殊车辆质保期维护
 * @author Administrator
 *
 */
public interface TtWrSpecialVehwarrantyService {
	//特殊车辆质保期维护查询
	public PageInfoDto  SpecialVehwarrantyQuery(Map<String, String> queryParam) ;
	//新增特殊车辆质保期维护
	public Long add(TtWrSpecialVehwarrantyDTO ptdto) ;
	//修改信息回显
	public TtWrSpecialVehwarrantyPO getSpecialVehwarrantyById(Long id); 
	//修改
	public void edit(Long id, TtWrSpecialVehwarrantyDTO ptdto) ;
	//删除
	public void delete(Long id);
	
}
