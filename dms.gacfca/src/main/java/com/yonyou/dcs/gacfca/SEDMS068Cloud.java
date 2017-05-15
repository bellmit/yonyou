package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.SEDMS068Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SEDMS068Cloud {
	
	public String execute(String allocateOutNo) throws ServiceBizException;
	
	public LinkedList<SEDMS068Dto> getDataList(String allocateOutNo) throws ServiceBizException;

}
