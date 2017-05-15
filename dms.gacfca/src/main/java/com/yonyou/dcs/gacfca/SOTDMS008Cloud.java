package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.PtDlyInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDMS008Cloud {
	
	public String execute() throws ServiceBizException;
	
	public LinkedList<PtDlyInfoDto> getDataList() throws ServiceBizException;

}
