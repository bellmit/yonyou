package com.yonyou.dms.schedule.service.impl;

import java.util.LinkedList;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.schedule.domains.DTO.BigCustomerVisitItemDto;
/**
 * 计划任务每周一上报大客户拜访信息 接口
 * 计划任务
 * @author wangliang
 * @date 2017年2月22日
 */

public interface SADMS053Service {

	public LinkedList<BigCustomerVisitItemDto> getSADMS053() ; 
	
}
