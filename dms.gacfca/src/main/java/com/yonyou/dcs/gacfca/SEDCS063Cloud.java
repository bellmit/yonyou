package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.SEDMS063Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SEDCS063Cloud {
	
	public String execute(String id) throws ServiceBizException;
	
	public LinkedList<SEDMS063Dto> getDataList(String id) throws ServiceBizException;

}
