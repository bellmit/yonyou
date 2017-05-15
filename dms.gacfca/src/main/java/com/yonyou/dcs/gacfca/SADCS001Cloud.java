package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.VehicleShippingDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: SADCS001Cloud 
* @Description: 车辆发运信息下发
* @author zhengzengliang 
* @date 2017年4月11日 下午4:36:23 
*
 */
public interface SADCS001Cloud {
	
	public String execute(String dealerList,String dealerId, String vehicleId) throws ServiceBizException;
	
	public LinkedList<VehicleShippingDto> getDataList(String dealerId, String vehicleId) throws ServiceBizException;
	
	public List<String> sendData(String dealerCode, String dealerId, String vehicleId);

}
