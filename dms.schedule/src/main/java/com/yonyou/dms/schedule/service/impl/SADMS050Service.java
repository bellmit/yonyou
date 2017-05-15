package com.yonyou.dms.schedule.service.impl;

/**
 * 二手车置换率月报周报 接口
 * @author wangliang
 * @date 2017年3月15日
 */
import java.text.ParseException;
import java.util.LinkedList;

import com.yonyou.dms.common.domains.DTO.basedata.SADCS050Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SADMS050Service {
	public LinkedList<SADCS050Dto> getSADMS050(String entityCode) throws ServiceBizException ,ParseException;
}
