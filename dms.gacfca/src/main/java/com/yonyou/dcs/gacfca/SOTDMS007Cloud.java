package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppNCultivateDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDMS007Cloud {
	
	public String execute() throws ServiceBizException;
	
	public LinkedList<TiAppNCultivateDto> getDataList() throws ServiceBizException;

}
