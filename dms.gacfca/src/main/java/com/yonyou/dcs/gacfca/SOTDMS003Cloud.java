package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppNCustomerInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDMS003Cloud {
	
	public String execute() throws ServiceBizException;
	
	public LinkedList<TiAppNCustomerInfoDto> getDataList() throws ServiceBizException;

}
