package com.yonyou.dms.vehicle.service.dealerStorage.vehicleAcceptance;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.checkManagement.TtVsInspectionDTO;

public interface DealerVehicleCheckMaintainService {

	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	public Map<String, Object> queryDetail(Long id, LoginInfoDto loginInfo)throws ServiceBizException;

	public void dealerVehicleDtialCheck(TtVsInspectionDTO tviDTO, LoginInfoDto loginInfo)throws ServiceBizException;
	
	public List<Map> getHourList()throws ServiceBizException ;
	

}
