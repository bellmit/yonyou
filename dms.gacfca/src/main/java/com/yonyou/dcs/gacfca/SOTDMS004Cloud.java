package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDMS004Cloud {
	
	public String execute() throws ServiceBizException;
	
	public LinkedList<TiAppNTestDriveDto> getDataList() throws ServiceBizException;

}
