package com.yonyou.dcs.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.SEDCSP6Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SEDCSP6Cloud {

	String execute() throws ServiceBizException;

	LinkedList<SEDCSP6Dto> getDataList() throws ServiceBizException;

}
