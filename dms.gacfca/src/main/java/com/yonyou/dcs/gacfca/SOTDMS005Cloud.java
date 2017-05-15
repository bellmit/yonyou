package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppNSwapDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDMS005Cloud {
	
	public String execute() throws ServiceBizException;
	
	public LinkedList<TiAppNSwapDto> getDataList() throws ServiceBizException;


}
