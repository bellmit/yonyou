package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.OwnerVehicleDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SADMS065Cloud {
	
	public String execute(String vinList) throws ServiceBizException;
	
	public LinkedList<OwnerVehicleDto> getDataList(String vinList) throws ServiceBizException;

}
