package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppUSwapDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDMS014Cloud {
		
	public String execute() throws ServiceBizException;
	
	public LinkedList<TiAppUSwapDto> getDataList() throws ServiceBizException;

}
