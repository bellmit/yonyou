package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SA007Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: SADCS007Cloud 
* @Description: 经销商之间车辆调拨下发
* @author zhengzengliang 
* @date 2017年4月12日 下午7:17:20 
*
 */
public interface SADCS007Cloud {
	
	public String execute(List<Long> dealerIds,Long vehicleId,Long inDealerId,Long outDealerId) throws Exception;
	
	public List<SA007Dto> getDataList(Long vehicleId,Long inDealerId,Long outDealerId) throws ServiceBizException;

}
