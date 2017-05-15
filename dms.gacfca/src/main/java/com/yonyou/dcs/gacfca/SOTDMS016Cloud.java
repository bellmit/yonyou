package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppUCultivateDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDMS016Cloud {
		
	public String execute() throws ServiceBizException;
	
	public LinkedList<TiAppUCultivateDto> getDataList() throws ServiceBizException;


}
