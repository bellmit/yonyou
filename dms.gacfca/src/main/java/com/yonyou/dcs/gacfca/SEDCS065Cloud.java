package com.yonyou.dcs.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.SEDMS065Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SEDCS065Cloud {

	LinkedList<SEDMS065Dto> getDataList() throws ServiceBizException;

	String execute() throws ServiceBizException;

}
